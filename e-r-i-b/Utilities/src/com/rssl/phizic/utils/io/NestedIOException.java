package com.rssl.phizic.utils.io;

import java.io.IOException;

/**
 * @author Evgrafov
* @ created 03.05.2007
* @ $Author: Evgrafov $
* @ $Revision: 4093 $
*/
public class NestedIOException extends IOException
{
	/**
	 * @param t cause
	 */
	public NestedIOException(Throwable t)
	{
		super(t.getMessage());
		initCause(t);
	}
}