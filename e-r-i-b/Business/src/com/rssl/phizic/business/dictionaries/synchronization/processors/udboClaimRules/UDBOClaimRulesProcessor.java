package com.rssl.phizic.business.dictionaries.synchronization.processors.udboClaimRules;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.connectUdbo.UDBOClaimRules;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * Класс для синхронизации условий заявлния о подключении УДБО
 * User: miklyaev
 * @ created 11.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class UDBOClaimRulesProcessor extends ProcessorBase<UDBOClaimRules>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";

	@Override
	protected Class<UDBOClaimRules> getEntityClass()
	{
		return UDBOClaimRules.class;
	}

	@Override
	protected UDBOClaimRules getNewEntity()
	{
		return new UDBOClaimRules();
	}

	@Override
	protected UDBOClaimRules getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(UDBOClaimRules source, UDBOClaimRules destination) throws BusinessException
	{
		destination.setStartDate(source.getStartDate());
		destination.setRulesText(source.getRulesText());
		destination.setUuid(source.getUuid());
	}
}
