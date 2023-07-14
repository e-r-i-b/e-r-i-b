package com.rssl.phizic.operations.graphics;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.PermissionUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rydvanskiy
 * @ created 21.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewFinanceOperation extends OperationBase
{
	private static final DepartmentService departmentService = new DepartmentService();

	// Согласно РО курс валюты должен быть относительно 99 ТБ
	private static final String REGION_ID = "99";
	private static final String DEFAULT_CURRENCY = "RUB";
	private List<AccountLink> accountLinks = Collections.emptyList();     //список линков вкладов
	private List<CardLink> cardLinks = Collections.emptyList();           //список линков карт
	private List<IMAccountLink> imAccountLinks = Collections.emptyList(); //список линков ОМС
	private List<SecurityAccountLink> securityAccountLinks = Collections.emptyList(); //список линков сберсертификатов
	private Map<SecurityAccountLink, SecurityAccount> securityAccounts = Collections.emptyMap();
	private boolean isBackError = false; // были ли ошибки при выполнении операции

	private final Office   office;
	private final Office   clientOffice;
	private final Currency defaultCurrency;

	/**
	 * @throws BusinessException
	 */
	public ViewFinanceOperation() throws BusinessException
	{
		this.office       = departmentService.findByCode(new ExtendedCodeImpl(REGION_ID, null, null));
		this.clientOffice = departmentService.findById(PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId());

		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			this.defaultCurrency = currencyService.findByAlphabeticCode(DEFAULT_CURRENCY);
		}
		catch (GateException ge)
		{
			throw new BusinessException(ge);
		}
	}

	/**
	 * задать список линков вкладов
	 *
	 * @param accountLinks список линков вкладов
	 */
	public void setAccountLinks(List<AccountLink> accountLinks)
	{
		this.accountLinks = accountLinks;
	}

	/**
	 * задать список линков карт
	 *
	 * @param cardLinks список линков карт
	 */
	public void setCardLinks(List<CardLink> cardLinks)
	{
		this.cardLinks = cardLinks;
	}

	/**
	 * задать список линков карт
	 *
	 * @param imAccountLinks список линков карт
	 */
	public void setImAccountLinks(List<IMAccountLink> imAccountLinks)
	{
		this.imAccountLinks = imAccountLinks;
	}

	/**
	 * задать список линков сберсертификатов
	 *
	 * @param securityAccountLinks список линков сберсертификатов
	 */
	public void setSecurityAccountLinks(List<SecurityAccountLink> securityAccountLinks)
	{
		this.securityAccountLinks = securityAccountLinks;
	}

	public void setSecurityAccounts(Map<SecurityAccountLink, SecurityAccount> securityAccounts)
	{
		this.securityAccounts = securityAccounts;
	}

	/**
	 * @return были ли ошибки при выполнении операции
	 */
	public boolean isBackError()
	{
		return isBackError;
	}

	/**
	 * Возвращает курсы валют Центрального Банка для продуктов определенных в операции и конверсия для которых производится
	 * в курсах ЦБ (вклады, карты и т.д).
	 *
	 * @return key - код валюты(RUB, EUR...), value - курс
	 *
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Map<String, CurrencyRate> getCurrencyRateByCentralBank() throws BusinessException, BusinessLogicException
	{
		HashMap<String, CurrencyRate> currencyRates = new HashMap<String, CurrencyRate>();

		// счета/вклады
		for (AccountLink accountLink : accountLinks)
		{
			Currency currency = accountLink.getAccount().getCurrency();

			if (currency == null)
			{
				isBackError = true;
				continue;
			}

			if (existCurrency(currency.getCode(), currencyRates))
			{
				continue;
			}

			CurrencyRate rate = getCurrencyRate(currency, defaultCurrency, CurrencyRateType.CB, office);
			if (rate != null)
			{
				currencyRates.put(currency.getCode(), rate);
			}
			else
			{
				isBackError = true;
			}
		}

		for (CardLink cardLink : cardLinks)
		{
			Currency currency = cardLink.getCard().getCurrency();
			if (currency == null)
			{
				isBackError = true;
				continue;
			}

			if (existCurrency(currency.getCode(), currencyRates))
			{
				continue;
			}

			CurrencyRate rate = getCurrencyRate(currency, defaultCurrency, CurrencyRateType.CB, office);
			if (rate != null)
			{
				currencyRates.put(currency.getCode(), rate);
			}
			else
			{
				isBackError = true;
			}
		}

		// сберсертификаты
		for (SecurityAccountLink securityAccountLink : securityAccountLinks)
		{
			SecurityAccount securityAccount = securityAccounts.get(securityAccountLink);

			Money nominalAmount = securityAccount.getNominalAmount();
			if (nominalAmount == null)
			{
				continue;
			}

			Currency currency =  nominalAmount.getCurrency();
			if (currency == null)
			{
				isBackError = true;
				continue;
			}

			if (existCurrency(currency.getCode(), currencyRates))
			{
				continue;
			}

			CurrencyRate rate = getCurrencyRate(currency, defaultCurrency, CurrencyRateType.CB, office);
			if (rate != null)
			{
				currencyRates.put(currency.getCode(), rate);
			}
			else
			{
				isBackError = true;
			}
		}

		return currencyRates;
	}

	/**
	 * Возвращает курсы валют типа BUY_REMOTE.
	 *
	 * @return key - код валюты(RUB, EUR...), value - курс
	 *
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Map<String, CurrencyRate> getCurrencyRateByRemoteBuy() throws BusinessException, BusinessLogicException
	{
		Map<String, CurrencyRate> currencyRate = new HashMap<String, CurrencyRate>();
		// металлические счета
		for (IMAccountLink imAccountLink : imAccountLinks)
		{
			Currency currency = imAccountLink.getImAccount().getCurrency();
			if (currency == null)
			{
				isBackError = true;
				continue;
			}

			if (existCurrency(currency.getCode(), currencyRate))
			{
				continue;
			}

			CurrencyRate rate = getCurrencyRate(currency, defaultCurrency, CurrencyRateType.BUY_REMOTE, clientOffice);
			if (rate != null)
			{
				currencyRate.put(currency.getCode(), rate);
			}
			else
			{
				isBackError = true;
			}
		}

		return currencyRate;
	}

	private boolean existCurrency(String currencyCode, Map<String, CurrencyRate> currencyRate)
	{
		return DEFAULT_CURRENCY.equals(currencyCode) || currencyRate.containsKey(currencyCode);
	}

	private CurrencyRate getCurrencyRate(Currency fromCurrency, Currency defaultCurrency, CurrencyRateType rateType, Office office) throws BusinessLogicException, BusinessException
	{
		CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);

		try
		{
			String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ? TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
			return currencyRateService.getRate(fromCurrency, defaultCurrency, rateType, office,tarifPlanCodeType);
		}
		catch (GateLogicException gle)
		{
			throw new BusinessLogicException(gle);
		}
		catch (GateException ge)
		{
			throw new BusinessException(ge);
		}
	}
}
