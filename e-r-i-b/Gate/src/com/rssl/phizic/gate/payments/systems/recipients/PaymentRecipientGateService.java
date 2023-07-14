package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * ������ ��� ������ � ������������ �� ������� �������.
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface PaymentRecipientGateService extends Service
{

	/**
	 * ����� ����������� � �������� �� �����, ���� � ���
	 * @param account ���� ����������
	 * @param bic ��� ����� ����������
	 * @param inn ��� ����������
	 * @param billing �������, � ������� ������ ����� ����������
	 * @return ������ ����������� ��������������� ����������. ���� ��������� �� ������� - ������ ������
	 */
	List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException;

	/**
	 * �������� ������ ������������ ����������(��������) �������
	 *
	 * @param billingClientId ������������� ������� � �������.
	 * @param billing ������
	 * @return ������ ����������� ��� ������ �������, ���� �� ���
	 */
	List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException;

	/**
	 * �������� ������ ��������� ������������ ����� ������������� ����������(�������)
	 * @param recipient ����������
	 * @param billingClientId ������������� ������� � ��������
	 * @return ������ �������� ����� ��� ������ �������, ���� �� ���
	 */
	List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException;

	/**
	 * �������� �������������� ���������� �� ����������.
	 *
	 * @param recipient ����������
	 * @param fields ������ �������������� �����
	 * @param debtCode - ��� �������������, ����� ���� null
	 * @return �������������� ��������� �� ����������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException;

	/**
	 * ��������� ���������� ������� �� ��� ��������������.
	 * GateException - ���� ���������� �� ������.
	 *
	 * @param recipientId ������������� ����������.
	 * @return ���������� �������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	Recipient getRecipient(String recipientId) throws GateException, GateLogicException;

	/**
	 * �������� ������ ����������� ���������� � ��������
	 * @param billing �������
	 * @return ������ ����������� ��� ������ �������, ���� �� ���
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException;

	/**
	 * �������� ������ ���. ����� ��� ����������
	 * @param recipient ����������
	 * @param keyFields ����
	 * @return ������ ��� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException;

	/**
	 * �������� ������ �������� ����� ��� ����������
	 * @param recipient ����������
	 * @throws GateException
	 * @throws GateLogicException
	 * @return ������ �������� ����� 
	 */
	List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException;

	/**
	 * �������� ��������� ����� ������������ ��������
	 * @param cardId - ����� ����� ������������ ��������
	 * @param billing - �������
	 * @return �������� ����� ������������ ��������
	 * @exception GateException
	 * @exception GateLogicException
	 */
	Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException;
}