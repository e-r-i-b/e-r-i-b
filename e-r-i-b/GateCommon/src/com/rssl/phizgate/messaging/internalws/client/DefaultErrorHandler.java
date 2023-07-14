package com.rssl.phizgate.messaging.internalws.client;

import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DefaultErrorHandler<T extends Exception> implements ErrorHandler
{
	private Class<T> exceptionClass;

	public DefaultErrorHandler(Class<T> exceptionClass)
	{
		this.exceptionClass = exceptionClass;
	}

	public void process(String errorCode, String errorDescription, Document document) throws Exception
	{
		throw exceptionClass.getConstructor(String.class).newInstance(errorDescription);
	}
}
