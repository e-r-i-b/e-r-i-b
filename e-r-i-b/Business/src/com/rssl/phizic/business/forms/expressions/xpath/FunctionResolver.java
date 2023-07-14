package com.rssl.phizic.business.forms.expressions.xpath;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionResolver;

/**
 * @author Krenev
 * @ created 17.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class FunctionResolver implements XPathFunctionResolver
{
	private static final Map<QName, XPathFunction> functions = new HashMap<QName, XPathFunction>();

	static
	{
		//инициализируем набор функций
		functions.put(new QName(NamespaceContext.PHIZIC_URI, "document", NamespaceContext.PHIZIC_PREFIX), new DocumentFunction());
	}

	public XPathFunction resolveFunction(QName functionName, int arity)
	{
		return functions.get(functionName);
	}
}
