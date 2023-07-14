package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.AnonymousLoginGenerator;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.Person;

import java.io.InvalidClassException;
import java.util.Set;
import java.util.HashSet;

/**
 * Класс создает анонимного клиента (логин - null, пароль - не задан совсем, запись в БД отсутствует)
 */
public class AnonymousClientCreator
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();
	private static final PersonService    personService    = new PersonService();

	private final String       userId;
	private final AccessScheme scheme;

	/**
	 * @param userId user id анонимного пользователя
	 * @param scheme схема прав анонимного пользователя
	 */
	public AnonymousClientCreator(String userId, AccessScheme scheme)
	{
		if(userId == null)
			throw new IllegalArgumentException("userId");

		if(scheme == null)
			throw new IllegalArgumentException("scheme");

		this.userId = userId;
		this.scheme = scheme;
	}

	/**
	 * Создать пользователя. Если все уже создано ранее - остается как было.
	 */
	public void create() throws Exception
	{
		Login login = createLogin(userId);
		setAccessScheme(login);
		createPerson(login);
		log.info("Обновление встроенной учетной записи анонимного клиента завершено");
	}

	private void createPerson(Login login) throws BusinessException
	{
		//noinspection UnusedCatchParameter
		try
		{
			personService.findByLogin(login);
		}
		catch (NotFoundException e)
		{
			ActivePerson newPerson = new ActivePerson();
			newPerson.setLogin(login);
			newPerson.setStatus(Person.ACTIVE);
			newPerson.setFirstName("Анонимный");
			newPerson.setSurName("Пользователь");
			newPerson.setPatrName(".");

		    Set<PersonDocument> personDocuments = new HashSet<PersonDocument>();
			PersonDocument document = new PersonDocumentImpl();

			document.setDocumentType(PersonDocumentType.REGULAR_PASSPORT_RF);
			document.setDocumentMain(true);

			personDocuments.add(document);
			newPerson.setPersonDocuments(personDocuments);
			newPerson.setCreationType(CreationType.ANONYMOUS);

			personService.add(newPerson);
		}

	}

	private void setAccessScheme(Login login) throws BusinessException, InvalidClassException
	{
		AccessScheme oldScheme = schemeOwnService.findScheme(login, AccessType.anonymous);

		if (oldScheme != null)
		{
			log.info("Схема прав доступа для встроенной " +
					"учетной записи анонимного клиента уже назначена : " + oldScheme);
			return;
		}

		schemeOwnService.setScheme(login, AccessType.anonymous, scheme);
		log.info("Установлена схема прав доступа для встроенной учетной записи анонимного клиента : " + scheme);
	}


	/*
	 * Создать логин
	 */
	private Login createLogin(String userId) throws Exception
	{
		Login login = new AnonymousLoginGenerator(userId).generate();
		log.info("Логин для анонимного пользователя создан.");
		return login;
	}

}