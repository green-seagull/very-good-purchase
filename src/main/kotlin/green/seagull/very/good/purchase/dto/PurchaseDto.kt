package green.seagull.very.good.purchase.dto

import java.math.BigDecimal

data class PurchaseDto(
        var date: String,
        var amountDollars: BigDecimal,
        var title: String,
        var purchaseType: String
) {
    // For json deserialisers
    constructor() : this(
            "",
            BigDecimal.ZERO,
            "",
            ""
    )
}
