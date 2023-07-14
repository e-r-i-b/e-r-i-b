package com.rssl.phizic.web.client.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Evgrafov
 * @ created 27.12.2006
 * @ $Author: erkin $
 * @ $Revision: 24884 $
 */
@SuppressWarnings({"JavaDoc"})
public class ConfirmCryptoLoginForm extends ActionFormBase
{
	private String certId;
	private String randomString;
	private String signature;

	public String getRandomString()
	{
		return randomString;
	}

	public void setRandomString(String randomString)
	{
		this.randomString = randomString;
	}

	public String getSignature()
	{
		return signature;
	}

	public void setSignature(String signature)
	{
		this.signature = signature;
	}


	public String getCertId()
	{
		return certId;
	}

	public void setCertId(String certId)
	{
		this.certId = certId;
	}
}