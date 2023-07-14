package com.rssl.phizic.web.common.messages;

import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.config.locale.MultiLocaleContext;

/**
 * @author mihaylov
 * @ created 22.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Роутер отвечающий за использование корректного MessageConfig-а.
 */
public class MessageConfigRouter implements MessageConfig
{
	private static final MessageConfigRouter INSTANCE = new MessageConfigRouter();

	private MessageConfig strutsMessageConfig = new StrutsMessageConfig();
	private MessageConfig multiLocaleMessageConfig = MultiLocaleMessageConfig.getInstance();

	private MessageConfigRouter() {}

	/**
	 * @return инстанс
	 */
	public static MessageConfigRouter getInstance()
	{
		return INSTANCE;
	}

	public String message(String bundle, String key)
	{
		return message(bundle, key, null);
	}

	public String message(String bundle, String key, Object[] args)
	{
		ERIBLocaleConfig config = ConfigFactory.getConfig(ERIBLocaleConfig.class);
		if(config.isUseERIBMessagesMode(ApplicationInfo.getCurrentApplication(), MultiLocaleContext.getLocaleId()))
			return multiLocaleMessageConfig.message(bundle, key, args);
		return strutsMessageConfig.message(bundle, key, args);
	}
}
