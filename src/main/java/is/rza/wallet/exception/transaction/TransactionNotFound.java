package is.rza.wallet.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFound extends RuntimeException {
    public TransactionNotFound(Long id) {
        super("transaction id: " + id);
    }
}