package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * Информация по отменам платежей/возвратам товаров по заказам.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopRecall
{
	/**
	* @return Идентификатор (PK)
	*/
	public String getUuid();

	/**
	* @return связанный заказ.
	*/
	public String getOrderUuid();

	/**
	* @return Внутренний идентификатор документа отмены/возврата у получателя
	*/
	public String getExternalId();

	/**
	* @return Идентификатор документа оплаты заказа в биллинге
	*/
	public String getUtrrno();

	/**
	* @return Внутренний статус отмены/возврата
	*/
	public RecallState getState();

	/**
	 * @return Код получателя
	 */
	public String getReceiverCode();

	/**
	 * @return Дата заказа
	 */
	public Calendar getDate();

	/**
	 * @return тип
	 */
	public RecallType getType();

	/**
	 * Сумма отмены платежа/возврата товара
	 * @return - сумма
	 */
	public Money getAmount();
}
