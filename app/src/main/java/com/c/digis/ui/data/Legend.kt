package com.c.digis.ui.data

data class Legend(
    val RSRP: List<GeneralAsset>,
    val RSRQ: List<GeneralAsset>,
    val SINR: List<GeneralAsset>
)

data class GeneralAsset(val From: String, val To: String, val Color: String)
