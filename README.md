# gestor-gastos-java-pruebas

Rastreador de gastos personales en Java, convertido a una API REST con Spring Boot. El proyecto mantiene el dominio, servicios y repositorio en memoria, aplicando principios SOLID (inyección de dependencias por constructor, separación de responsabilidades).

## Requisitos

- JDK 21. El proyecto usa un toolchain de Gradle para usarlo automáticamente si no está instalado localmente.
- No es necesario tener Gradle instalado: incluye el Gradle Wrapper (`gradlew` / `gradlew.bat`).

## Compilar y ejecutar pruebas

```bash
./gradlew build
```

En Windows (PowerShell / cmd):

```bash
gradlew.bat build
```

## Ejecutar la aplicación

```bash
./gradlew bootRun
```

La API estará disponible en `http://localhost:8080`.

## Endpoints

- `GET /api/gastos` - Listar todos los gastos (opcional: `?categoria=COMIDA`)
- `GET /api/gastos/{id}` - Obtener un gasto por ID
- `POST /api/gastos` - Crear un gasto
- `DELETE /api/gastos/{id}` - Eliminar un gasto
- `GET /api/reportes/total` - Calcular total de gastos
- `GET /api/reportes/total/{categoria}` - Calcular total por categoría
- `GET /api/reportes/promedio` - Calcular promedio de gastos

## Estructura de carpetas

```
gestor-gastos-java-pruebas/
├── build.gradle
├── settings.gradle
├── gradlew, gradlew.bat
├── gradle/wrapper/
└── src/
    ├── main/java/com/practica/expensetracker/
    │   ├── model/          Categoria, Gasto
    │   ├── repository/     RepositorioGastos, RepositorioGastosEnMemoria
    │   ├── service/        RastreadorGastos, ServicioReporte
    │   ├── dto/            DTOs y respuestas de error
    │   ├── controller/     Controladores REST y manejo de excepciones
    │   ├── exception/      Excepciones de validación y no encontrado
    │   └── GestorGastosApplication.java
    └── test/java/com/practica/expensetracker/
        ├── controller/     Pruebas de API con MockMvc
        ├── model/          Pruebas del modelo
        ├── repository/     Pruebas del repositorio
        ├── service/        Pruebas de servicios
        └── support/        Utilidades para pruebas
```

## Dependencias principales

- Spring Boot 3.5.16 (Web, Validation, Test)
- JUnit Jupiter 6, Mockito, AssertJ
- JMH para benchmarks
- JaCoCo y SonarQube para análisis de calidad
