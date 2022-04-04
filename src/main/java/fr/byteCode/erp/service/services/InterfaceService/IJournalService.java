package fr.byteCode.erp.service.services.InterfaceService;


import java.time.LocalDateTime;
import java.util.List;


import fr.byteCode.erp.persistance.dto.JournalDto;
import fr.byteCode.erp.persistance.entities.Journal;
import fr.byteCode.erp.service.services.InterfaceService.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IJournalService extends IGenericService<Journal, Long> {

    JournalDto findById(Long id);

    JournalDto save(JournalDto journalDto);

    JournalDto update(JournalDto journalDto);

    boolean isDeleteJournal(Long id);

    Journal findJournalByLabel(String label);

    Journal findByCode(String code);

    boolean existsById(Long journalANewId);

    List<Journal> findByIds(List<Long> journalIds);

    //Page<JournalDto> filterJournal(List<Filter> filters, Pageable pageable);

    byte[] exportJournalsExcelModel();

    //FileUploadDto loadJournalsExcelData(FileUploadDto fileUploadDto);

    byte[] exportJournalsAsExcelFile();

    //CentralizingJournalDto findCentralizingJournalPage(int page, int size, LocalDateTime startDate,
    //                                                   LocalDateTime endDate, List<Long> journalIds, int breakingAccount, int breakingCustomerAccount,
      //                                                 int breakingProviderAccount);

    //List<CentralizingJournalDetailsDto> findCentralizingJournalDetails(Long journalId, LocalDateTime startDate,
  //                                                                     LocalDateTime endDate, int breakingAccount, int breakingCustomerAccount, int breakingProviderAccount);

    //List<CentralizingJournalReportLineDto> generateCentralizingJournalTelerikReportLines(List<Long> journalIds,
     //                                                                                    LocalDateTime startDate, LocalDateTime endDate, int breakingAccount, int breakingCustomerAccount,
       //                                                                                  int breakingProviderAccount);

    //List<CentralizingJournalByMonthReportLineDto> generateCentralizingJournalByMonthReportLines(List<Long> journalIds,
         //                                                                                       LocalDateTime startDate, LocalDateTime endDate, int breakingAccount, int breakingCustomerAccount,
      //                                                                                          int breakingSupplierAccount);
//
}
