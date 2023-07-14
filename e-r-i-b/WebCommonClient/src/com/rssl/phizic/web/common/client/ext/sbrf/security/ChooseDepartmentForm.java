package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 @author Pankin
 @ created 13.12.2010
 @ $Author$
 @ $Revision$
 */
public class ChooseDepartmentForm extends ListFormBase
{
	private List<Person> persons;
	private Map<Person, List<CardLink>> cardLinks;
	private Map<Person, List<AccountLink>> accountLinks;
	private Map<Person, List<LoanLink>> loanLinks;
	private Map<Person, Boolean> isPersonBlocked;

	public List<Person> getPersons()
	{
		return persons;
	}

	public void setPersons(List<Person> persons)
	{
		this.persons = persons;
	}

	public Map<Person, List<CardLink>> getCardLinks()
	{
		return cardLinks;
	}

	public void setCardLinks(Map<Person, List<CardLink>> cardLinks)
	{
		this.cardLinks = cardLinks;
	}

	public Map<Person, List<AccountLink>> getAccountLinks()
	{
		return accountLinks;
	}

	public void setAccountLinks(Map<Person, List<AccountLink>> accountLinks)
	{
		this.accountLinks = accountLinks;
	}

	public Map<Person, List<LoanLink>> getLoanLinks()
	{
		return loanLinks;
	}

	public void setLoanLinks(Map<Person, List<LoanLink>> loanLinks)
	{
		this.loanLinks = loanLinks;
	}

	public Map<Person, Boolean> getPersonBlocked()
	{
		return Collections.unmodifiableMap(isPersonBlocked);
	}

	public void setPersonBlocked(Map<Person, Boolean> personBlocked)
	{
		isPersonBlocked = personBlocked;
	}
}
