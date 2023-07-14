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
		//���������� ������.
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
				//������ ��DBO
				Client client =  asHelper.findClient(clientInd);

				if (client != null && client.isUDBO())
				{
					updateOfficeBranch(client.getOffice());
					personImportService.addOrUpdatePerson(person, client, CreationType.UDBO, DefaultSchemeType.UDBO);
				}
				//���� ������� ����
				ermbProfil = profileService.findByUser(person);
				if (ermbProfil == null && !parameters.isCreateIfNone())
				{
                    asHelper.setClientNotFoundMsg(status, clientInd);
					isError = true;
				}
				else if (ermbProfil != null)
					ermbProfil.setPerson(person);
			}
			//������ ���� �� ������ � ������ ������� ������� ����� �������
			else
			{
				if (parameters.isCreateIfNone())
				{
					department = getDeparment(bankInfo);
					if (department != null)
						LogThreadContext.setDepartmentName(department.getName());
					//������ ��DBO
					Client client = asHelper.findClient(clientInd);

                    if (department != null)
                        if (client != null)
                        {
    //						���������������� ������ ��� ������ ����� ��������.
    //						personImportService.addOrUpdatePerson(null, client, CreationType.UDBO, department);
                            CreationType creationType = null;
                            if (client.isUDBO())
                                creationType = CreationType.UDBO;
                            else
                                creationType = CreationType.CARD;

	                        //���� � CEDBO ������ ���� �� ���������� ���
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
                        {//���� CEDBO ������� �� �����, �� ������� "������"
                            person = asHelper.creatPersonOnQueryProfile(department,clientInd);
                        }
                    else
                    {
                        asHelper.setStatus(status, "�������� ������� �� ��������, �� ������ ����������� ", ASFilialReturnCode.BUSINES_ERROR);
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
				//������������� ������ �������� GFL
				if (person.getCreationType() == CreationType.CARD)
					service.updateResources(person, false, Card.class);
				else
					service.updateResources(person, false, Account.class, Card.class, Loan.class);

				//���������� ����������������� ������ �������
				clientOldInd = asHelper.getOldData(person);

				//��������� ���� �� ������� WAY, ���� ��� �� ���������.
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
					//���� �� ������ ����� �� �������, �� ����������� ����� ��������� ���������
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
				//������� �������  CSA ���� ��� ��� ���. ��� ������� ����� ������ �������� ��������(�.� �� ����� ASProxy)
				CSABackRequestHelper.sendFindProfileNodeByUserInfoRq(userInfo, true, CreateProfileNodeMode.CREATION_ALLOWED_FOR_MAIN_NODE);

				boolean infoCardConflict = false;
				Set<String> engagedPhoneNumbers = new HashSet<String>();
				//������� ��������� ��������
				boolean repeatMigration = ermbProfil != null && ermbProfil.isMigrationConflict();
				//���� ��� ���������� � ������ ���, ���� ���� ���� ������ �������� ��� ������� ���������
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
                //���������  �����
				ResponseType responseType = responseBuilder.buildQueryProfileResponse(ermbProfil, clientInd, clientOldInd, isNew);
				result.setResponse(responseType);
				if (ConfigFactory.getConfig(ASFilialConfig.class).isUseV19spec())
				{
					if (CollectionUtils.isNotEmpty(engagedPhoneNumbers))
					{
						result.setEngagedPhones(responseBuilder.buildPhoneArray(engagedPhoneNumbers));
					}
				}

				//������
				if (CollectionUtils.isNotEmpty(engagedPhoneNumbers))
				{
					asHelper.setStatus(status, ASFilialReturnCode.DUPLICATION_PHONE_ERR);
					String statusDesc = responseBuilder.getPhonesInfoMessage(engagedPhoneNumbers,
							"���� ��� ��������� ��������� ��������� ���������������� �� ������ ���, ���������� ����������� ��� ������� ����������� ������.");
					if (infoCardConflict)
					{
						statusDesc += "\n��������� �������������� ����� ���������� �������������� ���������� ��������� ���� � ���������.";
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

		//������ �� ������ ���������� ����. ���� null �� �� ��������
		MobileBankServiceUpdateProfileRqType mobileBankServiceType = parameters.getMobileBankService();
		//������ ������� ����������.
		boolean regStatus = mobileBankServiceType != null && mobileBankServiceType.isRegistrationStatus();
		//�������� �������.
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

            //��� �������� � �������
            Set<ErmbClientPhone> currentPhones = profile.getPhones();
            //������ ���������� ������� ���������, ������� ������ �� ���� � �������
            Set<String> newComePhoneNumber =  new HashSet<String>();
			//������ ��������/��� ������������� ��������� ������, ���� ���� ��� �� ������ ������
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
                //������� ������ ��������
                currentPhones.clear();
                //���������� �����
                currentPhones.addAll(comePhones);
			}
			else if (clientPhonesData != null && clientPhonesData.length == 0)
			{//������ ������ ���, ������ ������� ��� �������� �������
				if (currentPhones.size() != 0)
                    currentPhones.clear();
			}
			//������ �� ������ ���������-������. ���� null �� �� ��������
			ResourcesType[] internetResources = parameters.getInternetClientService();
			//������ �� ������ ���������� ������. ���� null �� �� ��������
			ResourcesType[] mobileResources = parameters.getMobileClientService();
			//������ �� ������ ����������� �����������������. ���� null �� �� ��������
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
				//����� ���������� <ClientPhones> = 0, �������� ���� ������ ���� ����� false
				if (ArrayUtils.isEmpty(clientPhonesData) && regStatus)
				{
					String logMsg = "���������� ������ ��������� UpdateProfileRq. ��� ���������� ClientPhones = 0, �������� ���� RegistrationStatus ������ ���� ����� false";
                    asHelper.setStatus(status, logMsg, ASFilialReturnCode.FORMAT_ERROR);
					break end;
				}
				//��������� ������.���� null �� ������ ���������.
				ServiceParamsType serviceParamsType = mobileBankServiceType.getServiceParams();
				if (serviceParamsType == null && regStatus)
				{
					String logMsg = "���������� ������ ��������� UpdateProfileRq. �� ������ ��������� RegistrationStatus � ServiceParams";
                    asHelper.setStatus(status, logMsg, ASFilialReturnCode.FORMAT_ERROR);
					break end;
				}
				else if (!regStatus)
				{//���������� ������
					profile.setServiceStatus(false);
					// �� ���������� ���������� ���������� ��������
					// BUG075813: ���� : ��� ���������� : ���������� ������ ��� �������� ������
					// profile.setClientBlocked(true);
					// ������� ��� �������� �������.
					currentPhones.clear();
					currentPhones.addAll(comePhones);
				}
				else if (serviceParamsType != null && regStatus)
				{//��������������/����������� ������
					if (!profile.isServiceStatus())
					{
						profile.setServiceStatus(true);
						if (profile.getConnectionDate() == null)
							profile.setConnectionDate(DateHelper.getCurrentDate());
					}
					//�������� ���� (full, saving)
					String tariff = serviceParamsType.getTariffId();
					ErmbTariff ermbTarif;
					try
					{
						ermbTarif = tariffService.getTariffByCode(tariff);
						if (ermbTarif != null)
							ErmbHelper.updateErmbTariff(profile, ermbTarif);
						else
						{
							String logMsg = "�� ������ ����� ���� � code = " + tariff;
							asHelper.setStatus(status, logMsg, ASFilialReturnCode.BUSINES_ERROR);
							break end;
						}
					}
					catch (BusinessException e)
					{
                        asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
						break end;
					}
					//������� ��������� ��� ������� ����������� ������ ������ �������� � ��������� �� ������ ��������
					profile.setFastServiceAvailable(serviceParamsType.isQuickServices());
					//������ ������� �������� ���������. �� ���� ����� ������������ ����������� ������ � ����������� ��
					PhoneNumberType activePhone = serviceParamsType.getActivePhone();
					if (activePhone != null && !StringHelper.isEmpty(activePhone.getPhoneNumberN()))
					{
						//������ ������� � ����� ������ ��������� �������
						if (ArrayUtils.isEmpty(clientPhonesData)||(!phoneAndCode.keySet().contains(activePhone.getPhoneNumberN())))
						{
							String logMsg = "���������� ������ ��������� UpdateProfileRq. ActivePhone ����������� � ������ ClientPhones";
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
						String logMsg = "� ������� �� ������ �������� ���������� �����.";
						asHelper.setStatus(status, logMsg, ASFilialReturnCode.BUSINES_ERROR);
						break end;
					}
                    //��������, ��������� � ���-������. ������ ������� � �������� ������ ��������� �� �������
                    ResourcesType[] visibleRes = serviceParamsType.getVisibleResources();
                    //�������� �������, �� ������� ������ ������������ ����������
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
					//������ ����������� ����������� �� ����� ������������ ��������
					profile.setNewProductNotification(serviceParamsType.isInformNewResource());

					//����� ������������ ����� ��� �������� ����������� �����.
					String chargeOffCard = serviceParamsType.getChargeOffCard();
					if (StringHelper.isNotEmpty(chargeOffCard))
					{
						CardLink chargeCard = (CardLink) allLinks.get(chargeOffCard);
						profile.setForeginProduct(chargeCard);
					}
					//��������� ���������, � ������� ��������� ���������� �����������
					DaytimePeriodType daytimePeriod = serviceParamsType.getInformPeriod();
					asHelper.updateNotificationTime(profile, daytimePeriod);
					//������� ������� ��������� ��������
					profile.setSuppressAdv(serviceParamsType.isSuppressAdvertising());
					profile.setDepositsTransfer(serviceParamsType.isInformDespositEnrollment());
				}
			}
			//��������/����������/������ � �����:
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
			String mbkRemoveText = "��� ������� " + clientInd.getFirstName() + " " +  clientInd.getLastName() +  " " + clientInd.getMiddleName() + " ������� ������ � ���";
			if (person == null)
			{
				String logMsg = "�� ������ ������� ������ � ���� �� �������.";
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
			    String logMsg = "�� ������ ������� ���� �� ������ �������";
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
