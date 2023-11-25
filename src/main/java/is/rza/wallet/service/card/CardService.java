package is.rza.wallet.service.card;

import is.rza.wallet.exception.card.CardNotFound;
import is.rza.wallet.mapper.CardMapper;
import is.rza.wallet.mapper.CardMapperImpl;
import is.rza.wallet.model.dto.CardDTO;
import is.rza.wallet.model.dto.CreateCardDTO;
import is.rza.wallet.repo.CardRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardService implements ICardService {
    private final CardMapper mapper = new CardMapperImpl();
    private final CardRepo cardRepo;

    @Override
    public List<CardDTO> getAll() {
        return cardRepo
                .findAll()
                .stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CardDTO getById(Long id) throws CardNotFound {
        return cardRepo
                .findById(id)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new CardNotFound(id));
    }

    @Override
    public CardDTO getByPan(String pan) {
        return cardRepo
                .findByPan(pan)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new CardNotFound(pan));
    }

    @Override
    public CardDTO create(CreateCardDTO dto) {
        return mapper.entityToDTO(
                cardRepo.save(
                        mapper.createDTOToEntity(dto)
                )
        );
    }

    @Override
    public CardDTO deleteById(Long id) throws CardNotFound {
        var card = cardRepo
                .findById(id)
                .map(mapper::entityToDTO)
                .orElseThrow(() -> new CardNotFound(id));

        cardRepo.deleteById(id);

        return card;
    }
}
