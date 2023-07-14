package com.rssl.phizic.business.finances.financeCalendar;

import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.payments.template.ReminderInfo;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Информация о напоминании для финансового каледаря
 */
public class CalendarDayExtractByReminderDescription
{
	private long id;
	private ReminderInfo info;
	private String name;
	private Calendar nextReminderDate;
	private BigDecimal amount;
	private String formTypeName;
	private State state;
	private String providerName;
	private String keyName;
	private String keyValue;

	/**
	 * @return идентификатор напоминания
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор напоминания
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return наименование напоминания
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name наименование напоминания
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return дата ближайшего напоминания
	 */
	public Calendar getNextReminderDate()
	{
		return nextReminderDate;
	}

	/**
	 * @param nextReminderDate дата ближайшего напоминания
	 */
	public void setNextReminderDate(Calendar nextReminderDate)
	{
		this.nextReminderDate = nextReminderDate;
	}

	/**
	 * @return сумма
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount сумма
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return информация о напоминании
	 */
	public ReminderInfo getInfo()
	{
		return info;
	}

	/**
	 * @param info информация о напоминании
	 */
	public void setInfo(ReminderInfo info)
	{
		this.info = info;
	}

	/**
	 * @return наименование типа формы шаблона
	 */
	public String getFormTypeName()
	{
		return formTypeName;
	}

	/**
	 * @param formTypeName наименование типа формы напоминания
	 */
	public void setFormTypeName(String formTypeName)
	{
		this.formTypeName = formTypeName;
	}

	/**
	 * @return состояние напоминания
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * @param state состояние напоминания
	 */
	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * @return наименование получателя
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * @param providerName наименование получателя
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @return название ключевого поля
	 */
	public String getKeyName()
	{
		return keyName;
	}

	/**
	 * @param keyName название ключевого поля
	 */
	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}

	/**
	 * @return значение ключевого поля
	 */
	public String getKeyValue()
	{
		return keyValue;
	}

	/**
	 * @param keyValue значение ключевого поля
	 */
	public void setKeyValue(String keyValue)
	{
		this.keyValue = keyValue;
	}
}
