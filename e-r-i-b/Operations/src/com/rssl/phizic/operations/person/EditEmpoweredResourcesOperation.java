package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 25.07.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */
public class EditEmpoweredResourcesOperation extends PersonOperationBase
{
	private List<String>              newAccountNumbers;    // номера новых cчетов
	private EmpoweredAccountsModifier accountsModifier;

	private List<String>              newCardNumbers; //номера новых карт
	private EmpoweredCardsModifier    cardsModifier;


	public void initialize(ActivePerson person, ActivePerson trustingPerson) throws BusinessLogicException, BusinessException
	{
		setPersonId(trustingPerson.getId());

		if(!Person.ACTIVE.equals(person.getStatus()))
	        setMode(PersonOperationMode.direct);

		if (!trustingPerson.getId().equals(person.getTrustingPersonId()))
		{
			throw new BusinessException("person не является представителем");
		}

		initializeInternal(person, trustingPerson);
	}

	private void initializeInternal(ActivePerson person, ActivePerson trustingPerson) throws BusinessException, BusinessLogicException
	{
		accountsModifier = new EmpoweredAccountsModifier(person, trustingPerson, getInstanceName());
		cardsModifier = new EmpoweredCardsModifier(person, trustingPerson,getInstanceName());
	}

	//счета
	public Collection<AccountLink> getTrustingAccountLinks() throws BusinessException, BusinessLogicException
	{
		return accountsModifier.getTrustingAccounts();
	}

	public Collection<AccountLink> getCurrentAccountLinks()
	{
		return accountsModifier.getCurrentAccounts();
	}

	public void setNewAccountLinks(List<String> accountNumbers)
	{
		newAccountNumbers = new ArrayList<String>(accountNumbers);
	}
	//карты
   public Collection<CardLink> getTrustingCardLinks() throws BusinessException, BusinessLogicException
	{
		return cardsModifier.getTrustingCards();
	}

	public Collection<CardLink> getCurrentCardLinks()
	{
		return cardsModifier.getCurrentCards();
	}

	public void setNewCardLinks(List<String> cardNumbers)
	{
		newCardNumbers = new ArrayList<String>(cardNumbers);
	}
	//
	public void save() throws BusinessException
	{
		Map<String, AccountLink> all = new HashMap<String, AccountLink>();
		Map<String, CardLink> allCards = new HashMap<String, CardLink>();

		for (AccountLink accountLink : accountsModifier.getTrustingAccounts())
		{
			all.put(accountLink.getAccount().getNumber(), accountLink);
		}
		for (CardLink cardLink : cardsModifier.getTrustingCards())
		{
			allCards.put(cardLink.getCard().getNumber(), cardLink);
		}

		final List<AccountLink> newAccountLinks = new ArrayList<AccountLink>();
		final List<CardLink> newCardLinks = new ArrayList<CardLink>();

		for (String accountNumber : newAccountNumbers)
		{
			if(all.get(accountNumber) != null)
				newAccountLinks.add(all.get(accountNumber));
		}
		for (String cardNumber : newCardNumbers)
		{
			if(allCards.get(cardNumber) != null)
				newCardLinks.add(allCards.get(cardNumber));
		}

		accountsModifier.setInstanceName(getInstanceName());
		accountsModifier.change(newAccountLinks);
		cardsModifier.setInstanceName(getInstanceName());
		cardsModifier.change(newCardLinks);
	}
}