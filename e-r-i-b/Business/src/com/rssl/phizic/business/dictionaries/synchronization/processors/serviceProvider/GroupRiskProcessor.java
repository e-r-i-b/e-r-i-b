package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.limits.GroupRisk;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author lepihina
 * @ created 30.01.14
 * $Author$
 * $Revision$
 *
 * Процессор записей групп риска
 */
public class GroupRiskProcessor extends ProcessorBase<GroupRisk>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "externalId";

	@Override
	protected Class<GroupRisk> getEntityClass()
	{
		return GroupRisk.class;
	}

	@Override
	protected GroupRisk getNewEntity()
	{
		return new GroupRisk();
	}

	@Override
	protected GroupRisk getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(GroupRisk source, GroupRisk destination) throws BusinessException
	{
		destination.setName(source.getName());
		destination.setIsDefault(source.getIsDefault());
		destination.setRank(source.getRank());
		destination.setExternalId(source.getExternalId());
	}
}
