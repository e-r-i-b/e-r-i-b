package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.Collection;
import java.util.Set;

/**
 * @author Evgrafov
 * @ created 24.07.2006
 * @ $Author: erkin $
 * @ $Revision: 48492 $
 */

@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class EditEmpoweredPersonForm extends EditPersonForm
{
	private Long                    id;
	private ActivePerson            empoweredPerson;

	private AccessEmpoweredPersonSubForm    simpleAccess = new AccessEmpoweredPersonSubForm();
	private AccessEmpoweredPersonSubForm    secureAccess = new AccessEmpoweredPersonSubForm();

	private Collection<AccountLink> allAccountLinks;
	private String[]                selectedAccountLinks = new String[]{};

	private Collection<CardLink>    allCardLinks;
	private String[]                selectedCardLinks = new String[]{};

	private Set<PersonDocument> personDocuments;

	public Long getId()
	{
	    return id;
	}

	public void setId(Long id)
	{
	    this.id = id;
	}

	public Long getEditedPerson()
	{
		return id;
	}

	public ActivePerson getEmpoweredPerson()
	{
		return empoweredPerson;
	}

	public void setEmpoweredPerson(ActivePerson empoweredPerson)
	{
		this.empoweredPerson = empoweredPerson;
	}

	public Collection<AccountLink> getAllAccountLinks()
	{
		return allAccountLinks;
	}

	public void setAllAccountLinks(Collection<AccountLink> allAccountLinks)
	{
		this.allAccountLinks = allAccountLinks;
	}

	public String[] getSelectedAccountLinks()
	{
		return selectedAccountLinks;
	}

	public void setSelectedAccountLinks(String[] selectedAccountLinks)
	{
		this.selectedAccountLinks = selectedAccountLinks;
	}

	public Collection<CardLink> getAllCardLinks()
	{
		return allCardLinks;
	}

	public void setAllCardLinks(Collection<CardLink> allCardLinks)
	{
		this.allCardLinks = allCardLinks;
	}

	public String[] getSelectedCardLinks()
	{
		return selectedCardLinks;
	}

	public void setSelectedCardLinks(String[] selectedCardLinks)
	{
		this.selectedCardLinks = selectedCardLinks;
	}

	public AccessEmpoweredPersonSubForm getSecureAccess()
	{
		return secureAccess;
	}

	public void setSecureAccess(AccessEmpoweredPersonSubForm secureAccess)
	{
		this.secureAccess = secureAccess;
	}

	public AccessEmpoweredPersonSubForm getSimpleAccess()
	{
		return simpleAccess;
	}

	public void setSimpleAccess(AccessEmpoweredPersonSubForm simpleAccess)
	{
		this.simpleAccess = simpleAccess;
	}

	public Set<PersonDocument> getPersonDocuments()
	{
		return personDocuments;
	}

	public void setPersonDocuments(Set<PersonDocument> personDocuments)
	{
		this.personDocuments = personDocuments;
	}
}