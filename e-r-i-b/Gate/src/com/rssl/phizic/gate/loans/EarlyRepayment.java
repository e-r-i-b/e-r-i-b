package com.rssl.phizic.gate.loans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * досрочные погашение
 * @author basharin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */

public interface EarlyRepayment
{
	/**
	 * <Amount> GetPrivateLoanDetailsRs
	 * @return Сумма платежа, вносимого сверх планового платежа
	 */
	public BigDecimal getAmount();

	/**
	 * <Date> GetPrivateLoanDetailsRs
	 * @return Дата досрочного погашения
	 */
	public Date getDate();

	/**
	 * <Status> GetPrivateLoanDetailsRs
	 * @return Статус досрочного погашения((«Принято»/ «Исполнено» / «Отменено» / «Отклонено» [Причина отклонения])
	 */
	public String getStatus();

	/**
	 * <Account> GetPrivateLoanDetailsRs
	 * @return Счет для погашения
	 */
	public String getAccount();

	/**
	 * <RepaymentChannel> GetPrivateLoanDetailsRs
	 * @return Канал, по которому зарегистрировано ДП.
	 * Возможные значения:
	 * 1 – канал УКО
	 * 2 – канал не УКО(ВСП)
	 */
	public String getRepaymentChannel();

	/**
	 * <TerminationRequestId> GetPrivateLoanDetailsRs
	 * @return Идентификатор заявки на досрочное погашение
	 */
	public long getTerminationRequestId();

}
