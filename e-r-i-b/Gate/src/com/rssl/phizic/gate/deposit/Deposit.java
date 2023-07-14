package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * Основная информация по депозиту
 */
public interface Deposit extends Serializable
{
	/**
	 * Внешний ID депозита
	 * Domain: ExternalID
	 *
	 * @return внешний id
	 */
	String getId();

	/**
	 * Описание депозита
	 * Domain: Text
	 *
	 * @return описание
	 */
	String getDescription();

	/**
	 * Сумма вклада
	 *
	 * @return сумма вклада
	 */
	Money getAmount();

	/**
	 * Срок в днях
	 *
	 * @return срок
	 */
	Long getDuration();

	/**
	 * Процентная ставка (% годовых)  2.00 -> 2% 11.30 -> 11.3%
	 *
	 * @return %годовых
	 */
	BigDecimal getInterestRate();

	/**
	 * Дата открытия депозита
	 * Domain: Date
	 *
	 * @return дата
	 */
	Calendar getOpenDate();

	/**
	 * Дата истечения срока действия договора для депозита (как написано в договоре).
	 * Domain: Date
	 *
	 * @return дата
	 */
	Calendar getEndDate();

	/**
	 * Фактическая дата закрытия депозита. Для открытого (действующего) депозита == null.
	 * Domain: Date
	 *
	 * @return дата
	 */
	Calendar getCloseDate();
	/**
	 * Статус депозита (открыт, закрыт ...)
	 *
	 * @return состояние
	 */
	DepositState getState();
}
