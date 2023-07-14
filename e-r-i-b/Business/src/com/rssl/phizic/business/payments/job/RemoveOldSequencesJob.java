package com.rssl.phizic.business.payments.job;

import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Barinov
 * @ created 16.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class RemoveOldSequencesJob extends BaseJob implements StatefulJob, InterruptableJob
{
	private static final CounterService counterService = new CounterService();
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private volatile boolean isInterrupt = false;

	public RemoveOldSequencesJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		log.info("�������� ������ ���������");
		try
		{
			List<String> seqNames = counterService.getDailySequencesNames();
			String curDate = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
			for (String sequenceName : seqNames)
			{
				// ���� ��������� ����������� ������� �� �����
				if(isInterrupt)
					break;

				if (!sequenceName.endsWith(curDate))
					try
					{
						counterService.removeSequence(sequenceName);
					}
					catch (CounterException e)
					{
						log.error("������ ��� �������� �������� " + sequenceName, e);
					}
			}
		}
		catch (CounterException e)
		{
			throw new JobExecutionException("������ ��� ��������� ������ ���������.", e);
		}
		finally
		{
			log.info("�������� ��������� ���������");
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
