package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceCompany;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей страховых компаний
 */

public class InsuranceCompanyProcessor extends PFPProcessorBase<InsuranceCompany>
{
	@Override
	protected Class<InsuranceCompany> getEntityClass()
	{
		return InsuranceCompany.class;
	}

	@Override
	protected InsuranceCompany getNewEntity()
	{
		return new InsuranceCompany();
	}

	@Override
	protected void update(InsuranceCompany source, InsuranceCompany destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
	}

	@Override
	protected void doRemove(InsuranceCompany localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
