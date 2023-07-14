package com.rssl.phizic.notification.ip.unusual;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.email.EmailResource;
import com.rssl.phizic.business.email.EmailResourceService;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.notification.ip.unusual.UnusualIPNotification;
import com.rssl.phizic.business.notification.ip.unusual.UnusualIPNotificationIterator;
import com.rssl.phizic.business.notification.ip.unusual.UnusualIPNotificationService;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.freemarker.FreeMarkerConverter;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @author akrenev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Джоб оповещения клиентов о входе с нестандартного IP
 */

public class UnusualIPNotificationJob extends BaseJob implements InterruptableJob, StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	private static final UnusualIPNotificationService unusualIPNotificationService = new UnusualIPNotificationService();
	private static final EmailResourceService emailResourceService = new EmailResourceService();
	private static final SubscriptionService subscriptionService = new SubscriptionService();
	private static final EmailMessagingService emailMessagingService = new EmailMessagingService();
	private static final PersonService personService = new PersonService();

	private static final int MAX_ATTEMPTS_COUNT = 5;

	private static final String EMAIL_MESSAGE_DATE_FORMAT = "%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS";
	private static final String EMAIL_MESSAGE_DATE_PARAMETER_NAME = "date";
	private static final String EMAIL_MESSAGE_FIO_PARAMETER_NAME = "fio";

	private static final String USER_LOG_RECORD_OPERATION_KEY = "UNUSUAL_USER_IP";
	private static final String USER_LOG_RECORD_DESCRIPTION_PREFIX = "Уменьшение лимитов клиента. Причина: ";
	private static final String USER_LOG_RECORD_DESCRIPTION_SUFFIX = ".";

	private volatile boolean isInterrupt = false;

	public void interrupt()
	{
		isInterrupt = true;
	}

	@Override
	protected void executeJob(JobExecutionContext context)
	{
		EmailResource emailTemplate = getEmailTemplate();

		int actualPeriod = ConfigFactory.getConfig(JobRefreshConfig.class).getPaymentNotificationsActualPeriod();
		Calendar startDate = DateHelper.getPreviousNDay(Calendar.getInstance(), actualPeriod);

		UnusualIPNotificationIterator notificationsIterator = unusualIPNotificationService.getNotificationsIterator(startDate, MAX_ATTEMPTS_COUNT);

		while (notificationsIterator.hasNext() && !isInterrupt)
		{
			try
			{
				UnusualIPNotification notification = notificationsIterator.next();
				writeToLog(notification);
				boolean smsIsSent = sendSMS(notification);
				boolean emailIsSent = sendEmail(notification, emailTemplate);
				if (smsIsSent || emailIsSent)
					notificationsIterator.remove();
			}
			catch (Exception e)
			{
				log.error("Ошибка обработки оповещения клиента о входе с нестандартного IP.", e);
			}
		}
	}

	private void writeToLog(UnusualIPNotification notification) throws Exception
	{
		if (notification.getAttemptsCount() > 1)
			return;

		try
		{
			addLogRecord(notification);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи в лог сообщения о входе клиента с нестандартного IP (id = " + notification.getId() + ").", e);
		}
	}

	private void addLogRecord(UnusualIPNotification notification) throws Exception
	{
		DefaultLogDataReader reader = new DefaultLogDataReader(USER_LOG_RECORD_DESCRIPTION_PREFIX + notification.getMessage() + USER_LOG_RECORD_DESCRIPTION_SUFFIX);
		reader.setOperationKey(USER_LOG_RECORD_OPERATION_KEY);

		try
		{
			ApplicationInfo.setCurrentApplication(Application.PhizIC);
			LogThreadContext.setLoginId(notification.getLoginId());
			Calendar dateCreated = notification.getDateCreated();
			OperationLogFactory.getLogWriter().writeSecurityOperation(reader, dateCreated, dateCreated);
		}
		finally
		{
			LogThreadContext.setLoginId(null);
			ApplicationInfo.setCurrentApplication(getApplication());
		}
	}

	private String getMessageKey()
	{
		return UnusualIPNotification.class.getName();
	}

	private EmailResource getEmailTemplate()
	{
		try
		{
			return emailResourceService.getEmailMessage(getMessageKey());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения шаблона емейл оповещения клиента о входе с нестандартного IP.", e);
			return null;
		}
	}

	private boolean sendSMS(UnusualIPNotification notification)
	{
		try
		{
			IKFLMessage message = MessageComposer.buildInformingSmsMessage(notification.getLoginId(), getMessageKey());
			message.setOperationType(OperationType.UNUSUAL_IP);
			message.setAdditionalCheck(false);
			MessagingService messagingService = MessagingSingleton.getInstance().getMessagingService();
			messagingService.sendSms(message);
			return true;
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки смс оповещения клиенту о входе с нестандартного IP (id = " + notification.getId() + ").", e);
		}
		return false;
	}

	private boolean sendEmail(UnusualIPNotification notification, EmailResource emailTemplate)
	{
		if (emailTemplate == null)
			return false;

		try
		{
			PersonalSubscriptionData personalSubscriptionData = subscriptionService.findPersonalData(notification.getLoginId());
			if (personalSubscriptionData == null)
				return false;

			String emailAddress = personalSubscriptionData.getEmailAddress();
			if (StringHelper.isEmpty(emailAddress))
				return false;

			ActivePerson person = personService.findByLogin(notification.getLoginId());
			if (person == null)
				return false;

			HashMap<String, Object> properties = new HashMap<String, Object>(2);
			properties.put(EMAIL_MESSAGE_FIO_PARAMETER_NAME,  person.getFirstPatrName());
			properties.put(EMAIL_MESSAGE_DATE_PARAMETER_NAME, String.format(EMAIL_MESSAGE_DATE_FORMAT, Calendar.getInstance()));

			emailMessagingService.sendPlainEmail(emailAddress, emailTemplate.getTheme(), FreeMarkerConverter.convert(emailTemplate.getPlainText(), properties));

			return true;
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки email оповещения клиенту о входе с нестандартного IP (id = " + notification.getId() + ").", e);
		}
		return false;
	}
}
