package com.rssl.phizic.scheduler.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.sms.delayed.DelayedCommand;
import com.rssl.phizic.business.ermb.sms.delayed.DelayedCommandService;
import com.rssl.phizic.business.ermb.sms.delayed.PersonalDelayedCommand;
import com.rssl.phizic.business.ermb.sms.messaging.CommandExecutor;
import com.rssl.phizic.business.ermb.sms.messaging.DelayedCommandRequisites;
import com.rssl.phizic.business.ermb.sms.messaging.ErmbSmsChannel;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.scheduler.StatefulModuleJob;
import com.rssl.phizic.utils.EntityUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author Gulov
 * @ created 17.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Выполнение отложенных (невыполненных) СМС-команд
 */
public class DelayedCommandExecuteJob extends StatefulModuleJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final DelayedCommandService delayedCommandService = new DelayedCommandService();
	private static final PersonService personService = new PersonService();
	private static final int MAX_RECORD_COUNT = 1000;

	public Class<? extends Module> getModuleClass()
	{
		return ErmbSmsChannel.class;
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
//		получаем список всех невыполненных команд
		List<DelayedCommand> list = delayedCommandService.find(MAX_RECORD_COUNT);
		if (CollectionUtils.isEmpty(list))
			return;
		if (log.isDebugEnabled())
			log.debug("[Шедуллер ЕРМБ] Обработа отложенных комманд " +
			StringUtils.join(EntityUtils.collectEntityIds(list), ", "));
		for (DelayedCommand delayedCommand : list)
		{
			Person person = null;
			try
			{
				person = personService.findById(delayedCommand.getUserId());
			}
			catch (BusinessException e)
			{
				log.error("Ошибка поиска клиента по id = " + delayedCommand.getUserId(), e);
				continue;
			}
			if (person == null)
			{
				delayedCommandService.remove(delayedCommand);
				continue;
			}
			DelayedCommandRequisites requisites = new DelayedCommandRequisites(new PersonalDelayedCommand(person, delayedCommand), getModule());
			CommandExecutor executor = new CommandExecutor(requisites);
			try
			{
				executor.run();
			}
			finally
			{
				// убиваем сессию в любом случае
				requisites.destroySession();
			}
		}
		if (log.isDebugEnabled())
			log.debug("[Шедуллер ЕРМБ] Обработа отложенных комманд завершена");
	}
}
