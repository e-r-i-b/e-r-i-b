package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 12.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class MoneyHelper
{
	private static final char DECIMAL_SEPARATOR = ',';
	private static final char GROUPING_SEPARATOR = '\u00A0'; // ����������� ������

	/**
	 * ���������� ������� � ��������� ����
	 * @param money - ������
	 * @return ��������� ������������� <money>
	 */
	public static String formatMoney(Money money)
	{
		if (money == null)
			return "empty";
		return formatMoney(money.getWholePart(), money.getCents(), money.getCurrency().getCode());
	}

	/**
	 * ���������� ������� � ��������� ����
	 * @param
	 * @return ��������� ������������� <money>
	 */
	public static String formatMoney(Long wholePart, Long cents, String currencyCode)
	{

		return String.format("%d.%02d %s", wholePart, Math.abs(cents), currencyCode);
	}

	/**
	 * ������ ����� MoneyFunctions.formatAmount. ���������� ��� ������� � �������.
	 * � ����������� ������� ���� ����� � ��������� ��� ������ � ���������, ��������� � �������. 
	 * @param formatedAmount
	 * @return
	 */
	public static String formatAmount(BigDecimal formatedAmount)
	{
		if (formatedAmount == null)
			return "";

		BigDecimal amount = formatedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		StringBuilder strBuffer = new StringBuilder(amount.abs().toString().replace('.', DECIMAL_SEPARATOR));
		for (int i = strBuffer.indexOf(String.valueOf(DECIMAL_SEPARATOR))-3; i>0; i -= 3)
		{
			strBuffer.insert(i, GROUPING_SEPARATOR);
		}

		if (amount.signum()<0)
		{
			strBuffer.insert(0, "-");
		}
		return strBuffer.toString();
	}

	/**
	 * ���������� ��������� ������������� ����� � ������� � ����������� �������� � �������� ������
	 * @param formatedAmount - ������������� �����
	 * @return  ������� ������������� �����
	 */
	public static String formatAmount (Money formatedAmount)
	{
		if (formatedAmount == null)
			return "";

		if (formatedAmount.getCurrency() == null)
			return formatAmount(formatedAmount.getDecimal());

		return formatAmount(formatedAmount.getDecimal()) + " " + CurrencyUtils.getCurrencySign(String.valueOf(formatedAmount.getCurrency().getCode()));
	}

	/**
	 * �������� ������ � ��������� � ���� ��������� ���������� � ���������� �������� ��������������� ����������,
	 * ��������������� ������� [����� ����� �����]([��������� �������� �.�. �����]).[������� ����� �����]. �������� 1000(���� ������).00
	 * @param sum - ��������� ������������� ����� (��� ���� ������)
	 * @return ������ � ���������
	 */
	public static String describeSum(String sum)
	{
		if (!StringHelper.getEmptyIfNull(sum).matches("^\\d{1,}\\.\\d{2}$"))
			return sum;

		String sumArray[] = sum.split("\\.");

		StringBuffer sb = new StringBuffer();
		sb.append(sumArray[0]);
		sb.append("(");
		sb.append(com.rssl.phizic.utils.StringUtils.sumInWords(sumArray[0], "").toLowerCase());
		sb.append(").");
		sb.append(sumArray[1]);

		return sb.toString();
	}

	/**
	 * ���������� ������� � ��������� ����
	 * @param money - ������
	 * @return ��������� ������������� <money> � �������� ������
	 */
	public static String formatMoneyWithCurrencySign(Money money)
	{
		if (money == null)
			return "empty";
		return formatMoney(money.getWholePart(), money.getCents(), CurrencyUtils.getCurrencySign(money.getCurrency().getCode()));
	}
}
