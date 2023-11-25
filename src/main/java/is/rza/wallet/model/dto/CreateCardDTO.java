package is.rza.wallet.model.dto;

import lombok.Data;

@Data
public class CreateCardDTO {
    private String pan;
    private Long customerId;
}
