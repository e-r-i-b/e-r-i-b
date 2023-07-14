package com.rssl.phizic.web.util;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 19.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class CollectionFunctions
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	/**
	 * ���������, ���������� �� ��������� ������ � ���������
	 * @param collection
	 * @param o
	 * @return true, ���� ������ ���������� � ���������
	 */
	public static boolean contains(Collection collection, Object o)
	{
		try
		{
			return collection.contains(o);
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * ���������, ���������� �� ��������� ������ � �������
	 * @param array ������
	 * @param objectToFind ������ ��� ������
	 * @return true, ���� ������ ���������� � ���������
	 */
	public static boolean containsInArray(Object[] array, Object objectToFind)
	{
		try
		{
			return ArrayUtils.contains(array, objectToFind);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������� �������.", e);
			return false;
		}
	}

	/**
	 * ���������� ��� �� ��������� ������������� ��������� ���������
	 * @param collection
	 * @return
	 */
	public static Set<String> toStringSet(Collection collection)
	{
		try
		{
			if (CollectionUtils.isEmpty(collection))
				return Collections.emptySet();

			Set<String> set = new LinkedHashSet<String>(collection.size());
			for (Object o : collection)
				set.add(o.toString());
			return set;
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
			return Collections.emptySet();
		}
	}

	/**
	 * ���������� �� � ���� �������� ����
	 * @param map ���
	 * @param key ����
	 * @return true - ����������
	 */
	public static boolean containsKey(Map map, Object key)
	{
		try
		{
			return map.containsKey(key);
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
