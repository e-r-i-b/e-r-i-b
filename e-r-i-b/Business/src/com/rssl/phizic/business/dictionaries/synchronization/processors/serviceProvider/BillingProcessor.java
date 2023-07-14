package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author lepihina
 * @ created 29.01.14
 * $Author$
 * $Revision$
 *
 * Процессор записей биллинговых систем
 */
public class BillingProcessor extends ProcessorBase<Billing>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "synchKey";

	@Override
	protected Class<Billing> getEntityClass()
	{
		return Billing.class;
	}

	@Override
	protected Billing getNewEntity()
	{
		return new Billing();
	}

	@Override
	protected Billing getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(Billing source, Billing destination) throws BusinessException
	{
		destination.setSynchKey(source.getSynchKey());
		destination.setCode(source.getCode());
		destination.setName(source.getName());
		destination.setNeedUploadJBT(source.isNeedUploadJBT());
		destination.setAdapterUUID(source.getAdapterUUID());
		destination.setTemplateState(source.getTemplateState());
	}
}
