package com.rssl.phizic.business.dictionaries.synchronization.processors.locale.banks;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.bank.locale.ResidentBankResourceService;
import com.rssl.phizic.business.dictionaries.bank.locale.ResidentBankResources;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author koptyaev
 * @ created 07.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class BankResourcesProcessor extends ProcessorBase<ResidentBankResources>
{
	private static final ResidentBankResourceService BANK_RESOURCE_SERVICE = new ResidentBankResourceService();

	@Override
	protected Class<ResidentBankResources> getEntityClass()
	{
		return ResidentBankResources.class;
	}

	@Override
	protected ResidentBankResources getNewEntity()
	{
		return new ResidentBankResources();
	}

	@Override
	protected ResidentBankResources getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("id", uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(ResidentBankResources source, ResidentBankResources destination) throws BusinessException
	{
		destination.setId(source.getId());
		destination.setLocaleId(source.getLocaleId());
		destination.setName(source.getName());
		destination.setPlace(source.getPlace());
		destination.setShortName(source.getShortName());
		destination.setAddress(source.getAddress());
	}
}
