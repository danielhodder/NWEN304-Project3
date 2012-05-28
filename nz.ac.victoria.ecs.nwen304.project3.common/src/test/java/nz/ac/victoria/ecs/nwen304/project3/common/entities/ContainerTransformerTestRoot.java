package nz.ac.victoria.ecs.nwen304.project3.common.entities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.ContainerTransformer;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.NoteTransformer;

import org.junit.Test;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class ContainerTransformerTestRoot {	
	@Test
	public void testSerilizationOfEmptyRootContainer() {
		Container c = new Container("blarg", new ArrayList<Item>(0));
		c.setRoot(true);
		
		final String format = "{\"type\":\"container\",\"uuid\":\"%s\",\"name\":\"%s\",\"contents\":[],\"links\":{" +
				"\"self\":\"http://localhost/Container/%s\"," +
				"\"update\":\"http://localhost/Container/%s\"," +
				"\"add\":\"http://localhost/Container/%s\"}}";
		
		assertEquals(String.format(format, c.getUuid(), c.getName(), c.getUuid(), c.getUuid(), c.getUuid(), c.getUuid()),
				new JSONSerializer()
						.transform(new ContainerTransformer(), Container.class)
						.transform(new NoteTransformer(), Note.class)
						.exclude("class")
						.serialize(c));
	}
	
	@Test
	public void testDeserilizationOfEmptyRootContainer() {
		UUID id = UUID.randomUUID();
		final String s = String.format("{\"type\":\"container\",\"uuid\":\"%s\",\"name\":\"foo\",\"contents\":[],\"links\":{" +
				"\"self\":\"http://localhost/Container/%s\"," +
				"\"update\":\"http://localhost/Container/%s\"," +
				"\"add\":\"http://localhost/Container/%s\"}}",
				
				id, id, id, id, id
		);
		
		Container c = new Container("foo");
		c.setRoot(true);
		c.setUuid(id);
		
		assertEquals(c, new JSONDeserializer<Container>()
				.use(Container.class, new ContainerTransformer())
				.use(Note.class, new NoteTransformer())
				.deserialize(s, Container.class));
	}
}
