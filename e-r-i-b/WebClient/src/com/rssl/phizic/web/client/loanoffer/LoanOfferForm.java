package com.rssl.phizic.web.client.loanoffer;

import com.rssl.phizic.web.common.client.offer.LoanOfferCommonForm;

/**
 * User: Moshenko
 * Date: 30.05.2011
 * Time: 10:07:24
 */
public class LoanOfferForm extends LoanOfferCommonForm
{
	private Long loan;

	public Long getLoan()
	{
		return loan;
	}

	public void setLoan(Long loan)
	{
		this.loan = loan;
	}
}
