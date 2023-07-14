package com.rssl.phizic.logging.operations;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Krenev
 * @ created 16.03.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class LogParemetersReaderBase implements LogParametersReader
{
	protected static final PropertyReader descriptions = ConfigFactory.getReaderByFileName("com/rssl/phizic/business/entities.properties");

	private String description;

	public LogParemetersReaderBase(String description) {
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
