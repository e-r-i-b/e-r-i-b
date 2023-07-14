package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;

/** Created by IntelliJ IDEA.
 *  User: Evgrafov
 *  Date: 05.10.2005
 * Time: 21:15:52 */
public class ListPersonResourcesForm  extends ListFormBase
{
    private ActivePerson activePerson;

	private List<IMAccountLink> imAccounts = new ArrayList<IMAccountLink>();
    private List<AccountLink> accounts = new ArrayList<AccountLink>();
    private List<CardLink> cards    = new ArrayList<CardLink>();

    private String[] selectedCards    = new String[0];
    private String[] selectedAccounts = new String[0];

    // для импорта счетов и карт - тип и выбранные id
    private String resourcesType       = "";
    private String[] selectedResources = new String[0];

    private String     newCard;
    private String     newAccount;
    private String[]   selectedPaymentAbleAccounts = new String[0];
	private Boolean    needAccount;
	private Department department;
	private Boolean    modified=false;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}


	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public Boolean getNeedAccount()
	{
		return needAccount;
	}

	public void setNeedAccount(Boolean needAccount)
	{
		this.needAccount = needAccount;
	}

	public ActivePerson getActivePerson()
    {
        return activePerson;
    }

    public void setActivePerson(ActivePerson activePerson)
    {
        this.activePerson = activePerson;
    }

	public List<IMAccountLink> getImAccounts()
	{
		return imAccounts;
	}

	public void setImAccounts(List<IMAccountLink> imAccounts)
	{
		this.imAccounts = imAccounts;
	}

	public List<AccountLink> getAccounts()
	{
        return accounts;
    }

    public void setAccounts(List<AccountLink> accounts) {
        this.accounts = accounts;
    }

    public List<CardLink> getCards() {
        return cards;
    }

    public void setCards(List<CardLink> cards) {
        this.cards = cards;
    }

    public String[] getSelectedCards() {
        return selectedCards;
    }

    public void setSelectedCards(String[] selectedCards) {
        this.selectedCards = selectedCards;
    }

    public String[] getSelectedAccounts() {
        return selectedAccounts;
    }

    public void setSelectedAccounts(String[] selectedAccounts) {
        this.selectedAccounts = selectedAccounts;
    }

    public String[] getSelectedResources() {
        return selectedResources;
    }

    public void setSelectedResources(String[] selectedResources) {
        this.selectedResources = selectedResources;
    }

    public void setResourcesType(String resourcesType) {
        this.resourcesType = resourcesType;
    }

    public String getResourcesType()
    {
        return resourcesType;
    }

    public String getNewCard()
    {
        return newCard;
    }

    public void setNewCard(String newCard)
    {
        this.newCard = newCard;
    }

    public String getNewAccount()
    {
        return newAccount;
    }

    public void setNewAccount(String newAccount)
    {
        this.newAccount = newAccount;
    }

    public String[] getSelectedPaymentAbleAccounts()
    {
        return selectedPaymentAbleAccounts;
    }

    public void setSelectedPaymentAbleAccounts(String[] selectedPaymentAbleAccounts)
    {
        this.selectedPaymentAbleAccounts = selectedPaymentAbleAccounts;
    }
}
