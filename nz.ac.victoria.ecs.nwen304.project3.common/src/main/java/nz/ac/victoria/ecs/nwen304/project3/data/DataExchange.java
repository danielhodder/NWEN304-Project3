package nz.ac.victoria.ecs.nwen304.project3.data;

import java.util.List;

import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;

/**
 * Defines methods on how a data service should behave
 * 
 * @author danielh
 *
 */
public interface DataExchange {
	/**
	 * Get all the elements in the given container.
	 * 
	 * @param container	The container to search in, or null for the root container.
	 * @return	A list containing all the items in the container, or an empty list if there is nothing in the container.
	 */
	public List<Item> getAllElementsInContainer(Container container);
	
	
}
