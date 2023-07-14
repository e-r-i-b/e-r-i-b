package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.List;

/**
 * Интерфейс должен реализовываться всеми отчетами, для которых существует своя таблица в БД
 * @author Mescheryakova
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */

public interface ReportAdditionalInfo
{
	/**
	 * Получает заполненный объект по переданному массиву объектов
	 * @param obj - массив объектов
	 * @param report - отчет, на кот. ссылается создаваемый отчет
	 * @return - построенный по переданным параметрам отчет
	 */
	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report) throws BusinessException;

	/**
	 * Получает имя квери
	 * @param report - отчет
	 * @return - возвращает  название квери для отправки запроса к бд для получения отчета
	 */
	public String getQueryName(ReportAbstract report);

	/**
	 * Получаем допольнительные параметры для sql-запроса
	 * @param report - отчет
	 * @return хеш вида "ключ => значение", кот. потом будут подставляться в sql
	 */
	public Map<String,Object> getAdditionalParams(ReportAbstract report);

	/**
	 * Узнает, строится ли отчет по id-департаментов
	 * @return true - да, false - нет
	 */
	public boolean isIds();

	/**
	 * Обработать полученные из sql-запроса данные
	 * @param list результат sql-запроса
	 */
	public List processData(List list);
}
