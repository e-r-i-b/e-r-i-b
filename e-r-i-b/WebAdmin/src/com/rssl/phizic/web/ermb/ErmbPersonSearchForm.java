package com.rssl.phizic.web.ermb;

import com.rssl.phizic.web.persons.SearchPersonForm;

/**
 * User: Moshenko
 * Date: 27.05.2013
 * Time: 12:07:49
 * ����� ������� � ��������� ����������� ��������� ������� ����
 */
public class ErmbPersonSearchForm extends SearchPersonForm
{
	private boolean searchByPhone;

	/**
	 * @return ����� ������ ������� (true - ����� �� ������ ��������, false - ����� �� ��� + ��� + �� + ��)
	 */
	public boolean isSearchByPhone()
	{
		return searchByPhone;
	}

	/**
	 * ������ ����� ������ ������� (true - ����� �� ������ ��������, false - ����� �� ��� + ��� + �� + ��)
	 * @param searchByPhone ����� ������
	 */
	@SuppressWarnings("UnusedDeclaration") // ���������� �������� ��� ��������� �������
	public void setSearchByPhone(boolean searchByPhone)
	{
		this.searchByPhone = searchByPhone;
	}
}
