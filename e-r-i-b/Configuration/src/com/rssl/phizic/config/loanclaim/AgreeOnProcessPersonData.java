package com.rssl.phizic.config.loanclaim;

import java.util.Calendar;

/**
 * @author Nady
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * ���������� "���������� �� ��������� ������������ ������"
 */
public class AgreeOnProcessPersonData
{
	private int id;
	/**
	 * ���� ����������
	 */
	private Calendar effectiveDate;
	/**
	 * html-����� ������� ����������
	 */
	private String conditionsText;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Calendar getEffectiveDate()
	{
		return effectiveDate;
	}

	public void setEffectiveDate(Calendar effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public String getConditionsText()
	{
		return conditionsText;
	}

	public void setConditionsText(String conditionsText)
	{
		this.conditionsText = conditionsText;
	}
}
