package com.rssl.phizic.business.clients.list.parsers;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый парсер
 */

public abstract class ParserBase implements ForeachElementAction
{
	protected String getStringValue(Element parent, String tagName)
	{
		return XmlHelper.getSimpleElementValue(parent, tagName);
	}

	protected Long getLongValue(Element parent, String tagName)
	{
		String stringValue = getStringValue(parent, tagName);
		if (StringHelper.isEmpty(stringValue))
			return null;
		return Long.valueOf(stringValue);
	}

	protected <T extends Enum<T>> T getEnumTypeValue(Element parent, String tagName, Class<T> enumClass)
	{
		String stringValue = getStringValue(parent, tagName);
		if (StringHelper.isEmpty(stringValue))
			return null;
		return Enum.valueOf(enumClass, stringValue);
	}

	protected Calendar getCalendarValue(Element parent, String tagName)
	{
		String stringValue = getStringValue(parent, tagName);
		if (StringHelper.isEmpty(stringValue))
			return null;
		return XMLDatatypeHelper.parseDateTime(stringValue);
	}
}
