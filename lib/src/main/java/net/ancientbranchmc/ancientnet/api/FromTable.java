package net.ancientbranchmc.ancientnet.api;

public @interface FromTable {
    public String database() default "";
    public String table() default "";
    public String primaryKey() default "";
}
