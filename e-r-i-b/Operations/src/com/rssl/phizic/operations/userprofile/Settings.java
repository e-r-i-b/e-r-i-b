package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Map;
import java.util.HashMap;

/**
 * @ author: filimonova
 * @ created: 20.07.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class Settings implements ConfirmableObject
{
	private Long id;

	protected abstract Map<String, String> getSettings();

	public Long getId()
	{
		hashParameters();
		return id;
	}

	private void hashParameters()
	{
		Map<String, String> settings = getSettings();
		String parameters = new String();
		for (String key: settings.keySet())
		{
			parameters += key+"="+settings.get(key);
		}
		Integer intId = parameters.hashCode();
		id = intId.longValue();
	}

	public byte[] getSignableObject()
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

}
