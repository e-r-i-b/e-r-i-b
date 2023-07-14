package com.rssl.phizic.utils.store;

/**
 * User: Roshka
 * Date: 27.09.2005
 * Time: 14:54:21
 */
public interface Store
{
    String getId();
	void save(String key, Object obj);
    Object restore(String key);
	void clear();

	void remove(String key);

	/**
	 * �������� ���������������� ������ ���������.
	 * ����� ������, ���� ��������� ��� ������ �������.
	 * �� ������� ������� �������� ������������� ��� ����������� ��������� � ����������
	 * (��������, ��������� �������, ��������� � ��� ��������).
	 * @return ���������������� ������ ���������. �� ����� ���� null.
	 */
	Object getSyncObject();
}
