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
	 * �������� ��������� �� ����������
	 * @param operation ��������
	 * @param e ����������
	 * @param args ���������
	 * @return ���������
	 */
	public static String getMessage(Operation operation, LogicException e, Object... args)
	{
		return getMessage(operation, e, false, args);
	}

	/**
	 * �������� ��������� �� ���������� � ��������������� �����
	 * @param operation ��������
	 * @param additionalKey - �������������� ���������� �����
	 * @param e ����������
	 * @param args ���������
	 * @return ���������
	 */
	public static String getMessage(Operation operation, String additionalKey, LogicException e, Object... args)
	{
		return getMessage(operation, additionalKey, e, false, args);
	}

	/***
	 * �������� ��������� �� ����������
	 * @param operation ��������
	 * @param e ����������
	 * @param onlyResource true - ������� ��������� ������ �� error-messages.properties
	 * @param args ���������
	 * @return ���������
	 */
	public static String getMessage(Operation operation, LogicException e, boolean onlyResource, Object... args)
	{
		return getMessage(operation, null, e, onlyResource, args);
	}

	/***
	 * �������� ��������� �� ���������� � ��������������� �����
	 * @param operation ��������
	 * @param additionalKey - �������������� ���������� �����
	 * @param e ����������
	 * @param onlyResource true - ������� ��������� ������ �� error-messages.properties
	 * @param args ���������
	 * @return ���������
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
	 * �������� ��������� �� �����
	 * @param key ����
	 * @param args ���������
	 * @return ���������
	 */
	public static String getMessage(String key, Object... args)
	{
		String message = propertyReader.getProperty(key);
		if(message == null)
			return null;

		return new MessageFormat(message).format(args);
	}
}
