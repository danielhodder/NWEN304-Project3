package nz.ac.victoria.ecs.nwen304.project3.data;

import java.util.List;
import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;

public aspect DataExchangePropertySetter {
	// Get a single item
	private pointcut getItemByID() : execution(Item DataExchange+.getItemByID(UUID));
	after() returning (Item item) : getItemByID() {
		setFromDatasourceOnItem(item);
	}
	
	// Get the contents of a container
	private pointcut getItemsInContainer() : execution(List<Item> DataExchange+.getAllElementsInContainer(UUID));
	after() returning (List<Item> items) : getItemsInContainer() {
		for (Item item : items)
			setFromDatasourceOnItem(item);
	}
	
	/**
	 * Set the from datasource flag on items.
	 * 
	 * @param item
	 */
	static final void setFromDatasourceOnItem(Item item) {
		item.setFromDatasource(true);
		
		if (item instanceof Container)
			for (Item inside : ((Container) item).getItems())
				setFromDatasourceOnItem(inside);
	}
}
