package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodTypeService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsurancePeriodTypeOperation extends EditDictionaryEntityOperationBase
{
	private static final PeriodTypeService periodTypeService = new PeriodTypeService();
	private PeriodType periodType = new PeriodType();

	/**
	 * инициализаци€ операции
	 * @param id идентификатор типа периода
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		periodType = new PeriodType();
		if (id != null)
			periodType = periodTypeService.getById(id, getInstanceName());

		if(periodType == null)
			throw new ResourceNotFoundBusinessException("¬ системе не найден тип периода с id: " + id, PeriodType.class);
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		periodTypeService.addOrUpdate(periodType, getInstanceName());
	}

	public Object getEntity()
	{
		return periodType;
	}
}
