package com.rssl.phizic.rsa.config;

/**
 * ��������� ������� ������� RSA
 *
 * @author khudyakov
 * @ created 04.05.15
 * @ $Author$
 * @ $Revision$
 */
public enum State
{
	ACTIVE_INTERACTION,                 //�������, ������ ��������������� (�������� �������, ��������� ������, ��������� ��������� ���� � ������������ � �������)
	ACTIVE_ONLY_SEND,                   //�������, ������ �������� ��������� �� ����
	NOT_ACTIVE,                         //�� �������
}
