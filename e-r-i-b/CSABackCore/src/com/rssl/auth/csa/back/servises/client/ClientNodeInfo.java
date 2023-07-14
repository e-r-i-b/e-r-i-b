package com.rssl.auth.csa.back.servises.client;

/**
 * @author akrenev
 * @ created 16.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ����� �������
 */

@SuppressWarnings("PublicInnerClass")
public class ClientNodeInfo
{
	private Long id;
	private Type type;
	private State state;

	/**
	 * @return ������������� �����
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� �����
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� �����
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * ������ ��� �����
	 * @param type ���
	 */
	public void setType(Type type)
	{
		this.type = type;
	}

	/**
	 * @return ��������� �����
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * ������ ��������� �����
	 * @param state ���������
	 */
	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * ��� �����
	 */
	public enum Type
	{
		MAIN,              //��������
		TEMPORARY          //���������
	}

	/**
	 * ��������� �����
	 */
	public enum State
	{
		WAIT_MIGRATION,    //��������� �������� ������ �� ����� �����
		PROCESS_MIGRATION, //���� ��������, ���������� ������� ������ �� ����� ����� � ������
		ACTIVE             //������ ������ � ������, ������� �������� ��������
	}
}
