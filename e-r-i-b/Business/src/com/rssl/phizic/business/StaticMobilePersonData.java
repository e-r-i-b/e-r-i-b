package com.rssl.phizic.business;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;

import java.util.List;

/**
 * @author lukina
 * @ created 14.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class StaticMobilePersonData  extends StaticPersonData
{
    private boolean upToDate = false;

	public StaticMobilePersonData(ActivePerson person)
	{
		super(person);
	}

	public List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.getInMobileLinks(getLogin(), AccountLink.class);
	}

	public AccountLink getAccount(Long accountLinkId) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		AccountLink al = resourceService.findInMobileLinkById(getLogin(), AccountLink.class, accountLinkId);
		if (al == null)
			throw new ResourceNotFoundBusinessException("cчет c id=" + accountLinkId + " не принадлежит пользователю", AccountLink.class);
		return al;
	}

	public AccountLink findAccount(String accountNumber) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.findInMobileLinkByNumber(getLogin(), ResourceType.ACCOUNT, accountNumber);
	}

	public List<CardLink> getCards() throws BusinessException, BusinessLogicException
	{
		return resourceService.getInMobileLinks(getLogin(), CardLink.class);
	}

	public CardLink getCard(Long cardLinkId) throws BusinessException
	{
		CardLink cl = resourceService.findInMobileLinkById(getLogin(), CardLink.class, cardLinkId);
		if (cl == null)
			throw new ResourceNotFoundBusinessException("карта c id=" + cardLinkId + " не принадлежит пользователю", CardLink.class);
		return cl;
	}

	public CardLink findCard(String cardNumber) throws BusinessException
	{
		return resourceService.findInMobileLinkByNumber(getLogin(), ResourceType.CARD, cardNumber);
	}

	public List<LoanLink> getLoans() throws BusinessException, BusinessLogicException
	{
		loadLoans();
		return resourceService.getInMobileLinks(getLogin(), LoanLink.class);
	}

	public LoanLink getLoan(Long loanLinkId) throws BusinessException, BusinessLogicException
	{
		loadLoans();
		LoanLink link = resourceService.findInMobileLinkById(getLogin(), LoanLink.class, loanLinkId);
		if (link != null)
			return link;
		throw new ResourceNotFoundBusinessException("кредит c id=" + loanLinkId + " не принадлежит пользователю", LoanLink.class);
	}

	public LoanLink findLoan(String accountNumber) throws BusinessException
	{
		return resourceService.findInMobileLinkByNumber(getLogin(), ResourceType.LOAN, accountNumber);
	}

	public List<IMAccountLink> getIMAccountLinks() throws BusinessException, BusinessLogicException
	{
		reloadIMAccounts();
		return resourceService.getInMobileLinks(getLogin(), IMAccountLink.class);
	}

	public IMAccountLink getImAccountLinkById(Long imAccountLinkId) throws BusinessException
	{
		IMAccountLink link = resourceService.findInMobileLinkById(getLogin(), IMAccountLink.class, imAccountLinkId);
		if (link != null)
			return link;
		throw new ResourceNotFoundBusinessException("ОМС c id=" + imAccountLinkId + " не принадлежит пользователю", LoanLink.class);
	}

	public IMAccountLink findIMAccount(String imAccountNumber) throws BusinessException
	{
		return resourceService.findInMobileLinkByNumber(getLogin(), ResourceType.IM_ACCOUNT, imAccountNumber);
	}

	public void decCountNewLetters()
	{
		//Пока в mAPI не используется
	}
}
