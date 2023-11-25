package is.rza.wallet.model.dto;

import is.rza.wallet.model.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private Boolean hasCashback;
    private LocalDateTime createdAt;
}
