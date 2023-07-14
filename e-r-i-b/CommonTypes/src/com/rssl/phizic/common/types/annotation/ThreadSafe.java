package com.rssl.phizic.common.types.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Erkin
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Такой аннотацией помечаются потоко-безопасные классы
 */
@Target({TYPE})
@Retention(SOURCE)
public @interface ThreadSafe
{
}
