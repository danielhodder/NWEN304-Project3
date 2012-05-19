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
	public Item(final String name, final Container parent) {
		setName(name);
		setParent(parent);
	}
	
	// -------------------------- Generated Code Below --------------------------

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Item other = (Item) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Item [name=");
		builder.append(this.name);
		builder.append("]");
		return builder.toString();
	}

	public Item getParent() {
		return this.parent;
	}

	public void setParent(final Container parent) {
		this.parent = parent;
	}
}
