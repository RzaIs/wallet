package is.rza.wallet.service.card;

import is.rza.wallet.exception.card.CardNotFound;
import is.rza.wallet.model.dto.CardDTO;
import is.rza.wallet.model.dto.CreateCardDTO;
import java.util.List;

public interface ICardService {
    List<CardDTO> getAll();
    CardDTO getById(Long id) throws CardNotFound;
    CardDTO getByPan(String pan) throws CardNotFound;
    CardDTO create(CreateCardDTO dto);
    CardDTO deleteById(Long id) throws CardNotFound;
}
