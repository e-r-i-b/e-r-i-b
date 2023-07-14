package com.rssl.phizic.logging.system.guest;

import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.LogLevel;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.SystemLogEntry;

/**
 * @author osminin
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - журнал системных действий гостевого клиента
 */
public class GuestSystemLogEntry extends SystemLogEntry
{
	private String phoneNumber;
	private long guestCode;

	/**
	 * ctor для хибернейт
	 */
	public GuestSystemLogEntry()
	{
	}

	/**
	 * ctor
	 * @param message сообщение
	 * @param level уровень записи
	 * @param source модуль
	 */
	public GuestSystemLogEntry(String message, LogLevel level, LogModule source)
	{
		super(message, level, source);

		setGuestCode(LogThreadContext.getGuestCode());
		setPhoneNumber(LogThreadContext.getGuestPhoneNumber());
	}

	/**
	 * ctor
	 * @param entry запись в журнал
	 */
	public GuestSystemLogEntry(GuestSystemLogEntry entry)
	{
		super(entry);

		setGuestCode(entry.getGuestCode());
		setPhoneNumber(entry.getPhoneNumber());
	}

	/**
	 * ctor
	 * @param entry запись в журнал
	 */
	public GuestSystemLogEntry(SystemLogEntry entry)
	{
		super(entry);
	}

	/**
	 * @return код гостевого клиента
	 */
	public Long getGuestCode()
	{
		return guestCode;
	}

	/**
	 * @param guestCode код гостевого клиента
	 */
	public void setGuestCode(Long guestCode)
	{
		this.guestCode = guestCode;
	}

	/**
	 * @return номер телефона
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param phoneNumber номер телефона
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
