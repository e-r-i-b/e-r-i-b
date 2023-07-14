package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;

/**
 * Рестрикшн для проверки принадлежности объекта учета клиенту
 * @author niculichev
 * @ created 15.06.14
 * @ $Author$
 * @ $Revision$
 */
public class OwnAccountingEntityRestriction implements AccountingEntityRestriction
{
	public boolean accept(AccountingEntity accountingEntity)
	{
		CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();
		return accountingEntity.getLoginId() == login.getId();
	}
}
