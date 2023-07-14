package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 * ќграничение на несовпадение заданному значению
 */
public class NotEquilsRestriction<T> implements Restriction<T>
{
	private T template;
	private String message;

	public NotEquilsRestriction(T template, String message)
	{
		this.template = template;
		this.message = message;
	}

	public void check(T object) throws Exception
	{
		if ((template == null && object == null) || (template != null && template.equals(object)))
		{
			throw new RestrictionException(message);
		}
	}
}
