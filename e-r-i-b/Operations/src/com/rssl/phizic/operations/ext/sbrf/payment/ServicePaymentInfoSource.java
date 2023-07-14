package com.rssl.phizic.operations.ext.sbrf.payment;

import java.util.Map;

/**
 * @author krenev
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 * Источник предоставления информации для платежа оплаты услуг
 */
public interface ServicePaymentInfoSource
{
	/**
	 * Получить идентифкатор услуги
	 * @return идентифкатор услуги
	 */
	Long getServiceId();

	/**
	 * Получить идентифкатор поставщика
	 * @return идентифкатор поставвщика
	 */
	Long getProviderId();

	/**
	 * Получит ключевые поля
	 * @return  ключевые поля
	 */
	Map<String, Object> getKeyFields();

}
