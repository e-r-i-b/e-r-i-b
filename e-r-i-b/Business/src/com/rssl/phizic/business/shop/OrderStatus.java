package com.rssl.phizic.business.shop;

/**
 * User: Moshenko
 * Date: 28.11.2011
 * Time: 16:18:58
 * ������� ����������  �������� �� �������
 */
public enum OrderStatus
{
	ERROR,     //�� ����������� ��������� ������
	OK,        //����������� ������ ������� (������ �� ��������)
	NOT_SEND , //����������� �� ����������
	SUSPENDED  //����������� �� ������ �� ���� (������ �� ��������)
}
