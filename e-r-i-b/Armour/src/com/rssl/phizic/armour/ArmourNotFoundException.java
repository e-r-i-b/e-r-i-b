package com.rssl.phizic.armour;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArmourNotFoundException extends ArmourException
{
	public ArmourNotFoundException(Exception cause)
	{
		super(cause);
	}

	public ArmourNotFoundException()
	{
		this("�������� �� �������");
	}

	public ArmourNotFoundException(String message)
	{
		super(message);
	}
}
