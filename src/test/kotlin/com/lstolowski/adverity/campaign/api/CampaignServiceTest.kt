package com.lstolowski.adverity.campaign.api

import com.lstolowski.adverity.campaign.domain.CampaignData
import com.lstolowski.adverity.campaign.repository.CampaignDataRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class CampaignServiceTest{

    @Mock
    private lateinit var campaignDataRepository: CampaignDataRepository

    @InjectMocks
    private lateinit var campaignService: CampaignService

    @Test
    fun shouldReturnCampaignData() {

        // given
        whenever(campaignDataRepository.findAllByDatasourceAndDailyBetween(eq("testDs"), any(), any()))
                .thenReturn(listOf(CampaignData(
                        id = 1L,
                        datasource = "testDs",
                        campaign = "testCamp",
                        daily = LocalDate.of(2021, 10, 17),
                        clicks = 100L,
                        impressions = 1000L)))

        // when
        val result = campaignService.findAllByDatasourceAndDailyBetween("testDs", LocalDate.of(2021, 10, 17), LocalDate.of(2021, 10, 17))

        // then
        assertEquals(CampaignDataVO(
                    datasource = "testDs",
                    campaign = "testCamp",
                    daily = LocalDate.of(2021, 10, 17),
                    clicks = 100L,
                    impressions = 1000L),
                result[0])

    }

    @Test
    fun shouldReturnNoCampaignData() {

        // given
        whenever(campaignDataRepository.findAllByDatasourceAndDailyBetween(eq("testDs"), any(), any()))
                .thenReturn(listOf())
        // when
        val result = campaignService.findAllByDatasourceAndDailyBetween("testDs", LocalDate.of(2021, 10, 17), LocalDate.of(2021, 10, 17))
        // then
        assertTrue(result.isEmpty())
    }


    @Test
    fun shouldCountCtr() {

        // given
        whenever(campaignDataRepository.findAllByDatasourceAndCampaign(eq("testDs"), eq("testCamp")))
                .thenReturn(listOf(CampaignData(
                        id = 1L,
                        datasource = "testDs",
                        campaign = "testCamp",
                        daily = LocalDate.of(2021, 10, 17),
                        clicks = 100L,
                        impressions = 1000L)))

        // when
        val result = campaignService.fetchCtrByDatasourceAndCampaign("testDs", "testCamp")

        // then
        assertEquals(CtrMetric(
                    datasource = "testDs",
                    campaign = "testCamp",
                    ctr = BigDecimal("0.1")),
                result)

    }

    @Test
    fun shouldCountCtrForEmptyData() {

        // given
        whenever(campaignDataRepository.findAllByDatasourceAndCampaign(eq("testDs"), eq("testCamp")))
                .thenReturn(listOf())

        // when
        val result = campaignService.fetchCtrByDatasourceAndCampaign("testDs", "testCamp")

        // then
        assertEquals(null, result)

    }


    @Test
    fun shouldCountImpressions() {

        // given
        whenever(campaignDataRepository.findAll())
                .thenReturn(listOf(CampaignData(
                        id = 1L,
                        datasource = "testDs",
                        campaign = "testCamp",
                        daily = LocalDate.of(2021, 10, 17),
                        clicks = 100L,
                        impressions = 1000L)))

        // when
        val result = campaignService.fetchImpressionsPerDay()

        // then
        assertEquals(listOf(ImpressionsByDateMetric(
                daily = LocalDate.of(2021, 10, 17),
                impressions = 1000)),
                result)

    }

    @Test
    fun shouldReturnEmptyListOfImpressionsForEmptyData() {

        // given
        whenever(campaignDataRepository.findAll())
                .thenReturn(listOf())

        // when
        val result = campaignService.fetchImpressionsPerDay()

        // then
        assertTrue(result.isEmpty())

    }

}