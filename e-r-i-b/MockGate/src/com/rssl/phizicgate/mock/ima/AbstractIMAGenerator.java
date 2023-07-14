package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizicgate.mock.payments.CurrencyImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
abstract class AbstractIMAGenerator
{
	private Random random;
	private CurrencyService currencyService;

	static final String CURRENCY_CODE_RUB = "RUB";
	static final String CURRENCY_CODE_AUR = "XAU";

	AbstractIMAGenerator()
	{
		random = new Random();
	}

	protected abstract Object generate();

	protected String generateNumber(int lenght)
	{
		return RandomHelper.rand(lenght, RandomHelper.DIGITS);
	}

	protected Money generateMoney(String code)
	{
		BigDecimal value = null;

		if (code != null)
		{
			String prefix = null;
			String postfix = null;

			if (code.equals(CURRENCY_CODE_RUB))
			{
				prefix  = RandomHelper.rand(5, RandomHelper.DIGITS);
				postfix = RandomHelper.rand(2, RandomHelper.DIGITS);
			}
			else
			{

				prefix  = RandomHelper.rand(2, RandomHelper.DIGITS);
				postfix = RandomHelper.rand(2, RandomHelper.DIGITS);
			}
			value = new BigDecimal(Double.parseDouble(prefix + "." + postfix));

			return new Money(value, getCurrency(code));
		}
		return null;
	}

	protected Money generateEmptyMoney(String code)
	{
		return new Money(new BigDecimal(0.00), getCurrency(code));
	}

	protected Currency getCurrency(String currencyAlphabeticCode)
	{
		try
		{
			if (currencyAlphabeticCode.equals("XAU"))
			{
				CurrencyImpl currency = new CurrencyImpl();
				currency.setName("Золото");
				currency.setCode("XAU");
				currency.setNumber("959");
				return currency;
			}
			else
			{
				return getCurrencyService().findByAlphabeticCode(currencyAlphabeticCode);
			}
		}
		catch (GateException ignored) {}
		
		return null;
	}

	protected Calendar generateDate()
	{
		Calendar date = DateHelper.getCurrentDate();

		int dd = 0;
		do
		{
			dd = getRandom().nextInt( date.get(Calendar.DAY_OF_MONTH) ) + 1;
		}
		while (dd == 0);

		int mm = 0;
		do
		{
			mm = getRandom().nextInt(date.get(Calendar.MONTH)) + 1;
		}
		while (mm == 0);

		date.set(2010, mm, dd);

		if (date.getTimeInMillis() > DateHelper.getCurrentDate().getTimeInMillis())
		{
			return generateDate();
		}
		return date;
	}

	protected CurrencyService getCurrencyService()
	{
		if (currencyService == null)
		{
			currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		}
		return currencyService;
	}

	protected Random getRandom()
	{
		return random;
	}
}