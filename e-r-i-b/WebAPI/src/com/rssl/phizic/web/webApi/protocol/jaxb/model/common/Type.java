package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import com.rssl.phizic.utils.StringHelper;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Типы пунктов меню
 *
 * Значения activity взяты из Settings/sbrf/iccs.properties
 *
 * @author Balovtsev
 * @since 05.05.2014
 */
@XmlEnum
public enum Type
{
	/**
	 * Главная
	 */
	@XmlEnumValue(value = "MAIN")
	MAIN(null),

	/**
	 * Счета депо
	 */
	@XmlEnumValue(value = "DEPO")
	DEPO("Depo"),

	/**
	 * Карты
	 */
	@XmlEnumValue(value = "CARDS")
	CARDS("Cards"),

	/**
	 * Кредиты
	 */
	@XmlEnumValue(value = "LOANS")
	LOANS("Loans"),

	/**
	 * Спасибо от Сбербанка
	 */
	@XmlEnumValue(value = "THANKS")
	THANKS("LoyaltyProgram"),

	/**
	 * Вклады и счета
	 */
	@XmlEnumValue(value = "ACCOUNTS")
	ACCOUNTS("Deposits"),

	/**
	 * Платежи и переводы
	 */
	@XmlEnumValue(value = "PAYMENTS")
	PAYMENTS(null),

	/**
	 * Металлические счета
	 */
	@XmlEnumValue(value = "IMACCOUNTS")
	IMACCOUNTS("IMAInfo"),

	/**
	 * Сертификаты
	 */
	@XmlEnumValue(value = "CERTIFICATES")
	CERTIFICATES("Security"),

	/**
	 * Пенсионные программы
	 */
	@XmlEnumValue(value = "PENSION_PRODUCTS")
	PENSION_PRODUCTS("NPF"),

	/**
	 * Страховые программы
	 */
	@XmlEnumValue(value = "INSURANCE_PRODUCTS")
	INSURANCE_PRODUCTS("Insurance"),

	/**
	 * Элемент правого меню «Мои финансы»
	 */
	@XmlEnumValue(value = "MY_FINANCE")
	MY_FINANCE(null),

	/**
	 * Элемент правого меню «Избранное»
	 */
	@XmlEnumValue(value = "FAVORITES")
	FAVORITES(null),

	/**
	 * Элемент правого меню «Шаблоны»
	 */
	@XmlEnumValue(value = "TEMPLATES")
	TEMPLATES(null),

	/**
	 * Кредитная история
	 */
	@XmlEnumValue(value = "CREDIT_BUREAU_HISTORY")
	CREDIT_BUREAU_HISTORY("CreditBureauHistory"),

	/**
	 * Промо-коды
	 */
	@XmlEnumValue(value = "PROMO_CODES")
	PROMO_CODES("promoCodes");

	private final String activity;

	Type(String activity)
	{
		this.activity = activity;
	}

	/**
	 * @param activity название пункта меню как оно представлено в ЕРИБ
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
	 * Возвращает null для MAIN и PAYMENTS
	 *
	 * @return String
	 */
	public String toValue()
	{
		return this.activity;
	}
}
