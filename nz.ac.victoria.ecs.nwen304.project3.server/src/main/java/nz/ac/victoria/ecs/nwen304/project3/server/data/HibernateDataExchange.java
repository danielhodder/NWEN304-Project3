package nz.ac.victoria.ecs.nwen304.project3.server.data;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.google.inject.AbstractModule;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;

public class HibernateDataExchange implements DataExchange {
	@Transactional
	@Override
	public List<Item> getAllElementsInContainer(Container container) {
		// TODO Auto-generated method stub
		return null;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void delete(Item item) {
		// TODO Auto-generated method stub

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
