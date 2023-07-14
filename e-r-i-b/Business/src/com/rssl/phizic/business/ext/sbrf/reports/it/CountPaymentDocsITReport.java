package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.CountOperationsReport;
import com.rssl.phizic.business.ext.sbrf.reports.CountOperationsSBRFReport;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;

import java.util.Map;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 13.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Подотчет 'Количество платежных документов в день' для отчета по бизнес-параметрам
 */
public class CountPaymentDocsITReport  implements ReportAdditionalInfo
{
	private long id;
	private long tb_id;
	private String tb_name;
	private double countDocsDayAvg;        // среднее кол-во платежных документов в день
	private long countDocsDayMax;          // максимальное кол-во платежных документов в день
	private long countOperationsSecondMax;  // кол-во бизнес-операций в секунду
	private ReportAbstract report_id;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getTb_id()
	{
		return tb_id;
	}

	public void setTb_id(long tb_id)
	{
		this.tb_id = tb_id;
	}

	public String getTb_name()
	{
		return tb_name;
	}

	public void setTb_name(String tb_name)
	{
		this.tb_name = tb_name;
	}

	public double getCountDocsDayAvg()
	{
		return countDocsDayAvg;
	}

	public void setCountDocsDayAvg(double countDocsDayAvg)
	{
		this.countDocsDayAvg = countDocsDayAvg;
	}

	public long getCountDocsDayMax()
	{
		return countDocsDayMax;
	}

	public void setCountDocsDayMax(long countDocsDayMax)
	{
		this.countDocsDayMax = countDocsDayMax;
	}

	public long getCountOperationsSecondMax()
	{
		return countOperationsSecondMax;
	}

	public void setCountOperationsSecondMax(long countOperationsSecondMax)
	{
		this.countOperationsSecondMax = countOperationsSecondMax;
	}

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
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
		this.setTb_id( Long.decode(obj[0].toString()) );
		this.setTb_name( obj[1].toString() );
		this.setCountDocsDayAvg( obj[2] == null ? 0 : Double.valueOf(obj[2].toString().trim()).doubleValue() );
		this.setCountDocsDayMax( obj[3] == null ? 0 : Long.decode(obj[3].toString()) );
		this.setCountOperationsSecondMax( obj[4] == null ? 0 : Long.decode(obj[4].toString()) );
		return this;
	}

	/**
	 * Получает имя квери
	 * @return - возвращает  название квери для отправки запроса к бд для получения отчета
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getBusinessParamsTB";
	}

	/**
	 * Получаем допольнительные параметры для sql-запроса
	 * @param report - отчет
	 * @return хеш вида "ключ => значение", кот. потом будут подставляться в sql
	 */
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		CountOperationsReport  countOperationsReport = new CountOperationsSBRFReport();
		Map<String, Object> map = countOperationsReport.getAdditionalParams(report);       // ставим такие же параметры как и в отчетах по операциям, чтобы гарантировать идентичность запросов для отчетов по списку бизнес-операций
		return map;
	}

	/**
	 * Узнает, строится ли отчет по id-департаментов
	 * @return true - да, false - нет
	 */
	public boolean isIds()
	{
		return true;
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
