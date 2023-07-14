package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.logging.Constants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class ActiveUsersReport extends SubReports implements ReportAdditionalInfo
{
	private Long id;
	private Long tb_id;
	private String tb_name;
	private Long count_getLogins;
	private Long count_auth;
	private Long count_UDBO_all;
	private Long count_SBOL_all;
	private ReportAbstract report_id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getTb_id()
	{
		return tb_id;
	}

	public void setTb_id(Long tb_id)
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

	public Long getCount_getLogins()
	{
		return count_getLogins;
	}

	public void setCount_getLogins(Long count_getLogins)
	{
		this.count_getLogins = count_getLogins;
	}

	public Long getCount_auth()
	{
		return count_auth;
	}

	public void setCount_auth(Long count_auth)
	{
		this.count_auth = count_auth;
	}

	public Long getCount_UDBO_all()
	{
		return count_UDBO_all;
	}

	public void setCount_UDBO_all(Long count_UDBO_all)
	{
		this.count_UDBO_all = count_UDBO_all;
	}

	public Long getCount_SBOL_all()
	{
		return count_SBOL_all;
	}

	public void setCount_SBOL_all(Long count_SBOL_all)
	{
		this.count_SBOL_all = count_SBOL_all;
	}

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
	}

	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Calendar nextDay = Calendar.getInstance();
		nextDay.setTime( report.getStartDate().getTime() ); 
		nextDay.add(Calendar.DATE, 1); // получаем следующий день, т.к. в запросе используетс€ between

		Calendar startDay = Calendar.getInstance();
		startDay.setTime( report.getStartDate().getTime() );
		startDay.clear(Calendar.DATE);
		startDay.set(Calendar.DATE, 1); // первое число текущего мес€ца
		startDay.get(Calendar.DATE); // без этого не сохран€етс€	

		// параметры фильтрации
		Map<String, Object>  map = new HashMap<String, Object>();
		map.put("nextDayDate", nextDay);
		map.put("startDate", startDay);
		map.put("auth",  Constants.AUTHENTICATION_FINISH_KEY);
		map.put("passw", "com.rssl.phizic.web.client.ext.sbrf.security.ConfirmPasswordLoginAction.generateNewPassword");
		map.put("sbol", "SBOL");
		map.put("application", "PhizIC");
		return map;
	}

	/**
	 * ”знает, строитс€ ли отчет по id-департаментов
	 * @return true - да, false - нет
	 */
	public boolean isIds()
	{
		return true;
	}

	/**
	 * ќбработать полученные из sql-запроса данные
	 * @param list результат sql-запроса
	 */
	public List processData(List list)
	{
		return list;
	}
}
