package com.rssl.common.forms;

/**
 * Достугнут лимит на подбор номеров телефонов.
 *
 * @author bogdanov
 * @ created 18.11.14
 * @ $Author$
 * @ $Revision$
 */

public class PhoneLimitExceededException extends DocumentLogicException
{
	public PhoneLimitExceededException(Throwable cause)
	{
		super(cause);
	}

	public PhoneLimitExceededException(String message)
	{
		super(message);
	}

	public PhoneLimitExceededException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
