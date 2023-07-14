package com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Адаптер для RGB-цвета
 * @author Jatsky
 * @ created 22.07.14
 * @ $Author$
 * @ $Revision$
 */

public class ColorXmlAdapter extends XmlAdapter<String, String>
{
	@Override
	public String unmarshal(String fieldValue) throws Exception
	{
		return fieldValue;
	}

	@Override
	public String marshal(String fieldValue) throws Exception
	{
		if (!fieldValue.startsWith("#"))
		{
			return "#" + fieldValue;
		}
		else
		{
			return fieldValue;
		}
	}
}
