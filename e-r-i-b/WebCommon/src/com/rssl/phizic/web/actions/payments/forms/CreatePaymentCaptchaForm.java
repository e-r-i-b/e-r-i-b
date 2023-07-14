package com.rssl.phizic.web.actions.payments.forms;

/**
 * @author eMakarov
 * @ created 23.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class CreatePaymentCaptchaForm extends CreatePaymentForm
{
	private String captchaId;
	private String captchaCode;

	public String getCaptchaId()
	{
		return captchaId;
	}

	public void setCaptchaId(String captchaId)
	{
		this.captchaId = captchaId;
	}

	public String getCaptchaCode()
	{
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode)
	{
		this.captchaCode = captchaCode;
	}
}
