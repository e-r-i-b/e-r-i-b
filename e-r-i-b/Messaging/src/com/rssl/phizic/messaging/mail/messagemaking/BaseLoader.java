package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import freemarker.cache.TemplateLoader;

/**
 * базовый загрузчик шаблонов сообщений
 * @author basharin
 * @ created 20.11.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class BaseLoader implements TemplateLoader
{
	protected ChannelType getChannelType()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();

		if (applicationInfo.isATM())
		{
			return ChannelType.SELF_SERVICE_DEVICE;
		}

		if (applicationInfo.isMobileApi())
		{
			return ChannelType.MOBILE_API;
		}

		if (applicationInfo.isSMS())
		{
			return ChannelType.ERMB_SMS;
		}

        if (applicationInfo.isSocialApi())
        {
            return ChannelType.SOCIAL_API;
        }

		return ChannelType.INTERNET_CLIENT;
	}
}
