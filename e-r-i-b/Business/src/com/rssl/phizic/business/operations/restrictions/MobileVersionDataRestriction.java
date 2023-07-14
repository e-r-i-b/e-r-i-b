package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.login.exceptions.MobileVersionException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.Map;

/**
 * Ограничение на входящие данные mAPI
 *
 * @author khudyakov
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileVersionDataRestriction implements MobileDataRestriction
{
	/**
	 * Ограничение версии mAPI
	 * @param data версия
	 * @return true - нет ограничения
	 */
	public boolean accept(Map<String, Object> data) throws BusinessException, RegistrationErrorException
	{
		try
		{
			MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);

			String version = (String) data.get("version");
			if (StringHelper.isEmpty(version))
				throw new MobileVersionException(mobileApiConfig.getInvalidAPIVersionText());

			VersionNumber versionNumber = VersionNumber.fromString(version);
			// жесткая деактивация старых мобильных версий
			if (versionNumber.lt(MobileAPIVersions.V5_00))
				throw new MobileVersionException(mobileApiConfig.getInvalidAPIVersionText());

			if (!mobileApiConfig.getApiVersions().contains(versionNumber))
				throw new MobileVersionException(mobileApiConfig.getInvalidAPIVersionText());
		}
		catch (MalformedVersionFormatException e)
		{
			throw new BusinessException(e);
		}

		return true;
	}
}
