package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.security.certification.CertificateOwnService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author eMakarov
 * @ created 09.02.2008
 * @ $Author$
 * @ $Revision$
 */
public class CertificateStatusChanger implements StatefulJob
{
	private CertificateOwnService certificateOwnService = new CertificateOwnService();

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		certificateOwnService.setExpiredStatus();
	}
}
