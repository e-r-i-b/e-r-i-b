package com.rssl.phizic.logging.operations;

import java.util.LinkedHashMap;

/**
 * @author Krenev
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class DefaultLogDataReader implements LogDataReader
{
	private CompositeLogParametersReader parametersReader = new CompositeLogParametersReader();
	private String description;
	private String key;
	private String operationPath;
	private String operationKey;

	public DefaultLogDataReader(String description)
	{
		this.description = description;
	}

	public DefaultLogDataReader(String description, String key, String operationKey)
	{
		this.description = description;
		this.key = key;
		this.operationKey = operationKey;
	}	

	public String getOperationPath()
	{
		return operationPath;
	}

	public String getOperationKey()
	{
		return operationKey;
	}

	public String getDescription()
	{
		return description;
	}

	public String getKey()
	{
		return key;
	}

	public ResultType getResultType()
	{
		return ResultType.SUCCESS;
	}

	public LinkedHashMap readParameters() throws Exception
	{
		return parametersReader.read();
	}

	public void addParametersReader(LogParametersReader reader)
	{
		parametersReader.add(reader);
	}

	public void setOperationPath(String operationPath)
	{
		this.operationPath = operationPath;
	}

	public void setOperationKey(String operationKey)
	{
		this.operationKey = operationKey;
	}

	public void setKey(String key)
	{
		this.key = key;
	}
}
