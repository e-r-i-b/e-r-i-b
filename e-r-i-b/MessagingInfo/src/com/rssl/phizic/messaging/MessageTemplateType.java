package com.rssl.phizic.messaging;

/**
 * ��� ������� ���������
 * @author Rtischeva
 * @created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */
public enum MessageTemplateType
{
	INFORM,//������������� ���-���������

	CONFIRM,//���-��������� � ��������������

	REFUSE, //���������, ������������� �� ������ � ���������� ��������
	BLOCK,  //���������, ������������� � ����������
	REVIEW, //���������, ������������� � �����������������
	DENY,   //���������, ������������� � �������
}
