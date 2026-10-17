@reportes
Feature: Reportes de gastos

  Background:
    * url baseUrl

  Scenario: El total aumenta al crear un gasto
    Given path 'api', 'reportes', 'total'
    When method get
    Then status 200
    * def antes = parseFloat(response)
    * match antes == '#number'

    * call read('classpath:features/helpers/crear-gasto.feature') { monto: 100 }

    Given path 'api', 'reportes', 'total'
    When method get
    Then status 200
    * def despues = parseFloat(response)
    * assert Math.abs(despues - (antes + 100)) < 0.001

  Scenario: El total por categoría solo suma los gastos de esa categoría
    Given path 'api', 'reportes', 'total', categoriaA
    When method get
    Then status 200
    * def antesA = parseFloat(response)

    Given path 'api', 'reportes', 'total', categoriaB
    When method get
    Then status 200
    * def antesB = parseFloat(response)

    * call read('classpath:features/helpers/crear-gasto.feature') { monto: 50, categoria: '#(categoriaA)' }

    Given path 'api', 'reportes', 'total', categoriaA
    When method get
    Then status 200
    * assert Math.abs(parseFloat(response) - (antesA + 50)) < 0.001

    Given path 'api', 'reportes', 'total', categoriaB
    When method get
    Then status 200
    * assert Math.abs(parseFloat(response) - antesB) < 0.001

  Scenario: Rechazar un total por categoría inexistente
    Given path 'api', 'reportes', 'total', 'NO_EXISTE'
    When method get
    Then status 400

  Scenario: Calcular el promedio de los gastos
    * call read('classpath:features/helpers/crear-gasto.feature') { monto: 100 }
    Given path 'api', 'reportes', 'promedio'
    When method get
    Then status 200
    * def promedio = parseFloat(response)
    * match promedio == '#number'
    * assert promedio > 0