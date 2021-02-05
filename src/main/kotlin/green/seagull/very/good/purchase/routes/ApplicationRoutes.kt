package green.seagull.very.good.purchase.routes


import green.seagull.very.good.purchase.dto.PurchaseDto
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode
import org.springframework.stereotype.Component

@Component
class ApplicationRoutes : RouteBuilder() {

    override fun configure() {
        restConfiguration()
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/api")
                .apiContextPath("/api-doc")
                    .apiProperty("api.title", "Very Good Purchase API").apiProperty("api.version", "1.0.0")
                    .apiProperty("cors", "true")

        rest("/purchases").description("Purchases REST service")
            .consumes("application/json").produces("application/json")

            .get("/").description("List of all purchases")
                .outType(Array<PurchaseDto>::class.java)
                        .responseMessage().code(200).message("All purchases").endResponseMessage()
                    .to("bean:temporaryPurchaseService?method=findAll");

    }
}