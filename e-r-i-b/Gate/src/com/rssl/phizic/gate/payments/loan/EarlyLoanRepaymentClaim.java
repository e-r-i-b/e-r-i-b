package com.rssl.phizic.gate.payments.loan;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.AbstractTransfer;

import java.util.Calendar;

/**
 * Интерфейс заявки на досрочное погашение кредита
 * User: petuhov
 * Date: 14.05.15
 * Time: 9:24 
 */
public interface EarlyLoanRepaymentClaim extends AbstractTransfer
{
	/**
	 * Возвращает признак частичного досрочного погашения
	 * @return true, если досрочное погашение - частичное
	 */
	public boolean isPartial();

	/**
	 * Возвращает дату досрочного погашения
	 * @return Дата досрочного погашения
	 */
	public Calendar getRepaymentDate();

	/**
	 * Возвращает идентификатор кредитного договора в ЕКП
	 * @return Идентификатор кредита в системе УКО
	 */
	public String getLoanIdentifier();

	/**
	 * Счет для погашения
	 * @return Номер счета для погашения
	 */
	public String getChargeOffAccount();

	/**
	 * Сумма(для частичного досрочного погашения)
	 * @return сумма для досрочного погашения
	 */
	public Money getRepaymentAmount();

	void setOperUID(String currentOperUID);

	String getOperUID();
}