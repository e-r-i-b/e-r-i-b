package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.math.BigDecimal;

/**
 * ��������� �������������� EInvoicing � ERIB � CSAAdmin.
 *
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface InvoiceGateBackService extends Service
{
	/**
	 * @param receiverCode ��� ���������� ����� (codeRecipientSBOL).
	 * @return ��������� ���������� �����.
	 */
	public ShopProvider getActiveProvider(String receiverCode) throws GateException;

	/**
	 * ���������� ������ �� ���������� ������ �������.
	 *
	 * @param shopOrder �����, �� �������� ������������ ������ �������.
	 * @param amount ����� ������.
	 * @param currencyCode ������ ������.
	 * @param refundUuid ������������� ������.
	 */
	public void sendRefundOrder(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String refundUuid) throws GateException, GateLogicException;

	/**
	 * ���������� ������ �� ���������� �������� ������� �� ������.
	 *
	 * @param shopOrder �����, �� �������� ������������ ������� �������.
	 * @param amount ����� ��������.
	 * @param currencyCode ������ ��������.
	 * @param returnGoodsUuid ������������� ��������.
	 * @param returnedGoods ������ ������������ �������.
	 */
	public void sendReturnGoods(ShopOrder shopOrder, BigDecimal amount, String currencyCode, String returnGoodsUuid, String returnedGoods) throws GateException, GateLogicException;

	/**
	 * ������������ ��������� ������ ������.
	 * ����� ������������ ��� ������ ������ ��� ���������� ����� mobile checkout �������.
	 * @param order - �����, �� �������� ��������� ��������
	 */
	public void createOrderPayment(ShopOrder order) throws GateException, GateLogicException;

	/**
	 * �������� ��������� ������ ������ � �������.
	 * ����� ������������ ��� ������ ������ ��� ���������� ����� mobile checkout �������.
	 * @param order - �����, �� �������� ��� ������ �������� ������
	 */
	public void sendOrderPayment(ShopOrder order) throws GateException, GateLogicException;

	/**
	 * �������� ������ ������� � ����
	 * @param order - �����
	 * @param ticketInfo - xml � ��������� �������
	 */
	public void sendTickets(ShopOrder order, String ticketInfo) throws GateException, GateLogicException;

	/**
	 * ��������� ������� ������
	 * ����� ������������ ������ ��������� ������� ������ �������, �������� � ������� "�������� �������"
	 * @param order  - �����
	 */
	public OrderStateInfo getOrderState(ShopOrder order) throws GateException, GateLogicException;

	/**
	 * ��������� ������� �����/���������
	 * ����� ������������ ������ ��������� ������� �����/���������, �������� � ������� "������"
	 * @param recall  - ������/�������
	 */
	public RecallStateInfo getRecallState(ShopRecall recall) throws GateException, GateLogicException;
}
