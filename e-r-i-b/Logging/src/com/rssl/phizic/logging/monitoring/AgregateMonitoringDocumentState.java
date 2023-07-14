package com.rssl.phizic.logging.monitoring;

import java.util.Calendar;

/**
 * Состояние агрегации документов за текущие сутки.
 *
 * @author bogdanov
 * @ created 19.03.15
 * @ $Author$
 * @ $Revision$
 */
public class AgregateMonitoringDocumentState
{
	private Calendar reportDate;
	private String state;
	private String stateDescription;

	/**
	 * @return дата отчета.
	 */
	public Calendar getReportDate()
	{
		return reportDate;
	}

	/**
	 * @param reportDate дата отчета.
	 */
	public void setReportDate(Calendar reportDate)
	{
		this.reportDate = reportDate;
	}

	/**
	 * @return состояние.
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state состояние.
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return описание ошибки.
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * @param stateDescription описание ошибки.
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}
}
