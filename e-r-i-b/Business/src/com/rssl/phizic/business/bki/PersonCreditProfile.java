package com.rssl.phizic.business.bki;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Кредитный профиль клиента
 */
public class PersonCreditProfile
{
	/**
	 *  Идентификатор профиля. Совпадает с идентификатором персоны.
	 */
	private Long id;

	/**
	 * Флажок "есть КИ"
	 */
	private boolean connected;

	/**
	 * Дата-время последнего запроса проверки в БКИ (CHECK)
	 */
	private Calendar lastCheckRequest;

	/**
	 * Дата-время исполнения документа оплаты отчета
	 */
	private Calendar lastPayment;

	/**
	 * Дата-время получения отчёта (только GET)
	 */
	private Calendar lastReport;

	/**
	 * Данные отчёта (ответ БКИ на GET в виде XML)
	 */
	private String report;

	/**
	 * Последняя ошибка на запрос отчета (GET)
	 */
	private Calendar lastGetError;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean isConnected()
	{
		return connected;
	}

	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}

	public Calendar getLastCheckRequest()
	{
		return lastCheckRequest;
	}

	public void setLastCheckRequest(Calendar lastCheckRequest)
	{
		this.lastCheckRequest = lastCheckRequest;
	}

	public Calendar getLastPayment()
	{
		return lastPayment;
	}

	public void setLastPayment(Calendar lastPayment)
	{
		this.lastPayment = lastPayment;
	}

	public Calendar getLastReport()
	{
		return lastReport;
	}

	public void setLastReport(Calendar lastReport)
	{
		this.lastReport = lastReport;
	}

	public String getReport()
	{
		return report;
	}

	public void setReport(String report)
	{
		this.report = report;
	}

	public Calendar getLastGetError()
	{
		return lastGetError;
	}

	public void setLastGetError(Calendar lastGetError)
	{
		this.lastGetError = lastGetError;
	}
}
