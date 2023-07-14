package com.rssl.phizgate.messaging.internalws.client;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Базовый класс запроса в CSABack
 * @author niculichev
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class RequestDataBase implements RequestData
{
	protected InternalMessageBuilder createBuilder()
	{
		try
		{
			InternalMessageBuilder builder = new InternalMessageBuilder();
			builder.openTag(getName());
			return builder;
		}
		catch (SAXException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected Element createTag(Document request, String tagName, String tagValue)
	{
		Element tag = request.createElement(tagName);
		tag.appendChild(request.createTextNode(tagValue));
		return tag;
	}

	protected String format(Calendar calendar)
	{
		return XMLDatatypeHelper.formatDateTimeWithoutTimeZone(calendar);
	}

	protected String format(BigDecimal value)
	{
		if (value == null)
			return null;

		return value.toString();
	}
}
