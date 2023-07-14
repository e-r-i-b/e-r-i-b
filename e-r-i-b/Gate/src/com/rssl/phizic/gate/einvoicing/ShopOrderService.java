package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Сервис для работы с данными e-invoicing.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopOrderService extends Service
{
	/**
	 * Связывает заказ с клиентом.
	 *
	 * @param orderUUID внешний идентификатор заказа.
	 * @param profile ФИО+ДУЛ+ДР+ТБ клиента.
	 */
	public void linkOrderToClient(String orderUUID, ShopProfile profile) throws GateException, GateLogicException;

	/**
	 * Возвращает заказ по внешнему идентификатору.
	 *
	 * @param orderUUID внешний идентификатор заказа.
	 * @return заказ.
	 */
	@Cachable(name="ShopOrderService.getOrder",keyResolver = OrdersKeyComposer.class)
	public ShopOrder getOrder(String orderUUID) throws GateException, GateLogicException;

	/**
	 * Возвращает заказ по внутреннему идентификатору.
	 * @param id внутрений идентификатор заказа.
	 * @return заказ.
	 */
	@Cachable(name="ShopOrderService.getOrderById",keyResolver = OrdersInternalKeyComposer.class)
	public ShopOrder getOrder(Long id) throws GateException, GateLogicException;

	/**
	 * Возвращает список заказов по истории изменения профиля.
	 *
	 * @param profiles история изменения профиля.
	 * @param dateFrom дата начала выборки.
	 * @param dateTo дата окончания выборки.
	 * @param dateDelayedTo дата ограничения выборки для отложенных.
	 * @param status статус заказа.
	 * @param amountFrom сумма от.
	 * @param amountTo сумма до.
	 * @param currency валюта.
	 * @return список заказов.
	 */
	@Cachable(name="ShopOrderService.getOrdersList",keyResolver = OrderListKeyComposer.class)
	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState ... status) throws GateException, GateLogicException;

	/**
	 * @param profiles история изменения профиля.
	 * @param dateFrom дата начала выборки.
	 * @param dateTo дата окончания выборки.
	 * @param dateDelayedTo статус заказа.
	 * @param amountFrom сумма от.
	 * @param amountTo сумма до.
	 * @param currency валюта.
	 * @param limit ограничение на количество отбираемых заказов
	 * @param status статусы заказов
	 * @return список заказов
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(name="ShopOrderSerrvice.getOrdersList",keyResolver = OrderListKeyForEmployeeComposer.class)
	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, Long limit, Boolean orderByDelayDate, OrderState... status) throws GateException, GateLogicException;

	/**
	 * Возвращает список заказов по истории изменения профиля. Для страницы "Платежи и переводы"
	 *
	 * @param profiles история изменения профиля.
	 * @param dateFrom дата начала выборки.
	 * @param dateTo дата окончания выборки.
	 * @param status статус заказа.
	 * @param amountFrom сумма от.
	 * @param amountTo сумма до.
	 * @param currency валюта.
	 * @return список заказов.
	 */
	@Cachable(name="ShopOrderService.getOrdersList",keyResolver = OrderListKeyForMainPageComposer.class)
	public List<ShopOrder> getOrdersByProfileHistoryForMainPage(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState ... status) throws GateException, GateLogicException;


	/**
	 * Возвращает описание заказа (список товаров или информация о брони авиабилетов)
	 *
	 * @param orderUuid внешний идентификатор заказа.
	 * @return xml строка с информацией о заказе.
	 */
	@Cachable(name="ShopOrderService.getOrderInfo",keyResolver = OrdersKeyComposer.class)
	public String getOrderInfo(String orderUuid) throws GateException, GateLogicException;

	/**
	 * Изменения статуса заказа.
	 *
	 * @param orderUUID внешний идентификатор отмены платежа/возврата товаров.
	 * @param newState новое состояние.
	 * @param nodeId номер блока, с которым необходимо связать заказ.
	 * @param utrrno Уникальный код операции в биллинге.
	 * @param paidBy тип карты списания (Mastercard, Visa, ...)
	 */
	public void changeOrderStatus(String orderUUID, OrderState newState, Long nodeId, String utrrno, String paidBy, Calendar delayDate) throws GateException, GateLogicException;

	/**
	 * Изменения статуса возврата товаров или отмены платежа..
	 *
	 * @param recallUUID внешний идентификатор отмены платежа/возврата товаров.
	 * @param newState новое состояние.
	 * @param utrrno Уникальный код операции в биллинге.
	 * @param recallType тип платежа.
	 */
	public void changeRecallStatus(String recallUUID, RecallState newState, String utrrno, RecallType recallType) throws GateException, GateLogicException;

	/**
	 * Возвращает информацию о выпущенных авиабилетах.
	 *
	 * @param orderUUID внешний идентификатор заказа.
	 * @return информация об авиябилетах.
	 */
	public String getTicketInfo(String orderUUID) throws GateException, GateLogicException;

	/**
	 * Пометить заказ как просмотренный.
	 * @param orderUUID - уид заказа
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void markViewed(String orderUUID) throws GateException, GateLogicException;

	/**
	 * Поиск списка КПУ по коду.
	 *
	 * @param facilitatorCode код поставщика услуг.
	 * @param firstResult начать с.
	 * @param maxResult всего записей.
	 * @return список КПУ.
	 */
	public List<FacilitatorProvider> findEndPointProviderByCode(String facilitatorCode, int firstResult, int maxResult) throws GateException, GateLogicException;

	/**
	 * Поиск списка КПУ по имени и/или ИНН.
	 *
	 * @param name имя поставщика услуг.
	 * @param inn ИНН.
	 * @param firstResult начать с.
	 * @param maxResult всего записей.
	 * @return список КПУ.
	 */
	public List<FacilitatorProvider> findEndPointProviderByName(String name, String inn, int firstResult, int maxResult) throws GateException, GateLogicException;

	/**
	 * Получение КПУ.
	 *
	 * @param providerId идентификатор поставщика услуг.
	 * @return КПУ.
	 */
	public FacilitatorProvider getEndPointProvider(long providerId) throws GateLogicException, GateException;

	/**
	 * Обновление КПУ.
	 *
	 * @param providerId идентификатор поставщика.
	 * @param mcheckoutEnabled разрешен ли mobileCheckout.
	 * @param eInvoicingEnabled разрешены ли платежи через EInvoicing.
	 * @param mbCheckEnabled разрешена ли проверка в мобильном банке.
	 */
	public void updateEndPointProvider(long providerId, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException;

	/**
	 * Обновление свойств КПУ для всех КПУ фасилитатора.
	 *
	 * @param facilitatorCode код фасилитатора.
	 * @param mcheckoutEnabled разрешен ли mobileCheckout.
	 * @param eInvoicingEnabled разрешены ли платежи через EInvoicing.
	 * @param mbCheckEnabled разрешена ли проверка номера телефона в мобильном банке.
	 */
	public void updateEndPointProviders(String facilitatorCode, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException;
}
