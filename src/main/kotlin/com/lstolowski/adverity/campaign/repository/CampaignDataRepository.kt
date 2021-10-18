package com.lstolowski.adverity.campaign.repository

import com.lstolowski.adverity.campaign.domain.CampaignData
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate


interface CampaignDataRepository : CrudRepository<CampaignData, Long> {
    fun findAllByDatasourceAndCampaign(datasource: String, campaign: String): Iterable<CampaignData>
    fun findAllByDatasourceAndDailyBetween(datasource: String, from: LocalDate, to: LocalDate): Iterable<CampaignData>
}