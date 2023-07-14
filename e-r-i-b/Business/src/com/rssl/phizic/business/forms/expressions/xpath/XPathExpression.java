package com.rssl.phizic.business.forms.expressions.xpath;

import com.rssl.common.forms.expressions.Expression;


import java.io.Serializable;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 28.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class XPathExpression implements Expression, Serializable
{
	private static final XPathFactory xPathFactory = XPathFactory.newInstance();

	private String expression;

	public XPathExpression(String expression)
	{
		this.expression = expression;
	}

	/**
	 * Вычислить значение выражения
	 * @param form даные формы
	 * @return значение выражения
	 */
	public Object evaluate(Map<String, Object> form)
	{
		try
		{
			XPath xpath = xPathFactory.newXPath();
			xpath.setNamespaceContext(new NamespaceContext());
			xpath.setXPathFunctionResolver(new FunctionResolver());
			xpath.setXPathVariableResolver(new MapVariableResolver(form));
			javax.xml.xpath.XPathExpression xPathExpression = xpath.compile(expression);
			// Evaluate XPath xPathExpression
			return xPathExpression.evaluate((Document)null);
		}
		catch (XPathExpressionException e)
		{
			throw new RuntimeException(e);
		}
	}
}
