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
 * Сервис для работы с операциями по карте
 */
@SuppressWarnings({"OverlyComplexAnonymousInnerClass", "MethodWithTooManyParameters"})
public class CardOperationService
{
	private static final String QUERY_PREFIX = CardOperationService.class.getName() + ".";

	private static final int BATCH_SIZE = 1000;

	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Возвращает карточную операцию по ID
	 * @param id - ID
	 * @return карточная операция или null, если не найдена
	 */
	public CardOperation findById(long id) throws BusinessException
	{
		return simpleService.findById(CardOperation.class, id);
	}

	/**
	 * Добавляет или сохраняет карточную операцию в бд
	 * @param cardOperation - карточная операция
	 */
	public void addOrUpdate(CardOperation cardOperation) throws BusinessException
	{
		simpleService.addOrUpdate(cardOperation);
	}

	/**
	 * Добавляет или сохраняет доп. поля карточной операции в бд
	 * @param cardOperationExtendedFields - доп. поля карточной операции
	 */
	public void addOrUpdate(CardOperationExtendedFields cardOperationExtendedFields) throws BusinessException
	{
		simpleService.addOrUpdate(cardOperationExtendedFields);
	}

	/**
	 * Удаляет карточную операцию из бд
	 * @param cardOperation - карточная операция
	 */
	public void remove(CardOperation cardOperation) throws BusinessException
	{
		simpleService.remove(cardOperation);
	}

	/**
	 * Добавляет новые карточные операции в бд
	 * @param operations - список новых карточных операций
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
						// Если в сессию хибернейта набилось достаточно много данных, сбрасываем их в бд
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
			throw new BusinessException("Не удалось сохранить список новых карточных операций", e);
		}
	}

	/**
	 * Метод для получения графической выписки(суммы операций) по категории
	 * @param fromDate дата с
	 * @param toDate дата по
	 * @param cards номера карт
	 * @param cash включать ли операции с наличными
	 * @param income true только доходные операции в противном случаи расходные
	 * @param showTransfer - признак показывать ли переводы
	 * @param login логин
	 * @param showCashPayments - показать траты наличными
	 * @param excludeCategories исключаемые категории операций
	 * @return графическая выписка(суммы операций) по категориям
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
	 * Возвращает суммарный расход по категории за период
	 * @param fromDate - дата от
	 * @param toDate - дата по
	 * @param cards - список карт
	 * @param login - логин
	 * @param categoryId - идентификатор категории
	 * @return графическая выписка(суммы операций) по категориям
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
	 * Метод для получения графической выписки (доход и расход)
	 * @param fromDate дата с
	 * @param toDate дата по
	 * @param cards номера карт
	 * @param cash операции с наличными
	 * @param login логин
	 * @return список операций
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

	 /** Метод для получения графической выписки (наличные и безналичные)
	 * @param fromDate дата с
	 * @param toDate дата по
	 * @param cards номера карт
	 * @param cash операции с наличными
	  * @param login логин
	 * @return список операций
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
	 * Получение категорий банка и пользователя
	 * @param income - признак расходных/доходных операций
	 * @param login - логин
	 * @return список категорий операций
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
	 * Получение категорий банка и пользователя (учитывается доходность категорий и необходимость получать категории для переводов)
	 * @param income - признак расходных/доходных операций
	 * @param showTransfer - признак получать ли категории для переводов
	 * @param login - логин
	 * @return список категорий операций
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
	 * Проверяет совместимость операции с категорией
	 * @param operation - карточная операция
	 * @param category - категория карточных операций
	 * @return true, если категория совместима с операцией
	 */
	public boolean testOperationCompatibleToCategory(CardOperation operation, CardOperationCategory category) throws BusinessException
	{
		// 0. Если category - это категория операция, то она совместима с операцией
		if (category.equals(operation.getCategory()))
			return true;

		// 1. Если операция наличная, категория должна включать наличные операции
		// Если операция безналичная, категория должна включать безналичные операции
		boolean res = operation.isCash() ? category.isCash() : category.isCashless();

		// 2. Доходные операции должны быть в доходных категриях.
		// Расходные операции - в расходных категориях
		res = res && operation.getCategory().isIncome() == category.isIncome();

		// 3. Если категория не приемлет несовместимые операции
		// то операция с MCC-кодом, не соответствующим категории, является несовместимой 
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
	 * Проверяет содержатся ли карточные операцие в данной категории
	 * @param categoryId - идентификатор категории
	 * @return true - содержатся
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
	 * Получает список операций по заданным параметрам
	 * @param login логин
	 * @param categoryId категория
	 * @param maxResults число результатов на странице
	 * @param firstResult первый результат страницы
	 * @return список операций
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
	 * Устанавливает новое значение категории для операций
	 * @param loginId - логин клиента
	 * @param oldCategoryId - ид категории, операции из которой меняем
	 * @param newCategoryId - новый ид категории
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
	 * Обновление категории у списка операций
	 * @param map - мапа вида: ид_операции - ид_новой_категории
	 * @param loginId - логин клиента
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
	 * Обновляет категорию у операции
	 * @param loginId - логин клиента
	 * @param operationId - идентификатор операции
	 * @param newCategoryId - идентификатор новой категории
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
	 * Удаляет старые данные об операциях пользователя
	 * @param date дата раньше которой данные нужно удалить
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
	 * Возвращает список карточных операций для страницы расходов по категориям
	 * @param filterParams - параметры фильтрации
	 * @param maxResults - максимальное количество записей
	 * @param firstResult - с какой записи начинать отбор
	 * @param cardsNumbers - список карт, по которым отбирать операции
	 * @param categoryIds - список категорий, по которым отбирать операции
	 * @param loginId - идентификатор клиента
	 * @param showCashPayments - показывать операции с наличными
	 * @return список карточных операций
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

					//В hibernate'а происходит сложение maxResults и firstResult.
					//В результате (т.к. мы в maxResults можем передать Integer.MAX_VALUE) получается отрицательное число
					//Далее hibernate начинает искать элементы, начиная с firstResult и до отрицательного числа, в результате возвращая пустой лист.
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
	 * Метод для получения данных для финансового календаря (сумма доходных и расходных операций)
	 * @param fromDate - дата с
	 * @param toDate - дата по
	 * @param loginId - логин клиента
	 * @param cards - список карт, по которым отбирать операции
	 * @param showCashPayments - показывать операции с наличными
	 * @param showCash - показывать операции внесения/выдачи наличных
	 * @param excludeCategories - исключаемые категории операций
	 * @param country - страна совершения операций
	 * @return данные для финансового календаря
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
	 * Метод для получения данных для выписки за день для финансового календаря
	 * @param fromDate - дата с
	 * @param toDate - дата по
	 * @param maxOperationLoadDate - максимальная дата загрузки операции (операции, которые были загружены после этой даты, не участвуют в выборке)
	 * @param loginId - логин клиента
	 * @param cards - список карт, по которым отбирать операции
	 * @param showCashPayments - показывать операции с наличными
	 * @return данные для выписки за день
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
	 * Возвращает список операций клиента
	 * @param loginId идентификатор логина
	 * @param fromDate дата начала периода
	 * @param toDate дата конца периода
	 * @return список операций клиента
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
	 * Поиск операции по pushUID
	 * @param pushUID pushUID
	 * @return операция
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
	 * Поиск операций по parentPushUID
	 * @param parentPushUID parentPushUID
	 * @return операции
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
	 * Поиск доп. полей операции по ид операции
	 * @param cardOperationId ид операции
	 * @return доп. поля операции
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
	 * Возвращает операции с окончившимся временем жизни
	 * @return операции с окончившимся временем жизни
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
