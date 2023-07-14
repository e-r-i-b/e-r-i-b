package com.rssl.phizic.errors;

import com.rssl.phizic.ConfigurationCheckedException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.ClassHelper;

import java.util.List;

/**
 * @author krenev
 * @ created 17.06.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class ErrorMessagesMatcher
{
	private static volatile ErrorMessagesMatcher instance;

	public static final ErrorMessagesMatcher getInstance() throws ConfigurationCheckedException
	{
		if (instance != null)
			return instance;

		synchronized (ErrorMessagesMatcher.class)
		{
			if (instance != null)
				return instance;

			try
			{
				instance = ConfigFactory.getConfig(ErrorMessageConfig.class).getMatcherClass().newInstance();
			}
			catch (Exception e)
			{
				throw new ConfigurationCheckedException(e);
			}
		}
		return instance;
	}

	/**
	 * получить список сообщений, которые подход€т дл€ данной ошибки
	 * @param message сообщение
	 * @param system система породивша€ ошибку
	 * @return список сообщений, либо пустой список
	 * @throws ConfigurationCheckedException
	 */
	public abstract List<ErrorMessage> matchMessage(String message, ErrorSystem system) throws ConfigurationCheckedException;
}
