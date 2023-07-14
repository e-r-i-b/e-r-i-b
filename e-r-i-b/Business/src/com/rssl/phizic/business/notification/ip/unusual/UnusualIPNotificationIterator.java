package com.rssl.phizic.business.notification.ip.unusual;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author akrenev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Итератор оповещений о входе с нестандартного IP
 */

public class UnusualIPNotificationIterator implements Iterator<UnusualIPNotification>
{
	private static final UnusualIPNotificationService service = new UnusualIPNotificationService();
	private static final int BATCH_SIZE = 1000;

	private final Calendar startDate;
	private final long maxAttemptsCount;
	private Long currentId;
	private int currentIndex;
	private boolean nextIsCalled = false;

	private List<UnusualIPNotification> notifications;

	UnusualIPNotificationIterator(Calendar startDate, long maxAttemptsCount)
	{
		this.startDate = startDate;
		this.maxAttemptsCount = maxAttemptsCount;
		currentId = -1L;
		currentIndex = -1;
	}

	public boolean hasNext()
	{
		prepareCollection();
		return notifications.size() > currentIndex;
	}

	public UnusualIPNotification next()
	{
		prepareCollection();
		UnusualIPNotification notification = notifications.get(currentIndex++);
		currentId = notification.getId();
		notification.setAttemptsCount(notification.getAttemptsCount() + 1);
		try
		{
			service.update(notification);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка обновления оповещения клиента о входе с нестандартного IP (id = " + currentId + ").", e);
		}
		nextIsCalled = true;
		return notification;
	}

	public void remove()
	{
		if (!nextIsCalled)
			throw new IllegalStateException();

		nextIsCalled = false;
		try
		{
			service.remove(currentId);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка удаления оповещения клиента о входе с нестандартного IP (id = " + currentId + ").", e);
		}
	}

	private void prepareCollection()
	{
		if (CollectionUtils.isNotEmpty(notifications) && notifications.size() > currentIndex && BATCH_SIZE >= currentIndex)
			return;

		currentIndex = 0;
		try
		{
			notifications = service.getNotifications(startDate, currentId, maxAttemptsCount, BATCH_SIZE);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка получения списка оповещений клиента о входе с нестандартного IP.", e);
		}
	}
}
