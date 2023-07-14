package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.payment.link;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.GroupRiskProcessor;
import com.rssl.phizic.business.limits.link.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author komarov
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 */
public abstract class LimitPaymentsLinkBaseProcessor<T extends LimitPaymentsLinkBase> extends ProcessorBase<T>
{
	@Override
	protected T getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("tb", uuid));
		return simpleService.<T>findSingle(criteria);
	}

	@Override
	protected void update(T source, T destination) throws BusinessException
	{
		destination.setGroupRisk(getLocalVersionByGlobal(source.getGroupRisk(), GroupRiskProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME));
		destination.setTb(source.getTb());
	}
}
