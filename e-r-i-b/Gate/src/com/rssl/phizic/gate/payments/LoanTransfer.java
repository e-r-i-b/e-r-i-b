package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.documents.AbstractCardTransfer;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 05.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface LoanTransfer extends AbstractCardTransfer
{
	/**
	 * Внешний id кредита
	 */
	String getLoanExternalId();

	/**
	 * Номер ссудного счета
	 */
	String getAccountNumber();

	/**
	 * Номер кредитного договора
	 */
	String getAgreementNumber();

	/**
	 * Идентификатор разбивки
	 */
	String getIdSpacing();

	/**
	 * Дата получения разбивки
	 */
	Calendar getSpacingDate();
}