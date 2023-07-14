package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.ermb.profile.ErmbPersonListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbPersonEvent;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.persons.oldIdentity.DuplicationIdentityException;
import com.rssl.phizic.business.persons.oldIdentity.PersonIdentityService;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.ChangeUserTypeLogDataWrapper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.util.*;

public class PersonImportService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PersonService personService = new PersonService();
	private static final AdditionalClientsService additionalClientsService = new AdditionalClientsService();
	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();
	private final static DepartmentService departmentService = new DepartmentService();
	private final static SubscriptionService subscriptionService = new SubscriptionService();
	private static final SimpleService simpleService = new SimpleService();
	private static final PersonIdentityService personIdentityService = new PersonIdentityService();

	private static final String SEPARATOR = "|";

	private static final PersonDocumentTypeComparator DOCUMENT_COMPARATOR = new PersonDocumentTypeComparator();

	/**
	 * Получить департамент, в котором обслуживается клиент
	 * @param client - клиент
	 * @param creationType - тип созлания клиента
	 * @return Подразделение в котором обслуживается клиент.
	 * null если у клиента нет офиса, офис не корректный, подразделения по такому коду не найдено или подразделение не обслуживается в системе
	 * @throws BusinessException
	 */
	public Department getClientDepartment(Client client, CreationType creationType) throws BusinessException
	{
		DepartmentService depatrmentService = new DepartmentService();

		Office clientOffice = client.getOffice();
		if (clientOffice == null || clientOffice.getCode() == null)
			throw new BusinessException("Не задан офис обслуживания клиента");
		Department department = depatrmentService.findByCode(clientOffice.getCode());
		if (department == null)
			throw new DepartmentNotFoundException("Подразделение, в котором обслуживается клиент не подключено к системе.");
		if (!department.isService() && creationType != CreationType.CARD)
			throw new BusinessException("Подразделение, в котором обслуживается клиент не обслуживается в системе");
		if (department instanceof ExtendedDepartment && !((ExtendedDepartment) department).isEsbSupported() && creationType == CreationType.UDBO)
			throw new ClientDepartmentNotAvailableException("Подразделение, в котором обслуживается УДБО клиент c ID = " + client.getId() + " не подключено к БП.");
		return department;
	}

	/**
	 * @param person - существующая персона для обновления, если нет, то null
	 * @param client - клиент
	 * @param creationType - тип подключения клиента
	 * @return добавленного или обновленного клиента
	 * @throws BusinessException, BusinessLogicException
	 */
	public ActivePerson addOrUpdatePerson(ActivePerson person, Client client, CreationType creationType, DefaultSchemeType type) throws BusinessException, BusinessLogicException
	{
		return addOrUpdatePerson(person, client, creationType, type, getClientDepartment(client, creationType));
	}

	/**
	 * Обновить тип подключения клиента
	 * @param person персона для обновления
	 * @param profileType персона для обновления
	 * @param newCreationType новый тип подключения
	 */
	@Transactional
	public void changeCreationType(ActivePerson person, CreationType newCreationType, ProfileType profileType) throws BusinessException
	{
		person.setCreationType(newCreationType);
		if (newCreationType == CreationType.CARD)
			personService.updateAndRemoveLinks(person);
		else
			personService.update(person);

		Department personDepartment = departmentService.findById(person.getDepartmentId());
		DefaultSchemeType defaultSchemeType = DefaultSchemeType.getDefaultSchemeType(newCreationType, profileType);
		schemeOwnService.setClientDefaultSchemes(person.getLogin(), defaultSchemeType, getDepartmentRegion(defaultSchemeType, personDepartment));
	}

	/**
	 * Добавление или обновление клиента
	 * @param client - клиент
	 * @param person - существующая персона для обновления, если нет, то null
	 * @param creationType - тип подключения клиента
	 * @param department - подразделение, к которому привязан клиент
	 * @return добавленного или обновленного клиента
	 * @throws BusinessException, BusinessLogicException
	 */
	public ActivePerson addOrUpdatePerson(ActivePerson person, Client client, CreationType creationType, DefaultSchemeType type, Department department) throws BusinessException, BusinessLogicException
	{
		String clientId = client.getId();

		//При добавлении клиента необходимо выяснить к какому реально подразделению он привязан,
		// для ситуаций, когда необходимо идти не в шину
		String UUID = department.getAdapterUUID();
		if (!clientId.contains(UUID))
			clientId += SEPARATOR + UUID;

		//Если персоны не существует в системе, добавляем её
		if (person == null)
		{
			return createPerson(client, clientId, department, client.getAgreementNumber(), null, creationType, type);
		}
		return personUpdate(person, client, creationType, type, clientId, department);
	}

	/**
	 * Обновление клиента
	 * @param client - клиент
	 * @param person - существующая персона для обновления, если нет, то null
	 * @param creationType - тип подключения клиента
	 * @param department - подразделение, к которому привязан клиент
	 * @return добавленного или обновленного клиента
	 * @throws BusinessException, BusinessLogicException
	 */
	private ActivePerson personUpdate(final ActivePerson person, final Client client, final CreationType creationType, final DefaultSchemeType type, final String clientId, final Department department) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ErmbPersonEvent personEvent = new ErmbPersonEvent(person.getId());
					personEvent.setOldPerson(person);

					//если изменился тип клиента или клиент вошёл в резервный блок, то меняем схему прав доступа
					if(person.getCreationType() != creationType || type == DefaultSchemeType.CARD_TEMPORARY || type == DefaultSchemeType.UDBO_TEMPORARY)
					{
						schemeOwnService.setClientDefaultSchemes(person.getLogin(),
								type, getDepartmentRegion(type, department));
					}

					List<String> accessTypes = new ArrayList<String>();
					accessTypes.add(AccessType.simple.getKey());
					accessTypes.add(AccessType.mobileLimited.getKey());
					List<String> clientAccessTypes = schemeOwnService.findClientSchemeOwnTypes(person.getLogin(), accessTypes);
					accessTypes.removeAll(clientAccessTypes);

					for (String accessType : accessTypes)
					{
						schemeOwnService.setClientDefaultSchemesParams(person.getLogin(), type, getDepartmentRegion(type, department), AccessType.valueOf(accessType));
					}

					//Если персона есть, её необходимо обновить.
					//При обновлении персоны нельзя обновлять идентификатор, т.к. в существующем зашит UUID адаптера
					PersonalSubscriptionData subscriptionData = subscriptionService.findPersonalData(person.getLogin());
					if (subscriptionData == null) {
						subscriptionData = new PersonalSubscriptionData();
						subscriptionData.setLogin(person.getLogin());
					}
					if (isPersonChanged(person, client))
					{
						try
						{
							simpleService.add(person.getPersonKey());

							PersonDocument document = PersonHelper.getMainPersonDocument(person);
							PersonOldIdentity identity = PersonHelper.rememberCurrentIdentity(person, document, null);
							personIdentityService.addOrUpdate(identity);
						}
						catch (DuplicationIdentityException ignore)
						{
						}
					}
					updatePersonData(person, subscriptionData, client, clientId, department.getId(), creationType, null);
					personService.update(person);
					UserDocumentService.get().resetUserDocument(person.getLogin(), DocumentType.INN, client.getINN());
					subscriptionService.changePersonalData(subscriptionData);
					personEvent.setNewPerson(person);
					ErmbPersonListener ermbChangesListener = ErmbUpdateListener.getListener();
					ermbChangesListener.onPersonUpdate(personEvent);
					return null;
				}
			});
			UserPropertiesConfig.processUserSettingsWithoutPersonContext(person.getLogin(), new SettingsProcessor<Void>()
			{
				public Void onExecute(UserPropertiesConfig userProperties)
				{
					userProperties.setUseOfert(true);
					return null;
				}
			});
		}
		catch (BusinessLogicException ble)
		{
			throw ble;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		return person;
	}

	private boolean isPersonChanged(ActivePerson person, Client client)
	{
		if (!person.getFirstName().equals(client.getFirstName()))
			return true;
		if (!person.getSurName().equals(client.getSurName()))
			return true;
		if (!StringHelper.equalsNullIgnore(person.getPatrName(), client.getPatrName()))
			return true;
		if (person.getBirthDay().compareTo(client.getBirthDay()) != 0)
			return true;

		List<PersonDocument> personDocuments = new ArrayList<PersonDocument>(person.getPersonDocuments());
		Collections.sort(personDocuments, new PersonDocumentTypeComparator());
		PersonDocument personDocument = personDocuments.get(0);
		String personDocumentString = (StringHelper.getEmptyIfNull(personDocument.getDocumentSeries()) + StringHelper.getEmptyIfNull(personDocument.getDocumentNumber())).replaceAll(" ", "");

		List<? extends ClientDocument> clientDocuments = client.getDocuments();
		Collections.sort(clientDocuments, new DocumentTypeComparator());
		ClientDocument clientDocument = clientDocuments.get(0);
		String clientDocumentString = (StringHelper.getEmptyIfNull(clientDocument.getDocSeries()) + StringHelper.getEmptyIfNull(clientDocument.getDocNumber())).replaceAll(" ", "");

		if (!StringHelper.equalsNullIgnore(personDocumentString, clientDocumentString))
			return true;

		return false;
	}

	/**
	 * Создание новой персоны или обновление существующей.
	 * @param person - существующая персона для обновления, если хотим создать новую передаем null
	 * @param client - клиент по которому создаем или обновляем персону
	 * @param clientId - идентификатор клиента
	 * @param departmentId - идентификатор департамента, к которому привязан клиент
	 * @param creationType - тип подключения персоны
	 * @return Новая или обновлённая персона
	 */
	private ActivePerson updatePersonData(ActivePerson person, PersonalSubscriptionData subscriptionData, Client client, String clientId, Long departmentId, CreationType creationType, String agreementNumber) throws BusinessException
	{
		PersonCreateConfig flowConfig = ConfigFactory.getConfig(PersonCreateConfig.class);
		if (person == null)
		{
			person = new ActivePerson();
			ChangeUserTypeLogDataWrapper.writeAddingClientLogData(creationType.name(), departmentId);
		}
		else if (creationType != person.getCreationType())
		{
			ChangeUserTypeLogDataWrapper.writeUpdateClientLogData(person.getCreationType().name(), creationType.name(), departmentId);
		}

		person.setStatus(Person.ACTIVE);
		person.setClientId(clientId);

		person.setSurName(client.getSurName());
		person.setFirstName(client.getFirstName());
		person.setPatrName(client.getPatrName());

		person.setBirthDay(client.getBirthDay());
		person.setBirthPlace(client.getBirthPlace());
		// Шина не возвращает признак нерезидента, ставим по умолчанию true для УДБО и карточных клиентов, BUG041238
		person.setIsResident(creationType != CreationType.SBOL || client.isResident());
	    person.setPersonDocuments(addOrUpdatePersonDocuments(person, client.getDocuments(), creationType));

		person.setRegistrationAddress(addOrUpdatePersonRegistrationAddress(person, client.getLegalAddress()));
		person.setResidenceAddress(addOrUpdatePersonResidenceAddress(person, client.getRealAddress()));
		person.setGender(client.getSex());

		person.setDepartmentId(departmentId);

		person.setAgreementNumber(StringHelper.isEmpty(agreementNumber) ? client.getAgreementNumber() : agreementNumber);
		if (client.getInsertionDate() != null)
			person.setAgreementDate(client.getInsertionDate());

		if (StringHelper.isNotEmpty(client.getJobPhone()))
			person.setJobPhone(client.getJobPhone());
		if (StringHelper.isNotEmpty(client.getHomePhone()))
			person.setHomePhone(client.getHomePhone());
		subscriptionData.setMobilePhone(client.getMobilePhone());
		if (StringHelper.isNotEmpty(client.getEmail()))
			subscriptionData.setEmailAddress(client.getEmail());
		person.setCitizenship(client.getCitizenship());
		person.setDisplayClientId(client.getDisplayId());
		person.setCreationType(creationType);
		person.setSegmentCodeType(client.getSegmentCodeType() != null ? client.getSegmentCodeType() : SegmentCodeType.NOTEXISTS);
		TariffPlanConfig tariff = TariffPlanHelper.getActualTariffPlanByCode(client.getTarifPlanCodeType());
		if (tariff != null)
		{
			person.setTarifPlanCodeType(client.getTarifPlanCodeType());
			person.setTarifPlanConnectionDate(client.getTarifPlanConnectionDate());
		}
		else
		{
			person.setTarifPlanCodeType(TariffPlanHelper.getUnknownTariffPlanCode());
			person.setTarifPlanConnectionDate(null);
		}
		person.setManagerId(client.getManagerId());
		person.setManagerTB(client.getManagerTB());
		person.setManagerOSB(client.getManagerOSB());
		person.setManagerVSP(client.getManagerVSP());
		return person;
	}

	/**
	 * Создать Person на из Client
	 * @param client клиент
	 * @param department подразделение клиента
	 * @return созданный Person
	 * @throws BusinessException
	 * @throws BusinessLogicException - попытка добавить уже добавленного пользователя.
	 */
	public ActivePerson createPerson(final Client client, String clientId, final Department department, String agreementNumber, final Set<String> clientIds, CreationType creationType, final DefaultSchemeType type) throws BusinessException, BusinessLogicException
	{
		ClientDataValidator validator = new ClientDataValidator(client);
		if (!validator.validate())
		{
			List<String> errors = validator.getErrors();
			StringBuilder buffer = new StringBuilder();
			buffer.append("Импортировать клиента не удалось. Устраните следующие проблемы и повторите попытку:");
			for (String error : errors)
			{
				buffer.append("&#10;");
				buffer.append(error);
			}
			throw new BusinessLogicException(buffer.toString());
		}
		final PersonalSubscriptionData subscriptionData = new PersonalSubscriptionData();
		final ActivePerson person = updatePersonData(null, subscriptionData, client, clientId, department.getId(), creationType, agreementNumber);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					String clientId  = additionalClientsService.getClientIdByAdditionalId(client.getId());
					ActivePerson temp = clientId==null ? findByClientId(client.getId()) : findByClientId(clientId);
					if (temp != null)
						throw new BusinessLogicException("Клиент банка " + client.getFullName() + " уже добавлен в систему. Номер договора:" + temp.getAgreementNumber() + ".");

					//Если заполнены дополнительные id'ки, значит дополнительная необходима провека
					if (clientIds!=null && !clientIds.isEmpty() && checkAttributeEquals(client, clientIds))
						additionalClientsService.createClientIdsConnection(client, clientIds);

					personService.createLogin(person);
					personService.add(person);
					UserDocumentService.get().resetUserDocument(person.getLogin(), DocumentType.INN, client.getINN());
					subscriptionData.setLogin(person.getLogin());
					subscriptionService.changePersonalData(subscriptionData);

					schemeOwnService.setClientDefaultSchemes(person.getLogin(),
							type, getDepartmentRegion(type, department));

					return null;
				}
			});
			UserPropertiesConfig.processUserSettingsWithoutPersonContext(person.getLogin(), new SettingsProcessor<Void>()
			{
				public Void onExecute(UserPropertiesConfig userProperties)
				{
					userProperties.setUseOfert(true);
					return null;
				}
			});
		}
		catch (BusinessLogicException ble)
		{
			throw ble;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		return person;
	}

	private void addOrUpdateAddress(com.rssl.phizic.person.Address address, Address gateAddress)
	{
		address.setPostalCode(gateAddress.getPostalCode());
		address.setProvince(gateAddress.getProvince());
		address.setDistrict(gateAddress.getDistrict());
		address.setCity(gateAddress.getCity());
		address.setStreet(gateAddress.getStreet());
		address.setHouse(gateAddress.getHouse());
		address.setBuilding(gateAddress.getBuilding());
		address.setFlat(gateAddress.getFlat());
		address.setUnparseableAddress(gateAddress.getUnparseableAddress());
	}

	private com.rssl.phizic.person.Address addOrUpdatePersonRegistrationAddress(ActivePerson person, Address legalAddress)
	{
		com.rssl.phizic.person.Address address = (person != null && person.getRegistrationAddress() != null) ?
				person.getRegistrationAddress() : new com.rssl.phizic.person.Address();

		if (legalAddress != null)
			addOrUpdateAddress(address, legalAddress);

		return address;
	}

	private com.rssl.phizic.person.Address addOrUpdatePersonResidenceAddress(ActivePerson person, Address realAddress)
	{
		com.rssl.phizic.person.Address address = (person != null && person.getResidenceAddress() != null) ?
				person.getResidenceAddress() : new com.rssl.phizic.person.Address();

		if (realAddress != null)
			addOrUpdateAddress(address, realAddress);

		return address;
	}

	private Set<PersonDocument> addOrUpdatePersonDocuments(ActivePerson person, List<? extends ClientDocument> clientDocuments, CreationType creationType)
	{
		Set<PersonDocument> personDocuments = (person != null && person.getPersonDocuments() != null)?person.getPersonDocuments():new HashSet<PersonDocument>();
		if (clientDocuments != null)
		{
			PersonDocument mainDocument = null;
			boolean isCEDBODocument = creationType == CreationType.UDBO;
			for(ClientDocument clientDocument : clientDocuments)
			{
				//Если один тип документа, то надо обновлять имеющийся, а не создавать новый
				PersonDocument personDocument = addOrUpdatePersonDocumentByType(personDocuments, clientDocument);
				if (isCEDBODocument)
					mainDocument = getMain(mainDocument, personDocument);
				personDocuments.add(personDocument);
			}

			for (PersonDocument personDocument : personDocuments)
			{
				personDocument.setDocumentMain(false);
				if (!isCEDBODocument)
					mainDocument = getMain(mainDocument, personDocument);
			}
			mainDocument.setDocumentMain(true);
		}
		return personDocuments;
	}

	private PersonDocument getMain(PersonDocument first, PersonDocument second)
	{
		return first == null || DOCUMENT_COMPARATOR.compare(first, second) > 0 ? second: first;
	}

	private PersonDocument addOrUpdatePersonDocumentByType(Set<PersonDocument> personDocuments, ClientDocument clientDocument)
	{
		if (personDocuments == null || personDocuments.isEmpty())
			return new PersonDocumentImpl(clientDocument);
		String documentType = clientDocument.getDocumentType().toString();
		for (PersonDocument personDocument : personDocuments)
		{
			if (documentType.equals(personDocument.getDocumentType().toString()))
			{
				updatePersonDocumentFromGate(personDocument, clientDocument);
				return personDocument;
			}
		}
		return new PersonDocumentImpl(clientDocument);
	}

	/**
	 * Обновление документа данными пришедшими из гейта.
	 * @param personDocument - персон-документ
	 * @param clientDocument - гейтовый документ
	 */
	private void updatePersonDocumentFromGate(PersonDocument personDocument, ClientDocument clientDocument)
	{
		personDocument.setDocumentName(clientDocument.getDocTypeName());
		personDocument.setDocumentNumber(clientDocument.getDocNumber());
        personDocument.setDocumentSeries(clientDocument.getDocSeries());
		personDocument.setDocumentIssueDate(clientDocument.getDocIssueDate());
		personDocument.setDocumentIssueBy(clientDocument.getDocIssueBy());
		personDocument.setDocumentIssueByCode(clientDocument.getDocIssueByCode());
		personDocument.setDocumentTimeUpDate(clientDocument.getDocTimeUpDate());
		personDocument.setDocumentIdentify(clientDocument.isDocIdentify());

	}

	public ActivePerson findByClientId(String clientId) throws BusinessException
	{
		return personService.findByClientId(clientId);
	}

	private boolean checkAttributeEquals( Client client, Set<String> clientIds) throws BusinessLogicException, BusinessException
	{
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		try
		{
			for (String clientId: clientIds)
			{
				Client additionalCient = clientService.getClientById(clientId);
				if (!additionalCient.getSurName().toLowerCase().equals(client.getSurName().toLowerCase()) ||
						!additionalCient.getFirstName().toLowerCase().equals(client.getFirstName().toLowerCase()) ||
						!additionalCient.getPatrName().toLowerCase().equals(client.getPatrName().toLowerCase()) ||
						!additionalCient.getBirthDay().equals(client.getBirthDay()))
				{
					throw new BusinessLogicException("Фамилия, Имя, Отчество и дата рождения всех импортируемых клиентов должны совпадать.");
				}
			}
		}
		catch (GateLogicException e)
		{
		   throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
		   throw new BusinessException(e);
		}

		return true;
	}

	private String getDepartmentRegion(DefaultSchemeType type, Department department) throws BusinessException
	{
		if (type == DefaultSchemeType.CARD || type == DefaultSchemeType.CARD_TEMPORARY || type == DefaultSchemeType.UDBO_TEMPORARY || type == DefaultSchemeType.GUEST)
			return null;
		return department.getRegion();
	}
}
