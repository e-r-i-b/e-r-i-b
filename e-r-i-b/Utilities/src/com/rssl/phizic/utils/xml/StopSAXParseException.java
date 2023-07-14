package com.rssl.phizic.utils.xml;

import org.xml.sax.SAXException;

/**
 * @author Erkin
 * @ created 04.07.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение для остановки работы SAX-парсера
 */
public class StopSAXParseException extends SAXException
{
	public StopSAXParseException()
	{
	}

	public StopSAXParseException(String message)
	{
		super(message);
	}

	public StopSAXParseException(Exception e)
	{
		super(e);
	}

	public StopSAXParseException(String message, Exception e)
	{
		super(message, e);
	}
}
