package is.rza.wallet.rest;

import is.rza.wallet.model.dto.CreateTransactionDTO;
import is.rza.wallet.model.dto.TransactionDTO;
import is.rza.wallet.service.transaction.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("transaction")
public class TransactionController {
    private final ITransactionService transactionService;

    @PostMapping
    TransactionDTO createTransaction(@RequestBody CreateTransactionDTO body) {
        return transactionService.create(body);
    }
}

