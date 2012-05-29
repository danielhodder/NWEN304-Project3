package nz.ac.victoria.ecs.nwen304.project3.server.guice;

import com.google.inject.Injector;

import nz.ac.victoria.ecs.nwen304.project3.guice.GuiceInjector;

public aspect ServetContextGuiceInjector extends GuiceInjector {
	@Override
	protected Injector getInjector() {
		return GuiceServletContextListner.getInjector();
	}
}
