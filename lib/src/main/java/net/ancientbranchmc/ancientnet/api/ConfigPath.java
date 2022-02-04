package net.ancientbranchmc.ancientnet.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPath {
    boolean absolute() default false;
    String path() default "sample.value";
}
