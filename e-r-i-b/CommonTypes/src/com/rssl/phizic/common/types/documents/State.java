package com.rssl.phizic.common.types.documents;

import java.io.Serializable;

/**
 * Состояние документа
 * реализация должна быть immutable
 * @author Evgrafov
 * @ created 28.11.2006
 * @ $Author: erkin $
 * @ $Revision: 58385 $
 */
public class State implements Serializable
{
	private String code;
	private String description;

	public State(){}	

	/**
	 * ctor
	 * @param code уточняющий код
	 */

	public State(String code)
	{
		this.code = code;
	}
	/**
	 * ctor
	 * @param code уточняющий код
	 * @param description описание
	 */
	public State(String code, String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * @return код состояния (каждая категория сотояния может уточнятся кодом)
	 */
	public String getCode()
	{
		return code;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		State state = (State) o;

		return code.equals(state.code);
	}

	public int hashCode()
	{
		return code.hashCode();
	}

	//todo добавлено только для работы веб-сервисов
	@Deprecated
	public void setCode(String code)
	{
		this.code = code;
	}

	//todo добавлено только для работы веб-сервисов
	@Deprecated
	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return code;
	}
}
