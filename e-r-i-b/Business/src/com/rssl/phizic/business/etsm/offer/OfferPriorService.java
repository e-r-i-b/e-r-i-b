package com.rssl.phizic.business.etsm.offer;

import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.EtsmConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Moshenko
 * @ created 24.06.15
 * @ $Author$
 * @ $Revision$
 * Сервис для работы с сущьностями оферт
 */
public class OfferPriorService
{
	private static final SimpleService SIMPLE_SERVICE = new SimpleService();

	/**
	 *
	 * @param appNum сквозной идентификатор оферт
	 * @param status статус оферты (ACTIVE, DELETED)
	 * @param lifeDate время жизни оферты
	 * @return результат
	 * @throws BusinessException
	 */
	public List<OfferOfficePrior> getOfficeOffers(final String appNum, final Date lifeDate, final String status) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName()).execute(new HibernateAction<List<OfferOfficePrior>>()
			{
				public List<OfferOfficePrior> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior.getOffers");
					query.setParameter("appNum", appNum);
					query.setParameter("lifeDate", lifeDate);
					query.setParameter("status", status);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param appNum сквозной идентификатор оферт
	 * @param status статус оферты (ACTIVE, DELETED)
	 * @param lifeDate время жизни оферты
	 * @return результат
	 * @throws BusinessException
	 */
	public List<OfferEribPrior> getEribOffers(final String appNum, final Calendar lifeDate, final String status) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<OfferEribPrior>>()
			{
				public List<OfferEribPrior> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.etsm.offer.OfferEribPrior.getOffers");
					query.setParameter("appNum", appNum);
					query.setParameter("lifeDate", lifeDate);
					query.setParameter("status", status);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param appNum сквозной идентификатор оферт
	 * @param offerDate лата загрузки оферт
	 * @throws BusinessException
	 */
	public void deleteOffers(final String appNum,final Date offerDate) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior.deleteOffers");
					query.setParameter("appNum", appNum);
					query.setParameter("offerDate", offerDate);
					query.executeUpdate();
					return  null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<OfferEribPrior> getOfferEribPriorByAppNumAndClaimId(String appNum,Long cliamId, String state) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(OfferEribPrior.class).
				add(Expression.eq("applicationNumber", appNum)).add(Expression.eq("claimId", cliamId)).add(Expression.eq("state", state));
		return SIMPLE_SERVICE.find(criteria);
	}


	public String addOfficeLoanClaim(OfficeLoanClaim claim)
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(OfficeLoanClaim.class).add(Expression.eq("applicationNumber", claim.getApplicationNumber()));
			OfficeLoanClaim oldClaim = SIMPLE_SERVICE.findSingle(criteria, ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName());
			if (oldClaim == null)
			{
				claim.setCreateDate(Calendar.getInstance());
				SIMPLE_SERVICE.add(claim, ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName());
			} else
			{
				oldClaim.setState(claim.getState());
				SIMPLE_SERVICE.update(oldClaim, ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName());
			}
			return "OK";
		}
		catch (BusinessException e)
		{
			return "ERROR";
		}
	}

	/**
	 * @param appNum уникальный идентификатор оферты
	 * @return заявка на кредит созданная вне ериб
	 */
	public OfficeLoanClaim getOfficeLoanClaim(String appNum) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(OfficeLoanClaim.class).add(Expression.eq("applicationNumber", appNum));
		return SIMPLE_SERVICE.findSingle(criteria, ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName());
	}

	/**
	 * @param offer оферта подтверждение
	 * @return оферта подтверждение
	 * @throws BusinessException
	 */
	public OfferConfirmed addOrUpdate(OfferConfirmed offer) throws BusinessException
	{
		return SIMPLE_SERVICE.addOrUpdate(offer);
	}

	/**
	 * @param offerList Оферты на удаление
	 * @throws BusinessException
	 */
	public void delete(List<OfferEribPrior> offerList) throws BusinessException
	{
		for (OfferEribPrior offer:offerList)
			offer.setState("DELETED");

		try
		{
			SIMPLE_SERVICE.addOrUpdateList(offerList, null);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

    public OfferOfficePrior disableOfficeOffer(String appNum) throws BusinessException
    {
        EtsmConfig etsmConfig = ConfigFactory.getConfig(EtsmConfig.class);
        DetachedCriteria criteria = DetachedCriteria.forClass(OfferOfficePrior.class).add(Expression.eq("applicationNumber", appNum));
        OfferOfficePrior offer = SIMPLE_SERVICE.findSingle(criteria,etsmConfig.getDbInstanceName());
        if (offer == null)
            return null;
        offer.setState("DELETED");
        SIMPLE_SERVICE.update(offer,etsmConfig.getDbInstanceName());
        return offer;

    }

    public OfferEribPrior disableEribOffer(String appNum, Long cliamId) throws BusinessException
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(OfferEribPrior.class).
                add(Expression.eq("applicationNumber", appNum)).add(Expression.eq("claimId", cliamId));
        OfferEribPrior offer = SIMPLE_SERVICE.findSingle(criteria);
        if (offer == null)
            return null;
        offer.setState("DELETED");
        SIMPLE_SERVICE.update(offer);
        return offer;

    }

	public List<OfficeLoanClaim> findOfficeLoanClaimsByFIODulBD(final String FIO, final String DUL, final Calendar birthday) throws BusinessException
	{
		final String clientFIO = FIO.replace(" ", "").toUpperCase();
		final String clientDOC = DUL.replace(" ", "");
		try
		{
			return HibernateExecutor.getInstance(ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName()).execute(new HibernateAction<List<OfficeLoanClaim>>()
			{
				public List<OfficeLoanClaim> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim.getOfficeLoanClaimsByFIODulBD");
					query.setParameter("FIO", clientFIO);
					query.setParameter("Doc", clientDOC);
					query.setCalendar("birthDay", birthday);
					Calendar createDate = Calendar.getInstance();
					createDate.add(Calendar.YEAR, -1);
					query.setCalendar("createDate", createDate);
					return  (List<OfficeLoanClaim>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение оферт, созданных в каналах отличных от УКО
	 * @param FIO - ФИО клиента
	 * @param DUL - серия и номер паспорта
	 * @param birthday - дата рождения клиента
	 * @return список оферт клиента за последние 30 дней
	 * @throws BusinessException
	 */
	public List<OfferOfficePrior> findOfferOfficePriorsByFIODulBD(final String FIO, final String DUL, final Calendar birthday) throws BusinessException
	{
		try
		{
			final String clientFIO = FIO.replace(" ", "").toUpperCase();
			final String clientDOC = DUL.replace(" ", "");
			return HibernateExecutor.getInstance(ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName()).execute(new HibernateAction<List<OfferOfficePrior>>()
			{
				public List<OfferOfficePrior> run(Session session) throws Exception
				{
					Calendar currDate = DateHelper.getCurrentDate();
					Calendar actualDate = DateHelper.addDays(currDate, -30);

					Query query = session.getNamedQuery("com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior.getOfferOfficePrior");
					query.setParameter("FIO", clientFIO);
					query.setParameter("Doc", clientDOC);
					query.setCalendar("birthDay", birthday);
					query.setCalendar("actualDate", actualDate);

					//noinspection unchecked
					return (List<OfferOfficePrior>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void updateOfferOfficePriorVisibleCounter(Long id) throws BusinessException
	{
		OfferOfficePrior officePrior = SIMPLE_SERVICE.findById(OfferOfficePrior.class, id, ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName());
		officePrior.setVisibilityCounter(officePrior.getVisibilityCounter() + 1);
		officePrior.setCounterUpdated(true);
		SIMPLE_SERVICE.update(officePrior, ConfigFactory.getConfig(EtsmConfig.class).getDbInstanceName());
	}
}
