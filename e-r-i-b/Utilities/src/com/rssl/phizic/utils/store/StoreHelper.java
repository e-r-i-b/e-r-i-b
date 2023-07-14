package com.rssl.phizic.utils.store;

/**
 * Хелпер для работы со store
 *
 * @author khudyakov
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 */
public class StoreHelper
{
	/**
	 * Добавить значение в кеш
	 * @param store хранилище
	 * @param key ключ, под которым будет положено добавляемое значение
	 * @param value добавляемое значение
	 * @param <T> сущноть
	 * @return добавляемое значение
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
	 * Получить кешируемую сущность из кеша, при отсутствии в кеше из соответствующего ей источника
	 * @param store хранилище
	 * @param key ключ
	 * @param action метод получения из источника
	 * @param <T> сущноть
	 * @return сущность
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
