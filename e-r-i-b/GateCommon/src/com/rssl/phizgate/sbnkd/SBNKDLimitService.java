package com.rssl.phizgate.sbnkd;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.limits.LimitsConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardService;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizgate.sbnkd.limits.PhoneLimit;
import com.rssl.phizgate.sbnkd.limits.PhoneLimitType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author shapin
 * @ created 23.06.15
 * @ $Author$
 * @ $Revision$
 */
public class SBNKDLimitService
{
	final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * @return удовлетовряет ли документ лимитам на число карт и колчество документов в месяц
	 */
	public boolean checkLimit(IssueCardDocumentImpl issueCardDoc) throws GateException
	{
		LimitsConfig config = ConfigFactory.getConfig(LimitsConfig.class);
		int cardLimit = config.getLimitClaimeCardPerMonth();
		if (cardLimit != 0 && getLimit(issueCardDoc, PhoneLimitType.LIMIT_CARD_PER_MONTH) + issueCardDoc.getCardInfos().size() > cardLimit)
			return false;

		int claimLimit = config.getLimitClaimeFromOnePhoneNumber();
		if (claimLimit != 0 && getLimit(issueCardDoc, PhoneLimitType.LIMIT_CLAIME_PER_MONTH) + 1 > claimLimit)
			return false;

		return true;
	}


	public void updateLimit(final IssueCardDocumentImpl issueCardDoc, final long add, final PhoneLimitType limitType)
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Object[] objects = getLimitCount(issueCardDoc, session, limitType);
					if (objects == null && add > 0)
					{
						PhoneLimit phoneLimit = new PhoneLimit();
						phoneLimit.setCount(add);
						phoneLimit.setLastDate(Calendar.getInstance());
						phoneLimit.setPhone(issueCardDoc.getCardInfos().get(0).getMBCPhone());
						phoneLimit.setType(limitType);
						session.saveOrUpdate(phoneLimit);
					}
					else if (isCurrentMonth((Calendar)objects[1]))
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.limits.phone.incCounter");
						query.setParameter("typeLimit", limitType);
						query.setParameter("lastDate", Calendar.getInstance());
						query.setParameter("phone", issueCardDoc.getCardInfos().get(0).getMBCPhone());
						query.setParameter("add", add);
						query.setLockMode(limitType.toString(), LockMode.UPGRADE);
						query.executeUpdate();
					}
					else
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.limits.phone.clearCounter");
						query.setParameter("typeLimit", limitType);
						query.setParameter("lastDate", Calendar.getInstance());
						query.setParameter("phone", issueCardDoc.getCardInfos().get(0).getMBCPhone());
						query.setParameter("add", add > 0 ? add : 0);
						query.setLockMode(limitType.toString(), LockMode.UPGRADE);
						query.executeUpdate();
					}

					return null;
				}

			});

		}
		catch (Exception e)
		{
			log.error("Ошибка обновления лимита для СБНКД", e);
		}
	}



	public long getLimit(final IssueCardDocumentImpl issueCardDoc, final PhoneLimitType typeLimit)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					Object[] objects = getLimitCount(issueCardDoc, session, typeLimit);
					if (objects == null)
						return 0L;
					Calendar lastDate = (Calendar)objects[1];
					if (isCurrentMonth(lastDate))
						return (Long) objects[0];
					else
						return 0L;
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка получения лимита для СБНКД", e);
			return 0;
		}
	}

	private boolean isCurrentMonth(Calendar lastDate)
	{
		Calendar currentDate = Calendar.getInstance();
		return lastDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) && lastDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR);
	}

	public Object[] getLimitCount(IssueCardDocumentImpl issueCardDoc, Session session, PhoneLimitType typeLimit) throws GateException
	{
		Query query = session.getNamedQuery("com.rssl.phizic.business.limits.phone.getLimit");
		query.setParameter("typeLimit", typeLimit);
		query.setParameter("phone", issueCardDoc.getCardInfos().get(0).getMBCPhone());
		return (Object[])query.uniqueResult();
	}
}
