package green.seagull.very.good.purchase

import green.seagull.very.good.purchase.dto.PurchaseDto
import org.springframework.stereotype.Service

@Service
class TemporaryPurchaseService(var purchases: List<PurchaseDto> = emptyList()) {
    fun findAll() = purchases
}