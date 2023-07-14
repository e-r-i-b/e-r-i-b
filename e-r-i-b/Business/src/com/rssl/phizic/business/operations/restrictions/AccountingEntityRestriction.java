package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;

/**
 * @author niculichev
 * @ created 15.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface AccountingEntityRestriction extends Restriction
{
	/**
	 * Попадает ли под ограничение объект учета
	 * @param accountingEntity сущность подписки на инвойсы
	 * @return true - не попадает
	 */
	boolean accept(AccountingEntity accountingEntity);
}
