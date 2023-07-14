package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.loans.products.LoanProduct;

/**
 * Рестрикшн на работу с подпиской на инвойсы
 * @author niculichev
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface InvoiceSubscriptionRestriction extends Restriction
{
	/**
	 * Попадает ли под ограничение подписка на инвойсы
	 * @param invoiceSubscription сущность подписки на инвойсы
	 * @return true - не попадает
	 */
	boolean accept(InvoiceSubscription invoiceSubscription);
}
