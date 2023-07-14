package com.rssl.phizic.payment;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.ermb.ErmbPaymentType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация платежной задачи "Оплата кредита"
 * @author Rtischeva
 * @created 09.10.13
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentTaskImpl extends PaymentTaskBase implements LoanPaymentTask
{
	private BigDecimal amount; //сумма списания

	private String fromResourceCode; //код карты списания

	private String fromResourceAlias; //алиас карты списания

	private String loanLinkCode; //код кредита

	private String loanAlias; //алиас кредита

	private String loanCurrency; //валюта кредита

	private String loanBalanceAmount; //сумма остатка ссудной задолженности

	@Override
	protected String getFormName()
	{
		return FormConstants.LOAN_PAYMENT_FORM;
	}

	@Override
	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(PaymentFieldKeys.FROM_RESOURCE_KEY, fromResourceCode);
		map.put(PaymentFieldKeys.AMOUNT, amount.toPlainString());
		map.put("loan", loanLinkCode);
		return new MapValuesSource(map);
	}

	public void setFromResourceCode(String fromResourceCode)
	{
		this.fromResourceCode = fromResourceCode;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setFromResourceAlias(String fromResourceAlias)
	{
		this.fromResourceAlias = fromResourceAlias;
	}

	public String getFromResourceAlias()
	{
		return fromResourceAlias;
	}

	public void setLoanAlias(String loanAlias)
	{
		this.loanAlias = loanAlias;
	}

	public String getLoanAlias()
	{
		return loanAlias;
	}

	public void setLoanLinkCode(String loanLinkCode)
	{
		this.loanLinkCode = loanLinkCode;
	}

	public void setLoanBalanceAmount(String balanceAmount)
	{
		this.loanBalanceAmount = balanceAmount;
	}

	public String getLoanBalanceAmount()
	{
		return loanBalanceAmount;
	}

	public String getLoanCurrency()
	{
		return loanCurrency;
	}

	public void setLoanCurrency(String loanCurrency)
	{
		this.loanCurrency = loanCurrency;
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.LOAN_PAYMENT;
	}
}
