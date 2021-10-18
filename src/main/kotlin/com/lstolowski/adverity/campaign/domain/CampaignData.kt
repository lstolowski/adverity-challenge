package com.lstolowski.adverity.campaign.domain

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(indexes = [
        Index(name = "datasourceCampaignIdx", columnList = "datasource, campaign"),
        Index(name = "datasourceDailyIdx", columnList = "datasource, daily")
]
)
class CampaignData(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Column(nullable = false)
        val datasource: String,
        @Column(nullable = false)
        val campaign: String,
        @Column(nullable = false)
        val daily: LocalDate,
        @Column(nullable = false)
        val clicks: Long,
        @Column(nullable = false)
        val impressions: Long) {
}
