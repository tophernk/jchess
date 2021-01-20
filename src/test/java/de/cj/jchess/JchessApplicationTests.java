package de.cj.jchess;

import de.cj.jchess.rest.JChessController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JchessApplicationTests {

	@Autowired
	private JChessController controller;

	@Test
	void contextLoads() {
		assertNotNull(controller);
	}

}
