package com.crealmcode.effectrun.state;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by TheCrealm on 18.10.2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface StateDescription {

    public String name();

}
