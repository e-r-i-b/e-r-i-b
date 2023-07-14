package com.rssl.phizic.operations.limits;

import com.rssl.phizic.operations.userprofile.Settings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gulov
 * @ created 04.02.2011
 * @ $Authors$
 * @ $Revision$
 */
public class IndividualLimitSettings extends Settings
{
	private Map<String, String> limitSettings;
	private String message;

	public IndividualLimitSettings(Map<String, Object> fields)
	{
		limitSettings = new HashMap<String, String>();
		message = new String();

		for(String key: fields.keySet())
		{
			limitSettings.put(key, fields.get(key).toString());
			message += key + " " + fields.get(key).toString()+";";
		}
	}

	protected Map<String, String> getSettings()
	{
		return limitSettings;
	}

	public String getMessage()
	{
		return message;
	}
}
