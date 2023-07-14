package com.rssl.phizic.payment;

import java.math.BigDecimal;

/**
 * ��������� ������ "������ �������"
 * @author Rtischeva
 * @created 09.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface LoanPaymentTask extends PaymentTask
{
	/**
	 * ������ ����� �������
	 * @param loanAlias - ����� �������
	 */
	void setLoanAlias(String loanAlias);

	/**
	 * ������� ����� �������
	 */
	String getLoanAlias();

	/**
	 *  ������ ��� �������
	 * @param loanLinkCode - ��� �������
	 */
	void setLoanLinkCode(String loanLinkCode);

	/**
	 * ������ ��� ������� ��������
	 * @param fromResourceCode - ��� ������� ��������
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * ������ ����� ��������
	 * @param amount - ����� ��������
	 */
	void setAmount(BigDecimal amount);

	/**
	 * ������� ����� ��������
	 * @return  ����� ��������
	 */
	BigDecimal getAmount();

	/*
	 * ���������� ����� ����� ��������
	 * @param fromResourceAlias - ����� ����� �������� (never null)
	 */
	void setFromResourceAlias(String fromResourceAlias);

	/**
	 * @return ����� ����� ��������
	 */
	String getFromResourceAlias();

	/**
	 * ���������� ������� �������� ������������� �� �������
	 * @param balanceAmount
	 */
	void setLoanBalanceAmount(String balanceAmount);

	/**
	 * ������� ������� �������� ������������� �� �������
	 * @return
	 */
	String getLoanBalanceAmount();

	/**
	 * ������� ������ ������� � ���� ������
	 * @return
	 */
	String getLoanCurrency();

	/**
	 * ���������� ������ �������
	 * @param loanCurrency - ������ �������
	 */
	void setLoanCurrency(String loanCurrency);
}
