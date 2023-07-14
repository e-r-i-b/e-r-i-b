package com.rssl.phizic.logging.system;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author eMakarov
 * @ created 27.03.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class LogLevelConfig extends Config
{
	public static final String LEVEL_PREFIX = "level.";
	public static final String EXTENDED_PREFIX = "extended.";
	public static final String CONFIG_NAME = "log.properties";

	protected LogLevelConfig(PropertyReader reader)
	{
		super(reader);
	}
}
