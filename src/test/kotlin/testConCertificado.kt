import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class TestCursoConCertificadoDigital5 : DescribeSpec({
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


    var mail1 = Mail(
        from = "eduardo",
        to = "tu vieja",
        subject = "agregado",
        content = "agregado al curso"
    )

    val emailsEnviados : MutableList<Mail> = mutableListOf()
    val servicioCorreo = mockk<ServicioCorreo>(relaxUnitFun = true)
    every { servicioCorreo.enviarMail(mail = any()) } answers { emailsEnviados.add(firstArg()) }

    val cursoBase = CursoBuilder(cursoCpp)
        .certificacionDigital(servicioCorreo)
        .build()

    describe("cursos con certificados digitales") {
        it("cursos con certificado") {
            emailsEnviados.size shouldBe 0
            servicioCorreo.enviarMail(mail1)
            emailsEnviados.size shouldBe 1
            cursoBase.dictarA(pepe)
            emailsEnviados.size shouldBe 2
        }
    }
})
