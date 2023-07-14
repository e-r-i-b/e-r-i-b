package com.rssl.phizic.config.account;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * ������ ��� ��������� �����  � �������� ������ ��� �������� ������ ��������� � �� � ������� ������ ������������� ����� �����
 *
 * @ author: Gololobov
 * @ created: 03.10.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningErrorsAutoRefreshConfig extends Config
{
	//������ ����� ������ � ������ �� ��������� ������
	private static final String ERROR_CODES_LIST = "com.rssl.account.opening.error.codes";
	private static final String ERRORS_SEPARATOR = ";";
	private static final String ERROR_DESCRIPTION_SEPARATOR = "\\|";
	private Map<String, String> errorsMap = new HashMap<String, String>(); //���� ������ � ����������

	public AccountOpeningErrorsAutoRefreshConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		String errorCodesListString = getProperty(ERROR_CODES_LIST);

		String[] errors = errorCodesListString.split(ERRORS_SEPARATOR);
		for (String errorInfo : errors)
		{
			String[] error = errorInfo.split(ERROR_DESCRIPTION_SEPARATOR);
			errorsMap.put(error[0],error[1]);
		}
	}

	/**
	 * ���� � ������ ������ � ���������
	 * @return Map<String, String> - K-��� ������, V-�������� ������
	 */
	public Map<String, String> getErrors()
	{
		return errorsMap;
	}

	/**
	 * ���������� �������� ������ �� � ����
	 * @param errorCode - ��� ������
	 * @return - String - �������� ������
	 */
	public String getErrorDescription(String errorCode)
	{
		if (StringHelper.isEmpty(errorCode))
			return null;

		Map<String, String> errorsMap = getErrors();
		return errorsMap.get(errorCode);
	}
}
