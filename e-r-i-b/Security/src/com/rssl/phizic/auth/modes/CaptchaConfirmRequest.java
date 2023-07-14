package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;

/**
 * @author Krenev
 * @ created 26.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class CaptchaConfirmRequest implements ConfirmRequest
{
	private String captchaId;

	public CaptchaConfirmRequest(String code)
	{
		this.captchaId = code;
	}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.captcha;
	}

	public boolean isError()
	{
		return false;
	}

	public boolean isErrorFieldPassword()
	{
		return false;
	}

	public void setErrorFieldPassword(boolean error)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public String getErrorMessage()
	{
		return null;
	}

	public void setErrorMessage(String errorMessage)
	{		
	}

	public List<String> getMessages()
	{
		return null;
	}

	public void addMessage(String message)
	{		
	}

	public List<String> getAdditionInfo()
	{
		return null;
	}

	public String getCaptchaId()
	{
		return captchaId;
	}

	public boolean isPreConfirm()
	{
		return true;
	}

	public void setPreConfirm(boolean isPreConfirm)
	{
	}

	public void resetMessages()
	{
	}
}
