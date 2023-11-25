package is.rza.wallet.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CardDTO {
    private Long id;
    private String pan;
    private Long customerId;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
