package com.rssl.common.forms.expressions.js;

import com.rssl.common.forms.FormsConfig;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.mozilla.javascript.*;

import java.util.Map;

/**
 *
 * @author komarov
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"OverlyComplexAnonymousInnerClass"})
public abstract class RhinoExpressionBase implements Expression
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private final String expression;

	protected RhinoExpressionBase(String expression)
	{
		this.expression = expression;
	}

	/**
	 * Возвращает sharedScope
	 * @return getShared
	 */
	public abstract Scriptable getSharedScope();

	/**
	 * Вычислить значение выражения
	 * @param form даные формы
	 * @return значение выражения
	 */
	public Object evaluate(final Map<String, Object> form)
	{
		return Context.call(new ContextAction()
		{
			@SuppressWarnings({"ThrowCaughtLocally"})
			public Object run(Context cx)
			{
				try
				{
					cx.setOptimizationLevel(ConfigFactory.getConfig(FormsConfig.class).getRhinoOptimizationLevel());
				}
				catch (Exception e)
				{
					log.warn("Ошибка установки 'уровня оптимизации' Rhino. Используем дефолтный уровень для выражения: " + expression, e);
				}
				try
				{
					Script script = compileScript();

					Scriptable executionScope = cx.newObject(getSharedScope());
					executionScope.setPrototype(getSharedScope());
					executionScope.setParentScope(null);
					ScriptableObject.putProperty(executionScope, "form", JSMap.wrap(form));

					Object result = script.exec(cx, executionScope);
					if (result instanceof Undefined)
					{
						throw new RuntimeException("Выражение не имеет значения: " + expression);
					}

					if (result instanceof NativeJavaObject)
						return ((NativeJavaObject) result).unwrap();
					else
						return result;
				}
				catch (WrappedException e)
				{
					Throwable original = e.getWrappedException();
					if (original instanceof InactiveExternalSystemException)
						throw (InactiveExternalSystemException) original;
					throw e;
				}
				catch (Exception e)
				{
					throw new RuntimeException("Ошибка вычисления выражения: " + expression, e);
				}
			}
		});
	}

	private Script compileScript()
	{
		return (Script) Context.call(new ContextAction()
		{
			public Object run(Context cx)
			{
				try
				{
//TODO передлать на нормальное объявление функции.
					return cx.compileString("function getFieldValue(field){return form[field];}" + expression, null, 1, null);
				}
				catch (EvaluatorException e)
				{
					throw new RuntimeException("Ошибка компиляции скрипта: " + e.getMessage() +
							"\nLine:" + e.lineNumber() +
							"\nColumn:" + e.columnNumber() +
							"\nLine source:" + e.lineSource() +
							"\nFull source:" + expression, e);
				}
			}
		});
	}

}
