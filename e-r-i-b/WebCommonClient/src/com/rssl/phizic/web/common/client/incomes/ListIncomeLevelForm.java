package com.rssl.phizic.web.common.client.incomes;

import com.rssl.phizic.business.creditcards.incomes.IncomeLevel;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Dorzhinov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListIncomeLevelForm extends ListFormBase<IncomeLevel>
{
	private String loanId;
	private Long doRedirectToLoanCardOffer = null;
	private Boolean isIncomeLevelsExists;
	private boolean loanCardClaimAvailable = true;
	private int timeToRefresh;
	private boolean fromRefresh;

	public String getLoanId()
	{
		return loanId;
	}

	public void setLoanId(String loanId)
	{
		this.loanId = loanId;
	}

	public Long getDoRedirectToLoanCardOffer()
	{
		return doRedirectToLoanCardOffer;
	}

	public void setDoRedirectToLoanCardOffer(Long doRedirectToLoanCardOffer)
	{
		this.doRedirectToLoanCardOffer = doRedirectToLoanCardOffer;
	}

	public Boolean isIncomeLevelsExists()
	{
		return isIncomeLevelsExists;
	}

	public void setIncomeLevelsExists(Boolean incomeLevelsExists)
	{
		this.isIncomeLevelsExists = incomeLevelsExists;
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

	public int getTimeToRefresh()
	{
		return timeToRefresh;
	}

	public void setTimeToRefresh(int timeToRefresh)
	{
		this.timeToRefresh = timeToRefresh;
	}

	public boolean isFromRefresh()
	{
		return fromRefresh;
	}

	public void setFromRefresh(boolean fromRefresh)
	{
		this.fromRefresh = fromRefresh;
	}
}
