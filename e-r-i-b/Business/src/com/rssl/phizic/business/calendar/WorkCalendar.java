package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Gainanov
 * @ created 24.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class WorkCalendar extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private String name;
	private String tb;//Департамент к которому привязан календарь.
	/** сюда попадают только те дни (рабочие или выходные), которые отличаются от стандартных
	 * то есть, если пользователь принял календарь "по умолчанию", то workDays = пустой сет **/
	private Set<WorkDay> workDays = new HashSet<WorkDay>();

	/**
	 * получить ид календаря
	 * @return ид календаря
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * установить ид календаря
	 * @param id ид календаря
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * получить имя календаря
	 * @return имя календаря
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * установить имя календаря
	 * @param name имя календаря
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * получить набор дней в календаре
	 * @return набор дней в календаре
	 */
	public Set<WorkDay> getWorkDays()
	{
		return workDays;
	}

	/**
	 * установить набор дней в календаре
	 * @param workDays набор дней в календаре
	 */
	public void setWorkDays(Set<WorkDay> workDays)
	{
		this.workDays = workDays;
	}

	/**
	 * @return номер ТБ к которому привязан календарь
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb номер ТБ к которому привязан календарь
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
