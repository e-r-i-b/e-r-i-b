package com.rssl.phizic.web.client.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Kosyakov
 * @ created 23.12.2005
 * @ $Author: erkin $
 * @ $Revision: 24884 $
 */
@SuppressWarnings({"JavaDoc"})
public class ConfirmCardLoginForm extends ActionFormBase
{
	private Integer passwordNumber;
	private String  passwordCard;
	private String  password;

	public Integer getPasswordNumber ()
	{
		return passwordNumber;
	}

	public void setPasswordNumber ( Integer passwordNumber )
	{
		this.passwordNumber = passwordNumber;
	}

	public String getPasswordCard()
	{
		return passwordCard;
	}

	public void setPasswordCard(String passwordCard)
	{
		this.passwordCard = passwordCard;
	}

	public String getPassword ()
	{
		return password;
	}

	public void setPassword ( String password )
	{
		this.password=password;
	}

}
