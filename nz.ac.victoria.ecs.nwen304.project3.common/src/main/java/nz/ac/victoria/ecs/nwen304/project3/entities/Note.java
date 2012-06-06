package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
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
	@Column(nullable=false)
	private String contents;
	
	/**
	 * The latatude of the location where the note was made
	 */
	@Column(nullable=false)
	private double latatude;
	
	/**
	 * The longdtude of the the location where the note was made
	 */
	@Column(nullable=false)
	private double longdtude;
	
	// Visible for Hibernate
	Note() {}
	
	/**
	 * Creates a new note
	 * 
	 * @param name	The name of the note
	 */
	public Note(final String name) {
		this(name, "");
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
	@Deprecated
	public Note(final String name, final Container parent, final String contents) {
		this(name, parent);
		
		setContents(contents);
	}
	
	@Deprecated
	Note(final String name, final String contents, final UUID uuid) {
		this(name, null, contents);
		
		setUuid(uuid);
	}

	/**
	 * Create a note with a title and some contents
	 * 
	 * @param name	The name of the note
	 * @param contents	The contents of the note
	 */
	public Note(final String name, final String contents) {
		super(name);
		
		setContents(contents);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((contents == null) ? 0 : contents.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latatude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longdtude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (Double.doubleToLongBits(latatude) != Double
				.doubleToLongBits(other.latatude))
			return false;
		if (Double.doubleToLongBits(longdtude) != Double
				.doubleToLongBits(other.longdtude))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Note [contents=");
		builder.append(contents);
		builder.append(", latatude=");
		builder.append(latatude);
		builder.append(", longdtude=");
		builder.append(longdtude);
		builder.append(", getUuid()=");
		builder.append(getUuid());
		builder.append("]");
		return builder.toString();
	}

	public double getLatatude() {
		return latatude;
	}

	public void setLatatude(double latatude) {
		this.latatude = latatude;
	}

	public double getLongdtude() {
		return longdtude;
	}

	public void setLongdtude(double longdtude) {
		this.longdtude = longdtude;
	}
}
