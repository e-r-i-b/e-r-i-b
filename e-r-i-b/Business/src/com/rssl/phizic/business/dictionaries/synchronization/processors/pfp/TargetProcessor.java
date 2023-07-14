package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей целей
 */

public class TargetProcessor extends PFPProcessorBase<Target>
{
	@Override
	protected Class<Target> getEntityClass()
	{
		return Target.class;
	}

	@Override
	protected Target getNewEntity()
	{
		return new Target();
	}

	@Override
	protected void update(Target source, Target destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
		destination.setOnlyOne(source.isOnlyOne());
		destination.setLaterAll(source.isLaterAll());
		destination.setLaterLoans(source.isLaterLoans());
	}

	@Override
	protected void doRemove(Target localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
