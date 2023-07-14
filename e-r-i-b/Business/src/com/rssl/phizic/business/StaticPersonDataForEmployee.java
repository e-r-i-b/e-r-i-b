package com.rssl.phizic.business;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.List;

/**
 * @author bogdanov
 * @ created 06.06.2012
 * @ $Author$
 * @ $Revision$
 *
 * Данные о клиенте, отображаемые сотруднику.
 * Для сотрудника необходимо, чтобы показывались все карты и счета, даже те которые скрыты у клиента.
 */

public class StaticPersonDataForEmployee extends StaticPersonData
{
	public StaticPersonDataForEmployee(ActivePerson person)
	{
		super(person);
	}

	@Override
	public List<CardLink> getCards() throws BusinessException, BusinessLogicException
	{
		return resourceService.getLinks(getLogin(), CardLink.class);
	}

	@Override
	public CardLink getCard(Long cardLinkId) throws BusinessException
	{
		CardLink cl = resourceService.findLinkById(CardLink.class, cardLinkId);
		if (cl == null)
			throw new ResourceNotFoundBusinessException("карта c id=" + cardLinkId + " не принадлежит пользователю", CardLink.class);
		return cl;
	}

	@Override
	public CardLink findCard(String cardNumber) throws BusinessException
	{
		return resourceService.findLinkByNumber(getLogin(), ResourceType.CARD, cardNumber);
	}
	
	@Override
	public AccountLink getAccount(Long accountLinkId) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		AccountLink al = resourceService.findLinkById(AccountLink.class, accountLinkId);
		if (al == null)
			throw new ResourceNotFoundBusinessException("cчет c id=" + accountLinkId + " не принадлежит пользователю", AccountLink.class);
		return al;
	}

	@Override
	public AccountLink findAccount(String accountNumber) throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.findLinkByNumber(getLogin(), ResourceType.ACCOUNT, accountNumber);
	}

	@Override
	public List<AccountLink> getAccounts() throws BusinessException, BusinessLogicException
	{
		reloadAccounts();
		return resourceService.getLinks(getLogin(), AccountLink.class);
	}

	@Override
	protected void reloadAccounts() throws BusinessException, BusinessLogicException
	{
		if (needReloadAccounts)
		{
			needReloadAccounts = false;
			reset(resourceService.getLinks(getLogin(), AccountLink.class));
			clientResourceService.updateResources(getPerson(), false, Account.class);
		}
	}
}
