package nz.ac.victoria.ecs.nwen304.project3.server.guice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import nz.ac.victoria.ecs.nwen304.project3.server.data.HibernateDataExchange;
import nz.ac.victoria.ecs.nwen304.project3.server.data.HibernateSessionModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public final class GuiceServletContextListner implements ServletContextListener {
	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
		injector = null;
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		injector = Guice.createInjector(
				new HibernateSessionModule(),
				new HibernateDataExchange.Module());
	}
	
	private static Injector injector;
	public static Injector getInjector() {
		return injector;
	}
}
