package com.quantasnet.qlan2.layout;

import java.lang.annotation.*;

/**
 * Created by andrewlandsverk on 4/8/15.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String value() default "";
}
