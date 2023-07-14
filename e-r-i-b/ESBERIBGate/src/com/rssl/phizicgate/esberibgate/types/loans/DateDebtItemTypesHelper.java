package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizic.gate.loans.PenaltyDateDebtItemType;

import java.util.HashMap;
import java.util.Map;

/**
 * Соответсвия кодов статей задолженностей из ВС типам статей нашей системы
 * @author gladishev
 * @ created 12.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class DateDebtItemTypesHelper
{
	private static final Map<String, Enum> dateDebtTypeMap = new HashMap<String, Enum>();


	public static enum DateDebtItemType
	{
		/**
		* Сумма срочных процентов
		* за пользование кредитом
		*/
		percentsAmount,

		/**
		* Сумма задолженности по основному
		* долгу по сроку Дата_1.
		*/
		firstDateDebtAmount,

		/**
		* Сумма задолженности по основному
		* долгу по сроку Дата_2.
		*/
		secondDateDebtAmount,

		/**
		* Переплата
		*/
		overpayment
	}

	static
	{
		dateDebtTypeMap.put("193", PenaltyDateDebtItemType.otherCostsAmount);
		dateDebtTypeMap.put("191", PenaltyDateDebtItemType.earlyReturnAmount);
		dateDebtTypeMap.put("16", PenaltyDateDebtItemType.accountOperationsAmount);
		dateDebtTypeMap.put("44", PenaltyDateDebtItemType.penaltyAccountOperationsAmount);
		dateDebtTypeMap.put("13", PenaltyDateDebtItemType.penaltyDelayPercentAmount);
		dateDebtTypeMap.put("14", PenaltyDateDebtItemType.penaltyDelayDebtAmount);
		dateDebtTypeMap.put("98", PenaltyDateDebtItemType.penaltyUntimelyInsurance);
		dateDebtTypeMap.put("7", PenaltyDateDebtItemType.delayedPercentsAmount);
		dateDebtTypeMap.put("5", DateDebtItemType.percentsAmount);
		dateDebtTypeMap.put("6", PenaltyDateDebtItemType.delayedDebtAmount);
		dateDebtTypeMap.put("4", DateDebtItemType.firstDateDebtAmount);
		dateDebtTypeMap.put("-4", DateDebtItemType.secondDateDebtAmount);
		dateDebtTypeMap.put("-999", PenaltyDateDebtItemType.earlyBaseDebtAmount);
		dateDebtTypeMap.put("23", DateDebtItemType.overpayment);
	}

	public static Enum getItemType(String code)
	{
		return dateDebtTypeMap.get(code);
	}
}
