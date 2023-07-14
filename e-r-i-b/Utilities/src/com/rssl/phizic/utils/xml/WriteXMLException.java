package com.rssl.phizic.utils.xml;

/**
 * @author Erkin
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 * @see XMLWriter
 */

/**
 * Исключение при записи xml
 * @see XMLWriter
 */
class WriteXMLException extends RuntimeException
{
	WriteXMLException(String message)
	{
		super(message);
	}

	WriteXMLException(Throwable cause)
	{
		super(cause);
	}

	WriteXMLException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
