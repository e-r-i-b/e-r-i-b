package com.rssl.phizic.auth.modes;

/**
 * Response ��� ������������� ��������
 * @author basharin
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */

public class OneTimePasswordConfirmResponse implements ConfirmResponse
{
	private String  password;


	public OneTimePasswordConfirmResponse(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}
}
