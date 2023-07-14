package com.rssl.phizic.business.monitoring;

/**
 * @author mihaylov
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 */
//параметры мониторинга
public enum MonitoringReport
{
	TOTAL_AGREEMENT_COUNT("Общее количество договоров"),

	TOTAL_DISOLV_AGREEMENT_COUNT("Общее количество расторгнутых договоров"),

	TODAY_AGREEMENT_COUNT("Количество заключенных договоров за текущий день"),

	TODAY_DISOLV_AGREEMENT_COUNT("Количество расторгнутых договоров за текущий день"),

	USER_COUNT("Количество клиентов онлайн"),

	EMPLOYEE_COUNT("Количество сотрудников онлайн"),

	PAYMENT_COUNT_TODAY("Количество платежей за текущий день"),

	TRANSFER_COUNT_TODAY("Количество переводов за текущий день"),

	LOAN_PAYMENT_COUNT_TODAY("Количество операций погашения кредита за текущий день"),

	ERROR_DOCUMENT_COUNT_TODAY("Количество документов за текущий день с ошибками"),

	PAYMENT_COUNT_YESTERDAY("Количество платежей за вчерашний день"),

	TRANSFER_COUNT_YESTERDAY("Количество переводов за вчерашний день"),

	LOAN_PAYMENT_COUNT_YESTERDAY("Количество операций погашения кредита за вчерашний день"),

	ERROR_DOCUMENT_COUNT_YESTERDAY("Количество документов за вчерашний день с ошибками"),

	DISPATCH_DOCUMENT_COUNT("Количество документов в обработке"),

	DELAYED_DOCUMENT_COUNT("Количество документов с задержкой обработки более 30 минут");
	
	
	private String description;

	MonitoringReport(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
