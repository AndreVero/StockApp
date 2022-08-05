package com.example.stockapp.data.mappers

import com.example.stockapp.data.local.CompanyListingEntity
import com.example.stockapp.data.remote.dto.CompanyInfoDto
import com.example.stockapp.domain.model.CompanyInfoModel
import com.example.stockapp.domain.model.CompanyListingModel

fun CompanyListingEntity.toCompanyListing(): CompanyListingModel {
    return CompanyListingModel(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListingModel.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfoModel(): CompanyInfoModel {
    return CompanyInfoModel(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}