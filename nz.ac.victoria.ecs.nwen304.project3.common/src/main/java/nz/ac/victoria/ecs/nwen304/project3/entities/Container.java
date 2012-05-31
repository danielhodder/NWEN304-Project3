package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	@OneToMany(fetch=FetchType.EAGER)
	private List<Item> items;
	
	/**
	 * A flag to say if this is the root container or not.
	 */
	private boolean root;
	
	Container() {}
	
	/**
	 * Create a new empty container
	 * 
	 * @param name	The name of the container
	 */
	public Container(final String name) {
		this(name, new ArrayList<Item>(0));
	}
	
	/**
	 * Create a list with the given notes in it
	 * 
	 * @param name	The name of the container
	 * @param contents	The contents of the container
	 */
	public Container(final String name, final List<Item> items) {
		super(name);
		
		if (items == null)
			throw new IllegalArgumentException("The list of items can not be null");
		
		this.items = items;
		
		setUuid(UUID.randomUUID());
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
		} else {
			if (other.items.size() != this.items.size())
				return false;
			
			for (int i=0; i<this.items.size(); i++) {
				if (other.items.get(i) == null && this.items.get(i) == null)
					continue;
				if
						((other.items.get(i) != null && this.items.get(i) != null) && 
						(other.items.get(i).equals(this.items.get(i))))
					continue;
				else
					return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Container [items=");
		builder.append(this.items);
		builder.append(", root=");
		builder.append(this.root);
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getUuid()=");
		builder.append(getUuid());
		builder.append("]");
		return builder.toString();
	}

	public boolean isRoot() {
		return this.root;
	}

	public void setRoot(final boolean root) {
		this.root = root;
	}
}
