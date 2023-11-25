package is.rza.wallet.exception.card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OverdraftTransaction extends RuntimeException {
    public OverdraftTransaction(Long cardId, BigDecimal balance, BigDecimal trAmount) {
        super(
            "Overdraft from card(" + cardId + ") with balance("
            + balance +
            ") for transaction amount(" + trAmount + ")"
        );
    }
}
