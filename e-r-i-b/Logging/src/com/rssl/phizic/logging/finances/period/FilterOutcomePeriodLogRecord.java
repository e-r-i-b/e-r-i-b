package com.rssl.phizic.logging.finances.period;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Логгирование выборов периодов фильтрации расходов.
 * Запись выбранного периода.
 * @author Koptyaev
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */
public class FilterOutcomePeriodLogRecord implements Serializable
{
	private Long id;
	private Calendar date;
	private String terBank;
	private PeriodType periodType;
	private boolean isDefault = false;

	/**
	 * получить идентификатор записи
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор записи
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * получить дату логгирования
	 * @return дата
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * Установить дату логгирования
	 * @param date дата
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * получить номер тербанка
	 * @return тербанк
	 */
	public String getTerBank()
	{
		return terBank;
	}

	/**
	 * Установить номер тербанка
	 * @param terBank тербанк
	 */
	public void setTerBank(String terBank)
	{
		this.terBank = terBank;
	}

	/**
	 * получить тип периода (Месяц, до 10 дней, 10-20 дней,20-30 дней, 30-90 дней, 90-183 дня)
	 * @return тип периода
	 */
	public PeriodType getPeriodType()
	{
		return periodType;
	}
	/**
	 * установить тип периода (Месяц, до 10 дней, 10-20 дней,20-30 дней, 30-90 дней, 90-183 дня)
	 * @param periodType тип периода
	 */
	public void setPeriodType(PeriodType periodType)
	{
		this.periodType = periodType;
	}

	/**
	 * дефолтный ли тип периода
	 * @return дефолтный
	 */
	public boolean getIsDefault()
	{
		return isDefault;
	}

	/**
	 * установить дефолтный тип периода
	 * @param aDefault дефолтный
	 */
	public void setIsDefault(boolean aDefault)
	{
		isDefault = aDefault;
	}
}
