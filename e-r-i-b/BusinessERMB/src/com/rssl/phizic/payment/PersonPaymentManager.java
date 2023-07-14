package com.rssl.phizic.payment;

/**
 * @author Erkin
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ �������� ��������
 */
public interface PersonPaymentManager
{
	/**
	 * ������� �������� ������ "������� ����� ������ �������"
	 * @return ����� ������ ������� (never null)
	 */
	InternalTransferTask createInternalTransferTask();

	/**
	 * ������� �������� ������ "������� �� ������ �����"
	 * @return ����� ������ ������� (never null)
	 */
	CardTransferTask createCardTransferTask();

	/**
	 * ������� �������� ������ "������� �� ������ ��������"
	 * @return ����� ������ ������� (never null)
	 */
	PhoneTransferTask createPhoneTransferTask();

	/**
	 * ������� �������� ������ "���������� �����"
	 * @return ����� ������ ���������� (never null)
	 */
	LossPassbookTask createLossPassbookTask();

	/**
	 * ������� ��������� ������ "���������� �����"
	 * @return ����� ������ ���������� (never null)
	 */
	BlockingCardTask createBlockingCardTask();

	/**
	 * ������� ��������� ������ "������ ����� (����������)"
	 * @return ����� ������ ������ (never null)
	 */
	ServicePaymentTask createServicePaymentTask();

	/**
	 * ������� ��������� ������ "������ ��������"
	 * @return ����� ������ ������ ��������
	 */
	RechargePhoneTask createRechargePhoneTask();

	/**
	 * ������� ��������� ������ "������ ����� (������� �� �����������)"
	 * @return ����� ������ ������ (never null)
	 */
	TemplatePaymentTask createTemplatePaymentTask();

	/**
	 * ������� ��������� ������ "������ �������"
	 * @return ����� ������ "������ �������" (never null)
	 */
	LoanPaymentTask createLoanPaymentTask();

	/**
	 * ������� ��������� ������ "����������� ��������� ������� �� ���������"
	 * @return ����� ������ "����������� ��������� ������� �� ���������" (never null)
	 */
	LoyaltyRegistrationPaymentTask createLoyalRegistrationPaymentTask();

	/**
	 * ������� ��������� ������ "�������� �����������"
	 * @return ����� ������ "�������� �����������"  (never null)
	 */
	CreateAutoPaymentTask createAutoPaymentTask();

	/**
	 * ������� ��������� ������ "���������� �����������"
	 * @return ����� ������ "���������� �����������"  (never null)
	 */
	RefuseAutoPaymentTask createRefuseAutoPaymentTask();

	/**
	 * ������� ��������� ������ "����������"
	 * @return ����� ������ "����������" (never null)
	 */
	CardIssueTask createCardIssueTask();
}
