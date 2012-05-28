package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;

/**
 * A note in the system
 * 
 * @author danielh
 *
 */
@Entity
public class Note extends Item implements Serializable {
	private static final long serialVersionUID = -6066398017441454761L;
	
	/**
	 * The contents of this note
	 */
	private String contents;
	
	// Visible for Hibernate
	Note() {}
	
	/**
	 * Creates a new note
	 * 
	 * @param name	The name of the note
	 */
	public Note(String name) {
		super(name);
	}
	
	@Deprecated
	public Note(final String name, final Container parent) {
		super(name, parent);
	}

	/**
	 * Create a new note with contents
	 * 
	 * @param name	The name of the note
	 * @param parent	The parent of the note
	 * @param contents	The contents of the note
	 */
	public Note(final String name, final Container parent, final String contents) {
		this(name, parent);
		
		setContents(contents);
	}
	
	Note(final String name, final String contents, final UUID uuid) {
		this(name, null, contents);
		
		setUuid(uuid);
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return this.contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(final String contents) {
		this.contents = contents;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((this.contents == null) ? 0 : this.contents.hashCode());
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
		final Note other = (Note) obj;
		if (this.contents == null) {
			if (other.contents != null)
				return false;
		} else if (!this.contents.equals(other.contents))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Note [contents=" + this.contents + ", getName()="
				+ getName() + ", isFromDatasource()="
				+ isFromDatasource() + ", getUuid()=" + getUuid()
				+ "]";
	}
}
