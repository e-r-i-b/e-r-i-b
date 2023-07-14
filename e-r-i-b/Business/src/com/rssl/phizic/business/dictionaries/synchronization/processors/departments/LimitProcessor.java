package com.rssl.phizic.business.dictionaries.synchronization.processors.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.GroupRiskProcessor;
import com.rssl.phizic.business.limits.Limit;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author akrenev
 * @ created 27.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей лимитов
 */

public class LimitProcessor extends ProcessorBase<Limit>
{
	private static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";

	@Override
	protected Class<Limit> getEntityClass()
	{
		return Limit.class;
	}

	@Override
	protected Limit getNewEntity()
	{
		return new Limit();
	}

	@Override
	protected Limit getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Limit.class).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(Limit source, Limit destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setTb(source.getTb());
		destination.setType(source.getType());
		destination.setOperationType(source.getOperationType());
		destination.setRestrictionType(source.getRestrictionType());
		destination.setChannelType(source.getChannelType());
		destination.setCreationDate(source.getCreationDate());
		destination.setStartDate(source.getStartDate());
		destination.setAmount(source.getAmount());
		destination.setStatus(source.getStatus());
		destination.setOperationCount(source.getOperationCount());
		destination.setGroupRisk(getLocalVersionByGlobal(source.getGroupRisk(), GroupRiskProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME));
		destination.setState(source.getState());
		destination.setSecurityType(source.getSecurityType());
		destination.setEndDate(source.getEndDate());
	}
}
