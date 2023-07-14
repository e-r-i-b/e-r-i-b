package com.rssl.phizic.business.marker;

import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author mihaylov
 * @ created 26.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class JobExecutionMarkerServiceTest  extends BusinessTestCaseBase
{
	private static final JobExecutionMarkerService service = new JobExecutionMarkerService();

	/**
	 * ѕроверить работу сервиса по добавлению маркеров
	 * @throws Exception
	 */
	public void testAddMarker() throws Exception
	{
		String jobName = "testJobName";
		service.createForJob(jobName);
		JobExecutionMarker marker = service.findForJob(jobName);

		assertNotNull(marker);
		
		service.remove(marker);
		service.remove(marker);

		JobExecutionMarker secondMarker = service.findForJob(jobName);
		assertNull(secondMarker);
	}
}
