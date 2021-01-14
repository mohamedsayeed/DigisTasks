package com.c.digis.ui.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.c.digis.R
import com.c.digis.ui.data.RandomValues

class PagerViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {

    private val rsrp by lazy { view.findViewById<TextView>(R.id.rsrp) }
    private val rsrpProgress by lazy { view.findViewById<ProgressBar>(R.id.rsrp_progress) }
    private val rsrqProgress by lazy { view.findViewById<ProgressBar>(R.id.rsrq_progress) }
    private val sinrProgress by lazy { view.findViewById<ProgressBar>(R.id.sinr_progress) }
    private val rsrq by lazy { view.findViewById<TextView>(R.id.rsrq) }
    private val snr by lazy { view.findViewById<TextView>(R.id.sinr) }

    fun bind(values: RandomValues, viewModel: MainViewModel) {
        rsrp.text = values.RSRP.toString()
        rsrq.text = values.RSRQ.toString()
        snr.text = values.SINR.toString()
        val rsrpRanges = viewModel.rsrpRanges.value
        rsrpProgress.max = rsrpRanges?.size!!
        rsrpRanges.forEachIndexed { index, it ->
            when {
                it.From == "Min" && values.RSRP <= it.To.toInt() ||
                        it.From != "Min" && it.To != "Max" && values.RSRP >= it.From.toInt() && values.RSRP <= it.To.toInt() ||
                        it.To == "Max" && values.RSRP >= it.From.toInt() -> {
                    rsrpProgress.progress = index + 1
                    rsrpProgress.progressTintList =
                        ColorStateList.valueOf(Color.parseColor(it.Color))
                }
            }
        }

        val rsrqRanges = viewModel.rsrqRanges.value
        rsrqProgress.max = rsrqRanges?.size!!
        rsrqRanges.forEachIndexed { index, it ->
            when {
                it.From == "Min" && values.RSRQ <= it.To.toDouble() ||
                        it.From != "Min" && it.To != "Max" && values.RSRQ >= it.From.toDouble() && values.RSRQ <= it.To.toDouble() ||
                        it.To == "Max" && values.RSRQ >= it.From.toDouble() -> {
                    rsrqProgress.progress = index + 1
                    rsrqProgress.progressTintList =
                        ColorStateList.valueOf(Color.parseColor(it.Color))
                }
            }
        }

        val sinrRanges = viewModel.sinrRanges.value
        sinrProgress.max = sinrRanges?.size!!
        sinrRanges.forEachIndexed { index, it ->
            when {
                it.From == "Min" && values.SINR <= it.To.toInt() ||
                        it.From != "Min" && it.To != "Max" && values.SINR >= it.From.toInt() && values.SINR <= it.To.toInt() ||
                        it.To == "Max" && values.SINR >= it.From.toInt() -> {
                    sinrProgress.progress = index + 1
                    sinrProgress.progressTintList =
                        ColorStateList.valueOf(Color.parseColor(it.Color))
                }
            }
        }

    }

}

class ValuesAdapter(
    private val randomValues: List<RandomValues>,
    private val viewModel: MainViewModel,
) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parentView: ViewGroup, p1: Int): PagerViewHolder {
        return LayoutInflater
            .from(parentView.context)
            .inflate(R.layout.row_random, parentView, false)
            .let { PagerViewHolder(it) }
    }

    override fun onBindViewHolder(viewHolder: PagerViewHolder, position: Int) {
        viewHolder.bind(randomValues[position], viewModel)
    }

    override fun getItemCount() = randomValues.size
}