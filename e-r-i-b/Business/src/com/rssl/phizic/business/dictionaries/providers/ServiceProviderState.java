package com.rssl.phizic.business.dictionaries.providers;

/**
 * @author khudyakov
 * @ created 24.12.2009
 * @ $Author$
 * @ $Revision$
 */

public enum ServiceProviderState
{
	NOT_ACTIVE(0),      //����������
	ACTIVE(1),          //��������
	DELETED(2),         //������
	ARCHIVE(3),         //������ ��������� ��� ���������� �� ��� ������ � ����������� �����, �������,
						//����� �� �� ��� ������ � �������� �� ���������, ���������� ��� ��������� ������
	MIGRATION(4);       //����������� iqwave ������������ ��� ����� ��������, c����� ���� ��������������

	private int value;

	ServiceProviderState(int value)
	{
		this.value = value;
	}

	/**
	 * �������� ��� ������� ����������
	 * @param value ��������
	 * @return ������
	 */
	public static ServiceProviderState fromValue(int value)
	{
		if (ACTIVE.value == value)
			return ACTIVE;
		if (NOT_ACTIVE.value == value)
			return NOT_ACTIVE;
		if (DELETED.value == value)
			return DELETED;
		if (ARCHIVE.value == value)
			return ARCHIVE;
		if (MIGRATION.value == value)
			return MIGRATION;

		throw new IllegalArgumentException("����������� ��� ���� [" + value + "]");
	}

	/**
	 * @return ��������
	 */
	public int getValue()
	{
		return value;
	}
}
