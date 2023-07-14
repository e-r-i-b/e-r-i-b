package com.rssl.phizic.rsa.senders.types;

/**
 * �������, ������������ ������� ���������� ��������� ������ �� ����-����������
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum EventsType
{
	VIEW_STATEMENT("VIEW_STATEMENT"),                         //������ ����������� ������ � ��������� ������� ��������
	UPDATE_USER("UPDATE_USER"),                               //������ ������� ������
	ENROLL("ENROLL"),                                         //������ �������� �����������
	PAYMENT("PAYMENT"),                                       //������
	ADD_PAYEE("ADD_PAYEE"),                                   //������ ��������� ����� ������
	EDIT_PAYEE("EDIT_PAYEE"),                                 //�������������� ������� ��������
	CHANGE_LOGIN_ID("CHANGE_LOGIN_ID"),                       //����� ������
	CHANGE_PASSWORD("CHANGE_PASSWORD"),                       //����� ������
	CHANGE_ALERT_SETTINGS("CHANGE_ALERT_SETTINGS"),           //��������� �������� ��������������
	SESSION_SIGNIN("SESSION_SIGNIN"),                         //���� ������������ � �������
	FAILED_LOGIN_ATTEMPT("FAILED_LOGIN_ATTEMPT"),             //���������� ���� � �������
	FAILED_MOBILE_LOGIN_ATTEMPT("FAILED_LOGIN_ATTEMPT"),      //���������� ���� � ������� ����� ��������� ����������
	REQUEST_CREDIT("REQUEST_CREDIT"),                         //������ �� ������
	CLIENT_DEFINED("CLIENT_DEFINED"),                         //������ �� ����
	REQUEST_NEW_CARD("REQUEST_NEW_CARD");                     //������ �� ���������� �����

	private String description;

	EventsType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
