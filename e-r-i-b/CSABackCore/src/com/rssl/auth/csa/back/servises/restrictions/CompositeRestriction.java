package com.rssl.auth.csa.back.servises.restrictions;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CompositeRestriction<T> implements Restriction<T>
{
	private Restriction[] restrictions;

	public CompositeRestriction(Restriction<T> ... restrictions)
	{
		this.restrictions = restrictions;
	}

	public void check(T object) throws Exception
	{
		if (object == null)
		{
			throw new IllegalArgumentException("Объект на может быть null");
		}
		for (Restriction restriction : restrictions)
		{
			restriction.check(object);
		}
	}
}
