package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.util.Map;
import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */
/**
 * Строка графика погашения
 */
public interface ScheduleItem extends Serializable
{
/**
    * Дата выплаты
    * Domain: Date
    *
    * @return дата
    */
   Calendar getDate();
   /**
    * Сумма выплаты по основному долгу
    *
    *
    * @return сумма
    */
   Money getPrincipalAmount();
   /**
    * Сумма выплаты по процентам
    *
    *
    * @return сумма
    */
   Money getInterestsAmount();
   /**
    * Сумма комиссии
    *
    *
    * @return сумма
    */
   Money getCommissionAmount();
   /**
    * Сумма платежа (итого).
    *
    *
    * @return сумма
    */
   Money getTotalAmount();
   /**
    * Общая сумма, которую клиент должен заплатить (погасить) за промежуток времени.
    * Может не совпадать с getTotalAmount, так как могут присутствовать и другие, неучтенные здась, составляющие (штрафы и пр.)
    *
    *
    * @return сумма
    */
   Money getTotalPaymentAmount();
   /**
    * Сумма для досрочного погашения
    *
    *
    * @return сумма
    */
   Money getEarlyPaymentAmount();
	/**
	 * Порядковый номер платежа в графике
	 * @return номер
	 */
	Long getPaymentNumber();
	/**
	 * Статус платежа по кредиту
	 *
	 * @return статус
	 */
	LoanPaymentState getPaymentState();

	/**
	 * Идентификатор разбивки
	 * @return идентификатор
	 */
	String getIdSpacing();

   /**
	 * список штрафных и внеочередных платежей по кредиту
	 *
	 * @return мап<тип статьи, статья>
	 */
	Map<PenaltyDateDebtItemType, DateDebtItem> getPenaltyDateDebtItemMap();

	/**
	 * @return переплата
	 */
	Money getOverpayment();

    /**
     * остаток основного долга
     * @return
     */
   Money getRemainDebt();
}
