package com.lib.update

data class VersionEntity(
    val targetCode: Int,
    val targetName: String,
    val targetUrl: String,
    val updateLog: String,
    val targetMust: Boolean,
)