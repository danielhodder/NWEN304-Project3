package nz.ac.victoria.ecs.nwen304.project3.guice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks that all injected members of a class should be nulled after this method is called
 * 
 * @author danielh
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnInjectAfterCall {
}
