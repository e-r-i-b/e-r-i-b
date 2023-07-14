package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringOfflineSendersFactory;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.notifications.FraudNotification;
import com.rssl.phizic.rsa.notifications.FraudNotificationService;
import com.rssl.phizic.rsa.notifications.enumeration.FraudNotificationState;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author tisov
 * @ created 13.07.15
 * @ $Author$
 * @ $Revision$
 * Джоб отправки фрод-оповещений в систему фрод-мониторинга
 */
public class FraudNotificationJob extends BaseJob  implements Job
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final FraudNotificationService service = FraudNotificationService.getInstance();

	private static DocumentBuilder getBuilder() throws ParserConfigurationException
	{
		return DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		RSAConfig config = RSAConfig.getInstance();

		if (!config.isNotificationJobActivity())
		{
			return;
		}

		List<Long> packIds = getNotificationPackIds();

		for (Long notificationId : packIds)
		{
			FraudNotification notification = null;
			try
			{
				notification = service.getNotification(notificationId);
			}
			catch (Exception e)
			{
				log.error(e);
				continue;
			}

			processNotification(notification);
		}
	}

	private void processNotification(FraudNotification notification)
	{
		if (notification.getState() != FraudNotificationState.NOT_SENT)
		{
			return;
		}

		Calendar expirationDate = Calendar.getInstance();
		expirationDate.add(Calendar.DAY_OF_MONTH, - RSAConfig.getInstance().getFraudNotificationRelevancePeriod());

		try
		{
			if (expirationDate.compareTo(notification.getCreationDate()) > 0)
			{
				service.updateState(notification.getId(), FraudNotificationState.SKIPPED);
			}
			else
			{
				Document document = getDocumentByNotification(notification);
				FraudMonitoringSender sender = FraudMonitoringOfflineSendersFactory.getInstance().getSender(document);
				sender.send();
				service.updateState(notification.getId(), FraudNotificationState.SENT);
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	private Document getDocumentByNotification(FraudNotification notification) throws ParserConfigurationException, IOException, SAXException
	{
		return getBuilder().parse(new ByteArrayInputStream(notification.getRequestBody().getBytes("UTF-16")));
	}

	private List<Long> getNotificationPackIds() throws JobExecutionException
	{
		RSAConfig config = RSAConfig.getInstance();
		int limit = config.getFraudNotificationPackSize();
		int maxLimit = config.getFraudNotificationPackSizeLimit();
		try
		{
			return service.getNotificationPackIds(limit, maxLimit);
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}
}
