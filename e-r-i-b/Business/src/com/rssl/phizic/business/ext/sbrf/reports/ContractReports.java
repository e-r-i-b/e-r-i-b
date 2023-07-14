package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.logging.operations.ChangeUserTypeLogDataWrapper;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 12.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Класс для работы с отчетам вида "Количество договоров по..."
 * описывает все общие поля для отчетов данного вида
 */

public abstract class ContractReports  extends SubReports implements ReportAdditionalInfo
{
	private static final String ADD_SBOL_CLIENT     = ChangeUserTypeLogDataWrapper.KEY_PREFIX + ChangeUserTypeLogDataWrapper.ADD_KEY    + CreationType.SBOL + ChangeUserTypeLogDataWrapper.CLIENT_KEY + ChangeUserTypeLogDataWrapper.IN_DEPARTMENT_KEY;
	private static final String REMOVE_SBOL_CLIENT  = ChangeUserTypeLogDataWrapper.KEY_PREFIX + ChangeUserTypeLogDataWrapper.REMOVE_KEY + CreationType.SBOL + ChangeUserTypeLogDataWrapper.CLIENT_KEY + ChangeUserTypeLogDataWrapper.IN_DEPARTMENT_KEY;
	private static final String ADD_UDBO_CLIENT     = ChangeUserTypeLogDataWrapper.KEY_PREFIX + ChangeUserTypeLogDataWrapper.ADD_KEY    + CreationType.UDBO + ChangeUserTypeLogDataWrapper.CLIENT_KEY + ChangeUserTypeLogDataWrapper.IN_DEPARTMENT_KEY;
	private static final String REMOVE_UDBO_CLIENT  = ChangeUserTypeLogDataWrapper.KEY_PREFIX + ChangeUserTypeLogDataWrapper.REMOVE_KEY + CreationType.UDBO + ChangeUserTypeLogDataWrapper.CLIENT_KEY + ChangeUserTypeLogDataWrapper.IN_DEPARTMENT_KEY;

	private long id;
	private long tb_id;
	private String tb_name;
	private int count_UDBO_all;
	private int count_UDBO_month;
	private int count_UDBO_year;
	private int count_SBOL_all;
	private int count_SBOL_month;
	private int count_SBOL_year;
	private ReportAbstract report_id;

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
	}

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

	public int getCount_UDBO_all()
	{
		return count_UDBO_all;
	}

	public void setCount_UDBO_all(int count_UDBO_all)
	{
		this.count_UDBO_all = count_UDBO_all;
	}

	public int getCount_UDBO_month()
	{
		return count_UDBO_month;
	}

	public void setCount_UDBO_month(int count_UDBO_month)
	{
		this.count_UDBO_month = count_UDBO_month;
	}

	public int getCount_SBOL_all()
	{
		return count_SBOL_all;
	}

	public void setCount_SBOL_all(int count_SBOL_all)
	{
		this.count_SBOL_all = count_SBOL_all;
	}

	public int getCount_SBOL_month()
	{
		return count_SBOL_month;
	}

	public void setCount_SBOL_month(int count_SBOL_month)
	{
		this.count_SBOL_month = count_SBOL_month;
	}

	public int getCount_UDBO_year()
	{
		return count_UDBO_year;
	}

	public void setCount_UDBO_year(int count_UDBO_year)
	{
		this.count_UDBO_year = count_UDBO_year;
	}

	public int getCount_SBOL_year()
	{
		return count_SBOL_year;
	}

	public void setCount_SBOL_year(int count_SBOL_year)
	{
		this.count_SBOL_year = count_SBOL_year;
	}

	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object>  map = new HashMap<String, Object>();

		Calendar startDate = DateHelper.getCurrentDate();
		startDate.setTime( report.getStartDate().getTime() );
		startDate.add(Calendar.DATE, 1);  // переводим на день вперед,  т.к. в запросе используется between
		map.put("startDate", startDate);

		// получаем первое число месяца
		Calendar endDate = Calendar.getInstance();
		endDate.setTime( report.getStartDate().getTime() );
		endDate.clear(Calendar.DATE);
		endDate.set(Calendar.DATE, 1);
		endDate.get(Calendar.DATE);  // без этого день не устанавливается
		map.put("lastMonthDate", endDate);

		// получаем дату начала года
		Calendar newYear = Calendar.getInstance();
		newYear.setTime(report.getStartDate().getTime());
		newYear.clear(Calendar.MONTH);
		newYear.clear(Calendar.DATE);
		newYear.set(newYear.get(Calendar.YEAR), 0, 1);
		newYear.get(Calendar.DATE);  // без этого день не устанавливается
  		map.put("lastYearDate", newYear);

		// строки отбора
		map.put("addSBOLClient",    ADD_SBOL_CLIENT);
		map.put("removeSBOLClient", REMOVE_SBOL_CLIENT);
		map.put("addUDBOClient",    ADD_UDBO_CLIENT);
		map.put("removeUDBOClient", REMOVE_UDBO_CLIENT);

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
