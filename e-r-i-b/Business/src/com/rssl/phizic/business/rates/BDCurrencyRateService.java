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
 * ������ ��� ������������� � ��������� RATE (����� �����)
 */
public class BDCurrencyRateService
{
	private static final String BUSINESS_CURRENCY_RATE_CACHE = "BDCurrencyRateService.getAllCurrenciesRatesList";
	private static final String OFFICE_NOT_FOUND_ERROR_MESSAGE = "������ ��� �������� ������ �����. �� ���������� ������������� � ��:";
	//��� ������ ���� �����, ����� ������������� (����������� ������ 15 ��� - ��. "erib-ehcache.xml")
	private static final Cache allCurrenciesRateCache;
	private static final String DATE_TIME_FORMATE = "%1$te.%1$tm.%1$tY_%1$tH:%1$tM";

	private static final String QUERY_PREFIX = BDCurrencyRateService.class.getName();
	private static final int RATE_SCALE = 4;
	private static final DepartmentService departmentService = new DepartmentService();

	//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
	private static final Log debugLog;

	static
	{
		allCurrenciesRateCache = CacheProvider.getCache(BUSINESS_CURRENCY_RATE_CACHE);
		if ( allCurrenciesRateCache == null )
			throw new RuntimeException("�� ������ ��� ������ ���� �����");

		debugLog = DebugLogFactory.getLog(Constants.LOG_MODULE_WEB);
	}
	/**
    * ���������� �����
	*
    * @param rate - ����
	* @return
	 *  */
	public void addOrUpdate(final Rate rate) throws BusinessException
	{
		// ����� ����� ���� � ����. ���� ���� - �������� ���.
		// ������ �� ���� ������ ��������, ������ ��, ������ �, ������������, ���� ����� � ��������� �����.

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
						// �������� ������ rate �� �����
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
	 * ��������� ������ ����� ����� �� �����������.
	 *
	 * @param expireDate ���� �����������
	 * @param ids ������ ������ ������.
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
    * ��������� ����� �������������� �� ������ from � ������ to �� ���� date
	* ���� ���� �� ������ - �������� null
	* ���� ����������� ������ �� ��� �����
	* ��������� �������� ����� 
	*
    * @param date - ����, �� ������� ��������� ����� ����
	* @param from - ������, �� ������� ��������� ���������� ��������������
	* @param to - ������, � ������� ��������� ���������� ��������������
	* @param office - ����, ��� �������� ������������� ����
    * @param rateType - ��� ����� �����
	* @return ����
    */
	public Rate getRate(Calendar date, Currency from, Currency to, Office office, CurrencyRateType rateType,
	                    String tarifPlanCodeType) throws BusinessException
	{
		// A. �������� �������� ��-�����-����
		Rate rate = findRate(date, from, to, office, rateType, tarifPlanCodeType);
		// B. �������� �������� �����-����
		if (rate == null)
			rate = findCrossRate(date, from, to, office, rateType, tarifPlanCodeType);
		return rate;
	}

	/**
	 * ���������� ������ ���������� ������ �������/������� ��� ���� �����, ��� ���� �������� ������,
	 * �� ������� � �� ���� ������
	 * @param actualDate - ���� �� ������� ���������� �������� ����� �����
	 * @param to - ������ � ������� ��������� ���������� ��������������
	 * @param office - ����, ��� �������� ��������� ���� �����
	 * @param rateType - ��� ����� �����
	 * @param ignoreIMACodes - ���� ������������� �����
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

		//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
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

		debugLog.trace("CurrencyRateDebug: BDCurrencyRateService.findRate ��������� �������� ����� ����� �� �� �� ����������: "+builderMsg.toString());
		List<Rate> rates = null;
		try
		{
			//������� ������ ����� ��� ����������� ��������� ����� ���
			//��� �������� ����� (tarifPlanCodeType = null ��� ��� �������, ����� ��������� ������������� ����� �����)
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
			//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
			debugLog.trace("CurrencyRateDebug: BDCurrencyRateService.findRate ���� ����� ������� �� ��. ���������: "+builderMsg.toString());
			
			// A. �������� ���� ���������� ����
			Rate rate = selectRate(rates, rateType);
			if (rate != null)
				return calculateRate(rate, rateType, from, to);

			// B. �������� ����, �������� � ���������� ����
			Rate invRate = selectRate(rates, invRateType);
			if (invRate != null)
				return calculateRate(invRate, rateType, from, to);
		}

		//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
		debugLog.trace("CurrencyRateDebug: BDCurrencyRateService.findRate ���� ����� �� ��������� � ��. ���������: "+builderMsg.toString());
		return null;
	}

	/**
	 * �������� ���� � �������� "��������" �������������
	 * (�.�. �����������, � �������� ���������� �������)
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
	 * ��������� ���� �� � ������� ��������� ����� � ����������� � paramsMap �����������
	 * @param paramsMap - ���� � ����������� ��� �������
	 * @return - true - ���� ���������, false - ���
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
	 * ��������� ���� �� � ������� ����� � ��������� �������
	 * @return - true - ����, false - ���
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

	// �� ����� (������� ��� ���������) �������� ������ � ����������� �� ���� �����
	private static Rate calculateRate(Rate rate, CurrencyRateType rateType, Currency from, Currency to)
	{
		// ������ ���� ��������� ������� �� ������������ �����: ����� ��������� ���� �� � ������������� ��������
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
			// ���� ������ �� ��������� � ���������� ������� - ���� �����-����
			if (!(from.compare(rurCurrency)) && !(to.compare(rurCurrency)))
			{
				// �������� �������� �����-�����:
				// 1. ����� ���� [currencyRateType] ������ from �� �����
				Rate oneRate = findRate(date, from, rurCurrency, office, currencyRateType, tarifPlanCodeType);
				// 2. ����� ���� [currencyRateType] ����� �� ������ to
				Rate twoRate = findRate(date, rurCurrency, to, office, currencyRateType, tarifPlanCodeType);

				if (oneRate == null || twoRate == null)
					return null;

				// 3. ����� ���������� ��������� ����� ������� ������� ������ ������������ �����
				BigDecimal rurForOneFrom = oneRate.getToValue().divide(oneRate.getFromValue(), RATE_SCALE, RoundingMode.HALF_EVEN);
				BigDecimal rurForOneTo = twoRate.getFromValue().divide(twoRate.getToValue(), RATE_SCALE, RoundingMode.HALF_EVEN);

				// 4. ������� ����� ���� - ������ � ����������� ������� ������ From - ��������� �������� rurForOneFrom � rurForOneTo
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
