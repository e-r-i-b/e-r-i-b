package com.rssl.phizic.utils.store;

/**
 * ������ ��� ������ �� store
 *
 * @author khudyakov
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 */
public class StoreHelper
{
	/**
	 * �������� �������� � ���
	 * @param store ���������
	 * @param key ����, ��� ������� ����� �������� ����������� ��������
	 * @param value ����������� ��������
	 * @param <T> �������
	 * @return ����������� ��������
	 */
	public static <T> T put2store(Store store, String key, T value)
	{
		if (value == null)
		{
			return null;
		}

		store.save(key, value);
		return value;
	}

	/**
	 * �������� ���������� �������� �� ����, ��� ���������� � ���� �� ���������������� �� ���������
	 * @param store ���������
	 * @param key ����
	 * @param action ����� ��������� �� ���������
	 * @param <T> �������
	 * @return ��������
	 * @throws Exception
	 */
	public static <T> T getStoredEntity(Store store, String key, StoreAction<T> action) throws Exception
	{
		//noinspection unchecked
		T object = (T) store.restore(key);
		if (object == null)
		{
			return put2store(store, key, action.getEntity());
		}

		return object;
	}

}
