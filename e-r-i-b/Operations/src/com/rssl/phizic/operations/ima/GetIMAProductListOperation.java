package com.rssl.phizic.operations.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.business.ima.IMAProductService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.PermissionUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Операция для получения списка ОМС для открытия
 * @author Pankin
 * @ created 27.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class GetIMAProductListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final IMAProductService IMA_PRODUCT_SERVICE = new IMAProductService();
	private static final DepartmentService DEPARTMENT_SERVICE = new DepartmentService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);
	private static final String NO_CURRENCY_RATE = "Отсутствуют курсы по всем металлам";

	private List<IMAProduct> imaProducts;
	private Map<Currency, CurrencyRate> currencyRates;

	/**
	 * Инициализация операции
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
		Department department = DEPARTMENT_SERVICE.findById(departmentId);
		ExtendedDepartment extendedDepartment = (ExtendedDepartment) department;
		if (!extendedDepartment.isEsbSupported())
		{
			throw new BusinessException("Данное подразделение не поддерживает операцию получения курсов покупки/продажи металлов.");
		}

		imaProducts = IMA_PRODUCT_SERVICE.getAll();
		currencyRates = new HashMap<Currency, CurrencyRate>();

		//если в БД ничего нет, то делать больше нечего
		if (CollectionUtils.isEmpty(imaProducts))
			return;

		CurrencyRateService rateService = GateSingleton.getFactory().service(CurrencyRateService.class);
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			Currency nationalCurrency = currencyService.getNationalCurrency();
			for (Iterator<IMAProduct> it = imaProducts.iterator(); it.hasNext();)
			{
				IMAProduct product = it.next();
				Currency currency = product.getCurrency();

				String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
						TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
				CurrencyRate buy = rateService.getRate(currency, nationalCurrency, CurrencyRateType.SALE_REMOTE, 
						department, tarifPlanCodeType);
				if (buy != null)
					currencyRates.put(currency, buy);
				else
					it.remove();
			}

			if (currencyRates == null || currencyRates.isEmpty())
			{
				log.error(NO_CURRENCY_RATE);
				ApplicationConfig applicationConfig = ApplicationConfig.getIt();
				if (applicationConfig.getApplicationInfo().isNotMobileApi())
				{
					throw new BusinessException(NO_CURRENCY_RATE);
				}
			}
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
	 * Получить список ОМС
	 * @return список ОМС
	 */
	public List<IMAProduct> getImaProducts()
	{
		return Collections.unmodifiableList(imaProducts);
	}

	/**
	 * Получить курсы покупки металла
	 * @return карта с курсами
	 */
	public Map<Currency, CurrencyRate> getCurrencyRates()
	{
		return Collections.unmodifiableMap(currencyRates);
	}
}
