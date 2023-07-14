package com.rssl.phizic.business.limits.link;

/**
 * @author khudyakov
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */
public enum LimitPaymentsType
{
	PHYSICAL_EXTERNAL_ACCOUNT_PAYMENT_LINK,          //������ �� ���� �������� ���� � ������ ����
	PHYSICAL_EXTERNAL_CARD_PAYMENT_LINK,             //������ �� ����� �������� ���� � ������ ����
	PHYSICAL_INTERNAL_PAYMENT_LINK,                  //������ �� ����/����� �������� ���� ������� ���������
	INTERNAL_SOCIAL_TRANSFER_LINK,                   //������� ����� ������ ������� �� ���. �����
	CONVERSION_OPERATION_PAYMENT_LINK,               //������������� ��������
	JURIDICAL_PAYMENT_LINK                           //������ ������������ ���� ��� ����� ���������
}
