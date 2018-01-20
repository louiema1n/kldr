package com.lm.kldr;

import com.lm.kldr.utils.Req;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KldrApplicationTests {

	@Test
	public void contextLoads() {
		String s = new Req().hcSendPost("9bccc0de-0cc1-425d-9f5b-102a14a9b215");
		System.out.println(s);
	}

}
