package com.rssl.phizic.messaging;

/**
 * ��� ��������
 *
 * @author khudyakov
 * @ created 14.11.2012
 * @ $Author$
 * @ $Revision$
 */
public enum OperationType
{
	LOGIN,                          //������������� ����� �������
	UNUSUAL_IP,                     //���� � �������������� IP
	PAYMENT_OPERATION,              //������������� ��������� ��������
	CREATE_TEMPLATE_OPERATION,      //������������� ������� ��������
	EDIT_USER_SETTINGS,             //������������� ��������� ���������� � ������ ��������
	REGISTRATION_OPERATION,         //������������� �������� ����������� ������������
	CALLBACK_BCI_CONFIRM_OPERATION, //������������� ���������� ������� � ��� ��� ������ ��������� ������ ��� ����������� ��������� ������
	CALLBACK_CONFIRM_OPERATION,     //������������� ������� ������ ��������� ������ ��� ����������� ��������� ������
	VIEW_OFFER_TEXT_OPERATION       //������������� ������� ������ ��������� ������ ��� ����������� ��������� ������
}
