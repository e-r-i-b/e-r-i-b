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
 * ���������� � ����������� ��� ����������� ��������
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
	 * @return ������������� �����������
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� �����������
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������ �����������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ������������ �����������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ���� ���������� �����������
	 */
	public Calendar getNextReminderDate()
	{
		return nextReminderDate;
	}

	/**
	 * @param nextReminderDate ���� ���������� �����������
	 */
	public void setNextReminderDate(Calendar nextReminderDate)
	{
		this.nextReminderDate = nextReminderDate;
	}

	/**
	 * @return �����
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount �����
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return ���������� � �����������
	 */
	public ReminderInfo getInfo()
	{
		return info;
	}

	/**
	 * @param info ���������� � �����������
	 */
	public void setInfo(ReminderInfo info)
	{
		this.info = info;
	}

	/**
	 * @return ������������ ���� ����� �������
	 */
	public String getFormTypeName()
	{
		return formTypeName;
	}

	/**
	 * @param formTypeName ������������ ���� ����� �����������
	 */
	public void setFormTypeName(String formTypeName)
	{
		this.formTypeName = formTypeName;
	}

	/**
	 * @return ��������� �����������
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * @param state ��������� �����������
	 */
	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * @return ������������ ����������
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * @param providerName ������������ ����������
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @return �������� ��������� ����
	 */
	public String getKeyName()
	{
		return keyName;
	}

	/**
	 * @param keyName �������� ��������� ����
	 */
	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}

	/**
	 * @return �������� ��������� ����
	 */
	public String getKeyValue()
	{
		return keyValue;
	}

	/**
	 * @param keyValue �������� ��������� ����
	 */
	public void setKeyValue(String keyValue)
	{
		this.keyValue = keyValue;
	}
}
