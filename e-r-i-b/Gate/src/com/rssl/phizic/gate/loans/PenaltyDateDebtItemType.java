package com.rssl.phizic.gate.loans;

import com.rssl.phizic.utils.StringHelper;

/**
 * Типы статей задолженостей на дату (штрафные и внеочередные платежи)
 * @author gladishev
 * @ created 22.11.2011
 * @ $Author$
 * @ $Revision$
 */
public enum PenaltyDateDebtItemType
{
	/**
	* Сумма судебных и иных расходов
	* по взысканию задолженности
	*/
	otherCostsAmount("Сумма судебных и иных расходов по взысканию задолженности"),

	/**
	* Плата за досрочный возврат
	*/
	earlyReturnAmount("Плата за досрочный возврат"),

	/**
	* Плата за операции по ссудному счету
	*/
	accountOperationsAmount("Плата за операции по ссудному счету"),

	/**
	* Неустойка за просрочку платы
	* за операции по ссудному счету
	*/
	penaltyAccountOperationsAmount("Неустойка за просрочку платы за операции по ссудному счету"),

	/**
	* Неустойка за просрочку процентов
	*/
	penaltyDelayPercentAmount("Неустойка за просрочку процентов"),

	/**
	* Неустойка за просрочку основного долга
	*/
	penaltyDelayDebtAmount("Неустойка за просрочку основного долга"),

	/**
	* Неустойка за несвоевременное возобновление
	* страхования предмета залога
	*/
	penaltyUntimelyInsurance("Неустойка за несвоевременное возобновление страхования предмета залога"),

	/**
	* Сумма просроченных процентов
	* за пользование кредитом
	*/
	delayedPercentsAmount("Сумма просроченных процентов за пользование кредитом"),

	/**
	* Сумма просроченного основного долга
	*/
	delayedDebtAmount("Сумма просроченного основного долга"),

	/**
	* Досрочно, в счет основного долга
	*/
	earlyBaseDebtAmount("Досрочно, в счет основного долга");


	private final String description;

	PenaltyDateDebtItemType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public static PenaltyDateDebtItemType fromValue(String value)
	{
		if (StringHelper.isEmpty(value))
			return null;

		for (PenaltyDateDebtItemType type : values()) {
			if (value.equals(type.description))
				return type;
		}
		throw new IllegalArgumentException("Некорректный номер статьи [" + value + "]");
	}
}
