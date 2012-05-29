package nz.ac.victoria.ecs.nwen304.project3.common.guice;

import org.junit.Ignore;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import nz.ac.victoria.ecs.nwen304.project3.guice.GuiceInjector;

@Ignore
aspect TestGuiceInjector extends GuiceInjector pertypewithin(nz.ac.victoria.ecs.nwen304.project3.common.guice.*) {
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Boolean.class).toInstance(Boolean.TRUE);
			}
		});
	}
}
