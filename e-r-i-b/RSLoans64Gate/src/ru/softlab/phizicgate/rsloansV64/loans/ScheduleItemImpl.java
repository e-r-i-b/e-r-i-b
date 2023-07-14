package ru.softlab.phizicgate.rsloansV64.loans;

import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Danilov
 * @ created 21.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class ScheduleItemImpl implements ScheduleItem
{
	private Calendar date;
	private Money principalAmount;
	private Money interestsAmount;
	private Money commissionAmount;
	private Money totalAmount;
	private Money totalPaymentAmount;
	private Money earlyPaymentAmount;
	private LoanPaymentState paymentState;
	private Long paymentNumber;
	private String idSpacing;

	/**
	 * ���� �������
	 * Domain: Date
	 *
	 * @return ����
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * ����� ������� �� ��������� �����
	 *
	 *
	 * @return �����
	 */
	public Money getPrincipalAmount()
	{
		return principalAmount;
	}

	/**
	 * ����� ������� �� ���������
	 *
	 *
	 * @return �����
	 */
	public Money getInterestsAmount()
	{
		return interestsAmount;
	}

	/**
	 * ����� ��������
	 *
	 *
	 * @return �����
	 */
	public Money getCommissionAmount()
	{
		return commissionAmount;
	}

	/**
	 * ����� ������� (�����).
	 *
	 *
	 * @return �����
	 */
	public Money getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * ����� �����, ������� ������ ������ ��������� (��������) �� ���������� �������.
	 * ����� �� ��������� � getTotalAmount, ��� ��� ����� �������������� � ������, ���������� �����, ������������ (������ � ��.)
	 *
	 *
	 * @return �����
	 */
	public Money getTotalPaymentAmount()
	{
		return totalPaymentAmount;
	}

	/**
	 * ����� ��� ���������� ���������
	 *
	 *
	 * @return �����
	 */
	public Money getEarlyPaymentAmount()
	{
		return earlyPaymentAmount;
	}

	public Map<PenaltyDateDebtItemType, DateDebtItem> getPenaltyDateDebtItemMap()
	{
		return null;
	}

	public Money getOverpayment()
	{
		return null;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public void setPrincipalAmount(Money principalAmount)
	{
		this.principalAmount = principalAmount;
	}

	public void setInterestsAmount(Money interestsAmount)
	{
		this.interestsAmount = interestsAmount;
	}

	public void setCommissionAmount(Money commissionAmount)
	{
		this.commissionAmount = commissionAmount;
	}

	public void setTotalAmount(Money totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public void setTotalPaymentAmount(Money totalPaymentAmount)
	{
		this.totalPaymentAmount = totalPaymentAmount;
	}

	public void setEarlyPaymentAmount(Money earlyPaymentAmount)
	{
		this.earlyPaymentAmount = earlyPaymentAmount;
	}

	public LoanPaymentState getPaymentState()
	{
		return paymentState;
	}

	public void setPaymentState(LoanPaymentState paymentState)
	{
		this.paymentState = paymentState;
	}

	public Long getPaymentNumber()
	{
		return paymentNumber;
	}

	public void setLoanPaymentNumber(Long paymentNumber)
	{
		this.paymentNumber = paymentNumber;
	}

	public String getIdSpacing()
	{
		return idSpacing;
	}

	public void setIdSpacing(String idSpacing)
	{
		this.idSpacing = idSpacing;
	}
}
