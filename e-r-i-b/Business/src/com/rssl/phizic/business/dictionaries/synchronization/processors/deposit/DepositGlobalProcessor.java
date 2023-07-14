package com.rssl.phizic.business.dictionaries.synchronization.processors.deposit;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositGlobal;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author mihaylov
 * @ created 03.03.14
 * @ $Author$
 * @ $Revision$
 *
 *  Процессор глобального описания депозитных продуктов
 */
public class DepositGlobalProcessor extends ProcessorBase<DepositGlobal>
{
	private static final String CURRENT = "current";

	@Override
	protected Class<DepositGlobal> getEntityClass()
	{
		return DepositGlobal.class;
	}

	@Override
	protected DepositGlobal getNewEntity()
	{
		DepositGlobal depositGlobal = new DepositGlobal();
		depositGlobal.setKey(CURRENT);
		return depositGlobal;
	}

	@Override
	protected DepositGlobal getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("key", CURRENT));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(DepositGlobal source, DepositGlobal destination) throws BusinessException
	{
		destination.setListTransformation(source.getListTransformation());
		destination.setCalculatorTransformation(source.getCalculatorTransformation());
		destination.setAdminListTransformation(source.getAdminListTransformation());
		destination.setAdminEditTransformation(source.getAdminEditTransformation());
		destination.setDefaultDetailsTransformation(source.getDefaultDetailsTransformation());
		destination.setMobileListTransformation(source.getMobileListTransformation());
		destination.setMobileDetailsTransformation(source.getMobileDetailsTransformation());
		destination.setVisibilityDetailsTransformation(source.getVisibilityDetailsTransformation());
        destination.setDepositPercentRateTransformation(source.getDepositPercentRateTransformation());
	}
}
