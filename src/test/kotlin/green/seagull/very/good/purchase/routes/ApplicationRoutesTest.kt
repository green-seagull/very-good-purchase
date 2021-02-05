package green.seagull.very.good.purchase.routes


import green.seagull.very.good.purchase.TemporaryPurchaseService
import green.seagull.very.good.purchase.dto.PurchaseDto
import org.apache.camel.test.spring.junit5.CamelSpringBootTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal


@CamelSpringBootTest
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("production")
class ApplicationRoutesTest {
    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var purchaseService: TemporaryPurchaseService

    @Test
    fun `GET api-doc`() {
        val response = restTemplate.getForEntity("http://localhost:$port/api/api-doc", String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `GET from api purchases`() {
        purchaseService.purchases = listOf(
                PurchaseDto("2021-01-17", BigDecimal("5.55"), "Fool's Assassin", "Book"))

        // Warning: For REST API to work it requires settings in resources/application[-<profile>].yaml
        val response = restTemplate.getForEntity("http://localhost:$port/api/purchases", Array<PurchaseDto>::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        assertThat(response.body).hasSize(1)
        with (response.body!!.first()) {
            assertThat(amountDollars).isEqualByComparingTo(BigDecimal("5.55"))
            assertThat(title).isEqualTo("Fool's Assassin")
            assertThat(purchaseType).isEqualTo("Book")
            assertThat(date).isEqualTo("2021-01-17")
        }
    }
}

