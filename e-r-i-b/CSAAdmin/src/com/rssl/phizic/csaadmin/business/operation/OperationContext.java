package com.rssl.phizic.csaadmin.business.operation;

import com.rssl.phizic.csaadmin.business.session.Session;

/**
 * @author mihaylov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 * �������� ��������
 */
public class OperationContext
{
	private Long id;            //������������� ���������
	private Session session;    //������ � ������ ������� ����������� ��������
	private String context;  //��������� ��� ��������, XML � ������� ������������� ��������� ��������
	private OperationContextState state; //������

	/**
	 * ������ �����������, ��� ���������
	 */
	public OperationContext()
	{
	}

	/**
	 * �����������
	 * @param session - ������ � ������ ������� ��������� ��������
	 */
	public OperationContext(Session session)
	{
		this.session = session;
		this.state = OperationContextState.ACTIVE;
	}

	/**
	 * @return ������������ ���������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� �������������
	 * @param id - �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������
	 */
	public Session getSession()
	{
		return session;
	}

	/**
	 * ���������� ������
	 * @param session - ������
	 */
	public void setSession(Session session)
	{
		this.session = session;
	}

	/**
	 * @return �������� ��������
	 */
	public String getContext()
	{
		return context;
	}

	/**
	 * ���������� �������� ��������
	 * @param context - ��������
	 */
	public void setContext(String context)
	{
		this.context = context;
	}

	/**
	 * @return ������ ���������
	 */
	public OperationContextState getState()
	{
		return state;
	}

	/**
	 * ���������� ������ ���������
	 * @param state - ������
	 */
	public void setState(OperationContextState state)
	{
		this.state = state;
	}
}
