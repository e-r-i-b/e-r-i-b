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
	 * ����� < ISO-��� ������, �������� ������ >. ��� �������� ������ ������ - ������.
	 */
	private static final Map<String, String> currencySignMap = new HashMap<String, String>();
	/**
	 * ����� < ISO-��� �������, ��������������� ��� ������� >
	 */
	private static final Map<String, String> metalCodeMap = new HashMap<String, String>();

	/**
	 *  ��� � ��������, ��� �������� �� ������� ��� �������� �������� ����� ������������ ��������� ������� � �������� �����
	 */
	private static final Set<String> CURRENCY_SHOW_PRIVILEGED_MSG_SET = new HashSet<String>(Arrays.asList("EUR", "USD", "A98", "A99", "A76", "A33"));

	static
	{
		currencySignMap.put("RUB", "���.");
		currencySignMap.put("RUR", "���.");
		currencySignMap.put("A99", "�");
		currencySignMap.put("A98", "�");
		currencySignMap.put("A76", "�");
		currencySignMap.put("A33", "�");
		currencySignMap.put("USD", "$");
		currencySignMap.put("EUR", "�");

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
	 * ���������� ������ ������ �� �� ISO-����.
	 * @param currencyCode ISO-��� ������
	 * @return ������ ������
	 */
	public static String getCurrencySign(String currencyCode)
	{
		String sign = currencySignMap.get(currencyCode);
		return sign == null ? currencyCode : sign;
	}
	
	/**
	 * ��������� ���� �� �������������� ����� ������
	 * @param currencyCode1 - ��� ������
	 * @param currencyCode2 - ��� ������
	 * @return true, ���� ���� ����������� � ����� ������
	 */
	public static boolean isSameCurrency(String currencyCode1, String currencyCode2)
	{
		if (StringHelper.isEmpty(currencyCode1))
			throw new IllegalArgumentException("�������� 'currencyCode1' �� ����� ���� ������");
		if (StringHelper.isEmpty(currencyCode2))
			throw new IllegalArgumentException("�������� 'currencyCode2' �� ����� ���� ������");

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
	 * ��������� ISO-��� ������� � ��������������� ���.
	 * @param currencyCode
	 * @return
	 */
	public static String normalizeMetalCode(String currencyCode)
	{
		if (StringHelper.isEmpty(currencyCode))
			throw new IllegalArgumentException("�������� 'currencyCode' �� ����� ���� ������");

		currencyCode = currencyCode.toUpperCase();
		String normalizedCode = metalCodeMap.get(currencyCode);
		return normalizedCode == null ? currencyCode : normalizedCode;
	}

	/**
	 * ������������ ����� �� ����� � ������ <rate>.from � ����� � ������ <rate>.to
	 * ������������ � ��� �����, ��� ����� ��������� ����� �� ����
	 * @param amount - �����
	 * @param rate - ���� ������
	 * @return <amount> * <rate>.toValue() / <rate>.fromValue()
	 */
	public static BigDecimal convert(BigDecimal amount, CurrencyRate rate)
	{
		if (rate.getFromCurrency().getCode().equals("RUB"))
		{
			// ���� ������ ������ �����, ����� ������, ����� �������� ��� �� ���������, ��� � � js �� �����
			return amount.divide(rate.getReverseFactor(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
		}
		else
		{
			return amount.multiply(rate.getFactor());
		}
	}

	/**
	 * ������������ ����� �� ����� � ������ <rate>.to � ����� � ������ <rate>.from
	 * ������������ � ��� �����, ��� ����� �������� ����� �� ����
	 * @param amount - �����
	 * @param rate - ���� ������
	 * @return <amount> * <rate>.fromValue() / <rate>.toValue()
	 */
	public static BigDecimal reverseConvert(BigDecimal amount, CurrencyRate rate)
	{
		if (rate.getToCurrency().getCode().equals("RUB"))
		{
			// ���� ������ ������ �����, ����� ������, ����� �������� ��� �� ���������, ��� � � js �� �����
			return amount.divide(rate.getFactor(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
		}
		else
		{
			return amount.multiply(rate.getReverseFactor());
		}
	}

	/**
	 * ��������� � ����� ����� + 5%
	 * @param rate
	 * @return - ���� ����� + 5%
	 */
	public static CurrencyRate convertAddProcent(CurrencyRate rate)
	{
		BigDecimal multiply = rate.getToValue().multiply(new BigDecimal(PERCENT));
		BigDecimal to = rate.getToValue().add(multiply);
		return new CurrencyRate(rate.getFromCurrency(), rate.getFromValue(), rate.getToCurrency(), round(to), rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * ���������� ����, ������� � ���� fromValue �������� <from>
	 * @param from - �������� ��� ���� ����� fromValue
	 * @param rate - ����
	 * @return �������������� ��� <from> ����
	 */
	public static CurrencyRate getFromCurrencyRate(BigDecimal from, CurrencyRate rate)
	{
		// ��������� ����� �� ����������� �����
		BigDecimal to = convert(from, rate);
		// �� ������ ���������, ����� ������������ ���� �������� ���������� <from> � <to>
		BigDecimal roundedFrom = round(from);
		BigDecimal roundedTo = round(to);
		return new CurrencyRate(rate.getFromCurrency(), roundedFrom, rate.getToCurrency(), roundedTo, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * ���������� ����, ������� � ���� fromValue �������� <from> � �������� ����������� �����.
	 * @param from - �������� ��� ���� ����� fromValue
	 * @param rate - ����
	 * @return �������������� ��� <from> ����
	 */
	private static CurrencyRate getFromCurrencyRateWithRoundScale(BigDecimal from, CurrencyRate rate, int scale)
	{
		// ��������� ����� �� ����������� �����
		BigDecimal to = from.divide(rate.getReverseFactor(scale), scale, CurrencyRate.ROUNDING_MODE);
		return new CurrencyRate(rate.getFromCurrency(), from, rate.getToCurrency(), to, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * ���������� ����, ������� � ���� toValue �������� <to>
	 * @param to - �������� ��� ���� ����� toValue
	 * @param rate - ����
	 * @return �������������� ��� <to> ����
	 */
	public static CurrencyRate getToCurrencyRate(BigDecimal to, CurrencyRate rate)
	{
		// ��������� ����� �� ����������� �����
		BigDecimal from = reverseConvert(to, rate);
		// �� ������ ���������, ����� ������������ ���� �������� ���������� <from> � <to>
		BigDecimal roundedFrom = round(from);
		BigDecimal roundedTo = round(to);
		return new CurrencyRate(rate.getFromCurrency(), roundedFrom, rate.getToCurrency(), roundedTo, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * ���������� ����, ������� � ���� toValue �������� <to> � �������� ����������� �����.
	 * @param to - �������� ��� ���� ����� toValue
	 * @param rate - ����
	 * @return �������������� ��� <to> ����
	 */
	private static CurrencyRate getToCurrencyRateWithRoundScale(BigDecimal to, CurrencyRate rate, int scale)
	{
		// ��������� ����� �� ����������� �����
		BigDecimal from = to.divide(rate.getFactor(scale), scale, CurrencyRate.ROUNDING_MODE);
		return new CurrencyRate(rate.getFromCurrency(), from, rate.getToCurrency(), to, rate.getType(), rate.getTarifPlanCodeType(), rate.getExpireDate());
	}

	/**
	 * ���������� ����, ������� � ���� toValue �������� <to> � ����������� ����������� ��� �������� � �������.
	 * @param to - �������� ��� ���� ����� toValue
     * @param rate - ����
	 * @return �������������� ��� <from> ����
	 */
	public static CurrencyRate getToCurrencyRateForAccountPayment(BigDecimal to, CurrencyRate rate)
	{
		return getToCurrencyRateWithRoundScale(to, rate, 13);
	}

	/**
	 * ��������� �����
	 * @param amount - �����
	 * @return - ����� � �����������, �������� � ������� ��-���������
	 */
	private static BigDecimal round(BigDecimal amount)
	{
		return amount.setScale(CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
	}

	/**
	 * ���������� �������� ���� ������������� ��� ���
	 * @param currencyCode - ISO-���
	 * @return true, ���� ���� �������������
	 */
	public static boolean isMetallAccount(String currencyCode)
	{
		if (StringHelper.isEmpty(currencyCode))
			return false;
		return metalCodeMap.keySet().contains(currencyCode);
	}

	/**
	 * @param currency - ������ (never null)
	 * @return true, ���� ������ - ����� (RUR ��� RUB)
	 */
	public static boolean isRUR(Currency currency)
	{
		String code = currency.getCode();
		return code.equalsIgnoreCase("RUR") || code.equalsIgnoreCase("RUB");
	}

	/**
	 * @param currency - ������ (never null)
	 * @return true, ���� ������ - ����
	 */
	public static boolean isEUR(Currency currency)
	{
		String code = currency.getCode();
		return code.equalsIgnoreCase("EUR");
	}

	/**
	 * @param currency - ������ (never null)
	 * @return true, ���� ������ - �������
	 */
	public static boolean isUSD(Currency currency)
	{
		String code = currency.getCode();
		return code.equalsIgnoreCase("USD");
	}
}
