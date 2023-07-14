package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * ������ ��� ������ � ������� e-invoicing.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopOrderService extends Service
{
	/**
	 * ��������� ����� � ��������.
	 *
	 * @param orderUUID ������� ������������� ������.
	 * @param profile ���+���+��+�� �������.
	 */
	public void linkOrderToClient(String orderUUID, ShopProfile profile) throws GateException, GateLogicException;

	/**
	 * ���������� ����� �� �������� ��������������.
	 *
	 * @param orderUUID ������� ������������� ������.
	 * @return �����.
	 */
	@Cachable(name="ShopOrderService.getOrder",keyResolver = OrdersKeyComposer.class)
	public ShopOrder getOrder(String orderUUID) throws GateException, GateLogicException;

	/**
	 * ���������� ����� �� ����������� ��������������.
	 * @param id ��������� ������������� ������.
	 * @return �����.
	 */
	@Cachable(name="ShopOrderService.getOrderById",keyResolver = OrdersInternalKeyComposer.class)
	public ShopOrder getOrder(Long id) throws GateException, GateLogicException;

	/**
	 * ���������� ������ ������� �� ������� ��������� �������.
	 *
	 * @param profiles ������� ��������� �������.
	 * @param dateFrom ���� ������ �������.
	 * @param dateTo ���� ��������� �������.
	 * @param dateDelayedTo ���� ����������� ������� ��� ����������.
	 * @param status ������ ������.
	 * @param amountFrom ����� ��.
	 * @param amountTo ����� ��.
	 * @param currency ������.
	 * @return ������ �������.
	 */
	@Cachable(name="ShopOrderService.getOrdersList",keyResolver = OrderListKeyComposer.class)
	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState ... status) throws GateException, GateLogicException;

	/**
	 * @param profiles ������� ��������� �������.
	 * @param dateFrom ���� ������ �������.
	 * @param dateTo ���� ��������� �������.
	 * @param dateDelayedTo ������ ������.
	 * @param amountFrom ����� ��.
	 * @param amountTo ����� ��.
	 * @param currency ������.
	 * @param limit ����������� �� ���������� ���������� �������
	 * @param status ������� �������
	 * @return ������ �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(name="ShopOrderSerrvice.getOrdersList",keyResolver = OrderListKeyForEmployeeComposer.class)
	public List<ShopOrder> getOrdersByProfileHistory(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, Calendar dateDelayedTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, Long limit, Boolean orderByDelayDate, OrderState... status) throws GateException, GateLogicException;

	/**
	 * ���������� ������ ������� �� ������� ��������� �������. ��� �������� "������� � ��������"
	 *
	 * @param profiles ������� ��������� �������.
	 * @param dateFrom ���� ������ �������.
	 * @param dateTo ���� ��������� �������.
	 * @param status ������ ������.
	 * @param amountFrom ����� ��.
	 * @param amountTo ����� ��.
	 * @param currency ������.
	 * @return ������ �������.
	 */
	@Cachable(name="ShopOrderService.getOrdersList",keyResolver = OrderListKeyForMainPageComposer.class)
	public List<ShopOrder> getOrdersByProfileHistoryForMainPage(List<ShopProfile> profiles, Calendar dateFrom, Calendar dateTo, BigDecimal amountFrom, BigDecimal amountTo, String currency, OrderState ... status) throws GateException, GateLogicException;


	/**
	 * ���������� �������� ������ (������ ������� ��� ���������� � ����� �����������)
	 *
	 * @param orderUuid ������� ������������� ������.
	 * @return xml ������ � ����������� � ������.
	 */
	@Cachable(name="ShopOrderService.getOrderInfo",keyResolver = OrdersKeyComposer.class)
	public String getOrderInfo(String orderUuid) throws GateException, GateLogicException;

	/**
	 * ��������� ������� ������.
	 *
	 * @param orderUUID ������� ������������� ������ �������/�������� �������.
	 * @param newState ����� ���������.
	 * @param nodeId ����� �����, � ������� ���������� ������� �����.
	 * @param utrrno ���������� ��� �������� � ��������.
	 * @param paidBy ��� ����� �������� (Mastercard, Visa, ...)
	 */
	public void changeOrderStatus(String orderUUID, OrderState newState, Long nodeId, String utrrno, String paidBy, Calendar delayDate) throws GateException, GateLogicException;

	/**
	 * ��������� ������� �������� ������� ��� ������ �������..
	 *
	 * @param recallUUID ������� ������������� ������ �������/�������� �������.
	 * @param newState ����� ���������.
	 * @param utrrno ���������� ��� �������� � ��������.
	 * @param recallType ��� �������.
	 */
	public void changeRecallStatus(String recallUUID, RecallState newState, String utrrno, RecallType recallType) throws GateException, GateLogicException;

	/**
	 * ���������� ���������� � ���������� �����������.
	 *
	 * @param orderUUID ������� ������������� ������.
	 * @return ���������� �� �����������.
	 */
	public String getTicketInfo(String orderUUID) throws GateException, GateLogicException;

	/**
	 * �������� ����� ��� �������������.
	 * @param orderUUID - ��� ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void markViewed(String orderUUID) throws GateException, GateLogicException;

	/**
	 * ����� ������ ��� �� ����.
	 *
	 * @param facilitatorCode ��� ���������� �����.
	 * @param firstResult ������ �.
	 * @param maxResult ����� �������.
	 * @return ������ ���.
	 */
	public List<FacilitatorProvider> findEndPointProviderByCode(String facilitatorCode, int firstResult, int maxResult) throws GateException, GateLogicException;

	/**
	 * ����� ������ ��� �� ����� �/��� ���.
	 *
	 * @param name ��� ���������� �����.
	 * @param inn ���.
	 * @param firstResult ������ �.
	 * @param maxResult ����� �������.
	 * @return ������ ���.
	 */
	public List<FacilitatorProvider> findEndPointProviderByName(String name, String inn, int firstResult, int maxResult) throws GateException, GateLogicException;

	/**
	 * ��������� ���.
	 *
	 * @param providerId ������������� ���������� �����.
	 * @return ���.
	 */
	public FacilitatorProvider getEndPointProvider(long providerId) throws GateLogicException, GateException;

	/**
	 * ���������� ���.
	 *
	 * @param providerId ������������� ����������.
	 * @param mcheckoutEnabled �������� �� mobileCheckout.
	 * @param eInvoicingEnabled ��������� �� ������� ����� EInvoicing.
	 * @param mbCheckEnabled ��������� �� �������� � ��������� �����.
	 */
	public void updateEndPointProvider(long providerId, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException;

	/**
	 * ���������� ������� ��� ��� ���� ��� ������������.
	 *
	 * @param facilitatorCode ��� ������������.
	 * @param mcheckoutEnabled �������� �� mobileCheckout.
	 * @param eInvoicingEnabled ��������� �� ������� ����� EInvoicing.
	 * @param mbCheckEnabled ��������� �� �������� ������ �������� � ��������� �����.
	 */
	public void updateEndPointProviders(String facilitatorCode, Boolean mcheckoutEnabled, Boolean eInvoicingEnabled, Boolean mbCheckEnabled) throws GateLogicException, GateException;
}
