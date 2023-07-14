package com.rssl.phizic.web.common.messages;

import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.locale.ERIBMessageManager;
import com.rssl.phizic.utils.StringHelper;

import java.text.Format;
import java.text.MessageFormat;

/**
 * @author mihaylov
 * @ created 22.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiLocaleMessageConfig implements MessageConfig
{
	private static final MultiLocaleMessageConfig INSTANCE = new MultiLocaleMessageConfig();

	/**
	 * @return инстанс
	 */
	public static MultiLocaleMessageConfig getInstance()
	{
		return INSTANCE;
	}

	private MultiLocaleMessageConfig(){}

	public String message(String bundle, String key)
	{
		return message(bundle, key, null);
	}

	public String message(String bundle, String key, Object args[])
	{
		String message = ERIBMessageManager.getMessage(key, bundle);
		if(StringHelper.isEmpty(message))
			return message;
		Format format = new MessageFormat(message);
		return format.format(args);
	}
}
