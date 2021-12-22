package software.group1.project;

import config.enums.Role;
import data.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@Test
	void MainTest() {
		//ProjectApplication app = new ProjectApplication();
		//app.main(new String[0]);
		Assertions.assertTrue(true);
	}

	// entities tests

	@Test
	void UserTest() {
		User u = new User();
		Long id = 0L;
		Long codempleado = 101L;
		Role rol = Role.Docente;
		String username = "user";

		u.setId(id);
		u.setCodEmpleado(codempleado);
		u.setRol(rol);
		u.setUsername(username);

		Assertions.assertEquals(id, u.getId());
		Assertions.assertEquals(codempleado, u.getCodEmpleado());
		Assertions.assertEquals(rol, u.getRol());
		Assertions.assertEquals(username, u.getUsername());
	}
}
