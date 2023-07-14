package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.loans.products.LoanProduct;

/**
 * ��������� �� ������ � ��������� �� �������
 * @author niculichev
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface InvoiceSubscriptionRestriction extends Restriction
{
	/**
	 * �������� �� ��� ����������� �������� �� �������
	 * @param invoiceSubscription �������� �������� �� �������
	 * @return true - �� ��������
	 */
	boolean accept(InvoiceSubscription invoiceSubscription);
}
