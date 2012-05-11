package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * The superclass of all things that can be stored.
 * 
 * @author danielh
 *
 */
@MappedSuperclass
public abstract class Item implements Serializable {
	private static final long serialVersionUID = 3030360114496496023L;
	
	/**
	 * The name of the item
	 */
	private String name;
	
	/**
	 * The parent of this item
	 */
	@ManyToOne
	private Container parent;
	
	/**
	 * An internal consturctor used by the ORM implementation
	 */
	Item() {}
	
	/**
	 * Construct a new item.
	 * 
	 * @param name	The name of the item.
	 */
	public Item(String name, Container parent) {
		this.setName(name);
		this.setParent(parent);
	}
	
	// -------------------------- Generated Code Below --------------------------

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	public Item getParent() {
		return parent;
	}

	public void setParent(Container parent) {
		this.parent = parent;
	}
}
