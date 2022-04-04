package fr.byteCode.erp.controllers.restController;

import fr.byteCode.erp.persistance.dto.JournalDto;
import fr.byteCode.erp.persistance.entities.Journal;
import fr.byteCode.erp.service.services.InterfaceService.IJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/journal")
public class JournalController {
    private final IJournalService journalService;

    @Autowired
    public JournalController(IJournalService journalService) {
        this.journalService = journalService;
    }
    @GetMapping(value = "/journals")
    public List<Journal> findAll() {
        return journalService.findAll();
    }

    @GetMapping(value = "/{id}")
    public JournalDto getJournal(@PathVariable Long id) {
        return journalService.findById(id);
    }
    @PostMapping()
    public JournalDto save(@RequestBody @Valid JournalDto journalDto) {
        return journalService.save(journalDto);
    }

}
