package com.rssl.phizic.business;

/**
 * User: Moshenko
 * Date: 25.10.2012
 * Time: 11:48:54
 * Логическая ошибка говорящая что надо осуществить переход по переданному урл.
 */
public class ForceRedirectBusinessLogicException extends RedirectBusinessLogicException
{
	private String redirectUrl;


	public ForceRedirectBusinessLogicException(String cause,String url)
	{
		super(cause);
		redirectUrl = url;
	}

	public ForceRedirectBusinessLogicException(Throwable cause, String redirectUrl)
	{
		super(cause);
		this.redirectUrl = redirectUrl;
	}

	public ForceRedirectBusinessLogicException(String message, Throwable cause, String redirectUrl)
	{
		super(message, cause);
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl()
	{
		return redirectUrl;
	}

}
