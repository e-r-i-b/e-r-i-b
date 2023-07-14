package com.rssl.phizic.business.dictionaries.synchronization.processors.region;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.synchronization.processors.aggr.PostAggregationProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author komarov
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор регионов
 */

public class RegionProcessor extends PostAggregationProcessorBase<Region>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";

	@Override
	protected Class<Region> getEntityClass()
	{
		return Region.class;
	}

	@Override
	protected Region getNewEntity()
	{
		return new Region();
	}

	@Override
	protected Region getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(Region source, Region destination) throws BusinessException
	{
		destination.setCodeTB(source.getCodeTB());
		destination.setName(source.getName());
		destination.setParent(getLocalVersionByGlobal(source.getParent(), MULTI_BLOCK_RECORD_ID_FIELD_NAME));
		destination.setSynchKey(source.getSynchKey());
		destination.setProviderCodeATM(source.getProviderCodeATM());
		destination.setProviderCodeMAPI(source.getProviderCodeMAPI());
		destination.setUuid(source.getUuid());
	}
}
