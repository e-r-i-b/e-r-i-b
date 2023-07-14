package com.rssl.phizic.csaadmin.business.employee;

import com.rssl.phizic.dataaccess.query.QueryParameter;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������
 */

public class ContactCenterEmployeeFilterParameters
{
	private int firstResult;
	private int maxResults;

	private Calendar soughtBlockedUntil;
	private Long soughtId;
	private String soughtFIO;
	private String soughtArea;

	/**
	 * @return ������ ������ ������
	 */
	public int getFirstResult()
	{
		return firstResult;
	}

	/**
	 * ������ ������ ������ ������
	 * @param firstResult ������ ������ ������
	 */
	public void setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
	}

	/**
	 * @return ���������� �������
	 */
	public int getMaxResults()
	{
		return maxResults;
	}

	/**
	 * ������ ���������� �������
	 * @param maxResults ���������� �������
	 */
	public void setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
	}

	/**
	 * @return ���� ��������� ���������� ������� �����������
	 */
	@QueryParameter
	public Calendar getSoughtBlockedUntil()
	{
		return soughtBlockedUntil;
	}

	/**
	 * ������ ���� ��������� ���������� ������� �����������
	 * @param soughtBlockedUntil ���� ��������� ���������� ������� �����������
	 */
	public void setSoughtBlockedUntil(Calendar soughtBlockedUntil)
	{
		this.soughtBlockedUntil = soughtBlockedUntil;
	}

	/**
	 * @return ������������� �������� ����������
	 */
	@QueryParameter
	public Long getSoughtId()
	{
		return soughtId;
	}

	/**
	 * ������ ������������� �������� ����������
	 * @param soughtId ������������� �������� ����������
	 */
	public void setSoughtId(Long soughtId)
	{
		this.soughtId = soughtId;
	}

	/**
	 * @return ��� �������� ����������
	 */
	@QueryParameter
	public String getSoughtFIO()
	{
		return soughtFIO;
	}

	/**
	 * ������ ��� �������� ����������
	 * @param soughtFIO ��� �������� ����������
	 */
	public void setSoughtFIO(String soughtFIO)
	{
		this.soughtFIO = soughtFIO;
	}

	/**
	 * @return �������� �������� ����������
	 */
	@QueryParameter
	public String getSoughtArea()
	{
		return soughtArea;
	}

	/**
	 * ������ �������� �������� ����������
	 * @param soughtArea ��������
	 */
	public void setSoughtArea(String soughtArea)
	{
		this.soughtArea = soughtArea;
	}
}
