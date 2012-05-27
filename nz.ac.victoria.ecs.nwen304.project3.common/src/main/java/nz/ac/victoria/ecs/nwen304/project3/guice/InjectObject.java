package nz.ac.victoria.ecs.nwen304.project3.guice;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.annotation.Retention;

import java.lang.annotation.Documented;

/**
 * Marks that an object should have it's dependencies injected.
 * 
 * @author danielh
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectObject {
}