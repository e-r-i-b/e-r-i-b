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
	 * @return ����� ������������� �������� ���-����� (���� � ������� - 01.01.1970)
	 */
	Calendar getAuthTime();

	Long     getConfirmClerkNumber();
	String   getConfirmClerkName();
	Calendar getConfirmDate();

	/**
	 * @return ����� ���������� �������� (���� � ������� - 01.01.1970)
	 */
	Calendar getConfirmTime();

	Long     getPaymasterNumber();
	String   getPaymasterName();
	Calendar getPaymentDate();

	/**
	 * @return ����� ���������� �������� (���� � ������� - 01.01.1970)
	 */
	Calendar getPaymentTime();

	Calendar getOperationDate();

	/**
	 * @return ����� ���������� �������� (���� � ������� - 01.01.1970)
	 */
	Calendar getOperationTime();
}
