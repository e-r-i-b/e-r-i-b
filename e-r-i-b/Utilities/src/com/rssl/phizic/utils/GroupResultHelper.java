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
 * ������ ��� GroupResult
 */
public class GroupResultHelper
{
	/**
	 * �������� ��� �������� ����-�������� � ���� ������
	 * (������ ��� �������, ����� � �������� �������� ������������ ���������)
	 * @param groupResult ����-�������
	 * @param <K> ��� ������ ����-��������
	 * @param <V> ��� �������� ����-��������
	 * @return ������ �� ���������� (�������� ����������)
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
	 * �������� ��� �������� ����-�������� � ���� ������
	 * @param groupResult ����-�������
	 * @param <K> ��� ������ ����-��������
	 * @param <V> ��� �������� ����-��������
	 * @return ������ �� ���������� (�������� ����������)
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
	 * ���������� exception'�� � String
	 * @param groupResult ����-�������
	 * @param <K> ��� ������ ����-��������
	 * @param <V> ��� �������� ����-��������
	 * @return �������� 1��� ����������
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
	 * ���������� ������ ��������� � ������
	 * (������ ��� �������, ����� ������� ��������, ��� ������ ��������� 1 ���������)
	 * @param groupResult ����-�������
	 * @param <K> ��� ������ ����-��������
	 * @param <V> ��� �������� ����-��������
	 * @return �������� 1��� ����������
	 */
	public static <K, V> V getOneResult(GroupResult<K,V> groupResult) throws SystemException, LogicException
	{
		if (groupResult == null)
			throw new NullPointerException("Argument 'groupResult' cannot be null");

		List<K> keys = groupResult.getKeys();
		return (!keys.isEmpty()) ? getResult(groupResult, keys.get(0)) : null;
	}

	/**
	 * �������� ��������� �� ������, ���� null - ������ exception �� ������
	 * ���� �� ����� �� ������ �� ���������, �� ����������, ������������ null (���� ������ �� �����)
	 * @param groupResult ������
	 * @param key - ���� ������
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
				//���� ��������� null � ��� ���������� �� �����, ������ ���� �������� �� �����
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
	 * ���������� ��������� ���������� ����� ������� ��� ���� ������
	 * @param keys ����� ������
	 * @param e ������
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
	 * �������� ������ ���������� ����������, ���� ���� ���� �� ����
	 * @param groupResult ����-�������
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
