package is.rza.wallet.service.transaction;

import is.rza.wallet.model.dto.CreateTransactionDTO;
import is.rza.wallet.model.dto.TransactionDTO;

public interface ITransactionService {
    TransactionDTO create(CreateTransactionDTO dto);
    void processCashback(Long transactionId);
}
