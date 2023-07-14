package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodTypeService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class RemoveInsurancePeriodTypeOperation extends RemoveDictionaryEntityOperationBase
{
	private static final PeriodTypeService periodTypeService = new PeriodTypeService();
	private PeriodType periodType = new PeriodType();

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		periodType = periodTypeService.getById(id, getInstanceName());

		if(periodType == null)
			throw new ResourceNotFoundBusinessException("В системе не найден тип периода с id: " + id, PeriodType.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		periodTypeService.remove(periodType, getInstanceName());
	}

	public Object getEntity()
	{
		return periodType;
	}
}
