package nz.ac.victoria.ecs.nwen304.project3.data;

import java.util.List;
import java.util.UUID;

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
	 * Save an item to the datastore
	 * 
	 * @param item	The item to save
	 */
	void save(Item item);

	/**
	 * Get an item by it's ID
	 * 
	 * @param id	The ID of the item
	 * @return	The item or null if no item matched.
	 */
	public Item getItemByID(UUID id);

	/**
	 * Get all the elements in a container specified by the container's UUID.
	 * 
	 * @param containerID	The ID of the container
	 * @return	The list of items in the container, or null if no container was found.
	 */
	public List<Item> getAllElementsInContainer(UUID containerID);

	/**
	 * Delete an item from the datastore
	 * 
	 * @param item	The item to delete
	 */
	public void delete(Item item);
	
	/**
	 * Get the root container.
	 * 
	 * @return	The root container.
	 */
	public Container getRootContainer();
}
