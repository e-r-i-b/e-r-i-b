package com.rssl.phizic.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 11.10.2005
 * Time: 15:49:10
 */
public final class ListUtil
{
    private ListUtil()
    {
    }

	/**
	 * ���������� ��������� � ���� ������
	 * @param collection1 - ��������� ��� (can be null, can be empty)
	 * @param collection2 - ��������� ��� (can be null, can be empty)
	 * @return ��������� ����������� (never null, can be empty)
	 */
	public static <T> List<T> combine(Collection<T> collection1, Collection<T> collection2)
	{
		if (CollectionUtils.isEmpty(collection1) && CollectionUtils.isEmpty(collection2))
			return Collections.emptyList();

		List<T> result = new ArrayList<T>(CollectionUtils.size(collection1) + CollectionUtils.size(collection2));
		result.addAll(collection1);
		result.addAll(collection2);
		return result;
	}

    public static <T> List<T> intersect(List<T> fList, List<T> sList)
    {
        List<T> results = new ArrayList<T>();
        for (int i = 0; i < fList.size(); i++)
        {
            T o = fList.get(i);
            if (sList.contains(o))
                results.add(o);
        }
        return results;
    }

	public static <T> List<T> fromArray(T[] array)
	{
		List<T> list = new ArrayList<T>();
		for (T t : array)
		{
			list.add(t);
		}
		return list;
	}

	/**
	 * �������� ������ �� �������
	 * @param <T> - ��� �������� ������
	 * @param list - ������
	 * @param limit - ����������� �� ������ ������
	 * @return ���������� ������
	 */
	public static <T> List<T> truncateList(List<T> list, int limit)
	{
		if (list == null)
			throw new NullPointerException("Argument 'list' cannot be null");
		if (limit < 0)
			throw new IllegalArgumentException("Argument 'limit' must be positive or zero");
		if (list.size() > limit)
			return list.subList(0, limit);
		else return list;
	}

	/**
	 * ���������� ������ ������� � ���� ������
	 * @param <T> - ��� �������� ������
	 * @param lists - ������ ������
	 * @return ����������� �������
	 */
	public static <T> List<T> join(Collection<List<T>> lists)
	{
		if (lists.isEmpty())
			return Collections.emptyList();

		List<T> result = new LinkedList<T>();
		for (List<T> list : lists)
			result.addAll(list);
		return result;
	}

	/**
	 * ��������� ������ � ������� ����������� ���������
	 * @param list - ������ (never null)
	 * @param comparator - ���������� (never null)
	 */
	public static <T> void sortMinToMax(List<T> list, Comparator<T> comparator)
	{
		Collections.sort(list, comparator);
	}

	/**
	 * ��������� ������ � ������� �������� ���������
	 * @param list - ������ (never null)
	 * @param comparator - ���������� (never null)
	 */
	public static <T> void sortMaxToMin(List<T> list, Comparator<T> comparator)
	{
		Collections.sort(list, Collections.reverseOrder(comparator));
	}

	/**
	 * ��������� ������ � ������, ���� ������ �� ������
	 * @param stringList - ������ ����� (never null)
	 * @param string - ������ (can be null can be empty)
	 */
	public static void addIfNotEmpty(List<String> stringList, String string)
	{
		if (!StringHelper.isEmpty(string))
			stringList.add(string);
	}

	/**
	 * ����� ������ �� �����, �� ����������� ��������� ������
	 * @param list - ������ (never null can be empty)
	 * @param partSize - ������������ ������ ����� (������ ���� ������ 1)
	 * @return ������ ����������� ������ ��� ������ ������, ���� �������� ������ ����
	 */
	public static <T> List<List<T>> split(List<T> list, int partSize)
	{
		if (list == null)
		    throw new IllegalArgumentException("list �� ����� ���� null");

		if (partSize < 1)
		    throw new IllegalArgumentException("partSize �� ����� ���� ������ �������: " + partSize);

		if (list.isEmpty())
			return Collections.emptyList();

		int listSize = list.size();
		if (partSize >= listSize)
			return Collections.singletonList(list);

		List<List<T>> parts = new LinkedList<List<T>>();
		int begin = 0;
		while (begin < listSize)
		{
			int end = Math.min(begin + partSize, listSize);
			parts.add(list.subList(begin, end));
			begin = end;
		}
		return parts;
	}
}
