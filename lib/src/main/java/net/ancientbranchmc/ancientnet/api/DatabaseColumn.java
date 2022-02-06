package net.ancientbranchmc.ancientnet.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseColumn {
	String name() default "";
	DatabaseDataType type() default DatabaseDataType.String;
}
