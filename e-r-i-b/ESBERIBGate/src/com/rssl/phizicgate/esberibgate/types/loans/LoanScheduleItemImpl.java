package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.util.Map;

/**
 * @author gladishev
 * @ created 14.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanScheduleItemImpl implements ScheduleItem
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
	private Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDateDebtItemMap;
	private Money overpayment;
	private Money remainDebt;

	public LoanScheduleItemImpl()
	{}

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
		return penaltyDateDebtItemMap;
	}

	public Money getOverpayment()
	{
		return overpayment;
	}

	public LoanPaymentState getPaymentState()
	{
		return paymentState;
	}

	public Long getPaymentNumber()
	{
		return paymentNumber;
	}

	public String getIdSpacing()
	{
		return idSpacing;
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

	public void setPaymentState(LoanPaymentState paymentState)
	{
		this.paymentState = paymentState;
	}

	public void setPaymentNumber(Long paymentNumber)
	{
		this.paymentNumber = paymentNumber;
	}

	public void setIdSpacing(String idSpacing)
	{
		this.idSpacing = idSpacing;
	}

	public void setPenaltyDateDebtItemMap(Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDateDebtItemMap)
	{
		this.penaltyDateDebtItemMap = penaltyDateDebtItemMap;
	}

	public void setOverpayment(Money overpayment)
	{
		this.overpayment = overpayment;
	}

    public Money getRemainDebt()
    {
        return remainDebt;
    }

    public void setRemainDebt(Money remainDebt)
    {
        this.remainDebt = remainDebt;
	}
}