package com.lstolowski.adverity.campaign.api

import java.math.BigDecimal
import java.time.LocalDate

data class CtrMetric (
        val datasource: String,
        val campaign: String,
        val ctr: BigDecimal
)

data class TotalClicksMetric(val datasource: String,
                             val from: LocalDate,
                             val to: LocalDate,
                             val clicks: Long
)

data class ImpressionsByDateMetric(val daily: LocalDate, val impressions: Long)

data class CampaignDataVO(
        val datasource: String,
        val campaign: String,
        val daily: LocalDate,
        val clicks: Long,
        val impressions: Long
)

