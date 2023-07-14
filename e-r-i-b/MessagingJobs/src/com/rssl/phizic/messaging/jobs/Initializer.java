package com.rssl.phizic.messaging.jobs;

/**
 * @author Evgrafov
 * @ created 22.06.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

abstract class Initializer
{
	private static boolean initialized = false;

	public static synchronized void initialize()
	{
		if(initialized)
			return;

		initialized = true;
	}
}
