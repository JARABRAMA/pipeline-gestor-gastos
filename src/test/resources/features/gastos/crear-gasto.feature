@gastos @crear
Feature: Crear gastos

  Background:
    * url baseUrl
    * def hoy = java.time.LocalDate.now().toString()

  Scenario: Crear un gasto con datos válidos
    Given path 'api', 'gastos'
    And request { monto: 150.50, categoria: '#(categoriaA)', fecha: '2026-10-10', descripcion: 'Almuerzo' }
    When method post
    Then status 201
    And match response ==
      """
      {
        id: '#string',
        monto: 150.5,
        categoria: '#(categoriaA)',
        fecha: '2026-10-10',
        descripcion: 'Almuerzo'
      }
      """
    And match header Location contains '/api/gastos/' + response.id

  Scenario: Crear un gasto sin descripción (es opcional)
    Given path 'api', 'gastos'
    And request { monto: 20, categoria: '#(categoriaA)', fecha: '#(hoy)' }
    When method post
    Then status 201
    And match response.id == '#string'

  Scenario: El gasto creado se puede consultar con la URL del header Location
    Given path 'api', 'gastos'
    And request { monto: 75, categoria: '#(categoriaB)', fecha: '#(hoy)', descripcion: 'Taxi' }
    When method post
    Then status 201
    * def id = response.id
    Given path 'api', 'gastos', id
    When method get
    Then status 200
    And match response.id == id
    And match response.descripcion == 'Taxi'

  Scenario Outline: Rechazar un monto no positivo
    Given path 'api', 'gastos'
    And request { monto: <monto>, categoria: '#(categoriaA)', fecha: '#(hoy)' }
    When method post
    Then status 400
    And match response == { mensaje: 'monto: El monto debe ser positivo' }

    Examples:
      | monto |
      | 0     |
      | -10   |

  Scenario: Rechazar un gasto sin monto
    Given path 'api', 'gastos'
    And request { categoria: '#(categoriaA)', fecha: '#(hoy)' }
    When method post
    Then status 400
    And match response == { mensaje: 'monto: El monto no puede ser nulo' }

  Scenario: Rechazar un gasto sin categoría
    Given path 'api', 'gastos'
    And request { monto: 50, fecha: '#(hoy)' }
    When method post
    Then status 400
    And match response == { mensaje: 'categoria: La categoria no puede ser nula' }

  Scenario: Rechazar un gasto sin fecha
    Given path 'api', 'gastos'
    And request { monto: 50, categoria: '#(categoriaA)' }
    When method post
    Then status 400
    And match response == { mensaje: 'fecha: La fecha no puede ser nula' }

  Scenario: Rechazar una descripción mayor a 255 caracteres
    * def larga = new Array(257).join('a')
    Given path 'api', 'gastos'
    And request { monto: 50, categoria: '#(categoriaA)', fecha: '#(hoy)', descripcion: '#(larga)' }
    When method post
    Then status 400
    And match response == { mensaje: 'descripcion: La descripcion no puede exceder 255 caracteres' }

  Scenario: Rechazar una categoría inexistente
    Given path 'api', 'gastos'
    And request { monto: 50, categoria: 'NO_EXISTE', fecha: '#(hoy)' }
    When method post
    Then status 400