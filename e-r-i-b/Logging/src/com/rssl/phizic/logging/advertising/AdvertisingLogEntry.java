package com.rssl.phizic.logging.advertising;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ������ ������� ���������� �� �������� (����� �������, ���� �� ������ �������,
 * ���������� �������� ����� �������� �� ������(������) �������)
 * @author gladishev
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingLogEntry implements Serializable
{
	private Long id;                      // id ������.
	private Long advertisingId;           // ������ �� ��������� ����.
	private Calendar date;                // ����� �������.
	private AdvertisingLogEntryType type; // ��� �������.

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getAdvertisingId()
	{
		return advertisingId;
	}

	public void setAdvertisingId(Long advertisingId)
	{
		this.advertisingId = advertisingId;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public AdvertisingLogEntryType getType()
	{
		return type;
	}

	public void setType(AdvertisingLogEntryType type)
	{
		this.type = type;
	}
}
