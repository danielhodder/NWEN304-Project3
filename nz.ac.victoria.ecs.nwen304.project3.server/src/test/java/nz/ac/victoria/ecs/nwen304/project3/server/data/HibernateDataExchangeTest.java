package nz.ac.victoria.ecs.nwen304.project3.server.data;

import static org.junit.Assert.assertEquals;
import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;
import nz.ac.victoria.ecs.nwen304.project3.server.guice.GuiceServletContextListner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

@InjectObject
public class HibernateDataExchangeTest {
	@Before public void setupGuice() { new GuiceServletContextListner().contextInitialized(null); }
	@After public void teardownGuice() { new GuiceServletContextListner().contextDestroyed(null); }
	
	@Inject
	private DataExchange data;
	
	@Test
	public void testRootContainer() {
		Container c = new Container("foo");
		c.setRoot(true);
		this.data.save(c);
		
		assertEquals(c, this.data.getRootContainer());
	}
}
