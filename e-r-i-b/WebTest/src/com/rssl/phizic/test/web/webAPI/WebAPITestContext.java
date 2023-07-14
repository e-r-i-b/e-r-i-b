package com.rssl.phizic.test.web.webAPI;

/**
 * @author Pankin
 * @ created 31.03.14
 * @ $Author$
 * @ $Revision$
 */
public class WebAPITestContext
{
	private static ThreadLocal<String> session = new ThreadLocal<String>();

	public static String getSession()
	{
		return session.get();
	}

	public static void setSession(String sessionId)
	{
		session.set(sessionId);
	}
}
