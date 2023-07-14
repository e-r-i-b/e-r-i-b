package com.rssl.phizic.auth.modes;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 8559 $
 */

public class PasswordCardConfirmResponse implements ConfirmResponse
{
	private String password;

	public PasswordCardConfirmResponse(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}
}