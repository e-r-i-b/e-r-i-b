package com.rssl.phizic.business.dictionaries.synchronization.processors.aggr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.aggr.AggregationService;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;

/**
 * @author krenev
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class PostAggregationProcessorBase<R extends MultiBlockDictionaryRecord>  extends ProcessorBase<R>
{

	@Override
	protected final void doSave(R localEntity) throws BusinessException, BusinessLogicException
	{
		super.doSave(localEntity);
		AggregationService.markNeedAggregation();
	}

	@Override
	protected final void doRemove(String uid) throws BusinessException, BusinessLogicException
	{
		super.doRemove(uid);
		AggregationService.markNeedAggregation();
	}
}
