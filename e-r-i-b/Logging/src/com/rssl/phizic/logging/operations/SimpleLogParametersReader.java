package com.rssl.phizic.logging.operations;

import java.util.LinkedHashMap;

/**
 * @author hudyakov
 * @ created 04.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class SimpleLogParametersReader extends LogParemetersReaderBase implements LogParametersReader
{
	private String name;   
	private Object value;

	public SimpleLogParametersReader(Object value)
	{
		super("");

		this.name  = "";
		this.value = value;
	}

	public SimpleLogParametersReader(String name, Object value)
	{
		super("");

		this.name  = name;
		this.value = value;
	}

	public SimpleLogParametersReader(String description, String name, Object value)
	{
		super(description);

		this.name  = name;
		this.value = value;
	}

	public LinkedHashMap read() throws Exception
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(name, value);
		return map;
	}
}
