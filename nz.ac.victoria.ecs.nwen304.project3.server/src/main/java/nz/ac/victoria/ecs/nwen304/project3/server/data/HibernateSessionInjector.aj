package nz.ac.victoria.ecs.nwen304.project3.server.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;

@InjectObject
final aspect HibernateSessionInjector {
	@Inject
	private SessionFactory factory;
	
	private pointcut transactionalMethod() : execution(@Transactional * *(..));
	
	before(HibernateDataExchange hdx) : target(hdx) && transactionalMethod() {
		Session session = factory.openSession();
		session.beginTransaction();
		hdx.setSession(session);
	}
	
	after(HibernateDataExchange hdx) returning : target(hdx) && transactionalMethod() {
		hdx.getSession().getTransaction().commit();
		hdx.getSession().close();
		hdx.setSession(null);
	}
	
	after(HibernateDataExchange hdx) throwing(Throwable t) : target(hdx) && transactionalMethod() {
		hdx.getSession().getTransaction().rollback();
		hdx.getSession().close();
		hdx.setSession(null);
	}
}
