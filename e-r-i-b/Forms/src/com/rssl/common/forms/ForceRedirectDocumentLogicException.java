package com.rssl.common.forms;

/**
 * User: Moshenko
 * Date: 25.10.2012
 * Time: 12:05:39
 * * Логическая ошибка при работе с докуметом,
 *   говорящая что надо осуществить переход по переданному урл.
 */
public class ForceRedirectDocumentLogicException extends RedirectDocumentLogicException
{
	private String redirectUrl;


	public ForceRedirectDocumentLogicException(String cause,String url)
	{
		super(cause);
		redirectUrl = url;
	}

	public ForceRedirectDocumentLogicException(Throwable cause, String redirectUrl)
	{
		super(cause);
		this.redirectUrl = redirectUrl;
	}

	public ForceRedirectDocumentLogicException(String message, Throwable cause, String redirectUrl)
	{
		super(message, cause);
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl()
	{
		return redirectUrl;
	}


}
