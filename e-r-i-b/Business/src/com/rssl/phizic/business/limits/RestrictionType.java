package com.rssl.phizic.business.limits;

/**
 * @author basharin
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

public enum RestrictionType
{
	DESCENDING("По убыванию лимита"),                                               //  по убыванию лимита (для мобильного кошелька)
	AMOUNT_IN_DAY("На сумму операций в сутки"),						                //	На сумму операций в сутки
	CARD_ALL_AMOUNT_IN_DAY("На сумму операций в сутки всех получателей платежа по номеру карты"),   //	На сумму операций в сутки всех получателей платежа по номеру карты
	PHONE_ALL_AMOUNT_IN_DAY("На сумму операций в сутки всех получателей платежа по номеру телефона"),  //	На сумму операций в сутки всех получателей платежа по номеру телефона
	MIN_AMOUNT("На минимальную сумму операции"),					                //	На минимальную сумму операции
	OPERATION_COUNT_IN_DAY("На количество операций в сутки"),		                //	На количество операций в сутки
	OPERATION_COUNT_IN_HOUR("На количество операций в час"),                        //	На количество операций в час
	IMSI("Смена SIM-карты"),                                                        //  Смена sim-карты
	MAX_AMOUNT_BY_TEMPLATE("Превышена сумма оплаты по шаблону"),                    //  Превышена сумма оплаты по шаблону
	OVERALL_AMOUNT_PER_DAY("Превышена сумма единого суточного кумулятивного лимита");

	private String description;

	RestrictionType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * Ограниесние по количеству операций
	 * @param limit лимит
	 * @return true - да
	 */
	public static boolean isByCount(Limit limit)
	{
		RestrictionType restrictionType = limit.getRestrictionType();
		return OPERATION_COUNT_IN_DAY == restrictionType || OPERATION_COUNT_IN_HOUR == restrictionType;
	}

	/**
	 * Ограниесние по сумме
	 * @param limit лимит
	 * @return true - да
	 */
	public static boolean isByAmount(Limit limit)
	{
		RestrictionType restrictionType = limit.getRestrictionType();
		return AMOUNT_IN_DAY == restrictionType;
	}
}
