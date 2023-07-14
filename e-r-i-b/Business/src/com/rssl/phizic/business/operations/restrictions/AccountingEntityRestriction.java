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
	 * �������� �� ��� ����������� ������ �����
	 * @param accountingEntity �������� �������� �� �������
	 * @return true - �� ��������
	 */
	boolean accept(AccountingEntity accountingEntity);
}
