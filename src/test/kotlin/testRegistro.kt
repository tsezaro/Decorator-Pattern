import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TestCursoConRegistroDeEgresados : DescribeSpec({
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

    var Edgardo = Estudiante(
        nombre = "edu",
        apellido = "malardo",
        mail = "edu@gmail.com"
    )

    var cursoCpp = CursoPosta(
        nombre = "c++",
        aptitudesContenidas = mutableListOf(Aptitud.RE)
    )

    /*
        -queremos llevar un registro global de cantidad de recibidos por cursos:
         para ello cada vez que sedicta un curso (con esta característica)se
         contabiliza para el nombre del curso un nuevo egresado.
    */
    val cursoBase = CursoBuilder(cursoCpp)
        .contadorDeEgresados()
        .build()

    describe("Curso con registro de egresados") {

        it("provbar registro") {
            RegistroGlobalAlumnosAprobados.obtenerCantidadEgresados(cursoBase.nombre) shouldBe 0
            cursoBase.dictarA(pepe)
            RegistroGlobalAlumnosAprobados.obtenerCantidadEgresados(cursoBase.nombre) shouldBe 1
            cursoBase.dictarA(pepe)
            RegistroGlobalAlumnosAprobados.obtenerCantidadEgresados(cursoBase.nombre) shouldBe 2

        }
    }

})