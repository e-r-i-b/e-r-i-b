package com.rssl.phizic.utils.test.annotation;

import java.lang.annotation.*;

/**
 * @author Roshka
 * @ created 21.11.2006
 * @ $Author$
 * @ $Revision$
 */

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IncludeTest
{
	/**
	 * ¬ключаетс€ только в перечисленные конфигурации,
	 * если ничегоне указано игнорируетс€.
	 * @return конфигурации
	 */
	String[] configurations() default {};
}
