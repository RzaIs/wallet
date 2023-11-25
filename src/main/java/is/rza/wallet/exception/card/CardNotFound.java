package is.rza.wallet.exception.card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CardNotFound extends RuntimeException {
    public CardNotFound(String pan) {
        super("card pan: " + pan);
    }

    public CardNotFound(Long id) {
        super("card pan: " + id);
    }
}