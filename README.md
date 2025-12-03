# CLI Agenda Java

Aplicación de **agenda por consola desarrollada en Java 21** para la gestión de **tareas, eventos y notas**, con **persistencia en base de datos SQL**, arquitectura en capas, organización por features, ejecución mediante **Docker** y **testing con JUnit 5**.

Proyecto desarrollado como trabajo final en equipo aplicando buenas prácticas de diseño, modularidad y separación de responsabilidades.

---

## Funcionalidades

### Gestión de Tareas

* Crear, actualizar y eliminar tareas
* Listar todas las tareas
* Listar tareas pendientes y completadas
* Marcar tareas como completadas
* Persistencia en base de datos SQL

### Gestión de Eventos

* Crear, listar, actualizar y eliminar eventos
* Listar próximos eventos
* Persistencia en base de datos SQL

### Gestión de Notas

* Crear, listar, actualizar y eliminar notas
* Persistencia en base de datos SQL

### Otras características

* Arquitectura en capas
* Organización por features (task, event, note)
* Patrón DAO
* Uso de DTOs y Mappers
* Manejo de excepciones personalizado
* Aplicación CLI con menús interactivos
* Base de datos en contenedor Docker
* Scripts automáticos de creación y carga de datos
* Proyecto gestionado con Maven

---

## Tecnologías utilizadas

| Tecnología             | Uso                   |
| ---------------------- | --------------------- |
| Java 21                | Lógica de negocio     |
| Maven                  | Gestión del proyecto  |
| SQL                    | Persistencia de datos |
| PostgreSQL (en Docker) | Base de datos         |
| Docker                 | Contenedorización     |
| JUnit 5                | Testing               |

---

## Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas**, organizada además por **features**:

* **application** → punto de entrada (`MainApp`)
* **model** → entidades del dominio
* **dto** → objetos de transferencia de datos
* **mapper** → conversión entre DTO y modelo
* **repository / dao** → acceso a datos
* **service** → lógica de negocio
* **menu** → menús de consola
* **common** → utilidades, validaciones, excepciones, factorías
* **infrastructure** → implementación de persistencias (SQL, memoria, MongoDB preparadas)

Además:

* Patrón **DAO + Factory**
* Interfaz común **CrudAgenda**
* Excepciones personalizadas por capa

## Patrones de diseño

En el desarrollo del proyecto se ha tenido la **intención de aplicar los siguientes patrones de diseño**:

- **Patrón Factory**: para centralizar la creación de los repositorios y desacoplar la lógica de negocio del tipo de persistencia utilizada.
- **Patrón Observer**: aplicado en la gestión de eventos para notificar cambios de estado dentro del sistema.



---

## Estructura del Proyecto

```
cli-agenda-java
├── mysql-init
│   ├── 00-create-database.sql
│   ├── 01-create-task-table.sql
│   ├── 02-sample-task.sql
│   ├── 03-create-note-table.sql
│   ├── 04-sample-note.sql
│   ├── 05-create-event-table.sql
│   └── 06-sample-event.sql
│
├── src/main/java
│   ├── application
│   │   └── MainApp
│
│   ├── common
│   │   ├── dao
│   │   ├── exception
│   │   ├── factory
│   │   ├── repository
│   │   └── utils
│
│   ├── event
│   │   ├── dto
│   │   ├── mapper
│   │   ├── model
│   │   ├── repository
│   │   └── service
│
│   ├── note
│   │   ├── dto
│   │   ├── mapper
│   │   ├── model
│   │   ├── repository
│   │   └── service
│
│   ├── task
│   │   ├── dto
│   │   ├── enums
│   │   ├── mapper
│   │   ├── model
│   │   ├── repository
│   │   └── service
│
│   ├── infrastructure
│   │   ├── memory
│   │   ├── mongodb
│   │   └── sql
│
│   └── menu
│       ├── MainMenu
│       ├── TaskMenu
│       ├── EventMenu
│       └── NoteMenu
│
└── src/main/resources
    └── db.properties
```

---

## Persistencia

* Base de datos **SQL ejecutándose en Docker**
* Inicialización automática mediante scripts:

    * Creación de base de datos
    * Creación de tablas
    * Inserción de datos de ejemplo
* Configuración de conexión en `db.properties`

---

## Requisitos Previos

* Java 21
* Maven
* Docker
* Acceso al contenedor de base de datos

---

## Instalación y Ejecución

### Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd cli-agenda-java
```

---

### Construir el proyecto

```bash
mvn clean package
```

---

### Ejecutar la aplicación

```bash
java -jar target/cli-agenda-java.jar
```

---

### Ejecutar con Docker

```bash
docker build -t cli-agenda-java .
docker run cli-agenda-java
```

---

## Testing

El proyecto incluye **tests unitarios con JUnit 5**.
Para ejecutarlos:

```bash
mvn test
```

---

## Autores

Proyecto desarrollado por:

* Pau Gaston Boza
* Andres Rouge
* Jordi Casas González

---

## Wiki 📚

El proyecto dispone de una **Wiki completa** donde se documentan:

* Guía de instalación avanzada
* Arquitectura del sistema
* Diagramas de diseño
* Manual de uso de la aplicación
* Guía de contribución

---

## Licencia

Este proyecto **no dispone actualmente de licencia definida**.
