package com.evostar.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token{
    String value() default "avoid";
}
