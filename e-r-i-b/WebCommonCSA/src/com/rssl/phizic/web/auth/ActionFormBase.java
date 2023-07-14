package com.rssl.phizic.web.auth;

import org.apache.struts.action.ActionForm;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 *
 * базовая форма для ЦСА МАПИ
 */
public abstract class ActionFormBase extends ActionForm
{
	private String host;
	private String token;

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}
