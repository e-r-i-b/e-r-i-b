package com.rssl.phizic.web.ext.sbrf.security;

import com.rssl.phizic.web.security.LoginForm;

/**
 * @author eMakarov
 * @ created 05.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class LoginClosePasswordStageForm extends LoginForm
{
	private int maxLengthLogins;
	
	/**
	 * Случайная строка, сгенерированная на сервере
	 */
	private String serverRandom;
	/**
	 * Случайная строка, сгенерированная на клиенте
	 */
	private String clientRandom;

	public String getClientRandom()
	{
		return clientRandom;
	}

	public void setClientRandom(String clientRandom)
	{
		this.clientRandom = clientRandom;
	}

	public String getServerRandom()
	{
		return serverRandom;
	}

	public void setServerRandom(String serverRandom)
	{
		this.serverRandom = serverRandom;
	}

	public void setMaxLengthLogins(int maxLengthLogins)
	{
		this.maxLengthLogins = maxLengthLogins;
	}

	public int getMaxLengthLogins()
	{
		return maxLengthLogins;
	}
}
