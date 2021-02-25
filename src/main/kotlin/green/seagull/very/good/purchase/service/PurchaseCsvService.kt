package green.seagull.very.good.purchase.service

import green.seagull.very.good.purchase.dto.PurchaseDto
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.BufferedWriter
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


@Service
class PurchaseCsvService(@Value("\${csv.file.path}") val csvFile: String) {

    companion object {
        private val log = LoggerFactory.getLogger(PurchaseCsvService::class.java)
    }

    private val csvFilePath = Paths.get(csvFile)

    fun findAll(): List<PurchaseDto> {
        log.info("csv.file.path: $csvFilePath")
        if (Files.notExists(csvFilePath))
            return emptyList()

        Files.newBufferedReader(csvFilePath).use { reader ->
            CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
            ).use { csvParser ->

                return csvParser.map {
                    val date = it.get("date")
                    val title = it.get("title")
                    val purchaseType = it.get("purchaseType")
                    val amountDollars = it.get("amountDollars")

                    PurchaseDto(date, BigDecimal(amountDollars), title, purchaseType)
                }
            }
        }

    }

    fun updatePurchase(purchaseDto: PurchaseDto): PurchaseDto {
        log.info("Updating purchaseDto: $purchaseDto")
        if (findAll().contains(purchaseDto)) {
            log.info("Duplicate detected")
            return purchaseDto
        }

        val csvFileExists = Files.exists(csvFilePath)

        Files.newBufferedWriter(
            csvFilePath,
            StandardOpenOption.APPEND,
            StandardOpenOption.CREATE).use { writer ->

            csvPrinter(csvFileExists, writer).use { csv ->
                with(purchaseDto) {
                    csv.printRecord(date, title, purchaseType, amountDollars.toPlainString())
                }
                csv.flush()
            }
        }
        return purchaseDto
    }

    private fun csvPrinter(
        csvFileExists: Boolean,
        writer: BufferedWriter
    ): CSVPrinter {
        return if (csvFileExists)
            CSVPrinter(writer, CSVFormat.DEFAULT.withFirstRecordAsHeader())
        else
            CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("date", "title", "purchaseType", "amountDollars"))
    }
}