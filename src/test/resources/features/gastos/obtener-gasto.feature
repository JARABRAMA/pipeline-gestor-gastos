@gastos @obtener
Feature: Obtener un gasto por id

  Background:
    * url baseUrl

  Scenario: Obtener un gasto existente
    * def creado = call read('classpath:features/helpers/crear-gasto.feature') { monto: 99, descripcion: 'Cine' }
    * def id = creado.response.id
    Given path 'api', 'gastos', id
    When method get
    Then status 200
    And match response ==
      """
      { id: '#(id)', monto: 99, categoria: '#(categoriaA)', fecha: '#string', descripcion: 'Cine' }
      """

  Scenario: Gasto inexistente devuelve 404
    Given path 'api', 'gastos', 'id-que-no-existe'
    When method get
    Then status 404
    And match response == { mensaje: 'Gasto no encontrado con id: id-que-no-existe' }