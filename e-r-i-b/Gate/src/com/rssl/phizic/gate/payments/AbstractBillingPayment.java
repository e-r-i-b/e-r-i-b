package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.documents.AbstractTransfer;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * Общий интерфейс для биллинговых платежей
 */
public interface AbstractBillingPayment extends AbstractTransfer
{
	/**
	 * Код точки получателя перевода
	 * @return Код точки получателя перевода
	 */
	String getReceiverPointCode();

	/**
	 * @return межблочный идентификатор поставщика услуг
	 */
	String getMultiBlockReceiverPointCode();

	/**
	 * @return внутренний идентификатор поставщика
	 */
	Long getReceiverInternalId();
}
