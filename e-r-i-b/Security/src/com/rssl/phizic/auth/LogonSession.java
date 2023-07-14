package com.rssl.phizic.auth;

import com.rssl.phizic.common.types.ApplicationType;

import java.io.Serializable;

/**
 * @author krenev
 * @ created 31.10.2013
 * @ $Author$
 * @ $Revision$
 * ������, �������� � ��������� ����������� ������ � � ������� ���� ����������.
 */

public class LogonSession implements Serializable
{
	private Long loginId;
	private ApplicationType applicationType;
	private String sessionId;

	/**
	 * ��������� �����������
	 */
	public LogonSession()
	{
	}

	/**
	 * �����������
	 * @param loginId ������������ ������
	 * @param applicationType ��� ����������
	 * @param sessionId ������������ ������
	 */
	public LogonSession(Long loginId, ApplicationType applicationType, String sessionId)
	{
		this.loginId = loginId;
		this.applicationType = applicationType;
		this.sessionId = sessionId;
	}

	/**
	 * @return ������������ ������
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * ���������� ������������� ������
	 * @param sessionId ������������ ������
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * @return ������������ ������
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * ���������� ������������ ������
	 * @param loginId ������������ ������
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ��� ����������
	 */
	public ApplicationType getApplicationType()
	{
		return applicationType;
	}

	/**
	 * ���������� ��� ����������
	 * @param applicationType ��� ����������
	 */
	public void setApplicationType(ApplicationType applicationType)
	{
		this.applicationType = applicationType;
	}
}
