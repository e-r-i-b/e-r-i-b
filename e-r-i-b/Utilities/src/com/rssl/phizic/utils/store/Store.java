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
	 * Получить синхронизирующий объект хранилища.
	 * Грубо говоря, само хранилище без всяких оберток.
	 * По данному объекту возможна синхронизация при компоситных действиях с хранилищем
	 * (например, получение объекта, сравнение и его удаление).
	 * @return синхронизирующий объект хранилища. не может быть null.
	 */
	Object getSyncObject();
}
