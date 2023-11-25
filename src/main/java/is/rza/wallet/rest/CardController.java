package is.rza.wallet.rest;

import is.rza.wallet.model.dto.CardDTO;
import is.rza.wallet.model.dto.CreateCardDTO;
import is.rza.wallet.model.dto.CreateTransactionDTO;
import is.rza.wallet.model.dto.TransactionDTO;
import is.rza.wallet.service.card.ICardService;
import is.rza.wallet.service.transaction.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("card")
public class CardController {
    private final ICardService cardService;

    @GetMapping
    List<CardDTO> getAll() {
        return cardService.getAll();
    }

    @GetMapping("/{id}")
    CardDTO getById(@PathVariable Long id) {
        return cardService.getById(id);
    }

    @GetMapping("/pan/{pan}")
    CardDTO getById(@PathVariable String pan) {
        return cardService.getByPan(pan);
    }

    @PostMapping
    CardDTO create(@RequestBody CreateCardDTO body) {
        return cardService.create(body);
    }

    @DeleteMapping("/{id}")
    CardDTO deleteById(@PathVariable Long id) {
        return cardService.deleteById(id);
    }
}
