package software.group1.project;

import config.enums.Role;
import data.entities.Seccion;
import data.entities.User;
import data.entities.composite_keys.SeccionPK;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

		s.setSeccionPK(sPK);
	}
}
