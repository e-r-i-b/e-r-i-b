package com.rssl.phizic.web.commissions;

import com.rssl.phizic.business.commission.CommissionType;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 12.09.2007
 * @ $Author: erkin $
 * @ $Revision: 24884 $
 */

public class ListCommissionForm extends ActionFormBase
{
	private Map rulesByType;
	private List<CommissionType> types;

	public Map getRulesByType()
	{
		return rulesByType;
	}

	public void setRulesByType(Map rulesByType)
	{
		this.rulesByType = rulesByType;
	}

	public List<CommissionType> getTypes()
	{
		return types;
	}

	public void setTypes(List<CommissionType> types)
	{
		this.types = types;
	}
}