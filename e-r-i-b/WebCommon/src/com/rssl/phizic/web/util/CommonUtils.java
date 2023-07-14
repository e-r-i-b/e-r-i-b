package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 31.05.2007
 * @ $Author: krenev_a $
 * @ $Revision: 51851 $
 */

public class CommonUtils
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	/**
	 * Сортировка, из списка создаятся новый отсортированный массив
	 * Функцию можно использовать для сортировки на JSP (через phiz:sort(,))
	 * @param list список для сортировки
	 * @param comparator компаратор
	 * @return отсортированный массив
	 * (массив потому, что на JSP нет разницы массив или List но отсутствуют лишние телодвижения)
	 */
	public static Object[] sort(List list, Comparator comparator)
	{
		try
		{
			// выдран кусок Collections.sort(,);
			Object[] a = list.toArray();
			Arrays.sort(a, comparator);
			return a;
		}
		catch (Exception e)
		{
			log.error("Ошибка сортировки списка", e);
			return null;
		}
	}

	/**
	 * @param collection
	 * @return размер коллекции
	 */
	public static int size(Collection collection)
	{
		try
		{
			if (collection == null) return 0;
			return collection.size();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения размера коллекции", e);
			return 0;
		}		
	}

	/**
	 * @param map мапа
	 * @return размер мапы
	 */
	public static int size(Map map)
	{
		try
		{
			if (map == null) return 0;
			return map.size();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения размера мапы", e);
			return 0;
		}
	}

	public static Boolean isInstance(Object obj, String className) throws BusinessException
	{
		try
		{
			return Class.forName(className).isInstance(obj);
		}
		catch (ClassNotFoundException e)
		{
			log.error("Не найден класс "+className, e);
			return null;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения принадлежности объекта к классу", e);
			return null;
		}
	}
			
}