package nz.ac.victoria.ecs.nwen304.project3.server.actions;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public abstract class AbstractActionBean implements ActionBean {
	private ActionBeanContext context;
	
	@Override
	public void setContext(final ActionBeanContext context) {
		this.context = context;
	}

	@Override
	public ActionBeanContext getContext() {
		return this.context;
	}

}
