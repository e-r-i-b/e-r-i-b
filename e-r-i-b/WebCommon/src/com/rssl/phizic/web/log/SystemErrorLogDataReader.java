package com.rssl.phizic.web.log;

import com.rssl.phizic.logging.operations.LogDataReader;

import java.util.LinkedHashMap;

/**
 * @author Krenev
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 * TODO создать связь с журналом системы.
 */
public class SystemErrorLogDataReader implements LogDataReader
{
	private LogDataReader original;

	public SystemErrorLogDataReader(LogDataReader original, Exception e) {

		this.original = original;
	}

	public String getOperationPath()
	{
		return original.getOperationPath();
	}

	public String getOperationKey()
	{
		return original.getOperationKey();
	}

	public String getDescription()
	{
		return original.getDescription()+"(системная ошибка)";
	}

	public String getKey()
	{
		return original.getKey();
	}

	public LinkedHashMap readParameters() throws Exception
	{
		return original.readParameters();
	}

	public ResultType getResultType()
	{
		return ResultType.SYSTEM_ERROR;
	}
}
