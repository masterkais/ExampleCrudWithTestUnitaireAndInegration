package com.example.demo;


import fr.byteCode.erp.persistance.dto.JournalDto;
import fr.byteCode.erp.persistance.entities.Journal;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class JournalIT extends ITConfig {
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final String JOURNAL_URL = "/api/journal/";

    HttpHeaders httpHeaders;

    @BeforeAll
    void setUp() {
        httpHeaders = getHttpHeaders();
    }

@Order(1)
    @Test
    void saveIT() {
        JournalDto journalDto = new JournalDto(2L, "BNQ", "Banque",LocalDateTime.now(), BigDecimal.ZERO, BigDecimal.ZERO);
        HttpEntity entity = new HttpEntity(journalDto, httpHeaders);

        ResponseEntity<JournalDto> journalResponse = getTestRestTemplate().exchange(getRootUrl() + JOURNAL_URL,
                HttpMethod.POST, entity, JournalDto.class);

        assertNotNull(journalResponse);
        assertEquals(HttpStatus.OK, journalResponse.getStatusCode());
    }

@Order(2)
    @Test
    void getJournalIT() {

        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<JournalDto> journalResponse = getTestRestTemplate().exchange(getRootUrl() + JOURNAL_URL + ONE,
                HttpMethod.GET, entity, JournalDto.class);

        assertNotNull(journalResponse);
        assertEquals(HttpStatus.OK, journalResponse.getStatusCode());
    }
@Order(3)
    @Test
    void getJournalAllIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<Journal>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(JOURNAL_URL + "journals"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Journal>>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }


}
