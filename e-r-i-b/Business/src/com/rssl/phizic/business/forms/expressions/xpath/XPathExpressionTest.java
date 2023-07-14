package com.rssl.phizic.business.forms.expressions.xpath;

import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Krenev
 * @ created 31.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class XPathExpressionTest extends BusinessTestCaseBase
{
	public void test()
	{
		XPathExpression exp = new XPathExpression("phiz:document('test.xml')//test-nested-node");
		Object actual = exp.evaluate(Collections.EMPTY_MAP);
		assertEquals("test-nested-node-value", actual);

		exp = new XPathExpression("phiz:document('test.xml')//test-node[@test-attribute='testattributevalue2']/text()");
		actual = exp.evaluate(Collections.EMPTY_MAP);
		assertEquals("testnodeValue2", actual);

		exp = new XPathExpression("phiz:document('test.xml')//test-node[@test-attribute=$value]/text()");
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("value","testattributevalue2");
		actual = exp.evaluate(values);
		assertEquals("testnodeValue2", actual);

		exp = new XPathExpression("phiz:document('test.xml')//test-node[@test-attribute=$value]/text()");
		values = new HashMap<String, Object>();
		values.put("value1","testattributevalue2");
		actual = exp.evaluate(values);
		assertEquals("", actual);
	}
}
