package com.rssl.phizic.operations.dictionaries.pfp.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления Цели
 */

public class RemoveTargetOperation extends RemoveDictionaryEntityOperationBase
{
	private static final TargetService targetService = new TargetService();
	private Target target;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		target = targetService.getById(id, getInstanceName());

		if (target == null)
			throw new ResourceNotFoundBusinessException("В системе не найдена цель с id: " + id, Target.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		targetService.remove(target, getInstanceName());
	}

	public Object getEntity()
	{
		return target;
	}
}
