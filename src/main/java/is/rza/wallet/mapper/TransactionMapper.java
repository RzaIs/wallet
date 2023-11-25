package is.rza.wallet.mapper;

import is.rza.wallet.model.dto.CreateTransactionDTO;
import is.rza.wallet.model.dto.TransactionDTO;
import is.rza.wallet.model.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {
    TransactionDTO entityToDTO(TransactionEntity entity);
    TransactionEntity createDTOToEntity(CreateTransactionDTO dto);
}
