package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import flexjson.transformer.AbstractTransformer;

class ExceptionTransformer extends AbstractTransformer {

	@Override
	public void transform(Object object) {
		if (!(object instanceof Exception))
			throw new IllegalArgumentException("Object is not an exception");
		
		Exception e = (Exception) object;
		
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

}
