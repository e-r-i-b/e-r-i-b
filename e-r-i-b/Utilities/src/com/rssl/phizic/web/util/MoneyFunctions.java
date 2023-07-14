package com.rssl.phizic.web.util;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ author: filimonova
 * @ created: 26.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class MoneyFunctions
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final char DECIMAL_SEPARATOR = ',';
	private static final char GROUPING_SEPARATOR = ' ';
	private static final boolean ROUND = false;
	private static final boolean USE_GROUPING = true;
	private static final int SCALE = 2;

	public static java.lang.String getCurrencyName(com.rssl.phizic.common.types.Currency valueToFormat)
	{
		try
		{
			if (valueToFormat == null)
				return null;

			return getCurrencySign(valueToFormat.getCode());
		}
		catch (Exception e)
		{
			log.error("Ошибка преобразования валюты в строку.",e);
			return "";
		}
	}

	public static String getCurrencySign(String currencyCode)
	{
		return CurrencyUtils.getCurrencySign(currencyCode);
	}

	/**
	 * Метод для получения map с параметрами формата, для дальнейшего использования в js
	 * @return
	 */
	public static Map<String, String> getFormat()
	{
		Map<String, String> format = new HashMap();

		format.put("decimalSeparator", String.valueOf(DECIMAL_SEPARATOR));
		format.put("thousandSeparator", String.valueOf(GROUPING_SEPARATOR));

		return format;
	}

	public static String formatAmountRound(BigDecimal formatedAmount, boolean round)
	{
		if(round)
			return formatAmount(formatedAmount, 0, DECIMAL_SEPARATOR, USE_GROUPING, round);
		else
			return formatAmount(formatedAmount, SCALE, DECIMAL_SEPARATOR, USE_GROUPING, round);
	}

	public static String formatAmount(BigDecimal formatedAmount)
	{
		return formatAmount(formatedAmount, SCALE, DECIMAL_SEPARATOR, USE_GROUPING, ROUND);
	}

	public static String formatAmount(String formatedAmount)
	{
		if (StringHelper.isEmpty(formatedAmount))
		{
			return formatedAmount;
		}

		return formatAmount(new BigDecimal(formatedAmount), SCALE, DECIMAL_SEPARATOR, USE_GROUPING, ROUND);
	}

	public static String formatAmountWithNoCents(String formatedAmount)
	{
		if (StringHelper.isEmpty(formatedAmount))
		{
			return formatedAmount;
		}

		return formatAmount(new BigDecimal(formatedAmount), 0, DECIMAL_SEPARATOR, USE_GROUPING, ROUND);
	}

	public static String formatAmount(BigDecimal formatedAmount, int scale)
	{
		return formatAmount(formatedAmount, scale, DECIMAL_SEPARATOR, USE_GROUPING, ROUND);
	}

	/**
	 * Форматирует сумму без пробелов и копеек
	 * @param formatedAmount форматируемая сумма
	 * @return строковое представление
	 */
	public static String formatAmountWithoutSpaceNoCents(BigDecimal formatedAmount)
	{
		return formatAmount(formatedAmount, 0, DECIMAL_SEPARATOR, false, ROUND);
	}

	public static String formatAmount(BigDecimal formatedAmount, int scale, char decimalSeparator)
	{
		return formatAmount(formatedAmount, scale, decimalSeparator, USE_GROUPING, ROUND);
	}

	public static String formatAmount(BigDecimal formatedAmount, int scale, char decimalSeparator, boolean useGrouping)
	{
		return formatAmount(formatedAmount, scale, decimalSeparator, useGrouping, ROUND);
	}

	public static String formatAmount(BigDecimal formatedAmount, int scale, char decimalSeparator, boolean useGrouping, boolean round)
	{
		if (formatedAmount == null)
			return "";

		BigDecimal amount = formatedAmount.setScale(scale, round ? BigDecimal.ROUND_DOWN : BigDecimal.ROUND_HALF_UP);
		StringBuilder strBuffer = new StringBuilder(amount.abs().toString().replace('.',decimalSeparator));
		int intPartEndPosition = strBuffer.indexOf(String.valueOf(decimalSeparator));
		//если число целое то вставляем с последнего символа
		if (intPartEndPosition < 0)
			intPartEndPosition = strBuffer.length();

		if (useGrouping)
		{
			for (int i = intPartEndPosition -3; i>0; i -= 3)
			{
				strBuffer.insert(i, GROUPING_SEPARATOR);
			}
		}

		if (amount.signum()<0)
		{
			strBuffer.insert(0, "-");
		}
		return strBuffer.toString();
	}

	/**
	 * Возвращает денежную величину с заданным формат и символом валюты
	 * @param money
	 * @param amountFormat
	 * @param isCurrencyNextToFormat ставить ли знак валюты после форматирования
	 * @return
	 */
	public static java.lang.String getFormatAmount(Money money, String amountFormat,boolean isCurrencyNextToFormat, String currencyFormat)
	{
		try
		{
			if(money==null)
				return "";

			String currency = getCurrencyName(money.getCurrency());
			boolean isMetallAccount = CurrencyUtils.isMetallAccount(money.getCurrency().getCode());
			String formatedAmount;
			if (isMetallAccount)
				formatedAmount = formatAmount(money.getDecimal(), 1);
			else
				formatedAmount = formatAmount(money.getDecimal());
			if (isCurrencyNextToFormat)
			{
				if (amountFormat!=null)
					formatedAmount = String.format(amountFormat, formatedAmount);
				if (currency != null && StringHelper.isNotEmpty(formatedAmount))
					formatedAmount += " " + (currencyFormat == null ? currency : String.format(currencyFormat, currency));
			}
			else
			{
				if (currency != null && StringHelper.isNotEmpty(formatedAmount))
					formatedAmount += " " + (currencyFormat == null ? currency : String.format(currencyFormat, currency));
				if (amountFormat!=null)
					formatedAmount = String.format(amountFormat, formatedAmount);
			}

			return formatedAmount;
		}
		catch (Exception e)
		{
			log.error("Ошибка преобразования суммы в строку.",e);
			return "";
		}
	}

	public static java.lang.String getFormatAmount(Money money, String amountFormat)
	{
		return getFormatAmount(money,amountFormat,true, null);
	}

	/**
	 * Получить сторку вида (пр: 60 руб/мес)
	 * @param money Деньги
	 * @return
	 */
	public static java.lang.String getFormatAmountWithMonth(Money money)
	{
		return getFormatAmount(money,"%s/мес",false, null);
	}

	/**
		 * Возвращает денежную величину с установленными разделителями и символом валюты
		 * @param money
		 * @return
	*/
	public static java.lang.String getFormatAmount(Money money)
	{
	  return getFormatAmount(money, null);
	}

	/**
		 * Возвращает денежную величину с установленными разделителями и символом валюты
		 * @param money
		 * @return
	*/
	public static java.lang.String formatAmountWithoutSpaces(Money money)
	{
	  return getFormatAmount(money, null).replace(" ", "&nbsp;");
	}

	/**
	 * Возвращает денежную величину с форматированием и символом валюты
	 * @param money
	 * @return
	*/
	public static java.lang.String getFormatAmountWithSpan(Money money)
	{
		return getFormatAmount(money, "<span class=\"moneyAmount\">%s</span>");
	}

	/**
	 * Возвращает денежную величину с форматированием суммы и валюты
	 * @param money - сумма
	 * @return отформатированную сумму
	 */
	public static java.lang.String getHtmlFormatAmount(Money money)
	{
		return getFormatAmount(money, "<span class=\"moneyAmount\">%s</span>", true, "<span class=\"currency\">%s</span>");
	}
	/**
		* Возвращает денежную величину с установленными разделителями и символом валюты, численное значение
	    *при этом будет выделено жирным шрифтом
		* @param money
		* @return
	*/
	public static java.lang.String getProductFormatAmount(Money money)
	{
		return getFormatAmount(money, "<b>%s</b>").replace("-","&minus;");
	}
	/**
		* Возвращает денежную величину с установленными разделителями и символом валюты, численное значение
	    *при этом будет выделено жирным шрифтом
		* @param money
		* @return
	*/
	public static java.lang.String productFormatAmountWithoutSpaces(Money money)
	{
		return getFormatAmount(money, "<b>%s</b>").replace("-","&minus;").replace(" ", "&nbsp;");
	}

	/**
	 * Возвращает денежную величину с буквенным кодом валюты без копеек
	 * @param money
	 * @return
	 */
	public static java.lang.String getFormatAmountWithoutCents(Money money)
	{
		try
		{
			if(money==null)
				return "";

			String currency = getCurrencyName(money.getCurrency());
			String formatedAmount = formatAmount(money.getDecimal(), 0);
			formatedAmount += " " + currency;

			return formatedAmount;
		}
		catch (Exception e)
		{
			log.error("Ошибка преобразования суммы в строку.",e);
			return "";
		}
	}

	/**
	 *
	 * Возвращает денежную величину без дробной части и c символом валюты
	 * @param money
	 * @return
	 */
	public static String getISOFormatAmountWithNoCents(Money money)
	{
		return getFormatAmountISO(money,true);
	}

	/**
	 *
	 * Возвращает денежную величину без дробной части
	 * @param amount
	 * @param separator разделитель по которому отрезаем дробную часть
	 * @return
	 */
	public static String getFormatAmountWithNoCents(String amount,String separator)
	{
		if (StringHelper.isEmpty(amount))
			return "";
		return StringUtils.substringBefore(amount,separator);

	}

	/**
	 * Возвращает денежную величину без дробной части  установленными разделителями
	 * @param amount
	 * @param separator разделитель по которому отрезаем дробную часть
	 * @return
	 */
	public static String getFormatAmountWithNoCentsGrouping(String amount, String separator)
	{
	StringBuffer result = new StringBuffer();
	result.append(getFormatAmountWithNoCents(amount, separator));

	int lastSpace = result.length() - 3;
	for (int i = lastSpace; i>0; i -= 3)
		result.insert(i, GROUPING_SEPARATOR);

	return result.toString();
	}

	/**
	 * Возвращает денежную величину с установленными разделителями и ISO-кодом валюты
	 * @param money
	 * @return
	 */
	public static java.lang.String getFormatAmountISO(Money money)
	{
	    return getFormatAmountISO(money,false);
	}

	/**
	 * Возвращает денежную величину с установленными разделителями и ISO-кодом валюты
	 * @param money
	 * @param noCents true не отображать копейки, false отображать
	 * @return
	 */
	public static java.lang.String getFormatAmountISO(Money money,boolean noCents)
	{
		try
		{
			if(money==null)
				return "";

			BigDecimal amount = money.getDecimal();
			if (amount == null)
				return "";

			StringBuffer strBuffer;
			int iIndx;
			if (noCents)
			{
				amount = amount.setScale(0);
				strBuffer = new StringBuffer(amount.abs().toString());
				iIndx = strBuffer.length() - 3;
			}
			else
			{
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
				strBuffer = new StringBuffer(amount.abs().toString().replace('.', DECIMAL_SEPARATOR));
				iIndx = strBuffer.indexOf(String.valueOf(DECIMAL_SEPARATOR)) -  3;
			}

			for (int i = iIndx; i>0; i -= 3)
				strBuffer.insert(i, GROUPING_SEPARATOR);

			if (amount.signum()<0)
				strBuffer.insert(0, "-");

			String currency = StringHelper.getEmptyIfNull(money.getCurrency().getCode());
			
			if (!StringHelper.isEmpty(currency))
			{
				strBuffer.append(" ");
				strBuffer.append(currency);
			}

			return strBuffer.toString();
		}
		catch (Exception e)
		{
			log.error("Ошибка преобразования суммы в строку.",e);
			return "";
		}
	}

	/**
	 * Возвращает ISO-код валюты; для рублей - "р."
	 * @param curCode alphabetic code
	 * @return
	 */
	public static String getISOCodeExceptRubles(String curCode)
	{
		if("RUB".equals(curCode) || "RUR".equals(curCode))
			return "р.";
		else
			return StringHelper.getEmptyIfNull(curCode);
	}

	/**
	 * Возвращает ISO-код валюты; для рублей - "руб."
	 * @param curCode alphabetic code
	 * @return
	 */
	public static String getISOCodeExceptingRubles(String curCode)
	{
		if("RUB".equals(curCode) || "RUR".equals(curCode))
			return "руб.";
		else
			return StringHelper.getEmptyIfNull(curCode);
	}

	/**
	 * Сложение/вычитание двух сумм
	 * @param money1 1-я сумма
	 * @param money2 2-я сумма
	 * @return результат операции
	 */
	public static Money getMoneyOperation(Money money1, Money money2, String operation)
	{
		if(money1 == null && money2 == null || operation == null)
			return null;
		if(money1 == null)
			return money2;
		if(money2 == null)
			return money1;
		if (operation.equals("+"))
			return money1.add(money2);
		else if (operation.equals("-"))
			return money1.sub(money2);

		return null;
	}

	/**
	 * Сложение/вычитание двух сумм
	 * @param money1 1-я сумма
	 * @param money2 2-я сумма
	 * @return результат операции
	 */
	public static int compareMoney(Money money1, Money money2)
	{
		return money1.compareTo(money2);
	}

	/**
	 * Возвращает отформатированную денежную величину без копеек
	 * @param value
	 * @return
	 */
    public static String getFormatAmountLong(java.lang.Long value)
    {
        if (value == null)
            return "";
        return formatAmount(new BigDecimal(value), 0);
    }

	/**
	 * Возвращает отформатированную денежную величину без копеек
	 * @param value
	 * @return
	 */
	public static String getFormatAmountString(java.lang.String value)
	{
		if (StringHelper.isEmpty(value))
			return "";
		return formatAmount(new BigDecimal(value), 0);
	}
}
