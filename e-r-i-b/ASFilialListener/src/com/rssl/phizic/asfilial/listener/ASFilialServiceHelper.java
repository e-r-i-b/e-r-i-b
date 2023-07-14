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
 * Класс помощник, для использования в ASFilialInfoServiceSoapBindingImpl
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
	 * @param newPerson новый клиент
	 * @param clientInd пришедшие идентификационные данные 
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
	 * @param resArr пришедшие ресурсы
	 * @param method кокой метод будем вызывать у линков
	 * 1 - доступность в интернет-приложении
	 * 2 - доступность в мобильном приложении
	 * 3 - доступность в устройствах самообслуживания
	 * 4 - признак отправки оповещений
	 * 5 - доступность в CMC канале
	 */
	public void updateResources(ResourcesType[] resArr, int method, Map<String, ErmbProductLink> links, boolean tariffAllowsCardSmsNotification, boolean tariffAllowsAccountSmsNotification) throws BusinessLogicException
	{
		//1. если null то действий не требуется
		if (resArr == null)
			return;
		//2.устанавливаем все признаки в false
		Collection<ErmbProductLink> resLinks = links.values();
		for (ErmbProductLink link : resLinks)
		{
			updateLink(link, method, false, tariffAllowsCardSmsNotification, tariffAllowsAccountSmsNotification);
		}

		Collection<String> numbers = links.keySet();
		for (ResourcesType res : resArr)
		{
			//3. если номера пришедшего ресурса нет у клиента то кидаем ошибку
			String type = res.getType();
			String number = null;
			if (StringHelper.equals(type, CARD))
				number = res.getCard().getNumber();
			else if (StringHelper.equals(type, ACCOUNT))
				number = res.getAccount();
			else if (StringHelper.equals(type, LOAN))
				number = res.getCredit();

			if (!numbers.contains(number))
				throw new BusinessLogicException("Ресурс с номером " + number + " не найден у клиента");
			//4. обновляем ресурс с нашим номером
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
					throw new IllegalArgumentException("Линк на неизвестный тип продукта");
				}
				break;
			}
            case 5:
                link.setShowInSms(value);
		}
	}

	/**
	 * @param person профиль
	 * @return старые данные
	 */
	public IdentityType getOldData(Person person) throws BusinessException
	{
		List<PersonOldIdentity> pesonOldIdentities = personIdentityService.getListByProfile(person);
		if (pesonOldIdentities.isEmpty())
			return null;
		//берём первую из списка
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
	 * @param ermbProfile профиль ЕРМБ
	 * @return ("Номерлинка", "ErmbProductLink")
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
	 * Обновляем профиль информацией о периоде уведомления
	 * @param ermbProfile профиль ЕРМБ
	 * @param daytimePeriod Информация о периоде уведомления 
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
			//значение по умолчанию
			updateDefaultNotificationTime(ermbProfile);
		}
	}

    /**
     * Проверяем есть ли среди владельцев, переданного списка карт, не наш клиент
     * Т.е. карта найденная в МБК по телефону не содержится в списке карт клиента
     * @param mbkCards Список привязанных карт из МБК
     * @param allCards список ресурсов клиента
     * @return Если есть то true.
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
     * По полученным данным обновляем профиль клиента, создаем шаблоны
     * @param mbkMbkConnectionInfos Результат миграции мбк
     * @param profile профиль Ермб
     */
    void updateProfileMbkData (List<MbkConnectionInfo> mbkMbkConnectionInfos, ErmbProfileImpl profile)
    {
        //TODO Доделать как данный процес будет сформулирован болие детально.
    }

    /**
     * Обновляем все данный в одной транзакции
     * @param profile профиль
     * @param linklist линки
     * @param mbkTemplates шаблоны пришедшие из мбк
     * @param mbkPhoneOffers оферты пришедшие из мбк
     * @param phoneAndCodeMap ришедшие телефоны с кодами подтверждений
     * @return true если обновление профиля прошло успешно
     * @throws BusinessException
     */
    public Boolean updateProfileData(final ErmbProfileImpl profile, final List<ErmbProductLink> linklist,final List<MobileBankTemplate> mbkTemplates,final List<String> mbkPhoneOffers,final Map<String,String> phoneAndCodeMap,final StatusType status) throws BusinessException, BusinessLogicException
    {

        //Проверяем все телефоны на придмет наличия confrimCode
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

        //Сохраниение телефонов произайдет с сохранением профиля(т.е к данному моменту они уже в профиле.)
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
     * Переводит профиль в статус NOT_CONNECTED, удаляем привязанные телефоны
     * @param profile Профиль ЕРМБ.
     * @param status Информация о выполнении запроса.
     */
    public void rollbackErmbProfile(final ErmbProfileImpl profile,StatusType status)
    {
        //Профиль переводим в состояние NOT_CONNECTED
        profile.setServiceStatus(false);
        //Телефоны удаляем.
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
     * Поиск персоны в ЕРИБ
     * @param clientInd Идентификационные данные клиента
     * @return Профиль в ЕРИБ
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
	 * @param clientIdentity Идентификационные данные клиента
	 * @param oldClientIdentities Старые идентификационные данные клиента
	 * @return ActivePerson либо null
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
     * Поиск клиента в ишне
     * @param clientInd  Идентификационные данные клиента
     * @return Клиент из шины
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
		// GFL не вернул карты, или вернул ошибки
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
	 * Ищем телефоны клиента зарегистрированные в МБК, и если такие есть то удаляем их.
	 * @param clientInd Идентификационные данные клиента
	 * @param status Cтатус
	 * @return телефоны из МБК удалились
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
	 * Ищем телефоны клиента зарегистрированные в МБК, и если такие есть то удаляем их.
	 * @param client Клиент
	 * @param status Cтатус
	 * @return телефоны из МБК удалились
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

				log.info("Попытка удалить телефоны клиента:" + client.getFirstName() +" " +  client.getSurName() +  " " + client.getPatrName() + " из МБК");
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
	 * Сохранение изменений  в профиле + Миграция + Частичное заполнение ответа (updateProfile)
	 * @param profile  Частично обновленный  профиль
	 * @param profileBeforeUpdate Старый профиль
	 * @param newComePhoneNumber Новые телефонные номера (которых еще нет в профиле)
	 * @param phoneAndCode Все телефонные номера и коды к ним
	 * @param regStatus Флажок «услуга подключена».
	 * @param allLinks Все линки
	 * @param oldPhones Старые телефонные номера профиля
	 * @param oldMainPhone Старый основной номер
	 * @param response Ответ
	 * @param clientInd Идентификационные  данные клиента
	 * @param status Статс ответа.
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
		//Статус профиля до выполнение updateProfile
		boolean profileOldServiceStatus = profile.isServiceStatus();
		ActivePerson person = (ActivePerson)profile.getPerson();
		List<CardLink> cardLinks = profile.getCardLinks();
		//Миграция/Сохранение:
		try
		{
			//Призак того что нужно проверять в MBK, MBV
			boolean needMbkAndMbvTest = ( profileOldServiceStatus|| !newComePhoneNumber.isEmpty()) && (regStatus);
			//Телефоны которые есть в МБК
			Set<String> mbkPhones = new HashSet<String>();
			//Телефоны которые есть в МБВ
			Set<String> mbvPhones = new HashSet<String>();
			//все занятые телефоны
			Set<String> engagedPhones = new HashSet<String>();
			//телефоны с неверным кодом подтверждения
			Set<String> wrongConfirmCodePhones = new HashSet<String>();
			//Данные нашего клиента в МБВ
			MbvClientIdentity mbvClientIdentity = null;
			//Cписок привязанных карт из МБК
			Set<String> mbkCards = new HashSet<String>();
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			DepoMobileBankService mbvService = GateSingleton.getFactory().service(DepoMobileBankService.class);
			ErmbConfig ermbConfig = ConfigFactory.getConfig(ErmbConfig.class);
			if (!ermbConfig.isMigrationOnTheFlyUse())
			{
				log.warn("UPDATE_PROFILE: миграция данных на лету из МБК/МБВ выключена");
			}
			//Перебираем все новые телефоны
			for (String phone:newComePhoneNumber)
			{
				//Если номер привязанн к другому профилю в ЕРМБ и к нему не передан confirmCode
				//То добавляем его в  тэг <EngagedPhones>.
				if (ErmbHelper.isErmbEngagedPhones(phone,profile))
				{
					String code = phoneAndCode.get(phone);
					if (StringHelper.isEmpty(phoneAndCode.get(phone)))
					{
						log.info("UPDATE_PROFILE: телефон уже есть в ЕРМБ для другого клинта. Код подтверждения НЕ пришел. " + phone);
						engagedPhones.add(phone);
					}
					else
					{
						log.info("UPDATE_PROFILE: телефон уже есть в ЕРМБ для другого клинта. Код подтверждения пришел. " + phone);
						if (!testSwapPhoneNumberCode(phone, code))
						{
							wrongConfirmCodePhones.add(phone);
						}
					}
				}
				if (needMbkAndMbvTest)   //
				{
					//Ищем связи в МБК по переданному телефону
					Set<String> mbkCurrentPhoneCards = mobileBankService.getCardsByPhone(phone);
					//если есть привязанные к переданному телефону карты в МБК
					if (!mbkCurrentPhoneCards.isEmpty())
					{
						Boolean mbkEngaget = isMbkEngaged(mbkCurrentPhoneCards, allLinks.keySet());
						if (mbkEngaget)
						{
							String code = phoneAndCode.get(phone);
							//Если для такого телефона не  передан сonfirmCode, то добавляем в тэг <EngagedPhones>.
							if (StringHelper.isEmpty(phoneAndCode.get(phone)))
							{
								log.info("UPDATE_PROFILE: телефон еще привязан к другому клиенту МБК. Код подтверждения НЕ пришел. " + phone);
								engagedPhones.add(phone);
							}
							else
							{
								log.info("UPDATE_PROFILE: телефон еще привязан к другому клиенту МБК. Код подтверждения пришел. " + phone);
								if (!testSwapPhoneNumberCode(phone, code))
								{
									wrongConfirmCodePhones.add(phone);
								}
							}
						}
						//если этот телефон присутствует в МБК
						mbkPhones.add(phone);
						mbkCards.addAll(mbkCurrentPhoneCards);
					}
					//Ищем связи в MBV.
					//Смотрим кому привязан  в МБВ.
					if (mbvConfig.isMbvAvaliability())
					{
						List<MbvClientIdentity> mbvClientIdentityList = mbvService.checkPhoneOwn(phone);
						if (!mbvClientIdentityList.isEmpty())
						{
							for (MbvClientIdentity identity :mbvClientIdentityList)
							{
								//Если есть наш клиент.
								if (MigrationHelper.isSameClient(identity, person.asClient()))
								{
									mbvClientIdentity = identity;
								}
								else
								{
									String code = phoneAndCode.get(phone);
									//Если не передан confirmCode.
									if (StringHelper.isEmpty(phoneAndCode.get(phone)))
									{
										log.info("UPDATE_PROFILE: телефон еще привязан к другому клиенту МБВ. Код подтверждения НЕ пришел. " + phone);
										engagedPhones.add(phone);
									}
									else
									{
										log.info("UPDATE_PROFILE: телефон еще привязан к другому клиенту МБВ. Код подтверждения пришел. " + phone);
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

			//Поиск в МБК регистраций по актуальному списку карт (только платежные карты)
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

				//отдельно проверяем на конфликт другие МБК телефоны (кроме тех что пришли в запросе Филиала), т.к. они будут забираться при миграции
				Collection<String> otherMbkPhones = CollectionUtils.subtract(allMbkPhones, newComePhoneNumber);
				for (String phone : otherMbkPhones)
				{
					Set<String> mbkCurrentPhoneCards = mobileBankService.getCardsByPhone(phone);
					if (isMbkEngaged(mbkCurrentPhoneCards, allLinks.keySet()))
					{
						log.info("UPDATE_PROFILE: телефон еще привязан к другому клиенту МБК. В запросе телефон не пришел. " + phone);
						engagedPhones.add(phone);
					}
				}
			}

			if (CollectionUtils.isNotEmpty(wrongConfirmCodePhones))
			{
				status.setStatusCode(ASFilialReturnCode.CONFIRM_HOLDER_ERR.toValue());
				status.setStatusDesc(responseBuilder.getPhonesInfoMessage(wrongConfirmCodePhones, "Для номера был указан неверный код подтверждения держателя номера"));
			}
			else if (CollectionUtils.isNotEmpty(engagedPhones))
			{
				status.setStatusCode(ASFilialReturnCode.DUPLICATION_PHONE_ERR.toValue());
				status.setStatusDesc(responseBuilder.getPhonesInfoMessage(engagedPhones, "Телефоны зарегистрированы на других лиц"));
				response.setEngagedPhones(responseBuilder.buildPhoneArray(engagedPhones));
			}
			else
			{
				//Данные по миграции из МБК
				List<MbkConnectionInfo> mbkMbkConnectionInfos = new LinkedList<MbkConnectionInfo>();
				//Id Миграции МБК
				Long mbkMigrationId = null;
				com.rssl.phizic.common.types.UUID mbvMigrationId = null;
				//Шиблоны из мбк
				List<MobileBankTemplate> mbkTemplates = null;
				//Офферты из мбк
				List<String> mbkPoneOffers = null;
				try
				{
					if (ermbConfig.isMigrationOnTheFlyUse())
					{
						//запускаем миграцию МБК
						//1. Есть телефоны в МБК по картам gfl - забираем (даже с пустым списком телефонов)
						//2. Есть чужие телефоны в МБК, по которым пришло подтверждение - забираем
						if(!allMbkPhones.isEmpty() || !mbkPhones.isEmpty())
						{
							Set<MbkCard> mbkMigrationCards = MigrationHelper.getCardsToMigrate(cardLinks);

							BeginMigrationResult result = mobileBankService.beginMigrationErmb(mbkMigrationCards, mbkPhones, null, MigrationType.PILOT);
							mbkMbkConnectionInfos = result.getMbkConnectionInfo();
							mbkMigrationId = result.getMigrationId();
							if (mbkMigrationId == null)
								log.error("UPDATE_PROFILE: Не удалось проинициализировать миграционную транзакцию");
						}

						//Если есть связи в мбв, а также там есть связь нашего клиента, запускаем миграцию МБВ
						if (mbvConfig.isMbvAvaliability())
							if(mbvClientIdentity != null)
								mbvMigrationId = mbvService.beginMigration(mbvClientIdentity);
					}
					//Cохраняем данный по профилю,линкам, и телефонам
					List<ErmbProductLink> linklist = new ArrayList<ErmbProductLink>(allLinks.values());
					boolean  isSuccessUpdate = false;
					try
					{
						if (!mbkMbkConnectionInfos.isEmpty())
						{
							updateProfileMbkData(mbkMbkConnectionInfos, profile);
							//Перебираем результаты миграции в поисках:
							for(MbkConnectionInfo resEntity: mbkMbkConnectionInfos)
							{
								//шаблонов
								List<MobileBankTemplate> currentMbkTemplaes = resEntity.getTemplates();
								if (currentMbkTemplaes != null && !currentMbkTemplaes.isEmpty())
								{
									if (mbkTemplates == null)
										mbkTemplates = currentMbkTemplaes;
									else
										//Составляем общий список шаблонов
										mbkTemplates.addAll(currentMbkTemplaes);
								}
								//офферт
								List<String> currentMbkPhoneOffers = resEntity.getPhoneOffers();
								if (currentMbkPhoneOffers != null && !currentMbkPhoneOffers.isEmpty())
								{
									if (mbkPoneOffers == null)
										mbkPoneOffers = currentMbkPhoneOffers;
									else
										//Составляем общий список офферт
										mbkPoneOffers.addAll(currentMbkPhoneOffers);
								}
							}
						}

						//обновляем профиль ЕРМБ, и отсылаем оповещение об изменении профиля
						ErmbProfileListener profileListener = ErmbUpdateListener.getListener();
						ErmbProfileEvent profileEvent = new ErmbProfileEvent(profileBeforeUpdate);
						profileEvent.setNewProfile(profile);
						profileListener.beforeProfileUpdate(profileEvent);

						isSuccessUpdate = updateProfileData(profile,linklist,mbkTemplates,mbkPoneOffers,phoneAndCode,status);
						profileListener.afterProfileUpdate(profileEvent);

						//произошло подключение или отключение - оповестить внешние системы
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
						//Если поймали ошибку то откатываем миграции(есил были)
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
							//Если была миграция и если вернулись результаты, то запускаем процесс подтверждение миграции МБК
							if (ermbConfig.isMigrationOnTheFlyUse())
							{
								if (mbkMigrationId != null)
									mobileBankService.commitMigrationErmb(mbkMigrationId);
								if (mbvConfig.isMbvAvaliability())
									if(mbvMigrationId != null)
										mbvService.commitMigration(mbvMigrationId);
							}
						    ////////////////
							//Оповещение  CSA
							Set<String> newPhones = profile.getPhoneNumbers();
							boolean phonesChanged = !CollectionUtils.isEqualCollection(oldPhones, newPhones);
							boolean mainPhoneChanged = !StringUtils.equals(oldMainPhone, profile.getMainPhoneNumber());
							//если телефоны изменились, оповестить цса и обновить таблицу ермб-телефонов в PHIZ_PROXY_MBK
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
							//также делаем откат миграции
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

	//Код правильный  true, иначе false
	private boolean testSwapPhoneNumberCode(String phoneNumber, String confirmCode) throws BusinessException
	{
		return confirmHelper.testSwapPhoneNumberCodeOfflineDoc(confirmCode, phoneNumber);
	}

	/**
	 * создать клиента  по идентификационным данным.
	 * @param department департамент
	 * @param clientInd идентификационные данные клиента
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
	 * Логировать информацию о версии спецификации, по которой ведется обработка запроса
	 */
	public void logSpecVersion()
	{
		boolean useV19spec = ConfigFactory.getConfig(ASFilialConfig.class).isUseV19spec();
		log.info("Используется версия спецификации взаимодействия(релиз) " + (useV19spec ? ">= 19" : "< 19"));
	}
}
