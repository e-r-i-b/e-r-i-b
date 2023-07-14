package com.rssl.phizic.test.wsgateclient.webAPI.wsclient.exceptions;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * Исключение для олицетворения ошибок приложения
 * возникающих в WebAPI, но несвязанных с логикой.
 * @author Jatsky
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class WebAPIException  extends SystemException
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
