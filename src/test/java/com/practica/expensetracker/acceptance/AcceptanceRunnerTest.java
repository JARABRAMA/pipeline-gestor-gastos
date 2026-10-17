package com.practica.expensetracker.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceRunnerTest {

    @LocalServerPort
    int port;

    @Test
    void runAcceptanceTests() {
        System.setProperty("server.port", String.valueOf(port));
        Results results = Runner.path("classpath:features")
                .tags("~@ignore")
                .parallel(1);
        assertTrue(results.getScenariosTotal() > 0, "No se encontraron escenarios");
        assertEquals(0, results.getFailCount(), results.getErrorMessages());

    }
}