package com.c.digis.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.c.digis.R
import com.c.digis.databinding.ChartsFragmentBinding
import com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class ChartsFragment : Fragment() {
    private lateinit var binding: ChartsFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ChartsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        viewModel.mRandomValues.observe(viewLifecycleOwner, {
            binding.rsrpChart.apply {
                axisRight.isEnabled = false
                description.isEnabled = false
                setDrawGridBackground(false)
                xAxis.position = BOTTOM
                axisLeft.axisMaximum = -50f
                axisLeft.axisMinimum = -150f
                axisLeft.mAxisRange = 20f
                xAxis.valueFormatter = ClaimsXAxisValueFormatter(it.map { it1 -> it1.date!! })
            }
            binding.rsrqChart.apply {
                axisRight.isEnabled = false
                description.isEnabled = false
                setDrawGridBackground(false)
                xAxis.position = BOTTOM
                axisLeft.axisMaximum = 0f
                axisLeft.axisMinimum = -30f
                axisLeft.mAxisRange = 5f
                xAxis.valueFormatter = ClaimsXAxisValueFormatter(it.map { it1 -> it1.date!! })
            }
            binding.sinrChart.apply {
                axisRight.isEnabled = false
                description.isEnabled = false
                setDrawGridBackground(false)
                xAxis.position = BOTTOM
                axisLeft.axisMaximum = 35f
                axisLeft.axisMinimum = -15f
                axisLeft.mAxisRange = 10f
                xAxis.valueFormatter = ClaimsXAxisValueFormatter(it.map { it1 -> it1.date!! })
            }

            val rsrpEntries =
                it.mapIndexed { index, value ->
                    Entry(
                        index.toFloat(),
                        value.RSRP.toFloat()
                    )
                }
            val rsrpDataSet = LineDataSet(rsrpEntries, "RSRP").also { set ->
                set.color = resources.getColor(R.color.rsrp, resources.newTheme())
                set.lineWidth = 2f
                set.setCircleColor(resources.getColor(R.color.rsrp, resources.newTheme()))
                set.circleRadius = 4f
                set.setDrawValues(false)
                set.circleHoleColor = resources.getColor(R.color.rsrp, resources.newTheme())
                set.axisDependency
            }
            binding.rsrpChart.data = LineData(rsrpDataSet)
            binding.rsrpChart.invalidate()

            val rsrqEntries =
                it.mapIndexed { index, value ->
                    Entry(
                        index.toFloat(),
                        value.RSRQ.toFloat()
                    )
                }
            val rsrqDataSet = LineDataSet(rsrqEntries, "RSRQ").also { set ->
                set.color = resources.getColor(R.color.rsrq, resources.newTheme())
                set.lineWidth = 2f
                set.setCircleColor(resources.getColor(R.color.rsrq, resources.newTheme()))
                set.circleRadius = 4f
                set.setDrawValues(false)
                set.circleHoleColor = resources.getColor(R.color.rsrq, resources.newTheme())
                set.axisDependency
            }
            binding.rsrqChart.data = LineData(rsrqDataSet)
            binding.rsrqChart.invalidate()

            val sinrEntries =
                it.mapIndexed { index, value ->
                    Entry(
                        index.toFloat(),
                        value.SINR.toFloat()
                    )
                }
            val sinrDataSet = LineDataSet(sinrEntries, "SINR").also { set ->
                set.color = resources.getColor(R.color.sinr, resources.newTheme())
                set.lineWidth = 2f
                set.setCircleColor(resources.getColor(R.color.sinr, resources.newTheme()))
                set.circleRadius = 4f
                set.setDrawValues(false)
                set.circleHoleColor = resources.getColor(R.color.sinr, resources.newTheme())
                set.axisDependency
            }
            binding.sinrChart.data = LineData(sinrDataSet)
            binding.sinrChart.invalidate()

        })
    }
}