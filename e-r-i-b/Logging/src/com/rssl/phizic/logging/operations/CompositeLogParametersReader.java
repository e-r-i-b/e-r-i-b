package com.rssl.phizic.logging.operations;

import com.rssl.phizic.utils.ListUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Krenev
 * @ created 16.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class CompositeLogParametersReader implements LogParametersReader
{
	private List<LogParametersReader> readers;

	public CompositeLogParametersReader(LogParametersReader... readers)
	{
		this.readers = ListUtil.fromArray(readers);
	}

	public LinkedHashMap read() throws Exception
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (LogParametersReader reader : readers)
		{
			String description = reader.getDescription();
			if (description == null)
			{
				map.putAll(reader.read());
			}
			else
			{
				map.put(description, reader.read());
			}
		}
		return map;
	}

	public String getDescription()
	{
		return null;
	}

	public void add(LogParametersReader reader)
	{
		readers.add(reader);
	}
}
