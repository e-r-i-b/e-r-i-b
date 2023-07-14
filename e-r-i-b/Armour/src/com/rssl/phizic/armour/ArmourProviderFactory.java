package com.rssl.phizic.armour;

import com.rssl.api.armour.LicenseArmourProvider;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.libraries.LibraryLoader;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArmourProviderFactory implements Serializable
{
	private static final String ARMOUR_JNI = "armour_jni";
	private ArmourProvider provider;

	public ArmourProvider newArmourProvider()
	{
		if (provider == null)
		{
			provider = createProvider();
		}
		return provider;
	}

	private ArmourProvider createProvider()
	{
		try
		{
			File dir = deployJni();
			LicenseArmourProvider.Factory.setJabberLevel(LicenseArmourProvider.JABBER_LEVEL.DEBUG);
			LicenseArmourProvider.Factory.setNativeLibraryPath(dir.getCanonicalPath());
			LicenseArmourProvider licenseArmourProvider = LicenseArmourProvider.Factory.getInstance();
			return new ArmourProviderImpl(licenseArmourProvider);
		}
		catch (LicenseArmourProvider.Failure failure)
		{
			throw new ArmourException(failure);
		}
		catch (IOException e)
		{
			throw new ArmourException(e);
		}
	}

	private File deployJni()
	{
		ApplicationConfig appConfig = ApplicationConfig.getIt();
		String platformProperty=ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getPlatformType();
		if (platformProperty.equals("i64"))
			LicenseArmourProvider.Factory.setPlatform(LicenseArmourProvider.NATIVE_PLATFORM.WINDOWS_I64);
		else if (platformProperty.equals("x64"))
				LicenseArmourProvider.Factory.setPlatform(LicenseArmourProvider.NATIVE_PLATFORM.WINDOWS_X64);
			else
				LicenseArmourProvider.Factory.setPlatform(LicenseArmourProvider.NATIVE_PLATFORM.WINDOWS_X86);

		File result = LibraryLoader.saveResourceToTmp(LicenseArmourProvider.Factory.mapLibraryName(), appConfig.getApplicationPrefixAdoptedToFileName() + File.separator + ARMOUR_JNI);
		return result;
	}

	public void release()
	{
		if (provider != null)
		{
			provider.release();
		}
	}
}
