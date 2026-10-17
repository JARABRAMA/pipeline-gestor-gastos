@reportes
Feature: Reportes de gastos

  Background:
    * url baseUrl

  Scenario: El total general aumenta al registrar un gasto
    Given path 'api', 'reportes', 'total'
    When method get
    Then status 200
    And match response == '#number'
    * def antes = response

    * call read('classpath:features/helpers/crear-gasto.feature') { monto: 100 }

    Given path 'api', 'reportes', 'total'
    When method get
    Then status 200
    * def despues = response
    * assert despues == antes + 100

  Scenario: El total por categoría solo suma los gastos de esa categoría
    Given path 'api', 'reportes', 'total', categoriaA
    When method get
    Then status 200
    * def antesA = response

    Given path 'api', 'reportes', 'total', categoriaB
    When method get
    Then status 200
    * def antesB = response

    * call read('classpath:features/helpers/crear-gasto.feature') { monto: 50, categoria: '#(categoriaA)' }

    Given path 'api', 'reportes', 'total', categoriaA
    When method get
    Then status 200
    * assert response == antesA + 50

    Given path 'api', 'reportes', 'total', categoriaB
    When method get
    Then status 200
    * assert response == antesB

  Scenario: Rechazar un total por categoría inexistente
    Given path 'api', 'reportes', 'total', 'NO_EXISTE'
    When method get
    Then status 400

  Scenario: Calcular el promedio de los gastos
    * call read('classpath:features/helpers/crear-gasto.feature') { monto: 100 }
    Given path 'api', 'reportes', 'promedio'
    When method get
    Then status 200
    And match response == '#number'
    * assert response > 0