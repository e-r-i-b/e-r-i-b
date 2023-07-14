package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;

import java.util.*;

/**
 @author: Egorovaa
 @ created: 31.05.2012
 @ $Author$
 @ $Revision$
 */

/**
 *  �������� �������������� ����� ��� ��������� �������� ������ ����-�������
 */
public class AccountsOwnerValidator extends MultiFieldsValidatorBase
{
	private static final String CLIENT_ID = "clientId";
	private static final String BIRTH_DAY = "birthDay";
	private static final String SUR_NAME = "surName";
	private static final String FIRST_NAME = "firstName";
	private static final String PATR_NAME = "patrName";
	private static final String DOCUMENT_TYPE = "documentType";
	private static final String DOCUMENT_NUMBER = "documentNumber";
	private static final String DOCUMENT_SERIES = "documentSeries";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PersonService personService = new PersonService();
	private static final ExternalResourceService externalResourceService= new ExternalResourceService();
    private static final DepartmentService departmentService = new DepartmentService();

	public static final String CLIENT_NOT_OWNER_MESSAGE = "� ������ ������ ������� ���� �� ������������� ��� " +
			"�����. ����������, ��������� ������ ������ � ������� �����, �� ������������� ����� �������.";
	public static final String ERROR_MESSAGE = "�� ������ ������ ���������� ��������� �������������� ������ " +
			"�������. ����������, ��������� ������� �����.";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			ActivePerson person = personService.findByClientId(String.valueOf(values.get(CLIENT_ID)));
			if (checkChangedFields(values, person))
				if (!checkAccountsOwner(person))
				{
					setMessage(CLIENT_NOT_OWNER_MESSAGE);
					return false;
				}

		}
		catch (SystemException e)
		{
			setMessage(ERROR_MESSAGE);
			log.error("������ ��� ��������� ������� �������", e);
			return false;
		}
		catch (LogicException e)
		{
			throw new TemporalDocumentException(e);
		}

		return true;
	}

	/**
	 * ���������, ���������� �� ������������ ��� ����. ���������� ���� ��������� � person.
	 * @param fields - ���� �����
	 * @param person - ������
	 * @return true, ���� ���������� ���� �� ���� ����, ����� false
	 */
	private Boolean checkChangedFields(Map<String, Object> fields, ActivePerson person)
	{
		boolean isChangedAccountsOwner = false; // ������� ����, ���� �� ���������� ����

		// ���� ������� �� ������, �� ������ ��������� ���, �� ������� �������, � �� �����������
		if (person == null)
			return false;
		
		if (person.getBirthDay() != null)
		{
			if (!fields.get(BIRTH_DAY).equals(person.getBirthDay().getTime()))
			{
				person.setBirthDay(DateHelper.toCalendar((Date) fields.get(BIRTH_DAY)));
				isChangedAccountsOwner = true;
			}
		}
		if (!StringHelper.equalsNullIgnore((String) fields.get(SUR_NAME), person.getSurName()))
		{
			person.setSurName((String) fields.get(SUR_NAME));
			isChangedAccountsOwner = true;
		}
		if (!StringHelper.equalsNullIgnore((String) fields.get(FIRST_NAME), person.getFirstName()))
		{
			person.setFirstName((String) fields.get(FIRST_NAME));
			isChangedAccountsOwner = true;
		}
		if (!StringHelper.equalsNullIgnore((String) fields.get(PATR_NAME), person.getPatrName()))
		{
			person.setPatrName((String) fields.get(PATR_NAME));
			isChangedAccountsOwner = true;
		}
		boolean isNewDocument = true;    // ��� �� ������� ����� �������� � �������� ���
		Set<PersonDocument> personDocuments = person.getPersonDocuments();
		for (PersonDocument currentDocument : personDocuments)
		{
			// ���� ��������� � ����� �������� ��� �c�� � �������, �� ��������� ��� ����, ��������� ����������
			if (currentDocument.getDocumentType() == PersonDocumentType.valueOf(String.valueOf(fields.get(DOCUMENT_TYPE))))
			{
				isNewDocument = false;
				if (!StringHelper.equalsNullIgnore((String) fields.get(DOCUMENT_NUMBER), currentDocument.getDocumentNumber()))
				{
					currentDocument.setDocumentNumber((String) fields.get(DOCUMENT_NUMBER));
					isChangedAccountsOwner = true;
				}
				if (!StringHelper.equalsNullIgnore((String) fields.get(DOCUMENT_SERIES), currentDocument.getDocumentSeries()))
				{
					currentDocument.setDocumentSeries((String) fields.get(DOCUMENT_SERIES));
					isChangedAccountsOwner = true;
				}
				break;
			}
		}
		//���� ��� ������� ����� ��������, �� ���� ��� �������� � ������ ������������
		if (isNewDocument)
		{
			PersonDocument newDocument = new PersonDocumentImpl();
			newDocument.setDocumentType(PersonDocumentType.valueOf((String) fields.get(DOCUMENT_TYPE)));
			newDocument.setDocumentMain(true);
			newDocument.setDocumentNumber((String) fields.get(DOCUMENT_NUMBER));
			newDocument.setDocumentSeries((String) fields.get(DOCUMENT_SERIES));
			personDocuments.add(newDocument);
			isChangedAccountsOwner = true;
		}
		
		return isChangedAccountsOwner;
	}

	/**
	 * ��������� �������������� ������� ������������ ������
	 * @param person - ������
	 * @return true, ���� ����� ��������� � ������� �������
	 * @throws SystemException
	 * @throws LogicException
	 */
	private Boolean checkAccountsOwner(ActivePerson person) throws SystemException, LogicException
	{
		ClientProductsService productService = GateSingleton.getFactory().service(ClientProductsService.class);
		List<AccountLink> accountLinks = externalResourceService.getLinks(person.getLogin(), AccountLink.class);
		if (accountLinks.isEmpty())
				return true;
		// ���� �������������� ��, �� ���������� ������. ����� ���������� ������ ������� � ������
		Department department = departmentService.findById(person.getDepartmentId());
		if (((ExtendedDepartment) department).isEsbSupported())
		{
			GroupResult<Class, List<Pair<Object, AdditionalProductData>>> products = productService.getClientProducts(person.asClient(), Account.class);
			List<String> accountNubmers = new ArrayList<String>();
			for (Pair<Object, AdditionalProductData> pair : GroupResultHelper.getResult(products, Account.class))
			{
				Account account = (Account) pair.getFirst();
				accountNubmers.add(account.getNumber());
			}
			for (AccountLink accountLink : accountLinks)
			{
				if (!accountNubmers.contains(accountLink.getNumber()))
					return false;
			}
		}
		// ����� ���������� ������ ���������(����������) ������ � ��������� �� ����� ������
		else
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			for (AccountLink accountLink : accountLinks)
			{
				Client accountOwner = null;
				try
				{
					Account account = GroupResultHelper.getOneResult(bankrollService.getAccount(accountLink.getExternalId()));
					accountOwner = GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(account));
				}
				catch (LogicException e)
				{
					String message = "������ ��� ��������� ���������� � ��������� ����� � ���������������" + (accountLink.getNumber());
					log.error(message, e);
					return false;
				}
				// ���� �� ����� ������� ��������, �� ���������
				if (accountOwner != null)
				{
					if (!checkAccountOwner(person, accountOwner))
						return false;
				}
				// ���� �������� �� �������, �� ���� �� ����������� ������������ �������
				else
					return false;
			}
		}
		return true;
	}

	/**
	 * ���������� ���, ���� �������� � ��� ����������� ������� � ��������� �����
	 * @param person - ������ � ������������ �� ����� ������
	 * @param accountOwner  - �������� �����, ���������� �� �����
	 * @return true, ���� ������ ������� � ��������� ����� ���������
	 * @throws BusinessException
	 */
	private boolean checkAccountOwner(ActivePerson person, Client accountOwner) throws BusinessException
	{
		if (DateHelper.nullSafeCompare(accountOwner.getBirthDay(), person.getBirthDay()) != 0)
			return false;

		// ��������� ������ ���, �.�. �� ����� ������� ��� � �������� ���������� ��������
		String personFullName = StringHelper.getEmptyIfNull(person.getFullName()).replace(" ", "");
		String clientFullName = StringHelper.getEmptyIfNull(accountOwner.getFullName()).replace(" ", "");
		if (!personFullName.equalsIgnoreCase(clientFullName))
			return false;

		// ������� ��������, �������������� � �����
		PersonDocument mainDocument = new PersonDocumentImpl();
		for (PersonDocument currentDocument : person.getPersonDocuments())
		{
			if (currentDocument.getDocumentMain())
			{
				mainDocument = currentDocument;
				break;
			}
		}
		// ���������� �������� �� ����� �� ����� � ���������� �������
		for (ClientDocument clientDocument : accountOwner.getDocuments())
		{
			if (mainDocument.getDocumentType() == PersonDocumentType.valueOf(clientDocument.getDocumentType()))
			{
				String clientDocumentParams = joinDocumentParams(clientDocument.getDocNumber(), clientDocument.getDocSeries());
				String mainDocumentParams = joinDocumentParams(mainDocument.getDocumentNumber(), mainDocument.getDocumentSeries());
				if (!clientDocumentParams.equals(mainDocumentParams))
					return false;
			}
		}
		return true;
	}

	/**
	 * ��������� ����� � ����� ��������� � ���� ������, �.�. � ��������� �������� ��� ����� ��������� � ����� ����
	 * @param docNumber - ����� ���������
	 * @param docSeries - ����� ���������
	 * @return ������ ���� [����������]
	 */
	private String joinDocumentParams (String docNumber, String docSeries)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(StringHelper.getEmptyIfNull(docSeries));
		buf.append(StringHelper.getEmptyIfNull(docNumber));   
		return buf.toString().replace(" ", "").replace("-","");
	}
}
