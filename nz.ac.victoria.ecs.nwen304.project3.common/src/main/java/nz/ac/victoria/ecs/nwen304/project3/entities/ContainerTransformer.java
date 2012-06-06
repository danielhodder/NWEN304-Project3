package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import flexjson.ObjectBinder;

public class ContainerTransformer extends Transformer {

	@SuppressWarnings("unchecked")
	@Override
	public Object instantiate(
			final ObjectBinder context, 
			final Object value, 
			final Type targetType, 
			@SuppressWarnings("rawtypes") final Class targetClass // Because of the interface we implement
	) {
		if (!targetClass.equals(Container.class))
			throw new IllegalArgumentException("The container transformer only transforms containers, not "+targetClass.getName());
		
		if (!(value instanceof Map<?, ?>))
			throw new IllegalArgumentException("Invalid instantiation type");
		
		final Map<?, ?> map = (Map<?, ?>) value;
		final Container c = new Container((String) map.get("name"));
		if (map.get("uuid") != null)
			c.setUuid(UUID.fromString((String) map.get("uuid")));
		
		final List<Item> contents = new LinkedList<Item>();
		for (final Object o : (Iterable<Object>) map.get("contents"))
			contents.add((Item) new RawDeserilizer().instantiate(context, o, null, null));
		c.setItems(contents);
		
		if (((Map<?,?>) map.get("links")).get("parent") == null)
			c.setRoot(true);
		
		return c;
	}

	@Override
	public void transform(final Object object) {
		if (!(object instanceof Container))
			throw new IllegalArgumentException("Object must be a container, was given: " + object);
		
		final Container container = (Container) object;
		
		getContext().writeOpenObject();
			getContext().writeName("type");
			getContext().writeQuoted("container");
			getContext().writeComma();
			
			writeItemElements(container);
			
			getContext().writeName("contents");
			getContext().writeOpenArray();
				for (int i=0; i<container.getItems().size(); i++) {
					getContext().transform(container.getItems().get(i));
					
					if (i != (container.getItems().size()-1))
						getContext().writeComma();
				}
			getContext().writeCloseArray();
			getContext().writeComma();
			
			getContext().writeName("links");
			getContext().writeOpenObject();
				getContext().writeName("self");
				getContext().writeQuoted(String.format("%s/Container/%s", 
						serilizationProperties.getProperty("http.prefix"), container.getUuid().toString()));
				getContext().writeComma();
				
				getContext().writeName("update");
				getContext().writeQuoted(String.format("%s/Container/%s", 
						serilizationProperties.getProperty("http.prefix"), container.getUuid().toString()));
				getContext().writeComma();
				
				getContext().writeName("add");
				getContext().writeQuoted(String.format("%s/Container/%s", 
						serilizationProperties.getProperty("http.prefix"), container.getUuid().toString()));
					
				if (!container.isRoot()) {
					getContext().writeComma();
				
					getContext().writeName("delete");
					getContext().writeQuoted(String.format("%s/Container/%s", 

							serilizationProperties.getProperty("http.prefix"), container.getUuid().toString()));
				}
			getContext().writeCloseObject();
		getContext().writeCloseObject();
	}

}
