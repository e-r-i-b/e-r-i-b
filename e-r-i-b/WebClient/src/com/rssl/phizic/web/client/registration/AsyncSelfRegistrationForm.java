package com.rssl.phizic.web.client.registration;

/**
 * форма для асинхронной передачи данных для самостоятельной регистрации
 * @author basharin
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncSelfRegistrationForm extends SelfRegistrationForm
{
	private String nameFieldError;
	private String textError;

	private String redirect;
	private String token;
	private long timer;
	private boolean needShowCaptcha;
	private String messageId;

	public String getNameFieldError()
	{
		return nameFieldError;
	}

	public void setNameFieldError(String nameFieldError)
	{
		this.nameFieldError = nameFieldError;
	}

	public String getTextError()
	{
		return textError;
	}

	public void setTextError(String textError)
	{
		this.textError = textError;
	}

	/**
	 * @return нужно ли показать капчу на форме.
	 */
	public boolean isNeedShowCaptcha()
	{
		return needShowCaptcha;
	}

	public void setNeedShowCaptcha(boolean needShowCaptcha)
	{
		this.needShowCaptcha = needShowCaptcha;
	}

	public String getRedirect()
	{
		return redirect;
	}

	public void setRedirect(String redirect)
	{
		this.redirect = redirect;
	}

	public long getTimer()
	{
		return timer;
	}

	public void setTimer(long timer)
	{
		this.timer = timer;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getToken()
	{
		return token;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public String getMessageId()
	{
		return messageId;
	}
}

