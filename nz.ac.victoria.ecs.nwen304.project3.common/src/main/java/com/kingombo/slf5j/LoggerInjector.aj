package com.kingombo.slf5j;

import java.lang.reflect.Field;

public final privileged aspect LoggerInjector {
	private pointcut injectLogger() : execution(InjectLogger+.new(..));
	before(InjectLogger il) : target(il) && injectLogger() {
		try {
			for (Field f : il.getClass().getDeclaredFields()) {
				if (f.getType() == Logger.class && !f.isAnnotationPresent(DontFill.class)) {
					f.setAccessible(true);
					f.set(il, LoggerFactory.getLogger(il.getClass()));
					f.setAccessible(false);
				}
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public interface InjectLogger {}
	declare parents: (!hasfield(@DontFill Logger *) && hasfield(Logger *)) implements InjectLogger;
}
