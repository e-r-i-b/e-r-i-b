package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.DefaultClientIdGenerator;
import com.rssl.phizic.business.clients.list.ClientInformation;
import com.rssl.phizic.business.clients.list.ClientInformationService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.operations.person.search.multinode.SearchPersonMultiNodeOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;

import java.util.*;

/**
 * @author Nady
 * @ created 01.04.2015
 * @ $Author$
 * @ $Revision$
 */
/**
 * Операция для поиска клиента для создания заявки на кредит в АРМ Сотрудника
 */
public class LoanClaimCreateOperation extends SearchPersonMultiNodeOperation
{
	private static final DefaultClientIdGenerator clientIdGenerator = new DefaultClientIdGenerator();
	private static final ClientInformationService service = new ClientInformationService();
	private List<ClientInformation> activePersons = new ArrayList<ClientInformation>();
	private Map<String, Object> formData;

	public void initializePreSearch(Map<String, Object> identityData, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		setUserVisitingMode(userVisitingMode);
		String firstName = ((String) identityData.get("firstName"));
		String patrName = ((String) identityData.get("patrName"));
		String surName = ((String) identityData.get("surName"));

		String fio = surName + " " + firstName+" " + patrName;

		String docSeries = ((String) identityData.get("documentSeries"));
		String docNumber = ((String) identityData.get("documentNumber"));

		String doc = docSeries+docNumber;

		Calendar birthDay = (DateHelper.toCalendar((Date) identityData.get("birthDay")));

		List<String> allowedTb = new ArrayList<String>();
		for (Department department: AllowedDepartmentsUtil.getTerbanksByAllowedDepartments())
		{
			allowedTb.add(department.getRegion());
		}

		activePersons = service.getClientsInformation(fio, doc, birthDay, null, null, null, null, allowedTb, 10,0);
		formData = Collections.unmodifiableMap(identityData);
	}
	public List<ClientInformation> getActivePersons()
	{
		return activePersons;
	}

	public GuestPerson getGuestPerson()
	{
		GuestPerson guestPerson = new GuestPerson();

		guestPerson.setStatus(ActivePerson.ACTIVE);
		guestPerson.setCreationType(CreationType.ANONYMOUS);

		guestPerson.setFirstName((String) formData.get("firstName"));
		guestPerson.setPatrName((String) formData.get("patrName"));
		guestPerson.setSurName((String) formData.get("surName"));

		PersonDocumentImpl document = new PersonDocumentImpl();
		document.setDocumentType(PersonDocumentType.valueOf((String) formData.get("documentType")));
		document.setDocumentSeries((String) formData.get("documentSeries"));
		document.setDocumentNumber((String) formData.get("documentNumber"));
		Set<PersonDocument> documents = new HashSet<PersonDocument>(1);
		documents.add(document);
		guestPerson.setPersonDocuments(documents);

		guestPerson.setBirthDay(DateHelper.toCalendar((Date) formData.get("birthDay")));
		//дефолтный генератор идентификатора клиента
		guestPerson.setClientId(clientIdGenerator.generate(null));
		return guestPerson;
	}

	public Map<String, Object> getFormData()
	{
		return formData;
	}
}
