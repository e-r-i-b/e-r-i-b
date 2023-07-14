package com.rssl.phizicgate.mock.loans;

import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 26.08.2008
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
	private Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDateDebtItemMap;
    private Money remainDebt;

	public ScheduleItemImpl(Calendar date, Money principalAmount, Money interestsAmount, Money commissionAmount,
	                        Money totalAmount, Money totalPaymentAmount, Money earlyPaymentAmount,
	                        LoanPaymentState paymentState, Long paymentNumber, String idSpacing, Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDateDebtItemMap)
	{
		this.earlyPaymentAmount = earlyPaymentAmount;
		this.totalPaymentAmount = totalPaymentAmount;
		this.totalAmount = totalAmount;
		this.commissionAmount = commissionAmount;
		this.interestsAmount = interestsAmount;
		this.principalAmount = principalAmount;
		this.date = date;
		this.paymentState = paymentState;
		this.paymentNumber = paymentNumber;
		this.idSpacing = idSpacing;
		this.penaltyDateDebtItemMap = penaltyDateDebtItemMap;
	}

	public ScheduleItemImpl(Calendar date, Money principalAmount, Money interestsAmount, Money commissionAmount,
	                        Money totalAmount, Money totalPaymentAmount, Money earlyPaymentAmount,
	                        LoanPaymentState paymentState, Long paymentNumber, String idSpacing, Map<PenaltyDateDebtItemType, DateDebtItem> penaltyDateDebtItemMap, Money remainDebt)
	{
		this.earlyPaymentAmount = earlyPaymentAmount;
		this.totalPaymentAmount = totalPaymentAmount;
		this.totalAmount = totalAmount;
		this.commissionAmount = commissionAmount;
		this.interestsAmount = interestsAmount;
		this.principalAmount = principalAmount;
		this.date = date;
		this.paymentState = paymentState;
		this.paymentNumber = paymentNumber;
		this.idSpacing = idSpacing;
		this.penaltyDateDebtItemMap = penaltyDateDebtItemMap;
		this.remainDebt = remainDebt;
	}

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
		return null;
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

    public Money getRemainDebt()
    {
        return remainDebt;
	}
}
