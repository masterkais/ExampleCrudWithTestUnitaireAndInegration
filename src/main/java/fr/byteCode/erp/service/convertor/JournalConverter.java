package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.JournalDto;
import fr.byteCode.erp.persistance.entities.Journal;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public final class JournalConverter {
    private JournalConverter() {
        super();
    }

    public static Journal dtoToModel(JournalDto journalDto) {
        return new Journal(journalDto.getId(), journalDto.getCode(), journalDto.getLabel(),
                journalDto.getCreatedDate());
    }

    public static JournalDto modelToDto(Journal journal) {
        return new JournalDto(journal.getId(), journal.getCode(), journal.getLabel(), journal.getCreatedDate());
    }

    public static List<JournalDto> modelsToDtos(Collection<Journal> journals) {
        return journals.stream().filter(Objects::nonNull).map(JournalConverter::modelToDto)
                .collect(Collectors.toList());
    }

}
