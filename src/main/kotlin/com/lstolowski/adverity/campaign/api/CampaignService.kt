package com.lstolowski.adverity.campaign.api

import com.lstolowski.adverity.campaign.repository.CampaignDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.stream.Collectors

@Service
class CampaignService @Autowired constructor(
        val campaignDataRepository: CampaignDataRepository

) {

    fun findAllByDatasourceAndDailyBetween(datasource: String,
                                           from: LocalDate,
                                           to: LocalDate): List<CampaignDataVO> =
            campaignDataRepository.findAllByDatasourceAndDailyBetween(
                    datasource = datasource,
                    from = from,
                    to = to
            ).map { entity ->
                CampaignDataVO(
                        datasource = entity.datasource,
                        campaign = entity.campaign,
                        daily = entity.daily,
                        clicks = entity.clicks,
                        impressions = entity.impressions
                )
            }

    fun fetchCtrByDatasourceAndCampaign(datasource: String, campaign: String): CtrMetric? {

        val ctrPair = campaignDataRepository.findAllByDatasourceAndCampaign(datasource, campaign)
                .map { rec -> ClicksAndImpressions(rec.clicks, rec.impressions) }
                .reduceOrNull { l, r -> ClicksAndImpressions(l.clicks + r.impressions, r.clicks + r.impressions) }

        return if (ctrPair != null) {
            val ctr = ctrPair.clicks.toBigDecimal().divide(ctrPair.impressions.toBigDecimal())
            CtrMetric(datasource, campaign, ctr)

        } else {
            null
        }


    }

    fun fetchImpressionsPerDay(): List<ImpressionsByDateMetric> {
        return campaignDataRepository.findAll().toList()
                .stream()
                .map { record -> ImpressionsByDateMetric(record.daily, record.impressions) }
                .collect(Collectors.groupingBy(ImpressionsByDateMetric::daily, Collectors.summarizingLong(ImpressionsByDateMetric::impressions)))
                .map { e -> ImpressionsByDateMetric(e.key, e.value.sum) }
                .sortedBy { e -> e.daily }

    }

}

data class ClicksAndImpressions(val clicks: Long, val impressions: Long)


