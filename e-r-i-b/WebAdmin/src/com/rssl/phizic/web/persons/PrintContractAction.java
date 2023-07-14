package com.rssl.phizic.web.persons;

import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ForeignCurrencyAccountFilter;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.operations.person.PrintPersonOperation;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrintContractAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CLOSE = "Close";
	private static final String FORWARD_CONTACT2 = "/persons/printContract2";
	private static final String FORWARD_CONTRACT_PR1 = "/persons/printContract8_pr5";

	protected Map<String,String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        PrintPersonForm frm = (PrintPersonForm) form;

        PrintPersonOperation operation  =  createOperation(PrintPersonOperation.class);
        operation.setPersonId(frm.getPerson());

	    Department department = operation.getDepartment();
        List<ActivePerson> empoweredPersons = operation.getEmpoweredPersons();
        Map<Long, List<AccountLink>> empoweredPersonsAccounts = operation.getEmpoweredPersonsAccounts();
        Map<Long, List<Service>>     empoweredPersonsServices = operation.getEmpoweredPersonsServices();
        List<PaymentReceiverBase> receiversList = operation.getPaymentReceivers();
        //если получателей нет, то приложение 1 к заявлению не печатаем	    
	    if (mapping.getPath().equals(FORWARD_CONTRACT_PR1) && receiversList.isEmpty() )
	    {
	        return mapping.findForward(FORWARD_CLOSE);
	    }
	    Date currentDate = new Date();
        frm.setActivePerson(operation.getPerson());
	    Set<PersonDocument> personDocuments = operation.getPerson().getPersonDocuments();
	    if (personDocuments != null)
	    {
		    addPersonDocuments(frm, personDocuments, operation.getPerson().getIsResident());
	    }

	    AccessPolicyService accessPolicy = new AccessPolicyService();

	    Properties acceessProperties = accessPolicy.getProperties(frm.getActivePerson().getLogin(), AccessType.simple);
		if (acceessProperties == null || acceessProperties.keySet().size() == 0)
		{
			// права не установлены, но они не должны мешать печати... странно...
			frm.setSimpleAuthChoice(null);
		}
		else
		{
			frm.setSimpleAuthChoice(acceessProperties.getProperty("simple-auth-choice"));
		}

	    List<AccountLink> accountLinks; 
	    if (mapping.getPath().equals(FORWARD_CONTACT2))
	    {
		    accountLinks = operation.getDopAgreementAccountLinks();
			if (accountLinks.isEmpty())
			{
				return mapping.findForward(FORWARD_CLOSE);
			}
	    }
	    else
	    {
		    accountLinks = operation.getAccountLinks();
	    }
	    frm.setAccountLinks(accountLinks);
	    setForeignAccounts(frm, accountLinks);
	    setCurrentAccountLinks(frm, accountLinks);
        frm.setEmpoweredPersons(empoweredPersons);
        frm.setEmpoweredPersonsAccounts(empoweredPersonsAccounts);
        frm.setEmpoweredPersonsServices(empoweredPersonsServices);

	    for(ActivePerson empoweredPerson: empoweredPersons)
	    {
		   Set<PersonDocument> empoweredPersonDocuments = empoweredPerson.getPersonDocuments();
		    if (personDocuments != null)
	        {
		        addEmpoweredPersonDocuments(frm, empoweredPerson.getId(), empoweredPersonDocuments, empoweredPerson.getIsResident());
	        }
	    }	    

        frm.setCurrentDate(currentDate);
	    frm.setReceivers(receiversList);
        frm.setDepartment(department);
	    frm.setGorodCardNumber(operation.getGorodCardNumber());        
	    if (mapping.getParameter()!= null && mapping.getParameter().equals("dopAgreement"))
	        frm.setAddAgreementNumber(operation.getDopAgreementNumber());
        frm.setCardLinks(operation.getCardLinks());
        PINEnvelope pin = operation.getCurrentLinkedPINEnvelope();
        if (pin != null)
          frm.setPin(pin.getUserId());
        else
          frm.setPin("");

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getPerson()));

        return mapping.findForward(FORWARD_START);
    }

	private void setForeignAccounts(PrintPersonForm frm, List<AccountLink> accountLinks) throws Exception
	{
		List<Account> accounts = new ArrayList<Account>();
		AccountFilter filter = new ForeignCurrencyAccountFilter();
		for (AccountLink accountLink : accountLinks)
		{
			Account account = accountLink.getValue();
			if(!MockHelper.isMockObject(account))
			{
				if (filter.accept(accountLink))
					accounts.add(account);
			}

		}
		frm.setForeignAccounts(accounts);
	}

	private void  setCurrentAccountLinks(PrintPersonForm frm, List<AccountLink> accountLinks)
	{
		if (accountLinks.isEmpty())
			return;
		List<AccountLink> temp = new ArrayList<AccountLink>();
		for (AccountLink accountLink: accountLinks)
		{
			if (accountLink.getNumber().startsWith("40817") || accountLink.getNumber().startsWith("40820"))
				temp.add(accountLink);
		}
		frm.setCurrentAccountLinks(temp);
	}

	private void addPersonDocuments(PrintPersonForm frm,Set<PersonDocument> personDocuments, boolean isResident)
	{
		for(PersonDocument document : personDocuments)
		{
			if (!isResident && document.getDocumentType().equals(PersonDocumentType.MIGRATORY_CARD))
			{
				frm.setMigratoryCard(document);
				continue;
			}

		    if (document.getDocumentMain() && document.isDocumentIdentify())
		    {
		       frm.setActiveDocument(document);
			    continue;
			}
			if (!isResident && !document.isDocumentIdentify() && document.getDocumentMain())
			{
			    frm.setActiveNotResidentDocument(document);
			}
		}
	}

	private void addEmpoweredPersonDocuments(PrintPersonForm frm, Long personId, Set<PersonDocument> personDocuments, boolean isResident)
	{
		Map empoweredDocuments = new HashMap();
		for(PersonDocument document : personDocuments)
		{
			if (!isResident && document.getDocumentType().equals(PersonDocumentType.MIGRATORY_CARD))
			{
				empoweredDocuments.put(personId, document);
				frm.setEmpoweredsMigratoryCard(empoweredDocuments);
				continue;
			}

		    if (document.getDocumentMain() && document.isDocumentIdentify())
		    {
			   empoweredDocuments.put(personId, document);
		       frm.setActiveEmpoweredsDocument(empoweredDocuments);
			    continue;
			}
			if (!isResident && !document.isDocumentIdentify() && document.getDocumentMain())
			{
				empoweredDocuments.put(personId, document);
			    frm.setActiveEmpoweredsNotResidentDocument(empoweredDocuments);
			}
		}
	}
}
