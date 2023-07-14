package com.rssl.phizicgate.csaadmin.service.types;

import java.util.Calendar;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������������� � ��� �����
 */
public class AuthenticationParameters
{
	private String loginId;//������������� ������ ������������ � ��� �����
	private Calendar lastEmployeeUpdateDate;//���� ���������� ���������� ���������� �� ����������
	private String sessionId; //������������� ������ � ��� �����
	private String action; //����� ��������
	private Map<String,String> parameters;//��������� � ������ ��������

	/**
	 * @return ������������� ������ ������������
	 */
	public String getLoginId()
	{
		return loginId;
	}

	/**
	 * ���������� ������������� ������ ������������
	 * @param loginId - ������������� ������ ������������
	 */
	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ���� ���������� ���������� ���������� �� ����������
	 */
	public Calendar getLastEmployeeUpdateDate()
	{
		return lastEmployeeUpdateDate;
	}

	/**
	 * ���������� ���� ���������� ���������� ����������
	 * @param lastEmployeeUpdateDate ���� ���������� ���������� ���������� �� ����������
	 */
	public void setLastEmployeeUpdateDate(Calendar lastEmployeeUpdateDate)
	{
		this.lastEmployeeUpdateDate = lastEmployeeUpdateDate;
	}

	/**
	 * @return ������������� ������ � ��� �����
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * ���������� ������������� ������ � ��� �����
	 * @param sessionId - ������������� ������
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * @return ����� ��������
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * ���������� ����� ��������
	 * @param action - �����
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return ��������� ������ ��������
	 */
	public Map<String, String> getParameters()
	{
		return parameters;
	}

	/**
	 * ���������� ��������� ������ ��������
	 * @param parameters - ���������
	 */
	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}
}
