package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mbuesi.State;
import com.rssl.phizic.business.mbuesi.UESICancelLimitMessage;
import com.rssl.phizic.business.mbuesi.UESIService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizicgate.esberibgate.credit.CRMLimitRefusesSender;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Джоб для отправки отказов от изменения кредитных лимитов в КСШ
 * @author Pankin
 * @ created 28.01.15
 * @ $Author$
 * @ $Revision$
 */
public class SendCardOfferRefusesJob extends BaseJob implements StatefulJob
{
	private static final UESIService UESI_SERVICE = new UESIService();

	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		CRMLimitRefusesSender crmLimitRefusesSender = new CRMLimitRefusesSender();
		try
		{
			for (UESICancelLimitMessage message : UESI_SERVICE.getNewMessages())
			{
				Message queueMessage = crmLimitRefusesSender.send(message.getPhone(), message.getEventDateTime());
				UESI_SERVICE.updateStateAndExtId(message, State.PROCESSED, queueMessage.getJMSMessageID());
			}
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}
		catch (GateException e)
		{
			throw new JobExecutionException(e);
		}
		catch (GateLogicException e)
		{
			throw new JobExecutionException(e);
		}
		catch (JMSException e)
		{
			throw new JobExecutionException(e);
		}
	}
}
