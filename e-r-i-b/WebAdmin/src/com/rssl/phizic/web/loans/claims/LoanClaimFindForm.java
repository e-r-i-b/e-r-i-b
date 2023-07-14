package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма поиска/создания кредитной заявки
 * @author Rtischeva
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimFindForm extends ActionFormBase
{
	private String loanClaimNumber;
	private boolean continueSearch;

	public String getLoanClaimNumber()
	{
		return loanClaimNumber;
	}

	public void setLoanClaimNumber(String loanClaimNumber)
	{
		this.loanClaimNumber = loanClaimNumber;
	}

	public boolean isContinueSearch()
	{
		return continueSearch;
	}

	public void setContinueSearch(boolean continueSearch)
	{
		this.continueSearch = continueSearch;
	}
}
