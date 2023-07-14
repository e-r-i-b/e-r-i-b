package com.rssl.phizic.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Egorova
 * @ created 23.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoginBlock implements Serializable
{
	private Long    id;
	private CommonLogin login;
	private BlockingReasonType reasonType;
	private String reasonDescription;
	private Date blockedFrom;
	private Date blockedUntil;
	private Long employee;


	public CommonLogin getLogin()
	{
		return login;
	}

	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}

	public BlockingReasonType getReasonType()
	{
		return reasonType;
	}

	public void setReasonType(BlockingReasonType reasonType)
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

	public Date getBlockedFrom()
	{
		return blockedFrom;
	}

	public void setBlockedFrom(Date blockedFrom)
	{
		this.blockedFrom = blockedFrom;
	}

	public Date getBlockedUntil()
	{
		return blockedUntil;
	}

	public void setBlockedUntil(Date blockedUntil)
	{
		this.blockedUntil = blockedUntil;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getEmployee()
	{
		return employee;
	}

	public void setEmployee(Long employee)
	{
		this.employee = employee;
	}
}
