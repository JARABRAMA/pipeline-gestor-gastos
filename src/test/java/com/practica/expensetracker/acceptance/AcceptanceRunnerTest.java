package com.practica.expensetracker.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

class AcceptanceRunnerTest {

    @Test
    void runAcceptanceTests() {
        Results results = Runner.path("classpath:features")
                .tags("~@ignore")
                .parallel(1);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
}