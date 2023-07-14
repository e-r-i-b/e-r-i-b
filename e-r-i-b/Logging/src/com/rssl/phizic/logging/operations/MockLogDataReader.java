package com.rssl.phizic.logging.operations;

import java.util.LinkedHashMap;

/**
 * @author Krenev
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class MockLogDataReader implements LogDataReader
{
	public String getOperationPath()
	{
		return "MockOperationPath";
	}

	public String getOperationKey()
	{
		return "MockOperationKey";
	}

	public String getDescription()
	{
		return "MockDescription";
	}
	public String getKey()
	{
		return "MockKey";
	}

	public LinkedHashMap<String, ?> readParameters() throws Exception
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("parameter", "value");
		return map;
	}

	public ResultType getResultType()
	{
		return ResultType.SUCCESS;
	}
}
