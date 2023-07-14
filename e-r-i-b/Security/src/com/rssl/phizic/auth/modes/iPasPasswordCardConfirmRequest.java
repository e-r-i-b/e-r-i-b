package com.rssl.phizic.auth.modes;

import java.util.List;

/**
 * Author: Moshenko
 * Date: 26.04.2010
 * Time: 15:10:48
 */
public class iPasPasswordCardConfirmRequest extends PasswordCardConfirmRequest
{
	private String SID;
	private boolean mobilAvelible = false;
	private String passwordsLeft;

	public String getPasswordsLeft()
	{
		return passwordsLeft;
	}

	public void setPasswordsLeft(String passwordsLeft)
	{
		this.passwordsLeft = passwordsLeft;
	}

	public boolean getMobilAvelible()
	{
		return mobilAvelible;
	}

	public void setMobilAvelible(boolean mobilAvelible)
	{
		this.mobilAvelible = mobilAvelible;
	}

	public String getSID()
	{
		return SID;
	}

	public void setSID(String SID)
	{
		this.SID = SID;
	}

	/**
	 * @param cardNumber номер карты паролей
	 * @param passwordNumber номер пароля в карте
	 */
	public iPasPasswordCardConfirmRequest(String cardNumber, Integer passwordNumber,String SID,String passwordsLeft, List<String> additionInfo)
	{
		super(cardNumber, passwordNumber,additionInfo);
		this.SID = SID;
		this.passwordsLeft = passwordsLeft;
	}

}
