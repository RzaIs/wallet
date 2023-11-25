package is.rza.wallet.service;

import is.rza.wallet.mapper.CardMapperImpl;
import is.rza.wallet.model.dto.CardDTO;
import is.rza.wallet.model.dto.CreateCardDTO;
import is.rza.wallet.model.entity.CardEntity;
import is.rza.wallet.repo.CardRepo;
import is.rza.wallet.service.card.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CardServiceTests {
    private CardRepo cardRepo;
    private CardService cardService;

    @BeforeEach
    void setup() {
        cardRepo = Mockito.mock();
        cardService = new CardService(cardRepo);
    }

    @Test
    void testGetAll() {
        CardEntity cardEntity = Mockito.mock();
        CardDTO cardDTO = new CardMapperImpl().entityToDTO(cardEntity);

        Mockito.when(
                cardRepo.findAll()
        ).thenReturn(
                List.of(cardEntity)
        );

        var resultCardDTOs = cardService.getAll();
        assertEquals(resultCardDTOs, List.of(cardDTO));

        Mockito.verify(
                cardRepo
        ).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        Long cardId = 5L;
        CardEntity cardEntity = Mockito.mock();
        CardDTO cardDTO = new CardMapperImpl().entityToDTO(cardEntity);

        Mockito.when(
                cardRepo.findById(cardId)
        ).thenReturn(
                Optional.of(cardEntity)
        );

        var resultCardDTO = cardService.getById(cardId);
        assertEquals(resultCardDTO, cardDTO);

        Mockito.verify(
                cardRepo
        ).findById(
                cardId
        );
    }

    @Test
    void testFindByPan() {
        String cardPan = "mock-card-pan";
        CardEntity cardEntity = Mockito.mock();
        CardDTO cardDTO = new CardMapperImpl().entityToDTO(cardEntity);

        Mockito.when(
                cardRepo.findByPan(cardPan)
        ).thenReturn(
                Optional.of(cardEntity)
        );

        var resultCardDTO = cardService.getByPan(cardPan);
        assertEquals(resultCardDTO, cardDTO);

        Mockito.verify(
                cardRepo
        ).findByPan(
                cardPan
        );
    }

    @Test
    void testCreate() {
        CreateCardDTO createDTO = Mockito.mock();
        CardEntity creatEntity = new CardMapperImpl().createDTOToEntity(createDTO);

        CardEntity cardEntity = Mockito.mock();
        CardDTO cardDTO = new CardMapperImpl().entityToDTO(cardEntity);


        Mockito.when(
                cardRepo.save(creatEntity)
        ).thenReturn(
                cardEntity
        );

        var resultCardDTO = cardService.create(createDTO);
        assertEquals(resultCardDTO, cardDTO);

        Mockito.verify(
                cardRepo
        ).save(
                creatEntity
        );
    }

    @Test
    void deleteById() {
        Long cardId = 5L;
        CardEntity cardEntity = Mockito.mock();
        CardDTO cardDTO = new CardMapperImpl().entityToDTO(cardEntity);

        Mockito.when(
                cardRepo.findById(cardId)
        ).thenReturn(
                Optional.of(cardEntity)
        );

        var resultCardDTO = cardService.deleteById(cardId);
        assertEquals(resultCardDTO, cardDTO);

        Mockito.verify(
                cardRepo
        ).findById(
                cardId
        );

        Mockito.verify(
                cardRepo
        ).deleteById(
                cardId
        );
    }
}
