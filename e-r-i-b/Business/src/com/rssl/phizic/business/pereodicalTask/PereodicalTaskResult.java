package com.rssl.phizic.business.pereodicalTask;

import com.rssl.phizic.business.operations.background.TaskResultBase;

/**
 * User: Moshenko
 * Date: 01.11.2011
 * Time: 12:30:19
 * ��������� ���������� ������ �������� 
 */
public class PereodicalTaskResult extends TaskResultBase
{
	/**
	 * id
	 */
	private Long id;
   	/**
	 * id task
	 */
	private PereodicalTask task;
	/**
	 * ���������� ����������� �������
	 */
	private Long successlResultCount = 0l;
	/**
	 * ���������� ��������� �������
	 */
	private Long totalResultCount =0l;

	/**
	 * ����� ��� ���������� �������� �������� �������
	 */
	public void successRecordProcessed()
	{
		if (successlResultCount == null)
			successlResultCount = 0L;

		this.successlResultCount++ ;
	}

	public Long getSuccesslResultCount()
	{
		return successlResultCount;
	}

	public void setSuccesslResultCount(Long successlResultCount)
	{
		this.successlResultCount = successlResultCount;
	}

	public Long getTotalResultCount()
	{
		return totalResultCount;
	}

	public void setTotalResultCount(Long totalResultCount)
	{
		this.totalResultCount = totalResultCount;
	}

	public PereodicalTask getTask()
	{
		return task;
	}

	public void setTask(PereodicalTask task)
	{
		this.task = task;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}

