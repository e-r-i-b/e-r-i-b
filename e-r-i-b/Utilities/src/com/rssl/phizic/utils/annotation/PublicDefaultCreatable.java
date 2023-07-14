package com.rssl.phizic.utils.annotation;

import java.lang.annotation.*;

/**
 * Классы помеченные этим атрибутом должны быть public и иметь public конструктор без параметров
 * @author Evgrafov
 * @ created 31.03.2006
 * @ $Author: Roshka $
 * @ $Revision: 3671 $
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PublicDefaultCreatable
{
	/**
	 * Включается только в перечисленные конфигурации,
	 * если ничегоне указано игнорируется.
	 * @return конфигурации
	 */
	String[] configurations() default {};
}
