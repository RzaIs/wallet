package is.rza.wallet.queue.producer;

public interface ICashbackQueueProducer {
    void sendTransactionForCashback(Long transactionId);
}
