package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleEntry;

/**
 * @author lepihina
 * @ created 01.04.14
 * $Author$
 * $Revision$
 * Writer для записи логов о добавлении/применении правила перекатегоризации
 */
public interface RecategorizationRuleLogWriter
{
	/**
	 * Добавление записи о добавлении/применении правила перекатегоризации
	 * @param entry - объект, представляет собой запись о перекатегоризации
	 * @throws Exception
	 */
	void write(ALFRecategorizationRuleEntry entry) throws Exception;
}
