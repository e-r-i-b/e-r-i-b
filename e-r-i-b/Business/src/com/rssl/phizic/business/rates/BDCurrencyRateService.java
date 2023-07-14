package com.rssl.phizic.business.rates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.DebugLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @ author: filimonova
 * @ created: 04.10.2010
 * @ $Author$
 * @ $Revision$
 * Сервис для взамодейсвтия с табличкой RATE (Курсы валют)
 */
public class BDCurrencyRateService
{
	private static final String BUSINESS_CURRENCY_RATE_CACHE = "BDCurrencyRateService.getAllCurrenciesRatesList";
	private static final String OFFICE_NOT_FOUND_ERROR_MESSAGE = "Ошибка при плучении курсов валют. Не обнаружено подразделение в БД:";
	//Кеш курсов всех валют, кроме металлических (обновляется каждые 15 мин - см. "erib-ehcache.xml")
	private static final Cache allCurrenciesRateCache;
	private static final String DATE_TIME_FORMATE = "%1$te.%1$tm.%1$tY_%1$tH:%1$tM";

	private static final String QUERY_PREFIX = BDCurrencyRateService.class.getName();
	private static final int RATE_SCALE = 4;
	private static final DepartmentService departmentService = new DepartmentService();

	//TODO BUG057746: Лишние запросы курсов валют. Cбор статистики по обновлению курсов валют. Будет убрано после решения проблемы.
	private static final Log debugLog;

	static
	{
		allCurrenciesRateCache = CacheProvider.getCache(BUSINESS_CURRENCY_RATE_CACHE);
		if ( allCurrenciesRateCache == null )
			throw new RuntimeException("Не найден кеш курсов всех валют");

		debugLog = DebugLogFactory.getLog(Constants.LOG_MODULE_WEB);
	}
	/**
    * Сохранение курса
	*
    * @param rate - курс
	* @return
	 *  */
	public void addOrUpdate(final Rate rate) throws BusinessException
	{
		// Найти такой курс в базе. Если есть - обновить его.
		// Искать по дате начала действия, валюте из, валюте в, департаменту, типу курса и тарифному плану.

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".getByUniqData");
					query.setParameter("date", rate.getEffDate());
					query.setParameter("from", rate.getFromCurrency());
					query.setParameter("to", rate.getToCurrency());
					query.setParameter("rateType", rate.getCurrencyRateType());
					query.setParameter("department_id", rate.getDepartment() == null ? null: rate.getDepartment().getId());
					query.setString("tarifPlanCodeType", TariffPlanHelper.getCodeBySynonym(rate.getTarifPlanCodeType()));
					query.setMaxResults(1);

					Rate existRate = (Rate) query.uniqueResult();

					if (existRate == null)
						session.save(rate);
					else
					{
						// Заменяем старый rate на новый
						Long id = existRate.getId();
						session.evict(existRate);
						rate.setId(id);
						session.update(rate);
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * обновляем старые курсы датой их устаревания.
	 *
	 * @param expireDate дата устаревания
	 * @param ids список старых курсов.
	 */
	public void updateOldRates(final Calendar expireDate, final List<Long> ids) throws BusinessException
	{
		if (ids.isEmpty())
			return;
		
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".updateOldRates");
						query.setParameter("expDate", expireDate);
						query.setParameterList("ids", ids);
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
    * Получение курса преобразования из валюты from в валюту to на дату date
	* Если курс не найден - вернется null
	* Офис учитывается только до тер банка
	* Учитываем обратные курсы 
	*
    * @param date - дата, на которую требуется найти курс
	* @param from - валюта, из которой требутеся произвести преобразование
	* @param to - валюта, в которую требуется произвести преобразование
	* @param office - офис, для которого запрашивается курс
    * @param rateType - тип курса валют
	* @return курс
    */
	public Rate getRate(Calendar date, Currency from, Currency to, Office office, CurrencyRateType rateType,
	                    String tarifPlanCodeType) throws BusinessException
	{
		// A. Пытаемся получить не-кросс-курс
		Rate rate = findRate(date, from, to, office, rateType, tarifPlanCodeType);
		// B. Пытаемся получить кросс-курс
		if (rate == null)
			rate = findCrossRate(date, from, to, office, rateType, tarifPlanCodeType);
		return rate;
	}

	/**
	 * Возвращает список актуальных курсов покупки/продажи для всех валют, для всех тарифных планов,
	 * по которым в БД есть данные
	 * @param actualDate - дата на которую необходимо получить курсы валют
	 * @param to - валюта в кототую требуется произвести преобразование
	 * @param office - офис, для которого требуется курс валют
	 * @param rateType - тип курса валют
	 * @param ignoreIMACodes - коды металлических валют
	 * @return
	 * @throws BusinessException
	 */
	public List<Rate> getAllCurrenciesRatesList(final Calendar actualDate, final Currency to, final Office office,
	                                            final CurrencyRateType rateType, final List<String> ignoreIMACodes) throws BusinessException
	{
		Serializable cacheKey = String.format(DATE_TIME_FORMATE,actualDate.getTime())+'_'+
				to.getNumber()+'_'+office.getSynchKey()+'_'+rateType.name();
		Element element  = allCurrenciesRateCache.get(cacheKey);
		if ( element != null )
			return (List<Rate>) element.getObjectValue();

		final CurrencyRateType invRateType = Rate.inverseCurrencyRateType(rateType);
		final Department department = departmentService.getTBByOffice(office);
		if (department == null)
			throw new BusinessException(OFFICE_NOT_FOUND_ERROR_MESSAGE+office.getSynchKey());
		List<Rate> rates = null;
		try
		{
			rates = HibernateExecutor.getInstance().execute(new HibernateAction<List<Rate>>()
				{
					public List<Rate> run(Session session)
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".getActualCurrencyRatesListByParams");
						query.setTimestamp("actualDate", actualDate.getTime());
						query.setString("to", to.getNumber());
						query.setInteger("rateType", rateType.getId());
						query.setInteger("inverseRateType", invRateType.getId());
						query.setLong("department_id", department.getId());
						query.setParameterList("dontShowCurrencies", ignoreIMACodes);

						//noinspection unchecked
						return (List<Rate>) query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		if (rates != null)
		{
			allCurrenciesRateCache.put(new Element(cacheKey, rates));
		}
		return rates;
	}

	private Rate findRate(final Calendar date, final Currency from, final Currency to, final Office office,
	                      final CurrencyRateType rateType, final String tarifPlanCodeType) throws BusinessException
	{
		if (from.compare(to))
			return new Rate(from, to, BigDecimal.ONE, BigDecimal.ONE, rateType);
		
		final CurrencyRateType invRateType = Rate.inverseCurrencyRateType(rateType);
		final Department department = departmentService.getTBByOffice(office);
		if (department == null)
			throw new BusinessException(OFFICE_NOT_FOUND_ERROR_MESSAGE+office.getSynchKey());
		Application application = LogThreadContext.getApplication();

		//TODO BUG057746: Лишние запросы курсов валют. Cбор статистики по обновлению курсов валют. Будет убрано после решения проблемы.
		StringBuilder builderMsg = new StringBuilder();
		builderMsg.append("|from="+from.getNumber());
		builderMsg.append("; date="+ DateHelper.toISO8601DateFormat(date));
		builderMsg.append("; to="+to.getNumber());
		builderMsg.append("; department_id="+department.getId());
		builderMsg.append("; rateType="+rateType.getId());
		builderMsg.append("; inverseRateType="+invRateType.getId());
		builderMsg.append("; tarifPlanCodeType="+tarifPlanCodeType);
		builderMsg.append("; application="+(application != null ? application.name() : ""));
		builderMsg.append("|");

		debugLog.trace("CurrencyRateDebug: BDCurrencyRateService.findRate Получение текущего курса валют из БД по параметрам: "+builderMsg.toString());
		List<Rate> rates = null;
		try
		{
			//Пробуем искать курсы для конкретного тарифного плана или
			//все тарифные планы (tarifPlanCodeType = null это для админки, когда сотрудник просматривает курсы валют)
			rates = HibernateExecutor.getInstance().execute(new HibernateAction<List<Rate>>()
				{
					public List<Rate> run(Session session)
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".getListByData");
						query.setParameter("date", date);
						query.setParameter("fromNumber", from.getNumber());
						query.setParameter("toNumber", to.getNumber());
						query.setParameter("rateType", rateType.getId());
						query.setParameter("inverseRateType", invRateType.getId());
						query.setLong("department_id", department.getId());
						query.setString("tarifPlanCodeType", TariffPlanHelper.getCodeBySynonym(tarifPlanCodeType));
						//noinspection unchecked
						return (List<Rate>) query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		if (CollectionUtils.isNotEmpty(rates))
		{
			//TODO BUG057746: Лишние запросы курсов валют. Cбор статистики по обновлению курсов валют. Будет убрано после решения проблемы.
			debugLog.trace("CurrencyRateDebug: BDCurrencyRateService.findRate Курс валют получен из БД. Параметры: "+builderMsg.toString());
			
			// A. Выбираем курс требуемого типа
			Rate rate = selectRate(rates, rateType);
			if (rate != null)
				return calculateRate(rate, rateType, from, to);

			// B. Выбираем курс, обратный к требуемому типу
			Rate invRate = selectRate(rates, invRateType);
			if (invRate != null)
				return calculateRate(invRate, rateType, from, to);
		}

		//TODO BUG057746: Лишние запросы курсов валют. Cбор статистики по обновлению курсов валют. Будет убрано после решения проблемы.
		debugLog.trace("CurrencyRateDebug: BDCurrencyRateService.findRate Курс валют не обнаружен в БД. Параметры: "+builderMsg.toString());
		return null;
	}

	/**
	 * Выбирает курс с наиболее "глубоким" департаментом
	 * (т.е. департамент, у которого наибольший уровень)
	 * @param rates
	 * @param rateType
	 * @return
	 */
	private static Rate selectRate(List<Rate> rates, CurrencyRateType rateType)
	{
		Rate resultRate = null;
		Integer resultLevel = null;
		for (Rate rate: rates) {
			if (rate.getCurrencyRateType() == rateType) {
				int level = departmentService.getLevel(rate.getDepartment());
				if (resultLevel == null || level > resultLevel) {
					resultRate = rate;
					resultLevel = level;
				}
			}
		}
		return resultRate;
	}

	/**
	 * Проверяет были ли в таблицу загружены курсы с переданными в paramsMap параметрами
	 * @param paramsMap - мапа с параметрами для запроса
	 * @return - true - были загружены, false - нет
	 * @throws BusinessException
	 */
	public boolean rateExistByOrderNumber(final Map<String, Object> paramsMap) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
				{
					public Boolean run(Session session)
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".rateExistByOrderNumber");
						if (paramsMap != null && CollectionUtils.isNotEmpty(paramsMap.entrySet()))
						{
							for(String paramName: paramsMap.keySet())
							{
								query.setParameter(paramName, paramsMap.get(paramName));
							}
						}
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

	/**
	 * Проверяет есть ли в таблицу курсы с указанной валютой
	 * @return - true - есть, false - нет
	 * @throws BusinessException
	 */
	public boolean rateExistByCurrency(final CurrencyImpl currency) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
				{
					public Boolean run(Session session)
					{
						Query query = session.getNamedQuery(QUERY_PREFIX + ".rateExistByCurrency");
						query.setString("currency", currency.getId());
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

	// По курсу (прямому или обратному) получить прямой в зависимости от типа курса
	private static Rate calculateRate(Rate rate, CurrencyRateType rateType, Currency from, Currency to)
	{
		// Помимо типа добавляем условие на соответствие валют: может вернуться курс ЦБ с перевернутыми валютами
		if (rate.getCurrencyRateType() == rateType && rate.getFromCurrency().compare(from) && rate.getToCurrency().compare(to))
		{
			return rate;
		}
		return rate.inverse();
	}

	private Rate findCrossRate(Calendar date, Currency from, Currency to, Office office,
	                           CurrencyRateType currencyRateType, String tarifPlanCodeType) throws BusinessException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency rurCurrency = currencyService.findByAlphabeticCode("RUB");
			// Если валюты не совпадают с российской валютой - ищем кросс-курс
			if (!(from.compare(rurCurrency)) && !(to.compare(rurCurrency)))
			{
				// Алгоритм рассчета кросс-курса:
				// 1. Берем курс [currencyRateType] валюты from за рубли
				Rate oneRate = findRate(date, from, rurCurrency, office, currencyRateType, tarifPlanCodeType);
				// 2. Берем курс [currencyRateType] рубля за валюту to
				Rate twoRate = findRate(date, rurCurrency, to, office, currencyRateType, tarifPlanCodeType);

				if (oneRate == null || twoRate == null)
					return null;

				// 3. Нужно определить стоимость одной единицы внешней валюты относительно рубля
				BigDecimal rurForOneFrom = oneRate.getToValue().divide(oneRate.getFromValue(), RATE_SCALE, RoundingMode.HALF_EVEN);
				BigDecimal rurForOneTo = twoRate.getFromValue().divide(twoRate.getToValue(), RATE_SCALE, RoundingMode.HALF_EVEN);

				// 4. Создаем новый курс - ставим в соответсвие единице валюты From - отношение значений rurForOneFrom и rurForOneTo
				return new Rate(from, to, BigDecimal.ONE, rurForOneFrom.divide(rurForOneTo, RATE_SCALE, RoundingMode.HALF_EVEN), currencyRateType);
			}
			return null;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
