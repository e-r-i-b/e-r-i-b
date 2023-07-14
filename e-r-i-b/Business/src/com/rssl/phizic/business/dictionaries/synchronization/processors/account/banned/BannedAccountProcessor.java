package com.rssl.phizic.business.dictionaries.synchronization.processors.account.banned;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.bannedAccount.BannedAccount;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author komarov
 * @ created 20.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class BannedAccountProcessor extends ProcessorBase<BannedAccount>
{
	@Override
	protected Class<BannedAccount> getEntityClass()
	{
		return BannedAccount.class;
	}

	@Override
	protected BannedAccount getNewEntity()
	{
		return new BannedAccount();
	}

	@Override
	protected BannedAccount getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("uuid", uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(BannedAccount source, BannedAccount destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setAccountNumber(source.getAccountNumber());
		destination.setBanType(source.getBanType());
		destination.setBicList(source.getBicList());
	}
}
