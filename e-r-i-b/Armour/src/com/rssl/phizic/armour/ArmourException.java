package com.rssl.phizic.armour;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArmourException extends RuntimeException
{
	public ArmourException(String message)
	{
		super(message);
	}

	public ArmourException(Exception cause)
	{
		this("Лицензионная защита не найдена :"+cause.getMessage());
	}
}
