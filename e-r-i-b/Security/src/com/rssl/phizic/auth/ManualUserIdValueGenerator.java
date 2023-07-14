package com.rssl.phizic.auth;

/**
 * @author Kosyakov
 * @ created 13.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2121 $
 */
public class ManualUserIdValueGenerator implements UserIdValueGenerator
{
	private String userId;

	public ManualUserIdValueGenerator ( String userId )
	{
		this.userId = userId;
	}

	public String newUserId ( int length )
	{
		return userId;
	}
}
