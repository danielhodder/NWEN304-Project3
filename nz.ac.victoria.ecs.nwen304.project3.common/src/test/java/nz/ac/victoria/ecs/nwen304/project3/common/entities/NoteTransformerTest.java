package nz.ac.victoria.ecs.nwen304.project3.common.entities;

import static org.junit.Assert.assertEquals;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.NoteTransformer;

import org.junit.Test;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class NoteTransformerTest {
	@Test
	public void testTransformer() {
		final Note n = new Note("note1", new Container("blarg", null), "blarg");
		
		final String format = "{\"type\":\"note\",\"uuid\":\"%s\",\"name\":\"%s\",\"contents\":\"%s\",\"links\":{" +
				"\"self\":\"http://localhost/Note/%s\"," +
				"\"update\":\"http://localhost/Note/%s\"," +
				"\"delete\":\"http://localhost/Note/%s\"," +
				"\"parent\":\"http://localhost/Container/%s\"}}";
		
		assertEquals(String.format(format, n.getUuid(), n.getName(), n.getContents(), n.getUuid(), n.getUuid(), n.getUuid(), n.getParent().getUuid()),
				new JSONSerializer()
						.transform(new NoteTransformer(), Note.class)
						.exclude("class")
						.serialize(n));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTransformANonNoteClassFails() {
		new JSONSerializer()
			.transform(new NoteTransformer(), Object.class)
			.exclude("class")
			.serialize(new Object());
	}
	
	@Test
	public void testInflate() {
		final Note n = new Note("note1", null, "blarg");
		
		final String format = "{\"type\":\"note\",\"uuid\":\"%s\",\"name\":\"%s\",\"contents\":\"%s\",\"links\":{" +
				"\"self\":\"http://localhost/Note/%s\"," +
				"\"update\":\"http://localhost/Note/%s\"," +
				"\"delete\":\"http://localhost/Note/%s\"}}";
		
		assertEquals(n, 
				new JSONDeserializer<Note>().use(Note.class, new NoteTransformer())
						.deserialize(
								String.format(format, n.getUuid(), n.getName(), 
										n.getContents(), n.getUuid(), n.getUuid(), n.getUuid()), Note.class));
	}
}