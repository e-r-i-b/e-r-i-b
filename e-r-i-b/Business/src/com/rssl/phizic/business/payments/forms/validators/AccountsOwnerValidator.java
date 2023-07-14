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
 *  Проверка принадлежности счета при изменении анкетных данных СБОЛ-клиента
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

	public static final String CLIENT_NOT_OWNER_MESSAGE = "В списке счетов клиента есть не принадлежащие ему " +
			"счета. Пожалуйста, проверьте список счетов и удалите счета, не принадлежащие этому клиенту.";
	public static final String ERROR_MESSAGE = "На данный момент невозможно проверить принадлежность счетов " +
			"клиента. Пожалуйста, повторите попытку позже.";

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
			log.error("Ошибка при получении вкладов клиента", e);
			return false;
		}
		catch (LogicException e)
		{
			throw new TemporalDocumentException(e);
		}

		return true;
	}

	/**
	 * Проверяем, изменились ли интересующие нас поля. Измененные поля обновляем в person.
	 * @param fields - поля формы
	 * @param person - клиент
	 * @return true, если изменилось хотя бы одно поле, иначе false
	 */
	private Boolean checkChangedFields(Map<String, Object> fields, ActivePerson person)
	{
		boolean isChangedAccountsOwner = false; // признак того, есть ли измененные поля

		// если персона не пришла, то значит изменений нет, мы создаем персону, а не редактируем
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
		boolean isNewDocument = true;    // был ли заведен новый документ в качестве ДУЛ
		Set<PersonDocument> personDocuments = person.getPersonDocuments();
		for (PersonDocument currentDocument : personDocuments)
		{
			// если указанный в форме документ уже еcть у клиента, то проверяем его поля, сохраняем измененные
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
		//если был заведен новый документ, то надо его добавить к списку существующих
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
	 * Проверяем принадлежность клиенту существующих линков
	 * @param person - клиент
	 * @return true, если линки относятся к вкладам клиента
	 * @throws SystemException
	 * @throws LogicException
	 */
	private Boolean checkAccountsOwner(ActivePerson person) throws SystemException, LogicException
	{
		ClientProductsService productService = GateSingleton.getFactory().service(ClientProductsService.class);
		List<AccountLink> accountLinks = externalResourceService.getLinks(person.getLogin(), AccountLink.class);
		if (accountLinks.isEmpty())
				return true;
		// если поддерживается БП, то отправляем запрос. Будем сравнивать номера вкладов и линков
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
		// будем сравнивать данные владельца(владельцев) линков и пришедние из формы данные
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
					String message = "Ошибка при получении информации о владельце счета с идентификатором" + (accountLink.getNumber());
					log.error(message, e);
					return false;
				}
				// если по линку получен владелец, то проверяем
				if (accountOwner != null)
				{
					if (!checkAccountOwner(person, accountOwner))
						return false;
				}
				// если владелец не получен, то линк не принадлежит проверяемому клиенту
				else
					return false;
			}
		}
		return true;
	}

	/**
	 * Сравниваем ФИО, дату рождения и ДУЛ изменяемого клиента и владельца счета
	 * @param person - клиент с обновленными из формы полями
	 * @param accountOwner  - владелец счета, полученный по линку
	 * @return true, если данные клиента и владельца счета совпадают
	 * @throws BusinessException
	 */
	private boolean checkAccountOwner(ActivePerson person, Client accountOwner) throws BusinessException
	{
		if (DateHelper.nullSafeCompare(accountOwner.getBirthDay(), person.getBirthDay()) != 0)
			return false;

		// Проверяем полное имя, т.к. не везде фамилия имя и отчество запоняются отдельно
		String personFullName = StringHelper.getEmptyIfNull(person.getFullName()).replace(" ", "");
		String clientFullName = StringHelper.getEmptyIfNull(accountOwner.getFullName()).replace(" ", "");
		if (!personFullName.equalsIgnoreCase(clientFullName))
			return false;

		// Находим документ, отображающийся в форме
		PersonDocument mainDocument = new PersonDocumentImpl();
		for (PersonDocument currentDocument : person.getPersonDocuments())
		{
			if (currentDocument.getDocumentMain())
			{
				mainDocument = currentDocument;
				break;
			}
		}
		// Сравниваем документ из полей на форме с документом клиента
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
	 * Соединяем серию и номер документа в одну строку, т.к. в некоторых системах они могут храниться в одном поле
	 * @param docNumber - номер документа
	 * @param docSeries - серия документа
	 * @return строка вида [СерияНомер]
	 */
	private String joinDocumentParams (String docNumber, String docSeries)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(StringHelper.getEmptyIfNull(docSeries));
		buf.append(StringHelper.getEmptyIfNull(docNumber));   
		return buf.toString().replace(" ", "").replace("-","");
	}
}
