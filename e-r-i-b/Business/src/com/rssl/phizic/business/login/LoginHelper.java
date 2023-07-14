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
 * ������ ���������� ������ ������� �� �������� ��������� (����������� �� ������ ���� �������� ������ � ������ �����)
 *
 * @author khudyakov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginHelper
{
	private static final String ERROR_MESSAGE                       = "������ �� �������� ����, �� ���� ������� �� �� ����� �������� � ������ ������������.";
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
	private static final List<String> states = new ArrayList<String>();                           //������� ��������, ������� �� ���� ��������� ��� ���������������� ��������

	static
	{
		blockedSBOLClientStates.add(Person.TEMPLATE);
		blockedSBOLClientStates.add(Person.WAIT_CANCELATION);

		states.add("SAVED");
		states.add("INITIAL");
	}

	/**
	 * ��������� ���������� ������.
	 *
	 * @param userVisitingMode ��� ����� �������
	 * @param login ����� ��� ����������.
	 * @return ���������� �����.
	 */
	private static Login updateLogin(Login login, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		login.setLastUserVisitingMode(userVisitingMode);
		return simpleService.update(login);
	}

	private static void checkRestriction(Client client, ClientRestriction restriction) throws BusinessLogicException, BusinessException
	{
		if(client != null && (restriction != null && !restriction.accept(client)))
			throw new RestrictionException("� ��� �� ������� ���� ��� ������ � ������ ��������.");
	}

	private static void checkRestriction(ActivePerson person, ClientRestriction restriction) throws BusinessLogicException, BusinessException
	{
		if(person != null)
			checkRestriction(person.asClient(), restriction);
	}

	/**
	 * ������������� ������� � ������ ��� ����������� � CEDBO ������
	 * ��������� ��� �������� ��� �������� ��������� � ��������� ����� (����������, ����������, ����� ������...)
	 * (http://svn.softlab.ru/svn/PhizIC/analytics/������� ����/����������/����/2-����������/���� �������/)
	 *
	 * @param authData  ������ ��������������
	 * @param authToken ��� �����
	 * @param authentificationSource ��� ���������� (�������� ������, ��������)
	 * @param userVisitingMode ��� ����� (��������, ���/����)
	 * @param restriction ����������� �� ��������� ��������
	 * @param cedboClient ������ � �������, ��� ����������� � CEDBO
	 * @return 1. �������, ���� ��� ������
	 *         2. ���������� �� ������� ��������������� ������, ���� �� ������� ���������������� ������� ��� ������������� �������� ������� �� ������������ ����
	 *         3. null, ���� ������ �� ������
	 */
	public static Person synchronizeByCEDBOClient(AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode, ClientRestriction restriction, Client cedboClient) throws BusinessException, BusinessLogicException
	{
		return synchronizeBase(authData, authToken, authentificationSource, userVisitingMode, restriction, cedboClient);
	}

	/**
	 * ������������� �������
	 * ��������� ��� �������� ��� �������� ��������� � ��������� ����� (����������, ����������, ����� ������...)
	 * (http://svn.softlab.ru/svn/PhizIC/analytics/������� ����/����������/����/2-����������/���� �������/)
	 *
	 * @param authData  ������ ��������������
	 * @param authToken ��� �����
	 * @param authentificationSource ��� ���������� (�������� ������, ��������)
	 * @param userVisitingMode ��� ����� (��������, ���/����)
	 * @param restriction ����������� �� ��������� ��������
	 * @return 1. �������, ���� ��� ������
	 *         2. ���������� �� ������� ��������������� ������, ���� �� ������� ���������������� ������� ��� ������������� �������� ������� �� ������������ ����
	 *         3. null, ���� ������ �� ������
	 */
	public static Person synchronize(AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode, ClientRestriction restriction) throws BusinessException, BusinessLogicException
	{
		return synchronizeBase(authData, authToken, authentificationSource, userVisitingMode, restriction, null);
	}

	private static Person synchronizeBase(AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode, ClientRestriction restriction, Client cedboClient) throws BusinessException, BusinessLogicException
	{
		Calendar birthDate = getBirthDay(authData);

		//���� ������� ������� � ����� ���� �� ���, ���� ��������, ���, ��

		List<ActivePerson> persons = personService.getByFIOAndDoc(authData.getLastName(), authData.getFirstName(), authData.getMiddleName(), authData.getSeries(), authData.getDocument(), birthDate, getTBFromCB_CODE(authData.getCB_CODE()));
		// ���������, ��� �� ����� ��������� �������� ���� ��������� � ���������� �������� � ����������
		// ������������. ���� ����, ���������� ���� ������ � �����.
		filterPersons(persons);

		boolean personsEmpty = CollectionUtils.isEmpty(persons);
		//���� ������(�) ������(�) � ��
		if (!personsEmpty)
		{
			//���� ������� ������ ���� �������, ����� � ��� ��������
			if (persons.size() == 1)
			{
				log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "� ���� �� ���, ���� ��������, ���, �� ������ ������� �������.", authentificationSource));
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
			log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "� ���� �� ���, ���� ��������, ���, �� �� ������ ������� �������.", authentificationSource));

			//������� ������������� ���������� �������
			boolean needAdd = true;

			if (client != null && client.isUDBO())
			{
				if (client.getOffice() == null)
				{
					log.error(LoginInfoHelper.getPersonLogInfo(authData, authToken, "������������ �� �������� ���� �� CEDBO. ������ � ����� �������� �� ��������!", authentificationSource));
					throw new BusinessLogicException("�� ����� ���� �������. clientId = " + client.getId());
				}

				checkRestriction(client, restriction);
				//�������� ����� �� ���+���+��+�� � ����
				persons.addAll(personService.getByClient(client));

				if (CollectionUtils.isNotEmpty(persons))
				{
					//������� �������, ��� ��� ��������� ������ �� ����
					needAdd = false;
					//���� ����� ������ ������, ���������� ������������ � ��� � ���������� ������ ������� (��)
					StringBuilder errorText = LoginInfoHelper.getPersonLogInfo(authData, authToken, "������ ������� �� ��� � CEDBO �� ���������!!!  ������ �� ���:", authentificationSource).append(LoginInfoHelper.getPersonLogInfo(client, null, "������ �� CEDBO:", authentificationSource));
					log.info(errorText);

					//���� �� ������ �� CEDBO ������ ���� ������, �� �� ��� ���������
					if (persons.size() == 1)
					{
						ActivePerson person = persons.get(0);

						fillTrust(person, authData);
						updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
						log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "������ ������� ������� �� CEDBO.", authentificationSource));
						return person;
					}
				}
				else
				{
					//���� ����� � ���� ������ �� �������, ��, �������� ������ ������� ���������. ���� �� ������ � ���� �������� � ����� ����, � ���� �������, �� ���������!

					//����� �� ������ � ���� ��������
					String region = client.getOffice().getCode().getFields().get("region");
					persons.addAll(personService.getByAgreement(client.getAgreementNumber(), client.getInsertionDate(), region));

					if (!CollectionUtils.isEmpty(persons))
					{
						//������� �������, ��� ��� ��������� ������ �� ����
						needAdd = false;
						//���� ����� ������ ������, ���������� ������������ � ��� � ���������� ������ ������� (��)
						StringBuilder errorText = LoginInfoHelper.getPersonLogInfo(client, authToken, "� ������� ���������� ���!!! ������ �� CEDBO:", authentificationSource).append(LoginInfoHelper.getPersonLogInfo(persons.get(0), null, "������ ���������� ������� (���� ������� ���������, ��� ������ ����������):", authentificationSource));
						log.info(errorText);

						//���� �� ������ �� CEDBO ������ ���� ������, �� �� ��� ���������
						if (persons.size() == 1)
						{
							ActivePerson person = persons.get(0);

							fillTrust(person, authData);
							updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
							log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "������ ������� ������� �� CEDBO.", authentificationSource));
							return person;
						}
					}
				}
			}

			//��������� �������: ����� � �������������, �� ���� ���� ������ ��� ������
			boolean isUDBO = client != null && client.isUDBO();
			if (CollectionUtils.isEmpty(persons) && (isUDBO || !UserVisitingMode.isEmployeeInputMode(userVisitingMode)))
			{
				ActivePerson person = personService.getPotentialByFIOAndDoc(authData.getLastName(), authData.getFirstName(), authData.getMiddleName(), authData.getSeries(), authData.getDocument(), birthDate, getTBFromCB_CODE(authData.getCB_CODE()));
				if (person != null)
				{
					//������ ������ (����� ��������� ��������������), ��� ��� ��������� ������ �� ����
					needAdd = false;

					String header = "��������� �������������� �������: ������ �� CEDBO:";
					//���� CEDBO �� ������ �������, �� ������ ����������
					if (!isUDBO)
					{
						client = fillFullCardClient(authData, authentificationSource);
						if (client == null)
							throw new BusinessException("���������� �������� ������������� ������� �� ������ �� CSA. Authtoken = " + authToken);
						header = "��������� �������������� �������: ������ �� ���:";
					}
					log.info(LoginInfoHelper.getPersonLogInfo(person, authToken, header, authentificationSource).append(LoginInfoHelper.getPersonLogInfo(person, null, "������ �������������� �������:", authentificationSource)));

					fillTrust(person, authData);
					updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
					log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "������������� ������ ������� ������� �� CEDBO.", authentificationSource));
					return person;
				}
			}

			if (needAdd)
			{
				ActivePerson person = addClient(client, authData, authToken, authentificationSource, userVisitingMode, restriction);
				//���� ������ (���������) ��� (���� � � ����� ���� �� ������ ������), �������� ���
				if (person != null)
					persons.add(person);

				if (persons.size() == 1)
				{
					updateLogin(person.getLogin(), userVisitingMode);
					return person;
				}

				//���� � ��� �� ����� ����������� �������, �� �������� ������� ��������������
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
				//���� ������� ��������� ��������� �� ������������ �� ����� � ����� ������� �����. ���� �� ���� �������� ��� ���� ���������� �� ����� ������ ����������.
				//null ���� ��������� ����� ������.
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
			throw new StopClientsSynchronizationException(persons, client == null ? "������ CEDBO �� ������ ������ �� �������." : "������ CEDBO ������ ���������� � ���, ������ �� �������� ������������� ������� ���������� ������������.");
		}

		// ���� ������� ������, ���� CEDBO �������� � ��������� ���� �������
		try
		{
			checkRestriction(client, restriction);

			if (persons.size() == 1)
			{
				ActivePerson person = persons.get(0);
				log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "� �� ������ ������ � ID = " + person.getId()));

				// ��������� ���������� � ���� �������.
				fillTrust(person, authData);
				updatePerson(person, client, authData.getDocument(), userVisitingMode, authData);
				log.info(LoginInfoHelper.getPersonLogInfo(authData, authToken, "������ ������� ������� �� CEDBO.", authentificationSource));

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
		//� ����� ���� ����� ����������� �������� ������� �� ����, � ��������� �������� ����������
		ActivePerson mainPerson = persons.get(0);
		checkRestriction(mainPerson, restriction);
		persons.remove(mainPerson);

		ExtendedDepartment department = (ExtendedDepartment) personImportService.getClientDepartment(client, CreationType.UDBO);

		//�������� �������� ��� �������� � ���������� � �����
		log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "� �� ������� ��������� ��������. �������� ������ � ID = " + mainPerson.getId() + " ����� ������� ��� ����, ��������� � IDs = " + StringUtils.join(persons, "|") + " �������� ��� �������� � ����� � �����."));
		ActivePerson updatedPerson = updateAndMarkDelete(authData, mainPerson, client, persons, department, userVisitingMode);

		log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "������ � ID = " + mainPerson.getId() + " ������� �� ����. ��������� (���� ����) �������� ��� �������� � �������� � ���� ������ ������."));
		return updatedPerson;
	}

	private static Client getClient(Client cedboClient, AuthData authData, List<ActivePerson> persons, AuthentificationSource authentificationSource, String authToken) throws BusinessException, BusinessLogicException
	{
		try
		{
			//���� ����� ��� ������������ CEDBO ��� ��������� ������� � ������ � �� �� ������, �� ������ ��� CEDBO �� �����������
			if (cedboClient != null && persons.isEmpty())
			{
				log.info(LoginInfoHelper.getClientLogInfo(cedboClient, authentificationSource, authToken));
				return cedboClient;
			}

			//����������� CEDBO, ���� ������(�) �� ������(�) ������, ����,
			//���� ������� ���������, � ���� �� � ������ ������� ������������� ������������ ��
			Client client = findClient(authData, persons);

			log.info(LoginInfoHelper.getClientLogInfo(client, authentificationSource, authToken));
			return client;
		}
		catch (InactiveExternalSystemException e)
		{
			//���� ������� ������� ����������,
			//�� ������� ���������� � ����������� ����� ������� ��� ����� ��������
			log.info(e.getMessage());
			// CHG052091 ���� ��������� �������� �������.
			if (CollectionUtils.isNotEmpty(persons))
			{
				throw new StopClientsSynchronizationException(persons, e);
			}

			return new ClientImpl();
		}
	}

	/**
	 *  ����� ������� ��������� ������ � ��������
	 * @param authenticationContext - ������� �������� ��������������
	 * @param authData - ������ ��������������
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
	 * ��������� ������ ��������� ������������� �������
	 * @param login - ����� �������
	 * @param authData - ������ ��������������
	 */
	public static void updateClientLogonData(Login login, AuthData authData)
	{
		// ��������� � ������ ����� ����� (+ � ��� ������������) � ������� ����������� � �� (��. CHG024212)
		securityService.updateClientLastLogonData(login, authData.getPAN(), authData.getCB_CODE(), authData.isMB(), authData.getUserId(), authData.getUserAlias());
	}

	/**
	 * ��������� ������ ��������� ������������� �������
	 * @param login - ����� �������
	 * @param authContext - �������� ��������������
	 */
	public static void updateClientLogonData(Login login, AuthenticationContext authContext)
	{
		// ��������� � ������ ����� ����� (+ � ��� ������������) � ������� ����������� � �� (��. CHG024212)
		securityService.updateClientLastLogonData(login, authContext.getPAN(), authContext.getCB_CODE(), authContext.isMB(), authContext.getUserId(), authContext.getUserAlias());
	}

	/**
	 * ����� ������ ������ �������
	 * @param person �������
	 * @param userVisitingMode ��� ����� �������
	 * @param authentificationSource �������� ����� � ������� (������ ������, ���������, ����������)
	 * @param authToken  ���� �������������� (��� ����)
	 * @param authData ������ ��������������
	 * @param restriction ����������� �� ��������� ��������
	 * @return ������� � ����� �������, ���� ������ ������, ���� ���, �� null.
	 * @throws BusinessException
	 */
	private static Person synchronize(ActivePerson person, AuthData authData, UserVisitingMode userVisitingMode, AuthentificationSource authentificationSource, String authToken, ClientRestriction restriction, Client foundClient) throws BusinessException, BusinessLogicException
	{
		fillTrust(person, authData);

		// ��������� ������� way ��� ��������� � ���� ��������.
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
				// ����������� CEDBO, ����� ���������, �� ������� �� ������ �� ����
				client = foundClient != null ? foundClient : findClient(authData, Collections.singletonList(person));
				log.info(LoginInfoHelper.getClientLogInfo(client, authentificationSource, authToken));
			}

			//���� ������ �� ������� ��������� �� ����, ��������� �������. ��������� ������ ������� ����.
			//��� ���������� ������� �������� ������ ������� �������������� ����, ����� ���������.
			//���� ������ SBOL, � ��������� �� ���� ����������, �� �� � ��������� ���������� ����, �� ������ �� ���������

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
					// ���� ������ ��� ����, �� ������� ������������ ���� �� ��������, �������, ���� ��
					// ���������� ��� � ������ "���������" ��������

					boolean changeCreationType = ConfigFactory.getConfig(CardsConfig.class).isUdboToCard();
					//���� ����� ���������� � ��������� ���� �������� ����, �� ��������� � "����������" �������
					//������ � ���� ������ ������
					if (changeCreationType && !client.getCancellationDate().after(Calendar.getInstance()))
					{
						personImportService.changeCreationType(person, CreationType.CARD, authData.getProfileType());
						updateCSAProfile(authData, person);
						isDegradationUDBOToCard = true;
					}
				}

				Login personLogin = person.getLogin();
				updateLogin(personLogin, userVisitingMode);

				//��������� ������� ��� �� ������ ��������� � ������� ���� �� ������������� �������
				if (isDegradationUDBOToCard)
				{
					log.info(String.format("������ � ID=%s �������� �� ����������. ���� �������� �������� ����: '%s'",
							person.getId(),DateHelper.formatDateToStringWithPoint(client.getCancellationDate())));
					throw new DegradationFromUDBOToCardException(personLogin, client.getCancellationDate());
				}
				return person;
			}
			ActivePerson updatedPerson = updatePersonMultiProfile(person, client, authData.getDocument(), userVisitingMode, authData, authToken, authentificationSource, restriction);

			// ������� ��� �����, � ����� �������� � ��� � ����� �������. ����� ��������� �������� �����������.
			ContextFillHelper.fillContextByPerson(updatedPerson);

			if (!UserVisitingMode.isEmployeeInputMode(userVisitingMode) && authentificationSource != AuthentificationSource.atm_version && !UserVisitingMode.MC_PAYORDER_PAYMENT.equals(userVisitingMode))
			{
				securityService.changeUserId(updatedPerson.getLogin(), authData.getUserId());
			}
			return updatedPerson;
		}
		catch (InactiveExternalSystemException e)
		{
			//���� ������� ������� ����������,
			//�� ������� ���������� � ����������� ����� ������� ��� ����� ��������
			log.info(e.getMessage());
			throw new StopClientSynchronizationException(person, e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� �� �� CB_CODE - 2 ������ �����
	 * @param code - CB_CODE, ��������� �� ��� ��� iPas
	 * @return ��
	 */
	public static String getTBFromCB_CODE(String code)
	{
		if (StringHelper.isEmpty(code))
			return null;

		return code.substring(0, 2);
	}

	/**
	 * ���������, ��� �� ����� ���������� �������� ���� ��������� � ���������� �������� � ����������
	 * ������������. ���� ����, ���������� ���� ������ � �����.
	 * @param persons ������ ������
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
	 * ����� ������� �� ������� �������
	 * @param authData ������ ��������������
	 * @param persons ������ ��������
	 * @return ������. ���� ������ �� ������ - SecurityException
	 * @throws BusinessLogicException, BusinessException
	 */
	public static Client findClient(AuthData authData, List<ActivePerson> persons) throws BusinessLogicException, BusinessException
	{
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		// ���� � ���� ������� ������� �� ������ �� ���, ��������� ������ CEDBO ����������� �� ����,
		// ����� ����� �� ��������� ������ ���
		Client temp = (CollectionUtils.isEmpty(persons)) ? fillClient(authData, (ActivePerson) null) : fillClient(authData, persons.get(0));

		try
		{
			//����� ��������� ������� ���� ��������� ������� ������� �� ����������
			ExternalSystemHelper.check(externalSystemGateService.findByCodeTB(getTBFromCB_CODE(authData.getCB_CODE())));

			// ����������� CEDBO �� ���, ���, � ���� ��������
			Client client = clientService.fillFullInfo(temp);
			// ���� ������ null, ������ ��������� ������ -425, �� ���� � ��� ��� ���������� �� ���� ������,
			// ���� ���� ��� ������������� ���� ������� �������� ����� �� �������� ���� NO_CONTRACT (!client.isUDBO() && client.getCancellationDate() == null) � �������� ��������� com.rssl.iccs.card.request.by.udbo.state
			// ����������� CEDBO �� ������ ����� � CB_CODE (���� ����� ��������)
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
	 * ������������ ������� �� ������ �������������� � �������
	 * @param authData ������ ��������������
	 * @param person   �������
	 * @return ������
	 */
	private static Client fillClient(AuthData authData, ActivePerson person)
	{
		ClientImpl client = new ClientImpl();
		//������ ���� ���-�� �������� ���� ��������
		//todo ENH025292: ������� ������ � CEBDO � ��������� �������� ������
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
					//���� ����� ������. ��� ��� ����������� ���� ������ ������� � �������� WAY.
					// ��� ����, � GFL ����� ������ ������, ��� ��� WAY ���� ����� �� ���� ���������, ������� ��� ����� �� iPAs
					ClientDocumentImpl doc = new ClientDocumentImpl();
					//� �� ������� way �������� � �����. ��. fillClient()
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
				//� ��������� ������� way �������� � ������
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
	 * ���������� ������� �������, ����������� �� CSA (��� iPAS)
	 * ����������� ��, ����� �����.
	 *
	 * @param client ���� �������� ����, ����� ��������� �������� ������ � �������� way
	 * @param authData ������ ��������������
	 * @return ������
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
	 * ��������� ���� �������, ������� ������ �� ������, ������� �� �������� �� CSA/iPas.
	 * �������� PASSPORT_WAY ����������� � updatePerson
	 * @param authData ������ ��������������
	 * @param person ����������� ������
	 * @param authentificationSource ������� ����� � �������
	 * @param userVisitingMode ��� ����� �������
	 * @return ������ ���� null, ���� ���������� ���������� ������������� �� ������ �� CSA/iPas
	 */
	private static Client fillSBOLClient(AuthData authData, ActivePerson person, AuthentificationSource authentificationSource, UserVisitingMode userVisitingMode) throws BusinessLogicException
	{
		try
		{
			//��������� ����� � ����� ���������
			String passportWayNumber = authData.getDocument();
			if (!StringHelper.isEmpty(passportWayNumber) && !UserVisitingMode.isEmployeeInputMode(userVisitingMode))
				PersonHelper.updatePersonPassportWay(person, passportWayNumber);

			ClientImpl client = (ClientImpl) person.asClient();
			client.setBirthDay(getBirthDay(authData));
			client.setFirstName(authData.getFirstName());
			client.setPatrName(authData.getMiddleName());
			client.setSurName(authData.getLastName());

			// ������� ���������������� ���������� �� ID
			client.setId(IDHelper.restoreOriginalId(client.getId()));
			return client;
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);

			//��� ��������� ������ � �.�. � iPhone
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
	 * ��������� ���������� ������� �� ���������, ������ � ������
	 * @param authData ������ ��������������
	 * @param authentificationSource �������� ����� � �������
	 * @return ������ ���� null, ���� ���������� ���������� ������������� �� ������ �� CSA/iPas
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
			//��� ��������� ������ � �.�. � iPhone
			if (authentificationSource.equals(AuthentificationSource.mobile_version))
				throw new BusinessLogicException(e);

			log.error(e.getMessage(), e);
			return null;
		}
		return client;
	}

	/**
	 * �������� ������� ������� �� �������
	 * @param person - �������, ������� ���������� ��������
	 * @param client - ������, ��������� �� ������� �������
	 * @param passportWayNumber - ����� �������� way ������� (null, ���� �� ��������)
	 * @param visitingMode ��� ����� �������
	 * @throws BusinessException, BusinessLogicException
	 */
	public static void updatePerson(ActivePerson person, Client client, String passportWayNumber, UserVisitingMode visitingMode, AuthData authData) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (!StringHelper.isEmpty(passportWayNumber) && !UserVisitingMode.isEmployeeInputMode(visitingMode))
				PersonHelper.updatePersonPassportWay(person, passportWayNumber); // ���������� person � �� ����

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
			// ��� ���� �������� ������ ������ ����� �������� ��� ������������ clientId, ���� �� �������� ���
			// (������� � client.getId()).
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
	 * ���������� ������� � ������ ���������� ������������� ������ �������� ����� ����������
	 * @param person - ������� ������� � �����
	 * @param client - ������ �� ������� �������
	 * @param passportWayNumber - ������� way
	 * @param visitingMode - visiting mode
	 * @param authData - ������ �����
	 * @param authToken - �����
	 * @param authentificationSource - ����
	 * @param restriction - ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private static ActivePerson updatePersonMultiProfile(ActivePerson person, Client client, String passportWayNumber, UserVisitingMode visitingMode, AuthData authData,
	                                             String authToken, AuthentificationSource authentificationSource, ClientRestriction restriction) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (!StringHelper.isEmpty(passportWayNumber) && !UserVisitingMode.isEmployeeInputMode(visitingMode))
				PersonHelper.updatePersonPassportWay(person, passportWayNumber); // ���������� person � �� ����

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

			//����� ���������� �� ��������� �� ������? ���� �� ���+������� way+��+��. ���� ���������-���������� �������� ������.
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
				//������� ������ ������� � CEDBO �������. ������ ���� �������.
				updatedPerson = (ActivePerson) mergeProfiles(client, mergePersons, restriction, authData, authToken, authentificationSource, visitingMode);
			}
			else
			{
				// ��� ���� �������� ������ ������ ����� �������� ��� ������������ clientId, ���� �� �������� ���
				// (������� � client.getId()).
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
	 * ��������� ������ �� �������� �������
	 * @param persons ������ ��������
	 * @param clazz ����� ������
	 * @return ����� ������ - ������ ������ ���� clazz
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
	 * @return ����� �� ���������� �����
	 * @throws BusinessException
	 */
	public static String getEmployeeOfficeRegion() throws BusinessException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		Office office = departmentService.findById(employee.getDepartmentId());
		if (office == null)
			throw new BusinessException("������������� ���������� �� �������, id ������������� = " + employee.getDepartmentId());

		return new SBRFOfficeAdapter(office).getCode().getRegion();
	}

	/**
	 * @param lightScheme ���� �����
	 * @return ���� �����?
	 */
	public static boolean isLigthAppScheme(String lightScheme)
	{
		return Boolean.parseBoolean(lightScheme);
	}

	/**
	 * ��������� ������ ������� � �������
	 * @param client ������, ���������� �� ������� �������
	 * @param authData ������ ��������������
	 * @param authToken ��� �������������� � ����
	 * @param authentificationSource �������� ����� � ������� (������ ������, ���������, ����������)
	 * @param visitingMode ����� ������ �������
	 * @param restriction ����������� �� ��������� ��������
	 * @return �����, ��������� ����� ��� null, ���� �� ������� (�� ��������)
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private static ActivePerson addClient(Client client, AuthData authData, String authToken, AuthentificationSource authentificationSource, UserVisitingMode visitingMode, ClientRestriction restriction) throws BusinessLogicException, BusinessException
	{
		try
		{
			ExtendedDepartment department = null;
			// ������ ����� ���� null, ���� �� CEDBO �� �������� ���������� �� �� ���+���+���� ��������, �� �� �����.
			// ������ ������� ������� ���������, �������� �� ��������� �����
			if (client != null && client.isUDBO())
			{
				//���� ������ ����, ������ ������������� �� ���������� � ����, ������ ������� �� ���������
				department = (ExtendedDepartment) getDBClientDepartment(client);
				fillClient(authData, client);
			}
			else
			{
				//���� �������������, � ������� ������������� ������ ��������� � ������������ �����,
				// ��������� ������� ������� �� ��� (iPAS) � ��������� � ����.
				//� ������ ������� ������� �� ������, ����� ������� ������ ����� � �������������
				//��������� �������� � ���� ��
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
						// ������ ������ �������� ���/OZON, �������� �� ����������� ���� �� �����
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
					//����� �������������(���).
					String agencyId = StringHelper.appendLeadingZeros(((ExtendedCodeImpl)department.getCode()).getBranch(), 4);
					//����� ��������(��).
					String regionId = StringHelper.appendLeadingZeros(((ExtendedCodeImpl)department.getCode()).getRegion(), 2);
					//�������� ������ ��������������� ����������� ��������� �[0-9]{6}�
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
						"��� ������������� � ID = " + department.getId() + " �� ����� ������� ������� �������." +
								" ���������� �������� �������. ", authentificationSource).toString());
			}


			//��������� ����������� ������ �������(���������� � ��������)
			checkRestriction(client, restriction);

			CreationType type = getPersonCreationType(client, null);
			ActivePerson person = personImportService.addOrUpdatePerson(null, client, type, DefaultSchemeType.getDefaultSchemeType(type, authData.getProfileType()), department);
			if (person == null)
				throw new BusinessException("������ �� ��������.");
			if(!UserVisitingMode.isEmployeeInputMode(visitingMode))
			{
				securityService.changeUserId(person.getLogin(), authData.getUserId());
			}	

			fillTrust(person, authData);

			log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, " ������ �������� � �������."));
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
			log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, "�� ������� ��� �� ������������ �� �������������, � �������� �������� ������ (����)"));

			//��� ��������� ������ � �.�. � iPhone
			if (authentificationSource.equals(AuthentificationSource.mobile_version))
				throw new BusinessLogicException(e);

			return null;
		}
		catch (BusinessException e)
		{
			log.info(e);

			//��� ��������� ������ � �.�. � iPhone
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
	 * ����� � ���������� ������������� �������
	 * @param authData ������ ��������������
	 * @param authentificationSource
	 * @return �������������
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
			//��� ��������� ������ � �.�. � iPhone
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
			throw new BusinessLogicException("�������������, � ������� ������������� ������ �� ���������� � �������.");

		return department;
	}

	private static Department getDBClientDepartment(Client client) throws BusinessException
	{
		return personImportService.getClientDepartment(client, getPersonCreationType(client, null));
	}

	/**
	 * ��� �������� ������������
	 * @param client - ������ �� ������� �������
	 * @param person - ������� (�� ����� ��) ����� ���� null
	 * @return ��� �������� (����������������)
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
	 * �������� ������ ������� � ������ ��������� ��������� � �����
	 * @param authData ������ ��������������
	 * @param person ������������ ������� ��� ����������, ���� ���, �� null
	 * @param personsToDelete ������ ��������, ������� ���������� ��������� � �����. ���� ��� �������� ���������� null ��� ������ ������
	 * @param client ������
	 * @param department �������������, � �������� �������� ������
	 * @param userVisitingMode ����� ���������
	 * @return ������������� �����������, ����������� �������.
	 * @throws BusinessException, BusinessLogicException
	 */
	private static ActivePerson updateAndMarkDelete(AuthData authData, ActivePerson person, Client client, List<ActivePerson> personsToDelete, Department department, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		fillTrust(person, authData);

		//��������� ������� �������
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
		log.info("������c��� ��������������� ���������� = " + count);
		return addedPerson;
	}

	/**
	 * ������������� ����� ������������ ���������������� ��� ��������
	 *
	 * @param person ������
	 * @param authData ������ ��������������
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
			log.error("������ ���������� ���. ������ � ��� ��� ������� " + person.getId(), e);
		}
	}

	/**
	 * ������� ��� mAPI � socialApi. ��������� �� �������� ������� ��� �������� �����
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
