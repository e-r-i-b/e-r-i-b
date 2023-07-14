package com.rssl.phizic.business.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Утилиты для работы с денюжками
 */
public class MoneyUtil
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);
	private static final double amountRoundConstant = 0.5/1024;

	// Карта, определяющая количество значимых символов после запятой для суммы платежа в конкретной валюте
	private static final Map<String, Integer> scaleMap = new HashMap<String, Integer>(4);
	static
	{
		scaleMap.put("A33", 1);
		scaleMap.put("A76", 1);
		scaleMap.put("A98", 1);
		scaleMap.put("A99", 0);
	}

	/**
	 * @param account ОМС
	 * @return Баланс в национальной валюте (рубли)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Money getBalanceInNationCurrency(IMAccount account, String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
			if (account == null)
				return null;
			String checkedTariffPlanCodeType = TariffPlanHelper.getTariffPlanCode(tarifPlanCodeType);
			Currency nationCurrency = currencyService.getNationalCurrency();
			CurrencyRate rate = currencyRateService.convert(account.getBalance(), nationCurrency, account.getOffice(),
					checkedTariffPlanCodeType);
			if (rate == null)
				return null;
			return new Money(rate.getToValue(), nationCurrency);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @param account счет
	 * @return баланс в национальной валюте
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Money getBalanceInNationCurrency(Account account, String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
			if (account == null)
				return null;
			String checkedTariffPlanCodeType = TariffPlanHelper.getTariffPlanCode(tarifPlanCodeType);
			Currency nationCurrency = currencyService.getNationalCurrency();
			CurrencyRate rate = currencyRateService.convert(account.getBalance(), nationCurrency, account.getOffice(),
					checkedTariffPlanCodeType);
			if (rate == null)
				return null;
			return new Money(rate.getToValue(), nationCurrency);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Сравнивает 2 денюжки по суммам с учётом разновалютности
	 * @param one - одна деньга
	 * @param another - другая деньга
	 * @param office - офис, курсы валют которого используеются для конвертации
	 * @return 0 => денюжки эквивалетны
	 * больше 0 => сумма <one> больше, чем <another>
	 * меньше 0 => сумма <one> меньше, чем <another>
	 */
	public static int compare(Money one, Money another, Office office, String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		BigDecimal oneAmount = one.getDecimal();
		BigDecimal anotherAmount = another.getDecimal();
		Currency oneCurrency = one.getCurrency();
		Currency anotherCurrency = another.getCurrency();

		try
		{
			// (1) Валюта разная => приводим вторую деньгу к первой
			if (!oneCurrency.compare(anotherCurrency))
			{
				// курс для перевода деньги <another> в валюту <oneCurrency>
				CurrencyRate rate = GateSingleton.getFactory().service(CurrencyRateService.class).convert(another,
						oneCurrency, office, tarifPlanCodeType);
				anotherAmount = CurrencyUtils.convert(anotherAmount, rate);
			}

			// 2. Сравниваем суммы
			return oneAmount.compareTo(anotherAmount);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * округление суммы в зависимости от валюты
	 * @param decimal значение суммы
	 * @param currency валюта
	 * @return Money
	 */
	public static Money roundForCurrency(BigDecimal decimal, Currency currency)
	{
		return roundForCurrency(decimal, currency, RoundingMode.HALF_EVEN);
	}

	/**
	 * округление суммы в зависимости от валюты в сторону уменьшения
	 * @param decimal значение суммы
	 * @param currency валюта
	 * @return Money
	 */
	public static Money roundDownForCurrency(BigDecimal decimal, Currency currency)
	{
		return roundForCurrency(decimal, currency, RoundingMode.DOWN);

	}

	private static Money roundForCurrency(BigDecimal decimal, Currency currency, RoundingMode roundingMode)
	{
		Integer scale = scaleMap.get(currency.getCode());
		if (scale == null)
			return new Money(decimal, currency);
		return new Money(decimal.setScale(scale, roundingMode), currency);
	}

	/**
	 * Округление суммы для списания.
	 * Алгоритм округления:
     * остаток = полученная сумма минус полученная сумма, округленная до 2х знаков после запятой
     * значение = остаток*100, сравниваем с константой 0,5/1024
     * 1) если значение больше константы то добавляем к сумме 0.01
     * 2) если менше либо равно то сумма откруггленная до 2х знаков после запятной
	 */
	public static Money roundChargeOffAmount(BigDecimal amountDecimal, Currency currency)
	{
		//вычисляем результирующую сумму = Целая часть и 2 знака после запятой.
		double amountDouble = Double.valueOf((long) (amountDecimal.doubleValue() * 100)) / 100;

		BigDecimal resultSum = BigDecimal.valueOf(amountDouble);
		//вычисляем остаток
		BigDecimal rest = amountDecimal.subtract(resultSum);
		if (amountRoundConstant < rest.doubleValue() * 100)
			return new Money(resultSum.add(new BigDecimal(0.01)), currency);

		return new Money(resultSum, currency);
	}

	/**
	 * Округление суммы для зачисления.
	 * Алгоритм округления: отбрасываем все что после сотых. Зачисляем клиенту меньшую сумму.
	 */
	public static Money roundDestinationAmount(BigDecimal amountDecimal, Currency currency)
	{
		BigDecimal resultSum = BigDecimal.valueOf(Double.valueOf((long) (amountDecimal.doubleValue() * 100)) / 100);
		return new Money(resultSum, currency);
	}

	/**
	 * Округление суммы с отбрасыванием всего, что после сотых. Т.е. такое не правильно с точки зрения математики округление.
	 * @param amountDecimal
	 * @return
	 */
	public static String roundDestinationAmount(String amountDecimal)
	{
		if (StringHelper.isEmpty(amountDecimal))
			return null;
		Money roundMoney = roundDestinationAmount(new BigDecimal(amountDecimal), new CurrencyImpl());
		return roundMoney.getDecimal().toPlainString();
	}

	/**
	 * Сравнение двух Money.
	 * Если оба null, то возвращает true.
	 * Если только один из них null, то возвращает false.
	 * Если оба не null, то сравнение передается методу Money.equals
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static boolean equalsNullIgnore(Money val1, Money val2)
	{
		if (val1 == null && val2 == null)
			return true;

		if (val1 == null || val2 == null)
			return false;

		return val1.equals(val2);
	}

	/**
	 * Получить сумму в национальной валюте
	 * @param originalMoney - сумма и валюта оригинала
	 * @param nationalCurrency - национальная валюта, валюта в номинале которой хотим получить сумму
	 * @param office - департамент, в рамках которого хотим получить курс конверсии
	 * @param rateType - тип курса
	 * @return  
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */

	public static Money getBalanceInNationalCurrency(Money originalMoney, Currency nationalCurrency, Office office,
	                                                 CurrencyRateType rateType, String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		if(originalMoney.getCurrency().compare(nationalCurrency))
			return originalMoney;

		CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);
		try
		{
			CurrencyRate rate = currencyRateService.getRate(originalMoney.getCurrency(), nationalCurrency, rateType,
					office, tarifPlanCodeType);
			BigDecimal nationalAmount = CurrencyUtils.convert(originalMoney.getDecimal(),rate);
			return new Money(nationalAmount,rate.getToCurrency());
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
	 * @return возвращает национальную валюту
	 * @throws BusinessException если ловим GateException, то оборачиваем его в BusinessException  
	 */
	public static Currency getNationalCurrency() throws BusinessException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			return currencyService.getNationalCurrency();
		}
		catch (GateException e)
		{
			throw new BusinessException("Не удалось получить национальную валюту",e);
		}
	}

	/**
	 * Вернуть валюту по числовому коду
	 * @param currencyCode числовой код
	 * @return валюта
	 * @throws BusinessException
	 */
	public static Currency findCurrencyByNumericCode(String currencyCode) throws BusinessException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			return currencyService.findByNumericCode(currencyCode);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
