package com.rssl.phizic.ext.sbrf.armour;

import com.rssl.phizic.armour.ArmourProviderService;
import com.rssl.phizic.armour.ArmourProviderFactory;

import javax.management.MBeanException;

/**
 * @author Omeliyanchuk
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ArmourProviderServiceSBRF extends ArmourProviderService
{
	/**
	 * default ctor
	 * @throws javax.management.MBeanException
	 */
	public ArmourProviderServiceSBRF() throws MBeanException
	{
		super();
	}

	protected ArmourProviderFactory createFactory()
	{
		return new ArmourProviderFactorySBRF();
	}
}
