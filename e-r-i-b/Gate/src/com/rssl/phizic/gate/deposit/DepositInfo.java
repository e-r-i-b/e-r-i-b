package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Расширенная информация о депозите
 *
 * @author Evgrafov
 * @ created 29.06.2007
 * @ $Author: egorova $
 * @ $Revision: 12412 $
 */

public interface DepositInfo
{
	/**
	 * Депозитный счет
	 *
	 * @return счет
	 */
	Account getAccount();

	/**
	 * Счет для перечисления причисленных процентов
	 *
	 * @return счет
	 */
	Account getPercentAccount();

	/**
	 * Счета для перечисления средств по истечении срока вклада
	 * и доли их перечисления (в процентах)
	 *
	 * @return мап, номер счета и процент от суммы, который на него будет перечислен.
	 */
	Map<Account, BigDecimal> getFinalAccounts();

	/**
	 * Сумма неснижаемого остатка вклада
	 * (для вкладов не допускающих списание средств этот остаток равен сумме вклада)
	 *
	 * @return сумма
	 */
	Money getMinBalance();

	/**
	 * Минимальная сумма пополнения вклада
	 * @return сумма
	 */
	Money getMinReplenishmentAmount();

	/**
	 * Признак пролонгации вклада на следующий срок
	 * («пролонгируемый» или «непролонгируемый»);
	 *
	 * @return true==пролонгация разрешена
	 */
	boolean isRenewalAllowed();


	/**
	 * Разрешено ли досрочное снятие("разрешено" или "запрещено").
	 *
	 * @return true==разрешено
	 */
	boolean isAnticipatoryRemoval();


	/**
	 * Разрешено ли пополнение вклада("разрешено" или "запрещено").
	 *
	 * @return true==разрешено
	 */
	boolean isAdditionalFee();
	/**
	 * Номер договора (использовать только как текст!)
	 * Domain: Text
	 *
	 * @return номер договора
	 */
	String getAgreementNumber();

}