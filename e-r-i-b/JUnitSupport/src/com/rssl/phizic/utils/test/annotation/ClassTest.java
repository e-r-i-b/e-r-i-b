package com.rssl.phizic.utils.test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *
 *  ласс предназначен дл€ указани€ какие тесты запускать, а какие игнорировать.
 * 
 * User: Balovtsev
 * Date: 21.02.2012
 * Time: 9:48:30
 */
@Target(java.lang.annotation.ElementType.TYPE)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ClassTest
{

	/**
	 *
	 * ѕо-умолчанию тест будет выполн€тьс€. ƒл€ каждого теста, который необходимо не нужно выполн€ть
	 * параметр нужно выставить в true.
	 *
	 * @return boolean
	 */
	boolean exclude() default false;       

	/**
	 *
	 * —писок конфигураций дл€ которых выполнение теста запрещено. ¬ случае, когда параметр exclude выставлен
	 * в true этот параметр игнорируетс€.
	 *
	 * @return String[]
	 */
	String[] configurations() default {};

	/**
	 *
	 * ”станавливает по какому принципу из очереди выполнени€ исключаютс€ методы класса.
	 * ѕо-умолчанию все методы включены в очередь.
	 *
	 * @return TestExcludeType
	 */
	TestExcludeType excludeType() default TestExcludeType.DETERMINED_BY_METHOD_ANNOTATION;

	/**
	 *
	 * ѕозвол€ет задать зависимость запуска теста от других тестов, при этом пор€док в котором они указаны не играет
	 * роли. ќн будет выполн€тьс€ после тестов указанных в данном параметре. »збегайте цикличности! 
	 *
 	 * @return Class[]
	 */
	Class[] dependencies() default {};
}
