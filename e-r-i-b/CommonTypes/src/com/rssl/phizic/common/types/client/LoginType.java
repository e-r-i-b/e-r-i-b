package com.rssl.phizic.common.types.client;

/**
 * ��� �������
 *
 * @author khudyakov
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */
public enum LoginType
{
	CSA,        //������ � ������ ������ ����, ��������� ��� ��������������� ����������� � ���.
	TERMINAL,   //������ � ������ ������ ����, ��������� ��� �������� ��� ����� �� ������ ������ ��(iPas)
	MAPI,       //������ � mAPI, ��������� ��� ����������� ���������� ����������
    SOCIAL,
	ATM,         //������ � ���, ��������� ��� �������������� ������� ����� ���
	DISPOSABLE,   //��������� ������ � ������ ������
	GUEST
}
