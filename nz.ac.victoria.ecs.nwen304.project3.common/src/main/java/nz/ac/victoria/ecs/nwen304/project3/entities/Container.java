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
	
	Container() {}
	
	/**
	 * Create a new empty container
	 * 
	 * @param name	The name of the container
	 */
	public Container(final String name, final Container parent) {
		this(name, parent, new ArrayList<Item>(0));
	}
	
	/**
	 * Create a list with the given notes in it
	 * 
	 * @param name	The name of the container
	 * @param contents	The contents of the container
	 */
	public Container(final String name, final Container parent, final List<Item> items) {
		super(name, parent);
		this.items = items;
		
		setParent(parent);
	}
	
	// -------------------------- Generated Code Below --------------------------

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(final List<Item> items) {
		this.items = items;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((this.items == null) ? 0 : this.items.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Container other = (Container) obj;
		if (this.items == null) {
			if (other.items != null)
				return false;
		} else if (!this.items.equals(other.items))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Container [items=" + this.items + ", getName()="
				+ getName() + ", getParent()=" + getParent() + "]";
	}
}
