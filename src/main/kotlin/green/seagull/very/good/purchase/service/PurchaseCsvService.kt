package green.seagull.very.good.purchase.service

import green.seagull.very.good.purchase.dto.PurchaseDto
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths


@Service
class PurchaseCsvService(@Value("\${csv.file.path}") var csvFile: String) {

    fun findAll(): List<PurchaseDto> {
        Files.newBufferedReader(Paths.get(csvFile)).use { reader ->
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
        Files.newBufferedWriter(Paths.get(csvFile)).use { writer ->
            CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("date", "title", "purchaseType", "amountDollars")).use { csvPrinter ->
                with (purchaseDto) {
                    csvPrinter.printRecord(date, title, purchaseType, amountDollars.toPlainString())
                }
                csvPrinter.flush()
            }
        }
        return purchaseDto
    }
}