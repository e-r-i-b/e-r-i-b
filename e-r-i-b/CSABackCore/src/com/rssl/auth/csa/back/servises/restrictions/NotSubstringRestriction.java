package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на то, что строка не является подстрокой заданного шаблона template.
 */
public class NotSubstringRestriction implements Restriction<String>
{
	private String template;
	private String message;

	public NotSubstringRestriction(String template, String message)
	{
		this.template = template;
		this.message = message;
	}

	public void check(String object) throws Exception
	{
		if (template.contains(object))
		{
			throw new RestrictionException(message);
		}
	}
}
