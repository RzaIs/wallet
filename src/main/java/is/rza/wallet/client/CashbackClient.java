package is.rza.wallet.client;

import is.rza.wallet.model.dto.CashbackDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(
        name = "CashbackClient",
        url = "localhost:8083"
)
public interface CashbackClient {
    @GetMapping("/cashback")
    CashbackDTO calculateCashback(@RequestParam BigDecimal transactionAmount);
}
