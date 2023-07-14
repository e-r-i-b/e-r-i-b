package com.rssl.phizic.web.struts;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.util.MessageResourcesFactory;

import java.util.Locale;

/**
 * @author Mescheryakova
 * @ created 07.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Ресурсы сообщений для мобильного API
 * Сначала сообщение с ключом ищется в *properties для версии приложения по ключу + .название приложения (например, mobile3)
 * Если ничего не найдено, ищется сообщение по ключу + .mobile
 * Если ничего не найдено ищется просто по ключу
 *
 */

public class MobileMultiPropertyMessageResources  extends  MultiPropertyMessageResources
{
	private static String KEY_SEPARATOR = ".";
	private static String MOBILE = "mobile";

	public MobileMultiPropertyMessageResources(MessageResourcesFactory factory, String config)
	{
		super(factory, config);
	}

	public MobileMultiPropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull)
	{
		super(factory, config, returnNull);
	}

	 public String getMessage(Locale locale, String key, Object args[])
	 {
		 Application application = LogThreadContext.getApplication();
		 String message = super.getMessage(locale, key + KEY_SEPARATOR + application.name(), args);

		 if (StringHelper.isEmpty(message))
		    message =  super.getMessage(locale,  key + KEY_SEPARATOR + MOBILE,  args);

		 if (StringHelper.isEmpty(message))
			message =  super.getMessage(locale, key, args);

		 return message;
	 }
}
