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
	 * ����������� ������ � ���, ��� ���� ��� �������� ����������� � ������ 
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
	 * ����������� ������ � ���, ��� ���������� �������.
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
	 * ������� ����� ������� �� ������ ������ ����������
	 * @param object ���������� ������
	 * @param corl ������� ����������
	 * @param <O> ��� ������������� �������
	 * @return ����� (�� ������ ������ ����������) ����������� �������
	 * @throws Exception
	 */
	public static <O> O copyObject(Object object,  Map<Class, Class> corl) throws Exception
	{
		//noinspection unchecked
		return (O) buildObject(object, corl, null);
	}

	/**
	 * �������� ���������� ������. ������������ ��� ��������� ������� �����: Collection, Map, Iterator
	 * @param object - ������ ��� ��������������
	 * @param corl - �������-�����������
	 * @return ����� ������ ���� (��� �������������) � ���������� ���������
	 * @throws Exception - ���������� ����������, ��������� � ��������� � ���������������� �������
	 */	
	private static Object buildObject(Object object,  Map<Class, Class> corl, Map<Class, BeanFormatter> enumFormaters) throws Exception
	{
		//���� ������ �� ���� ������ ������������, �� ���������� ������ ���
		if(!checkAnalyzeNessesary(object, corl))
			return object;

		Object temp;
		Class destType = getDestType(object.getClass(), corl);
		//���� ��� � �������, �� ����� ��� ��������
		if (destType==null)
		{
			temp = object.getClass().getConstructor().newInstance();
		}
		//�� ����� ��� �� corl
		else
		{
			temp = destType.getConstructor().newInstance();
		}
		copyPropertiesWithDifferentTypes(temp, object, corl, enumFormaters);
		return temp;
	}

	/**
	 * ��������! ��������� ��� ����� JAX-RPC!!!
	 * �������� �������� � ������� ������ ����������.
	 * ������������ ��� �������������� �������� �������� � ���� ����������� �������
	 * Java ������� ������ ����������.
	 * @param dest - ���� ��������
	 * @param orig - ��� ��������
	 * @param corl - ������� ����������� ����������� ������ � ����������� ������� �����
	 */
	public static void copyPropertiesWithDifferentTypes(java.lang.Object dest, java.lang.Object orig, Map<Class, Class> corl) throws Exception
	{
		copyPropertiesWithDifferentTypes(dest, orig, corl, null);
	}
	/**
	 * ��������! ��������� ��� ����� JAX-RPC!!!
	 * �������� �������� � ������� ������ ����������.
	 * ������������ ��� �������������� �������� �������� � ���� ����������� �������
	 * Java ������� ������ ����������.
	 * @param dest - ���� ��������
	 * @param orig - ��� ��������
	 * @param corl - ������� ����������� ����������� ������ � ����������� ������� �����
	 * @param enumFormaters - ������� ������ � ��������������� �� ����������
	 */
	public static void copyPropertiesWithDifferentTypes(java.lang.Object dest, java.lang.Object orig, Map<Class, Class> corl, Map<Class, BeanFormatter> enumFormaters) throws Exception
	{
		if (orig == null)
		{
			return;
		}
		//����� ������ ���� Collection ���������� ��� ��� �������� � ��������� ��� ��������� ������
		if (orig instanceof Collection)
		{
			for (Object object: (Collection) orig)
			{
				Object temp = buildObject(object, corl, enumFormaters);
				((Collection) dest).add(temp);
			}
		}
		//����� ������ Iterator ���������� ��� ��������, ��������� ������, � ����� ����������� dest ������
		//TODO ������ �������� �������� ��������� � BUG022563: ������ ��� �������� Iterator � ���-��������
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
		//����� ������ ���� Map ���������� ��� ��� �������� (���� � ��������) � ��������� ��� ��������� ������
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
					//���� ��� �������� Map ��� Collection, �� ��������� �� ��������
					//��������� ������ �������, ������� ������� ��� �������� ����������� ��������
					//TODO ������ �������� �������� ��������� � BUG022563: ������ ��� �������� Iterator � ���-��������
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
					//���� ��� ������ ��������� ��������� � ������� �����������, �.�. �� ������������� ��� ������� (��������, �.�. �� �����������)
					//���� ������� ��� ������, � ����� ��� �����������

					BeanFormatter formatter = getFormatter(value.getClass(), corl, enumFormaters);
	                newValue = formatter.format(newValue);

					PropertyDescriptor desc = util.getPropertyDescriptor(dest, name);
					Method method = desc.getWriteMethod();
					Class<?> paramType = method.getParameterTypes()[0];
					//���� ��� ��������� ������� ��������� � ����� ����������� �������� (� ������ ��������������) ���
					//���� paramType.getPackage(), �.� ��� ������� �������� int ��� boolean, ��� ���������� ��������
					//util.getSimpleProperty(orig, name) ��������� ��� java.lang.* , �.�. �������� java.lang.Integer ��� java.lang.Boolean
					//� ����� ������ isAssignableFrom ������ ��� ������ ����, ���� ��� ���������.
					if (paramType.isAssignableFrom(newValue.getClass()) || paramType.getPackage()==null)
					{

						util.setSimpleProperty(dest, name, removeIncorrectSymbols(newValue));
					}
					else
					{
						//���� �� ���������, �� ���� ������ � ������ ����� ���������
						Method newMethod = dest.getClass().getMethod(method.getName(), newValue.getClass());
						newMethod.invoke(dest, newValue);
					}
				}
			}
		}
	}

	/**
	 * ������� ������� ���� "\u0000" �� ������ (��� �������� ������������� � ����� ������ xml)
	 * @param object - ��������, ������� ���� ��������� � ��������, ���� ��� ������, � ��������
	 * ������������ �������
	 * @return
	 */
	private static Object removeIncorrectSymbols(Object object)
	{
		if (object instanceof String)
		{
			//todo ���-�� ����������� ���������� ��� ���������� �������....�����
			String value = (String)object;
			if (value.indexOf('\u0000') != -1 || value.indexOf('\u0001') != -1)
				value = "";
			return value;
		}
		else
			return object;
	}

	/**
	 * ��������� ������������� ���������� ������� �� ��������.
	 * ���� ������ Collection ��� Map ��� Iterator ��� �������� � ������� ����������� ����� ����� �  ����� �������,
	 *  �� ��� ������� ���, ��� ���������� ������ ������������.
	 */
	private static boolean checkAnalyzeNessesary(Object object, Map<Class, Class> corl)
	{
		return (object != null)&&((object instanceof Collection) || (object instanceof Map) || (object instanceof Iterator)
				|| (isComplicatedType(object.getClass(), corl)));
	}

	/**
	 * ��������� �� ��, ��� ��� ������ � ������� �����������. �.�. �� ������� � ������� ����������� ����������.
	 * ���� ������ ��� ���������� �� true, ����� false
	 */
	private static boolean isComplicatedType(Class clazz, Map<Class, Class> corl)
	{
		return getDestType(clazz,corl)!=null;
	}

	/**
	 * �������� ����������� ��� ������� �� ���� �������� �������
	 * �.�. ������������� ���������� ������� ����������� � ��������� �������:
	 * ������� ��������� ����� �������, ���� ������ �� �������, �������� ��� ���������� ������ � ������ ������������
	 * ���������� ���������� � ���������� ��� ������� ���������� ����������.
	 * ��������! ���� ��� ���� � ����������� ������������ � ������� ����������� ����� ��������� ��������, �� �������� ������ ����������.
	 * @param clazz - �������� �����
	 * @param corl - �������-�����������
	 * @return ����� ���, ���� �� ���� ������ ���, �� null
	 */
	private static Class getDestType(Class clazz, Map<Class,Class> corl)
	{
		Class byClass = corl.get(clazz);
		if (byClass!=null)
			return byClass;
		List<Class> interfaces = getInterfaces(clazz);
		if(interfaces.isEmpty())
			return null;
		//������ ���������� ��������� ����������
		for (Class temp: interfaces)
		{
			byClass = corl.get(temp);
			 if (byClass!=null)
			    return byClass;
		}
		return null;
	}

	/*
	�������� formatter �� ������, ���� ��� ����, ���� ��� ��� ������������ �������
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
	//  ���� �� ����� clazz � ������� ���������� ������� �������� �� ������ null, �� �������� ������ ������� � ����� ��������
		if (destClass != null)
		{
			return new CopyBeanFormatter(destClass, corl, enumFormaters);
		}
	//  ���� �� ����� clazz � ������� ���������� ������� �������� ������ null, �� ���������� Class ��� Enum � ����������� �� �����
		if (corl.containsKey(clazz))
		{
			Object formatterObject = getFormatter(clazz);
            if(formatterObject!=null)
            {
				return (BeanFormatter) ((Class) formatterObject).getConstructor().newInstance();
            }
		}

		//���� � ������� enum-����������
		if (enumFormaters != null)
		{
			BeanFormatter beanFormatter = enumFormaters.get(clazz);
			if (beanFormatter != null)
				return beanFormatter;
		}
		//  ���� � ������� ���������� ��� ������ �������� ������ ��� ����
		return new EmptyFormatter();
	}

	/*
	��������� ���� ����������� ��� ������ � ������ ������������
	 */
	private static List<Class> getInterfaces(Class clazz)
	{
		List allInterfaces = new ArrayList();
		interfaces(clazz, allInterfaces);
		return allInterfaces;
	}

	/*
	 ����������� ����� ��������� ����������� �� ������-��������
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
	 * ��������� �������� �� ���� �� ���������
	 * @param obj1 ������ ������
	 * @param obj2 ������ ������
	 * @return
	 */
	public static int compareBeans(java.lang.Object obj1, java.lang.Object obj2)
	{
		return compareFilteredBeans(obj1, obj2, new ArrayList<String>());
	}

	/**
	 * ��������� �������� �� �� ���������
	 * @param obj1   ������ ������
	 * @param obj2   ������ ������
	 * @param filter ������ ����������� ������� ��� ���������
	 * @return 
	 */
	public static int compareFilteredBeans(java.lang.Object obj1, java.lang.Object obj2, List<String> filter)
	{
		if (obj1 == null && obj2 == null)
			return 0;

		if (obj1 == null || obj2 == null)
			throw new NullPointerException();

		if (!obj1.getClass().getName().equals(obj2.getClass().getName()))
			throw new IllegalStateException("������� ��� ��������� ������ ���� ������������ ������ ������.");

		//�������� ��� �������� ���� � ���� ��� ���������� ����
		Map<String,Object> map1 = BeanHelper.createMapFromPropertiesFull(obj1);
		Map<String,Object> map2 = BeanHelper.createMapFromPropertiesFull(obj2);

		filter.add("class");

		for (String property : filter)
		{
			map1.remove(property);
			map2.remove(property);
		}

        Set<String> keySet1 = map1.keySet();
		//�������� �� ��������� �������
		for (String key : keySet1)
		{
			Object res1 = map1.get(key);
			Object res2 = map2.get(key);

			if( (res2==null && res1==null) )
				continue;

			if( (res1 instanceof Comparable) && (res2 instanceof Comparable) )
			{//���� ��� ����� ����������, �� ����������
				int i = ((Comparable)res1).compareTo(res2);

				if (i != 0)
					return i;
			}
			else
			{//���� ��� ������ ���������� ������ ������ �� ����������� �����.
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
	 * ����������� �������� nameProperty �� ������� obj ��� ������ ������ �������
	 * @param obj, ��� � �������� ���������� ������
	 * @param nameProperty ��� ����
	 * @return �������� ��������
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
				throw new RuntimeException("������ ��������� ��������");

			if(!map1.get(key).(map2.get(key)))
				return -1;
		}
	}
*/
}
