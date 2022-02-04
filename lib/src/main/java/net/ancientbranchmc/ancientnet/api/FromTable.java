package net.ancientbranchmc.ancientnet.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FromTable {
    public String database() default "";
    public String table() default "";
    public String primaryKey() default "";
}
