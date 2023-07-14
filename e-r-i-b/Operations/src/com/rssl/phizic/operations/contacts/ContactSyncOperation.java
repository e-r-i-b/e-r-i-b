package com.rssl.phizic.operations.contacts;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizgate.common.profile.MBKCastService;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.addressBook.reports.ContactsCountReportEntity;
import com.rssl.phizic.business.addressBook.reports.ContactsCountReportEntityService;
import com.rssl.phizic.business.addressBook.reports.RequestsCountLogEntity;
import com.rssl.phizic.business.addressBook.reports.RequestsCountLogEntityService;
import com.rssl.phizic.business.csa.IncognitoService;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhone;
import com.rssl.phizic.business.incognitoDictionary.IncognitoPhonesService;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.profile.addressbook.AddedType;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.business.profile.smartaddressbook.MBKPhoneLoaderCallBack;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.config.addressbook.PeriodType;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;

/**
 * Синхронизация мобильных контактов
 * @author Dorzhinov
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ContactSyncOperation extends OperationBase
{
	private static final String XSD = "com/rssl/phizic/business/contacts/contacts.xsd";
	private static final MBKCastService mbkCastService = new MBKCastService(new MBKPhoneLoaderCallBack());
	private static final AddressBookService contactService = new AddressBookService();
	private static final IncognitoPhonesService incognitoPhonesService = new IncognitoPhonesService();
	private static final RequestsCountLogEntityService requestsCountLogEntityService = new RequestsCountLogEntityService();
	private static final ContactsCountReportEntityService contactsCountReportEntityService = new ContactsCountReportEntityService();
	public static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	private Map<String, String> nameByPhoneMap = new HashMap<String, String>(); //мапа <номер телефона, имя контакта> (используется при обработке ответа от МБ для определения имени по номеру)
	private boolean isLimitExceeded = false;

	/**
	 * Синхронизация
	 * @param contactsXml xml-строка с контактами
	 * @return мапа с именами пользователей и номерами телефонов, подключенных к мобильному банку
	 */
	public ContactSyncOperationResult synchronize(final String contactsXml, final boolean showSbAttribute) throws BusinessException, BusinessLogicException
	{
		final Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
		final ContactsCountReportEntity contactsCountReportEntity = getContactsCountReportEntity();
		contactsCountReportEntityService.createIfNotFound(contactsCountReportEntity);

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ContactSyncOperationResult>()
		    {
		        public ContactSyncOperationResult run(Session session) throws Exception
		        {
			        session.lock(contactsCountReportEntity, LockMode.UPGRADE);

			        //Список текущих номеров телефонов.
			        Map<String, Contact> currentContacts = getContactFromAddressBook(loginId);
			        //Список контактов для синхронизации.
			        Map<String, Contact> phonesContact = parsePhones(contactsXml);
			        //Номера телефон, которые еще не попали в адресную книгу.
			        Set<String> newContacts = getNewContacts(currentContacts, phonesContact);
			        //Учитываем лимиты на загрузку.
			        Set<String> contactWithLimit = getLimitFiltered(newContacts, loginId);
			        //Добавление новых контактов в адресную книгу
			        addNewContacts(currentContacts, phonesContact, contactWithLimit);
			        //Формируем ответ.
			        Collection<String> phones[] = generateAnswer(currentContacts, phonesContact, newContacts, contactWithLimit, showSbAttribute);

			        ContactSynchronizationCountChecker.check(PersonContext.getPersonDataProvider().getPersonData());
			        contactsCountReportEntity.setCount(Long.valueOf(currentContacts.size()));
			        session.update(contactsCountReportEntity);

			       return new ContactSyncOperationResult(convertToMapWithName(phones), getSyncSizeLimit(loginId));
				}
			});
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	private Collection<String>[] generateAnswer(Map<String, Contact> currentContacts, Map<String, Contact> phonesContact, Set<String> newContacts, Set<String> contactWithLimit, boolean showSbAttribute) throws BusinessException, BusinessLogicException
	{
		List<Contact> sberClient = new LinkedList<Contact>();
        List<Contact> notsberClient = new LinkedList<Contact>();
        List<Contact> incognito = new LinkedList<Contact>();
        List<Contact> unlimited = new LinkedList<Contact>();
		String csaSID = AuthenticationContext.getContext().getCSA_SID();
		boolean incognitoSetting = IncognitoService.getIncognitoSettings(csaSID);
        for (Contact contact : currentContacts.values())
        {
	        if (showSbAttribute && !incognitoSetting && contact.isSberbankClient())
		        sberClient.add(contact);
	        else
		        notsberClient.add(contact);

	        if (contact.isIncognito())
		        incognito.add(contact);
        }

        for (String ph : newContacts)
        {
	        if (!contactWithLimit.contains(ph))
		        unlimited.add(phonesContact.get(ph));
        }

		//noinspection unchecked
		return new Collection[]{
		        convertToPhoneCollection(sberClient),
		        convertToPhoneCollection(notsberClient),
		        convertToPhoneCollection(incognito),
		        convertToPhoneCollection(unlimited)
        };
	}

	private void addNewContacts(Map<String, Contact> currentContacts, Map<String, Contact> phoneContacts, Set<String> contactWithLimit) throws BusinessException, GateException
	{
		if (contactWithLimit.isEmpty())
			return;

		List<IncognitoPhone> incognitoPhones = incognitoPhonesService.findPhones(contactWithLimit);
		List<Contact> contactsForAdd = new LinkedList<Contact>();

		//Выставляем признак "инкогнито"
		for (IncognitoPhone phone : incognitoPhones)
		{
			if (phoneContacts.containsKey(phone.getPhoneNumber()))
				phoneContacts.get(phone.getPhoneNumber()).setIncognito(true);
		}

		Map<String, Contact> phonesForTest = new HashMap<String, Contact>();
		for (Contact contact : phoneContacts.values())
		{
			if (currentContacts.containsKey(contact.getPhone()))
			{
				Contact cnt = currentContacts.get(contact.getPhone());
				if (cnt.getAddedBy() == AddedType.MOBILE)
					continue;

				cnt.setFullName(contact.getFullName());
				cnt.setAddedBy(AddedType.MOBILE);
				cnt.setStatus(ContactStatus.ACTIVE);
				contactsForAdd.add(cnt);
			}
			else if (contactWithLimit.contains(contact.getPhone()))
			{
				//Признак клиент Сбербанка.
				if (mbkCastService.isSberbankClient(contact.getPhone()))
					contact.setSberbankClient(true);
				else if (ConfigFactory.getConfig(AddressBookConfig.class).isSberClientAllowERBMSync())
				{
					phonesForTest.put(contact.getPhone(), contact);
				}
				contact.setStatus(ContactStatus.ACTIVE);
				contactsForAdd.add(contact);
				currentContacts.put(contact.getPhone(), contact);
			}
		}

		//обновляем признак Клиент Сбербанка из ЕРМБ.
		if (!phonesForTest.isEmpty())
		{
			try
			{
				List<String> phones = CSABackRequestHelper.getRegisteredPhones(phonesForTest.keySet());
				for (String phone : phones)
				{
					phonesForTest.get(phone).setSberbankClient(true);
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
		}

		updateEribContacts(contactsForAdd);
	}

	private Set<String> getLimitFiltered(Set<String> newContacts, long loginId) throws BusinessException
	{
        //Максимальное количество записей для синхронизации.
		Long[] limits = getSyncSizeLimit(loginId);
        Long syncSizeLimit = limits[0] > 0 ? limits[0] : Math.min(limits[1], limits[2]);

		Set<String> res = new HashSet<String>();
		if (syncSizeLimit == 0)
		{
			isLimitExceeded = true;
		}
		else
		{
			for (String s : newContacts)
			{
				if (res.size() < syncSizeLimit)
					res.add(s);
				else
				{
					isLimitExceeded = true;
					break;
				}
			}
		}

		return res;
	}

	private Set<String> getNewContacts(Map<String, Contact> currentContacts, Map<String, Contact> phonesContact)
	{
		Set<String> phones = new HashSet<String>(phonesContact.keySet());
		for (String ph : currentContacts.keySet())
			phones.remove(ph);

		return phones;
	}

	/**
	 * Загружаем текущую адресную книгу клиента.
	 *
	 * @param loginId идентификаторв логина.
	 * @return текущая адресная книга.
	 * @throws BusinessException
	 */
	private Map<String, Contact> getContactFromAddressBook(Long loginId) throws BusinessException
	{
		List<Contact> eribContacts = contactService.getAllClientContacts(loginId);

		Map<String, Contact> contacts = new HashMap<String, Contact>();
		for (Contact contact : eribContacts)
			contacts.put(contact.getPhone(), contact);

		return contacts;
	}

	/**
	 * @return превышен ли лимит на синхронизацию телефонов
	 */
	public boolean isLimitExceeded()
	{
		return isLimitExceeded;
	}

	private Map<String, Contact> parsePhones(String contactsXml) throws BusinessException
	{
		try
		{
			Document document = validate(contactsXml);
			Map<String, Contact> contacts = new HashMap<String, Contact>();
			NodeList contactNodes = XmlHelper.selectNodeList(document.getDocumentElement(), "contact");
			for (int i = 0; i < contactNodes.getLength(); i++)
			{
				Element contact = (Element) contactNodes.item(i);
				String name = XmlHelper.getSimpleElementValue(contact, "name").trim();
				NodeList phoneNodes = XmlHelper.selectNodeList(contact, "phone");
				for (int j = 0; j < phoneNodes.getLength(); j++)
				{
					Element phoneElement = (Element) phoneNodes.item(j);
					String phone = XmlHelper.getElementText(phoneElement).trim();
					Contact phoneContact = new Contact();
					phoneContact.setPhone(phone);
					phoneContact.setFullName(name);
					phoneContact.setAddedBy(AddedType.MOBILE);
					contacts.put(phone, phoneContact);
				}
			}

			return contacts;
		}
		catch (ValidateException ex)
		{
			throw new BusinessException("Пришедшая xml-строка с контактами имеет некорректный формат", ex);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	private Map<String,Set<String>>[] convertToMapWithName(Collection<String>[] phoneses)
	{
		Map<String, Set<String>> resultContactMap[] = new HashMap[phoneses.length];
		int i = 0;
		for (Collection<String> phones : phoneses)
		{
			resultContactMap[i] = new HashMap<String, Set<String>>();
			for (String phone : phones)
			{
				if (StringHelper.isEmpty(phone))
					continue;
				String name = nameByPhoneMap.get(phone);
				Set<String> phoneSet = resultContactMap[i].get(name);
				if (phoneSet == null)
				{
					phoneSet = new HashSet<String>();
					resultContactMap[i].put(name, phoneSet);
				}
				phoneSet.add(phone);
			}
			i++;
		}
		return resultContactMap;
	}

	private Collection<String> convertToPhoneCollection(Collection<Contact> contactCollection)
	{
		List<String> phoneList = new ArrayList<String>();
		for(Contact contact : contactCollection)
		{
			phoneList.add(contact.getPhone());
			nameByPhoneMap.put(contact.getPhone(),contact.getFullName());
		}
		return phoneList;
	}

	/**
	 * Получение информации по ставщимся лимитам.
	 *
	 * @param loginId идентификатор логина.
	 * @return информация по всем лимитам на синхронизацию.
	 * @throws BusinessException
	 */
	private Long[] getSyncSizeLimit(Long loginId) throws BusinessException
	{
		Long[] limits = new Long[3];
		MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
		UserPropertiesConfig userPropertiesConfig = ConfigFactory.getConfig(UserPropertiesConfig.class);
		Calendar firstContactSynchronizationDate = userPropertiesConfig.getFirstContactSynchronizationDate();
		if(firstContactSynchronizationDate == null)
		{
			//При первой загрузке действует особый лимит.
			limits[0] = Math.max(0, Long.valueOf(mobileApiConfig.getFirstContactSyncLimit()));
			firstContactSynchronizationDate = PeriodType.MONTH.getPrevious();
		}
		else
			limits[0] = 0L;

		Long countPerDay = requestsCountLogEntityService.getContactCountByPeriodForLogin(loginId, PeriodType.DAY.getPrevious());
		limits[1] = Math.max(0, mobileApiConfig.getContactSyncPerDayLimit() - countPerDay);

		Long countPerWeek = requestsCountLogEntityService.getContactCountByPeriodForLogin(loginId, DateHelper.maxOfDates(PeriodType.WEEK.getPrevious(),firstContactSynchronizationDate));
		limits[2] = Math.max(0, mobileApiConfig.getContactSyncPerWeekLimit() - countPerWeek);

		return limits;
	}

	/**
	 * Валидирует контакты по XSD-схеме и возвращает DOM
	 * @param contactsXml - строка с XML-описанием контактов
	 * @return DOM-документ с контактами
	 * @throws BusinessException
	 */
	private Document validate(String contactsXml) throws BusinessException, ValidateException
	{
		if (StringHelper.isEmpty(contactsXml))
			throw new IllegalArgumentException("Аргумент 'contactsXml' не может быть пустым");

		Schema schema;
		try
		{
			schema = XmlHelper.schemaByResourceName(XSD);
		}
		catch (SAXException ex)
		{
			throw new BusinessException("Не удалось загрузить XSD-схему " + XSD, ex);
		}

		try
		{

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setSchema(schema);
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			//защита от XXE
			documentBuilder.setEntityResolver(new EntityResolver()
			{
				public InputSource resolveEntity(String publicId, String systemId)
				{
					return new InputSource(new StringReader(""));
				}
			});

			return documentBuilder.parse(new InputSource(new StringReader((contactsXml))));
		}
		catch (SAXParseException ex)
		{
			// сбой на разборе/валидации xml
			throw new ValidateException(ExceptionUtil.formatExceptionString(ex), ex);
		}
		catch (SAXException ex)
		{
			// сбой на разборе/валидации xml
			throw new ValidateException(ex);
		}
		catch (ParserConfigurationException ex)
		{
			throw new BusinessException(ex);
		}
		catch (IOException ex)
		{
			throw new BusinessException(ex);
		}
	}

	private void updateEribContacts(Collection<Contact> addContacts) throws BusinessException
	{
		for (Contact contact : addContacts) {
			contact.setOwner(PersonContext.getPersonDataProvider().getPersonData().getLogin());
			contactService.addOrUpdateContact(contact);
		}

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();
		PersonDocument document = getPersonDocument(person);

		RequestsCountLogEntity requestsCountLogEntity = new RequestsCountLogEntity();
		requestsCountLogEntity.setSynchronizationDate(Calendar.getInstance().getTime());
		requestsCountLogEntity.setCount(Long.valueOf(addContacts.size()));
		requestsCountLogEntity.setLogin(person.getLogin().getId());
		requestsCountLogEntity.setName(person.getFullName());
		requestsCountLogEntity.setDocument(document.getDocumentSeries() + document.getDocumentNumber());
		requestsCountLogEntity.setBirthday(person.getBirthDay());
		requestsCountLogEntity.setTb(personData.getDepartment().getRegion());
		requestsCountLogEntity = requestsCountLogEntityService.add(requestsCountLogEntity);

		UserPropertiesConfig userPropertiesConfig = ConfigFactory.getConfig(UserPropertiesConfig.class);
		userPropertiesConfig.setFirstContactSynchronizationDateIfEmpty(DateHelper.toCalendar(requestsCountLogEntity.getSynchronizationDate()));
	}

	private ContactsCountReportEntity getContactsCountReportEntity()
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();
		PersonDocument document = getPersonDocument(person);
		ContactsCountReportEntity contactsCountReportEntity = new ContactsCountReportEntity();
		contactsCountReportEntity.setCount(0L);
		contactsCountReportEntity.setLogin(person.getLogin().getId());
		contactsCountReportEntity.setName(person.getFullName());
		contactsCountReportEntity.setDocument(document.getDocumentSeries() + document.getDocumentNumber());
		contactsCountReportEntity.setBirthday(person.getBirthDay());
		contactsCountReportEntity.setTb(personData.getDepartment().getRegion());
		return contactsCountReportEntity;
	}

	private PersonDocument getPersonDocument(ActivePerson person)
	{
		List<PersonDocument> docs = new ArrayList<PersonDocument>(person.getPersonDocuments());
		PersonDocument personDoc = new PersonDocumentImpl();
		if(docs.size() > 0)
		{
			Collections.sort(docs, new PersonDocumentTypeComparator());
			personDoc = docs.get(0);
		}
		return personDoc;
	}

}
