package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.LogThreadContext;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 10.03.2006
 * @ $Author$
 * @ $Revision$
 * @noinspection ReturnOfCollectionOrArrayField
 */
public class LogEntry extends LogEntryBase
{
	private Long loginId;
	private String userId;
	//�����
	private String login;
	//��� ��
	private String departmentCode;
	//���
	private String osb;
	//���
	private String vsp;
	//���, ���� ������� � IP DataPower � �������: AAA�AAA;BBBB;CCC�CCC; - ���
	//AAA�AAA � BBBB � �������������� ��� � ���� �������, ����������� ��������� � ���;
	//��х��� � IP DataPower, ����� ������� �������� ������.
	private String addInfo;

	public LogEntry(LogDataReader reader, Calendar start, Calendar end)
	{
		fillBasePart(reader, start, end);
		setAddInfo(LogThreadContext.getAppServerInfo());
		setLoginId(LogThreadContext.getLoginId() == null ? 0L : LogThreadContext.getLoginId());
		setOsb(LogThreadContext.getDepartmentOSB());
		setVsp(LogThreadContext.getDepartmentVSP());
		setUserId(LogThreadContext.getUserId());
	}

	public LogEntry() {}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getDepartmentCode()
	{
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode)
	{
		this.departmentCode = departmentCode;
	}

	/**
	 * @return ���
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * @param osb ���
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return ���
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * @param vsp ���
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	/**
	 * @return ���, ���� ������� � IP DataPower, ����� ������� �������� ������
	 */
	public String getAddInfo()
	{
		return addInfo;
	}

	/**
	 * @param addInfo ���, ���� ������� � IP DataPower, ����� ������� �������� ������
	 */
	public void setAddInfo(String addInfo)
	{
		this.addInfo = addInfo;
	}
}