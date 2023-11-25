package is.rza.wallet.mapper;

import is.rza.wallet.model.dto.CardDTO;
import is.rza.wallet.model.dto.CreateCardDTO;
import is.rza.wallet.model.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CardMapper {
    CardDTO entityToDTO(CardEntity card);

    @Mapping(target = "balance", constant = "0")
    CardEntity createDTOToEntity(CreateCardDTO dto);
}