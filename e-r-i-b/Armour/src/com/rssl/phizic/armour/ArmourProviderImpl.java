package com.rssl.phizic.armour;

import com.rssl.api.armour.LicenseArmourProvider;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArmourProviderImpl implements ArmourProvider
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private LicenseArmourProvider plugin;

	public ArmourProviderImpl(LicenseArmourProvider provider)
	{
		this.plugin = provider;
	}

	public long getUsersAmount() throws ArmourException
	{		
		checkLicense();
		try
		{
			return plugin.getUsersAmount();
		}
		catch (LicenseArmourProvider.Failure failure)
		{
			throw new ArmourException(failure);
		}
	}

	private void checkLicense() throws ArmourException
	{
		if (!isLicenseExist())
		{
			throw new ArmourNotFoundException();
		}
	}

	public boolean isUserAmountNotExceed(long currentAmount) throws ArmourException
	{
		checkLicense();
		try
		{
			return plugin.isUserAmountNotExceed(currentAmount);
		}
		catch (LicenseArmourProvider.Failure failure)
		{
			throw new ArmourException(failure);
		}
	}

	public boolean isLicenseExist() throws ArmourException
	{
		try
		{
			return plugin.isLicenseExist();
		}
		catch (LicenseArmourProvider.Failure failure)
		{
			throw new ArmourException(failure);
		}
		finally
		{
			writeToLog();
		}
	}

	public synchronized void release()
	{
		plugin.release();
		plugin = null;
	}

	private void writeToLog() throws ArmourException
	{
		try
		{
			if (!plugin.isDemo())
			{
				return;
			}
			String error = plugin.getError();
			log.warn("Система работает в демо-режиме:" + error);
		}
		catch (LicenseArmourProvider.Failure failure)
		{
			throw new ArmourException(failure);
		}
	}
}
