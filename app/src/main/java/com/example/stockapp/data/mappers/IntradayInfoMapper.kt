package com.example.stockapp.data.mappers

import com.example.stockapp.data.remote.dto.IntradayInfoDto
import com.example.stockapp.domain.model.IntradayInfoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfoModel(): IntradayInfoModel {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfoModel(
        date = localDateTime,
        close = close
    )
}