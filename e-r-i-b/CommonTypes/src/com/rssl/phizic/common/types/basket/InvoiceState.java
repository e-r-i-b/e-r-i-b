package com.rssl.phizic.common.types.basket;

/**
 * @author osminin
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 *
 *  од состо€ни€ платежа в ј— AutoPay
 */
public enum InvoiceState
{
	NEW,            //получена нова€ задолженность
	CANCELED,       //не удалось получить задолженность или не удалось испольнить платеж
	DONE            //платеж успешно исполнен
}
