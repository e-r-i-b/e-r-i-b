package com.rssl.phizic.business.migration;

import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� �������� ��������
 */

public class MigrationTask
{
	private Long id;
	private Long totalCount;
	private boolean needStop;
	private Long batchSize;
	private List<MigrationThreadTask> threadTasks;

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
	 * @return ����� ���������� ����������� ��������
	 */
	public Long getTotalCount()
	{
		return totalCount;
	}

	/**
	 * ������ ����� ���������� ����������� ��������
	 * @param totalCount ����������
	 */
	public void setTotalCount(Long totalCount)
	{
		this.totalCount = totalCount;
	}

	/**
	 * @return ���� ���������
	 */
	public boolean isNeedStop()
	{
		return needStop;
	}

	/**
	 * ������ ���� ���������
	 * @param needStop ����
	 */
	public void setNeedStop(boolean needStop)
	{
		this.needStop = needStop;
	}

	/**
	 * @return ������ ����� ��������
	 */
	public Long getBatchSize()
	{
		return batchSize;
	}

	/**
	 * ������ ������ ����� ��������
	 * @param batchSize ������
	 */
	public void setBatchSize(Long batchSize)
	{
		this.batchSize = batchSize;
	}

	/**
	 * @return ������ ����� �� ������ �������� ��������
	 */
	public List<MigrationThreadTask> getThreadTasks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return threadTasks;
	}

	/**
	 * ������ ������ ����� �� ������ �������� ��������
	 * @param threadTasks ������
	 */
	public void setThreadTasks(List<MigrationThreadTask> threadTasks)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.threadTasks = threadTasks;
	}
}
