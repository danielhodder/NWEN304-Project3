package com.kingombo.slf5j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class LoggerInjectionTest {
	@Test
	public void testInjectDowsntWorkWhenNotExpected() {
		class Test {
			@DontFill
			private Logger logger;
		}
		
		assertNull(new Test().logger);
	}
	
	@Test
	public void testInjectWorksWhenExpected() {
		class Test {
			private Logger logger;
		}
		
		assertNotNull(new Test().logger);
	}
}
