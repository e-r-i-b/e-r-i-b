package com.rssl.phizic.asfilial.listener;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.asfilial.listener.generated.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.ASFilialReturnCode;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.AsFilialMigrationResult;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.MigratorASFilial;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.person.ermb.ErmbConfirmPhoneHolderHelper;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

public class ASFilialService extends AsFilialServiceBase
{
	private final static ErmbConfirmPhoneHolderHelper confirmHelper = new ErmbConfirmPhoneHolderHelper();
	private static final ErmbTariffService tariffService = new ErmbTariffService();
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private static final ClientResourcesService service = new ClientResourcesService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ASFilialResponseBuilder responseBuilder = new ASFilialResponseBuilder();
	private final ASFilialServiceHelper asHelper = new ASFilialServiceHelper();
	private final static PersonImportService personImportService = new PersonImportService();
	private static final OfficeCodeReplacer officeCodeReplacer = ConfigFactory.getConfig(OfficeCodeReplacer.class);
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();

	public com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType queryProfile(com.rssl.phizic.asfilial.listener.generated.QueryProfileRqType parameters) throws java.rmi.RemoteException
	{
		asHelper.logSpecVersion();
		//Заполнение ответа.
		QueryProfileRsType result = new QueryProfileRsType();
		result.setRqTm(parameters.getRqTm());
		result.setRqUID(parameters.getRqUID());
		result.setOperUID(parameters.getOperUID());
		StatusType status = new StatusType();
		IdentityType clientInd = parameters.getClientIdentity();
	    BankInfoType bankInfo = parameters.getBankInfo();
	    asHelper.removeTbLeadingZeros(clientInd);
	 	IdentityType clientOldInd = null;
		ErmbProfileImpl ermbProfil = null;
		boolean isError = false;
		boolean isNew = false;
	    Department department = null;
		try
		{
			ActivePerson person = asHelper.findPerson(clientInd,parameters.getClientOldIdentity());

			if (person != null)
			{
				department = departmentService.findById(person.getDepartmentId());
				LogThreadContext.setDepartmentName(department.getFullName());
				//делаем СЕDBO
				Client client =  asHelper.findClient(clientInd);

				if (client != null && client.isUDBO())
				{
					updateOfficeBranch(client.getOffice());
					personImportService.addOrUpdatePerson(person, client, CreationType.UDBO, DefaultSchemeType.UDBO);
				}
				//ищем профиль ЕРМБ
				ermbProfil = profileService.findByUser(person);
				if (ermbProfil == null && !parameters.isCreateIfNone())
				{
                    asHelper.setClientNotFoundMsg(status, clientInd);
					isError = true;
				}
				else if (ermbProfil != null)
					ermbProfil.setPerson(person);
			}
			//Клиент ЕРИБ не найден и пришёл признак создать новый профиль
			else
			{
				if (parameters.isCreateIfNone())
				{
					department = getDeparment(bankInfo);
					if (department != null)
						LogThreadContext.setDepartmentName(department.getName());
					//делаем СЕDBO
					Client client = asHelper.findClient(clientInd);

                    if (department != null)
                        if (client != null)
                        {
    //						раскоментировать только для работы через ЗАГЛУШКУ.
    //						personImportService.addOrUpdatePerson(null, client, CreationType.UDBO, department);
                            CreationType creationType = null;
                            if (client.isUDBO())
                                creationType = CreationType.UDBO;
                            else
                                creationType = CreationType.CARD;

	                        //если в CEDBO пришол офис то используем его
	                        Office clientOffice = client.getOffice();
	                        if (clientOffice != null && clientOffice.getCode() != null)
	                        {
		                        updateOfficeBranch(clientOffice);
		                        Code code = clientOffice.getCode();
		                        Map<String, String> fiels = code.getFields();
		                        Department clientDepartment = departmentService.getDepartment(fiels.get("region"), fiels.get("office"),fiels.get("branch"));
		                        if (clientDepartment != null)
			                        person = personImportService.addOrUpdatePerson(null, client, creationType, DefaultSchemeType.getDefaultSchemeType(creationType), clientDepartment);
		                        else
			                        person = personImportService.addOrUpdatePerson(null, client, creationType, DefaultSchemeType.getDefaultSchemeType(creationType), department);
	                        }
		                    else
                                person = personImportService.addOrUpdatePerson(null, client, creationType, DefaultSchemeType.getDefaultSchemeType(creationType), department);
                        }
                        else
                        {//если CEDBO клиента не нашел, то создаем "руками"
                            person = asHelper.creatPersonOnQueryProfile(department,clientInd);
                        }
                    else
                    {
                        asHelper.setStatus(status, "Создание профиля не возможно, не найден департамент ", ASFilialReturnCode.BUSINES_ERROR);
	                    isError = true;
                    }
				}
				else
				{
                    asHelper.setClientNotFoundMsg(status, clientInd);
					isError = true;
				}
			}
			if (!isError)
			{
				//актуализируем список ресурсов GFL
				if (person.getCreationType() == CreationType.CARD)
					service.updateResources(person, false, Card.class);
				else
					service.updateResources(person, false, Account.class, Card.class, Loan.class);

				//Устаревшие идентификационные данные клиента
				clientOldInd = asHelper.getOldData(person);

				//проверяем есть ли паспорт WAY, если нет то добавляем.
				PersonDocument passportWAY = PersonHelper.getPersonDocument(person, PersonDocumentType.PASSPORT_WAY);
				if (passportWAY == null)
				{
					List<CardLink> cardLinks = externalResourceService.getLinks(person.getLogin(), CardLink.class, null);
					for (CardLink cardLink : cardLinks)
					{
						MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
						UserInfo userInfo = mobileBankService.getClientByCardNumber(cardLink.getNumber());
						if (userInfo != null && StringHelper.isNotEmpty(userInfo.getPassport()))
						{
							passportWAY = new PersonDocumentImpl();
							passportWAY.setDocumentType(PersonDocumentType.PASSPORT_WAY);
							passportWAY.setDocumentSeries(userInfo.getPassport());
							break;
						}
					}
					//если по картам найти не удалось, то преобразуем номер основного документа
					if (passportWAY == null)
					{
						passportWAY = PersonHelper.getOrMakePersonPassportWay(person);
					}
					if (passportWAY != null)
					{
						Set<PersonDocument> personDocuments = person.getPersonDocuments();
						if (personDocuments == null)
							personDocuments = new HashSet<PersonDocument>();
						personDocuments.add(passportWAY);
						person.setPersonDocuments(personDocuments);
						personImportService.addOrUpdatePerson(person, person.asClient(), person.getCreationType(), DefaultSchemeType.getDefaultSchemeType(person.getCreationType()), getDeparment(bankInfo));
					}
				}
				com.rssl.phizic.gate.csa.UserInfo userInfo = asHelper.createCsaUserInfo(clientInd);
				//создаем профиль  CSA если его еще нет. Для случаев когда запрос приходит непрямую(т.е не через ASProxy)
				CSABackRequestHelper.sendFindProfileNodeByUserInfoRq(userInfo, true, CreateProfileNodeMode.CREATION_ALLOWED_FOR_MAIN_NODE);

				boolean infoCardConflict = false;
				Set<String> engagedPhoneNumbers = new HashSet<String>();
				//Признак повторной миграции
				boolean repeatMigration = ermbProfil != null && ermbProfil.isMigrationConflict();
				//Если это сохранение в первый раз, либо если была ошибка миграции при прошлом обращении
				if (ermbProfil == null && parameters.isCreateIfNone() || repeatMigration)
				{
					MigratorASFilial migrator = new MigratorASFilial();
					AsFilialMigrationResult migrationResult = migrator.migrateOnQueryProfile(person);

					ermbProfil = migrationResult.getProfile();
					infoCardConflict = migrationResult.isInfoCardConflict();
					engagedPhoneNumbers = migrationResult.getEngagedPhones();

					if (!repeatMigration)
						isNew = true;
				}
                //заполняем  ответ
				ResponseType responseType = responseBuilder.buildQueryProfileResponse(ermbProfil, clientInd, clientOldInd, isNew);
				result.setResponse(responseType);
				if (ConfigFactory.getConfig(ASFilialConfig.class).isUseV19spec())
				{
					if (CollectionUtils.isNotEmpty(engagedPhoneNumbers))
					{
						result.setEngagedPhones(responseBuilder.buildPhoneArray(engagedPhoneNumbers));
					}
				}

				//статус
				if (CollectionUtils.isNotEmpty(engagedPhoneNumbers))
				{
					asHelper.setStatus(status, ASFilialReturnCode.DUPLICATION_PHONE_ERR);
					String statusDesc = responseBuilder.getPhonesInfoMessage(engagedPhoneNumbers,
							"Один или несколько указанных телефонов зарегистрированы на других лиц, необходимо подтвердить или удалить конфликтные номера.");
					if (infoCardConflict)
					{
						statusDesc += "\nВладельцу дополнительной карты необходимо самостоятельно подключить Мобильный Банк в отделении.";
					}
					status.setStatusDesc(statusDesc);
				}
				else if (infoCardConflict)
				{
					asHelper.setStatus(status, ASFilialReturnCode.MB_HAVE_THIRD_PARTIES_ACCOUNTS);
				}
				else
				{
					asHelper.setStatus(status, ASFilialReturnCode.OK);
				}
			}
		}
		catch (LogicException e)
		{
            asHelper.setStatus(status, e, ASFilialReturnCode.BUSINES_ERROR);
		}
		catch (Exception e)
		{
            asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
		result.setStatus(status);
		return result;
	}

	public com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType updateProfile(com.rssl.phizic.asfilial.listener.generated.UpdateProfileRqType parameters) throws java.rmi.RemoteException
	{
		asHelper.logSpecVersion();

		UpdateProfileRsType response = new UpdateProfileRsType();
		response.setRqTm(parameters.getRqTm());
		response.setRqUID(parameters.getRqUID());
		response.setOperUID(parameters.getOperUID());
		StatusType status = new StatusType();
        IdentityType clientInd = parameters.getClientIdentity();
		asHelper.removeTbLeadingZeros(clientInd);
		ErmbProfileImpl profile = null;
        ActivePerson person = null;
		try
		{
            person = asHelper.findPerson(clientInd);
            if (person != null)
            {
                profile = profileService.findByUser(person);
	            Department department = departmentService.findById(person.getDepartmentId());
	            LogThreadContext.setDepartmentName(department.getFullName());
            }
		}
		catch (BusinessException e)
		{
            asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
        catch (BusinessLogicException e)
        {
            asHelper.setStatus(status, e, ASFilialReturnCode.BUSINES_ERROR);
        }

		//Данные по услуге «Мобильный банк». Если null то не меняется
		MobileBankServiceUpdateProfileRqType mobileBankServiceType = parameters.getMobileBankService();
		//Флажок «услуга подключена».
		boolean regStatus = mobileBankServiceType != null && mobileBankServiceType.isRegistrationStatus();
		//Телефоны клиента.
		ClientPhonesType[] clientPhonesData = parameters.getClientData();

		end:
		if (profile != null)
		{
			ErmbProfileImpl profileBeforeUpdate = null;
			try
			{
				profileBeforeUpdate = ErmbHelper.copyProfile(profile);
			}
			catch (Exception e)
			{
				asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
				break end;
			}
			String oldMainPhone = profile.getMainPhoneNumber();
			Set<String> oldPhones = profile.getPhoneNumbers();
            try
            {
                service.updateResources(person, false, Account.class, Card.class, Loan.class);
            }
            catch (BusinessException e)
            {
                asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
                break end;
            }

            //все телефоны в профиле
            Set<ErmbClientPhone> currentPhones = profile.getPhones();
            //Список телефонных номеров пришедших, которых раньше не было в профиле
            Set<String> newComePhoneNumber =  new HashSet<String>();
			//номера телефона/код подтверждения держателя номера, если кода нет то пустая строка
			Map<String,String> phoneAndCode = new HashMap<String,String>();
            Set<ErmbClientPhone> comePhones = new HashSet<ErmbClientPhone>();
            if (clientPhonesData != null && clientPhonesData.length > 0)
			{
				for(ClientPhonesType clientPhonesType:clientPhonesData)
				{
					PhoneNumberType phoneNumber = clientPhonesType.getPhone();
                    String phoneNumberStr = phoneNumber.getPhoneNumberN();
                    ErmbClientPhone ermbPhone = ErmbHelper.getErmbClientPhone(phoneNumberStr, profile);
                    if (ermbPhone.getId() == null)
                        newComePhoneNumber.add(phoneNumberStr);
                    comePhones.add(ermbPhone);

					phoneAndCode.put(phoneNumberStr,clientPhonesType.getConfirmCode());
				}
                //удаляем старые телефоны
                currentPhones.clear();
                //записываем новые
                currentPhones.addAll(comePhones);
			}
			else if (clientPhonesData != null && clientPhonesData.length == 0)
			{//Пришел пустой тэг, значит удаляем все телефоны клиента
				if (currentPhones.size() != 0)
                    currentPhones.clear();
			}
			//Данные по услуге «Интернет-клиент». Если null то не меняется
			ResourcesType[] internetResources = parameters.getInternetClientService();
			//Данные по услуге «Мобильный клиент». Если null то не меняется
			ResourcesType[] mobileResources = parameters.getMobileClientService();
			//Данные по услуге «Устройства самообслуживания». Если null то не меняется
			ResourcesType[] ATMResources = parameters.getATMClientService();
			Map<String, ErmbProductLink> allLinks = asHelper.getLinks(profile);
			try
			{
				asHelper.updateResources(internetResources, 1, allLinks, false, false);
				asHelper.updateResources(mobileResources, 2, allLinks, false, false);
				asHelper.updateResources(ATMResources, 3, allLinks, false, false);
			}
			catch (BusinessLogicException e)
			{
                asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
				break end;
			}

			if (mobileBankServiceType != null)
			{
				//«Если количество <ClientPhones> = 0, значение поля должно быть равно false
				if (ArrayUtils.isEmpty(clientPhonesData) && regStatus)
				{
					String logMsg = "Логическая ошибка сообщения UpdateProfileRq. При количестве ClientPhones = 0, значение поля RegistrationStatus должно быть равно false";
                    asHelper.setStatus(status, logMsg, ASFilialReturnCode.FORMAT_ERROR);
					break end;
				}
				//Параметры услуги.Если null то услуга отключена.
				ServiceParamsType serviceParamsType = mobileBankServiceType.getServiceParams();
				if (serviceParamsType == null && regStatus)
				{
					String logMsg = "Логическая ошибка сообщения UpdateProfileRq. Не верноя кратность RegistrationStatus и ServiceParams";
                    asHelper.setStatus(status, logMsg, ASFilialReturnCode.FORMAT_ERROR);
					break end;
				}
				else if (!regStatus)
				{//отключение услуги
					profile.setServiceStatus(false);
					// Не выставляем клиентскую блокировку согласно
					// BUG075813: ЕРМБ : АРМ Сотрудника : Блокировка услуги при удалении номера
					// profile.setClientBlocked(true);
					// удаляем все телефоны клиента.
					currentPhones.clear();
					currentPhones.addAll(comePhones);
				}
				else if (serviceParamsType != null && regStatus)
				{//редактирование/подключение услуги
					if (!profile.isServiceStatus())
					{
						profile.setServiceStatus(true);
						if (profile.getConnectionDate() == null)
							profile.setConnectionDate(DateHelper.getCurrentDate());
					}
					//Тарифный план (full, saving)
					String tariff = serviceParamsType.getTariffId();
					ErmbTariff ermbTarif;
					try
					{
						ermbTarif = tariffService.getTariffByCode(tariff);
						if (ermbTarif != null)
							ErmbHelper.updateErmbTariff(profile, ermbTarif);
						else
						{
							String logMsg = "Не найден тариф ЕРМБ с code = " + tariff;
							asHelper.setStatus(status, logMsg, ASFilialReturnCode.BUSINES_ERROR);
							break end;
						}
					}
					catch (BusinessException e)
					{
                        asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
						break end;
					}
					//Признак включения для клиента возможности оплаты чужого телефона и переводов по номеру телефона
					profile.setFastServiceAvailable(serviceParamsType.isQuickServices());
					//Данный телефон является «активным». На него будут отправляться одноразовые пароли и уведомления МБ
					PhoneNumberType activePhone = serviceParamsType.getActivePhone();
					if (activePhone != null && !StringHelper.isEmpty(activePhone.getPhoneNumberN()))
					{
						//Должен входить в общий список телефонов клиента
						if (ArrayUtils.isEmpty(clientPhonesData)||(!phoneAndCode.keySet().contains(activePhone.getPhoneNumberN())))
						{
							String logMsg = "Логическая ошибка сообщения UpdateProfileRq. ActivePhone Отсутствует в списке ClientPhones";
                            asHelper.setStatus(status, logMsg, ASFilialReturnCode.FORMAT_ERROR);
							break end;
						}
						for (ErmbClientPhone phone : currentPhones)
						{
							if (StringHelper.equals(phone.getNumber(), activePhone.getPhoneNumberN()))
								phone.setMain(true);
							else
								phone.setMain(false);
						}
					}
					else
					{
						String logMsg = "У клиента не указан активный телефонный номер.";
						asHelper.setStatus(status, logMsg, ASFilialReturnCode.BUSINES_ERROR);
						break end;
					}
                    //Продукты, доступные в СМС-канале. Должны входить в исходный список продуктов из профиля
                    ResourcesType[] visibleRes = serviceParamsType.getVisibleResources();
                    //Продукты клиента, на которые должны отправляться оповещения
					ResourcesType[] informResources = serviceParamsType.getInformResources();
					try
					{
						ErmbPermissionCalculator permissionCalculator = new ErmbPermissionCalculator(profile);
						asHelper.updateResources(informResources, 4, allLinks, permissionCalculator.impliesCardNotification(), permissionCalculator.impliesAccountNotification());
						asHelper.updateResources(visibleRes, 5, allLinks, false, false);
					}
					catch (BusinessLogicException e)
					{
                        asHelper.setStatus(status, e, ASFilialReturnCode.BUSINES_ERROR);
					}
					//Флажок «отправлять уведомления по вновь добавленному продукту»
					profile.setNewProductNotification(serviceParamsType.isInformNewResource());

					//Номер приоритетной карты для списания абонентской платы.
					String chargeOffCard = serviceParamsType.getChargeOffCard();
					if (StringHelper.isNotEmpty(chargeOffCard))
					{
						CardLink chargeCard = (CardLink) allLinks.get(chargeOffCard);
						profile.setForeginProduct(chargeCard);
					}
					//Временные интервалы, в которые разрешено отправлять уведомления
					DaytimePeriodType daytimePeriod = serviceParamsType.getInformPeriod();
					asHelper.updateNotificationTime(profile, daytimePeriod);
					//Признак запрета рекламных рассылок
					profile.setSuppressAdv(serviceParamsType.isSuppressAdvertising());
					profile.setDepositsTransfer(serviceParamsType.isInformDespositEnrollment());
				}
			}
			//Миграция/Сохранение/Запись в ответ:
			asHelper.saveAndUpdateResponse(profile,
										   profileBeforeUpdate,
									       newComePhoneNumber,
									       phoneAndCode,
										   regStatus,
									   	   allLinks,
										   oldPhones,
										   oldMainPhone,
										   response,
										   clientInd,
										   status);
		}
		else
		{
			String mbkRemoveText = "Для клиента " + clientInd.getFirstName() + " " +  clientInd.getLastName() +  " " + clientInd.getMiddleName() + " удалены связки в МБК";
			if (person == null)
			{
				String logMsg = "По данным клиента анкета в ЕРИБ не найдена.";
				asHelper.setStatus(status, logMsg, ASFilialReturnCode.PROFILE_NOT_FOUND);
				if(mobileBankServiceType != null && !regStatus)
				{
					boolean deleted = asHelper.removeClientPhonesFromMBK(clientInd, status);
					if (deleted)
					{
						asHelper.setStatus(status, mbkRemoveText, ASFilialReturnCode.OK);
					}
				}
			}
			else
		    {
			    String logMsg = "Не найден профиль ЕРМБ по данным клиента";
			    asHelper.setStatus(status, logMsg, ASFilialReturnCode.PROFILE_NOT_FOUND);
			    if (mobileBankServiceType != null && !regStatus)
				    try
				    {
					    boolean deleted = asHelper.removeClientPhonesFromMBK(person.asClient(), status);
					    if (deleted)
					    {
						    asHelper.setStatus(status, mbkRemoveText, ASFilialReturnCode.OK);
					    }
				    }
				    catch (BusinessException e)
				    {
					    asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
				    }
		    }
		}
		response.setStatus(status);
		return  response;
	}

	public com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType confirmPhoneHolder(com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRqType parameters) throws java.rmi.RemoteException {

		ConfirmPhoneHolderRsType response = new ConfirmPhoneHolderRsType();
		response.setRqTm(parameters.getRqTm());
		response.setRqUID(parameters.getRqUID());
		response.setOperUID(parameters.getOperUID());
		StatusType status = new StatusType();
		PhoneNumberType[] phoneNumbers = parameters.getPhones();
		for(PhoneNumberType phone:phoneNumbers)
		{
			try
			{
				String phoneNumber = phone.getPhoneNumberN();
				ASFilialConfig asFilialConfig = ConfigFactory.getConfig(ASFilialConfig.class);
				Calendar expiredDate = Calendar.getInstance();
				expiredDate.add(Calendar.SECOND, asFilialConfig.getHoldeCodeLifeTime());
				confirmHelper.sendConfirmCodeOfflineDoc(phoneNumber,asFilialConfig.getTranslitMode(),expiredDate);

			}
			catch (BusinessException e)
			{
                asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
			}
			response.setStatus(status);
		}
		return response;
    }

	private ExtendedDepartment getDeparment (BankInfoType bankInfo) throws BusinessException
	{
		String branchId = bankInfo.getBranchId();
		String agencyId = bankInfo.getAgencyId();
		String regionId = bankInfo.getRegionId();
		return departmentService.getDepartment(StringHelper.removeLeadingZeros(regionId), StringHelper.removeLeadingZeros(agencyId), StringHelper.removeLeadingZeros(branchId));
	}

	private void updateOfficeBranch(Office office)
	{
		ExtendedCodeImpl officeCode = new ExtendedCodeImpl(office.getCode());
		officeCode.setBranch(officeCodeReplacer.replaceCode(officeCode.getRegion(), officeCode.getBranch()));
		office.setCode(officeCode);
	}
}
