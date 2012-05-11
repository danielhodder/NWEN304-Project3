package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * A note in the system
 * 
 * @author danielh
 *
 */
@Entity
public final class Note extends Item implements Serializable {
	private static final long serialVersionUID = -6066398017441454761L;
	
	/**
	 * Creates a new note
	 * 
	 * @param name	The name of the note
	 * @param parent	The parent of this note
	 */
	public Note(String name, Container parent) {
		super(name, parent);
	}
}
