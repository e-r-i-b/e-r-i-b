package com.rssl.common.forms.expressions;

import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 25.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class ExpressionFactoryTest extends RSSLTestCaseBase
{

	public void testSimpleExpression()
	{
		ExpressionFactory factory = new ExpressionFactory();
		Expression expression = factory.newExpression("1+2==3");
		Boolean value = (Boolean) expression.evaluate(new HashMap<String, Object>());
		assertTrue(value);

		expression = factory.newExpression("2*2==5");
		value = (Boolean) expression.evaluate(new HashMap<String, Object>());
		assertTrue(!value);
	}
}
