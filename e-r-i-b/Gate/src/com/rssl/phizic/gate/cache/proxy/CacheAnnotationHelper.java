package com.rssl.phizic.gate.cache.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class CacheAnnotationHelper
{
	/**
	 * ���� ����� � ���������� Cachable ����� ������� ��������� ��������� ������
	 * @param method
	 * @return null ��� ��������� ���������
	 */
	public static Method getInterfaceMethodByMethod(Method method)
	{
		Class methodClass = method.getDeclaringClass();
		Class[] interfaces = getInheritanceInterfaces(methodClass);
		for (Class anInterface : interfaces)
		{
			try
			{
				Method interfaceMethod = anInterface.getMethod(method.getName(),method.getParameterTypes());
				Cachable result = interfaceMethod.getAnnotation(Cachable.class);
				if(result!=null)
					return interfaceMethod;

			}
			catch (NoSuchMethodException ex)
			{
				//������ �� ������, ���� ������
			}
		}
		return null;
	}

	/**
	 * ���� ����� � ���������� Cachable ����� ������� ��������� ��������� ������ � ������ ������.
	 * @param method �����.
	 * @return null ��� ��������� ���������.
	 */
	public static Method getCachableMethodByMethod(Method method)
	{
		Cachable result = method.getAnnotation(Cachable.class);
		if (result == null)
			return getInterfaceMethodByMethod(method);
		return method;
	}

	/**
	 * �������� ��� ���������� ��� ����������� ������ � ���� ��� ������������
	 * @param methodClass  - �����, ��� �������� ���� ����������
	 * @return ���������� ������ �������, ������������ ����������
	 * ���� ����������� ���, �� ���������� ������ ������
	 */
	private static Class[] getInheritanceInterfaces(Class methodClass)
	{
		List<Class> interfaces = new ArrayList<Class>();

		do
		{
			Class[] methodClassInterfaces = methodClass.getInterfaces();    // �������� ������ �����������, ������������ ��� ����� ������ (���������� ����� ������� �� ��������)
			Collections.addAll(interfaces, methodClassInterfaces);          // ��������� ��� � ������
			methodClass = methodClass.getSuperclass();    // ���� ����������
		}
		while(methodClass != null);       // ���� null, �� ������������ ��� (����� �� Object)

		return interfaces.toArray( new Class[interfaces.size()] );  // ���������� ������ ����������� ��� ����������� ������ � ���� �������
	}
}
