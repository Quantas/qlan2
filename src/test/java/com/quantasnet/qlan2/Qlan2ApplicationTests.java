package com.quantasnet.qlan2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Qlan2Application.class)
@WebAppConfiguration
@ActiveProfiles("junit")
public class Qlan2ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
