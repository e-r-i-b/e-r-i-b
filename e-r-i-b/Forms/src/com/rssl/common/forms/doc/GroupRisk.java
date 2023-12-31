package com.rssl.common.forms.doc;

/**
 * @author niculichev
 * @ created 15.08.2011
 * @ $Author$
 * @ $Revision$
 */
public enum GroupRisk
{
	//����� ����� �������
	/**
	 * ������ � ������� ������
	 */
	HIGH,

	/**
	 * ������ � ������� ������
	 */
	MEDIUM,

	/**
	 * ������ � ������ ������
	 */
	LOW,

	/**
	 * ������ � ����� ������ ������
	 */
	LOWEST,

	//TODO ��� ��������� ������ �������, ������ ��� ����������� ������� "BUG032737 ������ ��������� ������ �������." 8� �����
	/**
	 * ������ ����� ��� ��������� ������ �������: ������������� �� ����� ������
	 */
	CHECK,

	/**
	 * ������ ����� ��� �������� "���� - ���������� �����". ����� �������
	 */
	SOCCARD,

	/**
	 * ������ ����� ��� �������� ������������ �������
	 * ����� ����� ��� ����� ����� �����.
	 */
	GENERAL,

	/**
	 * ������ ����� ��� ������ �� IMSI
	 */
	IMSI
}
