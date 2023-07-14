package com.rssl.phizic.context;

/**
 * @author mihaylov
 * @ created 23.02.14
 * @ $Author$
 * @ $Revision$
 */
public class CSAAdminEmployeeDataImpl implements MultiNodeEmployeeData
{
	private Long loginId;
	private boolean isAllTbAccess;

	public CSAAdminEmployeeDataImpl(Long loginId, boolean allTbAccess)
	{
		this.loginId = loginId;
		isAllTbAccess = allTbAccess;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public boolean isAllTbAccess()
	{
		return isAllTbAccess;
	}

}
