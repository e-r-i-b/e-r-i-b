package com.rssl.phizic.asfilial.listener;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.asfilial.listener.generated.*;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.AgreementNumberCreatorHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.migration.CodWayErmbConnectionSender;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.ASFilialReturnCode;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.business.ermb.profile.ErmbProfileListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.oldIdentity.PersonIdentityService;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.operations.person.ermb.ErmbConfirmPhoneHolderHelper;
import com.rssl.phizic.person.Address;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.*;
import com.rssl.phizicgate.mobilebank.MobileBankRegistrationHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * User: moshenko
 * Date: 21.01.2013
 * Time: 10:17:47
 * ����� ��������, ��� ������������� � ASFilialInfoServiceSoapBindingImpl
 */
public class ASFilialServiceHelper extends ASFilialServiceBaseHelper
{
	private static final String CARD = "card";
	private static final String ACCOUNT = "account";
	private static final String LOAN = "loan";
	private static final PersonIdentityService personIdentityService = new PersonIdentityService();
	private final static ErmbConfirmPhoneHolderHelper confirmHelper = new ErmbConfirmPhoneHolderHelper();
    private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
    private static final PersonService personService = new PersonService();
    private final static ErmbClientSearchHelper clientSearchHelper = new ErmbClientSearchHelper();
	private static final ASFilialResponseBuilder responseBuilder = new ASFilialResponseBuilder();
	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();
	private final ClientProductsService productService = GateSingleton.getFactory().service(ClientProductsService.class);
	private final JDBCActionExecutor executor = new JDBCActionExecutor("jdbc/MobileBank", com.rssl.phizic.logging.messaging.System.jdbc);
	private MobileBankRegistrationHelper registrationHelper =  new MobileBankRegistrationHelper(executor);
	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();

	/**
	 * @param newPerson ����� ������
	 * @param clientInd ��������� ����������������� ������ 
	 */
	public void updateNewPersonData(ActivePerson newPerson, IdentityType clientInd)
	{
		newPerson.setFirstName(clientInd.getFirstName());
		newPerson.setSurName(clientInd.getLastName());
		newPerson.setPatrName(clientInd.getMiddleName());
		newPerson.setBirthDay(DateHelper.toCalendar(clientInd.getBirthday()));

		IdentityCardType docInd = clientInd.getIdentityCard();

		PersonDocument document = new PersonDocumentImpl();
		document.setDocumentIdentify(true);
		document.setDocumentMain(true);
		document.setDocumentIssueBy(docInd.getIssuedBy());
		document.setDocumentIssueDate(DateHelper.toCalendar(docInd.getIssueDt()));
		ClientDocumentType clientDocumentType = PassportTypeWrapper.getClientDocumentType(docInd.getIdType());
		document.setDocumentType(PersonDocumentType.valueOf(clientDocumentType));
		document.setDocumentNumber(docInd.getIdNum());
		document.setDocumentSeries(docInd.getIdSeries());
		Set<PersonDocument> personDocuments = new HashSet<PersonDocument>();
		personDocuments.add(document);
		newPerson.setPersonDocuments(personDocuments);
	}

	/**
	 * @param resArr ��������� �������
	 * @param method ����� ����� ����� �������� � ������
	 * 1 - ����������� � ��������-����������
	 * 2 - ����������� � ��������� ����������
	 * 3 - ����������� � ����������� ����������������
	 * 4 - ������� �������� ����������
	 * 5 - ����������� � CMC ������
	 */
	public void updateResources(ResourcesType[] resArr, int method, Map<String, ErmbProductLink> links, boolean tariffAllowsCardSmsNotification, boolean tariffAllowsAccountSmsNotification) throws BusinessLogicException
	{
		//1. ���� null �� �������� �� ���������
		if (resArr == null)
			return;
		//2.������������� ��� �������� � false
		Collection<ErmbProductLink> resLinks = links.values();
		for (ErmbProductLink link : resLinks)
		{
			updateLink(link, method, false, tariffAllowsCardSmsNotification, tariffAllowsAccountSmsNotification);
		}

		Collection<String> numbers = links.keySet();
		for (ResourcesType res : resArr)
		{
			//3. ���� ������ ���������� ������� ��� � ������� �� ������ ������
			String type = res.getType();
			String number = null;
			if (StringHelper.equals(type, CARD))
				number = res.getCard().getNumber();
			else if (StringHelper.equals(type, ACCOUNT))
				number = res.getAccount();
			else if (StringHelper.equals(type, LOAN))
				number = res.getCredit();

			if (!numbers.contains(number))
				throw new BusinessLogicException("������ � ������� " + number + " �� ������ � �������");
			//4. ��������� ������ � ����� �������
			ErmbProductLink curLink = links.get(number);
			updateLink(curLink, method, true, tariffAllowsCardSmsNotification, tariffAllowsAccountSmsNotification);
            curLink.setName(res.getName());
		}
	}

	private void updateLink(ErmbProductLink link, int method, boolean value, boolean tariffAllowsCardSmsNotification, boolean tariffAllowsAccountSmsNotification)
	{
		switch (method)
		{
			case 1:
				link.setShowInSystem(value);
				break;
			case 2:
				link.setShowInMobile(value);
				break;
			case 3:
				link.setShowInATM(value);
				break;
			case 4:
			{
				if (link instanceof CardLink)
				{
					if (tariffAllowsCardSmsNotification)
						link.setErmbNotification(value);
					else
						link.setErmbNotification(false);
				}
				else if (link instanceof AccountLink)
				{
					if (tariffAllowsAccountSmsNotification)
						link.setErmbNotification(value);
					else
						link.setErmbNotification(false);
				}
				else if (link instanceof LoanLink)
				{
					if (ErmbHelper.isLoanSmsNotificationAvailable())
						link.setErmbNotification(value);
					else
						link.setErmbNotification(false);
				}
				else
				{
					throw new IllegalArgumentException("���� �� ����������� ��� ��������");
				}
				break;
			}
            case 5:
                link.setShowInSms(value);
		}
	}

	/**
	 * @param person �������
	 * @return ������ ������
	 */
	public IdentityType getOldData(Person person) throws BusinessException
	{
		List<PersonOldIdentity> pesonOldIdentities = personIdentityService.getListByProfile(person);
		if (pesonOldIdentities.isEmpty())
			return null;
		//���� ������ �� ������
		PersonOldIdentity personOldIdentity = pesonOldIdentities.get(0);
		IdentityType oldIdentity = new IdentityType();
		oldIdentity.setFirstName(personOldIdentity.getFirstName());
		oldIdentity.setLastName(personOldIdentity.getSurName());
		oldIdentity.setMiddleName(personOldIdentity.getPatrName());
		oldIdentity.setBirthday(DateHelper.toDate(personOldIdentity.getBirthDay()));
		IdentityCardType identityCardType = new IdentityCardType();
		identityCardType.setIdNum(personOldIdentity.getDocNumber());
		identityCardType.setIdSeries(personOldIdentity.getDocSeries());
		PersonDocumentType oldType = personOldIdentity.getDocType();
		String passportType = null;
		if (oldType != null)
			passportType = PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(oldType));
		identityCardType.setIdType(passportType);
		identityCardType.setIssuedBy(personOldIdentity.getDocIssueBy());
		Calendar issueDate = personOldIdentity.getDocIssueDate();
		if (issueDate != null)
			identityCardType.setIssueDt(DateHelper.toDate(issueDate));
		oldIdentity.setIdentityCard(identityCardType);
		oldIdentity.setRegionId(personOldIdentity.getRegion());
		return oldIdentity;
	}

	/**
	 * @param ermbProfile ������� ����
	 * @return ("����������", "ErmbProductLink")
	 */
	public Map<String, ErmbProductLink> getLinks(ErmbProfileImpl ermbProfile)
	{
		Map<String, ErmbProductLink> links = new HashMap<String, ErmbProductLink>();
		for (CardLink card : ermbProfile.getCardLinks())
			links.put(card.getNumber(), card);
		for (AccountLink account : ermbProfile.getAccountLinks())
			links.put(account.getNumber(), account);
		for (LoanLink loan : ermbProfile.getLoanLinks())
			links.put(loan.getNumber(), loan);
		return links;
	}

	/**
	 * ��������� ������� ����������� � ������� �����������
	 * @param ermbProfile ������� ����
	 * @param daytimePeriod ���������� � ������� ����������� 
	 */
	public void updateNotificationTime(ErmbProfileImpl ermbProfile, DaytimePeriodType daytimePeriod)
	{
		if (ermbProfile == null)
			return;

		if (daytimePeriod != null)
		{
			String[] days = daytimePeriod.getDay();
			if (days == null || days.length == 0)
				ermbProfile.setDaysOfWeek(new DaysOfWeek());
			else
				ermbProfile.setDaysOfWeek(new DaysOfWeek(days, true));

			ermbProfile.setTimeZone(daytimePeriod.getTimeZone());
			String startTime = StringUtils.left(daytimePeriod.getBegin().toString(), 8);
			String endTime = StringUtils.left(daytimePeriod.getEnd().toString(), 8);
			ermbProfile.setNotificationStartTime(java.sql.Time.valueOf(startTime));
			ermbProfile.setNotificationEndTime(java.sql.Time.valueOf(endTime));
		}
		else
		{
			//�������� �� ���������
			updateDefaultNotificationTime(ermbProfile);
		}
	}

    /**
     * ��������� ���� �� ����� ����������, ����������� ������ ����, �� ��� ������
     * �.�. ����� ��������� � ��� �� �������� �� ���������� � ������ ���� �������
     * @param mbkCards ������ ����������� ���� �� ���
     * @param allCards ������ �������� �������
     * @return ���� ���� �� true.
     */
    public boolean isMbkEngaged(Set<String> mbkCards, Set<String> allCards) throws BusinessException
    {
	    for (String cardNumber : mbkCards)
	    {
		    if (!allCards.contains(cardNumber))
			    return true;
	    }
	    return false;
    }

    /**
     * �� ���������� ������ ��������� ������� �������, ������� �������
     * @param mbkMbkConnectionInfos ��������� �������� ���
     * @param profile ������� ����
     */
    void updateProfileMbkData (List<MbkConnectionInfo> mbkMbkConnectionInfos, ErmbProfileImpl profile)
    {
        //TODO �������� ��� ������ ������ ����� ������������� ����� ��������.
    }

    /**
     * ��������� ��� ������ � ����� ����������
     * @param profile �������
     * @param linklist �����
     * @param mbkTemplates ������� ��������� �� ���
     * @param mbkPhoneOffers ������ ��������� �� ���
     * @param phoneAndCodeMap �������� �������� � ������ �������������
     * @return true ���� ���������� ������� ������ �������
     * @throws BusinessException
     */
    public Boolean updateProfileData(final ErmbProfileImpl profile, final List<ErmbProductLink> linklist,final List<MobileBankTemplate> mbkTemplates,final List<String> mbkPhoneOffers,final Map<String,String> phoneAndCodeMap,final StatusType status) throws BusinessException, BusinessLogicException
    {

        //��������� ��� �������� �� ������� ������� confrimCode
        for(Map.Entry<String, String> phoneAndCode: phoneAndCodeMap.entrySet())
        {
            String phoneNumber = phoneAndCode.getKey();
            String confirmCode = phoneAndCode.getValue();
            if (!StringHelper.isEmpty(confirmCode) &&
	            confirmHelper.testSwapPhoneNumberCodeOfflineDoc(confirmCode, phoneNumber))
            {
                ErmbProfileImpl ermbProfile = ErmbHelper.getErmbProfileByPhone(phoneNumber);
                if (ermbProfile != null)
                    try
                    {
                        confirmHelper.removeSwapClientPhone(phoneNumber,confirmCode);
                    }
                    catch (ErmbPhoneNotFoundException e)
                    {
                        setStatus(status,e,ASFilialReturnCode.TECHNICAL_ERROR);
                        return false;
                    }
                    catch (SecurityLogicException e)
                    {
                        setStatus(status,e,ASFilialReturnCode.CONFIRM_HOLDER_ERR,phoneNumber);
                        return false;
                    }
            }
        } ;

        //����������� ��������� ���������� � ����������� �������(�.� � ������� ������� ��� ��� � �������.)
        profileService.updateProfileAndLink(profile,linklist);

        if (CollectionUtils.isNotEmpty(mbkTemplates))
        {
            saveMbkTemplates(profile, mbkTemplates);
        }
        if (CollectionUtils.isNotEmpty(mbkPhoneOffers))
        {
            savePhoneOffers(profile, mbkPhoneOffers, log);
        }

        return true;
    }

    /**
     * ��������� ������� � ������ NOT_CONNECTED, ������� ����������� ��������
     * @param profile ������� ����.
     * @param status ���������� � ���������� �������.
     */
    public void rollbackErmbProfile(final ErmbProfileImpl profile,StatusType status)
    {
        //������� ��������� � ��������� NOT_CONNECTED
        profile.setServiceStatus(false);
        //�������� �������.
        profile.getPhones().clear();
        try
        {
            profileService.addOrUpdate(profile);
        }
        catch (BusinessException e)
        {
            setStatus(status,e,ASFilialReturnCode.TECHNICAL_ERROR);
        }
    }

    /**
     * ����� ������� � ����
     * @param clientInd ����������������� ������ �������
     * @return ������� � ����
     * @throws BusinessLogicException
     * @throws BusinessException
     */
    public ActivePerson findPerson(IdentityType clientInd) throws BusinessLogicException, BusinessException
    {
        String patrName = StringHelper.getNullIfEmpty(clientInd.getMiddleName());
        String surName = clientInd.getLastName();
        String firstName = clientInd.getFirstName();
        IdentityCardType docInd = clientInd.getIdentityCard();
        String docSeries = StringHelper.getNullIfEmpty(docInd.getIdSeries());
        String docNumber = docInd.getIdNum();
        String tb = clientInd.getRegionId();
        Calendar birthDate = DateHelper.toCalendar(clientInd.getBirthday());

        return personService.getByFIOAndDocUnique(surName,
                    firstName,
                    patrName,
                    docSeries,
                    docNumber,
                    birthDate,
                    tb);
    }

	/**
	 * @param clientIdentity ����������������� ������ �������
	 * @param oldClientIdentities ������ ����������������� ������ �������
	 * @return ActivePerson ���� null
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public ActivePerson findPerson(IdentityType clientIdentity, IdentityType[] oldClientIdentities) throws BusinessException, BusinessLogicException
	{
		ActivePerson activePerson = findPerson(clientIdentity);
		if (activePerson != null)
			return activePerson;

		if (oldClientIdentities != null)
			for (IdentityType identityType:oldClientIdentities)
			{
				String surName = identityType.getLastName();
				String name = identityType.getFirstName();
				String patrName = identityType.getMiddleName();
				IdentityCardType doc = identityType.getIdentityCard();
				String docSeries = StringHelper.getNullIfEmpty(doc.getIdSeries());
				String docNumber = doc.getIdNum();
				Calendar birthDate = DateHelper.toCalendar(identityType.getBirthday());
				PersonOldIdentity  oldIdentity = personIdentityService.getByFIOAndDoc(
						surName,
						name,
						patrName,
						docSeries,
						docNumber,
						birthDate);

				if (oldIdentity != null)
				{
					Person person = oldIdentity.getPerson();
					if (person instanceof  ActivePerson)
						return (ActivePerson)person;
				}
			}
			 return null;
	}

    /**
     * ����� ������� � ����
     * @param clientInd  ����������������� ������ �������
     * @return ������ �� ����
     * @throws BusinessLogicException
     * @throws BusinessException
     */
    public Client findClient(IdentityType clientInd) throws BusinessLogicException, BusinessException
    {
        String patrName = StringHelper.getNullIfEmpty(clientInd.getMiddleName());
        String surName = clientInd.getLastName();
        String firstName = clientInd.getFirstName();
        IdentityCardType docInd = clientInd.getIdentityCard();
        String docSeries = StringHelper.getNullIfEmpty(docInd.getIdSeries());
        String docNumber = docInd.getIdNum();
        String docType = docInd.getIdType();
        String tb = clientInd.getRegionId();
        Calendar birthDate = DateHelper.toCalendar(clientInd.getBirthday());

        return clientSearchHelper.findClient(firstName,
                                             surName,
                                             patrName,
                                             birthDate,
                                             docSeries,
                                             docNumber,
                                             PassportTypeWrapper.getClientDocumentType(docType),
                                             tb);
    }

	private List<String> getCardNumbers(Client client) throws BusinessException
	{
		GroupResult<Class, List<Pair<Object, AdditionalProductData>>>  products = productService.getClientProducts(client, Card.class);
		// GFL �� ������ �����, ��� ������ ������
		if (products == null || products.getKeys().isEmpty() || products.getResults().isEmpty())
			return Collections.emptyList();
		List<Pair<Object, AdditionalProductData>> pairs = null;
		try
		{
			pairs = GroupResultHelper.getResult(products, Card.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		List<String> result = new LinkedList<String>();
		for (Pair<Object, AdditionalProductData> pair : pairs)
			result.add(((Card) pair.getFirst()).getNumber());
		return result;
	}

	/**
	 * ���� �������� ������� ������������������ � ���, � ���� ����� ���� �� ������� ��.
	 * @param clientInd ����������������� ������ �������
	 * @param status C�����
	 * @return �������� �� ��� ���������
	 */
	protected boolean removeClientPhonesFromMBK(IdentityType clientInd, StatusType status)
	{
		ClientImpl fakeClient = new ClientImpl();
		fakeClient.setSurName(clientInd.getLastName());
		fakeClient.setFirstName(clientInd.getFirstName());
		fakeClient.setPatrName(clientInd.getMiddleName());
		IdentityCardType identityCardType = clientInd.getIdentityCard();
		ClientDocumentType documentType = PassportTypeWrapper.getClientDocumentType(identityCardType.getIdType());
		List<ClientDocument> documents = new ArrayList<ClientDocument>(1);
		documents.add(new ClientDocumentImpl(identityCardType.getIdSeries(), identityCardType.getIdNum(), documentType));
		fakeClient.setDocuments(documents);
		fakeClient.setBirthDay(DateHelper.toCalendar(clientInd.getBirthday()));
		fakeClient.setOffice(new OfficeImpl(new CodeImpl(Collections.singletonMap("region",clientInd.getRegionId()))));
		fakeClient.setInternalOwnerId(-1l);
		return removeClientPhonesFromMBK(fakeClient,status);
	}

	/**
	 * ���� �������� ������� ������������������ � ���, � ���� ����� ���� �� ������� ��.
	 * @param client ������
	 * @param status C�����
	 * @return �������� �� ��� ���������
	 */
	public boolean removeClientPhonesFromMBK(Client client,StatusType status)
	{
		try
		{
			List<String> cardNumbers = getCardNumbers(client);
			Set<String> mbkPhones = (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp()) ?
					GateSingleton.getFactory().service(MobileBankService.class).getRegPhonesByCardNumbers(cardNumbers, GetRegistrationMode.SOLID) :
					registrationHelper.getRegPhonesByCardNumbers(cardNumbers, GetRegistrationMode.SOLID);

			if (!mbkPhones.isEmpty())
			{
				Set<String> formateMbkPhones = new HashSet<String>(mbkPhones.size());
				for (String phone: mbkPhones)
				{
					PhoneNumber phoneNumber = PhoneNumber.fromString(phone);
					formateMbkPhones.add(PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phoneNumber));
				}
				MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);

				log.info("������� ������� �������� �������:" + client.getFirstName() +" " +  client.getSurName() +  " " + client.getPatrName() + " �� ���");
				BeginMigrationResult result = mobileBankService.beginMigrationErmb(null, formateMbkPhones, null, MigrationType.PILOT);
				if (result != null && result.getMigrationId() != null)
				{
					mobileBankService.commitMigrationErmb(result.getMigrationId());
					return true;
				}
			}
		}
		catch (GateException e)
		{
			setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
		catch (BusinessException e)
		{
			setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
		catch (GateLogicException e)
		{
			setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
		catch (SystemException e)
		{
			setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
		return false;
	}

	/**
	 * ���������� ���������  � ������� + �������� + ��������� ���������� ������ (updateProfile)
	 * @param profile  �������� �����������  �������
	 * @param profileBeforeUpdate ������ �������
	 * @param newComePhoneNumber ����� ���������� ������ (������� ��� ��� � �������)
	 * @param phoneAndCode ��� ���������� ������ � ���� � ���
	 * @param regStatus ������ ������� ����������.
	 * @param allLinks ��� �����
	 * @param oldPhones ������ ���������� ������ �������
	 * @param oldMainPhone ������ �������� �����
	 * @param response �����
	 * @param clientInd �����������������  ������ �������
	 * @param status ����� ������.
	 */
	public void saveAndUpdateResponse(ErmbProfileImpl profile,
	                                  ErmbProfileImpl profileBeforeUpdate,
									  Set<String> newComePhoneNumber,
									  Map<String,String> phoneAndCode,
									  boolean regStatus,
									  Map<String, ErmbProductLink> allLinks,
									  Set<String> oldPhones,
									  String oldMainPhone,
									  UpdateProfileRsType response,
									  IdentityType clientInd,
									  StatusType status)
	{
		DepoMobileBankConfig mbvConfig = ConfigFactory.getConfig(DepoMobileBankConfig.class);
		//������ ������� �� ���������� updateProfile
		boolean profileOldServiceStatus = profile.isServiceStatus();
		ActivePerson person = (ActivePerson)profile.getPerson();
		List<CardLink> cardLinks = profile.getCardLinks();
		//��������/����������:
		try
		{
			//������ ���� ��� ����� ��������� � MBK, MBV
			boolean needMbkAndMbvTest = ( profileOldServiceStatus|| !newComePhoneNumber.isEmpty()) && (regStatus);
			//�������� ������� ���� � ���
			Set<String> mbkPhones = new HashSet<String>();
			//�������� ������� ���� � ���
			Set<String> mbvPhones = new HashSet<String>();
			//��� ������� ��������
			Set<String> engagedPhones = new HashSet<String>();
			//�������� � �������� ����� �������������
			Set<String> wrongConfirmCodePhones = new HashSet<String>();
			//������ ������ ������� � ���
			MbvClientIdentity mbvClientIdentity = null;
			//C����� ����������� ���� �� ���
			Set<String> mbkCards = new HashSet<String>();
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			DepoMobileBankService mbvService = GateSingleton.getFactory().service(DepoMobileBankService.class);
			ErmbConfig ermbConfig = ConfigFactory.getConfig(ErmbConfig.class);
			if (!ermbConfig.isMigrationOnTheFlyUse())
			{
				log.warn("UPDATE_PROFILE: �������� ������ �� ���� �� ���/��� ���������");
			}
			//���������� ��� ����� ��������
			for (String phone:newComePhoneNumber)
			{
				//���� ����� ��������� � ������� ������� � ���� � � ���� �� ������� confirmCode
				//�� ��������� ��� �  ��� <EngagedPhones>.
				if (ErmbHelper.isErmbEngagedPhones(phone,profile))
				{
					String code = phoneAndCode.get(phone);
					if (StringHelper.isEmpty(phoneAndCode.get(phone)))
					{
						log.info("UPDATE_PROFILE: ������� ��� ���� � ���� ��� ������� ������. ��� ������������� �� ������. " + phone);
						engagedPhones.add(phone);
					}
					else
					{
						log.info("UPDATE_PROFILE: ������� ��� ���� � ���� ��� ������� ������. ��� ������������� ������. " + phone);
						if (!testSwapPhoneNumberCode(phone, code))
						{
							wrongConfirmCodePhones.add(phone);
						}
					}
				}
				if (needMbkAndMbvTest)   //
				{
					//���� ����� � ��� �� ����������� ��������
					Set<String> mbkCurrentPhoneCards = mobileBankService.getCardsByPhone(phone);
					//���� ���� ����������� � ����������� �������� ����� � ���
					if (!mbkCurrentPhoneCards.isEmpty())
					{
						Boolean mbkEngaget = isMbkEngaged(mbkCurrentPhoneCards, allLinks.keySet());
						if (mbkEngaget)
						{
							String code = phoneAndCode.get(phone);
							//���� ��� ������ �������� ��  ������� �onfirmCode, �� ��������� � ��� <EngagedPhones>.
							if (StringHelper.isEmpty(phoneAndCode.get(phone)))
							{
								log.info("UPDATE_PROFILE: ������� ��� �������� � ������� ������� ���. ��� ������������� �� ������. " + phone);
								engagedPhones.add(phone);
							}
							else
							{
								log.info("UPDATE_PROFILE: ������� ��� �������� � ������� ������� ���. ��� ������������� ������. " + phone);
								if (!testSwapPhoneNumberCode(phone, code))
								{
									wrongConfirmCodePhones.add(phone);
								}
							}
						}
						//���� ���� ������� ������������ � ���
						mbkPhones.add(phone);
						mbkCards.addAll(mbkCurrentPhoneCards);
					}
					//���� ����� � MBV.
					//������� ���� ��������  � ���.
					if (mbvConfig.isMbvAvaliability())
					{
						List<MbvClientIdentity> mbvClientIdentityList = mbvService.checkPhoneOwn(phone);
						if (!mbvClientIdentityList.isEmpty())
						{
							for (MbvClientIdentity identity :mbvClientIdentityList)
							{
								//���� ���� ��� ������.
								if (MigrationHelper.isSameClient(identity, person.asClient()))
								{
									mbvClientIdentity = identity;
								}
								else
								{
									String code = phoneAndCode.get(phone);
									//���� �� ������� confirmCode.
									if (StringHelper.isEmpty(phoneAndCode.get(phone)))
									{
										log.info("UPDATE_PROFILE: ������� ��� �������� � ������� ������� ���. ��� ������������� �� ������. " + phone);
										engagedPhones.add(phone);
									}
									else
									{
										log.info("UPDATE_PROFILE: ������� ��� �������� � ������� ������� ���. ��� ������������� ������. " + phone);
										if (!testSwapPhoneNumberCode(phone, code))
										{
											wrongConfirmCodePhones.add(phone);
										}
									}
								}
								mbvPhones.add(phone);
							}
						}
					}
				}
			}

			//����� � ��� ����������� �� ����������� ������ ���� (������ ��������� �����)
			Set<String> allMbkPhones = new HashSet<String>();
			if (ermbConfig.isMigrationOnTheFlyUse())
			{
				String[] param = CardLinkHelper.listCardNumbers(cardLinks).toArray(new String[cardLinks.size()]);
				if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
				{
					List<MobileBankRegistration> registrations = mobileBankService.getRegistrationsPack(false, param);
					addPhones(allMbkPhones, registrations);
				}
				else
				{
					GroupResult<String, List<MobileBankRegistration>> registrationsByCard = mobileBankService.getRegistrations(false, param);
					GroupResultHelper.checkAndThrowAnyException(registrationsByCard);
					for (List<MobileBankRegistration> registrations : GroupResultHelper.getResults(registrationsByCard))
					{
						addPhones(allMbkPhones, registrations);
					}
				}

				//�������� ��������� �� �������� ������ ��� �������� (����� ��� ��� ������ � ������� �������), �.�. ��� ����� ���������� ��� ��������
				Collection<String> otherMbkPhones = CollectionUtils.subtract(allMbkPhones, newComePhoneNumber);
				for (String phone : otherMbkPhones)
				{
					Set<String> mbkCurrentPhoneCards = mobileBankService.getCardsByPhone(phone);
					if (isMbkEngaged(mbkCurrentPhoneCards, allLinks.keySet()))
					{
						log.info("UPDATE_PROFILE: ������� ��� �������� � ������� ������� ���. � ������� ������� �� ������. " + phone);
						engagedPhones.add(phone);
					}
				}
			}

			if (CollectionUtils.isNotEmpty(wrongConfirmCodePhones))
			{
				status.setStatusCode(ASFilialReturnCode.CONFIRM_HOLDER_ERR.toValue());
				status.setStatusDesc(responseBuilder.getPhonesInfoMessage(wrongConfirmCodePhones, "��� ������ ��� ������ �������� ��� ������������� ��������� ������"));
			}
			else if (CollectionUtils.isNotEmpty(engagedPhones))
			{
				status.setStatusCode(ASFilialReturnCode.DUPLICATION_PHONE_ERR.toValue());
				status.setStatusDesc(responseBuilder.getPhonesInfoMessage(engagedPhones, "�������� ���������������� �� ������ ���"));
				response.setEngagedPhones(responseBuilder.buildPhoneArray(engagedPhones));
			}
			else
			{
				//������ �� �������� �� ���
				List<MbkConnectionInfo> mbkMbkConnectionInfos = new LinkedList<MbkConnectionInfo>();
				//Id �������� ���
				Long mbkMigrationId = null;
				com.rssl.phizic.common.types.UUID mbvMigrationId = null;
				//������� �� ���
				List<MobileBankTemplate> mbkTemplates = null;
				//������� �� ���
				List<String> mbkPoneOffers = null;
				try
				{
					if (ermbConfig.isMigrationOnTheFlyUse())
					{
						//��������� �������� ���
						//1. ���� �������� � ��� �� ������ gfl - �������� (���� � ������ ������� ���������)
						//2. ���� ����� �������� � ���, �� ������� ������ ������������� - ��������
						if(!allMbkPhones.isEmpty() || !mbkPhones.isEmpty())
						{
							Set<MbkCard> mbkMigrationCards = MigrationHelper.getCardsToMigrate(cardLinks);

							BeginMigrationResult result = mobileBankService.beginMigrationErmb(mbkMigrationCards, mbkPhones, null, MigrationType.PILOT);
							mbkMbkConnectionInfos = result.getMbkConnectionInfo();
							mbkMigrationId = result.getMigrationId();
							if (mbkMigrationId == null)
								log.error("UPDATE_PROFILE: �� ������� ������������������� ������������ ����������");
						}

						//���� ���� ����� � ���, � ����� ��� ���� ����� ������ �������, ��������� �������� ���
						if (mbvConfig.isMbvAvaliability())
							if(mbvClientIdentity != null)
								mbvMigrationId = mbvService.beginMigration(mbvClientIdentity);
					}
					//C�������� ������ �� �������,������, � ���������
					List<ErmbProductLink> linklist = new ArrayList<ErmbProductLink>(allLinks.values());
					boolean  isSuccessUpdate = false;
					try
					{
						if (!mbkMbkConnectionInfos.isEmpty())
						{
							updateProfileMbkData(mbkMbkConnectionInfos, profile);
							//���������� ���������� �������� � �������:
							for(MbkConnectionInfo resEntity: mbkMbkConnectionInfos)
							{
								//��������
								List<MobileBankTemplate> currentMbkTemplaes = resEntity.getTemplates();
								if (currentMbkTemplaes != null && !currentMbkTemplaes.isEmpty())
								{
									if (mbkTemplates == null)
										mbkTemplates = currentMbkTemplaes;
									else
										//���������� ����� ������ ��������
										mbkTemplates.addAll(currentMbkTemplaes);
								}
								//������
								List<String> currentMbkPhoneOffers = resEntity.getPhoneOffers();
								if (currentMbkPhoneOffers != null && !currentMbkPhoneOffers.isEmpty())
								{
									if (mbkPoneOffers == null)
										mbkPoneOffers = currentMbkPhoneOffers;
									else
										//���������� ����� ������ ������
										mbkPoneOffers.addAll(currentMbkPhoneOffers);
								}
							}
						}

						//��������� ������� ����, � �������� ���������� �� ��������� �������
						ErmbProfileListener profileListener = ErmbUpdateListener.getListener();
						ErmbProfileEvent profileEvent = new ErmbProfileEvent(profileBeforeUpdate);
						profileEvent.setNewProfile(profile);
						profileListener.beforeProfileUpdate(profileEvent);

						isSuccessUpdate = updateProfileData(profile,linklist,mbkTemplates,mbkPoneOffers,phoneAndCode,status);
						profileListener.afterProfileUpdate(profileEvent);

						//��������� ����������� ��� ���������� - ���������� ������� �������
						if (profileBeforeUpdate.isServiceStatus() ^ profile.isServiceStatus())
						{
							CodWayErmbConnectionSender sender = new CodWayErmbConnectionSender();
							if (profile.isServiceStatus())
							{
								sender.sendErmbConnected(profile);
							}
							else
							{
								sender.sendErmbDisconnected(profile);
							}
						}
					}
					catch(BusinessException e)
					{
						//���� ������� ������ �� ���������� ��������(���� ����)
						setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
						if (ermbConfig.isMigrationOnTheFlyUse())
						{
							if (mbkMigrationId != null)
								mobileBankService.rollbackMigration(mbkMigrationId);
							if (mbvConfig.isMbvAvaliability())
								if(mbvMigrationId != null)
									mbvService.rollbackMigration(mbvMigrationId);
						}
					}
					if (isSuccessUpdate)
					{
						try
						{
							//���� ���� �������� � ���� ��������� ����������, �� ��������� ������� ������������� �������� ���
							if (ermbConfig.isMigrationOnTheFlyUse())
							{
								if (mbkMigrationId != null)
									mobileBankService.commitMigrationErmb(mbkMigrationId);
								if (mbvConfig.isMbvAvaliability())
									if(mbvMigrationId != null)
										mbvService.commitMigration(mbvMigrationId);
							}
						    ////////////////
							//����������  CSA
							Set<String> newPhones = profile.getPhoneNumbers();
							boolean phonesChanged = !CollectionUtils.isEqualCollection(oldPhones, newPhones);
							boolean mainPhoneChanged = !StringUtils.equals(oldMainPhone, profile.getMainPhoneNumber());
							//���� �������� ����������, ���������� ��� � �������� ������� ����-��������� � PHIZ_PROXY_MBK
							if (phonesChanged || mainPhoneChanged)
							{
								String newMainPhoneNumber = mainPhoneChanged ? profile.getMainPhoneNumber() : null;
								List<String> addedPhones = new ArrayList<String>(newPhones);
								addedPhones.removeAll(oldPhones);
								List<String> removedPhones = new ArrayList<String>(oldPhones);
								removedPhones.removeAll(newPhones);

								com.rssl.phizic.gate.csa.UserInfo userInfo = createCsaUserInfo(clientInd);
								CSABackRequestHelper.sendUpdatePhoneRegRemoveDuplicateRq(newMainPhoneNumber, userInfo, addedPhones, removedPhones);

								if (phonesChanged)
								{
									ermbPhoneService.saveOrUpdateERMBPhones(addedPhones, removedPhones);
								}
							}
						}
						catch (Exception ex)
						{
							setStatus(status, ex, ASFilialReturnCode.TECHNICAL_ERROR);
							rollbackErmbProfile(profile, status);
							//����� ������ ����� ��������
							if (ermbConfig.isMigrationOnTheFlyUse())
							{
								if (mbkMigrationId != null)
									mobileBankService.rollbackMigration(mbkMigrationId);
								if (mbvConfig.isMbvAvaliability())
									if(mbvMigrationId != null)
										mbvService.rollbackMigration(mbvMigrationId);
							}
						}
					}
					else
					{
						if (ermbConfig.isMigrationOnTheFlyUse())
						{
							if (mbkMigrationId != null)
								mobileBankService.rollbackMigration(mbkMigrationId);
							if (mbvConfig.isMbvAvaliability())
								if(mbvMigrationId != null)
									mbvService.rollbackMigration(mbvMigrationId);
						}
					}
				}
				catch (GateException e)
				{
					setStatus(status, e, ASFilialReturnCode.BUSINES_ERROR);
				}
				catch (BusinessLogicException e)
				{
					setStatus(status, e, ASFilialReturnCode.BUSINES_ERROR);
				}
			}
		}
		catch (IKFLException e)
		{
			setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
		catch (RuntimeException e)
		{
			setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
		}
	}

	private void addPhones(Set<String> allMbkPhones, List<MobileBankRegistration> registrations)
	{
		for (MobileBankRegistration registration : registrations)
		{
			PhoneNumber phoneNumber = PhoneNumber.fromString(registration.getMainCardInfo().getMobilePhoneNumber());
			allMbkPhones.add(PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phoneNumber));
		}
	}

	//��� ����������  true, ����� false
	private boolean testSwapPhoneNumberCode(String phoneNumber, String confirmCode) throws BusinessException
	{
		return confirmHelper.testSwapPhoneNumberCodeOfflineDoc(confirmCode, phoneNumber);
	}

	/**
	 * ������� �������  �� ����������������� ������.
	 * @param department �����������
	 * @param clientInd ����������������� ������ �������
	 * @return
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	protected ActivePerson creatPersonOnQueryProfile(Department department,IdentityType clientInd) throws BusinessLogicException, BusinessException
	{
		ActivePerson person = new ActivePerson();
		String clientId = PersonHelper.generateClientId(department);
		person.setDepartmentId(department.getId());
		String agreementNumber = AgreementNumberCreatorHelper.getNextAgreementNumber(department);
		person.setAgreementNumber(agreementNumber);
		person.setClientId(clientId);
		person.setStatus(ActivePerson.ACTIVE);
		person.setCreationType(CreationType.CARD);
		person.setIsResident(false);
		person.setRegistrationAddress(new Address());
		person.setResidenceAddress(new Address());
		person.setCheckLoginCount(0L);
		person.setSegmentCodeType(SegmentCodeType.NOTEXISTS);
		updateNewPersonData(person, clientInd);

		personService.createLogin(person);
		personService.add(person);

		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
		schemeOwnService.setScheme(person.getLogin(), AccessType.anonymous, schemesConfig.getAnonymousClientAccessScheme());

		return person;
	}

	/**
	 * ���������� ���������� � ������ ������������, �� ������� ������� ��������� �������
	 */
	public void logSpecVersion()
	{
		boolean useV19spec = ConfigFactory.getConfig(ASFilialConfig.class).isUseV19spec();
		log.info("������������ ������ ������������ ��������������(�����) " + (useV19spec ? ">= 19" : "< 19"));
	}
}
