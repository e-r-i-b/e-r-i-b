package com.rssl.phizicgate.mdm.common;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� �������
 */

public class LoanInfo
{
	private String number;
	private String additionalNumber;
	private String legalNumber;
	private String legalName;
	private Calendar startDate;
	private Calendar closingDate;

	/**
	 * @return ����� ��������
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * ������ ����� ��������
	 * @param number �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return ����� ��������������� ����������
	 */
	public String getAdditionalNumber()
	{
		return additionalNumber;
	}

	/**
	 * ������ ����� ��������������� ����������
	 * @param additionalNumber �����
	 */
	public void setAdditionalNumber(String additionalNumber)
	{
		this.additionalNumber = additionalNumber;
	}

	/**
	 * @return ����� ���������� �������� ������������ ����
	 */
	public String getLegalNumber()
	{
		return legalNumber;
	}

	/**
	 * ������ ����� ���������� �������� ������������ ����
	 * @param legalNumber �����
	 */
	public void setLegalNumber(String legalNumber)
	{
		this.legalNumber = legalNumber;
	}

	/**
	 * @return �������� ������������ ����
	 */
	public String getLegalName()
	{
		return legalName;
	}

	/**
	 * ������ �������� ������������ ����
	 * @param legalName ��������
	 */
	public void setLegalName(String legalName)
	{
		this.legalName = legalName;
	}

	/**
	 * @return ���� ������ ��������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * ������ ���� ������ ��������
	 * @param startDate ����
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ���� ��������� ��������
	 */
	public Calendar getClosingDate()
	{
		return closingDate;
	}

	/**
	 * ������ ���� ��������� ��������
	 * @param closingDate ����
	 */
	public void setClosingDate(Calendar closingDate)
	{
		this.closingDate = closingDate;
	}
}
