package com.rssl.phizic.business.migration;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ���������� � ��������
 */

public class TotalMigrationInfo extends MigrationInfo
{
	private final Calendar startDate;
	private final Calendar endDate;
	private final Long totalCount;
	private final long goodCount;
	private final long badCount;
	private final MigrationState state;

	TotalMigrationInfo(Long id, Calendar startDate, Calendar endDate, Long totalCount, long goodCount, long badCount, MigrationState state, Long batchSize)
	{
		super(id, batchSize);
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalCount = totalCount;
		this.goodCount = goodCount;
		this.badCount = badCount;
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
	 * @return ���� ��������� ��������
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * @return ����� ���������� ����������� ��������
	 */
	public Long getTotalCount()
	{
		return totalCount;
	}

	/**
	 * @return ���������� ������� �������������� ��������
	 */
	public long getGoodCount()
	{
		return goodCount;
	}

	/**
	 * @return ���������� ���������������� ��������
	 */
	public long getBadCount()
	{
		return badCount;
	}

	/**
	 * @return ��������� ��������
	 */
	public MigrationState getState()
	{
		return state;
	}
}
