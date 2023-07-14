package com.rssl.phizic.operations.logging.archive;

import com.rssl.phizic.logging.Constants;

import java.util.Map;
import java.util.HashMap;

/**
 * @author eMakarov
 * @ created 13.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class ArchivationValuesMaps
{
	private static Map<String, String> archiveNames = new HashMap<String, String>();
	private static Map<String, String> jobNames = new HashMap<String, String>();

	static
	{
		archiveNames.put(Constants.MESSAGE_LOG_PREFIX, Constants.MESSAGE_LOG_ARCHIVE_NAME);
		archiveNames.put(Constants.SYSTEM_LOG_PREFIX, Constants.SYSTEM_LOG_ARCHIVE_NAME);
		archiveNames.put(Constants.USER_LOG_PREFIX,  Constants.USER_LOG_ARCHIVE_NAME);
		archiveNames.put(Constants.ENTRIES_LOG_PREFIX, Constants.ENTRIES_LOG_ARCHIVE_NAME);

		jobNames.put(Constants.MESSAGE_LOG_PREFIX, "com.rssl.phizic.messaging.jobs.archivation.MessagesLogArchivation");
		jobNames.put(Constants.SYSTEM_LOG_PREFIX, "com.rssl.phizic.messaging.jobs.archivation.SystemLogArchivation");
		jobNames.put(Constants.USER_LOG_PREFIX,  "com.rssl.phizic.messaging.jobs.archivation.OperationsLogArchivation");
		jobNames.put(Constants.ENTRIES_LOG_PREFIX, "com.rssl.phizic.messaging.jobs.archivation.EntriesLogArchivation");
	}

	public static String getArchiveName(String prefix)
	{
		return archiveNames.get(prefix);
	}

	public static String getJobClassName(String prefix)
	{
		return jobNames.get(prefix);
	}
}
