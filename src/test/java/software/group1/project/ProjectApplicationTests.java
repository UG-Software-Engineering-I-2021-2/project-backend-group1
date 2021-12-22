package software.group1.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectApplicationTests {

	@Test
	void MainTest() {
		ProjectApplication app = new ProjectApplication();
		app.main(new String[0]);
	}
}
