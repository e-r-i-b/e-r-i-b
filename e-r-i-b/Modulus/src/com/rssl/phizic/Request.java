package com.rssl.phizic;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
public abstract class Request
{
	/**
	 * @return название запроса
	 */
	public abstract String getName();

	@Override
	public abstract String toString();
}
