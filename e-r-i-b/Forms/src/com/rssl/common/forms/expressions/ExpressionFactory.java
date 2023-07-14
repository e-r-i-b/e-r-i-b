package com.rssl.common.forms.expressions;

import com.rssl.phizic.utils.ClassHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 17.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class ExpressionFactory
{
	private static final Map<String, String> expressionClases = new HashMap<String, String>();
	private static final String defaultExpressionClass = "com.rssl.common.forms.expressions.js.RhinoExpression";

	static
	{
		//регистрируем возможные типы выражений по префиксам.
		expressionClases.put("js:", defaultExpressionClass);
		expressionClases.put("xpath:", "com.rssl.phizic.business.forms.expressions.xpath.XPathExpression");
	}

	/**
	 *
	 * @param expression выражени javascipt
	 * @return выражение
	 */
	public Expression newExpression(String expression)
	{
		for (Map.Entry<String, String> entry : expressionClases.entrySet())
		{
			String prefix = entry.getKey();
			if (expression.startsWith(prefix))
			{
				return newExpressionInternal(entry.getValue(), expression.substring(prefix.length()));
			}
		}
		return newExpressionInternal(defaultExpressionClass, expression);
	}

	private Expression newExpressionInternal(String className, String expression)
	{
		try
		{
			Class<Expression> aClass = ClassHelper.loadClass(className);
			Constructor<Expression> constructor = aClass.getConstructor(String.class);
			return constructor.newInstance(expression);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
}
