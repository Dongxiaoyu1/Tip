package com.soft.yozo.myrecode.db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn
{
    public enum  Types
    {
        INTEGER,TEXT,BLOB,DATETIME
    }

    Types type() default  Types.TEXT;

    boolean isPrimary() default  false;
    boolean isIndex() default false;
    boolean isNotNull() default  false;
    boolean isUnique() default  false;
}
