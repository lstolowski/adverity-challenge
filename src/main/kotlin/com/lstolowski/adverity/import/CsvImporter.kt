package com.lstolowski.adverity.import

import com.lstolowski.adverity.campaign.domain.CampaignData
import com.lstolowski.adverity.campaign.repository.CampaignDataRepository
import com.lstolowski.adverity.util.DateUtils
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.ClassLoader.getSystemResource
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.annotation.PostConstruct

@Service
class CsvImporter @Autowired constructor(
        val campaignDataRepository: CampaignDataRepository) {

    companion object {
        const val FILE_NAME = "PIxSyyrIKFORrCXfMYqZBI.csv"
    }

    @PostConstruct
    fun importDataFromCsvToDb() {

        val reader = Files.newBufferedReader(pathToFile())
        val csvParser = CSVParser(reader,
                CSVFormat.Builder.create(CSVFormat.DEFAULT)
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .build())

        csvParser.stream()
                .map { csvRecord -> toCampaignData(csvRecord) }
                .forEach { campaignDataRepository.save(it) }
    }

    private fun toCampaignData(csvRecord: CSVRecord): CampaignData {
        val datasource = csvRecord.get(0)
        val campaign = csvRecord.get(1)
        val daily = DateUtils.toDate(csvRecord.get(2))
        val clicks = csvRecord.get(3).toLong()
        val impressions = csvRecord.get(4).toLong()

        return CampaignData(
                datasource = datasource,
                campaign = campaign,
                daily = daily,
                clicks = clicks,
                impressions = impressions,
                id = 0 // id generator will work
        )
    }

    private fun pathToFile(): Path {
        return Paths.get(getSystemResource(FILE_NAME).toURI())
    }
}