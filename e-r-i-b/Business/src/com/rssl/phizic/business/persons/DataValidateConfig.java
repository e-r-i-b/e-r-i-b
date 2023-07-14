package com.rssl.phizic.business.persons;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author osminin
 * @ created 03.04.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class DataValidateConfig extends Config
{
	public static final String PHONE_FORMAT_REGEXP = "com.rssl.iccs.phone.format.regexp";
	public static final String PHONE_FORMAT_DESCRIPTION = "com.rssl.iccs.phone.format.description";

	protected DataValidateConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Получение регулярного выражения для валидации телефонных полей
	 * @return регулярное выражение
	 */

	public abstract String getPhoneFormatRegexp();

	/**
	 * Получения сообщения
	 * @return сообщение
	 */

	public abstract String getPhoneFormatDescription();
}
