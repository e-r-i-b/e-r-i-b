package com.rssl.phizicgate.csaadmin.service.types;

/**
 * @author mihaylov
 * @ created 24.02.14
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeContextData
{
	private Long loginId;
	private boolean isAllTbAccess;

	public EmployeeContextData(Long loginId, boolean allTbAccess)
	{
		this.loginId = loginId;
		isAllTbAccess = allTbAccess;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public boolean isAllTbAccess()
	{
		return isAllTbAccess;
	}

	public void setAllTbAccess(boolean allTbAccess)
	{
		isAllTbAccess = allTbAccess;
	}

}
