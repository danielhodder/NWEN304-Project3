package nz.ac.victoria.ecs.nwen304.project3.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;

public abstract aspect GuiceInjector {
	private pointcut injectOnFieldGet() : get(@Inject * (@InjectObject *).*);
	private pointcut injectOnMethodCall() : execution(@InjectOnCall * *(..));
	private pointcut injectOnConstructorCall() : execution(@InjectOnCall new(..));
	
	before(InjectableObject io) : target(io) && (injectOnFieldGet() || 
												 injectOnMethodCall() ||
												 injectOnConstructorCall()) {
		// If the object has not already been injected inject it
		if (!io.isInjected) {
			synchronized (io.injectLock) {
				if (!io.isInjected) {
					this.getInjector().injectMembers(io);
					io.isInjected = true;
				}
			}
		}
	}
	
	protected abstract Injector getInjector();
	
	// Introduce a private field onto the object that shows if it has been injected or not.
	public interface InjectableObject {}
	declare parents:
			(@InjectObject *) ||
			hasmethod(@InjectOnCall * * (..)) ||
			hasmethod(@InjectOnCall new(..))
		implements InjectableObject;
	public volatile boolean InjectableObject.isInjected;
	public Object InjectableObject.injectLock = new Object();
}
