import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

class TestCursoCorrelativo : DescribeSpec({
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

    var cursoCpp = CursoPosta(
        nombre = "c++",
        aptitudesContenidas = mutableListOf(Aptitud.RE)
    )

    val cursoBase = CursoBuilder(cursoCpp)
        .correlativo(mutableListOf(Aptitud.KOTLIN))
        .build()

    val esteban = Estudiante(
        nombre = "Esteban",
        apellido = "Quito",
        mail = "estebanquito@gmail.com",
        aptitudesDeseadas = mutableListOf(Aptitud.RE)
    )

    val cursoAlgo = CursoPosta(
        nombre = "Algoritmos2",
        aptitudesContenidas = mutableListOf(Aptitud.KOTLIN)
    )

    esteban.aptitudesObtenidas.add(Aptitud.KOTLIN)

    describe("validamos los alumnos que pueden cursar") {

        it("Esteban si puede cursar porque tiene los cursos correspondientes") {
            cursoBase.dictarA(esteban)
            esteban.aptitudesObtenidas.contains(Aptitud.KOTLIN) shouldBe true
        }

        it("Pepe no puede cursar oporque no tiene los cursos correspondientes") {
            assertThrows<RuntimeException> { cursoBase.dictarA(pepe) }
        }

    }

})

