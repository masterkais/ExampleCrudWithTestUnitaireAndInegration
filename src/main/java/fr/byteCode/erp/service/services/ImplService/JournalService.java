package fr.byteCode.erp.service.services.ImplService;




import fr.byteCode.erp.persistance.dao.JournalDao;
import fr.byteCode.erp.persistance.dto.JournalDto;
import fr.byteCode.erp.persistance.entities.Journal;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.JournalConverter;
import fr.byteCode.erp.service.services.InterfaceService.IJournalService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fr.byteCode.erp.persistance.constants.Constants.*;

@Service
@Slf4j
public class JournalService extends GenericService<Journal, Long> implements IJournalService {
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int TEN = 10;
    private static final String CHART_ACCOUNT_CODE_SUPPLIERS = "chartAccountCodeSuppliers";
    private static final String CHART_ACCOUNT_CODE_CUSTOMERS = "chartAccountCodeCustomers";
    private static final String CHART_ACCOUNT_CODE_OUT_OF_TIERS = "chartAccountCodeOutOfTiers";



    private final JournalDao journalDao;


    @Autowired
    public JournalService(JournalDao journalDao) {
        this.journalDao = journalDao;
  }


    @Override
    public JournalDto findById(Long id) {
        return JournalConverter.modelToDto(Optional.ofNullable(journalDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    public JournalDto save(JournalDto journalDto) {
        Objects.requireNonNull(journalDto);
        checkValidCode(journalDto.getCode());
        checkValidLabelLength(journalDto.getLabel());
        checkUniqueLabel(journalDto.getLabel(), journalDto.getId());
        Journal savedJournal = saveAndFlush(JournalConverter.dtoToModel(journalDto));
        log.info(LOG_ENTITY_CREATED, savedJournal);
        return JournalConverter.modelToDto(savedJournal);
    }

    @Override
    public JournalDto update(JournalDto journalDto) {
        return null;
    }

    @Override
    public boolean isDeleteJournal(Long id) {
        return false;
    }

    @Override
    public Journal findJournalByLabel(String label) {
        return null;
    }

    @Override
    public Journal findByCode(String code) {
        return null;
    }

    @Override
    public boolean existsById(Long journalANewId) {
        return false;
    }

    @Override
    public List<Journal> findByIds(List<Long> journalIds) {
        return null;
    }

    @Override
    public byte[] exportJournalsExcelModel() {
        return new byte[0];
    }

    @Override
    public byte[] exportJournalsAsExcelFile() {
        return new byte[0];
    }

    private void checkUniqueLabel(String label, Long id) {
        Journal journalWithTheSameLabel = journalDao.findByLabelAndIsDeletedFalseOrderByIdDesc(label);
        if (journalWithTheSameLabel != null && !journalWithTheSameLabel.getId().equals(id)) {
            log.error(JOURNAL_LABEL_EXIST, label);
            throw new HttpCustomException(ApiErrors.JOURNAL_LABEL_EXISTS, new ErrorsResponse().error(label));
        }
    }
    private void checkValidLabelLength(String label) {
        if (label.length() < THREE) {
            log.error(INVALID_LABEL_LEGNTH);
            throw new HttpCustomException(ApiErrors.LABEL_MIN_LENGTH, new ErrorsResponse().error(THREE));
        }
    }
     private void checkValidCode(String code) {
        if (journalDao.existsByCodeAndIsDeletedFalse(code)) {
            log.error(JOURNAL_CODE_EXIST, code);
            throw new HttpCustomException(ApiErrors.JOURNAL_CODE_EXISTS, new ErrorsResponse().error(code));
        }
        if (code.length() > TEN) {
            log.error(INVALID_CODE_MAX_LEGNTH_EXCEEDED);
            throw new HttpCustomException(ApiErrors.JOURNAL_CODE_LENGTH, new ErrorsResponse().error(code));
        }
    }

}
