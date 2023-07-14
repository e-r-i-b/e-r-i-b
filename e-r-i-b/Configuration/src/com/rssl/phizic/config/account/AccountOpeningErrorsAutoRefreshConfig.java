package com.rssl.phizic.config.account;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфиг для получения кодов  и описания ошибок при открытии вклада связанных с ТБ в котором должен обслуживаться новый вклад
 *
 * @ author: Gololobov
 * @ created: 03.10.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningErrorsAutoRefreshConfig extends Config
{
	//Список кодов ошибок в ответе на ооткрытие вклада
	private static final String ERROR_CODES_LIST = "com.rssl.account.opening.error.codes";
	private static final String ERRORS_SEPARATOR = ";";
	private static final String ERROR_DESCRIPTION_SEPARATOR = "\\|";
	private Map<String, String> errorsMap = new HashMap<String, String>(); //коды ошибок с описаниями

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
	 * Мапа с содами ошибок и описанием
	 * @return Map<String, String> - K-код ошибки, V-описание ошибки
	 */
	public Map<String, String> getErrors()
	{
		return errorsMap;
	}

	/**
	 * Возвращает описание ошибки по её коду
	 * @param errorCode - код ошибки
	 * @return - String - описание ошибки
	 */
	public String getErrorDescription(String errorCode)
	{
		if (StringHelper.isEmpty(errorCode))
			return null;

		Map<String, String> errorsMap = getErrors();
		return errorsMap.get(errorCode);
	}
}
