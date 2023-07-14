package com.rssl.phizicgate.csaadmin.service.types;

import com.rssl.phizic.gate.login.LoginBlock;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 */

public class LoginBlockImpl implements LoginBlock
{
	private String reasonType;
	private String reasonDescription;
	private Calendar blockedFrom;
	private Calendar blockedUntil;
	private LoginImpl employee;

	public String getReasonType()
	{
		return reasonType;
	}

	public void setReasonType(String reasonType)
	{
		this.reasonType = reasonType;
	}

	public String getReasonDescription()
	{
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription)
	{
		this.reasonDescription = reasonDescription;
	}

	public Calendar getBlockedFrom()
	{
		return blockedFrom;
	}

	public void setBlockedFrom(Calendar blockedFrom)
	{
		this.blockedFrom = blockedFrom;
	}

	public Calendar getBlockedUntil()
	{
		return blockedUntil;
	}

	public void setBlockedUntil(Calendar blockedUntil)
	{
		this.blockedUntil = blockedUntil;
	}

	public LoginImpl getEmployee()
	{
		return employee;
	}

	public void setEmployee(LoginImpl employee)
	{
		this.employee = employee;
	}
}
