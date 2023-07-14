package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.person.Person;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * СМС-команда "Курс"
 * @author Rtischeva
 * @ created 29.03.2013
 * @ $Author$
 * @ $Revision$
 */
class CurrencyRateCommand extends CommandBase
{
	private static final Map<String, String> currencyCodes = new LinkedHashMap<String, String>();

	private static final DepartmentService departmentService = new DepartmentService();

	static
	{
		currencyCodes.put("EUR", "EUR");
		currencyCodes.put("USD", "USD");
		currencyCodes.put("A98", "Золото");
		currencyCodes.put("A99", "Серебро");
		currencyCodes.put("A76", "Платина");
		currencyCodes.put("A33", "Палладий");
	}

	///////////////////////////////////////////////////////////////////////////

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		Person person = getPerson();
		Long departmentId = person.getDepartmentId();
		TextMessage message = new TextMessage(messageBuilder.buildCurrencyRatesMessage(getCurrencyRates(departmentId), currencyCodes));

		if (message!=null)
			sendMessage(message);

	}

	private List<CurrencyRate> getCurrencyRates(Long departmentId)
	{
		CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		List<CurrencyRate> currencyRates = new LinkedList<CurrencyRate>();

		try
		{
			Department department = departmentService.findById(departmentId);

			Currency rubCurrency = currencyService.getNationalCurrency();

			for (String currencyCode : currencyCodes.keySet())
			{
				Currency currency = currencyService.findByAlphabeticCode(currencyCode);
				String unknownTariffPlanCode = TariffPlanHelper.getUnknownTariffPlanCode();
				currencyRates.add(currencyRateService.getRate(currency, rubCurrency, CurrencyRateType.BUY_REMOTE,
						department, unknownTariffPlanCode));
				currencyRates.add(currencyRateService.getRate(currency, rubCurrency, CurrencyRateType.SALE_REMOTE,
						department, unknownTariffPlanCode));
			}
		}
		catch(GateException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (GateLogicException e)
		{
			throw new InternalErrorException(e);
		}

		return currencyRates;
	}

	@Override
	public String toString()
	{
		return "КУРС[]";
	}

	public String getCommandName()
	{
		return "CURRENCY_RATE";
	}
}
