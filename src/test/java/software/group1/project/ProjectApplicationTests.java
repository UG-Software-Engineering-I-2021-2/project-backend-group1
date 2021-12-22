package software.group1.project;

import config.endpoint_classes.rubric.Rubric;
import config.enums.Role;
import config.enums.State;
import data.entities.*;
import data.entities.composite_keys.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class ProjectApplicationTests {

	@Test
	public void MainTest() {
		ProjectApplication.main(new String[0]);
		Assert.assertTrue(true);
	}

	// entities tests

	@Test
	public void UserTest() {
		User u = new User();

		Long id = 0L;
		Long codempleado = 101L;
		Role rol = Role.Docente;
		String username = "user";
		Set<Seccion> seccionesDicta = new HashSet<>();
		Set<Seccion> seccionesCoordina = new HashSet<>();

		Seccion seccion1 = new Seccion();
		SeccionPK seccionPK1 = new SeccionPK();
		seccionPK1.setSemestre("2021 - 2");
		seccion1.setSeccionPK(seccionPK1);
		Seccion seccion2 = new Seccion();
		SeccionPK seccionPK2 = new SeccionPK();
		seccionPK2.setSemestre("2021 - 1");
		seccion2.setSeccionPK(seccionPK2);

		seccionesDicta.add(seccion1);
		seccionesCoordina.add(seccion1);
		seccionesDicta.add(seccion2);
		seccionesCoordina.add(seccion2);

		u.setId(id);
		u.setCodEmpleado(codempleado);
		u.setRol(rol);
		u.setUsername(username);
		u.setSeccionesDicta(seccionesDicta);
		u.setSeccionesCoordina(seccionesCoordina);

		Assert.assertEquals(u.getId(), id);
		Assert.assertEquals(u.getCodEmpleado(), codempleado);
		Assert.assertEquals(u.getRol(), rol);
		Assert.assertEquals(u.getUsername(), username);
		Assert.assertEquals(u.getSeccionesDicta(), seccionesDicta);
		Assert.assertEquals(u.getSeccionesDicta("2021 - 2").size(), 1);
		Assert.assertEquals(u.getSeccionesCoordina(), seccionesCoordina);
		Assert.assertEquals(u.getSeccionesCoordina("2021 - 2").size(), 1);
	}

	@Test
	public void SeccionTest() {
		Seccion s = new Seccion();

		SeccionPK sPK = new SeccionPK();
		String codseccion = "1";
		String semestre = "2021 - 2";
		String codcurso = "EN01";

		sPK.setCodSeccion(codseccion);
		sPK.setSemestre(semestre);
		sPK.setCodCurso(codcurso);

		Assert.assertEquals(sPK.getCodSeccion(), codseccion);
		Assert.assertEquals(sPK.getSemestre(), semestre);
		Assert.assertEquals(sPK.getCodCurso(), codcurso);

		Curso curso = new Curso();
		Set<User> docentesDicta = new HashSet<>();
		Set<User> docentesCoordina = new HashSet<>();
		Set<Lleva> lleva = new HashSet<>();
		Set<Rubrica> rubricas = new HashSet<>();

		s.setSeccionPK(sPK);
		s.setCursoSeccion(curso);
		s.setDocentesDicta(docentesDicta);
		s.setDocentesCoordina(docentesCoordina);
		s.setLleva(lleva);
		s.setRubricasRubricFinish(rubricas);

		Assert.assertEquals(s.getSeccionPK(), sPK);
		Assert.assertEquals(s.getCursoSeccion(), curso);
		Assert.assertEquals(s.getDocentesDicta(), docentesDicta);
		Assert.assertEquals(s.getDocentesCoordina(), docentesCoordina);
		Assert.assertEquals(s.getLleva(), lleva);
		Assert.assertEquals(s.getRubricasRubricFinish(), rubricas);
	}

	@Test
	public void CursoTest() {
		Curso c = new Curso();
		String codcurso = "EN01";
		String nombre = "curso";
		Set<Seccion> secciones = new HashSet<>();
		Set<RubricaBase> rubricasBase = new HashSet<>();
		Set<Carrera> carreras = new HashSet<>();

		c.setCodCurso(codcurso);
		c.setNombre(nombre);
		c.setSecciones(secciones);
		c.setRubricasBase(rubricasBase);
		c.setCarrerasPertenece(carreras);

		Assert.assertEquals(c.getCodCurso(), codcurso);
		Assert.assertEquals(c.getNombre(), nombre);
		Assert.assertEquals(c.getSecciones(), secciones);
		Assert.assertEquals(c.getRubricasBase(), rubricasBase);
		Assert.assertEquals(c.getCarrerasPertenece(), carreras);
	}

	@Test
	public void CarreraTest() {
		Carrera c = new Carrera();
		Long id = 0L;
		String nombre = "carrera";
		Set<Alumno> alumnos = new HashSet<>();
		Set<Competencia> competencias = new HashSet<>();
		Set<Curso> cursos = new HashSet<>();

		c.setId(id);
		c.setNombre(nombre);
		c.setAlumnos(alumnos);
		c.setCompetencias(competencias);
		c.setCursosPertenece(cursos);

		Assert.assertEquals(c.getId(), id);
		Assert.assertEquals(c.getNombre(), nombre);
		Assert.assertEquals(c.getAlumnos(), alumnos);
		Assert.assertEquals(c.getCompetencias(), competencias);
		Assert.assertEquals(c.getCursosPertenece(), cursos);
	}

	@Test
	public void AlumnoTest() {
		Alumno a = new Alumno();
		String codalumno = "0001";
		Carrera carrera = new Carrera();
		String nombre = "alumno";
		Set<Lleva> lleva = new HashSet<>();
		Set<Evalua> evalua = new HashSet<>();

		a.setCodAlumno(codalumno);
		a.setCarreraAlumno(carrera);
		a.setNombre(nombre);
		a.setLleva(lleva);
		a.setEvalua(evalua);

		Assert.assertEquals(a.getCodAlumno(), codalumno);
		Assert.assertEquals(a.getCarreraAlumno(), carrera);
		Assert.assertEquals(a.getNombre(), nombre);
		Assert.assertEquals(a.getLleva(), lleva);
		Assert.assertEquals(a.getEvalua(), evalua);
	}

	@Test
	public void CompetenciaTest() {
		Competencia c = new Competencia();
		String codcompetencia = "C01";
		Carrera carrera = new Carrera();
		String descripcion = "descripcion";
		Set<RubricaBase> rubricasBase = new HashSet<>();

		c.setCodCompetencia(codcompetencia);
		c.setCarreraCompetencia(carrera);
		c.setDescripcion(descripcion);
		c.setRubricas(rubricasBase);

		Assert.assertEquals(c.getCodCompetencia(), codcompetencia);
		Assert.assertEquals(c.getCarreraCompetencia(), carrera);
		Assert.assertEquals(c.getDescripcion(), descripcion);
		Assert.assertEquals(c.getRubricas(), rubricasBase);
	}

	@Test
	public void EvaluaTest() {
		Evalua e = new Evalua();

		EvaluaPK ePK = new EvaluaPK();
		String codalumno = "0001";
		RubricaPK rubricaPK = new RubricaPK();

		ePK.setCodAlumno(codalumno);
		ePK.setRubricaPK(rubricaPK);

		Assert.assertEquals(ePK.getCodAlumno(), codalumno);
		Assert.assertEquals(ePK.getRubricaPK(), rubricaPK);

		String calificacionAlumno = "0";
		String calificacionCompetencia = "1";
		boolean evaluacionTotal = false;
		Alumno alumno = new Alumno();
		Rubrica rubrica = new Rubrica();

		e.setEvaluaPK(ePK);
		e.setCalificacionAlumno(calificacionAlumno);
		e.setCalificacionCompetencia(calificacionCompetencia);
		e.setEvaluacionTotal(evaluacionTotal);
		e.setAlumnoEvalua(alumno);
		e.setRubricaEvalua(rubrica);

		Assert.assertEquals(e.getEvaluaPK(), ePK);
		Assert.assertEquals(e.getCalificacionAlumno(), calificacionAlumno);
		Assert.assertEquals(e.getCalificacionCompetencia(), calificacionCompetencia);
		Assert.assertEquals(e.isEvaluacionTotal(), evaluacionTotal);
		Assert.assertEquals(e.getAlumnoEvalua(), alumno);
		Assert.assertEquals(e.getRubricaEvalua(), rubrica);
	}

	@Test
	public void LlevaTest() {
		Lleva l = new Lleva();

		LlevaPK lPK = new LlevaPK();
		String codalumno = "0001";
		SeccionPK seccionPK = new SeccionPK();

		lPK.setCodAlumno(codalumno);
		lPK.setSeccionPK(seccionPK);

		Assert.assertEquals(lPK.getCodAlumno(), codalumno);
		Assert.assertEquals(lPK.getSeccionPK(), seccionPK);

		Short ciclo = 2;
		Alumno alumno = new Alumno();
		Seccion seccion = new Seccion();

		l.setLlevaPK(lPK);
		l.setCiclo(ciclo);
		l.setAlumnoLleva(alumno);
		l.setSeccionLleva(seccion);

		Assert.assertEquals(l.getLlevaPK(), lPK);
		Assert.assertEquals(l.getCiclo(), ciclo);
		Assert.assertEquals(l.getAlumnoLleva(), alumno);
		Assert.assertEquals(l.getSeccionLleva(), seccion);
	}

	@Test
	public void RubricaTest() {
		Rubrica r = new Rubrica();

		RubricaPK rPK = new RubricaPK();
		String semestre = "2021 - 2";
		RubricaBasePK rubricaBasePK = new RubricaBasePK();

		rPK.setSemestre(semestre);
		rPK.setRubricaBasePK(rubricaBasePK);

		Assert.assertEquals(rPK.getSemestre(), semestre);
		Assert.assertEquals(rPK.getRubricaBasePK(), rubricaBasePK);

		Short dimensiones = 3;
		LocalDate fecha = LocalDate.of(2020, 1, 8);
		State estado = State.SinAsignar;
		String descriptores = "d1,d2,d3";
		String actividad = "actividad";
		String titulo = "titulo";
		RubricaBase rubricaBase = new RubricaBase();
		Set<Evalua> evalua = new HashSet<>();
		Set<Seccion> secciones = new HashSet<>();

		r.setRubricaPK(rPK);
		r.setDimensiones(dimensiones);
		r.setFecha(fecha);
		r.setEstado(estado);
		r.setDescriptores(descriptores);
		r.setActividad(actividad);
		r.setTitulo(titulo);
		r.setRubricaBase(rubricaBase);
		r.setEvalua(evalua);
		r.setSeccionesRubricFinish(secciones);

		Assert.assertEquals(r.getRubricaPK(), rPK);
		Assert.assertEquals(r.getDimensiones(), dimensiones);
		Assert.assertEquals(r.getFecha(), fecha);
		Assert.assertEquals(r.getEstado(), estado);
		Assert.assertEquals(r.getDescriptores(), descriptores);
		Assert.assertEquals(r.getActividad(), actividad);
		Assert.assertEquals(r.getTitulo(), titulo);
		Assert.assertEquals(r.getRubricaBase(), rubricaBase);
		Assert.assertEquals(r.getEvalua(), evalua);
		Assert.assertEquals(r.getSeccionesRubricFinish(), secciones);
		Assert.assertEquals(r.getSemestre(), semestre);
	}

	@Test
	public void RubricaBaseTest() {
		RubricaBase r = new RubricaBase();

		RubricaBasePK rPK = new RubricaBasePK();
		String codrubrica = "R01";
		String codcurso = "EN01";
		String codcompetencia = "C01";

		rPK.setCodRubrica(codrubrica);
		rPK.setCodCurso(codcurso);
		rPK.setCodCompetencia(codcompetencia);

		Assert.assertEquals(rPK.getCodRubrica(), codrubrica);
		Assert.assertEquals(rPK.getCodCurso(), codcurso);
		Assert.assertEquals(rPK.getCodCompetencia(), codcompetencia);

		String actividadBase = "actividad base";
		Short semana = 7;
		Short nivel = 1;
		String criterioDesempeno = "criterio de desempeño";
		String evaluacion = "evaluacion";
		String evidencia = "evidencia";
		String tituloBase = "titulo base";
		Curso curso = new Curso();
		Competencia competencia = new Competencia();
		Set<Rubrica> rubricas = new HashSet<>();

		Rubrica rubrica1 = new Rubrica();
		RubricaPK rubricaPK1 = new RubricaPK();
		rubricaPK1.setSemestre("2021 - 2");
		rubrica1.setRubricaPK(rubricaPK1);

		Rubrica rubrica2 = new Rubrica();
		RubricaPK rubricaPK2 = new RubricaPK();
		rubricaPK2.setSemestre("2021 - 1");
		rubrica2.setRubricaPK(rubricaPK2);

		Rubrica rubrica3 = new Rubrica();
		RubricaPK rubricaPK3 = new RubricaPK();
		rubricaPK3.setSemestre("2021 - 2");
		rubrica3.setRubricaPK(rubricaPK3);

		rubricas.add(rubrica1);
		rubricas.add(rubrica2);
		rubricas.add(rubrica3);

		r.setRubricaPK(rPK);
		r.setActividadBase(actividadBase);
		r.setSemana(semana);
		r.setNivel(nivel);
		r.setCriterioDesempeno(criterioDesempeno);
		r.setEvaluacion(evaluacion);
		r.setEvidencia(evidencia);
		r.setTituloBase(tituloBase);
		r.setCursoRubrica(curso);
		r.setCompetenciaRubrica(competencia);
		r.setRubricas(rubricas);

		Assert.assertEquals(r.getRubricaPK(), rPK);
		Assert.assertEquals(r.getActividadBase(), actividadBase);
		Assert.assertEquals(r.getSemana(), semana);
		Assert.assertEquals(r.getNivel(), nivel);
		Assert.assertEquals(r.getCriterioDesempeno(), criterioDesempeno);
		Assert.assertEquals(r.getEvaluacion(), evaluacion);
		Assert.assertEquals(r.getEvidencia(), evidencia);
		Assert.assertEquals(r.getTituloBase(), tituloBase);
		Assert.assertEquals(r.getCursoRubrica(), curso);
		Assert.assertEquals(r.getCompetenciaRubrica(), competencia);
		Assert.assertEquals(r.getRubricas(), rubricas);
		Assert.assertEquals(r.getCodRubrica(), codrubrica);

		Assert.assertEquals(r.getRubricas("2021 - 2").size(), 2);
		Assert.assertEquals(r.getRubrica("2021 - 1"), rubrica2);
	}

	// enums tests

	@Test
	public void StateTest() {
		State sinAsignar = State.SinAsignar;
		State aprobacionPendientes = State.AprobacionPendiente;
		State disponibleParaCalificar = State.DisponibleParaCalificar;
		State fueraDeFecha = State.FueraDeFecha;
		State cumplidos = State.Cumplidos;

		Assert.assertEquals(sinAsignar.toString(), "Sin asignar");
		Assert.assertEquals(aprobacionPendientes.toString(), "Aprobacion pendiente");
		Assert.assertEquals(disponibleParaCalificar.toString(), "Disponible para calificar");
		Assert.assertEquals(fueraDeFecha.toString(), "Fuera de fecha");
		Assert.assertEquals(cumplidos.toString(), "Cumplidos");
	}
}
