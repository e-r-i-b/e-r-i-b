package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 14.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class CountUsersDayITReport  implements ReportAdditionalInfo
{
	private long id;
	private ReportAbstract report_id;
	private double countUsersDayAvg;
	private long countUsersDayMax;

	public static final boolean isIds = true;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
	}

	public double getCountUsersDayAvg()
	{
		return countUsersDayAvg;
	}

	public void setCountUsersDayAvg(double countUsersDayAvg)
	{
		this.countUsersDayAvg = countUsersDayAvg;
	}

	public long getCountUsersDayMax()
	{
		return countUsersDayMax;
	}

	public void setCountUsersDayMax(long countUsersDayMax)
	{
		this.countUsersDayMax = countUsersDayMax;
	}

	/**
	 * Получаем допольнительные параметры для sql-запроса
	 * @param report - отчет
	 * @return хеш вида "ключ => значение", кот. потом будут подставляться в sql
	 */
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar endDate = Calendar.getInstance();
		endDate.setTime( report.getEndDate().getTime() );
		endDate.add(Calendar.DATE, 1);     // + 1 день, т.к. в запросе присутствует between

		map.put("endDate", endDate);
		map.put("description", "Текущее кол-во сессий в приложении PhizIC равно ");
		map.put("beforeIP", "на машине с адресом ");
		map.put("application", "PhizIC");
		
		return map;
	}

	/**
	 * Получает заполненный объект по переданному массиву объектов
	 * @param obj - массив объектов
	 * @param report - отчет, на кот. ссылается создаваемый отчет
	 * @return - построенный по переданным параметрам отчет
	 */
	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setCountUsersDayAvg( obj[0] == null ? 0 : Double.valueOf(obj[0].toString().trim()).doubleValue() );
		this.setCountUsersDayMax( obj[1] == null ? 0 : Long.decode(obj[1].toString()) );
		return this;
	}

	/**
	 * Получает имя квери
	 * @return - возвращает  название квери для отправки запроса к бд для получения отчета
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getBusinessParamsSBRF";
	}

	/**
	 * Узнает, строится ли отчет по id-департаментов
	 * @return true - да, false - нет
	 */
	public boolean isIds()
	{
		return false;
	}

	/**
	 * Обработать полученные из sql-запроса данные
	 * @param list результат sql-запроса
	 */
	public List processData(List list)
	{
		return list;
	}
}
