package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategory;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей возрастных категорий
 */

public class AgeCategoryProcessor extends PFPProcessorBase<AgeCategory>
{
	@Override
	protected Class<AgeCategory> getEntityClass()
	{
		return AgeCategory.class;
	}

	@Override
	protected AgeCategory getNewEntity()
	{
		return new AgeCategory();
	}

	@Override
	protected void update(AgeCategory source, AgeCategory destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setMinAge(source.getMinAge());
		destination.setMaxAge(source.getMaxAge());
		destination.setWeight(source.getWeight());
	}
}
