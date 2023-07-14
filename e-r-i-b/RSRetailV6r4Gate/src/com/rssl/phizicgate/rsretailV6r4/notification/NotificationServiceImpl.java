package com.rssl.phizicgate.rsretailV6r4.notification;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.notification.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountNotification;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Omeliyanchuk
 * @ created 24.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class NotificationServiceImpl extends AbstractService implements NotificationService
{
	public NotificationServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Notification> getAllNotifications() throws GateException
	{
		return getNotifications(Notification.class, new HashMap<String, Object>());
	}

	public List<Notification> getNotifications(Class<? extends Notification> notificationClass, Map<String, Object> params) throws GateException
	{
		List<Notification> list = notificationQueryDispatcher(notificationClass, params);
		deleteNotifications(list);
		return list;
	}

	public void deleteGarbageNotification() throws GateException
	{
		try
		{
			RSRetailV6r4Executor.getInstance(true).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					NotificationConfig config = ConfigFactory.getConfig(NotificationConfig.class);
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r4.notification.GetGarbageNotifications");
					query.setParameter("cutDate", DateHelper.add(DateHelper.getCurrentDate().getTime(), 0, 0, -config.getDaysToKeepNotification()));

					//noinspection unchecked
					List<Notification> notifications = query.list();

					for (Notification notification : notifications)
					{
						session.delete(notification);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private List<Notification> notificationQueryDispatcher(final Class<? extends Notification> notificationType, final Map<String, Object> params) throws GateException
	{
		try
		{
			return RSRetailV6r4Executor.getInstance(true).execute(new HibernateAction<List<Notification>>(){
				public List<Notification> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(notificationType.getName()+".list");
					if (AccountNotification.class.isAssignableFrom(notificationType)){
						fillAccountParameters(query, params);
					}
					//noinspection unchecked
					List<Notification> notification = query.list();
					return (notification != null ? notification : new ArrayList<Notification>());
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void fillAccountParameters(Query query, Map<String, Object> params) throws GateException
	{
		Account account = (Account) params.get("account");
		if (account == null){
			throw new GateException("Ќе установлены необходимые парамерты дл€ получени€ оповещений по счету");
		}
		query.setParameter("accountNumber", account.getNumber());
	}

	private void deleteNotifications(final List<Notification> notifications) throws GateException
	{
		try
		{
			RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (Notification notification : notifications)
					{
						session.delete(notification);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
