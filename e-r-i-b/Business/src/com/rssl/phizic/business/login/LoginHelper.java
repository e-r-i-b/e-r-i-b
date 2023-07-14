package com.rssl.phizic.business.login;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.RestrictionException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.businessProperties.BusinessPropertyService;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.business.externalsystem.AutoStopSystemService;
import com.rssl.phizic.business.externalsystem.OfflineExternalSystemEvent;
import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.business.login.exceptions.DegradationFromUDBOToCardException;
import com.rssl.phizic.business.login.exceptions.StopClientSynchronizationException;
import com.rssl.phizic.business.login.exceptions.StopClientsSynchronizationException;
import com.rssl.phizic.business.operations.restrictions.ClientRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonAgreementTytpeAndDateComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.ClientDepartmentNotAvailableException;
import com.rssl.phizic.business.persons.clients.ClientDepartmentNotSupportedException;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.EntranceConfig;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.SimpleClientIdGenerator;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.ChangeUserTypeLogDataWrapper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Хелпер использует импорт клиента из текущего алгоритма (разнесенный из разных мест алгоритм собран в единое место)
 *
 * @author khudyakov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginHelper
{
	private static final String ERROR_MESSAGE                       = "Клиент не заключил УДБО, по этой причине он не может работать с данным функционалом.";
	private static final String ASP_KEY_KEY                         = "ASP_KEY";
	private static final String UDBO_TO_CARD_KEY                    = "com.rssl.iccs.edbo.to.card.change";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PersonService personService = new PersonService();
	private static final SecurityService securityService = new SecurityService();
	private static final SimpleService simpleService = new SimpleService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final PersonImportService personImportService = new PersonImportService();
	private static final MultiInstanceExternalResourceService resourceService = new MultiInstanceExternalResourceService();
	private static final BusinessPropertyService businessPropertyService = new BusinessPropertyService();
	private static final AutoStopSystemService autoStopSystemService = new AutoStopSystemService();
	private static final BusinessDocumentService documentService = new BusinessDocumentService();

	private static final List<String> blockedSBOLClientStates = new ArrayList<String>(3);
	private static final List<String> states = new ArrayList<String>();                           //Статусы платежей, которые не надо обновлять при перепривязывании платежей

	static
	{
		blockedSBOLClientStates.add(Person.TEMPLATE);
		blockedSBOLClientStates.add(Person.WAIT_CANCELATION);

		states.add("SAVED");
		states.add("INITIAL");
	}

	/**
	 * Выполняет обновление логина.
	 *
	 * @param userVisitingMode тип входа клиента
	 * @param login логин для обновления.
	 * @return измененный логин.
	 */
	private static Login updateLogin(Login login, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		login.setLastUserVisitingMode(userVisitingMode);
		return simpleService.update(login);
	}

	private static void checkRestriction(Client client, ClientRestriction restriction) throws BusinessLogicException, BusinessException
	{
		if(client != null && (restriction != null && !restriction.accept(client)))
			throw new RestrictionException("У Вас не хватает прав для работы с данным клиентом.");
	}

	private static void checkRestriction(ActivePerson person, ClientRestriction restriction) throws BusinessLogicException, BusinessException
	{
		if(person != null)
			checkRestriction(person.asClient(), restriction);
	}

	/**
	 * Синхронизация клиента с учетом уже запрошенных в CEDBO данных
	 * выполняем все действия над клиентом описанные в алгоритме входа (добавление, обновление, поиск дублей...)
	 * (http://svn.softlab.ru/svn/PhizIC/analytics/Проекты ИКФЛ/Клиентские/ЕРИБ/2-Разработка/Вход клиента/)
	 *
	 * @param authData  данные аутентификации
	 * @param authToken ЦСА токен
	 * @param authentificationSource тип приложения (основная версия, мобильня)
	 * @param userVisitingMode тип входа (основной, фнс/озон)
	 * @param restriction ограничение на найденных клиентов
	 * @param cedboClient данные о клиенте, уже запрошенные в CEDBO
	 * @return 1. персону, если все хорошо
	 *         2. исключение со списком идентификаторов персон, если не удалось синхронизировать клиента или подразделения профилей клиента не поддерживают шину
	 *         3. null, если клиент не найден
	 */
	public static Person synchronizeByCEDBOClient(AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode, ClientRestriction restriction, Client cedboClient) throws BusinessException, BusinessLogicException
	{
		return synchronizeBase(authData, authToken, authentificationSource, userVisitingMode, restriction, cedboClient);
	}

	/**
	 * Синхронизация клиента
	 * выполняем все действия над клиентом описанные в алгоритме входа (добавление, обновление, поиск дублей...)
	 * (http://svn.softlab.ru/svn/PhizIC/analytics/Проекты ИКФЛ/Клиентские/ЕРИБ/2-Разработка/Вход клиента/)
	 *
	 * @param authData  данные аутентификации
	 * @param authToken ЦСА токен
	 * @param authentificationSource тип приложения (основная версия, мобильня)
	 * @param userVisitingMode тип входа (основной, фнс/озон)
	 * @param restriction ограничение на найденных клиентов
	 * @return 1. персону, если все хорошо
	 *         2. исключение со списком идентификаторов персон, если не удалось синхронизировать клиента или подразделения профилей клиента не поддерживают шину
	 *         3. null, если клиент не найден
	 */
	public static Person synchronize(AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode, ClientRestriction restriction) throws BusinessException, BusinessLogicException
	{
		return synchronizeBase(authData, authToken, authentificationSource, userVisitingMode, restriction, null);
	}

	private static Person synchronizeBase(AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode, ClientRestriction restriction, Client cedboClient) throws BusinessException, BusinessLogicException
	{
		Calendar birthDate = getBirthDay(authData);

		//ищем профили клиента в нашей базе по ФИО, дате рождения, ДУЛ, ТБ

		List<ActivePerson> persons = personService.getByFIOAndDoc(authData.getLastName(), authData.getFirstName(), authData.getMiddleName(), authData.getSeries(), authData.getDocument(), birthDate, getTBFromCB_CODE(authData.getCB_CODE()));
		// Проверяем, нет ли среди найденных клиентов СБОЛ договоров в неактивных статусах с системными
		// блокировками. Если есть, отправляем этих персон в архив.
		filterPersons(persons);

		boolean personsEmpty = CollectionUtils.isEmpty(persons);
		//Если клиент(ы) найден(ы) в БД
		if (!personsEmpty)
		{
			//Если найдена только одна персона, знаем с кем работать
			if (persons.size() == 1)
			{
				log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "В базе по ФИО, дате рождения, ДУЛ, ТБ найден профиль клиента.", authentificationSource));
				ActivePerson person = persons.get(0);
				checkRestriction(person, restriction);
				ContextFillHelper.fillContextByPerson(person);
				return synchronize(person, authData, userVisitingMode, authentificationSource, authToken, restriction, null);
			}

			List<Long> departnentsIds = new ArrayList<Long>();
			for (ActivePerson person : persons)
			{
				departnentsIds.add(person.getDepartmentId());
			}

		}

		Client client = getClient(cedboClient, authData, persons, authentificationSource, authToken);

		if (CollectionUtils.isEmpty(persons))
		{
			log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "В базе по ФИО, дате рождения, ДУЛ, ТБ не найден профиль клиента.", authentificationSource));

			//признак необходимости добавления клиента
			boolean needAdd = true;

			if (client != null && client.isUDBO())
			{
				if (client.getOffice() == null)
				{
					log.error(LoginInfoHelper.getPersonLogInfo(authData, authToken, "Пользователю не вернулся офис из CEDBO. Работа с таким клиентом не возможна!", authentificationSource));
					throw new BusinessLogicException("Не задан офис клиента. clientId = " + client.getId());
				}

				checkRestriction(client, restriction);
				//Начинаем поиск по ФИО+ДУЛ+ДР+ТБ в базе
				persons.addAll(personService.getByClient(client));

				if (CollectionUtils.isNotEmpty(persons))
				{
					//Клиенты найдены, так что добавлять никого не надо
					needAdd = false;
					//Если такой клиент найден, записываем ругательство в лог и возвращаем такого клиента (ов)
					StringBuilder errorText = LoginInfoHelper.getPersonLogInfo(authData, authToken, "Данные клиента из ЦСА и CEDBO НЕ совпадают!!!  Данные из ЦСА:", authentificationSource).append(LoginInfoHelper.getPersonLogInfo(client, null, "Данные из CEDBO:", authentificationSource));
					log.info(errorText);

					//Если по данным из CEDBO найден один клиент, то мы его обновляем
					if (persons.size() == 1)
					{
						ActivePerson person = persons.get(0);

						fillTrust(person, authData);
						updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
						log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "Клиент обновлён данными из CEDBO.", authentificationSource));
						return person;
					}
				}
				else
				{
					//Если ОПЯТЬ в базе ничего не нашлось, то, возможно просто паспорт изменился. Ищем по номеру и дате договора в нашей базе, и если находим, то обновляем!

					//Поиск по номеру и дате договора
					String region = client.getOffice().getCode().getFields().get("region");
					persons.addAll(personService.getByAgreement(client.getAgreementNumber(), client.getInsertionDate(), region));

					if (!CollectionUtils.isEmpty(persons))
					{
						//Клиенты найдены, так что добавлять никого не надо
						needAdd = false;
						//Если такой клиент найден, записываем ругательство в лог и возвращаем такого клиента (ов)
						StringBuilder errorText = LoginInfoHelper.getPersonLogInfo(client, authToken, "У клиента изменилась ФИО!!! Данные из CEDBO:", authentificationSource).append(LoginInfoHelper.getPersonLogInfo(persons.get(0), null, "Данные найденного клиента (если найдено несколько, это первый попавшийся):", authentificationSource));
						log.info(errorText);

						//Если по данным из CEDBO найден один клиент, то мы его обновляем
						if (persons.size() == 1)
						{
							ActivePerson person = persons.get(0);

							fillTrust(person, authData);
							updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
							log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "Клиент обновлён данными из CEDBO.", authentificationSource));
							return person;
						}
					}
				}
			}

			//последняя надежда: найти в потенциальных, но ищем если клиент сам входит
			boolean isUDBO = client != null && client.isUDBO();
			if (CollectionUtils.isEmpty(persons) && (isUDBO || !UserVisitingMode.isEmployeeInputMode(userVisitingMode)))
			{
				ActivePerson person = personService.getPotentialByFIOAndDoc(authData.getLastName(), authData.getFirstName(), authData.getMiddleName(), authData.getSeries(), authData.getDocument(), birthDate, getTBFromCB_CODE(authData.getCB_CODE()));
				if (person != null)
				{
					//Клиент найден (будем обновлять потенциального), так что добавлять никого не надо
					needAdd = false;

					String header = "Обновляем потенциального клиента: Данные из CEDBO:";
					//если CEDBO не вернул клиента, то делаем карточного
					if (!isUDBO)
					{
						client = fillFullCardClient(authData, authentificationSource);
						if (client == null)
							throw new BusinessException("Невозможно получить подразделение клиента по данным из CSA. Authtoken = " + authToken);
						header = "Обновляем потенциального клиента: Данные из ЦСА:";
					}
					log.info(LoginInfoHelper.getPersonLogInfo(person, authToken, header, authentificationSource).append(LoginInfoHelper.getPersonLogInfo(person, null, "Данные потенциального клиента:", authentificationSource)));

					fillTrust(person, authData);
					updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
					log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "Потенциальный клиент обновлён данными из CEDBO.", authentificationSource));
					return person;
				}
			}

			if (needAdd)
			{
				ActivePerson person = addClient(client, authData, authToken, authentificationSource, userVisitingMode, restriction);
				//Если клиент (Карточный) или (УДБО и в нашей базе не найден дважды), добаляем его
				if (person != null)
					persons.add(person);

				if (persons.size() == 1)
				{
					updateLogin(person.getLogin(), userVisitingMode);
					return person;
				}

				//если в пфп не нашли нормального клиента, то пытаемся вернуть потенциального
				if (UserVisitingMode.EMPLOYEE_INPUT_FOR_PFP == userVisitingMode)
					return personService.getPotentialByFIOAndDoc(authData.getLastName(), authData.getFirstName(), authData.getMiddleName(), authData.getSeries(), authData.getDocument(), birthDate, getTBFromCB_CODE(authData.getCB_CODE()));

				throw new StopClientsSynchronizationException(persons, StopClientsSynchronizationException.getDefaultMessage(persons));
			}
		}

		if (client == null || !client.isUDBO())
		{
			if(UserVisitingMode.isEmployeeInputMode(userVisitingMode))
			{
				Calendar lastCreationDate = persons.get(0).getAgreementDate();
				ActivePerson person = persons.get(0);
				//если найдено несколько договоров на обслуживание то берем с более поздней датой. Если на всех договрах нет даты подписания то берем первый попавшийся.
				//null дата считается более ранней.
				for (ActivePerson pers : persons)
				{
					if (DateHelper.nullSafeCompare(pers.getAgreementDate(), lastCreationDate) > 0)
					{
						lastCreationDate = pers.getAgreementDate();
						person = pers;
					}
				}
				return synchronize(person, authData, userVisitingMode, authentificationSource, authToken, restriction, client);
			}
			throw new StopClientsSynchronizationException(persons, client == null ? "Запрос CEDBO не вернул данных по клиенту." : "Запрос CEDBO вернул информацию о том, клиент не подписал универсальный договор банковсого обслуживания.");
		}

		// Сюда доходим только, если CEDBO вернулся с признаком УДБО клиента
		try
		{
			checkRestriction(client, restriction);

			if (persons.size() == 1)
			{
				ActivePerson person = persons.get(0);
				log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "В БД найден клиент с ID = " + person.getId()));

				// Обновляем найденного в базе клиента.
				fillTrust(person, authData);
				updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
				log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "Клиент обновлён данными из CEDBO.", authentificationSource));

				return person;
			}
			Person updatedPerson = mergeProfiles(client, persons, restriction, authData, authToken, authentificationSource, userVisitingMode);
			updateLogin(updatedPerson.getLogin(), userVisitingMode);
			updateCSAProfile(authData, updatedPerson);
			return updatedPerson;
		}
		catch (ClientDepartmentNotAvailableException e)
		{
			log.error(e.getMessage(), e);
			throw new StopClientsSynchronizationException(persons, e.getMessage());
		}
	}

	private static Person mergeProfiles(Client client, List<ActivePerson> persons, ClientRestriction restriction, AuthData authData, String authToken,
	                             AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{

		Collections.sort(persons, new PersonAgreementTytpeAndDateComparator());
		//А здесь надо одной транзакцией обновить клиента до УДБО, а остальных пометить удаленными
		ActivePerson mainPerson = persons.get(0);
		checkRestriction(mainPerson, restriction);
		persons.remove(mainPerson);

		ExtendedDepartment department = (ExtendedDepartment) personImportService.getClientDepartment(client, CreationType.UDBO);

		//Помечаем клиентов как удалённые и отправляем в архив
		log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "В БД найдено несколько клиентов. Основной клиент с ID = " + mainPerson.getId() + " будет помечен как УДБО, остальные с IDs = " + StringUtils.join(persons, "|") + " помечены как удалённые и уйдут в архив."));
		ActivePerson updatedPerson = updateAndMarkDelete(authData, mainPerson, client, persons, department, userVisitingMode);

		log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "Клиент с ID = " + mainPerson.getId() + " Обновлён до УДБО. Остальные (если были) помечены как удалённые и работать с ними больше нельзя."));
		return updatedPerson;
	}

	private static Client getClient(Client cedboClient, AuthData authData, List<ActivePerson> persons, AuthentificationSource authentificationSource, String authToken) throws BusinessException, BusinessLogicException
	{
		try
		{
			//Если ранне уже запрашивался CEDBO для получения клиента и клиент в БД не найден, то второй раз CEDBO не запрашиваем
			if (cedboClient != null && persons.isEmpty())
			{
				log.info(LoginInfoHelper.getClientLogInfo(cedboClient, authentificationSource, authToken));
				return cedboClient;
			}

			//Запрашиваем CEDBO, если клиент(ы) не найден(ы) вообще, либо,
			//если найдено несколько, и хотя бы у одного клиента подразделение поддерживает БП
			Client client = findClient(authData, persons);

			log.info(LoginInfoHelper.getClientLogInfo(client, authentificationSource, authToken));
			return client;
		}
		catch (InactiveExternalSystemException e)
		{
			//если внешняя система недоступна,
			//то бросаем исключение с возможность входа клиенту под своим профилем
			log.info(e.getMessage());
			// CHG052091 есть некоторые побочные эффекты.
			if (CollectionUtils.isNotEmpty(persons))
			{
				throw new StopClientsSynchronizationException(persons, e);
			}

			return new ClientImpl();
		}
	}

	/**
	 *  метод заносит пришедшие данные в контекст
	 * @param authenticationContext - текущий контекст аутонтификации
	 * @param authData - данные аутентификации
	 */
	public static void updateAuthenticationContext(AuthenticationContext authenticationContext, AuthData authData) throws BusinessException
	{
		authenticationContext.setCSA_SID(authData.getSID());
		authenticationContext.setMoveSession(authData.isMoveSession());
		authenticationContext.setUserId(authData.getUserId());
		authenticationContext.setCsaProfileId(authData.getCsaProfileId());
		authenticationContext.setUserAlias(authData.getUserAlias());
		authenticationContext.setMB(authData.isMB());
		authenticationContext.setCB_CODE(authData.getCB_CODE());
		authenticationContext.setTB(ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(getTBFromCB_CODE(authData.getCB_CODE())));
		authenticationContext.setPAN(authData.getPAN());
		authenticationContext.setRandomString(authData.getASPKey());
		authenticationContext.setFirstName(authData.getFirstName());
		authenticationContext.setLastName(authData.getLastName());
		authenticationContext.setMiddleName(authData.getMiddleName());
		authenticationContext.setBirthDate(authData.getBirthDate());
		authenticationContext.setDocumentNumber(authData.getDocument());
		authenticationContext.setCameFromYoungPeopleWebsite(authData.isCameFromYoungWebsite());
		authenticationContext.setExpiredPassword(authData.isExpiredPassword());
		authenticationContext.setCsaType(authData.getCsaType());
		authenticationContext.setLoginType(authData.getLoginType());
		authenticationContext.setCsaGuid(authData.getCsaGuid());
		authenticationContext.setRegistrationStatus(authData.getRegistrationStatus());
		authenticationContext.setMobileAppScheme(authData.getMobileAppScheme());
		authenticationContext.setDeviceId(authData.getDeviceId());
		authenticationContext.setDeviceInfo(authData.getDeviceInfo());
		authenticationContext.setMigrationState(authData.getMigrationState());
		authenticationContext.setProfileType(authData.getProfileType());
		authenticationContext.setLastLoginDate(authData.getLastLoginDate());
		authenticationContext.setBrowserInfo(authData.getBrowserInfo());
		authenticationContext.setSecurityType(authData.getSecurityType());

		if (StringHelper.isNotEmpty(authData.getMobileAppVersion()))
			try
			{
				authenticationContext.setClientMobileAPIVersion(VersionNumber.fromString(authData.getMobileAppVersion()));
			}
			catch (MalformedVersionFormatException e)
			{
				throw new BusinessException(e);
			}

		StoreManager.getCurrentStore().save(ASP_KEY_KEY, authData.getASPKey());
	}

	/**
	 * Обновляет данные последней аутенификации клиента
	 * @param login - логин клиента
	 * @param authData - данные аутентификации
	 */
	public static void updateClientLogonData(Login login, AuthData authData)
	{
		// Сохраняем в логине номер карты (+ её код подразделния) и признак подключения к МБ (см. CHG024212)
		securityService.updateClientLastLogonData(login, authData.getPAN(), authData.getCB_CODE(), authData.isMB(), authData.getUserId(), authData.getUserAlias());
	}

	/**
	 * Обновляет данные последней аутенификации клиента
	 * @param login - логин клиента
	 * @param authContext - контекст аутентификации
	 */
	public static void updateClientLogonData(Login login, AuthenticationContext authContext)
	{
		// Сохраняем в логине номер карты (+ её код подразделния) и признак подключения к МБ (см. CHG024212)
		securityService.updateClientLastLogonData(login, authContext.getPAN(), authContext.getCB_CODE(), authContext.isMB(), authContext.getUserId(), authContext.getUserAlias());
	}

	/**
	 * Метод поиска логина персоны
	 * @param person персона
	 * @param userVisitingMode тип входа клиента
	 * @param authentificationSource источник входа в систему (Полная версия, мобильная, обрезанная)
	 * @param authToken  ключ аутентификации (для лога)
	 * @param authData данные аутентификации
	 * @param restriction ограничение на найденных клиентов
	 * @return персону в нашей системе, если клиент найден, если нет, то null.
	 * @throws BusinessException
	 */
	private static Person synchronize(ActivePerson person, AuthData authData, UserVisitingMode userVisitingMode, AuthentificationSource authentificationSource, String authToken, ClientRestriction restriction, Client foundClient) throws BusinessException, BusinessLogicException
	{
		fillTrust(person, authData);

		// Обновляем паспорт way для карточных и СБОЛ клиентов.
		if (person.getCreationType() != CreationType.UDBO && !StringHelper.isEmpty(authData.getDocument())
				&& ClientDocumentType.valueOf(authData.getDocumentType()) == ClientDocumentType.PASSPORT_WAY)
		{
			PersonHelper.updatePersonPassportWay(person, authData.getDocument());
			personService.update(person);
		}

		try
		{
			Client client = null;
			if (Application.atm == LogThreadContext.getApplication() && !ConfigFactory.getConfig(AtmApiConfig.class).isCheckUDBOOnLogin())
			{
				client = foundClient;
			}
			else
			{
				// Запрашиваем CEDBO, чтобы проверить, не перешел ли клиент на УДБО
				client = foundClient != null ? foundClient : findClient(authData, Collections.singletonList(person));
				log.info(LoginInfoHelper.getClientLogInfo(client, authentificationSource, authToken));
			}

			//Если данные по клиенту вернулись из шины, обновляем персону. Обновляем только клиента УДБО.
			//Для карточного клиента приходит только признак незаключенного УДБО, нечем обновлять.
			//Если клиент SBOL, и вернулась из шины информация, но не с признаком заключения УДБО, то ничего не обновляем

			if (client == null)
			{
				updateLogin(person.getLogin(), userVisitingMode);
				return person;
			}

			if (!client.isUDBO())
			{
				boolean isDegradationUDBOToCard = false;
				if (person.getCreationType() == CreationType.UDBO && client.getCancellationDate() != null)
				{
					// Если клиент был УДБО, но признак заключенного УДБО не вернулся, смотрим, надо ли
					// переводить его в разряд "карточных" клиентов

					boolean changeCreationType = ConfigFactory.getConfig(CardsConfig.class).isUdboToCard();
					//Если можно переводить и наступила дата закрытия УДБО, то переводим в "карточного" клиента
					//только в этом случае меняем
					if (changeCreationType && !client.getCancellationDate().after(Calendar.getInstance()))
					{
						personImportService.changeCreationType(person, CreationType.CARD, authData.getProfileType());
						updateCSAProfile(authData, person);
						isDegradationUDBOToCard = true;
					}
				}

				Login personLogin = person.getLogin();
				updateLogin(personLogin, userVisitingMode);

				//Отобразим клиенту что он теперь карточный и неплохо было бы перезаключить договор
				if (isDegradationUDBOToCard)
				{
					log.info(String.format("Клиент с ID=%s обновлен до карточного. Дата закрытия договора УДБО: '%s'",
							person.getId(),DateHelper.formatDateToStringWithPoint(client.getCancellationDate())));
					throw new DegradationFromUDBOToCardException(personLogin, client.getCancellationDate());
				}
				return person;
			}
			ActivePerson updatedPerson = updatePersonMultiProfile(person, client, authData.getDocument(), userVisitingMode, authData, authToken, authentificationSource, restriction);

			// Персону уже нашли, и будем работать с ней в нашей системе. Можно заполнить контекст логирования.
			ContextFillHelper.fillContextByPerson(updatedPerson);

			if (!UserVisitingMode.isEmployeeInputMode(userVisitingMode) && authentificationSource != AuthentificationSource.atm_version && !UserVisitingMode.MC_PAYORDER_PAYMENT.equals(userVisitingMode))
			{
				securityService.changeUserId(updatedPerson.getLogin(), authData.getUserId());
			}
			return updatedPerson;
		}
		catch (InactiveExternalSystemException e)
		{
			//если внешняя система недоступна,
			//то бросаем исключение с возможность входа клиенту под своим профилем
			log.info(e.getMessage());
			throw new StopClientSynchronizationException(person, e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить ТБ по CB_CODE - 2 первые цифры
	 * @param code - CB_CODE, пришедший из ЦСА или iPas
	 * @return ТБ
	 */
	public static String getTBFromCB_CODE(String code)
	{
		if (StringHelper.isEmpty(code))
			return null;

		return code.substring(0, 2);
	}

	/**
	 * Проверяем, нет ли среди переданных клиентов СБОЛ договоров в неактивных статусах с системными
	 * блокировками. Если есть, отправляем этих персон в архив.
	 * @param persons список персон
	 */
	private static void filterPersons(List<ActivePerson> persons) throws BusinessException
	{
		if (CollectionUtils.isEmpty(persons))
			return;

		for (Iterator<ActivePerson> iter = persons.iterator(); iter.hasNext();)
		{
			ActivePerson person = iter.next();
			if (person.getCreationType() == CreationType.SBOL && blockedSBOLClientStates.contains(person.getStatus()))
			{
				personService.markDeleted(person);
				iter.remove();
			}
		}
	}

	/**
	 * Найти клиента во внешней системе
	 * @param authData данные аутентификации
	 * @param persons список клиентов
	 * @return клиент. Если клиент не найден - SecurityException
	 * @throws BusinessLogicException, BusinessException
	 */
	public static Client findClient(AuthData authData, List<ActivePerson> persons) throws BusinessLogicException, BusinessException
	{
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		// Если в базе найдены клиенты по данным из ЦСА, заполняем запрос CEDBO документами из базы,
		// иначе берем из контекста данные ЦСА
		Client temp = (CollectionUtils.isEmpty(persons)) ? fillClient(authData, (ActivePerson) null) : fillClient(authData, persons.get(0));

		try
		{
			//перед отправкой запроса УДБО проверяем внешнюю систему на активность
			ExternalSystemHelper.check(externalSystemGateService.findByCodeTB(getTBFromCB_CODE(authData.getCB_CODE())));

			// запрашиваем CEDBO по ФИО, ДУЛ, и дате рождения
			Client client = clientService.fillFullInfo(temp);
			// если клиент null, значит вернулась ошибка -425, то есть в ЦОД нет информации по этим данным,
			// либо если для существующего УДБО клиента вернулся ответ со статусом УДБО NO_CONTRACT (!client.isUDBO() && client.getCancellationDate() == null) и включена настройка com.rssl.iccs.card.request.by.udbo.state
			// запрашиваем CEDBO по номеру карты и CB_CODE (если номер заполнен)
			boolean useRequest = ConfigFactory.getConfig(EntranceConfig.class).isUseUbdoCardRequest();
			if ((client == null && !StringHelper.isEmpty(authData.getPAN())) ||
					(client != null && !client.isUDBO() && client.getCancellationDate() == null && CollectionUtils.isNotEmpty(persons) && persons.get(0).getCreationType() == CreationType.UDBO &&
							useRequest && !authData.isMobileCheckout()))
			{
				client = clientService.getClientByCardNumber(authData.getCB_CODE(), authData.getPAN());
			}
			AuthenticationContext.getContext().setCheckedCEDBO(true);
			return client == null ? new ClientImpl() : client;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (OfflineExternalSystemException e)
		{
			List<? extends ExternalSystem> externalSystems = e.getExternalSystems();
			try
			{
				if (!ExternalSystemHelper.isActive(externalSystems))
					return new ClientImpl();
			}
			catch (GateException ex)
			{
				throw new BusinessException(ex);
			}
			List<OfflineExternalSystemEvent> eventList = new ArrayList<OfflineExternalSystemEvent>();
			for (ExternalSystem adapter : externalSystems)
			{
				OfflineExternalSystemEvent offlineEvent = new OfflineExternalSystemEvent();
				offlineEvent.setAdapter((Adapter) adapter);
				offlineEvent.setAutoStopSystemType(AutoStopSystemType.COD);
				offlineEvent.setEventTime(Calendar.getInstance());
				eventList.add(offlineEvent);
			}
			autoStopSystemService.addList(eventList);
			return new ClientImpl();
		}
		catch (GateTimeOutException e)
		{
			return temp;
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * Сформировать клиента по данным аутентификации и персоне
	 * @param authData данные аутентификации
	 * @param person   персона
	 * @return клиент
	 */
	private static Client fillClient(AuthData authData, ActivePerson person)
	{
		ClientImpl client = new ClientImpl();
		//просто надо где-то передать этот параметр
		//todo ENH025292: Вынести работу с CEBDO в отдельный гейтовый сервис
		client.setId(authData.getCB_CODE());
		client.setBirthDay(getBirthDay(authData));

		client.setFirstName((!StringHelper.isEmpty(authData.getFirstName()) || person == null) ? authData.getFirstName() : person.getFirstName());
		client.setPatrName((!StringHelper.isEmpty(authData.getMiddleName()) || person == null) ? authData.getMiddleName() : person.getPatrName());
		client.setSurName((!StringHelper.isEmpty(authData.getLastName()) || person == null) ? authData.getLastName() : person.getSurName());

		List<ClientDocument> documents = new ArrayList<ClientDocument>(1);

		if (person != null)
		{
			for (PersonDocument personDocument : person.getPersonDocuments())
			{
				if(personDocument.getDocumentType() == PersonDocumentType.valueOf(ClientDocumentType.PASSPORT_WAY))
				{
					//Бред сивой кобылы. Для ЦОД обязательно надо тереть пробелы в паспорте WAY.
					// При этом, в GFL этого делать нельзя, так как WAY ищет ровно по тому документу, который был отдан из iPAs
					ClientDocumentImpl doc = new ClientDocumentImpl();
					//в БД паспорт way хранится в серии. см. fillClient()
					if (!StringHelper.isEmpty(personDocument.getDocumentSeries()))
						doc.setDocSeries(personDocument.getDocumentSeries());
					doc.setDocumentType(ClientDocumentType.PASSPORT_WAY);
					documents.add(doc);
				}
				else
				{
					documents.add(new ClientDocumentImpl(personDocument));
				}
			}
		}
		else
		{
			ClientDocumentType documentType = ClientDocumentType.valueOf(authData.getDocumentType());
			if (ClientDocumentType.PASSPORT_WAY == documentType)
			{
				//в контексте паспорт way хранится в номере
				documents.add(new ClientDocumentImpl(authData.getDocument(), documentType));
			}
			else
			{
				documents.add(new ClientDocumentImpl(authData.getSeries(), authData.getDocument(), documentType));
			}
		}
		client.setDocuments(documents);

		return client;
	}

	/**
	 * Заполнение клиента данными, полученными от CSA (или iPAS)
	 * Заполняется всё, кроме офиса.
	 *
	 * @param client если параметр пуст, тогда добавляем сведения только о паспорте way
	 * @param authData данные аутентификации
	 * @return клиент
	 */
	private static Client fillClient(AuthData authData, Client client)
	{
		ClientDocumentImpl document = new ClientDocumentImpl();
		ClientDocumentType documentType = ClientDocumentType.valueOf(authData.getDocumentType());

		document.setDocIdentify(true);
		document.setDocumentType(documentType);
		if (ClientDocumentType.PASSPORT_WAY == documentType)
		{
			document.setDocSeries(authData.getDocument());
		}
		else
		{
			document.setDocSeries(authData.getSeries());
			document.setDocNumber(authData.getDocument());
		}

		if (client == null)
		{
			ClientImpl businessClient = new ClientImpl();
			businessClient.setFirstName(authData.getFirstName());
			businessClient.setSurName(authData.getLastName());
			businessClient.setPatrName(authData.getMiddleName());

			businessClient.setBirthDay(getBirthDay(authData));

			List<ClientDocument> documents = new ArrayList<ClientDocument>();
			documents.add(document);
			businessClient.setDocuments(documents);

			return businessClient;
		}
		else
		{
			((List<ClientDocumentImpl>) client.getDocuments()).add(document);
			return client;
		}
	}

	/**
	 * Заполнить СБОЛ клиента, обновив только те данные, ктоорые мы получаем из CSA/iPas.
	 * Документ PASSPORT_WAY обновляется в updatePerson
	 * @param authData данные аутентификации
	 * @param person обновляемый клиент
	 * @param authentificationSource источик входа в систему
	 * @param userVisitingMode тип входа клиента
	 * @return клиент либо null, если невозможно определить подразделение по данным из CSA/iPas
	 */
	private static Client fillSBOLClient(AuthData authData, ActivePerson person, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode) throws BusinessLogicException
	{
		try
		{
			//обновляем серию и номер документа
			String passportWayNumber = authData.getDocument();
			if (!StringHelper.isEmpty(passportWayNumber) && !UserVisitingMode.isEmployeeInputMode(userVisitingMode))
				PersonHelper.updatePersonPassportWay(person, passportWayNumber);

			ClientImpl client = (ClientImpl) person.asClient();
			client.setBirthDay(getBirthDay(authData));
			client.setFirstName(authData.getFirstName());
			client.setPatrName(authData.getMiddleName());
			client.setSurName(authData.getLastName());

			// Убираем маршрутизирующую информацию из ID
			client.setId(IDHelper.restoreOriginalId(client.getId()));
			return client;
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);

			//Для мобильной версии в т.ч. и iPhone
			if (authentificationSource.equals(AuthentificationSource.mobile_version))
				throw new BusinessLogicException(e);

			return null;
		}
	}

	public static GregorianCalendar getBirthDay(AuthData authData)
	{
		if (StringHelper.isEmpty(authData.getBirthDate()))
			return null;

		return XMLDatatypeHelper.parseDate(authData.getBirthDate());
	}

	/**
	 * Заполнить карточного клиента из контекста, вместе с офисом
	 * @param authData данные аутентификации
	 * @param authentificationSource источник входа в систему
	 * @return клиент либо null, если невозможно определить подразделение по данным из CSA/iPas
	 */
	private static Client fillFullCardClient(AuthData authData, AuthentificationSource authentificationSource) throws BusinessLogicException
	{
		Client client = fillClient(authData, (ActivePerson) null);
		try
		{
			((ClientImpl) client).setOffice(getDepartment(getTBFromCB_CODE(authData.getCB_CODE())));
		}
		catch (BusinessException e)
		{
			//Для мобильной версии в т.ч. и iPhone
			if (authentificationSource.equals(AuthentificationSource.mobile_version))
				throw new BusinessLogicException(e);

			log.error(e.getMessage(), e);
			return null;
		}
		return client;
	}

	/**
	 * Обновить персону данными из клиента
	 * @param person - персона, которую необходимо обновить
	 * @param client - клиень, пришедший из внешней системы
	 * @param passportWayNumber - номер паспорта way клиента (null, если не известен)
	 * @param visitingMode тип входа клиента
	 * @throws BusinessException, BusinessLogicException
	 */
	public static void updatePerson(ActivePerson person, Client client, String passportWayNumber, UserVisitingMode visitingMode, AuthData authData) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (!StringHelper.isEmpty(passportWayNumber) && !UserVisitingMode.isEmployeeInputMode(visitingMode))
				PersonHelper.updatePersonPassportWay(person, passportWayNumber); // обновление person в БД ниже

			CreationType oldCreationType = null;
			String oldAgreementNumber = null;
			String oldPhone = null;
			if (person != null)
			{
				oldCreationType = person.getCreationType();
				oldAgreementNumber = person.getAgreementNumber();
				oldPhone = person.getMobilePhone();
			}
			CreationType creationType = getPersonCreationType(client, person);
			// Для СБОЛ клиентов вместо номера карты передаем уже существующий clientId, чтоб не потерять его
			// (записан в client.getId()).
			personImportService.addOrUpdatePerson(person, client, creationType, DefaultSchemeType.getDefaultSchemeType(creationType, authData.getProfileType()));

			if (person.getCreationType() != oldCreationType ||
				!StringHelper.equalsNullIgnore(person.getAgreementNumber(), oldAgreementNumber) ||
				!StringHelper.equalsNullIgnore(person.getMobilePhone(), oldPhone))
			{
				updateCSAProfile(authData, person);
			}

			updateLogin(person.getLogin(), visitingMode);
		}
		catch (ClientDepartmentNotAvailableException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Обновление профиля с учетом возможного возникновения дублей профилей после обновления
	 * @param person - профиль клиента в блоке
	 * @param client - клиент из внешней системы
	 * @param passportWayNumber - паспорт way
	 * @param visitingMode - visiting mode
	 * @param authData - данные входа
	 * @param authToken - токен
	 * @param authentificationSource - сорц
	 * @param restriction - рестрикшен
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private static ActivePerson updatePersonMultiProfile(ActivePerson person, Client client, String passportWayNumber, UserVisitingMode visitingMode, AuthData authData,
	                                             String authToken, AuthentificationSource authentificationSource, ClientRestriction restriction) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (!StringHelper.isEmpty(passportWayNumber) && !UserVisitingMode.isEmployeeInputMode(visitingMode))
				PersonHelper.updatePersonPassportWay(person, passportWayNumber); // обновление person в БД ниже

			CreationType oldCreationType = null;
			String oldAgreementNumber = null;
			String oldPhone = null;
			if (person != null)
			{
				oldCreationType = person.getCreationType();
				oldAgreementNumber = person.getAgreementNumber();
				oldPhone = person.getMobilePhone();
			}
			CreationType creationType = getPersonCreationType(client, person);

			//после обновления не появиться ли дублей? Ищем по ФИО+паспорт way+ДР+ТБ. Если появилось-необходимо смержить записи.
			List<ActivePerson> persons = personService.getByFIOAndDoc(client.getSurName(), client.getFirstName(), client.getPatrName(), passportWayNumber, "", client.getBirthDay(), getTBFromCB_CODE(authData.getCB_CODE()));
			ActivePerson updatedPerson = null;
			List<ActivePerson> mergePersons = new ArrayList<ActivePerson>();
			if (persons.size() > 0)
			{
				mergePersons.add(person);
				for (ActivePerson oldPerson : persons)
				{
					if (!oldPerson.getId().equals(person.getId()))
						mergePersons.add(oldPerson);
				}
			}

			if (mergePersons.size() > 1)
			{
				//нашлась запись клиента с CEDBO данными. Значит надо мержить.
				updatedPerson = (ActivePerson) mergeProfiles(client, mergePersons, restriction, authData, authToken, authentificationSource, visitingMode);
			}
			else
			{
				// Для СБОЛ клиентов вместо номера карты передаем уже существующий clientId, чтоб не потерять его
				// (записан в client.getId()).
				updatedPerson = personImportService.addOrUpdatePerson(person, client, creationType, DefaultSchemeType.getDefaultSchemeType(creationType, authData.getProfileType()));
			}

			if (updatedPerson.getCreationType() != oldCreationType ||
				!StringHelper.equalsNullIgnore(updatedPerson.getAgreementNumber(), oldAgreementNumber) ||
				!StringHelper.equalsNullIgnore(updatedPerson.getMobilePhone(), oldPhone))
			{
				updateCSAProfile(authData, updatedPerson);
			}

			updateLogin(updatedPerson.getLogin(), visitingMode);
			return updatedPerson;
		}
		catch (ClientDepartmentNotAvailableException e)
		{
			log.error(e.getMessage(), e);
			return person;
		}
	}

	/**
	 * Получение ссылок на продукты клиента
	 * @param persons список клиентов
	 * @param clazz класс ссылки
	 * @return карта клиент - список ссылок типа clazz
	 * @throws BusinessException
	 */
	public static <T extends ExternalResourceLink> Map<Person, List<T>> getLinks(List<Person> persons, Class<T> clazz) throws BusinessException, BusinessLogicException
	{
		Map<Person, List<T>> linksMap = new HashMap<Person, List<T>>();
		for(Person person : persons)
		{
			linksMap.put(person, resourceService.getInSystemLinks(person.getLogin(), clazz, null));
		}
		return linksMap;
	}

	/**
	 * @return номер ТБ сотрудника банка
	 * @throws BusinessException
	 */
	public static String getEmployeeOfficeRegion() throws BusinessException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		Office office = departmentService.findById(employee.getDepartmentId());
		if (office == null)
			throw new BusinessException("Подразделение сотрудника не найдено, id подразделения = " + employee.getDepartmentId());

		return new SBRFOfficeAdapter(office).getCode().getRegion();
	}

	/**
	 * @param lightScheme лайт схема
	 * @return лайт схема?
	 */
	public static boolean isLigthAppScheme(String lightScheme)
	{
		return Boolean.parseBoolean(lightScheme);
	}

	/**
	 * Добавляем нового клиента в систему
	 * @param client клиент, полученный из внешней системы
	 * @param authData данные аутентификации
	 * @param authToken для аутентификации в логе
	 * @param authentificationSource источник входа в систему (Полная версия, мобильная, обрезанная)
	 * @param visitingMode режим работы клиента
	 * @param restriction ограничение на найденных клиентов
	 * @return новый, созданный логин или null, если не создали (не добавили)
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private static ActivePerson addClient(Client client, AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode visitingMode, ClientRestriction restriction) throws BusinessLogicException, BusinessException
	{
		try
		{
			ExtendedDepartment department = null;
			// Клиент может быть null, если из CEDBO не получили информацию ни по ФИО+ДУЛ+дата рождения, ни по карте.
			// Такого клиента считаем карточным, входящим по кредитной карте
			if (client != null && client.isUDBO())
			{
				//Если клиент УДБО, однако подразделение не подключено к шине, такого клиента не добавляем
				department = (ExtendedDepartment) getDBClientDepartment(client);
				fillClient(authData, client);
			}
			else
			{
				//Если подразделение, в котором обслуживается клиент относится к Байкальскому Банку,
				// заполняем клиента данными из ЦСА (iPAS) и добавляем в базу.
				//В других случаях выходим из метода, чтобы вернуть пустой логин и перенаправить
				//карточных клиентов в СБОЛ ЦА
				switch (visitingMode)
				{
					case EMPLOYEE_INPUT_BY_CARD:
					case EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT:
					case EMPLOYEE_INPUT_BY_PHONE:
					case EMPLOYEE_INPUT_FOR_PFP:
						return null;
					case BASIC:
						break;
					case PAYORDER_PAYMENT:
					case MC_PAYORDER_PAYMENT:
						// Клиент пришёл оплатить ФНС/OZON, проверка на Байкальский банк не нужна
						break;
				}
				ClientImpl businessClient = (ClientImpl) fillClient(authData, (Client) null);
				Department businessDepartment = getClientDepartment(authData, authentificationSource);
				if (businessDepartment == null)
				{
					return null;
				}
				department = (ExtendedDepartment) businessDepartment;
				businessClient.setOffice(department);
				if(visitingMode == UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT || visitingMode == UserVisitingMode.MC_PAYORDER_PAYMENT)
				{
					//Номер подразделения(ОСБ).
					String agencyId = StringHelper.appendLeadingZeros(((ExtendedCodeImpl)department.getCode()).getBranch(), 4);
					//Номер тербанка(ТБ).
					String regionId = StringHelper.appendLeadingZeros(((ExtendedCodeImpl)department.getCode()).getRegion(), 2);
					//Значение должно соответствовать регулярному выражению «[0-9]{6}»
					StringBuilder rbBrchId = new StringBuilder();
					rbBrchId.append(regionId).append(agencyId);
					businessClient.setId(SimpleClientIdGenerator.generateClientId(businessClient.getSurName(), businessClient.getFirstName(), businessClient.getPatrName(), authData.getBirthDate(), authData.getDocument(), rbBrchId.toString()));
				}
				else
					businessClient.setId(authData.getPAN());
				client = businessClient;
			}

			if (StringHelper.isEmpty(department.getAdapterUUID()))
			{
				throw new BusinessException(LoginInfoHelper.getPersonLogInfo(client, authToken,
						"Для подразделения с ID = " + department.getId() + " не задан адаптер внешней системы." +
								" Невозможно добавить клиента. ", authentificationSource).toString());
			}


			//проверяем возможность работы клиента(сотрудника с клиентом)
			checkRestriction(client, restriction);

			CreationType type = getPersonCreationType(client, null);
			ActivePerson person = personImportService.addOrUpdatePerson(null, client, type, DefaultSchemeType.getDefaultSchemeType(type, authData.getProfileType()), department);
			if (person == null)
				throw new BusinessException("Клиент не добавлен.");
			if(!UserVisitingMode.isEmployeeInputMode(visitingMode))
			{
				securityService.changeUserId(person.getLogin(), authData.getUserId());
			}	

			fillTrust(person, authData);

			log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, " Клиент добавлен в систему."));
			updateCSAProfile(authData, person);
			return person;
		}
		catch (ClientDepartmentNotSupportedException e)
		{
			log.info(e);
			throw new BusinessLogicException(e);
		}
		catch (ClientDepartmentNotAvailableException e)
		{
			log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "Не найдено или не поддерживает БП подразделение, к которому привязан клиент (УДБО)"));

			//Для мобильной версии в т.ч. и iPhone
			if (authentificationSource.equals(AuthentificationSource.mobile_version))
				throw new BusinessLogicException(e);

			return null;
		}
		catch (BusinessException e)
		{
			log.info(e);

			//Для мобильной версии в т.ч. и iPhone
			if (authentificationSource.equals(AuthentificationSource.mobile_version))
				throw new BusinessLogicException(e);

			throw e;
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск и заполнение подразделения клиента
	 * @param authData данные аутентификации
	 * @param authentificationSource
	 * @return подразделение
	 * @throws BusinessException
	 */
	private static Department getClientDepartment(AuthData authData, AuthentificationSource authentificationSource) throws BusinessException, BusinessLogicException
	{
		try
		{
			return getDepartment(getTBFromCB_CODE(authData.getCB_CODE()));
		}
		catch (ClientDepartmentNotSupportedException e)
		{
			throw new ClientDepartmentNotSupportedException(e);
		}
		catch (BusinessException e)
		{
			//Для мобильной версии в т.ч. и iPhone
			if (authentificationSource.equals(AuthentificationSource.mobile_version))
				throw new BusinessLogicException(e);

			log.error(e.getMessage(), e);
			return null;
		}
	}

	private static Department getDepartment(String terBankCode) throws BusinessException, BusinessLogicException
	{
		Department department = departmentService.findByCode(new ExtendedCodeImpl(terBankCode, null, null));
		if (department == null)
			throw new BusinessLogicException("Подразделение, в котором обслуживается клиент не подключено к системе.");

		return department;
	}

	private static Department getDBClientDepartment(Client client) throws BusinessException
	{
		return personImportService.getClientDepartment(client, getPersonCreationType(client, null));
	}

	/**
	 * Тип договора пользователя
	 * @param client - клиент из внешней системы
	 * @param person - персона (из нашей БД) может быть null
	 * @return тип договора (пользовательский)
	 */
	private static CreationType getPersonCreationType(Client client, ActivePerson person)
	{

		if (client.isUDBO())
		{
			return CreationType.UDBO;
		}

		return (person == null || person.getCreationType() == CreationType.CARD || person.getCreationType() == CreationType.POTENTIAL) ? CreationType.CARD : CreationType.SBOL;
	}

	/**
	 * Обновить одного клиента и список остальных отправить в архив
	 * @param authData данные аутентификации
	 * @param person существующая персона для обновления, если нет, то null
	 * @param personsToDelete список клиентов, которых необходимо отправить в архив. Если нет клиентов передавать null или пустой список
	 * @param client клиент
	 * @param department подразделение, к которому привязан клиент
	 * @param userVisitingMode режим посещения
	 * @return Единственного оставшегося, обновлённого клиента.
	 * @throws BusinessException, BusinessLogicException
	 */
	private static ActivePerson updateAndMarkDelete(AuthData authData, ActivePerson person, Client client, List<ActivePerson> personsToDelete, Department department, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		fillTrust(person, authData);

		//обновляем профиль клиента
		ActivePerson addedPerson = personImportService.addOrUpdatePerson(person, client, CreationType.UDBO, DefaultSchemeType.getDefaultSchemeType(CreationType.UDBO, authData.getProfileType()), department);
		List<Long> logins = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(personsToDelete))
		{
			for (ActivePerson personToDelete : personsToDelete)
			{
				personService.markDeleted(personToDelete);
				logins.add(personToDelete.getLogin().getId());
				if (Person.ACTIVE.equals(personToDelete.getStatus()))
				{
					ChangeUserTypeLogDataWrapper.writeRemoveClientLogData(personToDelete.getCreationType().name(), department.getId());
				}
			}
		}

		Integer count = documentService.findAndUpdateLogin(addedPerson.getLogin().getId(), logins, states);
		log.info("Количеcтво перепривязанных документов = " + count);
		return addedPerson;
	}

	/**
	 * Устанавливаем режим ограниченной функциональности для клиентов
	 *
	 * @param person клиент
	 * @param authData данные аутентификации
	 */
	private static void fillTrust(ActivePerson person, AuthData authData) throws BusinessException
	{
		person.setSecurityType(authData.getSecurityType());
		person.saveStoreSecurityType(authData.getSecurityType());
		personService.update(person);
	}

	public static void updateCSAProfile(AuthData authData, Person person)
	{
		try
		{
			UserInfo userInfo = new UserInfo();
			userInfo.setCbCode(authData.getCB_CODE());
			userInfo.setFirstname(authData.getFirstName());
			userInfo.setSurname(authData.getLastName());
			userInfo.setPatrname(authData.getMiddleName());
			userInfo.setPassport(StringHelper.getEmptyIfNull(authData.getSeries()) + StringHelper.getEmptyIfNull(authData.getDocument()));
			userInfo.setBirthdate(getBirthDay(authData));
			CSABackRequestHelper.sendUpdateProfileAdditionalDataRq(userInfo, person.getCreationType(), person.getAgreementNumber(), person.getMobilePhone());
		}
		catch (Exception e)
		{
			log.error("Ошибка обновления доп. данных в цса для клиента " + person.getId(), e);
		}
	}

	/**
	 * Костыль для mAPI и socialApi. Проверять по политике доступа для основной схемы
	 * @return
	 */
	public static AccessType getPrincipalAccessTypeForAPI (AuthenticationContext context, UserPrincipal principal)
	{
		if (context != null && (ApplicationUtil.isMobileApi() || ApplicationUtil.isSocialApi()) &&
				context.isPlatformPasswordConfirm() &&
				principal.getAccessPolicy().getAccessType().equals(AccessType.mobileLimited))
		{
			return AccessType.simple;
		}
		return null;
	}
}
