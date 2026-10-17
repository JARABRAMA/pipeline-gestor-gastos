@gastos @eliminar
Feature: Eliminar gastos

  Background:
    * url baseUrl

  Scenario: Eliminar un gasto existente
    * def creado = call read('classpath:features/helpers/crear-gasto.feature')
    * def id = creado.response.id
    Given path 'api', 'gastos', id
    When method delete
    Then status 204

  Scenario: Un gasto eliminado ya no se puede consultar
    * def creado = call read('classpath:features/helpers/crear-gasto.feature')
    * def id = creado.response.id
    Given path 'api', 'gastos', id
    When method delete
    Then status 204
    Given path 'api', 'gastos', id
    When method get
    Then status 404

  Scenario: Eliminar un gasto inexistente devuelve 404
    Given path 'api', 'gastos', 'id-que-no-existe'
    When method delete
    Then status 404
    And match response == { mensaje: 'Gasto no encontrado con id: id-que-no-existe' }