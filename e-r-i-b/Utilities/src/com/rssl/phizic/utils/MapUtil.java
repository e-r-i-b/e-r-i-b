package com.rssl.phizic.utils;

import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * @author Roshka
 * @ created 15.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class MapUtil
{
	/**
	 * Форматирование map в строку.
	 * @param map
	 * @param keyValueDelimiter символ-разделитель между ключом и значением
	 * @param entryDelimiter символ-разделитель между парами <ключ,значение>
	 */
	public static String format(Map map, String keyValueDelimiter, String entryDelimiter)
	{
		StringBuilder buf = new StringBuilder();

		for (Object o : map.entrySet())
		{
			Map.Entry entry = (Map.Entry) o;
			buf.append(entry.getKey());
			buf.append(keyValueDelimiter);
			buf.append(StringHelper.getEmptyIfNull(entry.getValue()));
			buf.append(entryDelimiter);
		}
		return buf.toString();
	}

	/**
	 * Распарсить строку в мар
	 * @param parameters строка значений
	 * @param keyValueDelimiter символ-разделитель между ключом и значением
	 * @param entryDelimiter символ-разделитель между парами <ключ,значение>
	 * @return мап значений
	 */
	public static Map<String, String> parse(String parameters, String keyValueDelimiter, String entryDelimiter)
	{
		Map<String, String> result = new TreeMap<String, String>();
		if (parameters == null)
		{
			return result;
		}
		StringTokenizer nameValuePairTokenizer = new StringTokenizer(parameters, entryDelimiter);

		while (nameValuePairTokenizer.hasMoreTokens())
		{
			String nameValuePair = nameValuePairTokenizer.nextToken();
			StringTokenizer valueTokenizer = new StringTokenizer(nameValuePair, keyValueDelimiter);

			String name = valueTokenizer.nextToken();
			String value = valueTokenizer.hasMoreElements()?valueTokenizer.nextToken():null;

			result.put(name, value);
		}
		return result;
	}

		/**
	 * Распарсить строку в Map<String, List<String>> (один ключ - несколько значений)
	 * @param parameters строка значений
	 * @param keyValueDelimiter символ-разделитель между ключом и значением
	 * @param entryDelimiter символ-разделитель между парами <ключ,значение>
	 * @return мап значений
	 */
	public static Map<String, List<String>> parseMultiMap(String parameters, String keyValueDelimiter, String entryDelimiter)
	{
		if (StringHelper.isEmpty(parameters))
		{
			return Collections.emptyMap();
		}
		StringTokenizer nameValuePairTokenizer = new StringTokenizer(parameters, entryDelimiter);

		Map<String, List<String>> result = new HashMap<String, List<String>>();
		while (nameValuePairTokenizer.hasMoreTokens())
		{
			String nameValuePair = nameValuePairTokenizer.nextToken();
			StringTokenizer valueTokenizer = new StringTokenizer(nameValuePair, keyValueDelimiter);
			String name = valueTokenizer.nextToken();
			String value = valueTokenizer.hasMoreElements()?valueTokenizer.nextToken():null;
			List<String> mapValue;
			if(result.containsKey(name))
				mapValue = result.get(name);
			else
				mapValue = new LinkedList<String>();
			mapValue.add(value);
			result.put(name, mapValue);
		}
		return result;
	}

	/**
	 * Возвращает суб-мап по ключам
	 * @param map - мап, из которого строится суб-мап
	 * @param keys - коллекция ключей
	 * @return суб-мап
	 */
	public static <K, V> Map<K, V> submap(Map<K, V> map, Collection<K> keys)
	{
		Map<K, V> submap = new LinkedHashMap<K,V>(Math.min(map.size(), keys.size()));
		for (K key : keys)
		{
			V value = map.get(key);
			if (value != null)
				submap.put(key, value);
		}
		return submap;
	}

	/**
	 * Ищет в мапе значения по ключам
	 * @param map - мап
	 * @param keys - коллекция ключей
	 * @return список знайденных значений
	 */
	public static <K, V> List<V> lookup(Map<K, V> map, Collection<K> keys)
	{
		List<V> list = new ArrayList<V>(Math.min(map.size(), keys.size()));
		for (K key : keys)
		{
			V value = map.get(key);
			if (value != null)
				list.add(value);
		}
		return list;
	}

	/**
	 * Сортирует мап по значениям
	 * @param map - мап для сортировки
	 * @return отсортированный мап
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map)
	{
		List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>()
		{
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
			{
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static Map getMap(String key, Object value)
	{
		Map<String, Object> result = new HashMap<String, Object>(1);
		MapUtils.safeAddToMap(result, key, value);
		return result;
	}
}