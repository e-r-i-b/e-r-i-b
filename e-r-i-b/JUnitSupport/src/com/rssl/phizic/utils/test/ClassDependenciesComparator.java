package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.ClassTest;

import java.util.Comparator;

/**
 * User: Balovtsev
 * Date: 06.04.2012
 * Time: 14:51:10
 *
 * Сортирует тесты в наборе в соответствии с зависимостями указанными в аннотации.
 *
 */
public class ClassDependenciesComparator implements Comparator<Class<?>>
{
	public int compare(Class<?> o1, Class<?> o2)
	{
		/*
		 * Получаем из аннотации к классу список зависимостей. В случае если аннотации нет, никуда текущий тест
		 * не двигаем.
		 */
		ClassTest annotation = o1.getAnnotation(ClassTest.class);
		if( annotation == null )
		{
			 return 0;
		}

		/*
		 * Проходим по зависимостям и если класс о2 входит туда, то о1 передвигается в очереди выполнения тестов
		 * ниже него.
		 */
		for (Class dependency : annotation.dependencies())
		{
			if (dependency.getName().equals( o2.getName() ))
			{
				return 1;
			}
		}

		return 0;
	}
}
