package com.rssl.phizic.utils.test.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 *
 * Класс предназначен для маркировки методов которые необходимо исключить из очереди выполнения тестов.
 *
 * User: Balovtsev
 * Date: 21.02.2012
 * Time: 9:56:04
 */
@Target(java.lang.annotation.ElementType.METHOD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface MethodTest
{

	/**
	 *
	 * Список конфигураций для которых выполнение метода теста запрещено.
	 *
	 * @return String[]
	 */
	String[] configurations()  default {};

	/**
	 *
	 * Исключить метод из тестирования
	 *
	 * @return boolean
	 */
	boolean  exclude() default false;
}
