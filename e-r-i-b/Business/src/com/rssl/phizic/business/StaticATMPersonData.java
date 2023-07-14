package com.rssl.phizic.business;

import com.rssl.phizic.business.clients.ClientResourceHelper;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;

import java.util.List;

/**
 * @author mescheryakova
 * @ created 01.10.2012
 * @ $Author$
 * @ $Revision$
 */


public class StaticATMPersonData  extends StaticPersonData
{
	//TODO Костыль для BUG082486: [ISUP] АТМ : поиск поставщика. Прихранивает в ID региона при получении cправочника поставщиков.
	// Убрать в следующем релизе и актуализировать спеку по ATM, добавив регион для "Получения списка услуг поставщика".
	private Long servProvRegionID;

	public StaticATMPersonData(ActivePerson person)
	{
		super(person);
	}

	public List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.getInATMLinks(getLogin(), AccountLink.class);
	}

	public AccountLink getAccount(Long accountLinkId) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		AccountLink al = resourceService.findInATMLinkById(getLogin(), AccountLink.class, accountLinkId);
		if (al == null)
			throw new ResourceNotFoundBusinessException("cчет c id=" + accountLinkId + " не принадлежит пользователю", AccountLink.class);
		return al;
	}

	public AccountLink findAccount(String accountNumber) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.findInATMLinkByNumber(getLogin(), ResourceType.ACCOUNT, accountNumber);
	}

	public List<CardLink> getCards() throws BusinessException, BusinessLogicException
	{
		reloadCards();
		return resourceService.getInATMLinks(getLogin(), CardLink.class);
	}

	public CardLink getCard(Long cardLinkId) throws BusinessException
	{
		try
		{
			reloadCards();
			CardLink cl = resourceService.findInATMLinkById(getLogin(), CardLink.class, cardLinkId);
			if (cl == null)
				throw new ResourceNotFoundBusinessException("карта c id=" + cardLinkId + " не принадлежит пользователю", CardLink.class);
			return cl;
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	public CardLink findCard(String cardNumber) throws BusinessException
	{
		try
		{
			reloadCards();
			return resourceService.findInATMLinkByNumber(getLogin(), ResourceType.CARD, cardNumber);
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	public List<LoanLink> getLoans() throws BusinessException, BusinessLogicException
	{
		loadLoans();
		return resourceService.getInATMLinks(getLogin(), LoanLink.class);
	}

	public LoanLink getLoan(Long loanLinkId) throws BusinessException, BusinessLogicException
	{
		loadLoans();
		LoanLink link = resourceService.findInATMLinkById(getLogin(), LoanLink.class, loanLinkId);
		if (link != null)
			return link;
		throw new ResourceNotFoundBusinessException("кредит c id=" + loanLinkId + " не принадлежит пользователю", LoanLink.class);
	}

	public LoanLink findLoan(String accountNumber) throws BusinessException
	{
		return resourceService.findInATMLinkByNumber(getLogin(), ResourceType.LOAN, accountNumber);
	}

	public List<IMAccountLink> getIMAccountLinks() throws BusinessException, BusinessLogicException
	{
		reloadIMAccounts();
		return resourceService.getInATMLinks(getLogin(), IMAccountLink.class);
	}

	public IMAccountLink getImAccountLinkById(Long imAccountLinkId) throws BusinessException
	{
		IMAccountLink link = resourceService.findInATMLinkById(getLogin(), IMAccountLink.class, imAccountLinkId);
		if (link != null)
			return link;
		throw new ResourceNotFoundBusinessException("ОМС c id=" + imAccountLinkId + " не принадлежит пользователю", LoanLink.class);
	}

	public IMAccountLink findIMAccount(String imAccountNumber) throws BusinessException
	{
		return resourceService.findInATMLinkByNumber(getLogin(), ResourceType.IM_ACCOUNT, imAccountNumber);
	}

	public void decCountNewLetters()
	{
		//Пока в ATM не используется
	}

	public Long getServProvRegionID()
	{
		return servProvRegionID;
	}

	public void setServProvRegionID(Long servProvRegionID)
	{
		this.servProvRegionID = servProvRegionID;
	}
}

