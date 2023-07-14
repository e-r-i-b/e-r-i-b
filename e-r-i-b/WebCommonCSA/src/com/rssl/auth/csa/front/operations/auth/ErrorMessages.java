package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.ResourcePropertyReader;
import com.rssl.phizic.utils.StringHelper;

import java.text.MessageFormat;

/**
 * @author niculichev
 * @ created 25.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ErrorMessages
{
	private static final String DEFAULT_ERROR_KEY = "default.error.message";

	private static final String ERROR_MESSAGES_PATH = "com/rssl/auth/csa/front/operations/auth/error-messages.properties";
	private static final PropertyReader propertyReader = ConfigFactory.getReaderByFileName(ERROR_MESSAGES_PATH);

	/**
	 * Получить текстовку по исключению
	 * @param operation операция
	 * @param e исключение
	 * @param args аргументы
	 * @return текстовка
	 */
	public static String getMessage(Operation operation, LogicException e, Object... args)
	{
		return getMessage(operation, e, false, args);
	}

	/**
	 * Получить текстовку по исключению и дополнительному ключу
	 * @param operation операция
	 * @param additionalKey - дополнительная информация ключа
	 * @param e исключение
	 * @param args аргументы
	 * @return текстовка
	 */
	public static String getMessage(Operation operation, String additionalKey, LogicException e, Object... args)
	{
		return getMessage(operation, additionalKey, e, false, args);
	}

	/***
	 * Получить текстовку по исключению
	 * @param operation операция
	 * @param e исключения
	 * @param onlyResource true - вернуть текстовку только из error-messages.properties
	 * @param args агрументы
	 * @return текстовка
	 */
	public static String getMessage(Operation operation, LogicException e, boolean onlyResource, Object... args)
	{
		return getMessage(operation, null, e, onlyResource, args);
	}

	/***
	 * Получить текстовку по исключению и дополнительному ключу
	 * @param operation операция
	 * @param additionalKey - дополнительная информация ключа
	 * @param e исключения
	 * @param onlyResource true - вернуть текстовку только из error-messages.properties
	 * @param args агрументы
	 * @return текстовка
	 */
	public static String getMessage(Operation operation, String additionalKey, LogicException e, boolean onlyResource, Object... args)
	{
		String key = operation.getClass().getName() + "|" + e.getClass().getName();
		if (StringHelper.isNotEmpty(additionalKey))
			key +=  "|" + additionalKey;
		String message = getMessage(key, args);
		if(onlyResource || StringHelper.isNotEmpty(message))
			return message;

		message = e.getMessage();
		if(StringHelper.isNotEmpty(message))
			return message;

		return getMessage(DEFAULT_ERROR_KEY);
	}

	/**
	 * Получить текстовку по ключу
	 * @param key ключ
	 * @param args аргументы
	 * @return текстовка
	 */
	public static String getMessage(String key, Object... args)
	{
		String message = propertyReader.getProperty(key);
		if(message == null)
			return null;

		return new MessageFormat(message).format(args);
	}
}
