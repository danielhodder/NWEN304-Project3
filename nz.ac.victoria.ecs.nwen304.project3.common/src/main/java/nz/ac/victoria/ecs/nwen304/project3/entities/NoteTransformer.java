package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

import flexjson.ObjectBinder;

public final class NoteTransformer extends Transformer {

	@SuppressWarnings("rawtypes") // From the interface we are implementing
	@Override
	public Object instantiate(final ObjectBinder arg0, final Object arg1, final Type arg2, final Class arg3) {
		if (!arg3.equals(Note.class))
			throw new IllegalArgumentException("The note transformer only transforms notes, not "+arg3.getName());
		
		if (!(arg1 instanceof Map<?, ?>))
			throw new IllegalArgumentException("Invalid instantiation type");
		
		final Map<?, ?> map = (Map<?, ?>) arg1;
		final Note n = new Note((String) map.get("name"), (String) map.get("contents"));
		
		if (map.get("uuid") != null)
			n.setUuid(UUID.fromString((String) map.get("uuid")));
		n.setLatatude((Double) map.get("latatude"));
		n.setLongdtude((Double) map.get("longditude"));
		
		return n;
	}

	@Override
	public void transform(final Object arg0) {
		if (!(arg0 instanceof nz.ac.victoria.ecs.nwen304.project3.entities.Note))
			throw new IllegalArgumentException("NoteTransformer can only transform notes");
		
		final Note note = (Note) arg0;
		
		getContext().writeOpenObject();
		
		getContext().writeName("type");
		getContext().writeQuoted("note");
		getContext().writeComma();
		
		// write the standard fields
		writeItemElements(note);
		
		getContext().writeName("contents");
		getContext().writeQuoted(note.getContents() == null ? "" : note.getContents());
		getContext().writeComma();
		
		getContext().writeName("latatude");
		getContext().write(String.valueOf(note.getLatatude()));
		getContext().writeComma();
		
		getContext().writeName("longditude");
		getContext().write(String.valueOf(note.getLongdtude()));
		getContext().writeComma();
		
		getContext().writeName("links");
		getContext().writeOpenObject();
			getContext().writeName("self");
			getContext().writeQuoted(
					String.format("%s/Note/%s", serilizationProperties.getProperty("http.prefix"), note.getUuid().toString()));
			getContext().writeComma();
			
			getContext().writeName("update");
			getContext().writeQuoted(
					String.format("%s/Note/%s", serilizationProperties.getProperty("http.prefix"), note.getUuid().toString()));
			getContext().writeComma();
			
			getContext().writeName("delete");
			getContext().writeQuoted(
					String.format("%s/Note/%s", serilizationProperties.getProperty("http.prefix"), note.getUuid().toString()));
		getContext().writeCloseObject();
		
		getContext().writeCloseObject();
	}

}
