package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.LogicException;

import java.util.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Erkin
 * @ created 29.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хелпер для GroupResult
 */
public class GroupResultHelper
{
	/**
	 * Собирает все значения груп-результа в один список
	 * (удобно для случаев, когда в качестве значений используются коллекции)
	 * @param groupResult груп-результ
	 * @param <K> тип ключей груп-результа
	 * @param <V> тип значений груп-результа
	 * @return список со значениями (возможны повторения)
	 */
	public static <K, V> List<V> joinValues(GroupResult<K, ? extends Collection<V>> groupResult)
	{
		if (groupResult == null)
			throw new NullPointerException("Argument 'groupResult' cannot be null");
		List<V> list = new LinkedList<V>();
		for (Map.Entry<K, ? extends Collection<V>> entry : groupResult.getResults().entrySet())
			list.addAll(entry.getValue());
		return list;
	}
	
	/**
	 * Собирает все значения груп-результа в один список
	 * @param groupResult груп-результ
	 * @param <K> тип ключей груп-результа
	 * @param <V> тип значений груп-результа
	 * @return список со значениями (возможны повторения)
	 */
	public static <K, V> List<V> getResults(GroupResult<K,V> groupResult)
	{
		if (groupResult == null)
			throw new NullPointerException("Argument 'groupResult' cannot be null");
		List<V> list = new LinkedList<V>();
		for (Map.Entry<K,V> entry : groupResult.getResults().entrySet())
			list.add(entry.getValue());
		return list;
	}

	/**
	 * Аггрегация exception'ов в String
	 * @param groupResult груп-результ
	 * @param <K> тип ключей груп-результа
	 * @param <V> тип значений груп-результа
	 * @return значение 1ого результата
	 */
	public static <K,V> String getErrorMessage(GroupResult<K,V> groupResult)
	{
		String errorMessage = new String();
		for (Exception ex: groupResult.getExceptions().values())
		{
			errorMessage+=ex.toString();
			errorMessage+=System.getProperty(System.getProperty("line.separator"));
		}
		return errorMessage;
	}

	/**
	 * Возвращает первый результат в группе
	 * (удобно для случаев, когда заранее известно, что должен вернуться 1 результат)
	 * @param groupResult груп-результ
	 * @param <K> тип ключей груп-результа
	 * @param <V> тип значений груп-результа
	 * @return значение 1ого результата
	 */
	public static <K, V> V getOneResult(GroupResult<K,V> groupResult) throws SystemException, LogicException
	{
		if (groupResult == null)
			throw new NullPointerException("Argument 'groupResult' cannot be null");

		List<K> keys = groupResult.getKeys();
		return (!keys.isEmpty()) ? getResult(groupResult, keys.get(0)) : null;
	}

	/**
	 * Получить результат из группы, если null - кинуть exception из группы
	 * Если по ключу не найден ни результат, ни исключение, возвращается null (шина ничего не нашла)
	 * @param groupResult группа
	 * @param key - ключ группы
	 * @exception SystemException, LogicException
	 * @return V
	 * */
	public static<K,V> V getResult(GroupResult<K,V> groupResult, K key) throws SystemException, LogicException
	{
		if (groupResult == null)
			throw new NullPointerException("Argument 'groupResult' cannot be null");

		V result = groupResult.getResult(key);

		if (result != null)
			return result;

		try
		{
			IKFLException e = groupResult.getException(key);
			if (e!=null)
			{
				if (e.getType()!=null)
				{
					try{
						throw e.getType().getConstructor(String.class, Throwable.class).newInstance(e.getMessage(), e.getCause());
					}
					catch (NoSuchMethodException ex)
					{
						throw e;
					}
					catch (InstantiationException ex)
					{
						throw e;
					}
					catch (InvocationTargetException ex)
					{
						throw e;
					}
					catch (IllegalAccessException ex)
					{
						throw e;
					}
				}
				throw e;
			}
			else
			{
				//если результат null и нет исключений по ключу, значит шина значение не нашла
				return null;
			}
		}
		catch (SystemException se)
		{
			throw se;
		}
		catch (LogicException le)
		{
			throw le;
		}
		catch (IKFLException e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Возвращает результат заполненый одной ошибкой для всех ключей
	 * @param keys набор ключей
	 * @param e ошибки
	 * @return
	 */
	public static <K, V> GroupResult<K, V> getOneErrorResult(K[] keys, IKFLException e)
	{
		GroupResult<K, V> result = new GroupResult<K, V>();
        result.setOneError(true);
		for (K key: keys)
        {
			result.putException(key, e);
        }
		return result;
	}

	/**
	 * Выкинуть первое попавшееся исключение, если есть хотя бы одно
	 * @param groupResult груп-результ
	 * @throws IKFLException
	 */
	public static <K, V> void checkAndThrowAnyException(GroupResult<K, V> groupResult) throws IKFLException
	{
		Map<K, IKFLException> exceptions = groupResult.getExceptions();
		if (!exceptions.isEmpty())
		{
			throw exceptions.entrySet().iterator().next().getValue();
		}
	}
}
