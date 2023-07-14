package com.rssl.phizic.logging.monitoring;

import java.util.Calendar;

/**
 * ��������� ��������� ���������� �� ������� �����.
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
	 * @return ���� ������.
	 */
	public Calendar getReportDate()
	{
		return reportDate;
	}

	/**
	 * @param reportDate ���� ������.
	 */
	public void setReportDate(Calendar reportDate)
	{
		this.reportDate = reportDate;
	}

	/**
	 * @return ���������.
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state ���������.
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return �������� ������.
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * @param stateDescription �������� ������.
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}
}
