package com.rssl.phizic.business.counters;

import com.rssl.phizic.context.PersonContext;

import java.util.Calendar;
import java.util.Date;

/**
 * ������� ��� ����������� ������������
 * @author niculichev
 * @ created 28.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class UserCounter
{
	private Long id;
	private Long loginId;   // ������������� ������
	private Long value;   // �������
	private Date changeDate;    // ���� ���������� ���������
	private CounterType counterType;    // ��� ��������
	private Calendar blockUntil;    // ���� ��������� ����������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getValue()
	{
		return value;
	}

	public void setValue(Long counter)
	{
		this.value = counter;
	}

	public Date getChangeDate()
	{
		return changeDate;
	}

	public void setChangeDate(Date changeDate)
	{
		this.changeDate = changeDate;
	}

	public void increment()
	{
		this.value++;
	}
	
	public void reset()
	{
		this.changeDate = null;
		this.value = 0L;
		this.blockUntil = null;
		if (PersonContext.isAvailable() && counterType == (CounterType.PROMO_CODE))
			PersonContext.getPersonDataProvider().getPersonData().setPromoBlockUntil(null);
	}

	public CounterType getCounterType()
	{
		return counterType;
	}

	public void setCounterType(CounterType counterType)
	{
		this.counterType = counterType;
	}

	public Calendar getBlockUntil()
	{
		return blockUntil;
	}

	public void setBlockUntil(Calendar blockUntil)
	{
		this.blockUntil = blockUntil;
	}
}
