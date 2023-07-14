package com.rssl.phizicgate.rsV55.notification;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.notification.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.gate.notification.AccountRestChangeNotification;
import com.rssl.phizic.gate.notification.AccountRestChangeLowNotification;
import com.rssl.phizic.gate.notification.StatusDocumentChangeNotification;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
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
	//private static StatusNotificationTransformer statusNotificationTransformer = new StatusNotificationTransformer();

    public NotificationServiceImpl(GateFactory factory)
    {
        super(factory);
    }

	public List<Notification> getAllNotifications() throws GateException
	{
		List<Notification> notifications = new ArrayList<Notification>();

		notifications.addAll(getNotifications(AccountRestChangeNotification.class, new HashMap<String, Object>()));
		notifications.addAll(getNotifications(AccountRestChangeLowNotification.class, new HashMap<String, Object>()));
		notifications.addAll(getNotifications(StatusDocumentChangeNotification.class, new HashMap<String, Object>()));

		return notifications;
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
            GateRSV55Executor.getInstance(true).execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
	                NotificationConfig config = ConfigFactory.getConfig(NotificationConfig.class);
	                Query query = session.getNamedQuery("com.rssl.phizicgate.rsV55.notification.GetGarbageNotifications");
			        query.setParameter("cutDate", DateHelper.add(DateHelper.getCurrentDate().getTime(),0,0,-config.getDaysToKeepNotification()));

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
			return GateRSV55Executor.getInstance(true).execute(new HibernateAction<List<Notification>>(){
				public List<Notification> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(notificationType.getName()+".list");

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


	private void deleteNotifications(final List<Notification> notifications) throws GateException
	{
		try
        {
            GateRSV55Executor.getInstance().execute(new HibernateAction<Void>()
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