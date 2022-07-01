![workflow](https://github.com/algo2-unsam/tp-cursar-2022-grupo-9/actions/workflows/build.yml/badge.svg)
[![codecov](https://codecov.io/gh/algo2-unsam/tp-cursar-2022-grupo-9/branch/main/graph/badge.svg?token=TODO)](https://codecov.io/gh/algo2-unsam/tp-cursar-2022-grupo-9)

# Cursar

## Entrega Decorator / Mixin

Estamos desarrollando las inscripciones a cursos de distintas actividades o asignaturas para la plataforma CursAr.
Tenemos las siguientes entidades:
Estudiante
Curso

## Estudiante
Sabemos que cuentan con:

* Nombre: Su nombre real.
* Apellido: Su apellido real.
* Mail: Su dirección de email.
* Aptitudes Deseadas: es un listado de aptitudes que desea o le interesa adquirir.
Ej: “Programar Kotlin”, “Bailar Tango”, “Hablar Francés”, etc.
Cursos Realizados: listado de Cursos que se realizaron.



## También que …

Un curso le es interesante si otorga alguna de las aptitudes deseadas.
Tiene una aptitud específica, si alguno de los cursos realizados, otorgan dicha aptitud.

## Curso
Sabemos que todos los cursos, tienen un nombre y entienden los siguientes mensajes:
* validarCursadaDe(estudiante: Estudiante): debe validar si el estudiante puede y/o le interesa realizar el curso.
* dictarA(estudiante: Estudiante): permite que el estudiante realice el curso si pasa las validaciones.
* agregarAptitudes(aptitudes: List<String>): permite agregar varias aptitudes para que el curso las otorgue.
* otorgaAptitud(aptitud: String): Boolean  : determina si una aptitud específica es otorgada por el curso.
* tieneAptitudes() : Boolean : determina si el curso tiene aptitudes a otorgar.

Para que un curso pueda dictarse a un estudiante, es necesario que a este último le sea interesante.
A este comportamiento habitual, nos piden poder incorporar estos otros:
Hay cursos que … 
* son limitados en cupos, es decir que solo puede dictarse si no llegó al cupo máximo establecido.
* son correlativos, no pueden dictarse a estudiantes que no tengan aptitudes requeridas por el curso.
* son con certificación digital, una vez que se dicta a un estudiante, este recibe un mail certificando su cursada. En el cuerpo del mail se debe detallar nombre y apellido del estudiante, y nombre del curso que realizó.
queremos llevar un registro global de cantidad de recibidos por cursos, para ello cada vez que se dicta un curso (con esta característica) se contabiliza para el nombre del curso un nuevo egresado.


Tener en cuenta que los cursos pueden combinar estas características (ser limitados y correlativos, y/o ser con certificación y registrables, etc … todas las combinaciones deberían ser posibles)


## Se pide:
* Implemente la solución utilizando alguna de estas opciones
* decoradores
* decoradores con delegated class de Kotlin
* mixines, en Wollok

* Diseñar e implementar el modelo de objetos de dominio.
* Diseñar e implementar los casos de prueba correspondientes.
* Armar el juego de datos necesario para realizar las pruebas.
* Codificar los tests unitarios.
* Usar mocks y/o stubs para testear de ser necesario.

* Implementar Builders Para Crear los cursos.
* La entrega debe desarrollarse en un branch develop, que será mergeado en master, a través de un pull request, luego de ser aprobado por el tutor del grupo.
* Agregar CI y cobertura de test implementando Github Actions.