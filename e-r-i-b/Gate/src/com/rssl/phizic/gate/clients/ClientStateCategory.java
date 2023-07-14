package com.rssl.phizic.gate.clients;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public enum ClientStateCategory
{
	//������� ����������
	agreement_dissolve,
	//������ ��� ������������
	error_dissolve,
	//��������� ������ ��� ����������
	system_error_dissolve,
	//��������� ��������, ����� ������ ��� �� ����� ����� � �������
	creation,
	//�������� ��������� �������������. ����� ������������ ����� ��� ����������� ������������ ��������
	active,
	//��������� ������������, ������� ���� ��� ������� �������, ���� ����������
	cancellation
}
