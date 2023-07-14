package com.rssl.phizic.operations.config.writers;

import java.util.Map;

/**
 * Писатель настроек в БД
 *
 * @author khudyakov
 * @ created 15.07.15
 * @ $Author$
 * @ $Revision$
 */
public interface PropertiesWriter
{
	/**
	 * Сохранить настройки
	 * @param properties настройки
	 */
	void write(Map<String, String> properties);
}
