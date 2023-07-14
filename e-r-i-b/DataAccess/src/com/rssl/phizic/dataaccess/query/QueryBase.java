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
 * Ѕазовый абстрактный класс запроса.
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
	 * ¬озвращает им€ параметра запроса по геттеру.
	 *
	 * ѕример:
	 * getParameter преобразуетс€ в parameter
	 * isAllDepartments преобразуетс€ в allDepartments
	 * но getURL преобразуетс€ в URL
	 *
	 * @param methodName метода
	 * @return им€ параметра
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
	 * «аполн€ет extraParameters значени€ми геттеров помеченных аннотацией QueryParameter из операции
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
				//ѕараметрами запроса могут €вл€тьс€ только геттеры помеченные аннотацией QueryParameter
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
