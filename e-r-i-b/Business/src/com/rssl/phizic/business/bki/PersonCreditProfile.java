package com.rssl.phizic.business.bki;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������� �������
 */
public class PersonCreditProfile
{
	/**
	 *  ������������� �������. ��������� � ��������������� �������.
	 */
	private Long id;

	/**
	 * ������ "���� ��"
	 */
	private boolean connected;

	/**
	 * ����-����� ���������� ������� �������� � ��� (CHECK)
	 */
	private Calendar lastCheckRequest;

	/**
	 * ����-����� ���������� ��������� ������ ������
	 */
	private Calendar lastPayment;

	/**
	 * ����-����� ��������� ������ (������ GET)
	 */
	private Calendar lastReport;

	/**
	 * ������ ������ (����� ��� �� GET � ���� XML)
	 */
	private String report;

	/**
	 * ��������� ������ �� ������ ������ (GET)
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
