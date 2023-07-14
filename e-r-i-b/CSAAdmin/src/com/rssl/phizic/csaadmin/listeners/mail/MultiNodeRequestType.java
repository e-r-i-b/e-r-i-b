package com.rssl.phizic.csaadmin.listeners.mail;

import com.rssl.phizic.common.types.mail.Constants;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Тип запроса в несколько блоков на получение данных
 */
public enum MultiNodeRequestType
{
	INCOME_MAIL(Constants.GET_INCOME_MAIL_LIST_REQUEST_NAME),
	OUTCOME_MAIL(Constants.GET_OUTCOME_MAIL_LIST_REQUEST_NAME),
	REMOVED_MAIL(Constants.GET_REMOVED_MAIL_LIST_REQUEST_NAME),
	MAIL_STATISTICS(Constants.GET_MAIL_STATISTICS_REQUEST_NAME),
	MAIL_EMPLOYEE_STATISTICS(Constants.GET_MAIL_EMPLOYEE_STATISTICS_REQUEST_NAME),
	MAIL_AVERAGE_TIME(Constants.GET_MAIL_AVERAGE_TIME_REQUEST_NAME),
	MAIL_FIRST_DATE(Constants.GET_MAIL_FIRST_DATE_REQUEST_NAME);

	private String requestName;

	private MultiNodeRequestType(String requestName)
	{
		this.requestName = requestName;
	}

	/**
	 * @return название запроса на получение данных из блоков
	 */
	public String getRequestName()
	{
		return requestName;
	}

}
