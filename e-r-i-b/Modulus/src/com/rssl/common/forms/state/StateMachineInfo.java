package com.rssl.common.forms.state;

/**
 * ���������� � ������ ���������
 *
 * @author khudyakov
 * @ created 31.07.14
 * @ $Author$
 * @ $Revision$
 */
public class StateMachineInfo
{
	private String name;
	private Type type;

	public StateMachineInfo(String name, Type type)
	{
		this.name = name;
		this.type = type;
	}

	/**
	 * @return �������� ������ ���������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ��� ������ ���������
	 */
	public Type getType()
	{
		return type;
	}
}
