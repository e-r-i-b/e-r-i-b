package com.rssl.phizic.ext.sbrf.armour;

import com.rssl.phizic.armour.ArmourProviderFactory;
import com.rssl.phizic.armour.ArmourProvider;

/**
 * @author Omeliyanchuk
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ArmourProviderFactorySBRF extends ArmourProviderFactory
{
	public ArmourProvider newArmourProvider()
	{
		return new ArmourProviderSBRF();
	}
}
