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
	 * @param className ��� ������ ��� ��������
	 * @return �����
	 */
	public static <C> Class<C> loadClass(String className) throws ClassNotFoundException
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		// TODO ��������� ������� �� ��� � ��� ��� ���������
		return (Class<C>) loader.loadClass(className);

	}

	/**
	 * �������� �����, ������� �� ������� CGLIB
	 * @param obj ������ ��� �������� ���� ���������� ���
	 * @return Class �������
	 */
	public static Class getActualClass(Object obj)
	{
		Class result = obj.getClass();
		while (Factory.class.isAssignableFrom(result))
			result = result.getSuperclass();
		return result;
	}

	/**
	 * ��� ��������������������(generic) ����� ������� ��� ���������, �.�. Some<Other> ������ Other
	 * @param typeToCheck ��� ��� �������
	 * @param parameterNum ����� ���������, ������� ���� �������
	 * @return null - ���� �� generic.
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
					//�������� �� ���� ������������ ����������
					returnTypePar = (Class)((ParameterizedType)paramType).getRawType();
				}
				return returnTypePar;
			}
		}
		return null;
	}

	/**
	 * ��������� ����� ������������� ��������, ������ ���� ������������� ���������, �� ����� �������� � ���������
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
	 * ��������� ���������� �� ������������� �������
	 * ���� ������ ����� ��� �����������, ������� ���� � ������ �������.
	 * �� ������ ����� ������, �.� ��� ����� ������
	 * ����� ��������������� ������������ ������ ��� GetClientProduct
	 * @param returnValue ������������ �������� ��� ��� �����, ��� � ��� �����
	 * @param interfaces ����� ����������� �����, ������� ����� ��������. 
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
	 * �������� �� ����� com.rssl.phizic.gate.clients.ClientProductsService#getClientProducts(com.rssl.phizic.gate.clients.Client, java.lang.Class...)
	 * @param method ����� ��� ��������
	 * @return true - ����, ��.                           
	 */
	public static boolean isGetClientProduct(Method method)
	{
		return GET_CLIENT_PRODUCTS.equalsIgnoreCase(method.getName());
	}


	/**
	 * ��������� �������� �� ����� ������� ��� ��������,
	 * ������ ���� GroupResult, �� ����������� ��� ����������, �.�. GroupResult<K,V> - ����������� V
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
	 * ��������� ���� �� CLASS_PATH
	 * @param name - ��� � CLASS_PATH, �������� -
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
	 * ������� ������ �� ����� ������
	 * ��������� ����������� ��� ����������
	 * @param classname - ��� ������, ������� �����
	 * @return ����� ��������� ������
	 * @throws RuntimeException - ������ �������� ������
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
			throw new RuntimeException("�� ����� ����� " + classname, e);
		}
	}

	/**
	 * ������� ������ �� ������
	 * ��������� ����������� ��� ����������
	 * @param instanceClass - �����
	 * @return ����� ��������� ������
	 * @throws RuntimeException - ������ �������� ������
	 */
	public static <T> T newInstance(Class<T> instanceClass) throws RuntimeException
	{
		try
		{
			return instanceClass.newInstance();
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("�� �������� ����� / ����������� ��� ���������� " + instanceClass.getName(), e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("���� ��� ������� ������� ��������� ������ " + instanceClass.getName(), e);
		}
	}

	/**
	 * ���������� ������ ������������ ����� (���������� transient-����)
	 * ���� �������� ������� ������ ������� - ������� IllegalArgumentException
	 * @param newObj - ������ ������
	 * @param oldObj - ����� ������
	 * @return ������ �������� �����, ������� ����������
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