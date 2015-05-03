package com.quantasnet.qlan2.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * Created by andrewlandsverk on 5/2/15.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@PreAuthorize("hasRole('ROLE_USER')")
public @interface HasUserRole {
}
