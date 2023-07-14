package com.rssl.phizic.messaging.quartz;

import com.rssl.phizic.business.SimpleJobService;
import com.rssl.phizic.quartz.QuartzException;
import org.quartz.JobDataMap;
import java.text.ParseException;
import java.util.Map;

/**
 * @author komarov
 * @ created 24.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class DeleteOldCardOperationJobService extends SimpleJobService
{
	private static final String DELETE_OLD_CARD_OPERATION_JOB_CLASS = "com.rssl.phizic.finances.delete.DeleteOldCardOperationJob";
	private static final String JOB_NAME = "DeleteOldCardOperationJob";

	private String getJobGroupName()
	{
		return "DeleteOldCardOperationJobs";
	}

	/**
	 *  �������� � ������� ����������� ����� Cron ����. ���� ���� ��� ���� �� �� ���������.
	 * @param expression Cron ���������
	 * @param dataMap ���. ����������
	 * @throws ParseException
	 * @throws QuartzException
	 */
	public void addNewCronJob(String expression, Map<String,String> dataMap) throws ParseException, QuartzException
	{
		JobDataMap jobMap = new JobDataMap(dataMap);
		addNewCronJob(JOB_NAME, expression, DELETE_OLD_CARD_OPERATION_JOB_CLASS, getJobGroupName(), jobMap);
	}

	/**
	 * ���������� Cron ��������� ��� JOB_NAME �����
	 * @return Cron ��������� ��� ����������� �����, null ���� ���� �� ������ ��� ������� �� �������� Cron
	 * @throws QuartzException
	 */
	public String getCronExpression() throws QuartzException
	{
	    return getCronExpression(JOB_NAME, getJobGroupName());
	}
}
