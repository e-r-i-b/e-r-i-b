package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessLogicException;

import java.util.Calendar;


/**
 * @author Mescheryakova
 * @ created 18.06.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class ReportAbstract
{
	private long id;
	private char state;
	private char type;
	private Calendar date;
	private Calendar startDate;
	private Calendar endDate;
	private String params;

	public void setId(long id)
	{
		this.id = id;
	}

	public long getId()
	{
		return this.id;
	}

	public void setState(char state)
	{
		this.state = state;
	}

	public char getState()
	{
		return this.state;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setType(char type)
	{
		this.type = type;
	}

	public char getType()
	{
		return this.type;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public String getParams()
	{
		return params;
	}

	public void setParams(String params) throws BusinessLogicException
	{
		if (params != null && params.length() >= 4000)             // ограничение на поле в БД
		    throw new BusinessLogicException("Слишком большое количество выбранных подразделений");		

		this.params = params;
	}

	/*
		Получает ссылку на подробный отчет
	 */
	public String getLinkToReport()
	{
		String link = null;

		if (this == null || getState() != 'o')       // ссылка должна отображаться только для отчетов со статусом "Исполнен"
			return link;

		link = TypeReport.LINK_REPORT_BY_CODE.get( getType() );

		if (link != null)
			link += "?id=" + getId();

		return link;
	}

	/*
		Получает текстовое название отчета
	 */
	public String getTypeReportNameByCode()
	{
		return TypeReport.NAME_TYPE_REPORT_BY_CODE.get( getType() );
	}

	/*
		Получает текстовое название состояния
	 */
	public String getStateNameByCode()
	{
		return StateReport.NAME_STATE_BY_CODE.get( getState() );
	}
}
