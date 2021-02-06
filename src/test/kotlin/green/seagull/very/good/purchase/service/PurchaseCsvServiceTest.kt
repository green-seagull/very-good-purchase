package green.seagull.very.good.purchase.service

import green.seagull.very.good.purchase.dto.PurchaseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

internal class PurchaseCsvServiceTest {
    companion object {
        const val TEST_CSV = "/tmp/test-purchases.csv"
        val TEST_CSV_PATH: Path = Paths.get(TEST_CSV)

        const val DATE = "2021-02-05"
        const val TITLE = "Way of Kings"
        const val PURCHASE_TYPE = "Book"
        private val AMOUNT = BigDecimal("100")
    }

    private val purchaseCsvService = PurchaseCsvService(TEST_CSV)

    @BeforeEach
    fun cleanup() {
        if (Files.exists(TEST_CSV_PATH))
            Files.delete(TEST_CSV_PATH)
    }

    @Test
    fun `store purchase as in csv file`() {
        purchaseCsvService.updatePurchase(somePurchaseDto())

        assertThat(purchaseCsvService.findAll()).hasSize(1)
        with (purchaseCsvService.findAll().first()) {
            assertThat(date).isEqualTo(DATE)
            assertThat(amountDollars).isEqualTo(AMOUNT)
            assertThat(title).isEqualTo(TITLE)
            assertThat(purchaseType).isEqualTo(PURCHASE_TYPE)
        }
    }

    @Test
    fun `append 2nd purchase in csv file once`() {
        purchaseCsvService.updatePurchase(somePurchaseDto())
        purchaseCsvService.updatePurchase(somePurchaseDto(title = "Words of Radiance"))

        assertThat(purchaseCsvService.findAll()).hasSize(2)
        assertThat(purchaseCsvService.findAll().map { it.title }).containsExactly(TITLE, "Words of Radiance")
    }

    private fun somePurchaseDto(date: String = DATE,
                                amount: BigDecimal = AMOUNT,
                                title: String = TITLE,
                                purchaseType: String = PURCHASE_TYPE) =
        PurchaseDto(date, amount, title, purchaseType)
}