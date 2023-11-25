package is.rza.wallet.repo;

import is.rza.wallet.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {
}
