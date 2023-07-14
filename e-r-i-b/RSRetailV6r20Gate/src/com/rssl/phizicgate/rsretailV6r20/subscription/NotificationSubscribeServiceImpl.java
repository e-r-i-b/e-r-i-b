package com.rssl.phizicgate.rsretailV6r20.subscription;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.common.types.CurrencyISOComparator;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;
import com.rssl.phizicgate.rsretailV6r20.dictionaries.currencies.CurrencyServiceImpl;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Omeliyanchuk
 * @ created 08.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class NotificationSubscribeServiceImpl extends AbstractService implements NotificationSubscribeService
{
	private static String ACCOUNT_PREFIX = "DA";
	private static int ACCOUNT_TYPE = 202;

	public NotificationSubscribeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void subscribeAccount(final Account account) throws GateException
	{
		try
		{
			RSRetailV6r20Executor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					SubscriptionObject oldObject = findAccountSubscription(account);
					if (oldObject != null)
					{
						unsubscribeAccount(account);
					}
					SubscriptionObject newObject = createObject(account);
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r20.subscription.FindMaxSubscriptionNumber");
					Object value = query.uniqueResult();
					newObject.setReferenc(value == null ? 0 : (Long) value + 1);
					session.save(newObject);
					session.flush();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void unsubscribeAccount(final Account account) throws GateException
	{
		try
		{
			RSRetailV6r20Executor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					SubscriptionObject object = findAccountSubscription(account);
					if (object != null) //подписывались
					{
						session.delete(object);
						session.flush();
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

	private SubscriptionObject findAccountSubscription(final Account account) throws GateException
	{
		try
		{
			return RSRetailV6r20Executor.getInstance(true).execute(new HibernateAction<SubscriptionObject>()
			{
				public SubscriptionObject run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r20.subscription.FindAccountSubscription");
					query.setParameter("id", NotificationSubscribeServiceImpl.ACCOUNT_PREFIX + account.getNumber());

					return (SubscriptionObject) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private SubscriptionObject createObject(Account account) throws GateLogicException, GateException
	{
		CurrencyServiceImpl currencyService = new CurrencyServiceImpl(getFactory());

		SubscriptionObject object = new SubscriptionObject();
		object.setId(NotificationSubscribeServiceImpl.ACCOUNT_PREFIX + account.getNumber());
		Office office = account.getOffice();
		object.setBranch(Long.valueOf(office.getSynchKey().toString()));
		object.setObjectType(NotificationSubscribeServiceImpl.ACCOUNT_TYPE);
		if (CurrencyISOComparator.compare(currencyService.getNationalCurrency(), account.getCurrency()))
		object.setIsCur(0L);
		else
		object.setIsCur(1L);
		object.setNotificationTypes(createNotification(object));
		return object;
	}

	private Set<SubscriptionNotification> createNotification(SubscriptionObject object)
	{
		Set<SubscriptionNotification> notificationSet = new HashSet<SubscriptionNotification>();
		for (int i = 1; i < 6; i++)
		{
			SubscriptionNotification subsNot = new SubscriptionNotification();
			subsNot.setBranch(object.getBranch());
			subsNot.setIsCur(object.getIsCur());
			subsNot.setObjectType(object.getObjectType());
			subsNot.setNotifyType(i);
			notificationSet.add(subsNot);
		}
		return notificationSet;
	}
}
