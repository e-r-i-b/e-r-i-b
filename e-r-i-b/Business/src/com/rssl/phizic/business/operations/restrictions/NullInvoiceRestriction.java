package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.basket.invoice.Invoice;

/**
 * @author muhin
 * Date: 20.06.15
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��� �������
 */
public class NullInvoiceRestriction implements InvoiceRestriction
{
	public boolean accept(Invoice invoice)
	{
		return true;
	}
}
