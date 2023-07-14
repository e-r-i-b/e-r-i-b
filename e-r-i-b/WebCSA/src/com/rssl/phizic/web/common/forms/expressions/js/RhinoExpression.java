package com.rssl.phizic.web.common.forms.expressions.js;

import com.rssl.common.forms.expressions.js.JSMap;
import com.rssl.common.forms.expressions.js.RhinoExpressionBase;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author komarov
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class RhinoExpression extends RhinoExpressionBase
{
	private static final Scriptable sharedScope = createSharedScope();

	/**
	 * @param expression выражение
	 */
	public RhinoExpression(String expression)
	{
		super(expression);
	}

	private static Scriptable createSharedScope()
	{
		return (Scriptable) Context.call(new ContextAction()
		{
			public Object run(Context cx)
			{
				try
				{
					Scriptable sharedScope = cx.initStandardObjects(null, true);
					ScriptableObject.defineClass(sharedScope, JSMap.class);
					return sharedScope;
				}
				catch (Exception e)
				{
					throw new RuntimeException(e);
				}
			}
		});
	}

	public Scriptable getSharedScope()
	{
		return sharedScope;
	}
}
