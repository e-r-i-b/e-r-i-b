package com.rssl.phizic.dataaccess.query;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 30.07.2011
 * @ $Author$
 * @ $Revision$
 */
//параметр сортировки данных в SQL запросе
public class OrderParameter implements Serializable
{
	private String value; // значение параметра
	private OrderDirection direction; // направление сортировки

	public OrderParameter(String value)
	{
		this(value, OrderDirection.ASC);
	}

	public OrderParameter(String value, OrderDirection direction)
	{
		this.value = value;
		this.direction = direction;
	}

	public String getValue()
	{
		return value;
	}

	public OrderDirection getDirection()
	{
		return direction;
	}

	public String toString()
	{
		return value + " " + direction;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OrderParameter parameter = (OrderParameter) o;

		if (direction != parameter.direction)
			return false;
		if (value != null ? !value.equals(parameter.value) : parameter.value != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		int result = value != null ? value.hashCode() : 0;
		result = 31 * result + (direction != null ? direction.hashCode() : 0);
		return result;
	}
}
