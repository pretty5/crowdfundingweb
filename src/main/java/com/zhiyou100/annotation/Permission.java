package com.zhiyou100.annotation;
/*权限注解*/

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String[] role();
}
