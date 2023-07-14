package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * Статьи задолженности на указанную дату
 * @author gladishev
 * @ created 14.07.2010
 * @ $Author$
 * @ $Revision$
 */
public interface DateDebtItem extends Serializable
{
	/**
	 * Код статьи задолженности
	 * @return код
	 */
	PenaltyDateDebtItemType getCode();

	/**
	* Приоритет погашения статьи
	* @return Приоритет
	*/
	Long getPriority();

	/**
	* Счет учета статьи
	* @return Счет
	*/
	String getAccountCount();

	/**
	* Разрешенная дата погашения
	* (для аннуитетных договоров)
	* @return дата
	*/
	Calendar getAnnuityDate();

	/**
	 * Наименование статьи задолженности
	 * @return название
	 */
	String getName();

	/**
	 * Сумма задолженности
	 * @return сумма
	 */
	Money getAmount();
}