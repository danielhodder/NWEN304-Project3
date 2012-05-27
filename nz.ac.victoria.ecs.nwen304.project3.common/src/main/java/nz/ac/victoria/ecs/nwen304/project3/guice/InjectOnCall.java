package nz.ac.victoria.ecs.nwen304.project3.guice;

import java.lang.annotation.ElementType;

import java.lang.annotation.Target;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Retention;

import java.lang.annotation.Documented;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR })
public @interface InjectOnCall {
}
