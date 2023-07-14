package com.rssl.phizic.operations.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.rates.BDCurrencyRateService;
import com.rssl.phizic.business.rates.Rate;
import com.rssl.phizic.business.rates.CurrencyRateHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;
import java.util.List;

/**
 * @author rydvanskiy
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowIMAccountRateOperation extends OperationBase implements ListEntitiesOperation
{
	private static final String[] IMA_CODES = new String[]{"A98", "A99", "A76", "A33"};
	private static final DepartmentService departmentService = new DepartmentService();
	private CurrencyRateService rateService = GateSingleton.getFactory().service(CurrencyRateService.class);
	private CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
	private static final BDCurrencyRateService bdCurrencyRateService = new BDCurrencyRateService();
	private Currency nationalCurrency;
	private Department department;


	public ShowIMAccountRateOperation() throws BusinessException
	{
		try
		{
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
	 * Курсы покупки/продажи актуальные на дату, по коду валюты и тарифному плану
	 * @param code - код валюты
	 * @param tarifPlanCodeType - тарифный план
	 * @return RateOfExchange
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private RateOfExchange getRateOfExchange (String code, String tarifPlanCodeType,
	                                          Calendar actualDate) throws BusinessLogicException, BusinessException
	{
		try
		{
			Currency fromCurrency = currencyService.findByAlphabeticCode(code);
			if (fromCurrency == null)
			{
				return null;
			}
			CurrencyRate buy;
			CurrencyRate sale;
			//Если передали дату актуальности для кусов (в админке)
			if (actualDate != null)
			{
				Rate buyRate = bdCurrencyRateService.getRate(actualDate, fromCurrency, nationalCurrency,
						department, CurrencyRateType.BUY_REMOTE, tarifPlanCodeType);
				buy = CurrencyRateHelper.makeCurrencyRate(buyRate);

				Rate saleRate = bdCurrencyRateService.getRate(actualDate, fromCurrency, nationalCurrency,
						department, CurrencyRateType.SALE_REMOTE, tarifPlanCodeType);
				sale = CurrencyRateHelper.makeCurrencyRate(saleRate);
			}
			else
			{
				buy = rateService.getRate(fromCurrency, nationalCurrency, CurrencyRateType.BUY_REMOTE,
						department, tarifPlanCodeType);
				sale = rateService.getRate(fromCurrency, nationalCurrency, CurrencyRateType.SALE_REMOTE,
						department, tarifPlanCodeType);
			}

			if(buy == null || sale == null)
			{
				return null;
			}
			
			return new RateOfExchange(buy, sale);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * Вернуть курсы покупки/продажи металов, актуальные на определенную дату
	 * @param tarifPlanCodeType - тип тарифного плана, для которого получать курсы. Если null- получить для всех тарифов
	 * @return GroupResult<String, RateOfExchange>
	 * @throws BusinessException
	 */
	public GroupResult<String, RateOfExchange> getIMARates (String tarifPlanCodeType,
	                                                        Calendar actualDate) throws BusinessException
	{
		GroupResult<String, RateOfExchange> result = new GroupResult<String, RateOfExchange>();

		ExtendedDepartment extendedDepartment = (ExtendedDepartment) department;
		if (!extendedDepartment.isEsbSupported())
		{
			result.putException("", new BusinessLogicException("Данное подразделение не поддерживает операцию получения курсов покупки/продажи металлов."));
			return result;
		}

		for (String code : IMA_CODES)
		{
			try
			{
				if (tarifPlanCodeType != null)
				{
					result.putResult(code, getRateOfExchange(code, tarifPlanCodeType, actualDate));
				}
				else
				{
					//Тянем курсы покупки/продажи данного металла актуальные на дату "actualDate", для всех тарифных планов
					List<TariffPlanConfig> tariffPlans = TariffPlanHelper.getAllTariffPlans();

					for (TariffPlanConfig tarifPlan: tariffPlans)
					{
						RateOfExchange rateOfExchange = getRateOfExchange(code, tarifPlan.getCode(), actualDate);
						if (rateOfExchange != null)
							result.putResult(code+tarifPlan.getCode(), rateOfExchange);
					}
				}
			}
			catch (BusinessLogicException e)
			{
				result.putException(code, e);
			}
		}
		return result;
	}
}
