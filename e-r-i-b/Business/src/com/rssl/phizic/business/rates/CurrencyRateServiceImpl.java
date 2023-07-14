package com.rssl.phizic.business.rates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.proxy.CacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.GateBusinessCacheConfig;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.DebugLogFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ author: filimonova
 * @ created: 12.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateServiceImpl extends AbstractService implements CurrencyRateService
{
	//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
	private static final Log debugLog;

	public static final String BUSINESS_RATE_CACHE = "BusinessCurrencyRateService.getRate";

	private static final BDCurrencyRateService currencyRateService = new BDCurrencyRateService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final Cache ratesCache;
	private static final Map<Serializable, Object> LOCKERS = new ConcurrentHashMap<Serializable, Object>();
	//������ ��� ������� ���������� ������������ �������� ��������� �����
	private static final Set<String> dynamicExchangeRateCurrenciesSet = new HashSet<String>(Arrays.asList("EUR", "USD", "RUB", "RUR"));

	static
	{
		ratesCache = CacheProvider.getCache(BUSINESS_RATE_CACHE);

		if ( ratesCache == null )
			throw new RuntimeException("�� ������ ��� ������ �����");

		debugLog = DebugLogFactory.getLog(Constants.LOG_MODULE_WEB);
	}

	public CurrencyRateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public CurrencyRate getRate(Currency from, Currency to, CurrencyRateType type, Office office,
	                            String tarifPlanCodeType) throws GateException, GateLogicException
	{
		return getRate(from, to, office, type, tarifPlanCodeType);
	}

	public CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		// �������� ���� ��������� �� ������ from � ������ to
		CurrencyRate rate = getRate(from.getCurrency(), to, office, CurrencyRateType.BUY_REMOTE, tarifPlanCodeType);
		if (rate != null)
			// ���������� ���������� ���� � ������� �����
			rate = CurrencyUtils.getFromCurrencyRate(from.getDecimal(), rate);
		return rate;
	}

	public CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		// �������� ���� ��������� �� ������ from � ������ to
		CurrencyRate rate = getRate(from, to.getCurrency(), office, CurrencyRateType.BUY_REMOTE, tarifPlanCodeType);
		if (rate != null)
			// ���������� ���������� ���� � ������� �����
			rate = CurrencyUtils.getToCurrencyRate(to.getDecimal(), rate);
		return rate;
	}

	private CurrencyRate getRate(Currency from, Currency to, Office office, CurrencyRateType type, String tarifPlanCodeType) throws GateException, GateLogicException
	{
		//����� �������� ��� �� => ���� ������� �� ��� �����, ����� ���� ���� � ���� �� ��, ���� �� �����,
		//�� ���� � �� �� ����� �� ��
		Department tb = null;
		try
		{
			tb = departmentService.getTBByOffice(office);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}

		CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(CurrencyRate.class);
		tarifPlanCodeType = StringHelper.isEmpty(tarifPlanCodeType) ? TariffPlanHelper.getUnknownTariffPlanCode() : tarifPlanCodeType;
		Serializable cacheKey = composer.getKey(new Object[]{from, to, type, tb, tarifPlanCodeType}, null);

		Application application = LogThreadContext.getApplication();

		//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
		StringBuilder builderMsg = new StringBuilder();
		builderMsg.append("|from="+from.getNumber());
		builderMsg.append("; to="+to.getNumber());
		builderMsg.append("; region="+office.getCode().getFields().get("region"));
		builderMsg.append("; department_id="+tb.getId());
		builderMsg.append("; rateType="+type.getId());
		builderMsg.append("; tarifPlanCodeType="+tarifPlanCodeType);
		builderMsg.append("; application="+(application != null ? application.name() : ""));
		builderMsg.append("|");
		debugLog.trace("CurrencyRateDebug: CurrencyRateServiceImpl.getRate ��������� �������� ����� ����� �� ���� �� ����������: "+builderMsg.toString());

		CurrencyRate rate = getFromCache(cacheKey);
		if (rate != null)
			return rate;

		synchronized (getLocker(cacheKey))
		{
			rate = getFromCache(cacheKey);
			if (rate != null)
				return rate;
			
			try
			{
				Rate lastRate = currencyRateService.getRate(Calendar.getInstance(), from, to, tb, type, tarifPlanCodeType);

				CurrencyRate result = null;
				if (lastRate != null)
				{
					result = CurrencyRateHelper.makeCurrencyRate(lastRate);

					if (result != null && lastRate.getId() != null)
					{
						DynamicExchangeRate dynamicExchangeRate = lastRate.getDynamicExchangeRate();
						//�������� ��������� ����� �������, ���� ��� �� ����������, ������ ��� ������������ �����
						if ( dynamicExchangeRate == null &&
							 dynamicExchangeRateCurrenciesSet.contains(lastRate.getFromCurrency().getCode().toUpperCase()) &&
							 dynamicExchangeRateCurrenciesSet.contains(lastRate.getToCurrency().getCode().toUpperCase()) )
						{
							Rate priorRate = currencyRateService.getRate(lastRate.getEffDate(), from, to, tb, type, tarifPlanCodeType);
							dynamicExchangeRate = CurrencyRateHelper.getDynamicExchangeRate(priorRate, lastRate);
						}
						result.setDynamicExchangeRate(dynamicExchangeRate);
						debugLog.trace("CurrencyRateDebug: CurrencyRateServiceImpl.getRate ���������� �������� ����� ����� � ���. ���������: "+builderMsg.toString());
						ratesCache.put(new Element(cacheKey, result));
					}
				}
				return result;
			}
			catch(BusinessException e)
			{
				throw new GateException(e);
			}
		}
	}

	private CurrencyRate getFromCache(Serializable cacheKey)
	{
		Element element  = ratesCache.get(cacheKey);
		if ( element != null )
		{
			CurrencyRate rate = (CurrencyRate) element.getObjectValue();
			Calendar expireDate = rate.getExpireDate();
			//���� ���� �� �������, �� ���������� �������� �� ����
			if (expireDate == null || expireDate.after(Calendar.getInstance()))
			{
				//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
				debugLog.trace("CurrencyRateDebug: CurrencyRateServiceImpl.getFromCache ���� ����� ��������� � ���� ratesCache. ����="+cacheKey);
				return rate;
			}
			//���� ���� �������, �� ������� �������� � ����.
		}

		//TODO BUG057746: ������ ������� ������ �����. C��� ���������� �� ���������� ������ �����. ����� ������ ����� ������� ��������.
		debugLog.trace("CurrencyRateDebug: CurrencyRateServiceImpl.getFromCache ���� ����� �� ��������� � ���� ratesCache. ����="+cacheKey);
		return null;
	}

	private Object getLocker(Serializable cacheKey)
	{
		if (!LOCKERS.containsKey(cacheKey))
		{
			synchronized (LOCKERS)
			{
				if (!LOCKERS.containsKey(cacheKey))
					LOCKERS.put(cacheKey, new Object());
			}
		}

		return LOCKERS.get(cacheKey);
	}
}
