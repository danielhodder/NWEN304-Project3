package nz.ac.victoria.ecs.nwen304.project3.server.data;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.kingombo.slf5j.LoggerFactory;

public final class InitialDataContextListner implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) { /* NOP */ }

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LoggerFactory.getLogger().info("Adding initial data");
		
		new InitialData().generateData();
	}
	
}