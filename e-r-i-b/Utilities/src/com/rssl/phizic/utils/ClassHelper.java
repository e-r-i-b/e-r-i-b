package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import net.sf.cglib.proxy.Factory;
import org.apache.commons.lang.ObjectUtils;

import java.io.InputStream;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author Evgrafov
 * @ created 16.03.2006
 * @ $Author: erkin $
 * @ $Revision: 47616 $
 */

public class ClassHelper implements BeanFormatter
{
	private static final String GET_CLIENT_PRODUCTS = "GetClientProducts";
	/**
	 * @param className имя класса для загрузки
	 * @return класс
	 */
	public static <C> Class<C> loadClass(String className) throws ClassNotFoundException
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		// TODO проверить реально ли это С или его наследник
		return (Class<C>) loader.loadClass(className);

	}

	/**
	 * Получает класс, который не обернут CGLIB
	 * @param obj объект для которого надо определить тип
	 * @return Class объекта
	 */
	public static Class getActualClass(Object obj)
	{
		Class result = obj.getClass();
		while (Factory.class.isAssignableFrom(result))
			result = result.getSuperclass();
		return result;
	}

	/**
	 * Для параметаризированный(generic) типов вернуть тип параметра, т.е. Some<Other> вернет Other
	 * @param typeToCheck тип для анализа
	 * @param parameterNum номер параметра, который надо вернуть
	 * @return null - если не generic.
	 */
	public static Class getGenericParameter(Type typeToCheck, int parameterNum)
	{
		Type genReturnType = typeToCheck;
		if(genReturnType instanceof ParameterizedType)
		{
			Class returnTypePar;

			ParameterizedType type = (ParameterizedType) genReturnType;
			Type[] params = type.getActualTypeArguments();
			if(params.length!=0)
			{
				Type paramType = params[parameterNum];
				if(paramType instanceof Class)
				{
					returnTypePar = (Class)params[parameterNum];
				}
				else
				{
					//закладка на наши существующие интерфейсы
					returnTypePar = (Class)((ParameterizedType)paramType).getRawType();
				}
				return returnTypePar;
			}
		}
		return null;
	}

	/**
	 * Возращает класс возвращаемого значения, причем если возвращаеться коллекция, то класс элемента в коллекции
	 * @param method
	 * @return
	 */
	public static Class getReturnClassIgnoreCollections(Method method)
	{
		Class returnType = method.getReturnType();
		if(returnType.isAssignableFrom(List.class))
		{
			return ClassHelper.getGenericParameter(method.getGenericReturnType(),0);

		}
		if(returnType.isAssignableFrom(GroupResult.class))
		{
			return ClassHelper.getGenericParameter(method.getGenericReturnType(),1);
		}
		if(returnType.isAssignableFrom(Array.class))
		{
			return returnType.getComponentType();
		}
		return returnType;
	}

	/**
	 * получение интерфейса по возвращаемому объекту
	 * ищем только среди тех интерфейсов, которые есть в других методах.
	 * из метода взять нельза, т.к там через обжект
	 * МЕТОД ПРЕДПОЛОГАЕТЬСЯ ИСПОЛЬЗОВАТЬ ТОЛЬКО ДЛЯ GetClientProduct
	 * @param returnValue возвращаемое значение уже без мапов, пар и так далее
	 * @param interfaces набор интерфейсов гейта, которые могут вернутся. 
	 * @return
	 */
	public static Class getReturnClassforGetClientProduct(Object returnValue, Collection<Class> interfaces)
	{
		Class returnType = returnValue.getClass();
		for (Class returnInterface : interfaces)
		{
			if(returnInterface.isAssignableFrom(returnType))
				return returnInterface;
		}
		return null;
	}

	/**
	 * является ли метод com.rssl.phizic.gate.clients.ClientProductsService#getClientProducts(com.rssl.phizic.gate.clients.Client, java.lang.Class...)
	 * @param method метод для проверки
	 * @return true - если, да.                           
	 */
	public static boolean isGetClientProduct(Method method)
	{
		return GET_CLIENT_PRODUCTS.equalsIgnoreCase(method.getName());
	}


	/**
	 * проверяет является ли класс списком или массивом,
	 * причем если GroupResult, то анализирует тип результата, т.е. GroupResult<K,V> - анализирует V
 	 * @param typeToCheck
	 * @return
	 */
	public static boolean checkIfCollection(Type typeToCheck)
	{
		Class clearClassToCheck;
				
		if(typeToCheck instanceof ParameterizedType)
		{
			clearClassToCheck = (Class)((ParameterizedType)typeToCheck).getRawType();
		}
		else
		{
			clearClassToCheck = (Class)typeToCheck;
		}

		if(clearClassToCheck.isAssignableFrom(GroupResult.class))
		{
			clearClassToCheck = ClassHelper.getGenericParameter(typeToCheck,1);
		}

		return clearClassToCheck.isArray() || clearClassToCheck.isAssignableFrom(List.class);
	}

	/**
	 * Загрузить файл из CLASS_PATH
	 * @param name - имя в CLASS_PATH, например -
	 * @return InputStream
	 */
	public static InputStream getInputStreamFromClassPath(String name)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResourceAsStream(name);
	}

	public Object format(Object object)
	{
		return ((Class) object).getName();
	}

	/**
	 * Создать объект по имени класса
	 * используя конструктор без параметров
	 * @param classname - имя класса, включая пакет
	 * @return новый экземпляр класса
	 * @throws RuntimeException - ошибка создания класса
	 */
	public static <T> T newInstance(String classname) throws RuntimeException
	{
		try
		{
			@SuppressWarnings({"unchecked"})
			Class<T> clazz = (Class<T>) Class.forName(classname);
			return newInstance(clazz);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Не наден класс " + classname, e);
		}
	}

	/**
	 * Создать объект по классу
	 * используя конструктор без параметров
	 * @param instanceClass - класс
	 * @return новый экземпляр класса
	 * @throws RuntimeException - ошибка создания класса
	 */
	public static <T> T newInstance(Class<T> instanceClass) throws RuntimeException
	{
		try
		{
			return instanceClass.newInstance();
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Не доступен класс / конструктор без параметров " + instanceClass.getName(), e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("Сбой при попытке создать экземпляр класса " + instanceClass.getName(), e);
		}
	}

	/**
	 * Возвращает список изменившихся полей (игнорирует transient-поля)
	 * если переданы объекты разных классов - бросает IllegalArgumentException
	 * @param newObj - старый объект
	 * @param oldObj - новый объект
	 * @return список названий полей, которые изменились
	 */
	public static Set<String> compareFields(Object newObj, Object oldObj)
	{
        if (newObj == oldObj)
			return Collections.emptySet();

		Set<String> changedFields = new HashSet<String>();
		Class newObjClass = newObj.getClass();
		Class oldObjClass =  oldObj.getClass();

		if (!newObjClass.equals(oldObjClass))
			throw new IllegalArgumentException();
		
	    try
	    {
			while(true)
			{
				Field[] fields = newObjClass.getDeclaredFields();
				for (Field field : fields)
				{
					int mods = field.getModifiers();
					if(!(Modifier.isTransient(mods) || Modifier.isStatic(mods)))
					{
						field.setAccessible(true);

						Object newValue = field.get(newObj);
						Object oldValue = field.get(oldObj);

						if(newValue instanceof String)
						{
							if(!StringHelper.equalsNullIgnore((String) newValue, (String) oldValue))
								changedFields.add(field.getName());
						}
						else
						{
							if(!ObjectUtils.equals(newValue, oldValue))
								changedFields.add(field.getName());
						}
					}
				}

				newObjClass = newObjClass.getSuperclass();

				if(newObjClass.equals(Object.class))
					break;
			}
	    }
	    catch(IllegalAccessException e)
	    {
		    throw new RuntimeException(e);
	    }

		return changedFields;
	}
}