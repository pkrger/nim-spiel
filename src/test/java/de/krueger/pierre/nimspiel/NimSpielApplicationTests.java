package de.krueger.pierre.nimspiel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NimSpielApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertDoesNotThrow(() -> NimSpielApplication.main(new String[] {}));
	}

}
