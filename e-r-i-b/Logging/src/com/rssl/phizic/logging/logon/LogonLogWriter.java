package com.rssl.phizic.logging.logon;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface LogonLogWriter
{
	/**
	 * ������ ������� "����� �������" � ������ ����������� ������
	 * @param loginId �����
	 * @param firstName ���
	 * @param patrName  ��������
	 * @param surName �������
	 * @param birthday ��
	 * @param cardNumber ����� ����� �����
	 * @param docSeries ����� ���
	 * @param docNumber ����� ���
	 * @param browserInfo ���������� �� ����������
	 */
	void writeFindProfile(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo);

	/**
	 * ������ ������� "����" � ������ ����������� ������
	 * @param loginId �����
	 * @param firstName ���
	 * @param patrName  ��������
	 * @param surName �������
	 * @param birthday ��
	 * @param cardNumber ����� ����� �����
	 * @param docSeries ����� ���
	 * @param docNumber ����� ���
	 * @param browserInfo ���������� �� ����������
	 */
	void writeLogon(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo);
}
