package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * Проверка доступности регистрации
 * @author Dorzhinov
 * @ created 29.08.13
 * @ $Author$
 * @ $Revision$
 */
public class MobileRegistrationRestriction implements MobileDataRestriction
{
	public boolean accept(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		//на refreshCaptcha параметров нет
		if (MapUtils.isEmpty(data))
			return true;

		String version = (String) data.get("version");
		VersionNumber versionNumber;
		try
		{
			versionNumber = VersionNumber.fromString(version);
		}
		catch (MalformedVersionFormatException e)
		{
			throw new BusinessLogicException("Некорректная версия " + version, e);
		}

		MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
		if (!mobileApiConfig.isRegistrationAvailable(versionNumber))
			throw new BusinessLogicException(mobileApiConfig.getRegistrationNotAvailableText(versionNumber));

		return true;
	}
}
