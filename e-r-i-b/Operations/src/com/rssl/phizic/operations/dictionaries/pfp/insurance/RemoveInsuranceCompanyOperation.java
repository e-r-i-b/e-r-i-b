package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompany;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompanyService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class RemoveInsuranceCompanyOperation extends RemoveDictionaryEntityOperationBase
{
	private static final InsuranceCompanyService service = new InsuranceCompanyService();
	private InsuranceCompany company;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		company = service.getById(id, getInstanceName());

		if (company == null)
			throw new ResourceNotFoundBusinessException("В системе не найдена страховая компания с id: " + id, InsuranceCompany.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		service.remove(company, getInstanceName());
	}

	public Object getEntity()
	{
		return company;
	}
}
