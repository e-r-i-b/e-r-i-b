package com.rssl.phizic.gate.dictionaries;

/**
 * Запись справочника
 */
public interface DictionaryRecord
{
	/**
	 * @return ключ синхронизации
	 */
    Comparable getSynchKey();

    /**
     * Обновить содержимое записи из другой
     * @param that запись из которой надо обновить
     */
    void updateFrom(DictionaryRecord that);
}
