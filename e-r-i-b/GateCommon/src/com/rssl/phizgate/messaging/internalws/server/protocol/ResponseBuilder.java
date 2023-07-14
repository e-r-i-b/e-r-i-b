package com.rssl.phizgate.messaging.internalws.server.protocol;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.SAXException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * Постоитель ответов
 */

public class ResponseBuilder extends InternalMessageBuilder implements ResponseInfo
{
	private final String bodyTagName;
	private final int errorCode;
	private final String errorDescription;
	private final String uid;
	private final Calendar date;

	public ResponseBuilder(String bodyTagName) throws SAXException
	{
		this(bodyTagName, 0, null);
	}

	public ResponseBuilder(String bodyTagName, int errorCode, String errorDescription) throws SAXException
	{
		super();

		this.bodyTagName = bodyTagName;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		uid = new RandomGUID().getStringValue();
		date = Calendar.getInstance();

		openTag(Constants.MESSAGE_TAG);
		addParameter(Constants.MESSAGE_UID_TAG, uid);
		addParameter(Constants.MESSAGE_DATE_TAG, date);

		openTag(Constants.MESSAGE_STATUS_TAG);
		addParameter(Constants.MESSAGE_STATUS_CODE_TAG, errorCode);
		if (!StringHelper.isEmpty(errorDescription))
		{
			addParameter(Constants.MESSAGE_STATUS_DESCRIPTION_TAG, errorDescription);
		}
		closeTag();//Constants.MESSAGE_STATUS_TAG

		openTag(bodyTagName);
	}

	/**
	 * @return информация об ответе
	 */
	public ResponseInfo getResponceInfo()
	{
		if (!isFinished())
		{
			throw new IllegalStateException("Необходимо завершить постоение документа");
		}
		return this;
	}

	public String getType()
	{
		return bodyTagName;
	}

	public String getUID()
	{
		return uid;
	}

	public Calendar getDate()
	{
		return date;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}

	public String asString()
	{
		if (!isFinished())
		{
			throw new IllegalStateException("Необходимо завершить постоение документа");
		}
		return getWriter().toString();
	}
	/**
	 * закончить постоение ответа.
	 * @return this
	 */
	public ResponseBuilder end() throws SAXException
	{
		closeTag();//bodyTag
		closeTag();//Constants.MESSAGE_TAG
		finished();
		return this;
	}
}
