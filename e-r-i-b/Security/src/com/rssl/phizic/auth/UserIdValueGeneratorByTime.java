package com.rssl.phizic.auth;

import java.util.Date;

/**
 * @author Kosyakov
 * @ created 13.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2121 $
 */
public class UserIdValueGeneratorByTime implements UserIdValueGenerator
{
	public String newUserId ( int length )
	{
		String userId=String.valueOf(new Date().getTime());
		return length<userId.length() ? userId.substring(userId.length()-length) : userId;
	}
}
