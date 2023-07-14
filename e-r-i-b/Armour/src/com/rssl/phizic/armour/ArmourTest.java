package com.rssl.phizic.armour;

import junit.framework.TestCase;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArmourTest extends TestCase
{
	public void testLoading() throws ArmourException
	{
		ArmourProviderFactory factory = new ArmourProviderFactory();
		ArmourProvider armourProvider = factory.newArmourProvider();
		assertNotNull(armourProvider);
		assertTrue(armourProvider.isLicenseExist());
	}
}
