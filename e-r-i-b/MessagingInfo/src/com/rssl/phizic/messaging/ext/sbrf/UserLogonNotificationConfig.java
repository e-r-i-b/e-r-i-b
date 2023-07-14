package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.Calendar;

/**
 * Настройки уведомлений о входе
 *
 * @author bogdanov
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */

public class UserLogonNotificationConfig extends Config
{
	public static final String DEFAULT_MESSAGE_TYPE_PROPERTY_KEY = "com.rssl.iccs.user.defaulttype.logon";
	public static final String TEMPLATE_PROPERTY_KEY_EMAIL = "com.rssl.iccs.user.logon.text.email";
	public static final String TEMPLATE_PROPERTY_KEY_PUSH_SMS = "com.rssl.iccs.user.logon.text";
	private static final String TEMPLATE_PROPERTY_KEY_ATM_SMS_TURN_ON = "com.rssl.iccs.user.logon.atm.sms.turn.on";
	private boolean atmSmsTurnOn;
	private String defaultMessagetype;

	public UserLogonNotificationConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		atmSmsTurnOn = getBoolProperty(TEMPLATE_PROPERTY_KEY_ATM_SMS_TURN_ON);
		defaultMessagetype = getProperty(DEFAULT_MESSAGE_TYPE_PROPERTY_KEY);
	}

	public boolean isAtmSmsTurnOn()
	{
		return atmSmsTurnOn;
	}

	public String getDefaultMessageType()
	{
		return defaultMessagetype;
	}

	private String getTemplateMessage(String type)
	{
		String template = new String(getProperty(type));
		String time = String.format("%1$tH:%1$tM %1$td.%1$tm.%1$ty", Calendar.getInstance());
		return String.format(template, time);
	}

	public String getSMSMessage()
	{
		return getTemplateMessage(UserLogonNotificationConfig.TEMPLATE_PROPERTY_KEY_PUSH_SMS);
	}

	public String getEMailMessage()
	{
		return getTemplateMessage(UserLogonNotificationConfig.TEMPLATE_PROPERTY_KEY_EMAIL);
	}
}
