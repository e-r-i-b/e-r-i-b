package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.basket.invoice.Invoice;

/**
 * @author osminin
 * @ created 10.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на работу за инвойсом (счетом)
 */
public interface InvoiceRestriction extends Restriction
{
	/**
	 * Попадает ли под ограничение инвойс
	 * @param invoice инвойс
	 * @return true - не попадет
	 */
	boolean accept(Invoice invoice);
}
