class Estudiante(
    var nombre: String,
    var apellido: String,
    var mail: String,
    var aptitudesDeseadas: MutableList<Aptitud> = mutableListOf(),
    var aptitudesObtenidas: MutableList<Aptitud> = mutableListOf(),
) {
    fun agregarAptitudDeseada(aptitud: Aptitud) {
        aptitudesDeseadas.add(aptitud)
    }

    fun agregarCursoTerminado(nuevoCurso: CursoPosta) {
        nuevoCurso.aptitudesContenidas.forEach { aptitudesObtenidas.add(it) }
    }

    fun esInteresante(curso: CursoPosta): Boolean {
        return curso.aptitudesContenidas.any { aptitudesDeseadas.contains(it) }

    }

    fun tieneAptitudEspecifica(aptitud: Aptitud): Boolean {
        return aptitudesObtenidas.contains(aptitud)
    }
}

//-contrato
interface Curso {
    val nombre: String
    var aptitudesContenidas: MutableList<Aptitud>
    fun validarCursadaDe(estudiante: Estudiante): Boolean
    fun otorgaAptitud(aptitud: Aptitud): Boolean
    fun tieneAptitudes(): Boolean
    fun dictarA(estudiante: Estudiante)
}

//-ultima capa
class CursoPosta(
    override val nombre: String,
    override var aptitudesContenidas: MutableList<Aptitud> = mutableListOf(),

    ) : Curso {

    override fun validarCursadaDe(estudiante: Estudiante): Boolean {
        return estudiante.esInteresante(this)
    }

    override fun dictarA(estudiante: Estudiante) {
        if (!validarCursadaDe(estudiante)) {
            throw BusinessException("no le interesa" + estudiante.nombre)
        }
        estudiante.agregarCursoTerminado(this)
    }

    override fun otorgaAptitud(aptitud: Aptitud): Boolean {
        return aptitudesContenidas.contains(aptitud)
    }

    override fun tieneAptitudes(): Boolean {
        return this.aptitudesContenidas.isNotEmpty()
    }
}

//-decorador y decorados
abstract class CursoConCaracteristicas(var curso: Curso) : Curso by curso {
}

class CursoConLimiteDeCupo(val limiteCupo: Int, curso: Curso) : CursoConCaracteristicas(curso) {

    var contadorDeEstudiantes:Int = 0
    private fun aumentarEstiantes(){
        contadorDeEstudiantes += 1
    }
    override fun dictarA(estudiante: Estudiante) {
        this.aumentarEstiantes()
        if (contadorDeEstudiantes >= limiteCupo) {
            throw BusinessException("No hay cupo disponible")
        }
        curso.dictarA(estudiante)
    }

}

class CursoCorrelativo(var aptitudesRequeridas: MutableList<Aptitud>, curso: Curso) : CursoConCaracteristicas(curso) {

    override fun dictarA(estudiante: Estudiante) {
        if (aptitudesRequeridas.any { !estudiante.tieneAptitudEspecifica(it) }) {
            throw BusinessException(
                "El estudiante no tiene las aptitudes " +
                        "necesarias para realizar el curso"
            )
        }
        curso.dictarA(estudiante)
    }
}

class CursoConCertificadoDigital(var servicioCorreo: ServicioCorreo, curso: Curso) : CursoConCaracteristicas(curso) {

    override fun dictarA(estudiante: Estudiante) {
        curso.dictarA(estudiante)
        enviarMail(estudiante, curso )
    }


    private fun enviarMail(estudiante: Estudiante, curso: Curso) {
        val mensaje = " El estudiante " + estudiante.nombre + " " + estudiante.apellido +
                "realizo el curso " + curso.nombre + "."


        servicioCorreo.enviarMail(
            Mail(from="${curso.nombre}@gmail.com", estudiante.mail, subject = "Cartificado ${curso.nombre}", mensaje)
        )
    }
}

interface ServicioCorreo {
    fun enviarMail(mail: Mail)
}

data class Mail(val from: String, val to: String, val subject: String, val content: String)

class CursoConRegistroDeEgresados(curso: Curso) : CursoConCaracteristicas(curso) {

    override fun dictarA(estudiante: Estudiante) {
       curso.dictarA(estudiante)
        RegistroGlobalAlumnosAprobados.aumentarContadorDeEgresados(this)
    }

}

class RegistroGlobalAlumnosAprobados(){

    companion object{
        var cursoGlobal: HashMap<String, Int> = hashMapOf()

        fun aumentarContadorDeEgresados(curso:Curso){
            cursoGlobal[curso.nombre] = cursoGlobal.getOrDefault(curso.nombre, 0) + 1

        }
        fun obtenerCantidadEgresados(nombre: String): Int? {
            return cursoGlobal.getOrDefault(nombre, 0)
        }
    }

}

class CursoBuilder(var curso: Curso) {

    fun certificacionDigital(correo: ServicioCorreo): CursoBuilder {
        curso = CursoConCertificadoDigital(correo, curso)
        return this
    }

    fun limitadoEnCupo(cupoMaximo: Int): CursoBuilder {
        curso = CursoConLimiteDeCupo(cupoMaximo,curso)
        return this
    }

    fun correlativo(aptitudesRequeridas: MutableList<Aptitud>): CursoBuilder {
        curso = CursoCorrelativo(aptitudesRequeridas, curso)
        return this
    }

    fun contadorDeEgresados(): CursoBuilder {
        curso = CursoConRegistroDeEgresados(curso)
        return this
    }

    fun build() = curso
}

class BusinessException(message: String) : RuntimeException(message) {}

enum class Aptitud {
    JAVA, RE, PASCAL, KOTLIN
}