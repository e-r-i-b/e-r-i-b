package com.rssl.phizic.business.operations.background;

/**
 * @author krenev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */
public enum TaskState
{
	NEW,  //����� ������
	PROCESSING, //������ � �������� ����������
	PROCESSED,  //������� ����������� ������
	ERROR, //��� ���������� ������ ��������� �������������� ��������� ������
    PERIODICAL, //��� ������������� � ������������� �������
    DISABLED //������� ���� ��� ������� ���������������(� ������������� �������)
}
