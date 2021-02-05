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
    }

    private val purchaseCsvService = PurchaseCsvService(TEST_CSV)

    @BeforeEach
    fun cleanup() {
        if (Files.exists(TEST_CSV_PATH))
            Files.delete(TEST_CSV_PATH)
    }

    @Test
    fun `store purchase as in csv file`() {
        purchaseCsvService.updatePurchase(
                PurchaseDto("2021-02-05", BigDecimal("100"), "Way of Kings", "Book"))

        assertThat(purchaseCsvService.findAll()).hasSize(1)
        with (purchaseCsvService.findAll().first()) {
            assertThat(date).isEqualTo("2021-02-05")
            assertThat(amountDollars).isEqualTo(BigDecimal("100"))
            assertThat(title).isEqualTo("Way of Kings")
            assertThat(purchaseType).isEqualTo("Book")
        }
    }
}