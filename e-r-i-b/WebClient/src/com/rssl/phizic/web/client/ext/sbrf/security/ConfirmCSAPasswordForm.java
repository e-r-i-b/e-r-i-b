package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Gainanov
 * @ created 12.11.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCSAPasswordForm extends ActionFormBase
{
	private boolean csa;
	private String path;
	private String token;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public boolean getCsa()
	{
		return csa;
	}

	public void setCsa(boolean csa)
	{
		this.csa = csa;
	}
}
