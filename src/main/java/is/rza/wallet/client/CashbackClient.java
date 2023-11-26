package is.rza.wallet.client;

import is.rza.wallet.model.dto.CashbackDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Profile("!local")
@FeignClient(
        name = "CashbackClient",
        url = "${client.cashback.url}"
)
public interface CashbackClient {
    @GetMapping("/cashback")
    CashbackDTO calculateCashback(@RequestParam BigDecimal transactionAmount);
}

@Component
@Profile("local")
class CashbackClientMock implements CashbackClient {
    @Override
    public CashbackDTO calculateCashback(BigDecimal transactionAmount) {
        var response = new CashbackDTO();
        response.setCashbackAmount(
                transactionAmount.multiply(BigDecimal.valueOf(0.1))
        );
        return response;
    }
}
