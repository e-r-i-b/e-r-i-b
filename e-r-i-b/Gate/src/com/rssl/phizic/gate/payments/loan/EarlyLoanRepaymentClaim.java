package com.rssl.phizic.gate.payments.loan;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.AbstractTransfer;

import java.util.Calendar;

/**
 * ��������� ������ �� ��������� ��������� �������
 * User: petuhov
 * Date: 14.05.15
 * Time: 9:24 
 */
public interface EarlyLoanRepaymentClaim extends AbstractTransfer
{
	/**
	 * ���������� ������� ���������� ���������� ���������
	 * @return true, ���� ��������� ��������� - ���������
	 */
	public boolean isPartial();

	/**
	 * ���������� ���� ���������� ���������
	 * @return ���� ���������� ���������
	 */
	public Calendar getRepaymentDate();

	/**
	 * ���������� ������������� ���������� �������� � ���
	 * @return ������������� ������� � ������� ���
	 */
	public String getLoanIdentifier();

	/**
	 * ���� ��� ���������
	 * @return ����� ����� ��� ���������
	 */
	public String getChargeOffAccount();

	/**
	 * �����(��� ���������� ���������� ���������)
	 * @return ����� ��� ���������� ���������
	 */
	public Money getRepaymentAmount();

	void setOperUID(String currentOperUID);

	String getOperUID();
}