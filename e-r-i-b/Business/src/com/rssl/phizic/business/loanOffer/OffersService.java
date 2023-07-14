package com.rssl.phizic.business.loanOffer;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.loanCardOffer.DublicateLoanCardOfferException;
import com.rssl.phizic.business.loanCardOffer.LoanCardOffer;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;
import java.util.List;

/**
 * User: Moshenko
 * Date: 24.05.2011
 * Time: 12:24:52
 * сервис для работы с предодобренными предложениями
 */
public class OffersService
{
	private static SimpleService simpleService = new SimpleService();

	/**
	 * Добавить предодобренный кредит
	 * @param loanOffer
	 * @return
	 * @throws DublicateLoanOfferException, BusinessException
	 */
	public LoanOffer addOrUpdate(final LoanOffer loanOffer) throws DublicateLoanOfferException,BusinessException
	{
		try
		{
			return simpleService.addOrUpdateWithConstraintViolationException(loanOffer);
		}
		catch (ConstraintViolationException e)
		{
			throw new DublicateLoanOfferException();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Добавить предодобренную кредитную карту
	 * @param loanCardOffer
	 * @return
	 * @throws DublicateLoanOfferException, BusinessException
	 */
	public LoanCardOffer addOrUpdate(final LoanCardOffer loanCardOffer) throws BusinessException, DublicateLoanCardOfferException
	{
		try
		{
			return simpleService.addOrUpdateWithConstraintViolationException(loanCardOffer);
		}
		catch (ConstraintViolationException e)
		{
			throw new DublicateLoanCardOfferException();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение кредитного предложения по id
	 * @param id
	 * @return предложение кредита, либо null
	 * @throws BusinessException
	 */
	public LoanOffer findLoanOfferById(Long id) throws BusinessException
	{
		return simpleService.findById(LoanOffer.class, id);
	}

	/**
	 * Получение предложения по кредитной карте по id
	 * @param id
	 * @return предложение кредита, либо null
	 * @throws BusinessException
	 */
	public LoanCardOffer findLoanCardOfferById(Long id) throws BusinessException
	{
		return simpleService.findById(LoanCardOffer.class, id);
	}

	/**
	 * Удаляем все предодобренные предложения на крет.
	 * @throws BusinessException
	 */
	public void removeAllLoanOffer() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.loanOffer.LoanOffer.removeAll");
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


	/**
	 * удаление всех предложений по кредитным картам
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void removeAllLoanCardOffer() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.loanCardOffer.LoanCardOffer.removeAll");
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

	public boolean isOfferReceivingInProgress() throws BusinessException
	{
		return false;
	}

	public void markLoanOfferAsViewed(List<OfferId> loanOfferIds) throws BusinessException
	{
		for (OfferId loanOfferId : loanOfferIds)
		{
			LoanOffer loanOffer = simpleService.findById(LoanOffer.class, loanOfferId.getId());

			if (loanOffer == null)
				throw new BusinessException("Не найдено предложение по кредиту с id " + loanOfferId.getId());

			loanOffer.setIsViewed(true);
			try
			{
				addOrUpdate(loanOffer);
			}
			catch (DublicateLoanOfferException e)
			{
				throw new BusinessException(e);
			}
		}
	}

	public void markLoanCardOfferAsViewed(List<OfferId> loanCardOfferIds) throws BusinessException
	{
		for (OfferId loanCardOfferId : loanCardOfferIds)
		{
			LoanCardOffer loanCardOffer = simpleService.findById(LoanCardOffer.class, loanCardOfferId.getId());

			if (loanCardOffer == null)
				throw new BusinessException("Не найдено предложение по кредитной карте с id " + loanCardOfferId.getId());

			loanCardOffer.setIsViewed(true);
			loanCardOffer.setViewDate(Calendar.getInstance());
			try
			{
				addOrUpdate(loanCardOffer);
			}
			catch (DublicateLoanCardOfferException e)
			{
				throw new BusinessException(e);
			}
		}
	}

	/**
	 * Получить предложения клиента по кредитам.
	 * @param person - пользователь
	 * @param number - колличества предложений
	 * @param isViewed - true(показанные)/false(непоказанные)/null(все подряд)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<LoanOffer> getLoanOfferByPersonData(final Integer number,final ActivePerson person, final Boolean isViewed) throws BusinessException {
			try
			{
				return HibernateExecutor.getInstance().execute(new HibernateAction<List<LoanOffer>>() {
					public List<LoanOffer> run(Session session) throws Exception {
						Query query = session.getNamedQuery("com.rssl.phizic.business.loanOffer.LoanOffer.getLoanOfferByPersonData");
						String FIO = (person.getSurName() + person.getFirstName() + person.getPatrName()).replace(" ", "").toUpperCase();
						query.setParameter("FIO", FIO);
						query.setParameter("birthDay", person.getBirthDay());
						query.setParameterList("seriesAndNumbers", PersonHelper.getPersonSeriesAndNumbers(person));
						query.setParameter("tb", PersonHelper.getPersonTb(person));
						query.setParameter("isViewed",isViewed);
						query.setParameter("now", Calendar.getInstance());
						if (number == null)
							return query.list();
						else
							return query.setMaxResults(number).list();
					}
				}
				);
			}
			catch (Exception e) {
				throw new BusinessException(e);
			}
	}

	/**
	 * лолучение списка n первых  предложений клиента по кредитным картам.
	 * @param person - пользователь
	 * @param number - колличества предложений(null все подряд)
	 * @param isViewed - true(показанные)/false(непоказанные)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
    public List<LoanCardOffer> getLoanCardOfferByPersonData(final Integer number, final ActivePerson person, final Boolean isViewed) throws BusinessException {
        if (isViewed != null)
            try {
                return HibernateExecutor.getInstance().execute(new HibernateAction<List<LoanCardOffer>>() {
                    public List<LoanCardOffer> run(Session session) throws Exception {
                        Query query = session.getNamedQuery("com.rssl.phizic.business.loanCardOffer.LoanCardOffer.getViewedLoanCardOfferByPersonData");
	                    String FIO = (person.getSurName() + person.getFirstName() + person.getPatrName()).replace(" ", "").toUpperCase();
                        query.setParameter("FIO", FIO);
                        query.setParameter("birthDay", person.getBirthDay());
	                    query.setParameterList("seriesAndNumbers", PersonHelper.getPersonSeriesAndNumbers(person));
	                    query.setParameter("tb",PersonHelper.getPersonTb(person));
                        query.setParameter("isViewed", isViewed ? true : false);
                        if (number == null)
                            return query.list();
                        else
                            return query.setMaxResults(number).list();
                    }
                }
                );
            }
            catch (Exception e) {
                throw new BusinessException(e);
            }
        else {
            return getLoanCardOfferByPersonData(person,number);
        }
    }

	/**
	 * лолучение списка всех предложений клиента по картам.
	 * @param person - пользователь
	 * @param number -  кол-во возвращаемых записей. Если null то все.
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<LoanCardOffer> getLoanCardOfferByPersonData(final ActivePerson person,final Integer number) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<LoanCardOffer>>()
			{
				public List<LoanCardOffer> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.loanCardOffer.LoanCardOffer.getLoanCardOfferByPersonData");
					String FIO = (person.getSurName() + person.getFirstName() + person.getPatrName()).replace(" ", "").toUpperCase();
					query.setParameter("FIO", FIO);
					query.setParameter("birthDay", person.getBirthDay());
					query.setParameterList("seriesAndNumbers", PersonHelper.getPersonSeriesAndNumbers(person));
	                query.setParameter("tb",PersonHelper.getPersonTb(person));
                    List<LoanCardOffer> list;
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

	/**
	 * лолучение списка всех предложений клиента по картам.
	 * @param person - пользователь
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public LoanCardOffer getNewLoanCardOffer(final ActivePerson person) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<LoanCardOffer>()
			{
				public LoanCardOffer run(Session session) throws Exception
				{
					session.enableFilter("byTypeFilter").setParameter("offerType", LoanCardOfferType.newCard.getValue());

					Query query = session.getNamedQuery("com.rssl.phizic.business.loanCardOffer.LoanCardOffer.getLoanCardOfferByPersonData");
					String FIO = (person.getSurName() + person.getFirstName() + person.getPatrName()).replace(" ", "").toUpperCase();
					query.setParameter("FIO", FIO);
					query.setParameter("birthDay", person.getBirthDay());
					query.setParameterList("seriesAndNumbers", PersonHelper.getPersonSeriesAndNumbers(person));
					query.setParameter("tb", PersonHelper.getPersonTb(person));
					query.setMaxResults(1);
					return (LoanCardOffer) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение кредитного предложения по id
	 * @param offerId
	 * @return предложение кредита, либо null
	 * @throws BusinessException
	 */
	public LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException
	{
		return simpleService.findById(LoanOffer.class, offerId.getId());
	}

	/**
	 * Получение предложения по кредитной карте по id
	 * @param offerId
	 * @return предложение кредита, либо null
	 * @throws BusinessException
	 */
	public LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException
	{
		return simpleService.findById(LoanCardOffer.class, offerId.getId());
	}

	public void markLoanOfferAsUsed(final OfferId offerId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						String namedQuery = "com.rssl.phizic.business.loanOffer.LoanOffer.removeLoanOfferById";
						Query query = session.getNamedQuery(namedQuery);
						query.setParameter("id", offerId.getId());
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

	public void markLoanCardOfferAsUsed(final OfferId offerId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						String namedQuery = "com.rssl.phizic.business.loanCardOffer.LoanCardOffer.markLoanCardOfferAsUsed";
						Query query = session.getNamedQuery(namedQuery);
						query.setParameter("id", offerId.getId());
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

	public boolean isLoanOfferViewed(final Long offerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{

					Query query = session.getNamedQuery("com.rssl.phizic.business.loanOffer.LoanOffer.isLoanOfferViewed");
					query.setParameter("offerId", offerId);
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

	public boolean isLoanCardOfferViewed(final Long offerId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{

					Query query = session.getNamedQuery("com.rssl.phizic.business.loanOffer.LoanCardOffer.isLoanCardOfferViewed");
					query.setParameter("offerId", offerId);
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

	public void updateLoanCardOfferRegistrationDate(final Long offerId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						String namedQuery = "com.rssl.phizic.business.loanCardOffer.LoanCardOffer.updateLoanCardOfferRegistrationDate";
						Query query = session.getNamedQuery(namedQuery);
						query.setParameter("id", offerId);
						query.setParameter("now", Calendar.getInstance());
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


	public void updateLoanCardOfferTransitionDate(final Long offerId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						String namedQuery = "com.rssl.phizic.business.loanCardOffer.LoanCardOffer.updateLoanCardOfferTransitionDate";
						Query query = session.getNamedQuery(namedQuery);
						query.setParameter("id", offerId);
						query.setParameter("now", Calendar.getInstance());
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



