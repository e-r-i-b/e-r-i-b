package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * Настройки синхронизации справочников
 */
public abstract class DictionaryConfig extends Config
{
	protected DictionaryConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
     * получить дескриптор по его имени
     * @param name имя дескриптора
     * @return дескриптор
     */
    public abstract DictionaryDescriptor getDescriptor(String name);

    /**
     * @return Список всех дескрипторов
     */
    public abstract List<DictionaryDescriptor> getDescriptors();

	/**
	 * получить дескрипторы группы справочников по её имени
	 * @param name имя дескриптора
	 * @return дескриптор
	 */
	public abstract List<String> getGroupDescriptorNames(String name);
}
