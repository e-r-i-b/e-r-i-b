package com.rssl.phizic.gate.templates.impl;

/**
 * Тип ресурса списания/зачисления
 *
 * @author khudyakov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 */
public enum ResourceType
{
	/**
	 * Счёт клиента
	 */
	ACCOUNT("com.rssl.phizic.business.resources.external.AccountLink"),

	/**
	 * Чужой счёт
	 */
	EXTERNAL_ACCOUNT(null),

	/**
	 * Карта клиента
	 */
	CARD("com.rssl.phizic.business.resources.external.CardLink"),

	/**
	 * Чужая карта
	 */
	EXTERNAL_CARD(null),

	/**
	 * Кредит
	 */
	LOAN("com.rssl.phizic.business.resources.external.LoanLink"),

	/**
	 * Счёт депо
	 */
	DEPO_ACCOUNT("com.rssl.phizic.business.resources.external.DepoAccountLink"),

	/**
	 * ОМС (Обезличенный Металлический Счет)
	 */
	IM_ACCOUNT("com.rssl.phizic.business.resources.external.IMAccountLink"),

	/**
	 * Пустой объект
	 */
	NULL(null);


	private String stringType;

	ResourceType(String stringType)
	{
		this.stringType = stringType;
	}

	String getStringType()
	{
		return stringType;
	}

	/**
	 * Восстановить тип ресурса списания/зачисления по строке
	 * Ограничение: не работает для внешних источников: EXTERNAL_CARD, EXTERNAL_ACCOUNT
	 *
	 * @param type тип ресурса списания/зачисления как строка
	 * @return тип ресурса списания/зачисления
	 */
	public static ResourceType fromValue(String type)
	{
		if (ACCOUNT.getStringType().equals(type))
		{
			return ACCOUNT;
		}
		if (CARD.getStringType().equals(type))
		{
			return CARD;
		}
		if (LOAN.getStringType().equals(type))
		{
			return LOAN;
		}
		if (DEPO_ACCOUNT.getStringType().equals(type))
		{
			return DEPO_ACCOUNT;
		}
		if (IM_ACCOUNT.getStringType().equals(type))
		{
			return IM_ACCOUNT;
		}
		return NULL;
	}
}
