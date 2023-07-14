package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class PrintPersonForm extends ActionFormBase
{
    private Long person;
    private ActivePerson activePerson;
    private List<AccountLink> accountLinks;
	private List<Account> foreignAccounts;
	private List<AccountLink> currentAccountLinks;
    private List<CardLink> cardLinks;
    private List<ActivePerson> empoweredPersons;
    private Map<Long, List<Service>> empoweredPersonsServices;
    private Map<Long, List<AccountLink>> empoweredPersonsAccounts;
    private List<Service> services;
	private List<PaymentReceiverBase> receivers;
    private Date currentDate;
    private Department department;
    private ActivePerson principalPerson;
    private String pin;
	private String addAgreementNumber;
	private PersonDocument activeDocument;
	private PersonDocument activeNotResidentDocument;
	private PersonDocument migratoryCard;
	private Map<Long, PersonDocument> activeEmpoweredsDocument;
	private Map<Long, PersonDocument> activeEmpoweredsNotResidentDocument;
	private Map<Long, PersonDocument> empoweredsMigratoryCard;
	private String gorodCardNumber;
	private String simpleAuthChoice;
	private String messageService;

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public ActivePerson getActivePerson()
    {
        return activePerson;
    }

    public void setActivePerson(ActivePerson activePerson)
    {
        this.activePerson = activePerson;
    }

    public List<AccountLink> getAccountLinks()
    {
        return accountLinks;
    }

    public void setAccountLinks(List<AccountLink> accountLinks)
    {
        this.accountLinks = accountLinks;
    }

	/**
	 *
	 * @return счета в иностранной валюте (необходимо дл€ печати доп.соглашени€)
	 */
	public List<Account> getForeignAccounts()
	{
		return foreignAccounts;
	}

	/**
	 * 
	 * @param foreignAccounts счета в иностранной валюте
	 */
	public void setForeignAccounts(List<Account> foreignAccounts)
	{
		this.foreignAccounts = foreignAccounts;
	}

	public List<AccountLink> getCurrentAccountLinks()
	{
		return currentAccountLinks;
	}

	public void setCurrentAccountLinks(List<AccountLink> currentAccountLinks)
	{
		this.currentAccountLinks = currentAccountLinks;
	}

	public void setEmpoweredPersons(List<ActivePerson> empoweredPersons)
    {
        this.empoweredPersons = empoweredPersons;
    }

    public List<ActivePerson> getEmpoweredPersons()
    {
        return empoweredPersons;
    }

    public Map<Long, List<AccountLink>> getEmpoweredPersonsAccounts()
    {
        return empoweredPersonsAccounts;
    }

    public void setEmpoweredPersonsAccounts(Map<Long, List<AccountLink>> empoweredPersonsAccounts)
    {
        this.empoweredPersonsAccounts = empoweredPersonsAccounts;
    }

    public Map<Long, List<Service>> getEmpoweredPersonsServices()
    {
        return empoweredPersonsServices;
    }

    public void setEmpoweredPersonsServices(Map<Long, List<Service>> empoweredPersonsServices)
    {
        this.empoweredPersonsServices = empoweredPersonsServices;
    }
    public List<Service> getServices()
    {
        return services;
    }

    public void setServices(List<Service> services)
    {
        this.services = services;
    }
   
	public List<PaymentReceiverBase> getReceivers()
	{
		return receivers;
	}

	public void setReceivers(List<PaymentReceiverBase> receivers)
	{
		this.receivers = receivers;
	}

	public void setCurrentDate(Date currentDate) {
       this.currentDate = currentDate;
    }

    public Date getCurrentDate (){
        return currentDate;
    }

    public Department getDepartment()
    {
        return department;
    }

    public void setDepartment(Department department)
    {
        this.department = department;
    }

    public String getMonthlyCharge()
    {
        return  Translate(department.getMonthlyCharge());
    }

    public String getReconnectionCharge()
    {
        return  Translate(department.getReconnectionCharge());
    }

    public String getConnectionCharge()
    {
        return  Translate(department.getConnectionCharge());
    }

    private String Translate(BigDecimal sum)
    {
        String moneyStr = StringUtils.sumInWords(sum.toString(),"RUB");
        int start = moneyStr.indexOf("руб");
        return moneyStr.substring(0,start);
    }

    public List<CardLink> getCardLinks()
    {
        return cardLinks;
    }

    public void setCardLinks(List<CardLink> cardLinks)
    {
        this.cardLinks = cardLinks;
    }

    public ActivePerson getPrincipalPerson()
    {
        return principalPerson;
    }

    public void setPrincipalPerson(ActivePerson principalPerson)
    {
        this.principalPerson = principalPerson;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

	public String getAddAgreementNumber()
	{
		return addAgreementNumber;
	}

	public void setActiveDocument(PersonDocument activeDocument)
	{
		this.activeDocument = activeDocument;
	}

	public void setAddAgreementNumber(String addAgreementNumber)
	{
		this.addAgreementNumber = addAgreementNumber;
	}
	public PersonDocument getActiveDocument()
	{
		return activeDocument;
	}

	public PersonDocument getActiveNotResidentDocument()
	{
		return activeNotResidentDocument;
	}

	public void setActiveNotResidentDocument(PersonDocument activeNotResidentDocument)
	{
		this.activeNotResidentDocument = activeNotResidentDocument;
	}

	public PersonDocument getMigratoryCard()
	{
		return migratoryCard;
	}

	public void setMigratoryCard(PersonDocument migratoryCard)
	{
		this.migratoryCard = migratoryCard;
	}

	public String getGorodCardNumber()
	{
		return gorodCardNumber;
	}

	public void setGorodCardNumber(String gorodCardNumber)
	{
		this.gorodCardNumber = gorodCardNumber;
	}

	public String getSimpleAuthChoice()
	{
		return simpleAuthChoice;
	}

	public void setSimpleAuthChoice(String simpleAuthChoice)
	{
		this.simpleAuthChoice = simpleAuthChoice;
	}

	public Map<Long, PersonDocument> getActiveEmpoweredsDocument()
	{
		return activeEmpoweredsDocument;
	}

	public void setActiveEmpoweredsDocument(Map<Long, PersonDocument> activeEmpoweredsDocument)
	{
		this.activeEmpoweredsDocument = activeEmpoweredsDocument;
	}

	public Map<Long, PersonDocument> getActiveEmpoweredsNotResidentDocument()
	{
		return activeEmpoweredsNotResidentDocument;
	}

	public void setActiveEmpoweredsNotResidentDocument(Map<Long, PersonDocument> activeEmpoweredsNotResidentDocument)
	{
		this.activeEmpoweredsNotResidentDocument = activeEmpoweredsNotResidentDocument;
	}

	public Map<Long, PersonDocument> getEmpoweredsMigratoryCard()
	{
		return empoweredsMigratoryCard;
	}

	public void setEmpoweredsMigratoryCard(Map<Long, PersonDocument> empoweredsMigratoryCard)
	{
		this.empoweredsMigratoryCard = empoweredsMigratoryCard;
	}

	/**
	 * @return —пособ доставки оповещений
	 */
	public String getMessageService()
	{
		return messageService;
	}

	/**
	 * ”становить способ доставки оповещений
	 * @param messageService- —пособ доставки оповещений
	 */
	public void setMessageService(String messageService)
	{
		this.messageService = messageService;
	}
}
