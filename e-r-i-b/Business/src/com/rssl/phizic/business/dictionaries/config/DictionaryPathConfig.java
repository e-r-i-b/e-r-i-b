package com.rssl.phizic.business.dictionaries.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * Created by IntelliJ IDEA. User: Evgrafov Date: 19.09.2005 Time: 12:41:14
 */
public abstract class DictionaryPathConfig extends Config
{
	protected DictionaryPathConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @param dictionaryPath имя проперти
	 * @return путь к справочникам
	 */
	public abstract String getPath(String dictionaryPath);
}
