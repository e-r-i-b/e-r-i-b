package com.rssl.phizic.business.log.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.operations.OperationType;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 10.03.2006
 * @ $Author: bogdanov $
 * @ $Revision: 67137 $
 */
public class OperationDescriptorsConfigImpl extends OperationDescriptorsConfig
{
	private static final String OPERATION_DESCIPTION_SUFFIX = ".description";
	private static final String OPERATION_TYPE_SUFFIX = ".type";
	private final Map<String, OperationType> defaultTypes = new HashMap<String, OperationType>();

	public OperationDescriptorsConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		for (OperationType type : OperationType.values())
		{
			registerDefaltOperationTypes(type);
		}
	}

	private void registerDefaltOperationTypes(OperationType type) throws ConfigurationException
	{
		String key = "default." + type + ".operations";
		String operations = getProperty(key.toLowerCase());
		if (operations == null)
		{
			return;
		}
		for (String operation : operations.split(";"))
		{
			OperationType operationType = defaultTypes.put(operation, type);
			if (operationType != null)
			{
				throw new ConfigurationException("Oперация <" + operation + "> помечена как " + operationType + " и " + type);
			}
		}
	}

	public LogEntryDescriptor getDescriptor(Class clazz, String operation) throws BusinessException
	{
		String operationKey = clazz.getName() + "." + operation;
		String description = getProperty(operationKey + OPERATION_DESCIPTION_SUFFIX);
		if (StringHelper.isEmpty(description))
		{
			return null;
		}
		String type = getProperty(operationKey + OPERATION_TYPE_SUFFIX);
		OperationType operationType;
		if (StringHelper.isEmpty(type))
		{
			operationType = defaultTypes.get(operation);
		}
		else
		{
			operationType = OperationType.valueOf(type);
		}
		if (operationType == null)
		{
			throw new BusinessException("Неопределен тип для действия " + operationKey);
		}
		return new LogEntryDescriptor(description, operationType);
	}
}
