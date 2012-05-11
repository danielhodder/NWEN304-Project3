package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A container of notes.
 * 
 * @author danielh
 *
 */
@Entity
public final class Container extends Item {
	private static final long serialVersionUID = 4659108855436657253L;
	
	/**
	 * The items in this container.
	 */
	@OneToMany
	private List<Item> items;
	
	/**
	 * Create a new empty container
	 * 
	 * @param name	The name of the container
	 */
	public Container(String name, Container parent) {
		this(name, parent, new ArrayList<Item>(0));
	}
	
	/**
	 * Create a list with the given notes in it
	 * 
	 * @param name	The name of the container
	 * @param contents	The contents of the container
	 */
	public Container(String name, Container parent, List<Item> items) {
		super(name, parent);
		this.items = items;
		this.setParent(parent);
	}
	
	// -------------------------- Generated Code Below --------------------------

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
