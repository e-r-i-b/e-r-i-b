package com.rssl.common.forms.types;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public abstract class TypesConfig extends Config
{
	protected TypesConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Возвращает тип по его имени и бросает исключение если тип не найден
	 */
	public abstract Type getType(String name);

	public abstract Type defaultType();
}
