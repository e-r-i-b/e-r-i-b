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
public @interface ExcludeTest
{
	/**
	 * Если не указан набор конфигураций, то тест
	 * не будет выполняться ни для одной
	 * @return конфигурации
	 */
	String[] configurations() default {};
}
