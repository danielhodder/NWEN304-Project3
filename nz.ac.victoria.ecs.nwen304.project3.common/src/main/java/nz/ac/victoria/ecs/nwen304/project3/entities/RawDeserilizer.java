package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.lang.reflect.Type;
import java.util.Map;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;

public class RawDeserilizer implements ObjectFactory {

	@Override
	public Object instantiate(final ObjectBinder context, final Object value,
			final Type targetType, @SuppressWarnings("rawtypes") final Class targetClass) {
		if (!(value instanceof Map<?, ?>))
			throw new IllegalArgumentException("Invalid instantiation type");
		
		final Map<?, ?> map = (Map<?, ?>) value;
		
		if (map.get("type").equals("note"))
			return new NoteTransformer().instantiate(context, map, targetType, Note.class);
		else if (map.get("type").equals("container"))
			return new ContainerTransformer().instantiate(context, value, targetType, Container.class);
		else if (map.get("type").equals("excaption"))
			throw new RuntimeException((Exception) new ExceptionTransformer().instantiate(context, value, targetType, Exception.class));
		else
			throw new IllegalArgumentException("Unknown entity type");
	}

}
