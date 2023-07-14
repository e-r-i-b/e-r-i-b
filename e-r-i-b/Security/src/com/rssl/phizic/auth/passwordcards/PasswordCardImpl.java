package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.auth.Login;

import java.util.Date;
import java.util.Calendar;

/**
 * @author Roshka
 * @ created 01.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class PasswordCardImpl implements PasswordCard
{

	private String state;

	private Long   id;
	private Login  login;
	private String number;
	private Calendar issueDate;
	private Calendar   activationDate;
	private Long   passwordsCount;
	private Long   validPasswordsCount;
	private Long   wrongAttempts;
	private String blockReason;
	private String blockType;

	public Long getId ()
	{
		return id;
	}

	public void setId ( Long id )
	{
		this.id=id;
	}

	public Login getLogin ()
	{
		return login;
	}

	public void setLogin ( Login login )
	{
		this.login=login;
	}

	public String getNumber ()
	{
		return number;
	}

	public void setNumber ( String number )
	{
		this.number=number;
	}

	public Calendar getIssueDate ()
	{
		return issueDate;
	}

	public void setIssueDate ( Calendar issueDate )
	{
		this.issueDate=issueDate;
	}

	public Calendar getActivationDate ()
	{
		return activationDate;
	}

	public void setActivationDate ( Calendar activationDate )
	{
		this.activationDate=activationDate;
	}

	public String getState ()
	{
		return state;
	}

	public void setState ( String state )
	{
		this.state=state;
	}

	public Long getPasswordsCount ()
	{
		return passwordsCount;
	}

	public void setPasswordsCount ( Long passwordsCount )
	{
		this.passwordsCount=passwordsCount;
	}

	public Long getValidPasswordsCount ()
	{
		return validPasswordsCount;
	}

	public void setValidPasswordsCount ( Long validPasswordsCount )
	{
		this.validPasswordsCount=validPasswordsCount;
	}

	public Long getWrongAttempts ()
	{
		return wrongAttempts;
	}

	public void setWrongAttempts ( Long wrongAttempts )
	{
		this.wrongAttempts=wrongAttempts;
	}

	public boolean isActive ()
	{
		return state.equals(STATE_ACTIVE);
	}

	public boolean isBlocked()
	{
		return state.equals(STATE_BLOCKED);
	}

	public boolean isExhausted()
	{
		return state.equals(STATE_EXHAUSTED);
	}

	public String getBlockType()
	{
		return blockType;
	}

	public void setBlockType(String blockType)
	{
		this.blockType = blockType;
	}

	public String getBlockReason()
	{
		return blockReason;
	}

	public void setBlockReason(String blockReason)
	{
		this.blockReason = blockReason;
	}

}
