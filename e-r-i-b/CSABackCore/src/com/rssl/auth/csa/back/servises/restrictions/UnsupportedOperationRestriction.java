package com.rssl.auth.csa.back.servises.restrictions;

/**
 * @author krenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 * Ѕ≈зусловное ограни€ение отказ
 */
public class UnsupportedOperationRestriction<T> implements Restriction<T>
{
	private String message;

	public UnsupportedOperationRestriction(String message)
	{
		this.message = message;
	}

	public void check(T object) throws Exception
	{
		throw new UnsupportedOperationException(message);
	}
}
