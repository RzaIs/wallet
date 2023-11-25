package is.rza.wallet.queue.consumer;

import is.rza.wallet.queue.QueueConfig;
import is.rza.wallet.service.transaction.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CashbackQueueConsumer {
    private final ITransactionService transactionService;

    @RabbitListener(queues = QueueConfig.queueName)
    public void receiveTransactionId(Long transactionId) {
        transactionService.processCashback(transactionId);
    }
}
