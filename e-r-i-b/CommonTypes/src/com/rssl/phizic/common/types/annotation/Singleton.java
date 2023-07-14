package com.rssl.phizic.common.types.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Target;

/**
 * @author Erkin
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Такой аннотацией помечаются классы синглтонов
 */
@Target({TYPE})
@Retention(SOURCE)
public @interface Singleton
{
}
