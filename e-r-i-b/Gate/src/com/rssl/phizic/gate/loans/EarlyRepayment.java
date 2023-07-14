package com.rssl.phizic.gate.loans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ��������� ���������
 * @author basharin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */

public interface EarlyRepayment
{
	/**
	 * <Amount> GetPrivateLoanDetailsRs
	 * @return ����� �������, ��������� ����� ��������� �������
	 */
	public BigDecimal getAmount();

	/**
	 * <Date> GetPrivateLoanDetailsRs
	 * @return ���� ���������� ���������
	 */
	public Date getDate();

	/**
	 * <Status> GetPrivateLoanDetailsRs
	 * @return ������ ���������� ���������((��������/ ���������� / ��������� / ���������� [������� ����������])
	 */
	public String getStatus();

	/**
	 * <Account> GetPrivateLoanDetailsRs
	 * @return ���� ��� ���������
	 */
	public String getAccount();

	/**
	 * <RepaymentChannel> GetPrivateLoanDetailsRs
	 * @return �����, �� �������� ���������������� ��.
	 * ��������� ��������:
	 * 1 � ����� ���
	 * 2 � ����� �� ���(���)
	 */
	public String getRepaymentChannel();

	/**
	 * <TerminationRequestId> GetPrivateLoanDetailsRs
	 * @return ������������� ������ �� ��������� ���������
	 */
	public long getTerminationRequestId();

}
