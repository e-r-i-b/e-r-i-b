package com.rssl.phizic.operations.ext.sbrf.loans;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.operations.restrictions.LoanRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.loans.EarlyRepayment;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mihaylov
 * @ created 25.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetLoanInfoOperation extends OperationBase<LoanRestriction>
{
	private LoanLink loanLink;

	private boolean isUseStoredResource;
	private boolean isBackError;
	private boolean isEarlyLoanRepaymentAllowed;
	private boolean isEarlyLoanRepaymentPossible;
	private List<EarlyRepayment> earlyRepayments;

	public void initialize(Long loanLinkId) throws BusinessException, BusinessLogicException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		loanLink = provider.getPersonData().getLoan(loanLinkId);
		Loan loan = loanLink.getLoan();

		if (MockHelper.isMockObject(loan))
		{
	        isBackError = true;
		}
		else if(!getRestriction().accept(loan))
		{
			throw new RestrictionViolationException("Кредит: " + loanLink.getNumber());
		}

		if (loan.getState() == LoanState.closed)
			throw new ClosedLoanException("По закрытому кредиту нельзя получить детальную информацию и график платежей");

		if(!isBackError)
		{
			isEarlyLoanRepaymentAllowed = LoanClaimHelper.isEarlyLoanRepaymentAvailable(loan);
			isEarlyLoanRepaymentPossible = LoanClaimHelper.isEarlyLoanRepaymentPossible(loan);
			earlyRepayments = loan.getEarlyRepayments();
		}
		else
		{
			isEarlyLoanRepaymentAllowed = false;
			isEarlyLoanRepaymentPossible = false;
		}
		if(earlyRepayments==null)
			earlyRepayments = Collections.EMPTY_LIST;
		isUseStoredResource = !isUseStoredResource ? (loan instanceof AbstractStoredResource) : isUseStoredResource;
	}

	public LoanLink getLoanLink()
	{
		return loanLink;
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	/**
	 * @return была ли ошибка во время получения данных
	 */
	public boolean isBackError()
	{
		return isBackError;
	}

	public boolean isEarlyLoanRepaymentAllowed()
	{
		return isEarlyLoanRepaymentAllowed;
	}

	public boolean isEarlyLoanRepaymentPossible()
	{
		return isEarlyLoanRepaymentPossible;
	}

	public List<EarlyRepayment> getEarlyRepayments()
	{
		return earlyRepayments;
	}

    //todo сделать лучше
    public List<LoanAccountInfo> getLoanAccountsList() throws BusinessException, BusinessLogicException
    {
	    if (loanLink == null || loanLink.getLoan() == null || loanLink.getLoan().getAccount() == null)
		    return null;
	    List<LoanAccountInfo> list = new ArrayList<LoanAccountInfo>();
	    List<AccountLink> accountLinks = PersonContext.getPersonDataProvider().getPersonData().getAccounts();
	    List<CardLink> cardLinks = PersonContext.getPersonDataProvider().getPersonData().getCards();
	    for (String info : loanLink.getLoan().getAccount())
	    {
		    String[] use = info.split(":");
		    if (use[0].equals("account"))
		    {
			    AccountLink accLink = getFromPersonAccounts(accountLinks, use[1]);
			    if (accLink != null)
			        list.add(new LoanAccountInfo(accLink.getName(), accLink.getAccount().getBalance(), "account:" + accLink.getId().toString()));
		    }
		    else if (use[0].equals("card"))
		    {
			    CardLink cardLink = getFromPersonCards(cardLinks, use[1]);
			    if (cardLink != null)
			        list.add(new LoanAccountInfo(MaskUtil.getCutCardNumber(cardLink.getNumber()) + " " + cardLink.getName(), cardLink.getCard().getAvailableLimit(), "card:" + cardLink.getId().toString()));
		    }
		    else
		    {
			    throw new BusinessException("Неизвесныё тип ресурса");
		    }
	    }
	    return list;
    }

    private CardLink getFromPersonCards(List<CardLink> cards, String number) throws BusinessLogicException
    {
        for (CardLink link : cards) {
            if (link.getNumber().equals(number))
                return link;
        }
        return null;
    }

    private AccountLink getFromPersonAccounts(List<AccountLink> acсounts, String number) throws BusinessLogicException
    {
        for (AccountLink link : acсounts) {
            if (link.getNumber().equals(number))
                return link;
        }
        return null;
	}

	/**
	 * Получить номер заявки на получение кредита (в случае, если была автоматическая выдача кредита)
	 * @return номер заявки
	 */
	public String getApplicationNumber()
	{
		if (loanLink == null || loanLink.getLoan() == null)
			return null;

		Loan loan = loanLink.getLoan();
		if (loan.isAutoGranted())
			return loan.getApplicationNumber();

		return null;
	}
}
