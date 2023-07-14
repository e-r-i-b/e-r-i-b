package com.rssl.phizic.business.dictionaries.synchronization.information;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 22.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������������� ������������
 */

public class SynchronizationStateEntity implements Serializable
{
	private Long lastUpdateId;
	private Calendar lastUpdateDate;
	private SynchronizationState state;
	private Long errorCount;

	/**
	 * @return ������������� ��������� ����������� ������
	 */
	public Long getLastUpdateId()
	{
		return lastUpdateId;
	}

	/**
	 * ������ ������������� ��������� ����������� ������
	 * @param lastUpdateId ������������� ��������� ����������� ������
	 */
	public void setLastUpdateId(Long lastUpdateId)
	{
		this.lastUpdateId = lastUpdateId;
	}

	/**
	 * @return ���� ��������� ����������� ������
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * ������ ������������� ����������� ������
	 * @param lastUpdateDate ���� ��������� ����������� ������
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return ��������� ����������
	 */
	public SynchronizationState getState()
	{
		return state;
	}

	/**
	 * ������ ��������� ����������
	 * @param state ��������� ����������
	 */
	public void setState(SynchronizationState state)
	{
		this.state = state;
	}

	/**
	 * @return ���������� ������
	 */
	public Long getErrorCount()
	{
		return errorCount;
	}

	/**
	 * ������ ���������� ������
	 * @param errorCount ���������� ������
	 */
	public void setErrorCount(Long errorCount)
	{
		this.errorCount = errorCount;
	}
}
