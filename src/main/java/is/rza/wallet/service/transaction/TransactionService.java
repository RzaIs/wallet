package is.rza.wallet.service.transaction;

import is.rza.wallet.client.CashbackClient;
import is.rza.wallet.exception.card.CardNotFound;
import is.rza.wallet.exception.card.OverdraftTransaction;
import is.rza.wallet.exception.transaction.TransactionNotFound;
import is.rza.wallet.mapper.TransactionMapper;
import is.rza.wallet.mapper.TransactionMapperImpl;
import is.rza.wallet.model.dto.CreateTransactionDTO;
import is.rza.wallet.model.dto.TransactionDTO;
import is.rza.wallet.model.entity.TransactionEntity;
import is.rza.wallet.model.entity.TransactionType;
import is.rza.wallet.queue.producer.ICashbackQueueProducer;
import is.rza.wallet.repo.CardRepo;
import is.rza.wallet.repo.TransactionRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService {
    private final TransactionMapper mapper = new TransactionMapperImpl();
    private final TransactionRepo transactionRepo;
    private final ICashbackQueueProducer cashbackQueue;
    private final CashbackClient cashbackClient;
    private final CardRepo cardRepo;

    @Override
    @Transactional
    public TransactionDTO create(CreateTransactionDTO dto) {
        var card = cardRepo
                .findById(dto.getCardId())
                .orElseThrow(() -> new CardNotFound(dto.getCardId()));

        var transaction = mapper.createDTOToEntity(dto);
        var balance = card.getBalance();

        switch (transaction.getType()) {
            case CREDIT -> balance = balance.subtract(transaction.getAmount());
            case DEBIT -> balance = balance.add(transaction.getAmount());
        }

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new OverdraftTransaction(
                    card.getId(),
                    card.getBalance(),
                    transaction.getAmount()
            );
        }

        card.setBalance(balance);

        cardRepo.save(card);
        transactionRepo.save(transaction);


        if (transaction.requiresCashback()) {
            cashbackQueue.sendTransactionForCashback(transaction.getId());
        }

        return  mapper.entityToDTO(transaction);
    }

    @Override
    @Transactional
    public void processCashback(Long transactionId) {
        var transaction = transactionRepo
                .findById(transactionId)
                .orElseThrow(() -> new TransactionNotFound(transactionId));

        var card = cardRepo
                .findById(transaction.getCardId())
                .orElseThrow(() -> new CardNotFound(transactionId));

        var cashback = cashbackClient.calculateCashback(transaction.getAmount());
        var cbTr = new TransactionEntity();
        cbTr.setType(TransactionType.DEBIT);
        cbTr.setAmount(cashback.getCashbackAmount());
        cbTr.setCardId(card.getId());
        cbTr.setHasCashback(false);

        var balance = card.getBalance().add(cashback.getCashbackAmount());
        card.setBalance(balance);

        transactionRepo.save(cbTr);
        cardRepo.save(card);
    }
}
