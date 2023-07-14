package com.rssl.phizic.web.common.client.product;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Mescheryakova
 * @ created 02.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductListForm extends ListFormBase
{
	private String loanId;
	private String offerId;
	private Long duration;
	private String paymentForm;
	private Long doRedirectToLoanCardOffer = null;
	private boolean loanCardClaimAvailable = true;

	/**
	 * передается в качестве GET.
	 * Нужен для изминения надписи на странице списка предложений.
	 */
	private boolean changeType = false;

	public String getLoanId()
	{
		return loanId;
	}

	public void setLoanId(String loanId)
	{
		this.loanId = loanId;
	}

	public String getOfferId()
	{
		return offerId;
	}

	public void setOfferId(String offerId)
	{
		this.offerId = offerId;
	}

	public Long getDuration()
	{
		return duration;
	}

	public void setDuration(Long duration)
	{
		this.duration = duration;
	}

	public String getPaymentForm()
	{
		return paymentForm;
	}

	public void setPaymentForm(String paymentForm)
	{
		this.paymentForm = paymentForm;
	}

	public boolean isChangeType()
	{
		return changeType;
	}

	public void setChangeType(boolean changeType)
	{
		this.changeType = changeType;
	}

	public Long getDoRedirectToLoanCardOffer()
	{
		return doRedirectToLoanCardOffer;
	}

	public void setDoRedirectToLoanCardOffer(Long doRedirectToLoanCardOffer)
	{
		this.doRedirectToLoanCardOffer = doRedirectToLoanCardOffer;
	}

	/**
	 * @return доступна ли заявка на кредитную карту
	 */
	public boolean isLoanCardClaimAvailable()
	{
		return loanCardClaimAvailable;
	}

	public void setLoanCardClaimAvailable(boolean loanCardClaimAvailable)
	{
		this.loanCardClaimAvailable = loanCardClaimAvailable;
	}
}
