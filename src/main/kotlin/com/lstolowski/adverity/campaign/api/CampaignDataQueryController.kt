package com.lstolowski.adverity.campaign.api

import com.lstolowski.adverity.util.DateUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/metrics")
class CampaignDataQueryController @Autowired constructor(
        val campaignService: CampaignService
) {

    @GetMapping("/datasource/{datasource}/clicks")
    fun totalClicksByDatasourceAndDateRange(
            @PathVariable datasource: String,
            @RequestParam(value = "from", required = true) from: String,
            @RequestParam(value = "to", required = true) to: String
    ): TotalClicksMetric {
        val fromDate = DateUtils.toDate(from)
        val toDate = DateUtils.toDate(to)

        val clicks = campaignService.findAllByDatasourceAndDailyBetween(
                    datasource = datasource,
                    from = fromDate,
                    to = toDate)
                .map { data -> data.clicks }
                .sum()

        return TotalClicksMetric(
                datasource = datasource,
                from = fromDate,
                to = toDate,
                clicks = clicks
        )
    }

    @GetMapping("/datasource/{datasource}/campaign/{campaign}/ctr")
    fun ctrByDatasourceAndCampaign(
            @PathVariable datasource: String,
            @PathVariable campaign: String
    ): CtrMetric {
        return campaignService.fetchCtrByDatasourceAndCampaign(
                datasource = datasource,
                campaign = campaign)?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "no data found")
    }

    @GetMapping("/datasource/{datasource}")
    fun fetchByDatasourceAndDateRange(
            @PathVariable datasource: String,
            @RequestParam(value = "from", required = true) from: String,
            @RequestParam(value = "to", required = true) to: String
    ): List<CampaignDataVO> {
        val fromDate = DateUtils.toDate(from)
        val toDate = DateUtils.toDate(to)

        return campaignService.findAllByDatasourceAndDailyBetween(
                datasource = datasource,
                from = fromDate,
                to = toDate)
    }

    @GetMapping("/impressions/daily")
    fun fetchImpressionsDaily(): List<ImpressionsByDateMetric> {
        return campaignService.fetchImpressionsPerDay()
    }
}