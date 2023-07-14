package com.rssl.phizic.business.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract;
import com.rssl.phizic.business.dictionaries.finances.DiagramAbstract;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDayExtractByOperationDescription;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Erkin
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ���������� �� �����
 */
@SuppressWarnings({"OverlyComplexAnonymousInnerClass", "MethodWithTooManyParameters"})
public class CardOperationService
{
	private static final String QUERY_PREFIX = CardOperationService.class.getName() + ".";

	private static final int BATCH_SIZE = 1000;

	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ���������� ��������� �������� �� ID
	 * @param id - ID
	 * @return ��������� �������� ��� null, ���� �� �������
	 */
	public CardOperation findById(long id) throws BusinessException
	{
		return simpleService.findById(CardOperation.class, id);
	}

	/**
	 * ��������� ��� ��������� ��������� �������� � ��
	 * @param cardOperation - ��������� ��������
	 */
	public void addOrUpdate(CardOperation cardOperation) throws BusinessException
	{
		simpleService.addOrUpdate(cardOperation);
	}

	/**
	 * ��������� ��� ��������� ���. ���� ��������� �������� � ��
	 * @param cardOperationExtendedFields - ���. ���� ��������� ��������
	 */
	public void addOrUpdate(CardOperationExtendedFields cardOperationExtendedFields) throws BusinessException
	{
		simpleService.addOrUpdate(cardOperationExtendedFields);
	}

	/**
	 * ������� ��������� �������� �� ��
	 * @param cardOperation - ��������� ��������
	 */
	public void remove(CardOperation cardOperation) throws BusinessException
	{
		simpleService.remove(cardOperation);
	}

	/**
	 * ��������� ����� ��������� �������� � ��
	 * @param operations - ������ ����� ��������� ��������
	 */
	public void addOperations(final List<Pair<CardOperation, CardOperationExtendedFields>> operations) throws BusinessException
	{
		if (operations.isEmpty())
			return;

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					int count = 0;
					for (Pair<CardOperation, CardOperationExtendedFields> operation : operations)
					{
						CardOperation cardOperation = operation.getFirst();
						session.saveOrUpdate(cardOperation);
						if (operation.getSecond() != null)
						{
							CardOperationExtendedFields cardOperationExtendedFields = operation.getSecond();
							cardOperationExtendedFields.setCardOperationId(cardOperation.getId());
							session.saveOrUpdate(cardOperationExtendedFields);
						}
						count++;
						// ���� � ������ ���������� �������� ���������� ����� ������, ���������� �� � ��
						if (count >= BATCH_SIZE)
						{
							session.flush();
							session.clear();
							count = 0;
						}
					}
					session.flush();
					session.clear();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("�� ������� ��������� ������ ����� ��������� ��������", e);
		}
	}

	/**
	 * ����� ��� ��������� ����������� �������(����� ��������) �� ���������
	 * @param fromDate ���� �
	 * @param toDate ���� ��
	 * @param cards ������ ����
	 * @param cash �������� �� �������� � ���������
	 * @param income true ������ �������� �������� � ��������� ������ ���������
	 * @param showTransfer - ������� ���������� �� ��������
	 * @param login �����
	 * @param showCashPayments - �������� ����� ���������
	 * @param excludeCategories ����������� ��������� ��������
	 * @return ����������� �������(����� ��������) �� ����������
	 */
	public List<CardOperationCategoryGraphAbstract> getCategoriesGraphData (final Calendar fromDate, final Calendar toDate, final List<String> cards, final Boolean cash, final Boolean income,
                                                final Boolean showTransfer, final Login login, final String[] excludeCategories, final boolean showCashPayments, final String country) throws BusinessException
	{
	    if (!showCashPayments && cards.isEmpty())
		    return Collections.emptyList();

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperationCategoryGraphAbstract>>()
			{
				public List<CardOperationCategoryGraphAbstract> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCategoriesGraph");
					query.setCalendar("fromDate", fromDate);
					query.setCalendar("toDate", toDate);
					query.setParameter("paymentType", showCashPayments ? OperationType.OTHER.name() : null);
					query.setBoolean("cash", cash);
					query.setBoolean("showTransfer", showTransfer);
					query.setParameter("loginId", login.getId());
					query.setParameter("country", country);

					if (income == null)
					{
						query.setSerializable("income", income);
					}
					else
					{
						query.setBoolean("income", income);
					}

					if (excludeCategories == null)
					{
						query.setBoolean("excludeCategoriesEmpty", true);
						query.setParameter("excludeCategories", "");
					}
					else
					{
						query.setBoolean("excludeCategoriesEmpty", false);
						query.setParameterList("excludeCategories", excludeCategories);
					}

					if (cards.isEmpty())
					{
						query.setBoolean("cardNumbersEmpty", true);
						query.setParameter("cardNumbers", "");
					}
					else
					{
						query.setBoolean("cardNumbersEmpty", true);
						query.setParameterList("cardNumbers", cards);
					}

					//noinspection unchecked
					return (List<CardOperationCategoryGraphAbstract>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ��������� ������ �� ��������� �� ������
	 * @param fromDate - ���� ��
	 * @param toDate - ���� ��
	 * @param cards - ������ ����
	 * @param login - �����
	 * @param categoryId - ������������� ���������
	 * @return ����������� �������(����� ��������) �� ����������
	 */
	public Double getCategorySumBudget (final Calendar fromDate, final Calendar toDate,
                                        final List<String> cards, final Login login, final Long categoryId) throws BusinessException
	{
	   	try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Double>()
			{
				public Double run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCategorySumBudget");
					query.setCalendar("fromDate", fromDate);
					query.setCalendar("toDate", toDate);
					query.setParameterList("cardNumbers", cards);
					query.setParameter("loginId", login.getId());
					query.setParameter("categoryId", categoryId);
					Double res = (Double)query.uniqueResult();
					return res == null ? 0 : res;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��� ��������� ����������� ������� (����� � ������)
	 * @param fromDate ���� �
	 * @param toDate ���� ��
	 * @param cards ������ ����
	 * @param cash �������� � ���������
	 * @param login �����
	 * @return ������ ��������
	 */
	public List<CardOperationAbstract> getOperationsGraphData (final Calendar fromDate, final Calendar toDate,
	                        final List<String> cards, final Boolean cash, final Login login) throws BusinessException
	{
	    if (CollectionUtils.isEmpty(cards))
		    return Collections.emptyList();

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperationAbstract>>()
			{
				public List<CardOperationAbstract> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getOperationsGraph");
					query.setCalendar("fromDate", fromDate);
					query.setCalendar("toDate", toDate);
					query.setParameterList("cardNumbers", cards);
					query.setBoolean("cash", cash);
					query.setParameter("loginId", login.getId());
					//noinspection unchecked
					return (List<CardOperationAbstract>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	 /** ����� ��� ��������� ����������� ������� (�������� � �����������)
	 * @param fromDate ���� �
	 * @param toDate ���� ��
	 * @param cards ������ ����
	 * @param cash �������� � ���������
	  * @param login �����
	 * @return ������ ��������
	 */
	public List<CashAndCashlessOperationsGraphAbstract> getCashOperationsGraphData (final Calendar fromDate, final Calendar toDate,
	                        final List<String> cards, final Boolean cash, final Login login) throws BusinessException
	{
	    if (CollectionUtils.isEmpty(cards))
		    return Collections.emptyList();

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CashAndCashlessOperationsGraphAbstract>>()
			{
				public List<CashAndCashlessOperationsGraphAbstract> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCashOperationsGraph");
					query.setCalendar("fromDate", fromDate);
					query.setCalendar("toDate", toDate);
					query.setParameterList("cardNumbers", cards);
					query.setBoolean("cash", cash);
					query.setParameter("loginId", login.getId());
					//noinspection unchecked
					return (List<CashAndCashlessOperationsGraphAbstract>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ��������� ����� � ������������
	 * @param income - ������� ���������/�������� ��������
	 * @param login - �����
	 * @return ������ ��������� ��������
	 */
	public List<CardOperationCategory> getCardOperationCategories (final Boolean income, final Login login) throws BusinessException
	{
		try
		{

			BeanQuery query = MultiLocaleQueryHelper.getQuery(QUERY_PREFIX + "getCardOperationCategories");
			query.setParameter("income", income);
			query.setParameter("loginId", login.getId());
			//noinspection unchecked
			return query.executeListInternal();


		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ��������� ����� � ������������ (����������� ���������� ��������� � ������������� �������� ��������� ��� ���������)
	 * @param income - ������� ���������/�������� ��������
	 * @param showTransfer - ������� �������� �� ��������� ��� ���������
	 * @param login - �����
	 * @return ������ ��������� ��������
	 */
	public List<CardOperationCategory> getCardOperationCategories (final Boolean income, final Boolean showTransfer, final Login login) throws BusinessException
	{
		try
		{

			BeanQuery query = MultiLocaleQueryHelper.getQuery(QUERY_PREFIX + "getCardOperationCategoriesByShowTransfers");
			query.setParameter("income", income);
			query.setParameter("showTransfer", showTransfer);
			query.setParameter("loginId", login.getId());
			//noinspection unchecked
			return query.executeListInternal();

		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ������������� �������� � ����������
	 * @param operation - ��������� ��������
	 * @param category - ��������� ��������� ��������
	 * @return true, ���� ��������� ���������� � ���������
	 */
	public boolean testOperationCompatibleToCategory(CardOperation operation, CardOperationCategory category) throws BusinessException
	{
		// 0. ���� category - ��� ��������� ��������, �� ��� ���������� � ���������
		if (category.equals(operation.getCategory()))
			return true;

		// 1. ���� �������� ��������, ��������� ������ �������� �������� ��������
		// ���� �������� �����������, ��������� ������ �������� ����������� ��������
		boolean res = operation.isCash() ? category.isCash() : category.isCashless();

		// 2. �������� �������� ������ ���� � �������� ���������.
		// ��������� �������� - � ��������� ����������
		res = res && operation.getCategory().isIncome() == category.isIncome();

		// 3. ���� ��������� �� �������� ������������� ��������
		// �� �������� � MCC-�����, �� ��������������� ���������, �������� ������������� 
		if (!category.isIncompatibleOperationsAllowed())
			res = res && (operation.getMccCode() != null && doesCategoryContainsMCC(category, operation.getMccCode()));

		return res;
	}

	private boolean doesCategoryContainsMCC(final CardOperationCategory category, final Long mcc) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "doesCategoryContainsMCC");
					query.setParameter("category", category);
					query.setLong("mccCode", mcc);
					query.setMaxResults(1);
					//noinspection unchecked
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ���������� �� ��������� �������� � ������ ���������
	 * @param categoryId - ������������� ���������
	 * @return true - ����������
	 */
	public boolean categoryContainsOperations(final Long categoryId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "categoryContainsOperations");
					query.setLong("categoryId", categoryId);
					query.setMaxResults(1);
					//noinspection unchecked
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ �������� �� �������� ����������
	 * @param login �����
	 * @param categoryId ���������
	 * @param maxResults ����� ����������� �� ��������
	 * @param firstResult ������ ��������� ��������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<CardOperation> getCardOperations (final Login login, final Long categoryId, final int maxResults, final int firstResult) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperation>>()
			{
				public List<CardOperation> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCardOperations");
					query.setLong("loginId", login.getId());
					query.setLong("categoryId", categoryId);
					if (maxResults > 0)
						query.setMaxResults(maxResults);
					query.setFirstResult(firstResult);
					//noinspection unchecked
					return (List<CardOperation>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������������� ����� �������� ��������� ��� ��������
	 * @param loginId - ����� �������
	 * @param oldCategoryId - �� ���������, �������� �� ������� ������
	 * @param newCategoryId - ����� �� ���������
	 * @throws BusinessException
	 */
	public void setGeneralCategory(final Long loginId, final Long oldCategoryId, final Long newCategoryId) throws BusinessException
	{
		try
        {
            HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery(QUERY_PREFIX + "setGeneralCategory");
					query.setLong("loginId", loginId);
					query.setLong("oldCategoryId", oldCategoryId);
					query.setLong("newCategoryId", newCategoryId);
	                query.executeUpdate();
	                return null;
                }
            });
        }
        catch (BusinessException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
	}

	/**
	 * ���������� ��������� � ������ ��������
	 * @param map - ���� ����: ��_�������� - ��_�����_���������
	 * @param loginId - ����� �������
	 * @throws BusinessException
	 */
	@Transactional
	public void updateOperationsList(final Map<Long, Long> map, final Long loginId) throws BusinessException
	{
		for(Map.Entry<Long, Long> entry : map.entrySet())
		{
			updateOperationCategory(loginId, entry.getKey(), entry.getValue());
		}
	}

	/**
	 * ��������� ��������� � ��������
	 * @param loginId - ����� �������
	 * @param operationId - ������������� ��������
	 * @param newCategoryId - ������������� ����� ���������
	 * @throws BusinessException
	 */
	public void updateOperationCategory(final Long loginId, final Long operationId, final Long newCategoryId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "setNewCategory");
					query.setLong("loginId", loginId);
					query.setParameter("operationId", operationId);
					query.setParameter("newCategoryId", newCategoryId);
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
	 * ������� ������ ������ �� ��������� ������������
	 * @param date ���� ������ ������� ������ ����� �������
	 * @throws BusinessException
	 */
	public void deleteOldCardOperation(final Calendar date) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CardOperationService.class.getName() + ".DeleteOldCardOperation");
					query.setParameter("date", date);
					query.executeUpdate();
					return null;
				};
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ ��������� �������� ��� �������� �������� �� ����������
	 * @param filterParams - ��������� ����������
	 * @param maxResults - ������������ ���������� �������
	 * @param firstResult - � ����� ������ �������� �����
	 * @param cardsNumbers - ������ ����, �� ������� �������� ��������
	 * @param categoryIds - ������ ���������, �� ������� �������� ��������
	 * @param loginId - ������������� �������
	 * @param showCashPayments - ���������� �������� � ���������
	 * @return ������ ��������� ��������
	 * @throws BusinessException
	 */
	public List<CardOperation> getCardOperations(final Map<String, Object> filterParams, final int maxResults, final int firstResult, final List<String> cardsNumbers,
	                                             final List<Long> categoryIds, final Long loginId, final boolean showCashPayments, final boolean orderByDesc) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperation>>()
			{
				public List<CardOperation> run(Session session) throws Exception
				{
					if(CollectionUtils.isNotEmpty(categoryIds))
						session.enableFilter("byCategoryFilter").setParameterList("categoryIds", categoryIds);

					String[] excludeCategories = (String[]) filterParams.get("excludeCategories");
					if(excludeCategories != null)
						session.enableFilter("byIdInMAPIFilter").setParameterList("excludeCategories", excludeCategories);

					Query query = session.getNamedQuery(QUERY_PREFIX + (orderByDesc ? "getOutcomeOperationsDesc" : "getOutcomeOperations"));
					query.setParameter("loginId", loginId, Hibernate.LONG);
					query.setParameter("fromDate", DateHelper.toCalendar((Date)filterParams.get("fromDate")), Hibernate.CALENDAR);
					query.setParameter("toDate", DateHelper.toCalendar((Date)filterParams.get("toDate")), Hibernate.CALENDAR);
					query.setParameter("maxLoadDate", DateHelper.toCalendar((Date)filterParams.get("openPageDate")), Hibernate.CALENDAR);
					query.setParameter("income", filterParams.get("income"), Hibernate.BOOLEAN);
					query.setParameter("hidden", filterParams.get("hidden"), Hibernate.BOOLEAN);
					query.setParameter("country", filterParams.get("country"), Hibernate.STRING);
					query.setParameter("showCash", BooleanHelper.asBoolean(filterParams.get("showCash")), Hibernate.BOOLEAN);
					query.setParameter("showOtherPayments", showCashPayments, Hibernate.BOOLEAN);

					if (CollectionUtils.isNotEmpty(cardsNumbers))
						query.setParameterList("cardNumbers", cardsNumbers);
					else
						query.setParameter("cardNumbers", "");

					//� hibernate'� ���������� �������� maxResults � firstResult.
					//� ���������� (�.�. �� � maxResults ����� �������� Integer.MAX_VALUE) ���������� ������������� �����
					//����� hibernate �������� ������ ��������, ������� � firstResult � �� �������������� �����, � ���������� ��������� ������ ����.
					int maxRes = ((long) maxResults + (long) firstResult) > Integer.MAX_VALUE ? maxResults - firstResult : maxResults;

					query.setMaxResults(maxRes);
					query.setFirstResult(firstResult);

					//noinspection unchecked
					return (List<CardOperation>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��� ��������� ������ ��� ����������� ��������� (����� �������� � ��������� ��������)
	 * @param fromDate - ���� �
	 * @param toDate - ���� ��
	 * @param loginId - ����� �������
	 * @param cards - ������ ����, �� ������� �������� ��������
	 * @param showCashPayments - ���������� �������� � ���������
	 * @param showCash - ���������� �������� ��������/������ ��������
	 * @param excludeCategories - ����������� ��������� ��������
	 * @param country - ������ ���������� ��������
	 * @return ������ ��� ����������� ���������
	 */
	public List<CalendarDataDescription> getCalendarData(final Calendar fromDate, final Calendar toDate, final Long loginId, final List<String> cards,
	                                                     final boolean showCashPayments, final boolean showCash, final String[] excludeCategories, final String country) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CalendarDataDescription>>()
			{
				public List<CalendarDataDescription> run(Session session) throws Exception
				{
					boolean useAdditionalParams = (!showCash || excludeCategories != null || StringHelper.isNotEmpty(country));

					Query query = session.getNamedQuery(QUERY_PREFIX + (useAdditionalParams ?  "getCalendarDataByAdditionalParams" : "getCalendarData"));
					query.setCalendar("fromDate", fromDate);
					query.setCalendar("toDate", toDate);
					query.setLong("loginId", loginId);
					query.setBoolean("showOtherPayments", showCashPayments);

					if (CollectionUtils.isNotEmpty(cards))
						query.setParameterList("cardNumbers", cards);
					else
						query.setParameter("cardNumbers", "");

					if (useAdditionalParams)
					{
						query.setBoolean("showCash", showCash);
						query.setString("country", country);

						if (excludeCategories != null)
							query.setParameterList("excludeCategories", excludeCategories);
						else
							query.setParameter("excludeCategories", "");
					}

					//noinspection unchecked
					return (List<CalendarDataDescription>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��� ��������� ������ ��� ������� �� ���� ��� ����������� ���������
	 * @param fromDate - ���� �
	 * @param toDate - ���� ��
	 * @param maxOperationLoadDate - ������������ ���� �������� �������� (��������, ������� ���� ��������� ����� ���� ����, �� ��������� � �������)
	 * @param loginId - ����� �������
	 * @param cards - ������ ����, �� ������� �������� ��������
	 * @param showCashPayments - ���������� �������� � ���������
	 * @return ������ ��� ������� �� ����
	 */
	public List<CalendarDayExtractByOperationDescription> getDayExtractToFinanceCalendar(final Calendar fromDate, final Calendar toDate, final Calendar maxOperationLoadDate,
	                                                                          final Long loginId, final List<String> cards, final boolean showCashPayments) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CalendarDayExtractByOperationDescription>>()
			{
				public List<CalendarDayExtractByOperationDescription> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getDayExtractToFinanceCalendar");
					query.setCalendar("fromDate", fromDate);
					query.setCalendar("toDate", toDate);
					query.setCalendar("maxLoadDate", maxOperationLoadDate);
					query.setLong("loginId", loginId);
					query.setBoolean("showOtherPayments", showCashPayments);

					if (CollectionUtils.isNotEmpty(cards))
						query.setParameterList("cardNumbers", cards);
					else
						query.setParameter("cardNumbers", "");

					//noinspection unchecked
					return (List<CalendarDayExtractByOperationDescription>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ �������� �������
	 * @param loginId ������������� ������
	 * @param fromDate ���� ������ �������
	 * @param toDate ���� ����� �������
	 * @return ������ �������� �������
	 * @throws BusinessException
	 */
	public List<CardOperation> getClientLoadedOperations(final Long loginId, final Calendar fromDate, final Calendar toDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperation>>()
			{
				public List<CardOperation> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getClientLoadedOperations");
					query.setParameter("loginId", loginId, Hibernate.LONG);
					query.setParameter("fromDate", fromDate, Hibernate.CALENDAR);
					query.setParameter("toDate", toDate, Hibernate.CALENDAR);
					//noinspection unchecked
					return (List<CardOperation>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� �� pushUID
	 * @param pushUID pushUID
	 * @return ��������
	 * @throws BusinessException
	 */
	public CardOperationExtendedFields findByPushUID(final Long pushUID) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CardOperationExtendedFields>()
			{
				public CardOperationExtendedFields run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findByPushUID");
					query.setLong("pushUID", pushUID);
					return (CardOperationExtendedFields) query.uniqueResult();
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
	 * ����� �������� �� parentPushUID
	 * @param parentPushUID parentPushUID
	 * @return ��������
	 * @throws BusinessException
	 */
	public List<CardOperation> findByParentPushUID(final Long parentPushUID) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperation>>()
			{
				public List<CardOperation> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findByParentPushUID");
					query.setLong("parentPushUID", parentPushUID);
					return (List<CardOperation>) query.list();
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
	 * ����� ���. ����� �������� �� �� ��������
	 * @param cardOperationId �� ��������
	 * @return ���. ���� ��������
	 * @throws BusinessException
	 */
	public CardOperationExtendedFields getCardOperationExtendedFields(final Long cardOperationId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CardOperationExtendedFields>()
			{
				public CardOperationExtendedFields run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCardOperationExtendedFields");
					query.setLong("cardOperationId", cardOperationId);
					return (CardOperationExtendedFields) query.uniqueResult();
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
	 * ���������� �������� � ������������ �������� �����
	 * @return �������� � ������������ �������� �����
	 * @throws BusinessException
	 */
	public List<CardOperation> getDeadOperations() throws BusinessException
	{
		IPSConfig ipsConfig = ConfigFactory.getConfig(IPSConfig.class);
		final Calendar lifetime = DateHelper.addSeconds(Calendar.getInstance(), -ipsConfig.getCardOperationLifetime());
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperation>>()
			{
				public List<CardOperation> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getDeadOperations");
					query.setParameter("lifetime", lifetime, Hibernate.CALENDAR);
					//noinspection unchecked
					return (List<CardOperation>) query.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<DiagramAbstract> getDiagramAbstracts(final Login login, final Calendar from, final Calendar to, final Boolean onlyCash, final Boolean transfer, final Boolean internal, final Boolean noTransfer, final List<String> cardNumbers) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<DiagramAbstract>>()
			{
				public List<DiagramAbstract> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getDiagramData");
					query.setParameter("loginId",    login);
					query.setParameter("fromDate",   from);
					query.setParameter("toDate",     to);
					query.setParameter("onlyCash",   onlyCash);
					query.setParameter("transfer",   transfer);
					query.setParameter("internal",   internal);
					query.setParameter("noTransfer", noTransfer);

					if (CollectionUtils.isEmpty(cardNumbers))
					{
						query.setParameter("cardNumbers", null);
					}
					else
					{
						query.setParameterList("cardNumbers", cardNumbers);
					}

					List queryResults = query.list();

					List<DiagramAbstract> result = new ArrayList<DiagramAbstract>();
					if (CollectionUtils.isNotEmpty(queryResults))
					{
						for (Object queryResult : queryResults)
						{
							Object[] values = (Object[]) queryResult;

							result.add(new DiagramAbstract((Boolean)  values[0],
														   (Double)   values[1],
									                       (Calendar) values[2]));
						}
					}

					return result;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
