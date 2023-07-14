package com.rssl.phizic.ext.sbrf.armour;

import com.rssl.phizic.armour.ArmourProvider;
import com.rssl.phizic.armour.ArmourException;

/**
 * @author Omeliyanchuk
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ArmourProviderSBRF implements ArmourProvider
{
	public long getUsersAmount() throws ArmourException
	{
		return Long.MAX_VALUE;
	}

	public boolean isUserAmountNotExceed(long currentAmount) throws ArmourException
	{
		return true;
	}

	public boolean isLicenseExist() throws ArmourException
	{
		return true;
	}

	public void release()
	{
		//ничего не занимали, ничего не освобождаем
	}
}
