package com.rssl.phizic.operations.currency;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.rates.BDCurrencyRateService;
import com.rssl.phizic.business.rates.CurrencyRateHelper;
import com.rssl.phizic.business.rates.Rate;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ima.RateOfExchange;

import java.util.*;

/**
 * @author: Pakhomova
 * @created: 11.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowCurrenciesRateOperation extends OperationBase implements ListEntitiesOperation
{
	//Коды металлов игнорируемых при отображении курсов
	private static final String[] IGNORE_IMA_CODES = new String[]{"A98", "A99", "A76", "A33"};
	private static final DepartmentService departmentService = new DepartmentService();
	private static final BDCurrencyRateService bdCurrencyRateService = new BDCurrencyRateService();
	private Currency nationalCurrency;
	private Department department;


	public ShowCurrenciesRateOperation() throws BusinessException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			this.nationalCurrency = currencyService.getNationalCurrency();
		}
		catch (GateException e)
		{
			throw new BusinessException ("Невозможно получить национальнцую валюту", e);
		}
	}
	/**
	 * Инициализируем операцию с явным указанием ID департамента
	 * @param departmentId
	 * @throws BusinessException
	 */
	public void initialize(Long departmentId) throws BusinessException
	{
		this.department = departmentService.findById(departmentId);
	}

	/**
	 * Инициализируем операцию. Департамент достаем из контекста персоны.
	 * @throws BusinessException
	 */
	public void initialize () throws BusinessException
	{
		Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
		this.department = departmentService.findById(departmentId);

	}

	/**
	 * Курсы покупки/продажи (кроме металлов) актуальные на дату
	 * @param actualDate
	 * @return RateOfExchange
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 * @throws BusinessException
	 */
	private GroupResult<Currency, Map<String, RateOfExchange>> getRateOfExchangeList (Calendar actualDate) throws BusinessException
	{
		GroupResult<Currency, Map<String, RateOfExchange>> currenciesRatesByTarifPlan =
			new GroupResult<Currency, Map<String, RateOfExchange>>();
		Map<Currency, Map<String, List<CurrencyRate>>> mapCurrencyRates =
				new HashMap<Currency, Map<String, List<CurrencyRate>>>();

		CurrencyRateType rateType =  CurrencyRateType.BUY_REMOTE;
		if (actualDate != null)
		{
			//Список всех курсов покупки/ продажи валют (кроме металлических) где участвует рубль в разрезе тарифных планов
			List<Rate> allCurrenciesRateList = bdCurrencyRateService.getAllCurrenciesRatesList(actualDate, nationalCurrency,
				department, rateType, Arrays.asList(IGNORE_IMA_CODES));

			TariffPlanHelper tariffPlanHelper = new TariffPlanHelper();
			//Разбор списка курсов по валютам и тарифным планам
			for (Rate rate: allCurrenciesRateList)
			{
				Currency currency = !rate.getFromCurrency().compare(nationalCurrency) ? rate.getFromCurrency() : rate.getToCurrency();
				Map<String, List<CurrencyRate>> mapCurrencyRateByTarifPlan = mapCurrencyRates.get(currency);

				if (mapCurrencyRateByTarifPlan == null)
					mapCurrencyRateByTarifPlan = new HashMap<String, List<CurrencyRate>>();

				List<CurrencyRate> currencyRatesList = mapCurrencyRateByTarifPlan.get(tariffPlanHelper.getCodeBySynonym(rate.getTarifPlanCodeType()));
				if (currencyRatesList == null)
					currencyRatesList = new ArrayList<CurrencyRate>();

				CurrencyRate currencyRate = CurrencyRateHelper.makeCurrencyRate(rate);
				currencyRatesList.add(currencyRate);
				mapCurrencyRateByTarifPlan.put(currencyRate.getTarifPlanCodeType(), currencyRatesList);
				mapCurrencyRates.put(currency, mapCurrencyRateByTarifPlan);

				//Сразу ищем курс покупки и продажи
				if (currencyRatesList.size() > 1)
				{
					CurrencyRate buy = null;
					CurrencyRate sale = null;
					for (CurrencyRate tarifPlanCurrencyRate :currencyRatesList)
					{
						CurrencyRateType currencyRateType = tarifPlanCurrencyRate.getType();

						if (currencyRateType == rateType)
							buy = tarifPlanCurrencyRate;
						else if (currencyRateType == Rate.inverseCurrencyRateType(rateType))
							sale = tarifPlanCurrencyRate;
					}

					if(buy != null && sale != null)
					{
						RateOfExchange rateOfExchange = new RateOfExchange(buy, sale);
						Map<Currency, Map<String, RateOfExchange>> resultMap = currenciesRatesByTarifPlan.getResults();
						Map<String, RateOfExchange> tarifPlanRate = resultMap.get(currency);

						if (tarifPlanRate == null)
							tarifPlanRate = new HashMap<String, RateOfExchange>();

						tarifPlanRate.put(buy.getTarifPlanCodeType(),rateOfExchange);
						resultMap.put(currency, tarifPlanRate);
					}
				}
			}
		}
		return currenciesRatesByTarifPlan;
	}

	/**
	 * Курсы покупки/продажи валют для разных тарифов, актуальные на определенную дату
	 * @param actualDate
	 * @return GroupResult<Currency, Map<TarifPlanCodeType, RateOfExchange>>
	 * @throws BusinessException
	 */
	public GroupResult<Currency, Map<String, RateOfExchange>> getAllCurrenciesRatesByTarifPlan (Calendar actualDate) throws BusinessException, BusinessLogicException
	{
		GroupResult<Currency, Map<String, RateOfExchange>> currenciesRatesByTarifPlan =
				new GroupResult<Currency, Map<String, RateOfExchange>>();

		ExtendedDepartment extendedDepartment = (ExtendedDepartment) department;

		if (!extendedDepartment.isEsbSupported())
		{
			currenciesRatesByTarifPlan.putException(null,
					new BusinessLogicException("Данное подразделение не поддерживает операцию получения курсов покупки/продажи валют."));
			return currenciesRatesByTarifPlan;
		}

		return getRateOfExchangeList(actualDate);
	}
}
