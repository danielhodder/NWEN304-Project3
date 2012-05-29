package nz.ac.victoria.ecs.nwen304.project3.server.data;

import java.io.IOException;
import java.util.Properties;

import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.google.inject.AbstractModule;

public class HibernateSessionModule extends AbstractModule {
	@Override
	protected void configure() {
		Class<?>[] entities = {
			Note.class,
			Container.class,
			Item.class
		};
		
		try {
			Properties p = new Properties();
			p.load(this.getClass().getClassLoader().getResourceAsStream("hibernate.properties"));			
			final Configuration configuration = new Configuration();
			configuration.addProperties(p);
			
			for (final Class<?> c : entities)
				configuration.addAnnotatedClass(c);
			
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			bind(SessionFactory.class).toInstance(configuration.buildSessionFactory(serviceRegistry));
		} catch (IOException e) {
			throw new RuntimeException("Could not load the hibernate properties file", e);
		}
	}
}
