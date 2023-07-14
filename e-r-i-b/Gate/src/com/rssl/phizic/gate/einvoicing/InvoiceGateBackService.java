package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.math.BigDecimal;

/**
 * Интерфейс взаимодействия EInvoicing с ERIB и CSAAdmin.
 *
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface InvoiceGateBackService extends Service
{
	/**
	 * @param receiverCode код поставщика услуг (codeRecipientSBOL).
	 * @return активного поставщика услуг.
	 */
	public ShopProvider getActiveProvider(String receiverCode) throws GateException;

	/**
	 * Отправляет запрос на проведение отмены платежа.
	 *
	 * @param shopOrder заказ, по которому производится отмена платежа.
	 * @param amount сумма отмены.
	 * @param currencyCode валюта отмены.
	 * @param refundUuid идентификатор отмены.
	 */
	public void sendRefundOrder(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String refundUuid) throws GateException, GateLogicException;

	/**
	 * Отправляет запрос на проведение возврата товаров по заказу.
	 *
	 * @param shopOrder заказ, по которому производится возврат товаров.
	 * @param amount сумма возврата.
	 * @param currencyCode валюта возврата.
	 * @param returnGoodsUuid идентификатор возврата.
	 * @param returnedGoods список возвращаемых товаров.
	 */
	public void sendReturnGoods(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String returnGoodsUuid, String returnedGoods) throws GateException, GateLogicException;

	/**
	 * формирование документа оплаты заказа.
	 * Метод используется как первая стадия при проведении оплат mobile checkout заказов.
	 * @param order - заказ, по которому создается документ
	 */
	public void createOrderPayment(ShopOrder order) throws GateException, GateLogicException;

	/**
	 * отправка документа оплаты заказа в биллинг.
	 * Метод используется как вторая стадия при проведении оплат mobile checkout заказов.
	 * @param order - заказ, по которому был создан документ оплаты
	 */
	public void sendOrderPayment(ShopOrder order) throws GateException, GateLogicException;

	/**
	 * Передача списка билетов в ЕРИБ
	 * @param order - заказ
	 * @param ticketInfo - xml с описанием билетов
	 */
	public void sendTickets(ShopOrder order, String ticketInfo) throws GateException, GateLogicException;

	/**
	 * Уточнение статуса заказа
	 * Метод используется джобом уточнения статуса старых заказов, зависших в статусе "списание средств"
	 * @param order  - заказ
	 */
	public OrderStateInfo getOrderState(ShopOrder order) throws GateException, GateLogicException;

	/**
	 * Уточнение статуса отмен/возвратов
	 * Метод используется джобом уточнения статуса отмен/возвратов, зависших в статусе "принят"
	 * @param recall  - отмена/возврат
	 */
	public RecallStateInfo getRecallState(ShopRecall recall) throws GateException, GateLogicException;
}
