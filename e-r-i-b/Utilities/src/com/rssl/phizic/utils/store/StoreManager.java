package com.rssl.phizic.utils.store;

/**
 * @author hudyakov
 * @ created 14.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class StoreManager
{
	private static ThreadLocal<Store>  currentStore  = new ThreadLocal<Store>();

	/**
	 * @return ������� Store, ��������������� � �����
	 */
	public static Store getCurrentStore()
    {
        return currentStore.get();
    }

	/**
	 * ��������������� Store ������ �����
	 * @param store ��������� ������
	 */
	public static void setCurrentStore(Store store)
    {
        currentStore.set(store);
    }
}
