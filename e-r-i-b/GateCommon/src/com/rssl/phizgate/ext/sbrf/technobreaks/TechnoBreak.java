package com.rssl.phizgate.ext.sbrf.technobreaks;

import com.rssl.phizic.common.types.UUID;

import java.io.Serializable;
import java.util.Calendar;

/**
 * �������� ���������������� ��������
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class TechnoBreak implements Serializable
{
	private Long id;
	private UUID uuid;          //���������� ������������ �������������
	private String adapterUUID; // ������������� ������� �������
	private Calendar fromDate; // ���� ������ ��������
	private Calendar toDate; // ���� ����� ��������
	private PeriodicType periodic;  // �������������
	private String message; // ��������� �������
	private boolean defaultMessage = true; // ��� ��������� (true ��������� �� ���������)
	private TechnoBreakStatus status; // ������
	private boolean autoEnabled; //��������� �������������
	private boolean allowOfflinePayments; //��������� �� ������� ��� ����������� ��

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public Calendar getToDate()
	{
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	public PeriodicType getPeriodic()
	{
		return periodic;
	}

	public void setPeriodic(PeriodicType periodic)
	{
		this.periodic = periodic;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public boolean isDefaultMessage()
	{
		return defaultMessage;
	}

	public void setDefaultMessage(boolean defaultMessage)
	{
		this.defaultMessage = defaultMessage;
	}

	public TechnoBreakStatus getStatus()
	{
		return status;
	}

	public void setStatus(TechnoBreakStatus status)
	{
		this.status = status;
	}

	public String getAdapterUUID()
	{
		return adapterUUID;
	}

	public void setAdapterUUID(String adapterUUID)
	{
		this.adapterUUID = adapterUUID;
	}

	public boolean isAutoEnabled()
	{
		return autoEnabled;
	}

	public void setAutoEnabled(boolean autoEnabled)
	{
		this.autoEnabled = autoEnabled;
	}

	public boolean isAllowOfflinePayments()
	{
		return allowOfflinePayments;
	}

	public void setAllowOfflinePayments(boolean allowOfflinePayments)
	{
		this.allowOfflinePayments = allowOfflinePayments;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}
}
