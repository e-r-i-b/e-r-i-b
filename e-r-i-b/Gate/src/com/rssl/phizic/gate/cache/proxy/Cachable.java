package com.rssl.phizic.gate.cache.proxy;

import java.lang.annotation.*;

/**
 * Аннотацией для пометки методов, результаты которых необходимо кешировать.
 * При этом аннотации на методых с void будут игнорироваться.
 *
 * В данной аннотации содержатся значения параметров по умолчанию.
 * Реальные значения берутся из файла ehcache.xml.
 *
 * @author Omeliyanchuk
 * @ created 29.04.2010
 * @ $Author$
 * @ $Revision$
 * @see com.rssl.phizic.gate.cache.CacheServiceCreator
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Cachable
{
	/**
	 *
	 * @return имя кэша, хранящееся в erib-ehcache.xml.
	 */
	String name();
	//класс для вычисления ключа
	Class<? extends CacheKeyComposer> keyResolver();
	//параметры для настройки композера, например порядковый номер ключевого поля
	String resolverParams() default "";
	//максимальное кол-во элементов хранящихся в памяти
	int maxElementsInMemory() default 200;
	//время без обращений, после которого элемент будет удален
	int timeToIdleSeconds() default 900;
	//общее время жизни
	int timeToLiveSeconds() default 900;
	//можно ли использовать результат операции в других операциях
	boolean linkable() default true;
	//кешировать ли результат если Value == null. Использовать только при острой необходимости!
	boolean cachingWithNullValue() default false;
	//отказывать ли повторный вызов метода, пока не сформировался кеш. Использовать только для методов с длительным откликом.
	boolean cachingWithWaitInvoke() default false;
}
