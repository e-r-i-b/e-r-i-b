package com.rssl.common.forms.expressions.js;

import com.rssl.common.forms.expressions.Expression;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 25.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class RhinoExpressionTest extends RSSLTestCaseBase
{

	public void testSimpleExpression()
	{
		Expression expression = new RhinoExpression("1+2==3");
		Boolean value = (Boolean) expression.evaluate(new HashMap<String, Object>());
		assertTrue(value);

		expression = new RhinoExpression("2*2==5");
		value = (Boolean) expression.evaluate(new HashMap<String, Object>());
		assertTrue(!value);
	}

	public void testParamExpression()
	{
		Map<String, Object> form = new HashMap<String, Object>();
		form.put("value", "10");
		Expression expression = new RhinoExpression("form.value==10");

		Boolean value = (Boolean) expression.evaluate(form);
		assertTrue(value);

		form.put("value-q", "10");
		expression = new RhinoExpression("form['value-q']==10");
		value = (Boolean) expression.evaluate(form);

		expression = new RhinoExpression("form.value+1==10");
		value = (Boolean) expression.evaluate(form);
		assertTrue(!value);

		expression = new RhinoExpression("getFieldValue('value')==10");
		value = (Boolean) expression.evaluate(form);
		assertTrue(value);

		expression = new RhinoExpression("getFieldValue('value')+1==10");
		value = (Boolean) expression.evaluate(form);
		assertTrue(!value);
	}
}
