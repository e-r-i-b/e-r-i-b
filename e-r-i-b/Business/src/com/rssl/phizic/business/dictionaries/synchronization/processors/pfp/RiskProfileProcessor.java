package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileService;

import java.util.HashMap;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей риск-профилей
 */

public class RiskProfileProcessor extends PFPProcessorBase<RiskProfile>
{
	private static final RiskProfileService riskProfileService = new RiskProfileService();

	@Override
	protected Class<RiskProfile> getEntityClass()
	{
		return RiskProfile.class;
	}

	@Override
	protected RiskProfile getNewEntity()
	{
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setProductsWeights(new HashMap<ProductType, Long>());
		return riskProfile;
	}

	@Override
	protected void update(RiskProfile source, RiskProfile destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setSegment(source.getSegment());
		destination.setMinWeight(source.getMinWeight());
		destination.setMaxWeight(source.getMaxWeight());
		destination.setDescription(source.getDescription());
		destination.updateProductsWeights(source.getProductsWeights());
		destination.setDeleted(source.isDeleted());
		destination.setIsDefault(source.getIsDefault());
	}

	@Override
	protected void doSave(RiskProfile localEntity) throws BusinessException, BusinessLogicException
	{
		riskProfileService.addOrUpdate(localEntity);
	}

	@Override
	protected void doRemove(RiskProfile localEntity) throws BusinessException, BusinessLogicException
	{
		riskProfileService.remove(localEntity);
	}
}
