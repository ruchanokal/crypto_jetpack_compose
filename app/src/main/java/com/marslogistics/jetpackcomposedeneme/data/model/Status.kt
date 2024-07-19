package com.marslogistics.jetpackcomposedeneme.data.model

data class Status(
    val credit_count: Int = -1,
    val elapsed: Int = -1,
    val error_code: Int = -1,
    val error_message: String = "",
    val timestamp: String = ""
)