package com.rssl.phizic.business.payments.job;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleJobService;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.news.NewsDistribution;
import com.rssl.phizic.business.news.NewsDistributionsState;
import com.rssl.phizic.business.news.NewsService;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.NotificationSettingsLogService;
import com.rssl.phizic.logging.settings.UserMessageLogHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.quartz.QuartzException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Джоб для новостной email-рассылки
 * @author gladishev
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 */
public class NewsDistributionJob extends BaseJob implements StatefulJob
{
	public static final String PROCESSED_DISTRIBUTION_ID_KEY = "processedDsitributionId";
	private static final String LAST_PROCESSED_LOGIN_ID_KEY = "lastProcessedLoginId";
	private static final String LAST_PROCESSED_TB_KEY = "lastProcessedTB";

	private static final NewsService newsService = new NewsService();
	private static final PersonService personService = new PersonService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);
	private static final NotificationSettingsLogService notificationLogService = new NotificationSettingsLogService();

	private final SimpleJobService jobService = new SimpleJobService();

	public NewsDistributionJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			process(context);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private void process(JobExecutionContext context) throws BusinessException
	{
		JobDataMap triggerJobDataMap = context.getTrigger().getJobDataMap();
		JobDataMap detailJobDataMap = context.getJobDetail().getJobDataMap();
		Long distributionId = (Long) triggerJobDataMap.get(PROCESSED_DISTRIBUTION_ID_KEY);
		NewsDistribution newsDistribution = newsService.findDistributionById(distributionId);

		Long lastProcessedLoginId = (Long) detailJobDataMap.get(LAST_PROCESSED_LOGIN_ID_KEY);
		String lastProcessedTB = (String) detailJobDataMap.get(LAST_PROCESSED_TB_KEY);
		if (lastProcessedTB == null)
			lastProcessedTB = StringUtils.split(newsDistribution.getTerbanks(), ",")[0];
		List<Object[]> clientEmails = personService.getClientEmails(lastProcessedTB,
														newsDistribution.getMailCount(), lastProcessedLoginId == null ? 0 : lastProcessedLoginId);

		if (CollectionUtils.isEmpty(clientEmails))
		{
			String[] terbanks = StringUtils.split(newsDistribution.getTerbanks(), ",");
			if (lastProcessedTB.equals(terbanks[terbanks.length - 1]))
			{
				//отправили письма клиентам из всех нужных тб. прекращаем выполнение джоба
				removeJob(context);
				newsDistribution.setState(NewsDistributionsState.SENT);
				newsService.addOrUpdateNewsDistribution(newsDistribution);
				return;
			}
			else
			{
				//для текущего тб клиенты закончились. переходим к следующему тб.
				detailJobDataMap.put(LAST_PROCESSED_LOGIN_ID_KEY, 0L);
				for (int i = 0; i < terbanks.length - 1; ++i)
				{
					if (lastProcessedTB.equals(terbanks[i]))
					{
						detailJobDataMap.put(LAST_PROCESSED_TB_KEY, terbanks[i + 1]);
						return;
					}
				}
			}
		}

		for (Object[] obj : clientEmails)
			sendMail(obj, newsDistribution);

		detailJobDataMap.put(LAST_PROCESSED_LOGIN_ID_KEY, clientEmails.get(clientEmails.size()-1)[0]);
		detailJobDataMap.put(LAST_PROCESSED_TB_KEY, lastProcessedTB);
	}

	private void removeJob(JobExecutionContext context)
	{
		try
		{
			jobService.removeJob(context.getJobDetail().getName(), context.getJobDetail().getGroup());
		}
		catch (QuartzException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private void sendMail(Object[] obj, NewsDistribution distribution)
	{
		try
		{
			MailFormat mailFormat = StringUtils.isNotEmpty((String)obj[2]) ? MailFormat.valueOf((String)obj[2]) : null;
			MailHelper.sendEMail(distribution.getTitle(), new StringReader(distribution.getText()), (String) obj[1], mailFormat, MailHelper.getCurrentDateFields());
			UserMessageLogHelper.saveMailNotificationToLog((Long) obj[0], null);
		}
		catch (IOException e)
		{
			log.error("Не удалось отправить сообщение для пользователя с loginId +" + distribution.getLogin().getId(), e);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось отправить сообщение для пользователя с loginId +" + distribution.getLogin().getId(), e);
		}
	}
}
