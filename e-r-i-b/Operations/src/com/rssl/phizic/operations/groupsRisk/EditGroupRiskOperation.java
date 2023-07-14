package com.rssl.phizic.operations.groupsRisk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.MultiInstanceGroupRiskService;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author basharin
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 * редактирование групп риска
 */

public class EditGroupRiskOperation extends EditDictionaryEntityOperationBase<GroupRisk, DepartmentRestriction>
{
	private static MultiInstanceGroupRiskService groupRiskService = new MultiInstanceGroupRiskService();

	private GroupRisk groupRisk;

	/**
	 * @return банк
	 */
	public GroupRisk getEntity()
	{
		return groupRisk;
	}

	/**
	 * @param groupRisk устанавливает банк
	 */
	public void setGroupRisk(GroupRisk groupRisk)
	{
		this.groupRisk = groupRisk;
	}


	public void initialize(Long id) throws BusinessException
	{
		groupRisk = groupRiskService.findById(id, getInstanceName());
		if (groupRisk == null)
		{
			throw new ResourceNotFoundBusinessException("Не найдена группа риска с указаным id", GroupRisk.class);
		}
	}

	public void initializeNew()
	{
		groupRisk = new GroupRisk();
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		if (groupRisk.getIsDefault())
		{
			if (groupRiskService.isOtherDefaultGroupRisk(groupRisk.getId(), getInstanceName()))
			{
				throw new BusinessLogicException("В системе уже есть группа с признаком – по умолчанию");
			}
		}

		groupRiskService.addOrUpdate(groupRisk, getInstanceName());
	}
}

