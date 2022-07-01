import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

class TestCursoMixtoMaximunDecorator : DescribeSpec({
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

    var cursoCobol = CursoPosta(
        nombre = "COBOL",
        aptitudesContenidas = mutableListOf(Aptitud.KOTLIN)
    )

    var cursoAssembler = CursoPosta(
        nombre = "Assembler",
        aptitudesContenidas = mutableListOf()
    )

    // describe("queremos testear un curso con todos los decorados puestos") {
    val emailsEnviados : MutableList<Mail> = mutableListOf()
    val servicioCorreo = mockk<ServicioCorreo>(relaxUnitFun = true)
    every { servicioCorreo.enviarMail(mail = any()) } answers { emailsEnviados.add(firstArg()) }

    val cursoBaseFullDeco = CursoBuilder(cursoCpp)
        .limitadoEnCupo(10)
        .correlativo(mutableListOf(Aptitud.KOTLIN))
        .certificacionDigital(servicioCorreo)
        .contadorDeEgresados()
        .build()

    var cursoParaPrueba = CursoPosta(
        nombre = "asemblerx86",
        aptitudesContenidas = mutableListOf(Aptitud.KOTLIN)
    )
    pepe.aptitudesObtenidas.add(Aptitud.KOTLIN)



    describe("COSAS DEL CURSO POSTA") {
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

    }

    describe("COSAS DEL CURSO CON LIMITE DE CUPO ") {

        val cursoBase2 = CursoBuilder(cursoCpp).limitadoEnCupo(2).build()

        describe("Validar curso con limite de cupo") {

            it("Comprobar que se dicta el curso sin superar el limite") {
                cursoBaseFullDeco.dictarA(pepe)
                pepe.aptitudesObtenidas.contains(Aptitud.RE) shouldBe true
            }

            it("Probamos que pasa si el numero de estudiantes llega al limite de cupo") {
                cursoBase2.dictarA(pepe)
                assertThrows<RuntimeException> { cursoBase2.dictarA(pepe) }
            }
        }
    }
    describe("COSAS DEL CURSO CORRELATIVO ") {

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
                cursoBaseFullDeco.dictarA(esteban)//3
                esteban.aptitudesObtenidas.contains(Aptitud.RE) shouldBe true
            }

            it("Edgardo no puede cursar oporque no tiene los cursos correspondientes") {
                assertThrows<RuntimeException> {cursoBaseFullDeco.dictarA(Edgardo)}
            }

        }
    }

    describe("COSAS DEL CURSO CON CERTIFICACION DIGITAL ") {

        describe("cursos con certificados digitales") {
            it("cursos con certificado") {
                emailsEnviados.isNotEmpty() shouldBe false
                cursoBaseFullDeco.dictarA(pepe)
                emailsEnviados.isNotEmpty() shouldBe true
            }
        }
    }

    describe("COSAS DEL CURSO CON CONTADOR DE EGRESADOS ") {
        describe("validar)") {
            it("provbar registro") {
                cursoBaseFullDeco.dictarA(pepe)
                RegistroGlobalAlumnosAprobados.obtenerCantidadEgresados(cursoBaseFullDeco.nombre) shouldBe 6

            }
        }
    }
})