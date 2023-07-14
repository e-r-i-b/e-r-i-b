package com.rssl.phizic.messaging.quartz;

import com.rssl.phizic.quartz.QuartzException;
import com.rssl.phizic.business.SimpleJobService;
import org.quartz.JobDataMap;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ArchiveMailJobService extends SimpleJobService
{
	private final Map<String, String> archiveMailJobClasses = new HashMap<String, String>();

	/**
	 * ctor
	 */
	public ArchiveMailJobService()
	{
		archiveMailJobClasses.put("DeleteOutgoingMailJob","com.rssl.phizic.archive.mail.DeleteOutgoingMailJob");
		archiveMailJobClasses.put("DeleteIncomingMailJob","com.rssl.phizic.archive.mail.DeleteIncomingMailJob");
		archiveMailJobClasses.put("ArchiveMailJob","com.rssl.phizic.archive.mail.ArchiveMailJob");
	}

	private String getJobGroupName()
	{
		return "ArchiveMailJobs";
	}

	private String getJobClassName(String jobName)
	{
		return archiveMailJobClasses.get(jobName);
	}

	public void addNewCronJob(String jobName, String expression, Map<String,String> dataMap) throws ParseException, QuartzException
	{
		JobDataMap jobMap = new  JobDataMap(dataMap);
		addNewCronJob(jobName,expression,getJobClassName(jobName),getJobGroupName(),jobMap);
	}

	public String getCronExpression(String jobName) throws QuartzException
	{
	    return getCronExpression(jobName,getJobGroupName());
	}
}
