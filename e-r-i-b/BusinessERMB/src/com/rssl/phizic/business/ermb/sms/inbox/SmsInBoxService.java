package com.rssl.phizic.business.ermb.sms.inbox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 07.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для удаления устаревших и сохранения новых входящих СМС
 */
public class SmsInBoxService
{
	void save(final SmsInBox smsInBox) throws DublicateSmsInBoxException, BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					session.save(smsInBox);
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new DublicateSmsInBoxException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти непросроченный дубликат смс (телефон и текст).
	 * Если найден просроченный, то сразу же удаляется в текущей сессии.
	 * @param smsInBox текущая смс
	 * @return дубликат или null
	 * @throws BusinessException
	 */
	SmsInBox findInbox(final SmsInBox smsInBox) throws BusinessException
	{
		final Calendar expiredDate = getExpiredDate();
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<SmsInBox>()
			{
				public SmsInBox run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.sms.inbox.SmsInBox.findDuplicate");
					query.setParameter("phone", smsInBox.getPhone());
					query.setParameter("text", smsInBox.getText());
					SmsInBox sms = (SmsInBox) query.uniqueResult();

					if (sms != null && sms.getTime().before(expiredDate))
					{
						session.delete(sms);
						return null;
					}

					return sms;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить просроченные смс
	 * @throws BusinessException
	 */
	public void removeExpired() throws BusinessException
	{
		final Calendar expirationDate = getExpiredDate();
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.sms.inbox.SmsInBox.deleteByInterval");
					query.setParameter("time", expirationDate);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private Calendar getExpiredDate()
	{
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		int expiredMinutes = smsConfig.getDublicateExpireInterval();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -expiredMinutes);
		return calendar;
	}
}
