package is.rza.wallet.queue.producer;

import is.rza.wallet.queue.QueueConfig;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CashbackQueueProducer implements ICashbackQueueProducer {
    private final RabbitTemplate template;

    @Override
    public void sendTransactionForCashback(Long transactionId) {
        template.convertAndSend(
                QueueConfig.topicExchangeName,
                QueueConfig.routingKey,
                transactionId
        );
    }
}
