package nz.ac.victoria.ecs.nwen304.project3.server.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.inject.AbstractModule;

public class HibernateDataExchange implements DataExchange {
	@Transactional
	@Override
	public void save(Item item) {
		getSession().save(item);
	}

	@Transactional
	@Override
	public Item getItemByID(UUID id) {
		return (Item) getSession().createCriteria(Item.class)
				.add(Restrictions.eq("uuid", id))
				.uniqueResult();
	}

	@Transactional
	@Override
	public List<Item> getAllElementsInContainer(UUID containerID) {
		Item container = getItemByID(containerID);
		
		if (!(container instanceof Container))
			return new ArrayList<Item>(0);
		
		return ((Container) container).getItems();
	}

	@Transactional
	@Override
	public void delete(Item item) {
		getSession().delete(item);
	}
	
	@Transactional
	@Override
	public Container getRootContainer() {
		return (Container) getSession().createCriteria(Container.class)
				.add(Restrictions.eq("root", true))
				.uniqueResult();
	}
	
	private Session session;
	void setSession(Session session) {
		this.session = session;
	}
	
	Session getSession() {
		return this.session;
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(DataExchange.class).to(HibernateDataExchange.class);
		}
	}
}
