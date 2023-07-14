package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Erkin
 * @ created 29.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyUtils
{
	private static final double PERCENT = 0.05; // 5%
	/**
	 * Карта < ISO-код валюты, символом валюты >. Для металлов символ валюты - граммы.
	 */
	private static final Map<String, String> currencySignMap = new HashMap<String, String>();
	/**
	 * Карта < ISO-код металла, нормализованный код металла >
	 */
	private static final Map<String, String> metalCodeMap = new HashMap<String, String>();

	/**
	 *  Сет с валютами, для операций по которым при льготном тарифном плане отображается сообщение клиенту о льготном курсе
	 */
	private static final Set<String> CURRENCY_SHOW_PRIVILEGED_MSG_SET = new HashSet<String>(Arrays.asList("EUR", "USD", "A98", "A99", "A76", "A33"));

	static
	{
		currencySignMap.put("RUB", "руб.");
		currencySignMap.put("RUR", "руб.");
		currencySignMap.put("A99", "г");
		currencySignMap.put("A98", "г");
		currencySignMap.put("A76", "г");
		currencySignMap.put("A33", "г");
		currencySignMap.put("USD", "$");
		currencySignMap.put("EUR", "€");

		metalCodeMap.put("A33", "PDR");
		metalCodeMap.put("A76", "PTR");
		metalCodeMap.put("A98", "AUR");
		metalCodeMap.put("A99", "ARG");
	}

	public static Map<String, String> getCurrencySignMap()
	{
		return currencySignMap;
	}

	public static Set<String> getCurrencyShowPremierMsgSet()
	{
		return Collections.unmodifiableSet(CURRENCY_SHOW_PRIVILEGED_MSG_SET);
	}

	/**
	 * Возвращает символ валюты по ее ISO-коду.
	 * @param currencyCode ISO-код валюты
	 * @return Символ валюты
	 */
	public static String getCurrencySign(String currencyCode)
	{
		String sign = currencySignMap.get(currencyCode);
		return sign == null ? currencyCode : sign;
	}
	
	/**
	 * Проверяет коды на принадлежность одной валюты
	 * @param currencyCode1 - код валюты
	 * @param currencyCode2 - код валюты
	 * @return true, если коды принадлежат к одной валюте
	 */
	public static boolean isSameCurrency(String currencyCode1, String currencyCode2)
	{
		if (StringHelper.isEmpty(currencyCode1))
			throw new IllegalArgumentException("Аргумент 'currencyCode1' не может быть пустым");
		if (StringHelper.isEmpty(currencyCode2))
			throw new IllegalArgumentException("Аргумент 'currencyCode2' не может быть пустым");

		currencyCode1 = normalizeCurrencyCode(currencyCode1);
		currencyCode2 = normalizeCurrencyCode(currencyCode2);

		return currencyCode1.equals(currencyCode2);
	}

	public static String normalizeCurrencyCode(String currencyCode)
	{
		if (StringHelper.isEmpty(currencyCode))
			return "";

		currencyCode = currencyCode.toUpperCase();

		if(currencyCode.equals("RUB"))
			return "RUR";
		
		String normalizedCode = metalCodeMap.get(currencyCode);
		return normalizedCode == null ? currencyCode : normalizedCode;
	}

	/**
	 * Переводит ISO-код металла в нормализованный код.
	 * @param currencyCode
	 * @return
	 */
	public static String normalizeMetalCode(String currencyCode)
	{
		if (StringHelper.isEmpty(currencyCode))
			throw new IllegalArgumentException("Аргумент 'currencyCode' не может быть пустым");

		currencyCode = currencyCode.toUpperCase();
		String normalizedCode = metalCodeMap.get(currencyCode);
		return normalizedCode == null ? currencyCode : normalizedCode;
	}

	/**
	 * Конвертирует сумму из суммы в валюте <rate>.from в сумму в валюте <rate>.to
	 * Использовать в том месте, где нужно помножить сумму на курс
	 * @param amount - сумма
	 * @param rate - курс валюты
	 * @return <amount> * <rate>.toValue() / <rate>.fromValue()
	 */
	public static BigDecimal convert(BigDecimal amount, CurrencyRate rate)
	{
		if (rate.getFromCurrency().getCode().equals("RUB"))
		{
			// Если клиент вводит рубли, нужно делить, чтобы получить тот же результат, что и в js на форме
			return amount.divide(rate.getReverseFactor(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
		}
		else
		{
			return amount.multiply(rate.getFactor());
		}
	}

	/**
	 * Конвертирует сумму из суммы в валюте <rate>.to в сумму в валюте <rate>.from
	 * Использовать в том месте, где нужно поделить сумму на курс
	 * @param amount - сумма
	 * @param rate - курс валюты
	 * @return <amount> * <rate>.fromValue() / <rate>.toValue()
	 */
	public static BigDecimal reverseConvert(BigDecimal amount, CurrencyRate rate)
	{
		if (rate.getToCurrency().getCode().equals("RUB"))
		{
			// Если клиент вводит рубли, нужно делить, чтобы получить тот же результат, что и в js на форме
			return amount.divide(rate.getFactor(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
		}
		else
		{
			return amount.multiply(rate.getReverseFactor());
		}
	}

	/**
	 * Добавляет к курсу валют + 5%
	 * @param rate
	 * @return - курс валют + 5%
	 */
	public static CurrencyRate convertAddProcent(CurrencyRate rate)
	{
		BigDecimal multiply = rate.getToValue().multiply(new BigDecimal(PERCENT));
		BigDecimal to = rate.getToValue().add(multiply);
		return new CurrencyRate(rate.getFromCurrency(), rate.getFromValue(), rate.getToCurrency(), round(to), rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * Возвращает курс, который в поле fromValue содержит <from>
	 * @param from - значение для поля курса fromValue
	 * @param rate - курс
	 * @return адаптированный под <from> курс
	 */
	public static CurrencyRate getFromCurrencyRate(BigDecimal from, CurrencyRate rate)
	{
		// Вычисляем сумму по полученному курсу
		BigDecimal to = convert(from, rate);
		// От метода требуется, чтобы возвращаемый курс содержал округлённые <from> и <to>
		BigDecimal roundedFrom = round(from);
		BigDecimal roundedTo = round(to);
		return new CurrencyRate(rate.getFromCurrency(), roundedFrom, rate.getToCurrency(), roundedTo, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * Возвращает курс, который в поле fromValue содержит <from> с заданным округлением суммы.
	 * @param from - значение для поля курса fromValue
	 * @param rate - курс
	 * @return адаптированный под <from> курс
	 */
	private static CurrencyRate getFromCurrencyRateWithRoundScale(BigDecimal from, CurrencyRate rate, int scale)
	{
		// Вычисляем сумму по полученному курсу
		BigDecimal to = from.divide(rate.getReverseFactor(scale), scale, CurrencyRate.ROUNDING_MODE);
		return new CurrencyRate(rate.getFromCurrency(), from, rate.getToCurrency(), to, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * Возвращает курс, который в поле toValue содержит <to>
	 * @param to - значение для поля курса toValue
	 * @param rate - курс
	 * @return адаптированный под <to> курс
	 */
	public static CurrencyRate getToCurrencyRate(BigDecimal to, CurrencyRate rate)
	{
		// Вычисляем сумму по полученному курсу
		BigDecimal from = reverseConvert(to, rate);
		// От метода требуется, чтобы возвращаемый курс содержал округлённые <from> и <to>
		BigDecimal roundedFrom = round(from);
		BigDecimal roundedTo = round(to);
		return new CurrencyRate(rate.getFromCurrency(), roundedFrom, rate.getToCurrency(), roundedTo, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * Возвращает курс, который в поле toValue содержит <to> с заданным округлением суммы.
	 * @param to - значение для поля курса toValue
	 * @param rate - курс
	 * @return адаптированный под <to> курс
	 */
	private static CurrencyRate getToCurrencyRateWithRoundScale(BigDecimal to, CurrencyRate rate, int scale)
	{
		// Вычисляем сумму по полученному курсу
		BigDecimal from = to.divide(rate.getFactor(scale), scale, CurrencyRate.ROUNDING_MODE);
		return new CurrencyRate(rate.getFromCurrency(), from, rate.getToCurrency(), to, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * Возвращает курс, который в поле toValue содержит <to> с округлением необходимым для платежей с вкладов.
	 * @param to - значение для поля курса toValue
     * @param rate - курс
	 * @return адаптированный под <from> курс
	 */
	public static CurrencyRate getToCurrencyRateForAccountPayment(BigDecimal to, CurrencyRate rate)
	{
		return getToCurrencyRateWithRoundScale(to, rate, 13);
	}

	/**
	 * Округляет сумму
	 * @param amount - сумма
	 * @return - сумма с округлением, принятым в системе по-умолчанию
	 */
	private static BigDecimal round(BigDecimal amount)
	{
		return amount.setScale(CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
	}

	/**
	 * Определяет является счёт металлическим или нет
	 * @param currencyCode - ISO-код
	 * @return true, если счёт металлический
	 */
	public static boolean isMetallAccount(String currencyCode)
	{
		if (StringHelper.isEmpty(currencyCode))
			return false;
		return metalCodeMap.keySet().contains(currencyCode);
	}

	/**
	 * @param currency - валюта (never null)
	 * @return true, если валюта - рубли (RUR или RUB)
	 */
	public static boolean isRUR(Currency currency)
	{
		String code = currency.getCode();
		return code.equalsIgnoreCase("RUR") || code.equalsIgnoreCase("RUB");
	}

	/**
	 * @param currency - валюта (never null)
	 * @return true, если валюта - евро
	 */
	public static boolean isEUR(Currency currency)
	{
		String code = currency.getCode();
		return code.equalsIgnoreCase("EUR");
	}

	/**
	 * @param currency - валюта (never null)
	 * @return true, если валюта - доллары
	 */
	public static boolean isUSD(Currency currency)
	{
		String code = currency.getCode();
		return code.equalsIgnoreCase("USD");
	}
}
