package com.rssl.phizic.dictionaries.synchronization;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * интерфейс записи справочника, работающего в многоблочном режиме
 */

public interface MultiBlockDictionaryRecord
{
	/**
	 * @return ключ синхронизации
	 */
	public String getMultiBlockRecordId();
}
