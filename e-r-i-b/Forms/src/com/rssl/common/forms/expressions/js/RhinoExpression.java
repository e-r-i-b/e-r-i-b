package com.rssl.common.forms.expressions.js;

import com.rssl.phizic.utils.ClassHelper;
import org.mozilla.javascript.*;

/**
 * @author Krenev
 * @ created 17.09.2007
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
					Class bankServiceClass = ClassHelper.loadClass("com.rssl.phizic.business.dictionaries.BankDictionaryService");
					ScriptableObject.putProperty(sharedScope, "bankService", bankServiceClass.newInstance());
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