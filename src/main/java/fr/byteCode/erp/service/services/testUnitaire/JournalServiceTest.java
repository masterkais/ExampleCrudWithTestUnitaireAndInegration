package fr.byteCode.erp.service.services.testUnitaire;


import fr.byteCode.erp.persistance.dao.BaseRepository;
import fr.byteCode.erp.persistance.dao.JournalDao;
import fr.byteCode.erp.persistance.dto.JournalDto;
import fr.byteCode.erp.persistance.entities.Journal;
import fr.byteCode.erp.service.convertor.JournalConverter;
import fr.byteCode.erp.service.services.ImplService.GenericService;
import fr.byteCode.erp.service.services.ImplService.JournalService;
import fr.byteCode.erp.service.services.InterfaceService.IGenericService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class JournalServiceTest {
    @InjectMocks
    private JournalService journalService;
    @Mock
    JournalDao journalDao;
    @Mock
    IGenericService genericService;
    @Mock
    GenericService genericServiceImp;
    JournalDto journalDto;
    Journal journal;
    Journal journalByCode;
    Journal journalByLable;
    Optional<Journal> journalOptional;
    @Mock
    BaseRepository<Journal, Long> baseRepository;

    List<Journal> journalList;

    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);
        journalByCode = new Journal();
        journal = new Journal();
        journal.setId(new Long(1));
        journal.setCode(new String("testCode"));
        journal.setLabel(new String("testLable"));
        journal.setCreatedDate(LocalDateTime.now());
        journalByLable = new Journal();
        journalDto = JournalConverter.modelToDto(journal);
        journalOptional = Optional.of(journal);
        journalList = new ArrayList<>();
        journalList.add(journal);
    }

    @AfterEach
    void tearDown() {
        journalByCode = null;
        journal = null;
        journalByLable = null;
        journalDto = null;
        journalDao = null;
        journalOptional = null;
    }

    @Test
    @DisplayName("test for save")
    public void testSaveJournal() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //GIVEN
        when(journalDao.existsByCodeAndIsDeletedFalse(anyString())).thenReturn(false);
        when(baseRepository.saveAndFlush(journal)).thenReturn(journal);
        //WHEN
        Method methodCheckValidCode = JournalService.class.getDeclaredMethod("checkValidCode", String.class);
        methodCheckValidCode.setAccessible(true);
        baseRepository.saveAndFlush(journal);
        //THEN
        assertDoesNotThrow(
                () -> methodCheckValidCode.invoke(journalService, journal.getCode())

        );
        verify(baseRepository, times(1)).saveAndFlush(journal);
        verify(journalDao,times(1)).existsByCodeAndIsDeletedFalse(journal.getCode());
    }


    @Test
    public void allJournal() {
        //GIVEN
        when(journalDao.findAll()).thenReturn(journalList);
        //WHEN
        List<Journal> results = journalDao.findAll();
        //THEN
        assertNotNull(results);
        verify(journalDao, times(1)).findAll();
    }
}