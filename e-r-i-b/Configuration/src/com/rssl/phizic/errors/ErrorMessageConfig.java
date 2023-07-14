package com.rssl.phizic.errors;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author bogdanov
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */

public class ErrorMessageConfig extends Config
{
	private static final String ERROR_MESSAGES_MATCHER_PARAMETER_NAME = "com.rssl.iccs.ErrorMessagesMatcher";
	private Class<ErrorMessagesMatcher> matcherClass;

	public ErrorMessageConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		try
		{
			matcherClass = ClassHelper.loadClass(getProperty(ERROR_MESSAGES_MATCHER_PARAMETER_NAME));
		}
		catch (ClassNotFoundException e)
		{
			throw new ConfigurationException("Ошибка во время перечитывания конфига", e);
		}
	}

	public Class<ErrorMessagesMatcher> getMatcherClass()
	{
		return matcherClass;
	}
}
