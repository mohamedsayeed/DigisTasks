package com.c.digis.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.digis.databinding.MainFragmentBinding
import com.c.digis.ui.data.RandomValues
import java.util.*


class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding


    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.mRandomValues.observe(viewLifecycleOwner, {
            initValuesRecycler(it)
        })
    }


    private fun initValuesRecycler(values: ArrayList<RandomValues>) {
        val valuesAdapter = ValuesAdapter(values, viewModel)
        binding.rvRandoms.layoutManager = LinearLayoutManager(activity)
        binding.rvRandoms.isNestedScrollingEnabled = false
        binding.rvRandoms.adapter = valuesAdapter
//        binding.rvRandoms.addItemDecoration(DividerItemDecoration(context,
//            VERTICAL)
//        )
    }

}

