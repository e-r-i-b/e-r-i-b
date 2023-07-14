package com.rssl.phizic.logging.settings;


import java.io.Serializable;
import java.util.Calendar;

/**
 * запись лога об оповещении пользователя
 * @author tisov
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */

public class UserNotificationLogRecord  implements Serializable
{
	private Long id;
	private Long loginId;
	private Calendar additionDate;
	private NotificationInputType type;
	private String value;

	/**
	 * геттер для идентификатора записи
	 * @return - ид записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * сеттер идентификатора записи
	 * @param id - новый ид записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * геттер идентификатора логина пользователя
	 * @return - ид логина
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * сеттер идентификатора логина пользователя
	 * @param loginId - новый ид
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * геттер даты создания записи
	 * @return - дата создания
	 */
	public Calendar getAdditionDate()
	{
		return additionDate;
	}

	/**
	 * сеттер даты создания записи
	 * @param additionDate - новая дата создания
	 */
	public void setAdditionDate(Calendar additionDate)
	{
		this.additionDate = additionDate;
	}

	/**
	 * геттер типа оповещения
	 * @return - тип оповещения
	 */
	public NotificationInputType getType()
	{
		return type;
	}

	/**
	 * сеттер типа оповещения
	 * @param type - новый тип оповещения
	 */
	public void setType(NotificationInputType type)
	{
		this.type = type;
	}

	/**
	 * геттер значения
	 * @return - значение
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * сеттер значения
	 * @param value - новое значение
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
