package com.rssl.phizic.security.crypto;

/**
 * @author Evgrafov
 * @ created 27.12.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3204 $
 */

@SuppressWarnings({"JavaDoc"})
public class CryptographyException extends SecurityException
{
	public CryptographyException(Throwable cause)
	{
		super(cause);
	}

	public CryptographyException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CryptographyException(String s)
	{
		super(s);
	}
}