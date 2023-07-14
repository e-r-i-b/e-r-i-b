package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.utils.StringHelper;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author mihaylov
 * @ created 25.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����������� ����� �������.
 */
abstract class QueryBase implements InternalQuery
{
	private static final String GET = "get";
	private static final String IS  = "is";

	private Object bean;

	Object getBean()
	{
		return bean;
	}

	void setBean(Object bean)
	{
		this.bean = bean;
	}

	/**
	 * ���������� ��� ��������� ������� �� �������.
	 *
	 * ������:
	 * getParameter ������������� � parameter
	 * isAllDepartments ������������� � allDepartments
	 * �� getURL ������������� � URL
	 *
	 * @param methodName ������
	 * @return ��� ���������
	 */
	private String getQueryParameterName(String methodName)
	{
		if(methodName.startsWith(GET))
			return Introspector.decapitalize(methodName.substring(GET.length()));

		if(methodName.startsWith(IS))
			return Introspector.decapitalize( methodName.substring(IS.length()) );

		return null;
	}

	/**
	 * ��������� extraParameters ���������� �������� ���������� ���������� QueryParameter �� ��������
	 */
	protected void fillBeanParameters()
	{
		if(bean == null)
			return;

		Class clazz = bean.getClass();
		for(Method method : clazz.getMethods())
		{
			try
			{
				//����������� ������� ����� �������� ������ ������� ���������� ���������� QueryParameter
				if(method.getAnnotation(QueryParameter.class) != null && method.getParameterTypes().length==0)
				{
					String name = getQueryParameterName(method.getName());
					if(StringHelper.isNotEmpty(name))
					{
	                    Object value = method.invoke(bean);
						setParameter(name, value);
					}
				}
			}
			catch (IllegalAccessException ignore)
			{}
			catch(InvocationTargetException ignore)
			{}
		}
	}

}
