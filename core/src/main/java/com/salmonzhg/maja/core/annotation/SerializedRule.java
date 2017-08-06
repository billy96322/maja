package com.salmonzhg.maja.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: Salmon
 * date: 2017-08-01 10:10
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface SerializedRule {
    int start();
    int length();
    int strategyIndex() default -1000;
}
