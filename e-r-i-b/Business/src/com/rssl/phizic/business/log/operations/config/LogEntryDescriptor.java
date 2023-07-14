package com.rssl.phizic.business.log.operations.config;

import com.rssl.phizic.logging.operations.OperationType;

import java.io.Serializable;

/**
 * @author Krenev
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 * Описание логируемого действия
 */
public class LogEntryDescriptor implements Serializable
{
	private String description;
	private OperationType type;

	public LogEntryDescriptor(String description, OperationType type)
	{
		this.type = type;
		this.description = description;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return тип действия.
	 */
	public OperationType getType()
	{
		return type;
	}
}
