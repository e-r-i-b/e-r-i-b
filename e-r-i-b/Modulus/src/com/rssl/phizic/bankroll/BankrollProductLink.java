package com.rssl.phizic.bankroll;

import com.rssl.phizic.common.types.Currency;

/**
 * @author Erkin
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� �� ���������� ���������� ������� - ����, ����� � �.�
 */
public interface BankrollProductLink
{
	/**
	 * @return ���������� � ������� ���� (�����������) ������ ���
	 */
	String getCode();

	/**
	 * @return ������
	 */
	Currency getCurrency();

	/**
	 * @return �������������� ���-����� ��������
	 */
	String getAutoSmsAlias();

	/**
	 * @param autoSmsAlias - �������������� ���-����� ��������
	 */
	void setAutoSmsAlias(String autoSmsAlias);

	/**
	 * @return true, ���� �� �������� ������ ������������ ���-�����������
	 */
	boolean getErmbNotification();

	/**
	 * @param ermbNotification - true, ���� �� �������� ������ ������������ ���-�����������
	 */
	void setErmbNotification(boolean ermbNotification);

	/**
	 * @return ���������������� ���-����� ��������
	 */
	String getErmbSmsAlias();

	/**
	 * @param ermbSmsAlias - ���������������� ���-����� ��������
	 */
	void setErmbSmsAlias(String ermbSmsAlias);

	/**
	 *
	 * @return true, ���� ������� ������ ���� �������� � ���-��������
	 */
	boolean getShowInSms();

	/**
	 * ���������� ����������� �������� � ���-��������
	 * @param showInSms - true, ���� ������� ������ ���� �������� � ���-��������
	 */
	void setShowInSms(boolean showInSms);

	/**
	 * ������� ���-����� ��� ��������� ��������� �������
	 * @return ���-����� ��� ��������� ��������� �������
	 */
	String getSmsResponseAlias();
}
