# CLI Agenda Java

Este proyecto consiste en una agenda desarrollada en Java que permite gestionar tareas, eventos y notas desde una aplicación ejecutable por consola. El objetivo es ofrecer una herramienta modular y extensible, con persistencia de datos en una base de datos SQL, ejecución mediante Docker y pruebas automatizadas con JUnit 5.

## Características principales

- Gestión de **tareas**
  - Crear, actualizar, eliminar
  - Listar todas, pendientes o completadas
  - Marcar como completadas
  - Persistencia en base de datos

- Gestión de **eventos**
  - Crear, listar, actualizar, eliminar
  - Listar próximos eventos
  - Persistencia en base de datos

- Gestión de **notas**
  - Crear, listar, actualizar, eliminar
  - Persistencia en base de datos

- Arquitectura modular basada en estructura por features
- Contenedorización con Docker
- Testing con JUnit 5

## Tecnologías utilizadas

| Tecnología | Uso |
|------------|-----|
| **Java 21** | Lógica de aplicación |
| **Maven** | Gestión del proyecto y dependencias |
| **SQL** | Persistencia de datos |
| **JUnit 5** | Testing |
| **Docker** | Ejecución y despliegue en contenedores |

## Requisitos previos

Antes de ejecutar el proyecto es necesario tener instalado:

- Java 21 o superior
- Maven
- Docker (para ejecución en contenedor)
- Acceso a una base de datos SQL (o imagen Docker configurada)

```markdown
```markdown
## Instalación y ejecución

Clonar el repositorio:

```bash
git clone <URL_DEL_REPO>
cd cli-agenda-java
```

### Ejecutar en modo desarrollo

Compilar y arrancar:

```bash
mvn clean package
java -jar target/cli-agenda-java.jar
```

### Ejecutar con Docker (cuando esté configurado)

Construir imagen:

```bash
docker build -t cli-agenda-java .
```

Ejecutar contenedor:

```bash
docker run cli-agenda-java
```


```
```
## Estructura del Proyecto
```markdown
# Classes del proyecto

## Modelos

* Clase Evento - Patron Observer
* Clase Tarea
* Clase Nota
* Clase Agenda

## Métodos:

### **Evento:**

* Actualizar (evento) - Común a todos
* persistir (evento) - Común a todos
* crear (evento) - Común a todos
* eliminar (evento) - Común a todos
* listas proximos (evento) - Común a todos

### **Tarea:**

* actualizar una tarea - Común a todos
* listar todas las tareas - Común a todos
* listar tareas completadas - Común a todos
* listas tareas pendientes - Común a todos
* marcar una tarea como completada. Propio de Clase
* eliminar una tarea - Común a todos
* persistir tareas - Común a todos
* crear una nueva tarea - Común a todos

### **Nota:**

* listas notas - Común a todos
* persistir notas - Común a todos
* eliminar una nota - Común a todos
* actualizar una nota - Común a todos
* crea una nota - Común a todos

## Interfaz/Clase Abstracta: CrudAgenda (Crear, Eliminar, Leer, Actualizar, Persistir).

## Controladores:

* GestorEvento
* GestorTarea
* GestorNota
* GestorBaseDatos

## Vista:

* Menus y sub Menus.


```
## Autores

Desarrolladores del equipo:

- Pau Gaston Boza
- Andres Rouge
- Jordi Casas González

## Próximas mejoras

- Implementación del contenedor Docker con base de datos incluida
- Comandos CLI interactivos
- Posible interfaz gráfica o API (pendiente de decidir)
- Documentación extendida en Wiki


## Wiki 📚

El proyecto puede incluir una Wiki para documentar aspectos más detallados como:

- Guía de instalación avanzada
- Documentación de endpoints o módulos internos
- Diagramas de arquitectura
- Manual para contribución

La creación de la Wiki está pendiente de decisión del equipo y podrá activarse más adelante desde GitHub.


## Licencia

Pendiente de definir.
