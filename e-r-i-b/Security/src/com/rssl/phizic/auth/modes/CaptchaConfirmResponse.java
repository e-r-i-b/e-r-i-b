package com.rssl.phizic.auth.modes;

/**
 * @author Krenev
 * @ created 26.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class CaptchaConfirmResponse implements ConfirmResponse
{
	private String code;

	public CaptchaConfirmResponse(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
