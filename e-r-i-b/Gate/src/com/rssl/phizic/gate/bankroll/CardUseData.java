package com.rssl.phizic.gate.bankroll;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 15.05.2014
 * @ $Author$
 * @ $Revision$
 */
public interface CardUseData
{
	String   getAuthCode();
	String   getCardNumber();
	Calendar getExperationDate();
	String   getClientName();
	Calendar getAuthDate();

	/**
	 * @return время подтверждения операции ПИН-кодом (дата в объекте - 01.01.1970)
	 */
	Calendar getAuthTime();

	Long     getConfirmClerkNumber();
	String   getConfirmClerkName();
	Calendar getConfirmDate();

	/**
	 * @return время исполнения операции (дата в объекте - 01.01.1970)
	 */
	Calendar getConfirmTime();

	Long     getPaymasterNumber();
	String   getPaymasterName();
	Calendar getPaymentDate();

	/**
	 * @return время исполнения операции (дата в объекте - 01.01.1970)
	 */
	Calendar getPaymentTime();

	Calendar getOperationDate();

	/**
	 * @return время исполнения операции (дата в объекте - 01.01.1970)
	 */
	Calendar getOperationTime();
}
