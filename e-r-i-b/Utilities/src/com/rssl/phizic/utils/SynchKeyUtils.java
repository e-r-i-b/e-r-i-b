package com.rssl.phizic.utils;

/**
 * @author Erkin
 * @ created 18.03.2015
 * @ $Author$
 * @ $Revision$
 */
public final class SynchKeyUtils
{
	public static String makeGuestSynchKey(long guestCode)
	{
		return "guestOwner" + guestCode;
	}

	public static String makeClientSynchKey(long clientLoginID)
	{
		return "clientOwner" + clientLoginID;
	}

}
