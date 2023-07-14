package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.text.ParseException;

/**
 * @author Mescheryakova
 * @ created 01.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * IT-отчет 'Отчет о качестве выполнения операций на дату (ежедневный)'
 */
public class CountOperationsDateITReport extends QualityOperationPeriodITReport
{
	private Calendar operation_date;
	private String type;

	public Calendar getOperation_date()
	{
		return operation_date;
	}

	public void setOperation_date(Calendar operation_date)
	{
		this.operation_date = operation_date;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getQualityOperatinsDateTB";
	}

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report) throws BusinessException
	{
		this.setReport_id(report);
		this.setTb_id(Long.decode(obj[0].toString()));
		this.setTb_name(obj[1].toString());
		this.setType(obj[2].toString());

		try
		{
			this.setOperation_date( DateHelper.parseCalendar(obj[3].toString()) );
		}
		catch(ParseException e)
		{
			throw new BusinessException(e.getMessage());
		}

		this.setCount(Long.decode(obj[4].toString()));
		this.setCountErrorOperations(Long.decode(obj[5].toString()));

		return this;
	}
}
