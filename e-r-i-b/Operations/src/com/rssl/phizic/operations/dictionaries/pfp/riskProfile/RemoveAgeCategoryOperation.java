package com.rssl.phizic.operations.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategory;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategoryService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления возрастных категорий
 */

public class RemoveAgeCategoryOperation extends RemoveDictionaryEntityOperationBase
{
	private static final AgeCategoryService service = new AgeCategoryService();
	private AgeCategory category;

	public void initialize(Long id) throws BusinessException
	{
		category = service.getById(id, getInstanceName());
		if (category == null)
			throw new ResourceNotFoundBusinessException("В системе не найдено категории с id: " + id, AgeCategory.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		service.remove(category, getInstanceName());
	}

	public Object getEntity()
	{
		return category;
	}
}
