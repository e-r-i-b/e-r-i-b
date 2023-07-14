package com.rssl.phizic.armour;

/**
 * @author Krenev
 * @ created 06.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class UserAmountExceedException extends ArmourException
{
	public UserAmountExceedException()
	{
		this("Количество пользователей превышает количество лицензий");
	}

	public UserAmountExceedException(Exception cause)
	{
		super(cause);
	}

	public UserAmountExceedException(String message)
	{
		super(message);
	}
}
