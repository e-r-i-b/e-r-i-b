package com.rssl.phizic.business.dictionaries.synchronization.information;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ����������� � �����
 */

public class DictionaryInformation implements Serializable
{
	private Long nodeId;
	private Calendar lastUpdateDate;
	private DictionaryState state;
	private String errorDetail;

	/**
	 * @return ������������� �����
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ������ ������������� �����
	 * @param nodeId ������������� �����
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return ���� ���������� ����������
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * ������ ���� ���������� ����������
	 * @param lastUpdateDate ���� ���������� ����������
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return ������ �����������
	 */
	public DictionaryState getState()
	{
		return state;
	}

	/**
	 * ������ ������ �����������
	 * @param state ������ �����������
	 */
	public void setState(DictionaryState state)
	{
		this.state = state;
	}

	/**
	 * @return ��������� �������� ������
	 */
	public String getErrorDetail()
	{
		return errorDetail;
	}

	/**
	 * ������ ��������� �������� ������
	 * @param errorDetail ��������� �������� ������
	 */
	public void setErrorDetail(String errorDetail)
	{
		this.errorDetail = errorDetail;
	}
}
