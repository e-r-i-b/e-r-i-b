package com.rssl.phizic.gate.basket;

import com.rssl.phizic.common.types.basket.InvoiceState;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import com.rssl.phizic.gate.payments.longoffer.DelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.RecoveryCardPaymentSystemPaymentLongOffer;

import java.util.Calendar;
import java.util.List;

/**
 * @author vagin
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 * ������ ������ � ���������(����� � ������).
 */
public interface InvoiceService extends Service
{
	/**
	 * ���������� ������ �������.
	 * @param invoice - ����� ������.
	 * @param operUID - ������������� ������ �� ��������.
	 * @throws GateException
	 */
	void addInvoice(GateInvoice invoice, String operUID) throws GateException;

	/**
	 * �������� ��� ������������ ������� �� �������� �����.
	 * @param invoice - ����� ������.
	 * @param operUID - ���������� ������������� ������ �� ��������. OperUID � ���� ���������.
	 * @throws GateException
	 */
	void updateInvoice(GateInvoice invoice, String operUID) throws GateException;

	/**
	 * ������� ��� ������������� � ������� New � ������ �������� �� ��������� ��������.
	 * @param operUID - ������������� ������ �� ��������.
	 * @param autoSubscriptionId - ������� ������������� ��������.
	 * @throws GateException
	 */
	void deleteInvoice(String operUID, String autoSubscriptionId) throws GateException;

	/**
	 * ��������� ������� �������-������� ������ ������������.
	 * @param externalPaymentId - ������������� ������� � �� "AutoPay"(PaymentId)
	 * @param state - ��� ��������� �������
	 * @param nonExecReasonDesc - ������� ��� ������ "�������"
	 * @param nonExecReasonCode - ��� ������� ������������ �������. ������������ ������ ��� �������� �� �������� �������. (PaymentStatus = Canceled)
	 * @param execPaymentDate - ���� ��������� ������������.
	 * @param operUID - ������������� ������ �� ��������.
	 */
	void updatePaymentState(String externalPaymentId, InvoiceState state, String nonExecReasonCode, String nonExecReasonDesc, Calendar execPaymentDate, String operUID) throws GateException, GateLogicException;

	/**
	 * ���������� ��������� �������-������� ������������ �� ��������� � �������� ������� ��������(�� "AutoPay").
	 * @param externalId - externalId �������(RqUID ������� ������)
	 * @param statusCode - ��� ������
	 * @param nonExecReasonDesc - �������� ������� ������.
	 */
	void updatePaymentStatus(String externalId, Long statusCode, String nonExecReasonDesc) throws GateException, GateLogicException;

	/**
	 * �������������� ��������.
	 * @param autoSubscriptionId - ������� �������������
	 * @param UID - ������������� ������.
	 */
	void activateInvoiceSubscription(String autoSubscriptionId, String UID) throws GateException, GateLogicException;

	/**
	 * �������� �������� �� ������ �� ������������ �����������
	 * @param claim ������ �� ������������ �����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void createInvoiceSubscription(DelayCardPaymentSystemPaymentLongOffer claim) throws GateException, GateLogicException;

	/**
	 * ������������� ������ ����������� ���������� � �����������(�� ������� �������� ����������)
	 * @param claim ������ �� ������������� �����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void recoverAutoSubscription(CloseInvoiceSubscription claim) throws GateException, GateLogicException;
}
