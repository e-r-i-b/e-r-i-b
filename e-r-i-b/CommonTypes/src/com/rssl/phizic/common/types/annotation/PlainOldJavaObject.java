package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Erkin
 * @ created 06.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Такой аннотацией помечаются классы POJO
 */
@Target({TYPE})
@Retention(SOURCE)
public @interface PlainOldJavaObject
{
}
