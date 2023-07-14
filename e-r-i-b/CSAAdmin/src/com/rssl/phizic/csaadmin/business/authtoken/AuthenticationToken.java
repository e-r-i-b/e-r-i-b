package com.rssl.phizic.csaadmin.business.authtoken;

import com.rssl.phizic.csaadmin.business.session.Session;
import com.rssl.phizic.utils.MapUtil;
import org.apache.commons.collections.MapUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 27.10.13
 * @ $Author$
 * @ $Revision$
 * ����� �������������� ����������
 */
public class AuthenticationToken
{
	private static final String PARAMETERS_ENTRY_DELIMETER = "&";
	private static final String PAREMETERS_VALUE_DELIMETER = "=";

	private String id; //�������������
	private Session session; //������ ����������
	private AuthenticationTokenState state; //������ ������
	private Calendar creationDate; //���� �������� ������
	private String action; //����� �������� � �����
	private String parameters; //��������� �������� � �����

	/**
	 * ������ �����������, ��� ���������
	 */
	public AuthenticationToken()
	{
	}

	/**
	 * ����������� �� ������ ����������
	 * @param session - ������ ����������
	 */
	public AuthenticationToken(Session session, String action, Map<String,String> parameters)
	{
		this.session = session;
		this.state = AuthenticationTokenState.ACTIVE;
		this.creationDate = Calendar.getInstance();
		this.action = action;
		if(MapUtils.isNotEmpty(parameters))
		{
			this.parameters = MapUtil.format(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
		}
	}

	/**
	 * @return ������������� ������
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * ���������� ������������� ������
	 * @param id - �������������
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return ������ ����������
	 */
	public Session getSession()
	{
		return session;
	}

	/**
	 * ���������� ������ ����������
	 * @param session - ������
	 */
	public void setSession(Session session)
	{
		this.session = session;
	}

	/**
	 * @return ������ ������
	 */
	public AuthenticationTokenState getState()
	{
		return state;
	}

	/**
	 * ���������� ������ ������
	 * @param state - ������
	 */
	public void setState(AuthenticationTokenState state)
	{
		this.state = state;
	}

	/**
	 * @return ���� �������� ������
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * ���������� ���� �������� ������
	 * @param creationDate - ���� �������� ������
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return ����� �������� � �����
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * ���������� ����� �������� � �����
	 * @param action - �����
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return ��������� ������ �������� � �����
	 */
	public Map<String,String> getParameters()
	{
		return MapUtil.parse(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
	}

	/**
	 * ���������� ��������� ������
	 * @param parameters - ���������
	 */
	public void setParameters(Map<String,String> parameters)
	{
		if(MapUtils.isNotEmpty(parameters))
		{
			this.parameters = MapUtil.format(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
		}
	}
}
