package com.rssl.phizic.business.ermb;

/**
 * ��� ������� � ����
 * @author Rtischeva
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */
public enum ErmbPaymentType
{
	SERVICE_PAYMENT,//������ �����

	TEMPLATE_PAYMENT, //������ �� �������

	RECHARGE_PHONE,//���������� ��������

	LOAN_PAYMENT, //������ �������

	BLOCKING_CARD, //���������� �����

	CARD_TRANSFER, //������� �� ������ �����

	PHONE_TRANSFER, //������� �� ������ ��������

	LOSS_PASSBOOK, //���������� �����

	INTERNAL_TRANSFER, //������� ����� ������ �������

	LOYALTY_PROGRAM_REGISTRATION_CLAIM, //����������� � ��������� ����������

	CREATE_AUTOPAYMENT, //�������� �����������

	REFUSE_AUTOPAYMENT, //���������� �����������

	CARD_ISSUE // ���������� �����
}
