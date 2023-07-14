package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.messaging.info.*;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.settings.UserMessageLogHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.jobs.impl.PaymentExecuteSupplier;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilderFactory;
import com.rssl.phizic.messaging.push.PushMessage;
import com.rssl.phizic.notifications.impl.PaymentExecuteNotification;
import com.rssl.phizic.notifications.service.NotificationService;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.exception.LockAcquisitionException;
import org.quartz.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.mail.Message;
import javax.mail.Transport;

/**
 * @author Kosyakov
 * @ created 27.03.2007
 * @ $Author: rtishcheva $
 * @ $Revision: 67917 $
 */
public class NotificationHandlingJob extends BaseJob implements Job, InterruptableJob
{
	private static final AtomicInteger counter = new AtomicInteger(0);//������� �������� ���������� ������������ ���������� ������.
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final NotificationService notificationService = new NotificationService();
	private static final SubscriptionService SUBSCRIPTION_SERVICE = new SubscriptionService();
	private static final ServiceService SERVICE_SERVICE = new ServiceService();
	private static final String ADDIT_STATES_SERVICE_KEY = "PaymentAdditStatesNotificationsManagement";
	private static final Set<String> ACCESS_CHECK_STATES = new HashSet<String>(2);

	private volatile boolean isInterrupt = false;

	static
	{
		ACCESS_CHECK_STATES.add("REFUSED");
		ACCESS_CHECK_STATES.add("WAIT_CONFIRM");
	}

	/**
	 * ������������� �����
	 */
	public NotificationHandlingJob() throws JobExecutionException
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		log.info("������ ��������� ������ ����������");
		//����������� ������� ���������� ��������� �����
		int jobNumber = counter.incrementAndGet();
		try
		{
			int maxJobCount = ConfigFactory.getConfig(JobRefreshConfig.class).getMaxJobsCount(getClass().getName());
			if (jobNumber > maxJobCount)
			{
				log.trace("������������ ���������� ������������� �����:" + maxJobCount + ". ������� ������:" + jobNumber + ". ��������� ������.");
				return;
			}
			log.trace("������������ ���������� ������������� �����:" + maxJobCount + ". ������� ������:" + jobNumber + ".���������� ������.");

			doJob();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������ ����������", e);
		}
		finally
		{
			//��������� ������� ���������� ��������� �����
			int jobCount = counter.decrementAndGet();
			log.info("����� ��������� ������ ����������");
			log.trace("������� ���������� ���������� ������ " + jobCount);
		}
	}

	/**
	 * ������ ��������� ����������:
	 * 1. �������� �������� 10 loginIds �� ������� ����������
	 * 2. ������������ ������ �� ���������� loginIds: ��������� �������� ������� ��� ����������� ������������ ��������� ���������� � ������� �������
	 * 3. �������� ������ ���������� ��� �������, ��������������� �� ���� ��������, ������� �� �� ��
	 * 4. ���������� ����������� �� ������, ���������� � �������� �������.
	 */
	private void doJob() throws Exception
	{
		int actualPeriod = ConfigFactory.getConfig(JobRefreshConfig.class).getPaymentNotificationsActualPeriod();
		for (; !isInterrupt; )
		{
			final Calendar notificationsStartDate = DateHelper.getPreviousNDay(Calendar.getInstance(), actualPeriod);
			//1. �������� loginId
			final List<Long> loginIds = notificationService.getLogins4Notification(notificationsStartDate);

			if (CollectionUtils.isEmpty(loginIds))
			{
				log.trace("�� ������� ������ ��� ����������.");
				return;
			}

			for (Long loginId : loginIds)
			{
				log.trace("�������� ������������ ������ ��� ��������� ����������, loginId=" + loginId);
				Pair<NotificationChannel, List<PaymentExecuteNotification>> notificationsInfo = getNotificationsInfo(loginId, notificationsStartDate);
				if (notificationsInfo == null)
					continue;

				List<PaymentExecuteNotification> notifications = notificationsInfo.getSecond();
				log.trace("�������� " + notifications.size() + " ���������� ��� ������� loginId=" + loginId);
				//3. ������������ �������� ���������� ��� �������
				boolean ermbProfileExistsByLoginId = ErmbHelper.hasErmbProfileByLogin(loginId);
				NotificationChannel channel = notificationsInfo.getFirst();
				MessageBuilder messageBuilder = MessageBuilderFactory.newInstance().newMessageBuilder(channel.name(), "PaymentExecute");
				PersonalSubscriptionData personalData = SUBSCRIPTION_SERVICE.findPersonalData(loginId);
				Boolean stateAccess = null;
				for (PaymentExecuteNotification notification : notificationsInfo.getSecond())
				{
					try
					{
						if (ACCESS_CHECK_STATES.contains(notification.getDocumentState()))
						{
							if(stateAccess == null)
								stateAccess = SERVICE_SERVICE.isPersonServices(loginId, ADDIT_STATES_SERVICE_KEY);

							if (!stateAccess)
							{
								log.trace("���������� �� ����������: ����������� ������ ������� loginId=" + loginId + " � ������ PaymentAdditStatesNotificationsManagement.");
								continue;
							}
						}

						sendNotification(personalData, messageBuilder, notification, channel, ermbProfileExistsByLoginId);
					}
					catch (Exception e)
					{
						log.error("������ �������� ���������� ��� ������� loginId="+ loginId, e);
					}
				}
				log.trace("��������� �������� ���������� ��� ������� loginId=" + loginId);
			}
		}
	}

	private Pair<NotificationChannel, List<PaymentExecuteNotification>> getNotificationsInfo(final Long loginId, final Calendar notificationsStartDate) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<Pair<NotificationChannel, List<PaymentExecuteNotification>>>()
		{
			public Pair<NotificationChannel, List<PaymentExecuteNotification>> run(Session session) throws Exception
			{
				try
				{
					//2. ��������� �������� �������
					SubscriptionImpl subscription = SUBSCRIPTION_SERVICE.findSubscription(loginId, UserNotificationType.operationNotification, LockMode.UPGRADE_NOWAIT);

					if (subscription == null || subscription.getChannel() == NotificationChannel.none)
					{
						notificationService.deletePaymentNotifications4Login(loginId);

						log.trace("���������� �� ���������� �������� ��� ������� loginId=" + loginId + " �������, ������ ��� ������ �� �������� �� ������ ��� ��������.");
						return null;
					}

					List<PaymentExecuteNotification> paymentNotifications = notificationService.findPaymentNotifications(loginId, notificationsStartDate);

					if (CollectionUtils.isEmpty(paymentNotifications))
					{
						log.trace("�� ������� ���������� ��� ������� loginId=" + loginId);
						return null;
					}

					notificationService.delete(paymentNotifications); //������� ����� �� ���������. ��������������� �������� ���.

					return new Pair<NotificationChannel, List<PaymentExecuteNotification>>(subscription.getChannel(), paymentNotifications);
				}
				catch (LockAcquisitionException ignored)
				{
					log.trace("������ ���������� �������� ��� loginId=" + loginId + ". ���������� ���.");
					return null;
				}
			}
		});
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}

	private void sendNotification(PersonalSubscriptionData subscriptionData, MessageBuilder messageBuilder, PaymentExecuteNotification notification, NotificationChannel channel, boolean ermbProfileExists) throws Exception
	{
		Object data = new PaymentExecuteSupplier(notification).getData(subscriptionData, Calendar.getInstance());
		switch (channel)
		{
			case sms:
			{
				IKFLMessage ikflMessage = messageBuilder.create(subscriptionData, data);
				ikflMessage.setErmbConnectedPerson(ermbProfileExists);
				MessagingSingleton.getInstance().getMessagingService().sendSms(ikflMessage);
				break;
			}
			case push:
			{
				PushMessage message = messageBuilder.create(subscriptionData, data);
				MessagingSingleton.getInstance().getMessagingService().sendPush(message);
				break;
			}
			case email:
			{
				Message message = messageBuilder.create(subscriptionData, data);
				Transport.send(message, message.getRecipients(Message.RecipientType.TO));
				UserMessageLogHelper.saveMailNotificationToLog(subscriptionData.getLoginId(), null);
			}
		}
	}
}
