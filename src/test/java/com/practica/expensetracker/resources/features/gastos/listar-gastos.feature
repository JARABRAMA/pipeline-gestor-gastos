@gastos @listar
Feature: Listar gastos

  Background:
    * url baseUrl

  Scenario: Listar todos los gastos
    * def creado = call read('classpath:features/helpers/crear-gasto.feature') { monto: 10 }
    * def id = creado.response.id
    Given path 'api', 'gastos'
    When method get
    Then status 200
    And match response == '#array'
    And match response contains deep { id: '#(id)', monto: 10, categoria: '#(categoriaA)', fecha: '#string', descripcion: 'Gasto de prueba' }

  Scenario: Filtrar los gastos por categoría
    * def a = call read('classpath:features/helpers/crear-gasto.feature') { categoria: '#(categoriaA)' }
    * def b = call read('classpath:features/helpers/crear-gasto.feature') { categoria: '#(categoriaB)' }
    Given path 'api', 'gastos'
    And param categoria = categoriaA
    When method get
    Then status 200
    And match each response contains { categoria: '#(categoriaA)' }
    And match response[*].id contains a.response.id
    And match response[*].id !contains b.response.id

  Scenario: Rechazar un filtro con categoría inexistente
    Given path 'api', 'gastos'
    And param categoria = 'NO_EXISTE'
    When method get
    Then status 400