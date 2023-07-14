package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */
public class RestrictionFormBase extends ActionFormBase
{
	private Long     loginId;
	private Long     serviceId;
	private Long     operationId;
	private String[] selectedIds = new String[0];

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getOperationId()
	{
		return operationId;
	}

	public void setOperationId(Long operationId)
	{
		this.operationId = operationId;
	}

	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public Long getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}
}