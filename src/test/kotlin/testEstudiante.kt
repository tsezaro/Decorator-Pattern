import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TestEstudiante : DescribeSpec({
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


    describe("vemos si le interesa el curso o no al estudiante") {
        it("le interesa el curso cuando hay aptitudes en comun") {
            pepe.aptitudesObtenidas.add(Aptitud.RE)
            pepe.esInteresante(cursoCpp) shouldBe true
        }

        it("no hay aptitudes en comun") {

            cursoCpp.otorgaAptitud(Aptitud.KOTLIN) shouldBe false
        }

    }

    describe("vemos si el curso tiene la aptitud") {
        it("tiene la aptitud") {
            cursoCpp.otorgaAptitud(Aptitud.RE) shouldBe true
        }

        it("no tiene la aptitud") {
            pepe.aptitudesObtenidas = mutableListOf()
            pepe.tieneAptitudEspecifica(Aptitud.KOTLIN) shouldBe false
        }

    }

})








