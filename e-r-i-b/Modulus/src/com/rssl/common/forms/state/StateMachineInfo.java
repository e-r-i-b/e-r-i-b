package com.rssl.common.forms.state;

/**
 * Инфирмация о машине состояний
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
	 * @return название машины состояний
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return тип машины состояний
	 */
	public Type getType()
	{
		return type;
	}
}
