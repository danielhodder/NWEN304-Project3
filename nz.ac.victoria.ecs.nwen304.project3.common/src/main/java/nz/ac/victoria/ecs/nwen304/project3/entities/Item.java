package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
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
	
	@Id
	private UUID uuid;
	
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
	 * Has the item been loaded from a datasource or is is created by client code.
	 */
	private boolean fromDatasource;
	
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
		setUuid(UUID.randomUUID());
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.fromDatasource ? 1231 : 1237);
		result = prime * result
				+ ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result
				+ ((this.parent == null) ? 0 : this.parent.hashCode());
		result = prime * result
				+ ((this.uuid == null) ? 0 : this.uuid.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Item other = (Item) obj;
		if (this.fromDatasource != other.fromDatasource)
			return false;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		if (this.parent == null) {
			if (other.parent != null)
				return false;
		} else if (!this.parent.equals(other.parent))
			return false;
		if (this.uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!this.uuid.equals(other.uuid))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [uuid=" + this.uuid + ", name=" + this.name + ", parent="
				+ this.parent + ", fromDatasource=" + this.fromDatasource + "]";
	}

	public Item getParent() {
		return this.parent;
	}

	public void setParent(final Container parent) {
		this.parent = parent;
	}

	/**
	 * @return the fromDatasource
	 */
	public boolean isFromDatasource() {
		return this.fromDatasource;
	}

	/**
	 * @param fromDatasource the fromDatasource to set
	 */
	public void setFromDatasource(final boolean fromDatasource) {
		this.fromDatasource = fromDatasource;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return this.uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(final UUID uuid) {
		this.uuid = uuid;
	}
}
