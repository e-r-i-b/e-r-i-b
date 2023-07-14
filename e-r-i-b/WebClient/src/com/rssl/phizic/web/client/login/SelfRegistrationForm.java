package com.rssl.phizic.web.client.login;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма уведомлениея о повторном входе.
 *
 * @author bogdanov
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class SelfRegistrationForm extends EditFormBase
{
	private String preRegistrationMessage;         //Сообщение о пререгистрации.
	private String titlePreRegistrationMessage;    //Заголовок сообщения о пререгистрации.
	private boolean hardRegistrationMode;
	private String pageToken;
	private String urlRegistration;
	private boolean denyMultipleRegistration;
	private int hintDelay;

	public String getPreRegistrationMessage()
	{
		return preRegistrationMessage;
	}

	public void setPreRegistrationMessage(String preRegistrationMessage)
	{
		this.preRegistrationMessage = preRegistrationMessage;
	}	

	public String getTitlePreRegistrationMessage()
	{
		return titlePreRegistrationMessage;
	}

	public void setTitlePreRegistrationMessage(String titlePreRegistrationMessage)
	{
		this.titlePreRegistrationMessage = titlePreRegistrationMessage;
	}

	public void setHardRegistrationMode(boolean hardRegistrationMode)
	{
		this.hardRegistrationMode = hardRegistrationMode;
	}

	public boolean isHardRegistrationMode()
	{
		return hardRegistrationMode;
	}

	public String getPageToken()
	{
		return pageToken;
	}

	public void setPageToken(String pageToken)
	{
		this.pageToken = pageToken;
	}

	public void setUrlRegistration(String urlRegistration)
	{
		this.urlRegistration = urlRegistration;
	}

	public String getUrlRegistration()
	{
		return urlRegistration;
	}

	public void setDenyMultipleRegistration(boolean denyMultipleRegistration)
	{
		this.denyMultipleRegistration = denyMultipleRegistration;
	}

	public boolean isDenyMultipleRegistration()
	{
		return denyMultipleRegistration;
	}

	public int getHintDelay()
	{
		return hintDelay;
	}

	public void setHintDelay(int hintDelay)
	{
		this.hintDelay = hintDelay;
	}
}
