package com.rssl.phizic.messaging.jobs.archivation;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.utils.files.FileHelper;
import org.quartz.JobExecutionException;

import java.util.Properties;

/**
 * @author eMakarov
 * @ created 14.04.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class ArchivationJobBase extends ArchivationJob
{
	private final String prefix;

	public ArchivationJobBase(String prefix) throws JobExecutionException
	{
		super();
		this.prefix = prefix;
	}

	public int getLastDays()
	{
		return ConfigFactory.getConfig(LoggingHelper.class).getLastDays(prefix);
	}

	public boolean isEnabled()
	{
		return ConfigFactory.getConfig(LoggingHelper.class).isEnabled(prefix);
	}

	public String getPath()
	{
		return ConfigFactory.getConfig(LoggingHelper.class).getPath(prefix);
	}
}
