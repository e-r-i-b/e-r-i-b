package com.rssl.phizic.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Kosyakov
 * @ created 03.08.2006
 * @ $Author: moshenko $
 * @ $Revision: 86050 $
 */
public class BeanHelper
{
	public static void SetPropertiesFromMap ( Object obj, Map map )
	{
		copyProperties(obj, map);
	}

	public static void SetPropertiesFromMapFull ( Object obj, Map map )
	{
		copyProperties(obj, map);
	}

	/**
	 * преобразует объект в мап, при этом все свойства преобразует в строку 
	 * @param obj
	 * @return
	 */
	public static Map createMapFromProperties ( Object obj )
	{
		Map map = null;
		try
		{
			map = BeanUtils.describe(obj);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return map;
	}

	/**
	 * преобразует объект в мап, без конвертаци свойств.
	 * @param obj
	 * @return
	 */
	 public static Map createMapFromPropertiesFull ( Object obj )
	 {
	  Map map = null;
	  try
	  {
		  PropertyUtilsBean util = new PropertyUtilsBean();
	      map = util.describe(obj);
	  }
	  catch (Exception e)
	  {
	   throw new RuntimeException(e);
	  }
	  return map;
	 }

	public static void copyProperties ( java.lang.Object dest, java.lang.Object orig )
	{
		try
		{
			BeanUtils.copyProperties(dest, orig);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void copyPropertiesFull ( java.lang.Object dest, java.lang.Object orig )
	{
		try
		{
			PropertyUtilsBean util = new PropertyUtilsBean();
		    util.copyProperties(dest, orig);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Сделать копию объекта на основе таблиц корреляции
	 * @param object копируемый объект
	 * @param corl таблица корреляции
	 * @param <O> Тип возвращаемого объекта
	 * @return копия (на основе таблиц корреляции) переданного объекта
	 * @throws Exception
	 */
	public static <O> O copyObject(Object object,  Map<Class, Class> corl) throws Exception
	{
		//noinspection unchecked
		return (O) buildObject(object, corl, null);
	}

	/**
	 * Получаем актуальный объект. Используется для элементов сложных типов: Collection, Map, Iterator
	 * @param object - объект для преобразования
	 * @param corl - таблица-соответсвия
	 * @return Обект нового типа (при необходимости) с актуальным значением
	 * @throws Exception - проблемные исключения, связанные с загрузкой и инстанцированием классов
	 */	
	private static Object buildObject(Object object,  Map<Class, Class> corl, Map<Class, BeanFormatter> enumFormaters) throws Exception
	{
		//Если объект не надо дальше раскладывать, то возвращаем просто его
		if(!checkAnalyzeNessesary(object, corl))
			return object;

		Object temp;
		Class destType = getDestType(object.getClass(), corl);
		//Если нет в таблице, то берем тип значения
		if (destType==null)
		{
			temp = object.getClass().getConstructor().newInstance();
		}
		//то берем тип из corl
		else
		{
			temp = destType.getConstructor().newInstance();
		}
		copyPropertiesWithDifferentTypes(temp, object, corl, enumFormaters);
		return temp;
	}

	/**
	 * ВНИМАНИЕ! Настроено под нужны JAX-RPC!!!
	 * Копируем свойства с разными типами параметров.
	 * Используется для преобразования объектов сервисов в наши стандартные объекты
	 * Java объекты просто копируются.
	 * @param dest - куда копируем
	 * @param orig - что копируем
	 * @param corl - таблица соответсвий сгенеренных ктипов и необходимых бизнесу типов
	 */
	public static void copyPropertiesWithDifferentTypes(java.lang.Object dest, java.lang.Object orig, Map<Class, Class> corl) throws Exception
	{
		copyPropertiesWithDifferentTypes(dest, orig, corl, null);
	}
	/**
	 * ВНИМАНИЕ! Настроено под нужны JAX-RPC!!!
	 * Копируем свойства с разными типами параметров.
	 * Используется для преобразования объектов сервисов в наши стандартные объекты
	 * Java объекты просто копируются.
	 * @param dest - куда копируем
	 * @param orig - что копируем
	 * @param corl - таблица соответсвий сгенеренных ктипов и необходимых бизнесу типов
	 * @param enumFormaters - таблица енумов и соответствующих им форматеров
	 */
	public static void copyPropertiesWithDifferentTypes(java.lang.Object dest, java.lang.Object orig, Map<Class, Class> corl, Map<Class, BeanFormatter> enumFormaters) throws Exception
	{
		if (orig == null)
		{
			return;
		}
		//Когда объект типа Collection перебираем все его элементы и разбираем как отдельный объект
		if (orig instanceof Collection)
		{
			for (Object object: (Collection) orig)
			{
				Object temp = buildObject(object, corl, enumFormaters);
				((Collection) dest).add(temp);
			}
		}
		//Когда объект Iterator перебираем его элементы, заполняем список, а потом присваиваем dest список
		//TODO решить проблему передачи итератора в BUG022563: Ошибка при передаче Iterator в веб-сервисах
/*

		else if (orig instanceof Iterator)
		{
			List temp = new ArrayList();
			for (Iterator it = ((Iterator) orig); it.hasNext();)
			{
				temp.add(buildObject(it.next(), corl));
			}
			dest = temp;
		}
*/
		//Когда объект типа Map перебираем все его элементы (ключ и знаяение) и разбираем как отдельный объект
		else if (orig instanceof Map)
		{
			((Map) orig).keySet();
			for (Object object : ((Map) orig).keySet())
			{
				Object key = buildObject(object, corl, enumFormaters);
				Object value = buildObject(((Map) orig).get(object), corl, enumFormaters);
				((Map) dest).put(key,value);
			}
		}
		else if (orig instanceof Object[])
		{
			for (int i=0; i<((Object[])orig).length; i++)
			{
				Object temp = buildObject(((Object[])orig)[i], corl, enumFormaters);
				((Object[]) dest)[i] = temp;
			}
		}
		else
		{

			PropertyUtilsBean util = new PropertyUtilsBean();
			PropertyDescriptor origDescriptors[] = util.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				if (util.isReadable(orig, name) && util.isWriteable(dest, name))
				{
					Object value = util.getSimpleProperty(orig, name);
					if (value == null)
					{
						util.setSimpleProperty(dest, name, value);
						continue;
					}
					Object newValue = value;
					//Если тип свойства Map или Collection, то разбираем их отдельно
					//Коллекции бывают ленивые, поэтому создаем для передачи конкретныве инстансы
					//TODO решить проблему передачи итератора в BUG022563: Ошибка при передаче Iterator в веб-сервисах
					if (value instanceof List)
					{
						newValue = new ArrayList();
						copyPropertiesWithDifferentTypes(newValue, value, corl, enumFormaters);
					}
					if (value instanceof Set)
					{
						newValue = new HashSet();
						copyPropertiesWithDifferentTypes(newValue, value, corl, enumFormaters);
					}
					if (value instanceof Map)
					{
						newValue = new HashMap();
						copyPropertiesWithDifferentTypes(newValue, value, corl, enumFormaters);
					}
					if (value instanceof Object[])
					{
						Class<?> componentType = corl.get(value.getClass().getComponentType());
						if (componentType!= null)
							newValue = Array.newInstance(componentType, ((Object[])value).length);
						copyPropertiesWithDifferentTypes(newValue, value, corl, enumFormaters);
					}
					//Если имя класса параметра находится в таблице соответсвия, т.е. он переопределен для сервиса (сгенерен, т.к. не стандартный)
					//Ищем сначала имя класса, а потом его интерфейсов

					BeanFormatter formatter = getFormatter(value.getClass(), corl, enumFormaters);
	                newValue = formatter.format(newValue);

					PropertyDescriptor desc = util.getPropertyDescriptor(dest, name);
					Method method = desc.getWriteMethod();
					Class<?> paramType = method.getParameterTypes()[0];
					//Если тип параметра сеттера совпадает с типом переданного значения (с учетом преобразования) или
					//Если paramType.getPackage(), т.е тип простой например int или boolean, при получениии значения
					//util.getSimpleProperty(orig, name) возращает тип java.lang.* , т.е. например java.lang.Integer или java.lang.Boolean
					//в таком случае isAssignableFrom поймет как разные типы, надо это исключить.
					if (paramType.isAssignableFrom(newValue.getClass()) || paramType.getPackage()==null)
					{

						util.setSimpleProperty(dest, name, removeIncorrectSymbols(newValue));
					}
					else
					{
						//Если не совпадает, то ищем сеттер с другим типом параметра
						Method newMethod = dest.getClass().getMethod(method.getName(), newValue.getClass());
						newMethod.invoke(dest, newValue);
					}
				}
			}
		}
	}

	/**
	 * удаляет символы типа "\u0000" из строки (они являются некорректными с точки зрения xml)
	 * @param object - значение, которое надо проверить и изменить, если это строка, и содержит
	 * некорректные символы
	 * @return
	 */
	private static Object removeIncorrectSymbols(Object object)
	{
		if (object instanceof String)
		{
			//todo как-то неправильно перебирать все юникодовые символы....криво
			String value = (String)object;
			if (value.indexOf('\u0000') != -1 || value.indexOf('\u0001') != -1)
				value = "";
			return value;
		}
		else
			return object;
	}

	/**
	 * Проверить необходимость разложения объекта на свойства.
	 * Если объект Collection или Map или Iterator или прописан в таблице соответсвия наших типов и  типов сервиса,
	 *  то это сложный тип, его необходимо дальше раскладывать.
	 */
	private static boolean checkAnalyzeNessesary(Object object, Map<Class, Class> corl)
	{
		return (object != null)&&((object instanceof Collection) || (object instanceof Map) || (object instanceof Iterator)
				|| (isComplicatedType(object.getClass(), corl)));
	}

	/**
	 * Проверяем на то, что тип входит в таблицу соответсвия. Т.е. он сложный и требует дальнейшего разложения.
	 * Если найден тип назначения то true, иначе false
	 */
	private static boolean isComplicatedType(Class clazz, Map<Class, Class> corl)
	{
		return getDestType(clazz,corl)!=null;
	}

	/**
	 * Получаем необходимый тип объекта по типу текущего объекта
	 * Т.е. просматриваем переданную таблицу соответсвия в следующем порядке:
	 * Сначала проверяем класс объекта, если ничего не найдено, получаем ВСЕ интерфейсы класса с учетом наследования
	 * Перебираем интерфейсы и возвращаем тип первого найденного интерфейса.
	 * ВНИМАНИЕ! Если для типа с несколькими интерфейсами в таблице соответсвия будет несколько значений, то вернется первый попавшийся.
	 * @param clazz - исходный класс
	 * @param corl - таблица-соответсвия
	 * @return Новый тип, если не надо менять тип, то null
	 */
	private static Class getDestType(Class clazz, Map<Class,Class> corl)
	{
		Class byClass = corl.get(clazz);
		if (byClass!=null)
			return byClass;
		List<Class> interfaces = getInterfaces(clazz);
		if(interfaces.isEmpty())
			return null;
		//Первый попавшийся интерфейс возвращаем
		for (Class temp: interfaces)
		{
			byClass = corl.get(temp);
			 if (byClass!=null)
			    return byClass;
		}
		return null;
	}

	/*
	Получаем formatter по классу, либо для него, либо для его родительских классов
	 */
	private static Object getFormatter(Class<? extends Object> clazz)
	{
		Object reslultClass = BeanFormatterMap.getMap().get(clazz);
		if (reslultClass!=null)
			return reslultClass;
		Class tempClass = clazz.getSuperclass();
		if (tempClass==null)
			return null;
		return getFormatter(tempClass);
	}

	private static BeanFormatter getFormatter(Class<? extends Object> clazz, Map<Class, Class> corl, Map<Class, BeanFormatter> enumFormaters) throws Exception
	{
		Class destClass = getDestType(clazz, corl);
	//  если по ключу clazz в таблице корелляции найдено значение не равное null, то копируем данные обьекта в новую сущность
		if (destClass != null)
		{
			return new CopyBeanFormatter(destClass, corl, enumFormaters);
		}
	//  если по ключу clazz в таблице корелляции найдено значение равное null, то возвращаем Class или Enum в зависимости от ключа
		if (corl.containsKey(clazz))
		{
			Object formatterObject = getFormatter(clazz);
            if(formatterObject!=null)
            {
				return (BeanFormatter) ((Class) formatterObject).getConstructor().newInstance();
            }
		}

		//ищем в таблице enum-форматеров
		if (enumFormaters != null)
		{
			BeanFormatter beanFormatter = enumFormaters.get(clazz);
			if (beanFormatter != null)
				return beanFormatter;
		}
		//  если в таблице корелляции нет класса копируем объект как есть
		return new EmptyFormatter();
	}

	/*
	Получение всех интерфейсов для класса с учетом наследования
	 */
	private static List<Class> getInterfaces(Class clazz)
	{
		List allInterfaces = new ArrayList();
		interfaces(clazz, allInterfaces);
		return allInterfaces;
	}

	/*
	 Рекурсивный метод получения интерфейсов по классу-родителю
	 */
	private static void interfaces(Class clazz, List interfaces)
	{
        for (; clazz != null; clazz = clazz.getSuperclass())
        {
            // Check the implemented interfaces of the parent class
            Class[] temp = clazz.getInterfaces();

            for (int i = 0; i < temp.length; i++)
            {
	            interfaces.add(temp[i]);
	            // Recursively get our parent interfaces
	            interfaces(temp[i], interfaces);
            }
	        interfaces.add(clazz);
        }
	}

	/**
	 * Сравнение объектов по всем их свойствам
	 * @param obj1 первый объект
	 * @param obj2 второй объект
	 * @return
	 */
	public static int compareBeans(java.lang.Object obj1, java.lang.Object obj2)
	{
		return compareFilteredBeans(obj1, obj2, new ArrayList<String>());
	}

	/**
	 * Сравнение объектов по их свойствам
	 * @param obj1   первый объект
	 * @param obj2   второй объект
	 * @param filter список исключаемых свойств для сравнения
	 * @return 
	 */
	public static int compareFilteredBeans(java.lang.Object obj1, java.lang.Object obj2, List<String> filter)
	{
		if (obj1 == null && obj2 == null)
			return 0;

		if (obj1 == null || obj2 == null)
			throw new NullPointerException();

		if (!obj1.getClass().getName().equals(obj2.getClass().getName()))
			throw new IllegalStateException("Объекты для сравнения должны быть экземплярами одного класса.");

		//получаем все свойства бина в мапе без приведения типа
		Map<String,Object> map1 = BeanHelper.createMapFromPropertiesFull(obj1);
		Map<String,Object> map2 = BeanHelper.createMapFromPropertiesFull(obj2);

		filter.add("class");

		for (String property : filter)
		{
			map1.remove(property);
			map2.remove(property);
		}

        Set<String> keySet1 = map1.keySet();
		//проходим по свойствам первого
		for (String key : keySet1)
		{
			Object res1 = map1.get(key);
			Object res2 = map2.get(key);

			if( (res2==null && res1==null) )
				continue;

			if( (res1 instanceof Comparable) && (res2 instanceof Comparable) )
			{//если оба можно сравнивать, то сравниваем
				int i = ((Comparable)res1).compareTo(res2);

				if (i != 0)
					return i;
			}
			else
			{//если оба нельзя сравнивать копаем дальше до примитивных типов.
				return compareBeans(res1,res2);
			}
		}
		return 0;
	}

	public static Map<String, Object> getProperties(Object obj)
	{
		Map<String, Object> map = null;
		try
		{
			map = new HashMap<String, Object>();
			for (PropertyDescriptor d:PropertyUtils.getPropertyDescriptors(obj))
			{
				Method method = PropertyUtils.getReadMethod(d);
				BeanUtils.getProperty(obj, d.getName());
				Object value = method.invoke(obj);
				map.put(d.getName(), value);
			};
			map.remove("class");
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return map;
	}

	/**
	 * Вытаскивает свойство nameProperty из объекта obj при помощи вызова геттера
	 * @param obj, бин у которого вызывается геттер
	 * @param nameProperty имя поля
	 * @return значение свойства
	 */
	public static Object getProperty(Object obj, String nameProperty)
	{
		try
		{
			return PropertyUtils.getProperty(obj, nameProperty);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
/*
	{
		Map<String,Object> map1 = BeanHelper.createMapFromPropertiesFull(obj1);
		Map<String,Object> map2 = BeanHelper.createMapFromPropertiesFull(obj2);


		Set<String> keySet1 = map1.keySet();
		for (String key : keySet1)
		{
			Object res1 = map1.get(key);
			Object res2 = map2.get(key);
			if(res2==null)
				return -1;
			if( (!(res1 instanceof Comparable)) || (!(res2 instanceof Comparable)) )
				throw new RuntimeException("Ошибка сравнения объектов");

			if(!map1.get(key).(map2.get(key)))
				return -1;
		}
	}
*/
}
