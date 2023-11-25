package is.rza.wallet.model.dto;


import is.rza.wallet.model.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionDTO {
    private BigDecimal amount;
    private TransactionType type;
    private Boolean hasCashback;
    private Long cardId;
}
