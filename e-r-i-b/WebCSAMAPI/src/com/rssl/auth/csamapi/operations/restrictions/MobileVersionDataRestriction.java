package com.rssl.auth.csamapi.operations.restrictions;

import com.rssl.auth.csamapi.exceptions.MobileVersionException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.Map;

/**
 * @author osminin
 * @ created 02.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ограничение по версии МАПИ
 */
public class MobileVersionDataRestriction implements MobileDataRestriction
{
	public boolean accept(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		try
		{
			MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
			String version = (String) data.get(Constants.VERSION_FIELD);

			if (StringHelper.isEmpty(version))
			{
				throw new MobileVersionException(mobileApiConfig.getInvalidAPIVersionText());
			}

			VersionNumber versionNumber = VersionNumber.fromString(version);
			// жесткая деактивация старых мобильных версий
			if (versionNumber.lt(MobileAPIVersions.V6_00))
				throw new MobileVersionException(mobileApiConfig.getInvalidAPIVersionText());

			if (!mobileApiConfig.getApiVersions().contains(versionNumber))
				throw new MobileVersionException(mobileApiConfig.getInvalidAPIVersionText());

			return true;
		}
		catch (MalformedVersionFormatException e)
		{
			throw new FrontException(e);
		}
	}
}
