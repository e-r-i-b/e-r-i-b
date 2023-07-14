package com.rssl.phizic.operations.contacts;

import com.rssl.phizic.business.addressBook.reports.RequestsCountLogEntityService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookSynchronizationConfig;
import com.rssl.phizic.config.addressbook.PeriodType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.contact.synchronization.ContactSyncCountExceedLog;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.LogWriter;
import com.rssl.phizic.logging.writers.LogWritersConfig;
import com.rssl.phizic.messaging.EmailMessagingService;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.freemarker.FreeMarkerConverter;

import java.util.*;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Чекер для проверки количества синхронизаций адресной книги клиентом.
 * При превышении количества синхронизаций отправляет письмо сотруднику и пишет в лог.
 * Не прерывает механизм синхронизации.
 */
public class ContactSynchronizationCountChecker
{
	private static final RequestsCountLogEntityService requestsCountLogEntityService = new RequestsCountLogEntityService();
	private static final EmailMessagingService emailMessagingService = new EmailMessagingService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String MAIL_SUBJECT = "Превышен порог синхронизаций адресной книги";

	/**
	 * Проверить не превышено ли количество синхронизаций для пользователя
	 * @param personData - персон дата пользователя
	 */
	public static void check(PersonData personData)
	{
		try
		{
			AddressBookSynchronizationConfig addressBookSynchronizationConfig = ConfigFactory.getConfig(AddressBookSynchronizationConfig.class);
			PeriodType periodType = addressBookSynchronizationConfig.getPeriodType();
			Long synchronizationCount = requestsCountLogEntityService.getSynchronizationCountByPeriodForLogin(personData.getLogin().getId(), periodType.getPrevious());
			if(synchronizationCount < addressBookSynchronizationConfig.getThresholdCount())
				return;

			Map<String,Object> messageProperties = getMessageProperties(synchronizationCount,addressBookSynchronizationConfig);
			String messageText = FreeMarkerConverter.convert(addressBookSynchronizationConfig.getEmailText(), messageProperties);
			emailMessagingService.sendPlainEmail(addressBookSynchronizationConfig.getEmail(),MAIL_SUBJECT,messageText);

			saveToLog(personData,messageText);
		}
		catch (IKFLMessagingException e)
		{
			log.error("Не удалось отправить письмо о превышении количества синхронизаций.",e);
		}
		catch (LoggingException e)
		{
			log.error("Не удалось записать в лог запись о превышении количества синхронизаций.",e);
		}
		catch (SystemException e)
		{
			log.error("Ошибка при формировании текста сообщения о превышении порога синхронизации.",e);
		}

	}


	private static void saveToLog(PersonData personData, String message) throws LoggingException
	{
		ActivePerson person = personData.getPerson();
		LogWritersConfig config = ConfigFactory.getConfig(LogWritersConfig.class);
		LogWriter logWriter = config.getContactSyncCountExceedLogWriter();
		ContactSyncCountExceedLog logEntry = new ContactSyncCountExceedLog();
		logEntry.setLoginId(personData.getLogin().getId());
		logEntry.setName(person.getFullName());
		PersonDocument document = getPersonDocument(person);
		logEntry.setDocument(document.getDocumentSeries() + document.getDocumentNumber());
		logEntry.setBirthDay(person.getBirthDay());
		logEntry.setTb(personData.getTb().getRegion());
		logEntry.setSyncDate(Calendar.getInstance());
		logEntry.setMessage(message);

		logWriter.write(logEntry);
	}


	private static Map<String,Object> getMessageProperties(Long synchronizationCount, AddressBookSynchronizationConfig addressBookSynchronizationConfig)
	{
		Map<String,Object> messageProperties = new HashMap<String, Object>();
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();
		PersonDocument document = getPersonDocument(person);
		messageProperties.put("FIO",person.getFullName());
		messageProperties.put("document",document.getDocumentSeries() + document.getDocumentNumber());
		messageProperties.put("birthDay", DateHelper.formatDateToString(person.getBirthDay()));
		messageProperties.put("login",person.getLogin().getId());
		messageProperties.put("periodType",addressBookSynchronizationConfig.getPeriodType());
		messageProperties.put("thresholdCount",addressBookSynchronizationConfig.getThresholdCount());
		messageProperties.put("synchronizationCount",synchronizationCount);
		return messageProperties;
	}

	private static PersonDocument getPersonDocument(ActivePerson person)
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
