package com.rssl.phizic.business.migration;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 03.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Задача на поток миграции клиентов
 */

public class MigrationThreadTask
{
	private Long id;
	private MigrationState state;
	private Calendar startDate;
	private Calendar endDate;
	private long goodCount;
	private long badCount;

	/**
	 * @return идентификатор записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор записи
	 * @param id идентификатор записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return состояние миграции
	 */
	public MigrationState getState()
	{
		return state;
	}

	/**
	 * задать состояние миграции
	 * @param state состояние
	 */
	public void setState(MigrationState state)
	{
		this.state = state;
	}
	/**
	 * @return начало миграции
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * задать начало миграции
	 * @param startDate начало миграции
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return дата окончания миграции
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * задать дату окончания миграции
	 * @param endDate дата
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * @return количество успешно смигрированных клиентов
	 */
	public long getGoodCount()
	{
		return goodCount;
	}

	/**
	 * задать количество успешно смигрированных клиентов
	 * @param goodCount количество
	 */
	public void setGoodCount(long goodCount)
	{
		this.goodCount = goodCount;
	}

	/**
	 * @return количество несмигрированных клиентов
	 */
	public long getBadCount()
	{
		return badCount;
	}

	/**
	 * задать количество несмигрированных клиентов
	 * @param badCount количество
	 */
	public void setBadCount(long badCount)
	{
		this.badCount = badCount;
	}
}
