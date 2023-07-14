package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.*;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.HashMap;
import java.text.DecimalFormat;

/**
 * @author Mescheryakova
 * @ created 30.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * IT-отчет 'Отчет о качестве выполнения операций за период'
 */

public class QualityOperationPeriodITReport  extends CountOperations
{
	private long tb_id;    //id ТБ
	private String tb_name;  // название ТБ
	private long countErrorOperations; // количество ошибочных операций

	private final static String SYSTEM_ERROR_PARAM_NAME = "SystemError";
	private final static String SYSTEM_ERROR_PARAM_VALUE = "(системная ошибка)";

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

	public long getCountErrorOperations()
	{
		return countErrorOperations;
	}

	public void setCountErrorOperations(long countErrorOperations)
	{
		this.countErrorOperations = countErrorOperations;
	}

	/**
	 * Считает процент неуспешных операций
	 * @return строку вида 0.00% 
	 */
	public String getPercentErrorOperations()
	{
		double percentErrors = (getCount() <= 0 ? 0 : (double) countErrorOperations * 100 / getCount());
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return decimalFormat.format(percentErrors) + '%';
	}

	/**
	 * Получает заполненный объект по переданному массиву объектов
	 * @param obj - массив объектов
	 * @param report - отчет, на кот. ссылается создаваемый отчет
	 * @return - построенный по переданным параметрам отчет
	 */	
	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)  throws BusinessException
	{
		this.setReport_id(report);
		this.setTb_id(Long.decode(obj[0].toString()));
		this.setTb_name(obj[1].toString());
		this.setCount(Long.decode(obj[2].toString()));
		this.setCountErrorOperations(Long.decode(obj[3].toString()));
		return this;
	}
	
	/**
	 * Получает имя квери
	 * @return - возвращает  название квери для отправки запроса к бд для получения отчета
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getQualityOperatinsPeriodTB";
	}

	/**
	 * Получаем допольнительные параметры для sql-запроса
	 * @param report - отчет
	 * @return хеш вида "ключ => значение", кот. потом будут подставляться в sql
	 */	
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> mapAllParams = super.getAdditionalParams(report);  // получаем хеш параметров для операций без ошибок

		// добавляем к параметрам sql для поля description запроса значения для подсчета ошибок
		Map<String, Object> systemError = new HashMap<String, Object>();
		for(Map.Entry<String, String> entry : sqlParamsDescription.entrySet())
			systemError.put(entry.getKey() + SYSTEM_ERROR_PARAM_NAME, entry.getValue() + SYSTEM_ERROR_PARAM_VALUE);

		mapAllParams.putAll(systemError);
		mapAllParams.putAll(sqlParamsDescription);

		return mapAllParams;
	}

	/**
	 * Получаем количество успешных операций
	 */
	public long getCountSuccessOperations()
	{
		return getCount() - countErrorOperations;
	}

	/**
	 * Узнает, строится ли отчет по id-департаментов
	 * @return true - да, false - нет
	 */
	public boolean isIds()
	{
		return true;
	}
}
