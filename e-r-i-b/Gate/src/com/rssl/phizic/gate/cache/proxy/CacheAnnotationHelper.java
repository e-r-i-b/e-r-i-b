package com.rssl.phizic.gate.cache.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class CacheAnnotationHelper
{
	/**
	 * Ищет метод с аннотацией Cachable среди методов интерфеса владельца метода
	 * @param method
	 * @return null или найденная аннтоация
	 */
	public static Method getInterfaceMethodByMethod(Method method)
	{
		Class methodClass = method.getDeclaringClass();
		Class[] interfaces = getInheritanceInterfaces(methodClass);
		for (Class anInterface : interfaces)
		{
			try
			{
				Method interfaceMethod = anInterface.getMethod(method.getName(),method.getParameterTypes());
				Cachable result = interfaceMethod.getAnnotation(Cachable.class);
				if(result!=null)
					return interfaceMethod;

			}
			catch (NoSuchMethodException ex)
			{
				//ничего не делаем, ищем дальше
			}
		}
		return null;
	}

	/**
	 * Ищет метод с аннотацией Cachable среди методов интерфеса владельца метода и самого метода.
	 * @param method метод.
	 * @return null или найденная аннотация.
	 */
	public static Method getCachableMethodByMethod(Method method)
	{
		Cachable result = method.getAnnotation(Cachable.class);
		if (result == null)
			return getInterfaceMethodByMethod(method);
		return method;
	}

	/**
	 * получаем все интерфейсы для переданного класса и всех его суперклассов
	 * @param methodClass  - класс, для которого ищем интерфейсы
	 * @return возвращает массив классов, определяющих интерфейсы
	 * если интерфейсов нет, то возвращает пустой массив
	 */
	private static Class[] getInheritanceInterfaces(Class methodClass)
	{
		List<Class> interfaces = new ArrayList<Class>();

		do
		{
			Class[] methodClassInterfaces = methodClass.getInterfaces();    // получаем список интерфейсов, определенных для этого класса (интерфейсы супер классов не приходят)
			Collections.addAll(interfaces, methodClassInterfaces);          // сохраняем все в список
			methodClass = methodClass.getSuperclass();    // ищем суперкласс
		}
		while(methodClass != null);       // если null, то суперклассов нет (дошли до Object)

		return interfaces.toArray( new Class[interfaces.size()] );  // возвращаем список интерфейсов для переданного класса в виде массива
	}
}
