package is.rza.wallet.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "_transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "_type")
    private TransactionType type;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "has_cashback")
    private Boolean hasCashback;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Boolean requiresCashback() {
        return type == TransactionType.CREDIT && hasCashback;
    }
}
