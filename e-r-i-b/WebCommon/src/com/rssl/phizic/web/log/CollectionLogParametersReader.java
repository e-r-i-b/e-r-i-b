package com.rssl.phizic.web.log;

import com.rssl.phizic.logging.operations.LogParametersReader;
import com.rssl.phizic.logging.operations.LogParemetersReaderBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author hudyakov
 * @ created 05.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class CollectionLogParametersReader extends LogParemetersReaderBase implements LogParametersReader
{
	private Collection collection;

	public CollectionLogParametersReader(String description, Collection collection)
	{
		super(description);

		this.collection = collection;
	}

	public LinkedHashMap read() throws Exception
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		int i = 1;
		for (Object obj : collection)
		{
			String entiryName = descriptions.getProperty(obj.getClass().getName());

			BeanLogParemetersReader reader;
			if (entiryName != null)
			{
				reader = new BeanLogParemetersReader(entiryName + " " + i, obj);
			}
			else
			{
				reader = new BeanLogParemetersReader("" + i, obj);
			}
			map.put(reader.getDescription(), reader.read());
			i++;
		}
		return map;
	}
}
