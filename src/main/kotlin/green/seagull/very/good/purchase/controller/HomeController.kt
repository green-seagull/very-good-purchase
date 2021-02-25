package green.seagull.very.good.purchase.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    @RequestMapping("/")
    fun home() = "index" // src/main/resources/static/index.html
}