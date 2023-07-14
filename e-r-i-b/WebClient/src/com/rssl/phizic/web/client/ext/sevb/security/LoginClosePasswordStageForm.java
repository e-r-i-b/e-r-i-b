package com.rssl.phizic.web.client.ext.sevb.security;

import com.rssl.phizic.web.security.LoginForm;

/**
 * @author Omeliyanchuk
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoginClosePasswordStageForm extends LoginForm
{
	/**
	 * ��������� ������, ��������������� �� �������
	 */
	private String serverRandom;
	/**
	 * ��������� ������, ��������������� �� ������� 
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
}
