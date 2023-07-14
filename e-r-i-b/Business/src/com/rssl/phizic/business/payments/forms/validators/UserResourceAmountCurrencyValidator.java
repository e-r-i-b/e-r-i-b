package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.DocumentConfig;

import java.util.Map;

/**
 * Проверка, что сумма источника(карты) не меньше, чем проверяемая
 * Проверка работает с учетом, что суммы могут быть в разных валютах
 * @author Mescheryakova
 * @ created 24.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class UserResourceAmountCurrencyValidator  extends  UserResourceAmountValidator
{
	private static final String FIELD_CURRENCY = "currency";
	private static final String FIELD_DEPARTMENT_ID = "currentDepartmentId";

	private final CurrencyRateService currencyRateService= GateSingleton.getFactory().service(CurrencyRateService.class);
	private final DepartmentService departmentService = new DepartmentService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		ExternalResourceLink resourceLink = (ExternalResourceLink) retrieveFieldValue(FIELD_RESOURCE, values);
		if (isStoredResource(resourceLink))
			return true;

		String currencyAmount = (String) retrieveFieldValue(FIELD_CURRENCY, values);
		if (currencyAmount == null)
			throw new TemporalDocumentException("Не задана валюта суммы");
		
		CardLink cardLink = (CardLink) retrieveFieldValue(FIELD_RESOURCE, values);
		Currency currencyCard = getCardCurrency(cardLink);

		// если валюта карты и валюта совпадают, то никаких преобразований не требуется
		if (currencyAmount.equals(currencyCard.getCode()))
		{
			return super.validate(values);
		}

		// преобразуем введенную сумму в нужную нам валюту
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency currency = currencyService.findByAlphabeticCode(currencyAmount);
			Money money = new Money(getExpectedAmount(values), currency);
			Long departmentId = Long.valueOf((String) retrieveFieldValue(FIELD_DEPARTMENT_ID, values));
			Department department = departmentService.findById(departmentId);

			if (money == null || currencyCard == null || department == null)
				throw new TemporalDocumentException("Недостаточно данных для конвертации суммы в валюту карты");

			CurrencyRate currencyRate = convertByCB(money, currencyCard, department);
			if (currencyRate == null)
				throw new TemporalDocumentException("Не удалось получить курс валют");

			Money moneyConvert = new Money(currencyRate.getToValue(), currencyRate.getToCurrency());

			if (moneyConvert == null)
				throw new TemporalDocumentException("После конвертации суммы в валюту карты вернулось null");

			return getCardAmount(cardLink).compareTo(moneyConvert.getDecimal()) >= 0;
		}
		catch(BusinessException e)
		{
			throw new TemporalDocumentException("Ошибка конвертации суммы в валюту карты", e);
		}
		catch(GateException e)
		{
			throw new TemporalDocumentException("Ошибка получения валюты суммы", e);
		}
	}

	protected Currency getCardCurrency(CardLink cardLink) throws TemporalDocumentException
	{
		Card card = getCard(cardLink);
		return card.getCurrency();

	}

	/**
	 * Возвращает курс ЦБ по ТБ + 5%, либо если курса для ТБ не найдено, то курс для ТБ г. Москвы + 5%
	 * @param from  - валюта, из которой конвертируют
	 * @param to  - валюта, в которую конвертируют
	 * @param office - Офис, для ТБ которого будем получать курс
	 * @return найденный курс валют, либо null
	 * @throws BusinessException
	 */	
	private CurrencyRate convertByCB(Money from, Currency to, Department office) throws BusinessException
	{
		if (office == null)
			return null;

		Department department;
		try
		{
			// получаем ТБ
			department = departmentService.getTB(office);
		}
		catch(BusinessException e)
		{
			return null;
		}

		CurrencyRate rate = null;

		try
		{
			String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
					TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
			// получаем курс конверсии ЦБ по ТБ и тарифному плану
			rate = currencyRateService.getRate(from.getCurrency(), to, CurrencyRateType.CB, department,
					tarifPlanCodeType);
		}
		catch(GateLogicException e)
		{
			throw new BusinessException("Ошибка получения курсов валют", e);
		}
		catch(GateException e)
		{
			throw new BusinessException("Ошибка получения курсов валют", e);
		}

		String regionCode = ConfigFactory.getConfig(DocumentConfig.class).getCbTbMoscow();

		if (rate == null && !department.getCode().getFields().get("region").equals(regionCode))
		{
			try
			{
				// получаем ТБ Москвы
				department = departmentService.getDepartmentTBByTBNumber(regionCode);
			}
			catch(BusinessException e)
			{
				return null;
			}

			// запрашиваем все опять
			return convertByCB(from, to, department);
		}

		if (rate != null)
		{
			rate = CurrencyUtils.convertAddProcent(rate);
			rate = CurrencyUtils.getFromCurrencyRate(from.getDecimal(), rate);
		}
		return rate;
	}
}
