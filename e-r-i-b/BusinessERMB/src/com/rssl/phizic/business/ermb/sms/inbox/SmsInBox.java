package com.rssl.phizic.business.ermb.sms.inbox;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 07.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Входящая СМС
 */
public class SmsInBox
{
	/**
	 * Уникальный идентификатор
	 */
	private UUID uuid;
	/**
	 * Время
	 */
	private Calendar time;
	/**
	 * Номер телефона
	 */
	private PhoneNumber phone;
	/**
	 * Текст СМС
	 */
	private String text;

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	public Calendar getTime()
	{
		return time;
	}

	public void setTime(Calendar time)
	{
		this.time = time;
	}

	public PhoneNumber getPhone()
	{
		return phone;
	}

	public void setPhone(PhoneNumber phone)
	{
		this.phone = phone;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	@Override
	public String toString()
	{
		return "SmsInBox{" +
				"uuid=" + uuid +
				", time=" + time +
				", phone=" + phone +
				", text='" + text + '\'' +
				'}';
	}
}
