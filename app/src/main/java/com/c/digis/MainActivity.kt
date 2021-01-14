package com.c.digis

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.c.digis.databinding.MainActivityBinding
import com.c.digis.ui.data.Legend
import com.c.digis.ui.main.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private val navController by lazy { findNavController(R.id.nav_main_host_fragment) }

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setValuesRanges()
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                viewModel.getRandomValues()
                mainHandler.postDelayed(this, 2000)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ic_chart -> {
                if (navController.currentDestination?.id == R.id.mainFragment)
                    navController.navigate(R.id.action_mainFragment_to_chartsFragment)
                else
                    navController.navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setValuesRanges() {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "legend.json")
        jsonFileString?.let { Log.i("data", it) }

        val listPersonType = object : TypeToken<Legend>() {}.type

        val assets: Legend = Gson().fromJson(jsonFileString, listPersonType)
        viewModel.rsrpRanges.postValue(assets.RSRP)
        viewModel.rsrqRanges.postValue(assets.RSRQ)
        viewModel.sinrRanges.postValue(assets.SINR)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}