package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;

/**
 * Author: Moshenko
 * Date: 30.04.2010
 * Time: 13:26:03
 */
public class iPasSmsPasswordConfirmRequest extends SmsPasswordConfirmRequest
{
	//признак того, что отправлено удачно sms
	private boolean send = false;
	private boolean isConfirmEnter = false;  //для настроек видимости продуктов, признак того, что подтверждали вход на страницу

	public iPasSmsPasswordConfirmRequest(ConfirmableObject confirmableObject,boolean send)
	{
		this.confirmableObject = confirmableObject;
		this.send              = send;
	}

	public iPasSmsPasswordConfirmRequest(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;

	}

	public boolean isSend()
	{
		return send;
	}

	public void setSend(boolean send)
	{
		this.send = send;
	}

	public boolean isConfirmEnter()
	{
		return isConfirmEnter;
	}

	public void setConfirmEnter(boolean confirmEnter)
	{
		isConfirmEnter = confirmEnter;
	}
}
