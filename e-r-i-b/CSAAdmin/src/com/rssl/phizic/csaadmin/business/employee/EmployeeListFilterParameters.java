package com.rssl.phizic.csaadmin.business.employee;

import com.rssl.phizic.dataaccess.query.QueryParameter;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������
 */

public class EmployeeListFilterParameters
{
	private int firstResult;
	private int maxResults;
	private Long seekerLoginId;
	private boolean seekerAllDepartments;
	private Long soughtId;
	private String soughtLogin;
	private String soughtFIO;
	private long soughtBlockedState;
	private Calendar soughtBlockedUntil;
	private String soughtInfo;
	private String soughtTB;
	private String soughtBranchCode;
	private String soughtDepartmentCode;

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
	 * @return ����� ������� ����������
	 */
	@QueryParameter
	public Long getSeekerLoginId()
	{
		return seekerLoginId;
	}

	/**
	 * ������ ����� ������� ����������
	 * @param seekerLoginId ����� ������� ����������
	 */
	public void setSeekerLoginId(Long seekerLoginId)
	{
		this.seekerLoginId = seekerLoginId;
	}

	/**
	 * @return ������� ����������� ������ �� ���� ��������������
	 */
	@QueryParameter
	public boolean isSeekerAllDepartments()
	{
		return seekerAllDepartments;
	}

	/**
	 * ������ ������� ����������� ������ �� ���� ��������������
	 * @param seekerAllDepartments ������� ����������� ������ �� ���� ��������������
	 */
	public void setSeekerAllDepartments(boolean seekerAllDepartments)
	{
		this.seekerAllDepartments = seekerAllDepartments;
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
	 * @return ����� �������� ����������
	 */
	@QueryParameter
	public String getSoughtLogin()
	{
		return soughtLogin;
	}

	/**
	 * ������ ����� �������� ����������
	 * @param soughtLogin ����� �������� ����������
	 */
	public void setSoughtLogin(String soughtLogin)
	{
		this.soughtLogin = soughtLogin;
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
	 * @return ������� ����������������� ������� �����������
	 */
	@QueryParameter
	public long getSoughtBlockedState()
	{
		return soughtBlockedState;
	}

	/**
	 * ������ ������� ����������������� ������� �����������
	 * @param soughtBlockedState ������� ����������������� ������� �����������
	 */
	public void setSoughtBlockedState(long soughtBlockedState)
	{
		this.soughtBlockedState = soughtBlockedState;
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
	 * @return ���. ���������� ������� �����������
	 */
	@QueryParameter
	public String getSoughtInfo()
	{
		return soughtInfo;
	}

	/**
	 * ������ ���. ���������� ������� �����������
	 * @param soughtInfo ���. ���������� ������� �����������
	 */
	public void setSoughtInfo(String soughtInfo)
	{
		this.soughtInfo = soughtInfo;
	}

	/**
	 * @return ��� �������� ������� �����������
	 */
	@QueryParameter
	public String getSoughtTB()
	{
		return soughtTB;
	}

	/**
	 * ������ ��� �������� ������� �����������
	 * @param soughtTB ��� ��������
	 */
	public void setSoughtTB(String soughtTB)
	{
		this.soughtTB = soughtTB;
	}

	/**
	 * @return ��� ��� ������� �����������
	 */
	@QueryParameter
	public String getSoughtBranchCode()
	{
		return soughtBranchCode;
	}

	/**
	 * ������ ��� ��� ������� �����������
	 * @param soughtBranchCode ��� ���
	 */
	public void setSoughtBranchCode(String soughtBranchCode)
	{
		this.soughtBranchCode = soughtBranchCode;
	}

	/**
	 * @return ��� ��� ������� �����������
	 */
	@QueryParameter
	public String getSoughtDepartmentCode()
	{
		return soughtDepartmentCode;
	}

	/**
	 * ������ ��� ��� ������� �����������
	 * @param soughtDepartmentCode ��� ���
	 */
	public void setSoughtDepartmentCode(String soughtDepartmentCode)
	{
		this.soughtDepartmentCode = soughtDepartmentCode;
	}
}
