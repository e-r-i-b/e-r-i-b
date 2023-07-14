package com.rssl.phizic.business.ermb.migration.list.task;

import java.util.Set;

/**
 * ������-��������, ����������� � ��������� ������
 * @author Puzikov
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 */

public interface Task extends Runnable
{
	/**
	 * @return ��� ������
	 */
	public TaskType getType();

	/**
	 * @return ������� ��������� ������
	 */
	public String getStatus();

	/**
	 * @return ��������� �� ������
	 */
	public boolean isDone();

	/**
	 * �������� ���������� ������
	 */
	public void interrupt();

	/**
	 * @return ������, ����������� � �������������� ���������� � ������
	 */
	public Set<TaskType> getIllegalTasks();
}
