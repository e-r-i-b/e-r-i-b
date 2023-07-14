package com.rssl.phizic.messaging;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

import java.util.Calendar;

/**
 * XML-запрос
 */
public abstract class XmlMessage
{
	private final String message;

	private final Class requestClass;

	private final String requestUID;

	private final Calendar requestTime;

	protected XmlMessage(String message, Class requestClass, String requestUID, Calendar requestTime)
	{
		this.message = message;
		this.requestClass = requestClass;
		this.requestUID = requestUID;
		this.requestTime = requestTime;
	}

	public String getMessage()
	{
		return message;
	}

	public Class getRequestClass()
	{
		return requestClass;
	}

	public String getRequestUID()
	{
		return requestUID;
	}

	public Calendar getRequestTime()
	{
		return requestTime;
	}
}
