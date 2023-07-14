package com.rssl.phizic.common.types.basket;

/**
 * @author osminin
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������� �� ������
 */
public enum InvoiceSubscriptionState
{
	ACTIVE,         //��������
	STOPPED,        //����������������
	INACTIVE,       //����������
	ERROR,          //������
	WAIT,           //������� ���������
	DELETED,        //���������
	AUTO,           // ������������� ��������������� ��������
	DELAY_DELETE,    // ��������� ������� ��������
	DELAY_CREATE,    // ��������� ������� ��������
	DRAFT,          // ������ ��� ��������
	FAKE_SUBSCRIPTION_PAYMENT  //������, ����������� ��� ������ �������� - ������ �� ��������, ���������������� � ������-�������� (���������� ��� ����������� ����������� �� �������� "������� ��������")
}
