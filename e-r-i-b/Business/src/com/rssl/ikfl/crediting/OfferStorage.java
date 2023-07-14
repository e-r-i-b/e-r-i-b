package com.rssl.ikfl.crediting;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */
public class OfferStorage
{
	private final Counter OFFER_ID_COUNTER = Counter.createSimpleCounter("CRM_OFFERS");

	private final SimpleService simpleService = new SimpleService();

	private final CounterService counterService = new CounterService();

	/**
	 * Получить запись из таблицы входов по указанному клиенту
	 * @param person - клиент
	 * @return запись из таблицы входов или null, если по клиенту такой записи нет
	 */
	OfferLogin loadOfferLogin(final ActivePerson person, final String tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<OfferLogin>()
			{
				public OfferLogin run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.Offer.findOfferLogin");
					query.setParameter("surname", person.getSurName());
					query.setParameter("firstname", person.getFirstName());
					query.setParameter("patrname", person.getPatrName());
					query.setParameterList("document", PersonHelper.getPersonSeriesAndNumbers(person));
					query.setParameter("birthday", person.getBirthDay());
					query.setParameter("tb", tb);
					return (OfferLogin) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить запись из таблицы входов по UID запроса
	 * @param rqUID - UID - запроса
	 * @return запись из таблицы входов или null, если по UID такой записи нет
	 * @throws BusinessException
	 */
	OfferLogin loadOfferLoginByUID(String rqUID) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(OfferLogin.class);
		criteria.add(Expression.eq("lastRqUID", rqUID));
		return simpleService.findSingle(criteria);
	}

	void saveOfferLogin(OfferLogin offerLogin) throws BusinessException
	{
		simpleService.addOrUpdate(offerLogin);
	}

	Long getNextOfferID() throws CounterException
	{
		return counterService.getNext(OFFER_ID_COUNTER);
	}

	void saveOffers(List<Offer> offers) throws BusinessException
	{
		if (!offers.isEmpty())
			simpleService.addList(offers);
	}

	void saveOfferConditions(List<OfferCondition> offerConditions) throws BusinessException
	{
		if (!offerConditions.isEmpty())
			simpleService.addList(offerConditions);
	}

	public void saveTopUps(List<OfferTopUp> topUps) throws BusinessException
	{
		if (topUps.isEmpty())
		{
			return;
		}

		simpleService.addList(topUps);
	}

	/**
	 * Удалить все предложения клиента
	 * @param person - клиент
	 */
	void deleteClientOffers(final ActivePerson person) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.ikfl.crediting.DeleteClientOffers");
			query.setParameter("surname", person.getSurName());
			query.setParameter("firstname", person.getFirstName());
			query.setParameter("patrname", person.getPatrName());
			query.setParameterList("document", PersonHelper.getPersonSeriesAndNumbers(person));
			query.setParameter("birthday", person.getBirthDay());
			query.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Сбой на удалении предложений по клиенту =" + person.getFullName(), e);
		}
	}

	public Feedback loadOfferFeedback(final Offer offer, final String feedbackType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Feedback>()
			{
				public Feedback run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.LoadFeedback");
					query.setParameter("surname", offer.getSurName());
					query.setParameter("firstname", offer.getFirstName());
					query.setParameter("patrname", offer.getPatrName());
					query.setParameter("feedbackType", feedbackType);
					List<String> seriesAndNumbers = new ArrayList<String>();
					StringBuilder sb = new StringBuilder();
					String series = offer.getDocSeries();
					if (!StringHelper.isEmpty(series))
					{
						series = StringUtils.remove(series, ' ');
						sb.append(series.toUpperCase());
					}
					String number = offer.getDocNumber();
					if (!StringHelper.isEmpty(number))
					{
						number = StringUtils.remove(number,' ');
						sb.append(number.toUpperCase());
					}
					seriesAndNumbers.add(sb.toString());
					query.setParameterList("document", seriesAndNumbers);
					query.setParameter("birthday", offer.getBirthDay());
					query.setString("sourceCode", offer.sourceCode);
					query.setString("campaignMemberId", offer.campaignMemberId);
					return (Feedback) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Сбой на загрузке отклика по предложению ID=" + offer.id, e);
		}
	}

	public Feedback findOfferFeedback(final Long offerId, final String feedbackType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Feedback>()
			{
				public Feedback run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.FindOfferFeedback");
					query.setLong("offerId", offerId);
					query.setString("feedbackType", feedbackType);
					return (Feedback) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Сбой на загрузке отклика по предложению ID=" + offerId, e);
		}
	}

	public void saveFeedback(Feedback feedback) throws BusinessException
	{
		simpleService.addOrUpdate(feedback);
	}
	public void remove(Feedback feedback) throws BusinessException
	{
		simpleService.remove(feedback);
	}

	public void saveOffersFeedbackList(List<Feedback> feedbackList) throws BusinessException
	{
		if (!feedbackList.isEmpty())
		{
			for (final Feedback feedback : feedbackList)
			{
				try
				{
					HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
					{
						public Void run(Session session) throws Exception
						{
							session.save(feedback);
							return null;
						}
					}
					);
				}
				catch (ConstraintViolationException ignored)
				{
				}
				catch(Exception e)
				{
					throw new BusinessException(e);
				}
			}
		}
	}

	public List<Offer> getLoanOfferByPersonData(final Integer number, final ActivePerson person, final Boolean isViewed) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Offer>>()
			{
				public List<Offer> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.Offer.getLoanOfferByPersonData");
					query.setParameter("surname", person.getSurName());
					query.setParameter("firstname", person.getFirstName());
					query.setParameter("patrname", person.getPatrName());
					query.setParameterList("document", PersonHelper.getPersonSeriesAndNumbers(person));
					query.setParameter("birthday", person.getBirthDay());
	                query.setParameter("now", Calendar.getInstance());
                    List<Offer> list;
                    if (number == null)
                       list =  query.list();
				    else
                       list =  query.setMaxResults(number).list();
                    return list;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void markPersonCrmOffersAsNonActive(final OfferLogin person) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.ikfl.crediting.Offer.markPersonCrmOffersAsNonActive");
			query.setParameter("surname", person.surName);
			query.setParameter("firstname", person.firstName);
			query.setParameter("patrname", person.patrName);
			StringBuilder sb = new StringBuilder();
			String series = person.docSeries;
			if (!StringHelper.isEmpty(series))
			{
				series = StringUtils.remove(series,' ');
				sb.append(series.toUpperCase());
			}

			String number = person.docNumber;
			if (!StringHelper.isEmpty(number))
			{
				number = StringUtils.remove(number,' ');
				sb.append(number.toUpperCase());
			}
			query.setParameter("document", sb.toString());
			query.setParameter("birthday", person.birthDay);
			query.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Сбой на удалении предложений по клиенту ="+person.surName+" " + person.firstName+" "+person.patrName, e);
		}
	}

	public List<Offer> getLoanCardOfferByPersonData(final Integer number, final ActivePerson person, final Boolean isViewed) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Offer>>()
			{
				public List<Offer> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.Offer.getLoanCardOfferByPersonData");
					query.setParameter("surname", person.getSurName());
					query.setParameter("firstname", person.getFirstName());
					query.setParameter("patrname", person.getPatrName());
					query.setParameterList("document", PersonHelper.getPersonSeriesAndNumbers(person));
					query.setParameter("birthday", person.getBirthDay());
	                query.setParameter("now", Calendar.getInstance());
                    List<Offer> list;
                    if (number == null)
                       list =  query.list();
				    else
                       list =  query.setMaxResults(number).list();
                    return list;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Offer findOfferById(Long offerId) throws BusinessException
	{
		return simpleService.findById(Offer.class, offerId);
	}

	public Offer findLoanOfferById(Long offerId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Offer.class);
		criteria.add(Expression.eq("id", offerId));
		criteria.add(Expression.eq("productType", OfferProductType.CONSUMER_CREDIT));
		return simpleService.findSingle(criteria);
	}

	public Offer findCardLoanOfferById(Long offerId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Offer.class);
		criteria.add(Expression.eq("id", offerId));
		criteria.add(Expression.eq("productType", OfferProductType.CREDIT_CARD));
		return simpleService.findSingle(criteria);
	}

	public List<OfferCondition> getOfferConditionsByOfferId(final Long offerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<OfferCondition>>()
			{
				public List<OfferCondition> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.Offer.getOfferConditionsByOfferId");
					query.setParameter("offerId", offerId);
                    return  query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<OfferCondition> getOfferConditionsByLoginId(final ActivePerson person) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<OfferCondition>>()
			{
				public List<OfferCondition> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.Offer.getOfferConditionsByLoginId");
					query.setParameter("surname", person.getSurName());
					query.setParameter("firstname", person.getFirstName());
					query.setParameter("patrname", person.getPatrName());
					query.setParameterList("document", PersonHelper.getPersonSeriesAndNumbers(person));
					query.setParameter("birthday", person.getBirthDay());
                    return  query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public boolean isOfferReceivingInProgress(final ActivePerson person) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{

					Query query = session.getNamedQuery("com.rssl.ikfl.crediting.Offer.isOfferReceivingInProgress");
					query.setParameter("surname", person.getSurName());
					query.setParameter("firstname", person.getFirstName());
					query.setParameter("patrname", person.getPatrName());
					query.setParameterList("document", PersonHelper.getPersonSeriesAndNumbers(person));
					query.setParameter("birthday", person.getBirthDay());
					query.setMaxResults(1);
					return query.uniqueResult() != null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void markOfferAsUsed(final Long offerId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						String namedQuery = "com.rssl.ikfl.crediting.Offer.markOfferAsUsed";
						Query query = session.getNamedQuery(namedQuery);
						query.setParameter("id", offerId);
						query.executeUpdate();

						return null;
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
