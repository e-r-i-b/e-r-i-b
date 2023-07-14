package com.rssl.phizic.operations.userprofile;

import java.util.HashMap;
import java.util.Map;

/**
 * @ author: filimonova
 * @ created: 21.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class PersonalSettings extends Settings
{
	private Map<String, String> personalSettings;
	private String message;

	public PersonalSettings(Map<String, Object> fields)
	{
		personalSettings = new HashMap<String, String>();
		message = new String();
		int size = fields.size();
		int i = 0;
		for(String key: fields.keySet())
		{
			personalSettings.put(key, (String)fields.get(key));
			message += key + " " + (String)fields.get(key);
			if(++i < size)
				message +="; ";
		}
	}

	protected Map<String, String> getSettings()
	{
		return personalSettings;
	}

	public String getMessage()
	{
		return message;
	}
}
