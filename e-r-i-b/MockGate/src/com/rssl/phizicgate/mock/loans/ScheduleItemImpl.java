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
    * Дата выплаты
    * Domain: Date
    *
    * @return дата
    */
	public Calendar getDate()
	{
		return date;
	}

   /**
    * Сумма выплаты по основному долгу
    *
    *
    * @return сумма
    */
	public Money getPrincipalAmount()
	{
		return principalAmount;
	}

   /**
    * Сумма выплаты по процентам
    *
    *
    * @return сумма
    */
	public Money getInterestsAmount()
	{
		return interestsAmount;
	}

   /**
    * Сумма комиссии
    *
    *
    * @return сумма
    */
	public Money getCommissionAmount()
	{
		return commissionAmount;
	}

   /**
    * Сумма платежа (итого).
    *
    *
    * @return сумма
    */
	public Money getTotalAmount()
	{
		return totalAmount;
	}

   /**
    * Общая сумма, которую клиент должен заплатить (погасить) за промежуток времени.
    * Может не совпадать с getTotalAmount, так как могут присутствовать и другие, неучтенные здась, составляющие (штрафы и пр.)
    *
    *
    * @return сумма
    */
	public Money getTotalPaymentAmount()
	{
		return totalPaymentAmount;
	}

   /**
    * Сумма для досрочного погашения
    *
    *
    * @return сумма
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
