package com.rssl.phizic.web.client.login;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * форма для странице по помощи к входу
 * @author basharin
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */

public class LoginErrorForm extends ActionFormBase
{
	private String urlRecover;
	private String urlRegistration;
	private boolean visibleMultipleRegistrationPart;

	public void setUrlRecover(String urlRecover)
	{
		this.urlRecover = urlRecover;
	}

	public String getUrlRecover()
	{
		return urlRecover;
	}

	public void setUrlRegistration(String urlRegistration)
	{
		this.urlRegistration = urlRegistration;
	}

	public String getUrlRegistration()
	{
		return urlRegistration;
	}

	public boolean isVisibleMultipleRegistrationPart()
	{
		return visibleMultipleRegistrationPart;
	}

	public void setVisibleMultipleRegistrationPart(boolean visibleMultipleRegistrationPart)
	{
		this.visibleMultipleRegistrationPart = visibleMultipleRegistrationPart;
	}
}
