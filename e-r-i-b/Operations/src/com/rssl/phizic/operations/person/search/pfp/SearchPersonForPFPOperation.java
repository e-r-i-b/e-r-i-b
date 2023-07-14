package com.rssl.phizic.operations.person.search.pfp;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
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
import com.rssl.phizic.operations.person.search.SearchPersonOperationBase;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.*;

/**
 * @author akrenev
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class SearchPersonForPFPOperation extends SearchPersonOperationBase
{
	private static final String DATE_FORMAT = "yyyy-MM-dd";
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

	protected ActivePerson findClient(AuthData authData, String authToken) throws BusinessException, BusinessLogicException
	{
		ActivePerson person = (ActivePerson) LoginHelper.synchronize(authData, authToken, AuthentificationSource.full_version, getUserVisitingMode(), getRestriction());
		if (person != null)
			return person;

		ActivePerson newPerson = new ActivePerson();
		newPerson.setStatus(ActivePerson.ACTIVE);
		newPerson.setCreationType(CreationType.POTENTIAL);

		newPerson.setFirstName(authData.getFirstName());
		newPerson.setPatrName(authData.getMiddleName());
		newPerson.setSurName(authData.getLastName());

		PersonDocumentImpl document = new PersonDocumentImpl();
		document.setDocumentType(PersonDocumentType.valueOf(authData.getDocumentType()));
		document.setDocumentSeries(authData.getSeries());
		document.setDocumentNumber(authData.getDocument());
		Set<PersonDocument> documents = new HashSet<PersonDocument>(1);
		documents.add(document);
		newPerson.setPersonDocuments(documents);

		try
		{
			Calendar birthDay = DateHelper.parseCalendar(authData.getBirthDate(), DATE_FORMAT);
			newPerson.setBirthDay(birthDay);
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}

		if (StringHelper.isEmpty(authData.getCB_CODE()))
			return newPerson;

		Department department = departmentService.findByCode(new ExtendedCodeImpl(authData.getCB_CODE().substring(0, 2), null, null));
		if (department != null)
			newPerson.setDepartmentId(department.getId());
		return newPerson;
	}

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
}
