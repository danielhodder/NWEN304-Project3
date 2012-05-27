package nz.ac.victoria.ecs.nwen304.project3.common.guice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectOnCall;

import org.junit.Test;

import com.google.inject.Inject;

public class GuiceInjectorTest {
	@Test
	public void testOnObjectConstruction() {
		@InjectObject
		class Test {
			@Inject
			private Boolean foo;
			
			Test() {
				assertTrue(this.foo);
			}
		}
		
		new Test();
	}
	
	@Test
	public void testObjectOnCall() {
		class Test {
			@Inject
			private Boolean foo = false;
			
			Test() {
				assertFalse(this.foo);
				this.foo = false;
			}
			
			@InjectOnCall
			void callMe() {
				assertTrue(this.foo);
			}
		}
		
		new Test().callMe();
	}
	
	@Test
	public void testObjectOnConstruct() {
		class Test {
			@Inject
			private final Boolean foo;
			
			@InjectOnCall
			Test () {
				assertNotNull(this.foo);
				assertTrue(this.foo);
				this.foo = false;
			}
		}
		
		new Test();
	}
}
