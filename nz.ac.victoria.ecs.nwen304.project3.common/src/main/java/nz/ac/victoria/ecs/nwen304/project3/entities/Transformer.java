package nz.ac.victoria.ecs.nwen304.project3.entities;

import java.io.IOException;
import java.util.Properties;

import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

abstract class Transformer extends AbstractTransformer implements ObjectFactory {
	protected void writeItemElements(final Item i) {
		getContext().writeName("uuid");
		getContext().writeQuoted(i.getUuid().toString());
		getContext().writeComma();
		
		getContext().writeName("name");
		getContext().writeQuoted(i.getName());
		getContext().writeComma();
	}
	
	protected static final Properties serilizationProperties;
	static {
		serilizationProperties = new Properties();
		try {
			serilizationProperties.load(Transformer.class.getClassLoader().getResourceAsStream("serilization.properties"));
		} catch (final IOException e) {
			throw new RuntimeException("Could not load serilization properties", e);
		}
	}
}
