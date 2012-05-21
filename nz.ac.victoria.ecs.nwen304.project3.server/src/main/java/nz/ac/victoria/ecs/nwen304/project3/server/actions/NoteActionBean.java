package nz.ac.victoria.ecs.nwen304.project3.server.actions;

import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.server.actions.resolutions.XMLEntityResolution;
import nz.ac.victoria.ecs.nwen304.project3.server.actions.typeconverters.NoteTypeConverter;

@UrlBinding("/Note/{$event}/{note=null}")
@StrictBinding
public class NoteActionBean extends AbstractActionBean {
	@ValidateNestedProperties({
		@Validate(field="name", trim=true, minlength=1)
	})
	@Validate(converter=NoteTypeConverter.class)
	private Note note;
	
	@HandlesEvent("new")
	public Resolution createNewNote() {
		return new XMLEntityResolution(note);
	}
	
	// ---------- GENERATED CODE ----------

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}
}
