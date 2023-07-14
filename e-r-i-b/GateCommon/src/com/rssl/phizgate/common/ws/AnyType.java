package com.rssl.phizgate.common.ws;

import java.io.Serializable;

/**
 * @author Erkin
* @ created 22.03.2012
* @ $Author$
* @ $Revision$
*/

/**
 * Объект для корректной сериализации xsd:anyType
 */
public class AnyType implements Serializable
{
	private String value;

	public AnyType()
	{
	}

	public AnyType(String value)
	{
		this.value = value;
	}

	public String toString()
	{
		return value;
	}
}
