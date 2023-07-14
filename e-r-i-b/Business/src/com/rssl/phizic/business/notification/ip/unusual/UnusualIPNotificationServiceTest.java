package com.rssl.phizic.business.notification.ip.unusual;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Тест сервиса работы с оповещениями о входе с нестандартного IP
 */

public class UnusualIPNotificationServiceTest extends BusinessTestCaseBase
{
	private static final SimpleService service = new SimpleService();
	private static final UnusualIPNotificationService unusualIPNotificationService = new UnusualIPNotificationService();

	/**
	 * тест получения списка оповещений
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void testGetList() throws BusinessException, BusinessLogicException
	{
		Long testLoginId = getTestLoginId();
		List<UnusualIPNotification> addedNotifications = new ArrayList<UnusualIPNotification>(5);
		UnusualIPNotification firstNotification = addNotification(testLoginId);
		addedNotifications.add(firstNotification);
		addedNotifications.add(addNotification(testLoginId));
		addedNotifications.add(addNotification(testLoginId));
		addedNotifications.add(addNotification(testLoginId));
		addedNotifications.add(addNotification(testLoginId));

		try
		{
			Calendar startDate = firstNotification.getDateCreated();
			Long startId = 0L;
			long maxAttemptsCount = 5;
			int batchSize = 2;

			long count = 0;

			for (List<UnusualIPNotification> notifications = getNotifications(startDate, startId, maxAttemptsCount, batchSize); !notifications.isEmpty(); notifications = getNotifications(startDate, startId, maxAttemptsCount, batchSize))
			{
				for (UnusualIPNotification notification : notifications)
				{
					count++;
					startId = notification.getId();
				}
			}

			assertEquals(count, addedNotifications.size());
		}
		finally
		{
			getSimpleService().removeList(addedNotifications);
		}
	}

	/**
	 * тест обновления оповещения
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void testUpdate() throws BusinessException, BusinessLogicException
	{
		UnusualIPNotification notification = addNotification(getTestLoginId());

		try
		{
			Calendar oldDate = notification.getDateCreated();
			Long oldLoginId = notification.getLoginId();
			long oldAttemptsCount = notification.getAttemptsCount();

			notification.setDateCreated(Calendar.getInstance());
			notification.setLoginId(oldLoginId + 1);
			notification.setAttemptsCount(oldAttemptsCount + 1);

			getUnusualIPNotificationService().update(notification);
			UnusualIPNotification updatedNotification = getById(notification.getId());

			assertEquals(oldDate.getTime().getTime(), updatedNotification.getDateCreated().getTime().getTime());
			assertEquals(oldLoginId, updatedNotification.getLoginId());
			assertTrue(oldAttemptsCount < updatedNotification.getAttemptsCount());
		}
		finally
		{
			getSimpleService().remove(notification);
		}
	}

	/**
	 * тест удаления оповещения
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void testRemove() throws BusinessException, BusinessLogicException
	{
		UnusualIPNotification notification = addNotification(getTestLoginId());
		getUnusualIPNotificationService().remove(notification.getId());
		assertNull(getById(notification.getId()));
	}

	/**
	 * тест итератора
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void testIterator() throws BusinessException, BusinessLogicException
	{
		Long testLoginId = getTestLoginId();
		List<UnusualIPNotification> addedNotifications = new ArrayList<UnusualIPNotification>(5);
		UnusualIPNotification firstNotification = addNotification(testLoginId);
		addedNotifications.add(firstNotification);
		addedNotifications.add(addNotification(testLoginId));
		addedNotifications.add(addNotification(testLoginId));
		addedNotifications.add(addNotification(testLoginId));
		addedNotifications.add(addNotification(testLoginId));

		try
		{
			Calendar startDate = firstNotification.getDateCreated();
			long maxAttemptsCount = 5;

			long count = 0;

			UnusualIPNotificationIterator iterator = getUnusualIPNotificationService().getNotificationsIterator(startDate, maxAttemptsCount);
			while (iterator.hasNext())
			{
				iterator.next();
				iterator.remove();
				count++;
			}
			assertEquals(count, addedNotifications.size());
		}
		finally
		{
			for (UnusualIPNotification addedNotification : addedNotifications)
			{
				assertNull(getById(addedNotification.getId()));
			}
		}
	}

	/**
	 * тест работы сервиса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void testUnusualIPNotificationService() throws BusinessException, BusinessLogicException
	{
		testGetList();
		testUpdate();
		testRemove();
		testIterator();
	}

	private UnusualIPNotificationService getUnusualIPNotificationService()
	{
		return unusualIPNotificationService;
	}

	private SimpleService getSimpleService()
	{
		return service;
	}

	private UnusualIPNotification addNotification(Long loginId) throws BusinessException
	{
		UnusualIPNotification notification = new UnusualIPNotification();
		notification.setDateCreated(Calendar.getInstance());
		notification.setLoginId(loginId);
		notification.setAttemptsCount(0);
		return getSimpleService().add(notification);
	}

	private List<UnusualIPNotification> getNotifications(Calendar startDate, Long startId, long maxAttemptsCount, int batchSize) throws BusinessException
	{
		return getUnusualIPNotificationService().getNotifications(startDate, startId, maxAttemptsCount, batchSize);
	}

	private Long getTestLoginId()
	{
		return 3L;
	}

	private UnusualIPNotification getById(Long notificationId) throws BusinessException
	{
		return getSimpleService().findById(UnusualIPNotification.class, notificationId);
	}
}
