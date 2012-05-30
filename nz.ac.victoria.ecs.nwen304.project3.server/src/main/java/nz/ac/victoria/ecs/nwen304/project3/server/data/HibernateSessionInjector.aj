package nz.ac.victoria.ecs.nwen304.project3.server.data;

import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

@InjectObject
final aspect HibernateSessionInjector {
	@Inject
	private SessionFactory factory;
	
	private pointcut transactionalMethod() : execution(@Transactional * *(..));
	
	before(HibernateDataExchange hdx) : target(hdx) && transactionalMethod() {	
		push(hdx);
		if (size(hdx) > 1)
			return;
			
		Session session = this.factory.openSession();
		session.beginTransaction();
		hdx.setSession(session);
	}
	
	after(HibernateDataExchange hdx) returning : target(hdx) && transactionalMethod() {
		pop(hdx);
		if (size(hdx) > 0)
			return;
		
		hdx.getSession().getTransaction().commit();
		hdx.getSession().close();
		hdx.setSession(null);
	}
	
	after(HibernateDataExchange hdx) throwing(Throwable t) : target(hdx) && transactionalMethod() {
		pop(hdx);
		if (size(hdx) > 0)
			return;
		
		hdx.getSession().getTransaction().rollback();
		hdx.getSession().close();
		hdx.setSession(null);
	}
	
	private void push(HibernateDataExchange hdx) {
		if (!objects.containsKey(hdx))
			objects.put(hdx, new Stack<Object>());
		
		objects.get(hdx).push(new Object());
	}
	
	private void pop(HibernateDataExchange hdx) {
		objects.get(hdx).pop();
	}
	
	private int size(HibernateDataExchange hdx) {
		return objects.get(hdx).size();
	}
	
	private Map<Object, Stack<Object>> objects = new ConcurrentHashMap<Object, Stack<Object>>();
}
