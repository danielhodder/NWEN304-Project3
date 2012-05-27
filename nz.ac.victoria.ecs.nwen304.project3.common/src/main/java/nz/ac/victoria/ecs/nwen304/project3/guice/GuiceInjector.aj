package nz.ac.victoria.ecs.nwen304.project3.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;

public abstract privileged aspect GuiceInjector {
	private pointcut injectOnFieldGet() : this(Object) && get(@Inject * (@InjectObject *).*);
	private pointcut injectOnMethodCall() : this(Object) && execution(@InjectOnCall * *(..));
	private pointcut injectOnConstructorCall() : this(Object) && execution(@InjectOnCall new(..));
	
	before() : injectOnFieldGet() || 
	           injectOnMethodCall() ||
	           injectOnConstructorCall() {
		// Is the object already injected
		if (false)
			return;
		
		this.getInjector().injectMembers(thisJoinPoint.getThis());
	}
	
	protected abstract Injector getInjector();
}
