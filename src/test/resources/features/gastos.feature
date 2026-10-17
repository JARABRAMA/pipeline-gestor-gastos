Feature: Creacion de gastos

  Background:
    * url baseUrl

  Scenario: Crear correctamente un gasto
    Given path 'gastos'
    And request { monto: 3000, categoria: "SALUD", fecha: "2026-10-16" }
    When method post
    Then status 201
    And match response.id == '#uuid'
