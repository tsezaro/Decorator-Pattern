import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

class TestCursoConLimiteDeCupo : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    /*
    -son limitados en cupos:
    es decir que solo puede dictarse si no llegó al cupo máximo establecido.
    */
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

    var Edgardo = Estudiante(
        nombre = "edu",
        apellido = "malardo",
        mail = "edu@gmail.com"
    )

    var cursoCpp = CursoPosta(
        nombre = "c++",
        aptitudesContenidas = mutableListOf(Aptitud.RE)
    )

    val cursoBase = CursoBuilder(cursoCpp)
        .limitadoEnCupo(2)
        .build()

    pepe.agregarAptitudDeseada(Aptitud.RE) //Le agrego la Aptitud.RE a Pepe para que le interese el curso

    describe("Validar curso con limite de cupo") {

        it("Comprobar que el se dicta el curso sin superar el limite") {
            cursoBase.dictarA(pepe)
            pepe.aptitudesObtenidas.contains(Aptitud.RE) shouldBe true
        }


        it("Probamos que pasa si el numero de estudiantes llega al limite de cupo") {
            assertThrows<RuntimeException> { cursoBase.dictarA(Edgardo) }
        }

    }

})