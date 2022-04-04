package com.example.demo;



import fr.byteCode.erp.Demo1Application;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;

@Getter
@Setter
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Demo1Application.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ITConfig {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    public String getRootUrl() {
        return "http://localhost:" + port;
    }

    protected HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        //headers.add(", "");

        return headers;
    }

}
