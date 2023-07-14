package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */
//График платежей по кредиту.
public interface ScheduleAbstract extends Serializable
{		
	/**
	 * Сумма всех выплат, которые были произведены, с учетом штрафов и проч. 
	 * @return
	 */
	Money getDoneAmount();

	/**
	 * Сумма платежа для закрытия кредита
	 * @return
	 */
	Money getRemainAmount();

	/**
	 * Сумма всех штрафов и пеней
	 * @return
	 */
	Money getPenaltyAmount();

	/**
	 * Общее количество платежей в графике
	 * @return количество платежей
	 */
	Long getPaymentCount();
	/**
	 * Строки графика погашения, сортированные по дате
	 * @return строки графика погашения
	 */
	List<ScheduleItem> getSchedules();

	/**
	 * Доступна ли информация по графику
	 * @return
	 */
	boolean getIsAvailable();

    List<LoanYearPayment> getYearPayments();
}
