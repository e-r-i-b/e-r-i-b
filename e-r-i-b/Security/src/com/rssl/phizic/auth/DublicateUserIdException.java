package com.rssl.phizic.auth;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Evgrafov
 * @ created 12.09.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2121 $
 */

public class DublicateUserIdException extends SecurityLogicException
{
	private String userId;
	private String kind;

	public DublicateUserIdException(String userId, String kind)
	{
		this(userId, kind, null);
	}
	/**
	 * @param userId USER ID - дубликат
	 * @param kind тип (admin, client etc)
	 */
	public DublicateUserIdException(String userId, String kind, Exception cause)
	{
		super("User ID " + userId + " уже существует", cause);
		this.userId = userId;
		this.kind = kind;
	}

	/**
	 * @return тип логина (admin, client etc)
	 */
	public String getKind()
	{
		return kind;
	}

	/**
	 * @return USER ID - дубликат
	 */
	public String getUserId()
	{
		return userId;
	}
}