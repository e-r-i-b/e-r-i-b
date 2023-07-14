package com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters;

import java.util.regex.Pattern;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Адаптер для элементов, содержимое которых должно быть заключено
 * в блок CDATA
 *
 * @author Balovtsev
 * @since 24.04.14
 */
public class CDATAXmlAdapter extends XmlAdapter<String, String>
{
	private final Pattern XML_CHARS = Pattern.compile("[&<>]");

	@Override
	public String unmarshal(String fieldValue) throws Exception
	{
		return fieldValue;
	}

	@Override
	public String marshal(String fieldValue) throws Exception
	{
		if (XML_CHARS.matcher(fieldValue).find())
		{
			return "<![CDATA[" + fieldValue + "]]>";
		}
		else
		{
			return fieldValue;
		}
	}
}