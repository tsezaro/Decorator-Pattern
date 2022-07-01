import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

class TestCursoPosta : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    var pepe = Estudiante(
        nombre = "pepe",
        apellido = "parada",
        mail = "pepepa@gmail.com",
        aptitudesDeseadas = mutableListOf(Aptitud.RE)
    )

    var juancho = Estudiante(
        nombre = "juancho",
        apellido = "talarga",
        mail = "juancho@gmail.com"
    )

    var cursoPosta=CursoBuilder(CursoPosta("assemblerx82",mutableListOf(Aptitud.KOTLIN))).build()

    describe("validarCursadaDe(estudiante: Estudiante)") {
        //validarCursadaDe(estudiante: Estudiante): debe validar si el
        // estudiante puede y/o le interesa realizar el curso.
        it("Pruebo la validacion de la cursada de Pepe") {
            cursoPosta.validarCursadaDe(pepe) shouldBe false
            pepe.agregarAptitudDeseada(Aptitud.KOTLIN)
            cursoPosta.validarCursadaDe(pepe) shouldBe true
        }

    }

    describe("dictarA(estudiante: Estudiante)") {
        //dictarA(estudiante: Estudiante): permite que el estudiante realice el curso
        //si pasa las validaciones. Para que un curso pueda dictarse a un estudiante, es
        //necesario que a este último le sea interesante.
        it("Curso2 dicta a Juancho, comprobamos que antes no estaba en la lista de cursos realizados y luego se agrega") {
            juancho.aptitudesDeseadas = mutableListOf()
            juancho.aptitudesObtenidas = mutableListOf()
            assertThrows<RuntimeException> { cursoPosta.dictarA(juancho) }
            juancho.aptitudesObtenidas.contains(Aptitud.KOTLIN) shouldBe false
            juancho.agregarAptitudDeseada(Aptitud.KOTLIN)
            cursoPosta.dictarA(juancho)
            juancho.aptitudesObtenidas.contains(Aptitud.KOTLIN) shouldBe true
        }

    }

    describe("agregarAptitudes(aptitudes: List<String>)") {
        //agregarAptitudes(aptitudes: List<String>): permite agregar varias
        //aptitudes para que el curso las otorgue.

        it("Agregamos muchas aptitudes a un curso y probamos que el curso ahora las contiene") {
            cursoPosta.aptitudesContenidas.remove(Aptitud.RE)
            cursoPosta.aptitudesContenidas.remove(Aptitud.KOTLIN)
            cursoPosta.aptitudesContenidas.remove(Aptitud.PASCAL)
            cursoPosta.aptitudesContenidas.contains(Aptitud.RE) shouldBe false
            cursoPosta.aptitudesContenidas.contains(Aptitud.KOTLIN) shouldBe false
            cursoPosta.aptitudesContenidas.contains(Aptitud.PASCAL) shouldBe false
            cursoPosta.aptitudesContenidas.add(Aptitud.RE)
            cursoPosta.aptitudesContenidas.add(Aptitud.KOTLIN)
            cursoPosta.aptitudesContenidas.add(Aptitud.PASCAL)
            cursoPosta.aptitudesContenidas.contains(Aptitud.RE) shouldBe true
            cursoPosta.aptitudesContenidas.contains(Aptitud.KOTLIN) shouldBe true
            cursoPosta.aptitudesContenidas.contains(Aptitud.PASCAL) shouldBe true
        }

    }

    describe("tieneAptitudes() : Boolean") {
        //tieneAptitudes() : Boolean : determina si el curso tiene aptitudes a otorgar.
        it("Comprobamos que el Curso primero no tiene aptitudes para otorgar y luego si") {
            cursoPosta.aptitudesContenidas = mutableListOf()
            cursoPosta.tieneAptitudes() shouldBe false
            cursoPosta.aptitudesContenidas.add(Aptitud.RE)
            cursoPosta.tieneAptitudes() shouldBe true
        }
    }

})