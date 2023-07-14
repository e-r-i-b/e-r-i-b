package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pages.messages.InformMessageService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Calendar;

/**
 * Джоб для автоматического снятия информационных сообщений с публикации
 * @author komarov
 * @ created 30.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class InformMessagesDeletingJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final InformMessageService informMessageService = new InformMessageService();

	public InformMessagesDeletingJob() throws JobExecutionException
	{
	}
	
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			informMessageService.removeOldInformMessages(Calendar.getInstance());
		}
		catch(BusinessException ex)
		{
		    log.error("Ошибка удаления информационных сообщений", ex);			
		}
	}
}
