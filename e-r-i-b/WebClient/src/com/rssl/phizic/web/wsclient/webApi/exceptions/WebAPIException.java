package com.rssl.phizic.web.wsclient.webApi.exceptions;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * Исключение для олицетворения ошибок приложения
 * возникающих в WebAPI, но несвязанных с логикой.
 * @author Jatsky
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class WebAPIException extends SystemException
{
	public WebAPIException()
	{
	}

	public WebAPIException(String message)
	{
		super(message);
	}

	public WebAPIException(Throwable cause)
	{
		super(cause);
	}

	public WebAPIException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
