package nz.ac.victoria.ecs.nwen304.project3.server.actions.typeconverters;

import java.util.Collection;
import java.util.Locale;
import java.util.UUID;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;

import com.google.inject.Inject;

public final class NoteTypeConverter implements TypeConverter<Note> {
	@Inject
	private DataExchange data;
	
	@Override
	public void setLocale(Locale locale) { /* NOP */ }

	@Override
	public Note convert(String input, Class<? extends Note> targetType, Collection<ValidationError> errors) {
		if (input == null)
			return null;
		
		try {
			Item item = data.getItemByID(UUID.fromString(input));
			
			return (item instanceof Note) ? (Note) item : null;
		} catch (IllegalArgumentException iae) {
			// This means that the UUID was not valid
			return null;
		}
	}
}
