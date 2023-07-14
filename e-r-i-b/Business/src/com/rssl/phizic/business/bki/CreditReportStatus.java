package com.rssl.phizic.business.bki;

/**
 * Статус запроса кредитного отчета
 * @author Rtischeva
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 */
public enum CreditReportStatus
{
	WAITING, //ожидание кредитного отчета

	RECEIVED, //кредитный отчет получен

	ERROR, //произошла ошибка
}