package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отчет "Количество операций по СБРФ за период"
 */

public class CountOperationsSBRFReport  extends CountOperationsReport
{
	/**
	 * Получает заполненный объект по переданному массиву объектов
	 * @param obj - массив объектов
	 * @param report - отчет, на кот. ссылается создаваемый отчет
	 * @return - построенный по переданным параметрам отчет
	 */
	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setType( obj[0] == null ? " " : obj[0].toString() );
		this.setCurrency( obj[1] == null ? " " : obj[1].toString() );
		this.setCount( Long.decode(obj[2].toString()) );
		this.setAmount( obj[3] == null ? 0 : Float.valueOf(obj[3].toString().trim()).floatValue() );
		return this;
	}

	/**
	 * Получает имя квери
	 * @return - возвращает  название квери для отправки запроса к бд для получения отчета
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getOperationsSBRF";
	}

	/**
	 * Узнает, строится ли отчет по id-департаментов
	 * @return true - да, false - нет
	 */	
	public boolean isIds()
	{
		return false;
	}
}
