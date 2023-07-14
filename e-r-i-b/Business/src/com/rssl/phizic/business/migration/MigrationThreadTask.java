package com.rssl.phizic.business.migration;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 03.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ����� �������� ��������
 */

public class MigrationThreadTask
{
	private Long id;
	private MigrationState state;
	private Calendar startDate;
	private Calendar endDate;
	private long goodCount;
	private long badCount;

	/**
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ������
	 * @param id ������������� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��������� ��������
	 */
	public MigrationState getState()
	{
		return state;
	}

	/**
	 * ������ ��������� ��������
	 * @param state ���������
	 */
	public void setState(MigrationState state)
	{
		this.state = state;
	}
	/**
	 * @return ������ ��������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * ������ ������ ��������
	 * @param startDate ������ ��������
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ���� ��������� ��������
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * ������ ���� ��������� ��������
	 * @param endDate ����
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * @return ���������� ������� �������������� ��������
	 */
	public long getGoodCount()
	{
		return goodCount;
	}

	/**
	 * ������ ���������� ������� �������������� ��������
	 * @param goodCount ����������
	 */
	public void setGoodCount(long goodCount)
	{
		this.goodCount = goodCount;
	}

	/**
	 * @return ���������� ���������������� ��������
	 */
	public long getBadCount()
	{
		return badCount;
	}

	/**
	 * ������ ���������� ���������������� ��������
	 * @param badCount ����������
	 */
	public void setBadCount(long badCount)
	{
		this.badCount = badCount;
	}
}
