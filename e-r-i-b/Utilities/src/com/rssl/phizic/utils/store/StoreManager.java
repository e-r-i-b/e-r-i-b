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
	 * @return текущая Store, ассоциированная с нитью
	 */
	public static Store getCurrentStore()
    {
        return currentStore.get();
    }

	/**
	 * Ассоциированная Store текуще нитью
	 * @param store хранилище данных
	 */
	public static void setCurrentStore(Store store)
    {
        currentStore.set(store);
    }
}
