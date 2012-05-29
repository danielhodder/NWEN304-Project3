package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.lang.reflect.Type;
import java.util.Map;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class ExceptionTransformer extends AbstractTransformer implements ObjectFactory {

	@Override
	public void transform(final Object object) {
		if (!(object instanceof Exception))
			throw new IllegalArgumentException("Object is not an exception");
		
		final Exception e = (Exception) object;
		
		getContext().writeOpenObject();
			getContext().writeName("type");
			getContext().writeQuoted("exception");
			getContext().writeComma();
			
			getContext().writeName("name");
			getContext().writeQuoted(e.getClass().getName());
			
			if (e.getMessage() != null) {
				getContext().writeComma();
				getContext().writeName("message");
				getContext().writeQuoted(e.getMessage());
			}
		getContext().writeCloseObject();
	}

	@Override
	public Object instantiate(final ObjectBinder context, final Object value,
			final Type targetType, @SuppressWarnings("rawtypes") final Class targetClass) {
		if (!(value instanceof Map<?, ?>))
			throw new IllegalArgumentException("Invalid instantiation type");
		final Map<?, ?> map = (Map<?, ?>) value;
		try {
			if (map.get("message") != null)
				return Class.forName((String) map.get("name")).getDeclaredConstructor(String.class).newInstance(map.get("message"));
			else
				return Class.forName((String) map.get("name")).getDeclaredConstructor().newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
