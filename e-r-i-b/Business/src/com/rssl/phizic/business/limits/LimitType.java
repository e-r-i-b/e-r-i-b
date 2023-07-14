package com.rssl.phizic.business.limits;

/**
 * @author basharin
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 * тип лимита
 */

public enum LimitType
{
	GROUP_RISK,                         //лимит по группам риска
	OBSTRUCTION_FOR_AMOUNT_OPERATION,   //заградительный лимит по сумме операции
	OBSTRUCTION_FOR_AMOUNT_OPERATIONS,  //заградительный лимит по сумме операций
	IMSI,                               //IMSI лимит
	USER_POUCH,                         //пользовательский кошелек
	EXTERNAL_CARD,                      //лимит на получателя для переводов на чужую карту
	EXTERNAL_PHONE,                     //лимит на получателя для оплаты чужого телефона
	OVERALL_AMOUNT_PER_DAY;             //суточный лимит для всех каналов

	/**
	 * Является ли данный тип лимита заградительным.
	 * Ограничение: лимит по группе риска не рассматривается
	 *
	 * @param limitType тип лимита
	 * @return true - заградительный
	 */
	public static boolean isObstruction(LimitType limitType)
	{
		return limitType == OBSTRUCTION_FOR_AMOUNT_OPERATION || limitType == OBSTRUCTION_FOR_AMOUNT_OPERATIONS
			|| limitType == EXTERNAL_CARD || limitType == EXTERNAL_PHONE || limitType == USER_POUCH;
	}
}
