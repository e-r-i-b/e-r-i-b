package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * @author basharin
 * @ created 06.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class GroupRiskService extends MultiInstanceGroupRiskService
{
	/**
	 * Возвращает все группы риска в системе ЕРИБ
	 * @return лист групп риска
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<GroupRisk> getAllGroupsRisk() throws BusinessException
	{
		return getAllGroupsRisk(null);
	}

	//получить группу риска с признаком "по умолчнанию"
	public GroupRisk getDefaultGroupRisk() throws BusinessException
	{
         return getDefaultGroupRisk(null);
	}
}
