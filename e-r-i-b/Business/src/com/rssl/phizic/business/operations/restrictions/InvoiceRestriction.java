package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.basket.invoice.Invoice;

/**
 * @author osminin
 * @ created 10.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �� ������ �� �������� (������)
 */
public interface InvoiceRestriction extends Restriction
{
	/**
	 * �������� �� ��� ����������� ������
	 * @param invoice ������
	 * @return true - �� �������
	 */
	boolean accept(Invoice invoice);
}
