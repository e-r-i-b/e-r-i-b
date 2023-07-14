package com.rssl.phizic.business.ermb.profile.comparators.sms;

import java.util.Set;

/**
 * ��������� ��������� ������ ��������� ���� � ��������� �������� ��� �� ���������
 * @author Puzikov
 * @ created 07.08.15
 * @ $Author$
 * @ $Revision$
 */

public interface ErmbSmsChangesComparator<T>
{
	String FIO              = "�������, ���, ��������";
	String DUL              = "��������";
	String DR               = "���� ��������";
	String TARIFF           = "�������� ����";
	String ADV              = "������� ������� ��������� ��������";
	String PAYMENT_CARD     = "����� �������� ����������� �����";
	String PHONES           = "��������";
	String NOTIFICATION     = "��������� ���������";

	/**
	 * ������� ��������� ���������� ����� �� � ����� ���������
	 * @param oldData �� ���������
	 * @param newData ����� ���������
	 * @return �������� ����� (��� �������� � ���)
	 */
	Set<String> compare(T oldData, T newData);
}
