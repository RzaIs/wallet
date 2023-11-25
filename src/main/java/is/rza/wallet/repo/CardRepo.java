package is.rza.wallet.repo;

import is.rza.wallet.model.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CardRepo extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByPan(String pan);
}
