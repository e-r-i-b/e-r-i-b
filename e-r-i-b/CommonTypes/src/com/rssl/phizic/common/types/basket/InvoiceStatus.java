package com.rssl.phizic.common.types.basket;

/**
 * @author vagin
 * @ created 26.05.14
 * @ $Author$
 * @ $Revision$
 * ��������� �������.
 */
public enum InvoiceStatus
{
	NEW,      //����� ���� � ������
	PAID,     //����������, ���� ������ �� ������� ������� � �������� �������.
	INACTIVE, //���������, ���������� ���� � ������, ����������.
	DELAYED,  //���������
	ERROR     //������
}
