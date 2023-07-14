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
 * �������� - ������ ��������� �������� ��������� �������
 */
public class GuestSystemLogEntry extends SystemLogEntry
{
	private String phoneNumber;
	private long guestCode;

	/**
	 * ctor ��� ���������
	 */
	public GuestSystemLogEntry()
	{
	}

	/**
	 * ctor
	 * @param message ���������
	 * @param level ������� ������
	 * @param source ������
	 */
	public GuestSystemLogEntry(String message, LogLevel level, LogModule source)
	{
		super(message, level, source);

		setGuestCode(LogThreadContext.getGuestCode());
		setPhoneNumber(LogThreadContext.getGuestPhoneNumber());
	}

	/**
	 * ctor
	 * @param entry ������ � ������
	 */
	public GuestSystemLogEntry(GuestSystemLogEntry entry)
	{
		super(entry);

		setGuestCode(entry.getGuestCode());
		setPhoneNumber(entry.getPhoneNumber());
	}

	/**
	 * ctor
	 * @param entry ������ � ������
	 */
	public GuestSystemLogEntry(SystemLogEntry entry)
	{
		super(entry);
	}

	/**
	 * @return ��� ��������� �������
	 */
	public Long getGuestCode()
	{
		return guestCode;
	}

	/**
	 * @param guestCode ��� ��������� �������
	 */
	public void setGuestCode(Long guestCode)
	{
		this.guestCode = guestCode;
	}

	/**
	 * @return ����� ��������
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param phoneNumber ����� ��������
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
