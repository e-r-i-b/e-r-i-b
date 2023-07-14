package com.rssl.phizic.operations.person.search.multinode;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankProductTypeWrapper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author mihaylov
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Поиск клиента для ПФП в многоблочном режиме
 */
public class SearchPersonForPfpMultiNodeOperation extends SearchPersonMultiNodeOperation
{

	private static final Map<Class, BankProductType> RESOURCES = getResources();

	private static Map<Class, BankProductType> getResources()
	{
		Map<Class, BankProductType> resources = new HashMap<Class, BankProductType>();
		resources.put(Account.class,    BankProductTypeWrapper.getBankProductType(Account.class));
		resources.put(Card.class,       BankProductTypeWrapper.getBankProductType(Card.class));
		resources.put(IMAccount.class,  BankProductTypeWrapper.getBankProductType(IMAccount.class));
		return resources;
	}

	@Override
	public ActivePerson getEntity() throws BusinessException, BusinessLogicException
	{
		ActivePerson person = getPerson();
		if (person != null)
			return person;

		ActivePerson newPerson = new ActivePerson();
		Map<String,Object> identityData = getIdentityData();

		newPerson.setStatus(ActivePerson.ACTIVE);
		newPerson.setCreationType(CreationType.POTENTIAL);

		newPerson.setFirstName((String) identityData.get("firstName"));
		newPerson.setPatrName((String) identityData.get("patrName"));
		newPerson.setSurName((String) identityData.get("surName"));

		PersonDocumentImpl document = new PersonDocumentImpl();
		document.setDocumentType(PersonDocumentType.valueOf((String)identityData.get("documentType")));
		document.setDocumentSeries((String) identityData.get("documentSeries"));
		document.setDocumentNumber((String)identityData.get("documentNumber"));
		Set<PersonDocument> documents = new HashSet<PersonDocument>(1);
		documents.add(document);
		newPerson.setPersonDocuments(documents);

		newPerson.setBirthDay(DateHelper.toCalendar((Date) identityData.get("birthDay")));

		if (StringHelper.isEmpty((String)identityData.get("region")))
			return newPerson;

		Department department = departmentService.findByCode(new ExtendedCodeImpl((String)identityData.get("region"), null, null));
		if (department != null)
			newPerson.setDepartmentId(department.getId());
		return newPerson;
	}

	@Override
	protected void updatePerson(ActivePerson person, Map<String, Object> identityData, AuthData authData) throws BusinessException
	{
		if (CreationType.POTENTIAL != person.getCreationType())
		{
			Login login = person.getLogin();
			if (StringHelper.isEmpty(login.getLastLogonCardDepartment()))
				LoginHelper.updateClientLogonData(login, authData);

			//подтягиваем продукты клиента
			loadProducts(person);
		}
	}


	@Override
	protected List<Class> getResourcesClasses(ActivePerson person) throws BusinessException
	{
		List<Class> resources = new ArrayList<Class>();

		for (Map.Entry<Class, BankProductType> entry : RESOURCES.entrySet())
		{
			try
			{
				if (ExternalSystemHelper.isActive(getExternalSystem(person, entry.getValue())))
					resources.add(entry.getKey());
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
		}

		return resources;
	}

}
