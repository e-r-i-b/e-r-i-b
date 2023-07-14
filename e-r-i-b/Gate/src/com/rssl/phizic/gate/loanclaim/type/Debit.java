package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.gate.loanclaim.dictionary.LoanPaymentMethod;
import com.rssl.phizic.gate.loanclaim.dictionary.LoanPaymentPeriod;
import com.rssl.phizic.gate.loanclaim.dictionary.TypeOfDebit;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ObjectUtils;

import java.math.BigDecimal;
import java.util.Calendar;

import static com.rssl.phizic.utils.NumericUtil.equalsAsMoneyAmount;

/**
 * @author Erkin
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �������������
 */
@Immutable
public class Debit
{
	private final TypeOfDebit type;

	private final String agreementNumber;

	private final Calendar startDate;

	private final Calendar endDate;

	private final String creditorName;

	private final Currency currency;

	private final BigDecimal totalAmount;

	private final BigDecimal rate;

	private final BigDecimal debitAmount;

	private final BigDecimal paymentAmount;

	private final LoanPaymentMethod paymentMethod;

	private final LoanPaymentPeriod paymentPeriod;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param type - ��� ������������� (never null)
	 * @param agreementNumber - ����� �������� (never null)
	 * @param startDate - ���� ���������� �������� (never null)
	 * @param endDate - ���� ��������� �������� (never null)
	 * @param creditorName - �������� (never null)
	 * @param currency - ������ (never null)
	 * @param totalAmount - ����� ������� (never null)
	 * @param rate - ������ �� ������� (never null)
	 * @param debitAmount - ������� ������������� (can be null)
	 * @param paymentAmount - ����� ����������� ������� (can be null)
	 * @param paymentMethod - ������ ��������� ������������� (can be null)
	 * @param paymentPeriod - ������ ��������� (can be null)
	 */
	public Debit(TypeOfDebit type, String agreementNumber, Calendar startDate, Calendar endDate, String creditorName, Currency currency, BigDecimal totalAmount, BigDecimal rate, BigDecimal debitAmount, BigDecimal paymentAmount, LoanPaymentMethod paymentMethod, LoanPaymentPeriod paymentPeriod)
	{
		if (type == null)
		    throw new IllegalArgumentException("�� ������ ��� �������������");
		if (StringHelper.isEmpty(agreementNumber))
		    throw new IllegalArgumentException("�� ������ ����� ��������");
		if (startDate == null)
			throw new IllegalArgumentException("�� ������� ���� ���������� ��������");
		if (endDate == null)
			throw new IllegalArgumentException("�� ������� ���� ��������� ��������");
		if (StringHelper.isEmpty(creditorName))
			throw new IllegalArgumentException("�� ������ ��������");
		if (currency == null)
			throw new IllegalArgumentException("�� ������� ������");
		if (totalAmount == null)
			throw new IllegalArgumentException("�� ������� ����� �������");
		if (rate == null)
			throw new IllegalArgumentException("�� ������� ������ �� �������");

		this.type = type;
		this.agreementNumber = agreementNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.creditorName = creditorName;
		this.currency = currency;
		this.totalAmount = totalAmount;
		this.rate = rate;
		this.debitAmount = debitAmount;
		this.paymentAmount = paymentAmount;
		this.paymentMethod = paymentMethod;
		this.paymentPeriod = paymentPeriod;
	}

	/**
	 * @return ��� ������������� (never null)
	 */
	public TypeOfDebit getType()
	{
		return type;
	}

	/**
	 * @return ����� �������� (never null)
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * @return ���� ���������� �������� (never null)
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * @return ���� ��������� �������� (never null)
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * @return �������� (never null)
	 */
	public String getCreditorName()
	{
		return creditorName;
	}

	/**
	 * @return ������ (never null)
	 */
	public Currency getCurrency()
	{
		return currency;
	}

	/**
	 * @return ����� ������� (never null)
	 */
	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * @return ������ �� ������� (never null)
	 */
	public BigDecimal getRate()
	{
		return rate;
	}

	/**
	 * @return ������� ������������� (can be null)
	 */
	public BigDecimal getDebitAmount()
	{
		return debitAmount;
	}

	/**
	 * @return ����� ����������� ������� (can be null)
	 */
	public BigDecimal getPaymentAmount()
	{
		return paymentAmount;
	}

	/**
	 * @return ������ ��������� ������������� (can be null)
	 */
	public LoanPaymentMethod getPaymentMethod()
	{
		return paymentMethod;
	}

	/**
	 * @return ������ ��������� (can be null)
	 */
	public LoanPaymentPeriod getPaymentPeriod()
	{
		return paymentPeriod;
	}

	@Override
	public int hashCode()
	{
		return agreementNumber.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Debit other = (Debit) o;

		boolean rc = ObjectUtils.equals(type,           other.type);
		rc = rc && ObjectUtils.equals(agreementNumber,  other.agreementNumber);
		rc = rc && ObjectUtils.equals(creditorName,     other.creditorName);
		rc = rc && ObjectUtils.equals(startDate,        other.startDate);
		rc = rc && ObjectUtils.equals(endDate,          other.endDate);
		rc = rc && ObjectUtils.equals(currency,         other.currency);
		rc = rc && equalsAsMoneyAmount(totalAmount,     other.totalAmount);
		rc = rc && equalsAsMoneyAmount(rate,            other.rate);
		rc = rc && equalsAsMoneyAmount(debitAmount,     other.debitAmount);
		rc = rc && equalsAsMoneyAmount(paymentAmount,   other.paymentAmount);
		rc = rc && ObjectUtils.equals(paymentMethod,    other.paymentMethod);
		rc = rc && ObjectUtils.equals(paymentPeriod,    other.paymentPeriod);
		return rc;
	}
}
