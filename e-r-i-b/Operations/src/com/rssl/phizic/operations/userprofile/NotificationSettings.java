package com.rssl.phizic.operations.userprofile;

import java.util.Map;

/**
 * @author gladishev
 * @ created 10.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotificationSettings extends Settings
{
	private Map<String, String> settings;

	NotificationSettings(Map<String, String> settings)
	{
		this.settings = settings;
	}

	protected Map<String, String> getSettings()
	{
		return settings;
	}

	public String getNotificationType()
	{
		return settings.get("notificationType");
	}
}
