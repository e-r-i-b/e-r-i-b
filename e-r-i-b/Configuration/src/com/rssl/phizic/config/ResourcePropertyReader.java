package com.rssl.phizic.config;

import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.utils.StringHelper;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @author Evgrafov
 * @ created 04.07.2006
 * @ $Author: bogdanov $
 * @ $Revision: 70100 $
 */

public class ResourcePropertyReader extends PropertyReader
{
	 private final String fileName;

	/**
	 * @param resource имя ресурса
	 */
	ResourcePropertyReader(String resource)
	{
		super(null);
		this.fileName = resource;
	}

	@Override
	protected Map<String, String> doRefresh()
	{
		Map<String, String> map = new HashMap<String, String>();
		if (PropertyHelper.propertiesExists(fileName))
		{
		    for (Map.Entry<Object, Object> property : PropertyHelper.readProperties(fileName).entrySet())
			    map.put((String)property.getKey(), (String)property.getValue());
		}

		return map;
	}

	public String getFileName()
	{
		return fileName;
	}
}