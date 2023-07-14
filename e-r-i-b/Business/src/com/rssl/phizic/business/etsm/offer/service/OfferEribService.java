package com.rssl.phizic.business.etsm.offer.service;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.etsm.offer.OfferConfirmed;
import com.rssl.phizic.business.etsm.offer.OfferEribPrior;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * @author EgorovaA
 * @ created 25.06.15
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с офертами в БД ЕРИБ
 */
public class OfferEribService
{
	/**
	 * Получение оферт, созданных в ЕРИБ
	 * @param loginId - идентификатор логина клиента
	 * @return список оферт клиента за последние 30 дней
	 * @throws BusinessException
	 */
	public List<OfferEribPrior> getOfferEribPrior(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<OfferEribPrior>>()
			{
				public List<OfferEribPrior> run(Session session) throws Exception
				{
					Calendar currDate = DateHelper.getCurrentDate();
					Calendar actualDate = DateHelper.addDays(currDate, -30);

					Query query = session.getNamedQuery("com.rssl.phizic.business.etsm.offer.OfferEribPrior.getOfferEribPrior");
					query.setParameter("loginId", loginId);
					query.setCalendar("actualDate", actualDate);

					//noinspection unchecked
					return (List<OfferEribPrior>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение подтвержденной оферты по номеру заявки
	 * @param appNum - номер заявки
	 * @return подтвержденная оферта
	 * @throws BusinessException
	 */
	public OfferConfirmed getOfferConfirmed(final String appNum) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<OfferConfirmed>()
			{
				public OfferConfirmed run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.etsm.offer.OfferConfirmed");
					query.setParameter("appNum", appNum);

					return (OfferConfirmed) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск неподтвержденной оферты
	 * @param appNum - номер заявки на получение КД
	 * @param offerId - id записи оферты в БД
	 * @return
	 * @throws BusinessException
	 */
	public OfferEribPrior getOneOfferEribPrior(final String appNum, final Long offerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<OfferEribPrior>()
			{
				public OfferEribPrior run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.etsm.offer.OfferEribPrior.getOneOfferEribPrior");
					query.setParameter("appNum", appNum);
					query.setParameter("id", offerId);

					//noinspection unchecked
					return (OfferEribPrior) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
