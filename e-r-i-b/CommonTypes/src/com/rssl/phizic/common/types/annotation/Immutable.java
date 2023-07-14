package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Erkin
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Такой аннотацией помечаются классы объектов, не меняющих своё состояние всю жизнь
 */
@Inherited
@Target({TYPE})
@Retention(SOURCE)
public @interface Immutable
{
}
