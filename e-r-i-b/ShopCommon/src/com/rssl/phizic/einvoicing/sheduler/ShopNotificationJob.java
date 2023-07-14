package com.rssl.phizic.einvoicing.sheduler;

import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ShopListenerConfig;
import com.rssl.phizic.config.invoicing.InvoicingConfig;
import com.rssl.phizic.einvoicing.ShopOrderServiceImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.InvoiceGateBackService;
import com.rssl.phizic.gate.einvoicing.NotificationStatus;
import com.rssl.phizic.gate.einvoicing.ShopNotification;
import com.rssl.phizic.gate.einvoicing.ShopProvider;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.shopclient.ShopInfoServiceWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.*;

/**
 * ���� ��� ���������� invoice-������ �� ��������� �������� �������
 * @author gladishev
 * @ created 28.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class ShopNotificationJob extends BaseJob implements StatefulJob
{
	private static final String ERROR_PREFIX = "������ �������� ���������� �� �������� �������: ";
	private static final String UPDATE_ERROR_PREFIX = "������ ��� ���������� ����������, uuid=%s, state=%s";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private ShopOrderServiceImpl shopService = new ShopOrderServiceImpl(null);

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		List<ShopNotification> notifications = null;
		try
		{
			Calendar startDate = Calendar.getInstance();
			startDate.add(Calendar.HOUR_OF_DAY, -ConfigFactory.getConfig(InvoicingConfig.class).getNotificationsDiffHours());
			notifications = shopService.getNotifications(new Period(startDate, Calendar.getInstance()));
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
		}

		if (CollectionUtils.isEmpty(notifications))
			return;

		//������� ��������� ���<��� ��, ���<UUID ������, ����������>>
		Map<String, Map<String, ShopNotification>> notificationsMap = new HashMap<String, Map<String, ShopNotification>>();

		Collection<ShopNotification> result = new ArrayList<ShopNotification>();
		for (ShopNotification notification : notifications)
		{
			String receiverCode = notification.getReceiverCode();
			if (!notificationsMap.containsKey(receiverCode))
			{
				Map<String, ShopNotification> map = new HashMap<String, ShopNotification>();
				map.put(notification.getUuid(), notification);
				notificationsMap.put(receiverCode, map);
			}
			else
			{

				Map<String, ShopNotification> tmp = notificationsMap.get(receiverCode);
				ShopNotification notif = tmp.get(notification.getUuid());
				if (notif != null)
				{
					//� ����� ������� �� ��������� ������� ����� ���������� ������ ���� ���������� � ������� uuid
					//��� ���� �� �� ����� ���� �������� ��������� ������� � ����� � ��� �� UUID,
					//�������� 2 ������ �� ������ �� ��������� ERROR � EXECUTED � ������ �� �������, ���������
					//notification#getExternalId � ������ � �����, ��������� ����� ����������
					//������� ����� ���������� ���������, ���� externalId � ��� ������� � ����(notif) ���������� � ������� (notification)
					//����������, �� ��������� � ������� ������� ���������� (����� ���������� � ��������� �������� �����), �����
					//������ ��������� �� ���� �������� ���������� � � notificationsMap ������ �������� ���������� � ������� ����� �������� ���������� (BUG086217)

					if (!notif.getExternalId().equals(notification.getExternalId()))
						continue;

					if (notification.getDate().after(notif.getDate()))
					{
						tmp.put(notification.getUuid(), notification);
						//������ ���������� �� ���������, ������ ��� ������
						notif.setNotifStatus(NotificationStatus.CANCELED);
						result.add(notif);
					}
					else
					{
						//������ ���������� �� ���������, ������ ��� ������
						notification.setNotifStatus(NotificationStatus.CANCELED);
						result.add(notification);
					}
				}
				else
				{
					tmp.put(notification.getUuid(), notification);
				}
			}
		}

		for (String receiverCode : notificationsMap.keySet())
		{
			try
			{
				ShopProvider provider = GateSingleton.getFactory().service(InvoiceGateBackService.class).getActiveProvider(receiverCode);
				if (provider == null)
				{
					log.error(ERROR_PREFIX + "�� ������ ��������� code=" + receiverCode);
					continue;
				}

				ShopInfoServiceWrapper shopInfoService = new ShopInfoServiceWrapper(provider);
				result.addAll(shopInfoService.notify(notificationsMap.get(receiverCode).values()));
			}
			catch (Exception e)
			{
				log.error(ERROR_PREFIX + "code " + receiverCode, e);
			}
		}

		for  (ShopNotification notification : result)
		{
			try
			{
				shopService.updateNotification(notification);
			}
			catch (GateException e)
			{
				log.error(String.format(UPDATE_ERROR_PREFIX, notification.getUuid(), notification.getState()), e);
			}
		}
	}
}
