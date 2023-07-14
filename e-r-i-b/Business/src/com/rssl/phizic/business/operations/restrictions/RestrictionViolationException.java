package com.rssl.phizic.business.operations.restrictions;

import java.security.AccessControlException;

/**
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class RestrictionViolationException extends AccessControlException
{
	/**
	 * Constructs an <code>AccessControlException</code> with the specified, detailed message.
	 *
	 * @param s the detail message.
	 */
	public RestrictionViolationException(String s)
	{
		super(s);
	}
}