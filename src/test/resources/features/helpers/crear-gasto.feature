@ignore
Feature: Helper para crear un gasto

  Scenario: Crear gasto
    * def monto = karate.get('monto', 100)
    * def categoria = karate.get('categoria', categoriaA)
    * def fecha = karate.get('fecha', java.time.LocalDate.now().toString())
    * def descripcion = karate.get('descripcion', 'Gasto de prueba')

    Given url baseUrl
    And path 'api', 'gastos'
    And request { monto: '#(monto)', categoria: '#(categoria)', fecha: '#(fecha)', descripcion: '#(descripcion)' }
    When method post
    Then status 201
