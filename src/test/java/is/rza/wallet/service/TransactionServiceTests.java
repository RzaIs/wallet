package is.rza.wallet.service;

import is.rza.wallet.client.CashbackClient;
import is.rza.wallet.mapper.TransactionMapperImpl;
import is.rza.wallet.model.dto.CashbackDTO;
import is.rza.wallet.model.dto.CreateTransactionDTO;
import is.rza.wallet.model.entity.CardEntity;
import is.rza.wallet.model.entity.TransactionEntity;
import is.rza.wallet.model.entity.TransactionType;
import is.rza.wallet.queue.producer.ICashbackQueueProducer;
import is.rza.wallet.repo.CardRepo;
import is.rza.wallet.repo.TransactionRepo;
import is.rza.wallet.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTests {
    CardRepo cardRepo;
    ICashbackQueueProducer cashbackQueueProducer;
    CashbackClient cashbackClient;
    TransactionRepo transactionRepo;
    TransactionService transactionService;

    @BeforeEach
    void setup() {
        cardRepo = Mockito.mock();
        transactionRepo = Mockito.mock();
        cashbackQueueProducer = Mockito.mock();
        cashbackClient = Mockito.mock();
        transactionService = new TransactionService(
                transactionRepo,
                cashbackQueueProducer,
                cashbackClient,
                cardRepo
        );
    }

    @Test
    void testCreateTransaction() {
        var id = 5L;
        var createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAmount(BigDecimal.ONE);
        createTransactionDTO.setType(TransactionType.CREDIT);
        createTransactionDTO.setHasCashback(true);
        createTransactionDTO.setCardId(id);

        var createTransactionEntity = new TransactionMapperImpl()
                .createDTOToEntity(createTransactionDTO);

        var transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(BigDecimal.ONE);
        transactionEntity.setType(TransactionType.CREDIT);
        transactionEntity.setHasCashback(true);
        transactionEntity.setCardId(id);

        var transactionDTO = new TransactionMapperImpl()
                .entityToDTO(transactionEntity);

        CardEntity cardEntity = new CardEntity();
        cardEntity.setId(id);
        cardEntity.setBalance(BigDecimal.TEN);

        Mockito.when(
                cardRepo.findById(createTransactionDTO.getCardId())
        ).thenReturn(
                Optional.of(cardEntity)
        );

        Mockito.when(
                cardRepo.save(cardEntity)
        ).thenReturn(
                cardEntity
        );

        Mockito.when(
                transactionRepo.save(createTransactionEntity)
        ).thenReturn(
                transactionEntity
        );

        var resultTransaction = transactionService.create(createTransactionDTO);
        assertEquals(resultTransaction, transactionDTO);

        Mockito.verify(
                cardRepo
        ).findById(
                id
        );

        Mockito.verify(
                cardRepo
        ).save(
                cardEntity
        );

        Mockito.verify(
                transactionRepo
        ).save(
                transactionEntity
        );

        Mockito.verify(
                cashbackQueueProducer
        ).sendTransactionForCashback(
                transactionEntity.getId()
        );
    }

    @Test
    void testProcessCashback() {
        var id = 5L;
        var amount = BigDecimal.ONE;

        var transaction = new TransactionEntity();
        transaction.setId(id);
        transaction.setCardId(id);
        transaction.setAmount(amount);

        var cbTr = new TransactionEntity();
        cbTr.setCardId(id);
        cbTr.setAmount(amount);
        cbTr.setHasCashback(false);
        cbTr.setType(TransactionType.DEBIT);

        var card = new CardEntity();
        card.setId(id);
        card.setBalance(amount);

        var cashback = new CashbackDTO();
        cashback.setCashbackAmount(amount);

        Mockito.when(
                transactionRepo.findById(id)
        ).thenReturn(
                Optional.of(transaction)
        );

        Mockito.when(
                cardRepo.findById(id)
        ).thenReturn(
                Optional.of(card)
        );

        Mockito.when(
                cashbackClient.calculateCashback(amount)
        ).thenReturn(
                cashback
        );

        transactionService.processCashback(id);

        Mockito.verify(
                transactionRepo
        ).save(
                cbTr
        );

        Mockito.verify(
                cardRepo
        ).save(
                card
        );
    }
}
