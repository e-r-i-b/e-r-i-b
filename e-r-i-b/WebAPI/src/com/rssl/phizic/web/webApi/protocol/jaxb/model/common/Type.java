package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.utils.StringHelper;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * ���� ������� ����
 *
 * �������� activity ����� �� Settings/sbrf/iccs.properties
 *
 * @author Balovtsev
 * @since 05.05.2014
 */
@XmlEnum
public enum Type
{
	/**
	 * �������
	 */
	@XmlEnumValue(value = "MAIN")
	MAIN(null),

	/**
	 * ����� ����
	 */
	@XmlEnumValue(value = "DEPO")
	DEPO("Depo"),

	/**
	 * �����
	 */
	@XmlEnumValue(value = "CARDS")
	CARDS("Cards"),

	/**
	 * �������
	 */
	@XmlEnumValue(value = "LOANS")
	LOANS("Loans"),

	/**
	 * ������� �� ���������
	 */
	@XmlEnumValue(value = "THANKS")
	THANKS("LoyaltyProgram"),

	/**
	 * ������ � �����
	 */
	@XmlEnumValue(value = "ACCOUNTS")
	ACCOUNTS("Deposits"),

	/**
	 * ������� � ��������
	 */
	@XmlEnumValue(value = "PAYMENTS")
	PAYMENTS(null),

	/**
	 * ������������� �����
	 */
	@XmlEnumValue(value = "IMACCOUNTS")
	IMACCOUNTS("IMAInfo"),

	/**
	 * �����������
	 */
	@XmlEnumValue(value = "CERTIFICATES")
	CERTIFICATES("Security"),

	/**
	 * ���������� ���������
	 */
	@XmlEnumValue(value = "PENSION_PRODUCTS")
	PENSION_PRODUCTS("NPF"),

	/**
	 * ��������� ���������
	 */
	@XmlEnumValue(value = "INSURANCE_PRODUCTS")
	INSURANCE_PRODUCTS("Insurance"),

	/**
	 * ������� ������� ���� ���� ��������
	 */
	@XmlEnumValue(value = "MY_FINANCE")
	MY_FINANCE(null),

	/**
	 * ������� ������� ���� ����������
	 */
	@XmlEnumValue(value = "FAVORITES")
	FAVORITES(null),

	/**
	 * ������� ������� ���� ���������
	 */
	@XmlEnumValue(value = "TEMPLATES")
	TEMPLATES(null),

	/**
	 * ��������� �������
	 */
	@XmlEnumValue(value = "CREDIT_BUREAU_HISTORY")
	CREDIT_BUREAU_HISTORY("CreditBureauHistory"),

	/**
	 * �����-����
	 */
	@XmlEnumValue(value = "PROMO_CODES")
	PROMO_CODES("promoCodes");

	private final String activity;

	Type(String activity)
	{
		this.activity = activity;
	}

	/**
	 * @param activity �������� ������ ���� ��� ��� ������������ � ����
	 * @return Type
	 */
	public static Type getTypeByActivity(String activity)
	{
		if (StringHelper.isNotEmpty(activity))
		{
			for (Type value : Type.values())
			{
				if (activity.equals(value.toValue()))
				{
					return value;
				}
			}
		}

		return null;
	}

	/**
	 * ���������� null ��� MAIN � PAYMENTS
	 *
	 * @return String
	 */
	public String toValue()
	{
		return this.activity;
	}
}
