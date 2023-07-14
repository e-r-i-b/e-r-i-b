package com.rssl.phizic.business.forms.expressions.xpath;

import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathVariableResolver;

/**
 * @author Krenev
 * @ created 18.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class MapVariableResolver implements XPathVariableResolver
{
	private Map<String, Object> values;

	public MapVariableResolver(Map<String, Object> values)
	{
		this.values = values;
	}

	public Object resolveVariable(QName variableName)
	{
		Object value = values.get(variableName.getLocalPart());
		if (value == null)
		{
			return "";//TODO правильно и это?
		}
		return String.valueOf(value);
	}
}
