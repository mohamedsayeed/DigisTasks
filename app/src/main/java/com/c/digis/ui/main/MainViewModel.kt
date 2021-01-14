package com.c.digis.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c.digis.ui.data.GeneralAsset
import com.c.digis.ui.data.RandomValues
import com.c.digis.ui.network.RandomApi
import com.c.digis.ui.network.RandomClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {
    val mRandomValues = MutableLiveData<ArrayList<RandomValues>>()
    val rsrpRanges = MutableLiveData<List<GeneralAsset>>()
    val rsrqRanges = MutableLiveData<List<GeneralAsset>>()
    val sinrRanges = MutableLiveData<List<GeneralAsset>>()
    private val service: RandomApi = RandomClient.createService(RandomApi::class.java)

    init {
        mRandomValues.value = ArrayList()
    }

    fun addRandomValues(randomValues: RandomValues) {
        mRandomValues.value?.add(randomValues)
        mRandomValues.value = mRandomValues.value
        Log.v("sa", "sa $randomValues")
    }

    fun getRandomValues() {
        service.getRandomValues().enqueue(object : Callback<RandomValues> {
            override fun onResponse(call: Call<RandomValues>, response: Response<RandomValues>) {
                val baseResponse = response.body()
                baseResponse?.let {
                    val simpleDateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
                    it.date = simpleDateFormat.format(Date())
                    addRandomValues(it)
                }
            }

            override fun onFailure(call: Call<RandomValues>, t: Throwable) {
            }

        })
    }

}