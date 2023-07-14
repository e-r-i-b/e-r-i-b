package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceTypeService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class RemoveInsuranceTypeOperation extends RemoveDictionaryEntityOperationBase
{
	private static final InsuranceTypeService insuranceTypeService = new InsuranceTypeService();
	private InsuranceType insuranceType = new InsuranceType();

	/**
	 * инициализация операции
	 * @param id идентификатор типа периода
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		insuranceType = insuranceTypeService.getById(id, getInstanceName());

		if(insuranceType == null)
			throw new ResourceNotFoundBusinessException("В системе не найден тип страховой программы с id: " + id, InsuranceType.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		try
		{
			insuranceTypeService.remove(insuranceType, getInstanceName());
		}
		catch (ConstraintViolationException ex)
		{
			throw new BusinessLogicException("Невозможно удалить тип продукта.", ex);
		}
	}

	public Object getEntity()
	{
		return insuranceType;
	}
}
