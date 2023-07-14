package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceType;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 *  Процессор записей типов страховых продуктов
 */

public class InsuranceTypeProcessor extends PFPProcessorBase<InsuranceType>
{
	@Override
	protected Class<InsuranceType> getEntityClass()
	{
		return InsuranceType.class;
	}

	@Override
	protected InsuranceType getNewEntity()
	{
		return new InsuranceType();
	}

	@Override
	protected void update(InsuranceType source, InsuranceType destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setDescription(source.getDescription());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
		destination.setParent(getLocalVersionByGlobal(destination.getParent()));
	}

	@Override
	protected void doRemove(InsuranceType localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
