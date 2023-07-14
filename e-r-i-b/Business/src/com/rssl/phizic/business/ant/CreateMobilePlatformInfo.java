package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.config.mobile.MobilePlatformInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * Заполнение справочника мобильных платформ
 * @author Jatsky
 * @ created 08.08.13
 * @ $Author$
 * @ $Revision$
 */

public class CreateMobilePlatformInfo extends SafeTaskBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private SimpleService simpleService = new SimpleService();

	@Override public void safeExecute() throws Exception
	{
		List<MobilePlatformInfo> mobilePlatfomInfos = ConfigFactory.getConfig(MobileApiConfig.class).getPlatforms();

		for (MobilePlatformInfo mobilePlatformInfo : mobilePlatfomInfos)
		{
			MobilePlatform platform = new MobilePlatform();
			platform.setPlatformId(mobilePlatformInfo.getPlatformId());
			platform.setPlatformName(mobilePlatformInfo.getName());
			platform.setVersion(mobilePlatformInfo.getVersion() == null ? null : Long.valueOf(mobilePlatformInfo.getVersion()));
			platform.setErrorText(mobilePlatformInfo.getErrText());
			platform.setDownloadFromSBRF(true);
			platform.setBankURL(mobilePlatformInfo.getBankURL());
			platform.setExternalURL(mobilePlatformInfo.getExternalURL());
			platform.setUseQR(true);
			platform.setQrName(mobilePlatformInfo.getQrCode());
			platform.setUseCaptcha(mobilePlatformInfo.getUseCaptcha());
			platform.setSocial(mobilePlatformInfo.getSocial());
			platform.setUnallowedBrowsers(mobilePlatformInfo.getUnallowedBrowsers());
			try
			{
				simpleService.addOrUpdateWithConstraintViolationException(platform);
			}
			catch (ConstraintViolationException ex)
			{
				log.warn("Платформа с platformID = " + platform.getPlatformId() + " уже существует");
			}
		}
	}
}
