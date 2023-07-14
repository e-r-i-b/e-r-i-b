package com.rssl.phizic.synchronization;

import com.rssl.phizic.common.type.*;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.common.types.Day;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.synchronization.types.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.commons.collections.CollectionUtils;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.util.*;
import javax.xml.bind.JAXBException;

/**
 * User: Moshenko
 * Date: 22.10.13
 * Time: 14:57
 * Класс для работы с сообщениями COC(по служебному каналу)
 */
public class SOSMessageHelper
{
	private static final VersionNumber VERSION = new VersionNumber(1, 0);
    private static final String REGION = "region";
	private static final String SCHEMA = "com/rssl/phizic/synchronization/mss.xsd";
    /**
     * @param profileList - список профилей, по которым требуется отправить уведомление
     * @return Сообщение оповещение ЕРМБ об изменении профиля (UpdateProfilesRq)
     */
    public String getUpdateProfilesRqStr(List<ErmbProfileInfo> profileList) throws JAXBException, SAXException
    {
	    UpdateProfilesRq rq = new UpdateProfilesRq();
        rq.setRqUID(new RandomGUID().toUUID());
        rq.setRqTime(Calendar.getInstance());
        rq.setRqVersion(VERSION);

        if (!profileList.isEmpty())
        {
            //updatedProfiles
            UpdatedProfilesType xmlProfiles = new UpdatedProfilesType();
            //profile [1-n]
            for (ErmbProfileInfo profile : profileList)
            {
                UpdateProfileType xmlProfile = new UpdateProfileType();
                //version [1]
                xmlProfile.setVersion(profile.getProfileVersion());
                //clientIdentity  [1]
                xmlProfile.setClientIdentity(getClientIdentity(profile.getIdentity()));
                //clientOldIdentity [0-n]
                List<ErmbProfileIdentity> oldIdentitys = profile.getOldIdentityList();
                if (!CollectionUtils.isEmpty(oldIdentitys))
                {
                    for (ErmbProfileIdentity identity : oldIdentitys)
                    {
                        ClientOldIdentityType xmlClientOldIdentity = new ClientOldIdentityType();
                        //identity [1]
                        xmlClientOldIdentity.setIdentity(getClientIdentity(identity));
                        //changed [1]
	                    Calendar changed = identity.getDocTimeUpDate();
	                    if (changed != null)
                            xmlClientOldIdentity.setChanged(DateHelper.getXmlDateTimeFormat((changed.getTime())));
	                    else
		                    //если null значит изменение происходит в текущий момент
	                        xmlClientOldIdentity.setChanged(DateHelper.getXmlDateTimeFormat(new Date()));

                        xmlProfile.getClientOldIdentities().add(xmlClientOldIdentity);
                    }
                }
//                clientCategory [1]
               xmlProfile.setClientCategory(profile.getClientCategory());
                //isUDBO [1]
                xmlProfile.setIsUDBO(profile.isUDBO());
                //clientPhones  [1]
                ClientPhonesType xmlPhones = new ClientPhonesType();
                    //phone [1-n]
                for (String phoneNumber : profile.getPhoneNumbers())
                {
                    xmlPhones.getPhones().add(PhoneNumber.fromString(phoneNumber));
                }
	            xmlProfile.setClientPhones(xmlPhones);
                //clientResources [1]
                ClientResourcesType xmlResources = new ClientResourcesType();
                Set<Card> cards = profile.getCards();
                if (!cards.isEmpty())
                {
                    //cards [0-1]
                    CardsType xmlCards = new CardsType();
                        //card [1-n]
                    Map<String, Boolean> additionalCadrs = profile.getAdditionalCadrs();
                    for (Card card : cards)
                    {
                        CardType xmlCard = new CardType();
                            //number [1]
                        xmlCard.setNumber(card.getNumber());
                            //isMain [1]
                        xmlCard.setIsMain(card.isMain());
                            //type [1]
                        xmlCard.setType(card.getCardType().toString());
                            //tb [1]
                        xmlCard.setTb(StringHelper.addLeadingZeros(card.getOffice().getCode().getFields().get(REGION), 3));
	                        //name[1]
	                    xmlCard.setName(card.getDescription());
                        Boolean isOwnerProfile = additionalCadrs.get(card.getNumber());
                        if (isOwnerProfile!=null)
                            xmlCard.setIsOwnerProfile(isOwnerProfile);
                        xmlCards.getCards().add(xmlCard);
                    }
                    xmlResources.setCards(xmlCards);
                }
                Set<Account> accounts = profile.getAccounts();
                if (!accounts.isEmpty())
                {
                    //accounts [0-1]
                    AccountsType xmlAccounts = new AccountsType();
                        //account [1-n]
                    for (Account account : accounts)
                    {
                        AccountType xmlAccount = new AccountType();
                            //number [1]
                        xmlAccount.setNumber(account.getNumber());
                            //tb [1]
                        xmlAccount.setTb(StringHelper.addLeadingZeros(account.getOffice().getCode().getFields().get(REGION), 3));
	                        //name[1]
                        xmlAccount.setName(account.getDescription());
                        xmlAccounts.getAccounts().add(xmlAccount);
                    }
                    xmlResources.setAccounts(xmlAccounts);
                }
                Set<Loan> loans = profile.getLoans();
                if (!loans.isEmpty())
                {
                    //loans [0-1]
                    LoansType xmlLoans = new LoansType();
                        //loan [1-n]
                    for (Loan loan : loans)
                    {
                        LoanType xmlLoan = new LoanType();
                            //agreementNumber [1]
                        xmlLoan.setAgreementNumber(loan.getAgreementNumber());
                            //accountNumber [1]
                        xmlLoan.setAccountNumber(loan.getAccountNumber());
                            //tb [1]
                        xmlLoan.setTb(StringHelper.addLeadingZeros(loan.getOffice().getCode().getFields().get(REGION), 3));
	                    xmlLoan.setName(loan.getDescription());
                        xmlLoans.getLoen().add(xmlLoan);
                    }
                    xmlResources.setLoans(xmlLoans);
                }
	            xmlProfile.setClientResources(xmlResources);
                //mobileBankService [1]
                MobileBankServiceType xmlMobileBankService = new MobileBankServiceType();
				//registrationStatus [1] Флажок «услуга подключена».
                boolean registrationStatus = StringHelper.isNotEmpty(profile.getServiceStatus());
                xmlMobileBankService.setRegistrationStatus(registrationStatus);

                if (registrationStatus)
                {
                    //serviceStatus [1]
	                String serviceStatus = profile.getServiceStatus();
	                xmlMobileBankService.setServiceStatus(serviceStatus);
                    //activePhone [1]
	                String activePhone = profile.getMainPhoneNumber();
	                if (StringHelper.isEmpty(activePhone))
		                throw new IllegalStateException("Неконсистентное состояние профиля ЕРМБ, услуга включена, но нет активного телефона" +
				                ". id=" + profile.getId() +
				                ". state=" + serviceStatus);
	                xmlMobileBankService.setActivePhone(PhoneNumber.fromString(activePhone));
                    //informResources[1] Продукты клиента, по которым должны отправляться оповещения
                    List<Pair<String, String>> informProducts = profile.getInformProducts();
                    if (!informProducts.isEmpty())
                    {
                     //informResources [0-1]
                        InformResourcesType xmlInformResources = new InformResourcesType();
                        //resource [0-n]
                        for (Pair<String, String> pair : informProducts)
                        {
                            ResourceType resType = ResourceType.fromValue(pair.getFirst());
                            ResourceIDType res = new ResourceIDType();
                            res.setType(resType);
                            switch (resType)
                            {
                                case ACCOUNT: res.setAccount(pair.getSecond()); break;
                                case CARD: res.setCard(pair.getSecond()); break;
                                case LOAN: res.setLoan(pair.getSecond()); break;
                            }
                            xmlInformResources.getResources().add(res);
                        }
	                    xmlMobileBankService.setInformResources(xmlInformResources);
                    }
                    //informNewResource [1]
                    xmlMobileBankService.setInformNewResource(profile.getNewProductNotification());
                    //informPeriod [0-1]
                    InformPeriodType xmlPeriodType = new InformPeriodType();
                        //dayPeriod [0-1]
                    TimePeriodType xmlTimePeriod = new TimePeriodType();
                    xmlTimePeriod.setBegin(DateHelper.getXmlTimeFormat(profile.getNotificationStartTime()));
                    xmlTimePeriod.setEnd(DateHelper.getXmlTimeFormat(profile.getNotificationEndTime()));
                    xmlTimePeriod.setTimeZone(TimeZone.getFormattedTimeZone(profile.getTimeZone()));
                    xmlPeriodType.setDayPeriod(xmlTimePeriod);
                        //enabledDays [0-1]
                    InformPeriodType.EnabledDays xmlEnabledDays = new InformPeriodType.EnabledDays();
                            //day [1-7]
                    DaysOfWeek daysOfWeek = profile.getDaysOfWeek();
                    if (daysOfWeek == null)
                        daysOfWeek = new DaysOfWeek(true);
                    for (Day day : daysOfWeek.getDays())
                        xmlEnabledDays.getDaies().add(DayOfWeekType.fromValue(day.toFullName()));
                    xmlPeriodType.setEnabledDays(xmlEnabledDays);
                    xmlMobileBankService.setInformPeriod(xmlPeriodType);
                    //suppressAdvertising [1]
                    xmlMobileBankService.setSuppressAdvertising(profile.isSuppressAdv());
                    //informDepositEnrollment [1]
                    xmlMobileBankService.setInformDepositEnrollment(profile.getDepositsTransfer());
                    //transliterateSms [1]
                    xmlMobileBankService.setTransliterateSms(profile.getTransliterateSms());

                }
                xmlProfile.setMobileBankService(xmlMobileBankService);
                //deletedProfiles [0-1]
                //Пока не реализуем
                xmlProfiles.getProfiles().add(xmlProfile);
            }
            rq.setUpdatedProfiles(xmlProfiles);
        }
	return JAXBUtils.marshalBean(rq);
    }

	/**
	 * @param phones Номер телефона.
	 * @return Запрос (от ЕРМБ):  на сброс IMSI
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public static String getResetIMSIRqStr(List<String> phones) throws JAXBException, SAXException
	{
		ResetIMSIRq rq = new ResetIMSIRq();
		rq.setRqUID(new RandomGUID().toUUID());
		rq.setRqTime(Calendar.getInstance());
		rq.setRqVersion(VERSION);
		for (String phone : phones)
		{
			rq.getPhones().add(PhoneNumber.fromString(phone));
		}

		return JAXBUtils.marshalBean(rq);
	}

	/**
	 *
	 * @param confirmProfilesRqStr сообщение о подтверждени полученных профилях ЕРМБ
	 * @return тип, соответствующий запросу ConfirmProfilesRq
	 * @throws JAXBException
	 * @throws ParseException
	 * @throws SAXException
	 */
	public ConfirmProfilesRequest getConfirmProfilesRequest(String confirmProfilesRqStr) throws JAXBException, ParseException, SAXException
	{
		ConfirmProfilesRq confirmProfilesRq = JAXBUtils.unmarshalBean(ConfirmProfilesRq.class, confirmProfilesRqStr, XmlHelper.schemaByFileName(SCHEMA));
		ConfirmProfilesRequest request = new ConfirmProfilesRequest();

		request.setRqVersion(confirmProfilesRq.getRqVersion().toString());
		request.setRqUID(confirmProfilesRq.getRqUID());
		request.setRqTime(confirmProfilesRq.getRqTime());
		request.setConfirmProfilesRqInfo(getErmbUpdateProfileInfoList(confirmProfilesRq.getUpdatedProfiles()));

		return request;
	}

    /**
     * @param confirmProfilesRqStr сообщение о подтверждени полученных профилях ЕРМБ
     * @return List<ErmbUpdateProfileInfo> информация о подтверждённых профилях
     */
    public List<ErmbUpdateProfileInfo> getConfirmProfilesRqInfo(String confirmProfilesRqStr) throws JAXBException, ParseException, SAXException
    {
        ConfirmProfilesRq confirmProfilesRq = JAXBUtils.unmarshalBean(ConfirmProfilesRq.class, confirmProfilesRqStr, XmlHelper.schemaByFileName(SCHEMA));
	    return getErmbUpdateProfileInfoList(confirmProfilesRq.getUpdatedProfiles());
    }

	public List<ErmbUpdateProfileInfo> getErmbUpdateProfileInfoList(ConfirmUpdatedProfilesType confirmUpdatedProfiles) throws ParseException
	{
		List<ErmbUpdateProfileInfo> ermbUpdateProfileInfoList = new ArrayList<ErmbUpdateProfileInfo>();
		List<ConfirmUpdatedProfileType>  confirmUpdatedProfileList = confirmUpdatedProfiles.getProfiles();
		for (ConfirmUpdatedProfileType confirmUpdatedProfile:confirmUpdatedProfileList)
		{
			ErmbUpdateProfileInfo ermbUpdateProfileInfo = new ErmbUpdateProfileInfo();

		    ermbUpdateProfileInfo.setClientIdentity(getClientIdentity(confirmUpdatedProfile.getClientIdentity()));
		    List<IdentityType> clientOldIdentityTypeList = confirmUpdatedProfile.getClientOldIdentities();
		    if (clientOldIdentityTypeList != null && !clientOldIdentityTypeList.isEmpty())
		    {
		        List<ErmbProfileIdentity> personOldIdentitiList = new ArrayList<ErmbProfileIdentity>();
		        for(IdentityType oldIdentityType:clientOldIdentityTypeList)
		            personOldIdentitiList.add(getClientIdentity(oldIdentityType));

		            ermbUpdateProfileInfo.setClientOldIdentity(personOldIdentitiList);
		        }
		        ermbUpdateProfileInfo.setProfileVersion(confirmUpdatedProfile.getVersion());
		        ermbUpdateProfileInfoList.add(ermbUpdateProfileInfo);
		    }
        return ermbUpdateProfileInfoList;
	}

	/**
	 * @param data список профилей на подтверждение
	 * @return xml запроса
	 * @throws JAXBException
	 */
	public static String getConfirmProfileRqXml(List<ErmbUpdateProfileInfo> data) throws JAXBException
	{
		ConfirmProfilesRq rq = new ConfirmProfilesRq();
		rq.setRqUID(new RandomGUID().toUUID());
		rq.setRqTime(Calendar.getInstance());
		rq.setRqVersion(VERSION);

		ConfirmUpdatedProfilesType updatedProfiles = new ConfirmUpdatedProfilesType();
		for (ErmbUpdateProfileInfo info : data)
		{
			ConfirmUpdatedProfileType updatedProfile = new ConfirmUpdatedProfileType();
			updatedProfile.setClientIdentity(getClientIdentity(info.getClientIdentity()));
			List<ErmbProfileIdentity> oldErmbProfileIdentities = info.getClientOldIdentity();
			if (oldErmbProfileIdentities != null)
				for (ErmbProfileIdentity oldIdentity : info.getClientOldIdentity())
				{
					updatedProfile.getClientOldIdentities().add(getClientIdentity(oldIdentity));
				}
			updatedProfile.setVersion(info.getProfileVersion());

			updatedProfiles.getProfiles().add(updatedProfile);
		}

		rq.setUpdatedProfiles(updatedProfiles);

		return JAXBUtils.marshalBean(rq);
	}

    /**
     * @param updateClientRqStr сообщение о оповещении ЕРИБ об изменении данных клиента
     * @return updateClientRq тип соответствующий запросу UpdateClientRq
     */
    public UpdateClientRequest getUpdateClientRqInfo(String updateClientRqStr) throws JAXBException, ParseException
    {
        UpdateClientRequest request = new UpdateClientRequest();
        UpdateClientRq updateClientRq = JAXBUtils.unmarshalBean(UpdateClientRq.class, updateClientRqStr);
        UUID uuid = updateClientRq.getRqUID();
        request.setRqUID(uuid);
        request.setRqTime(updateClientRq.getRqTime());
        UpdateClientProfilesType clientProfilesType = updateClientRq.getProfile();
        IdentityType identity = clientProfilesType.getClientIdentity();
        request.setClientIdentity(getClientIdentity(identity));
        IdentityType oldIdentity = clientProfilesType.getClientOldIdentity();
        request.setClientOldIdentity(getClientIdentity(oldIdentity));
        request.setRqVersion(updateClientRq.getRqVersion().toString());
        return request;
    }

    /**
     * @param updateResourceRqStr сообщение о оповещении ЕРИБ об изменении продукта клиента
     * @return UpdateResourceRqType тип соответствующий запросу UpdateResourceRq
     */
    public UpdateResourceRequest getUpdateResourceRqInfo(String updateResourceRqStr) throws JAXBException, ParseException
    {
        UpdateResourceRq updateResourceRq  = JAXBUtils.unmarshalBean(UpdateResourceRq.class, updateResourceRqStr);
        UpdateResourceRequest request = new UpdateResourceRequest();
        request.setRqVersion(updateResourceRq.getRqVersion().toString());
        UUID uuid = updateResourceRq.getRqUID();
        request.setRqUID(uuid);
        request.setRqTime(updateResourceRq.getRqTime());
        UpdateResourceProfilesType updateResourceProfilesType = updateResourceRq.getProfile();
        request.setClientIdentity(getClientIdentity(updateResourceProfilesType.getClientIdentity()));
        request.setTb(StringHelper.removeLeadingZeros(updateResourceProfilesType.getTb()));
        ResourceIDType resourceIDType = updateResourceProfilesType.getResource();
        ResourceType resourceType = resourceIDType.getType();
        switch (resourceType)
        {
            case CARD:
                request.setResourceType(ErmbResourceType.CARD);
                request.setCard(resourceIDType.getCard());
                break;

            case LOAN:
                request.setResourceType(ErmbResourceType.LOAN);
                request.setLoan(resourceIDType.getLoan());
                break;

            case ACCOUNT:
                request.setResourceType(ErmbResourceType.ACCOUNT);
                request.setAccount(resourceIDType.getAccount());
                break;
        }
        return request;
    }

	public SmsXmlRequest getSmsXmlRequest(String smsRq)
	{
		try
		{
			SMSRq smsRequest  = JAXBUtils.unmarshalBean(SMSRq.class, smsRq);
			SmsXmlRequest smsXmlRequest = new SmsXmlRequest();
			smsXmlRequest.setEribSID(smsRequest.getEribSID());
			smsXmlRequest.setPhone(smsRequest.getPhone());
			smsXmlRequest.setRqTime(smsRequest.getRqTime());
			smsXmlRequest.setRqUID(smsRequest.getRqUID());
			smsXmlRequest.setRqVersion(smsRequest.getRqVersion().toString());
			smsXmlRequest.setText(smsRequest.getText());
			return smsXmlRequest;
		}
		catch (JAXBException e)
		{
			return  null;
		}
	}

    public static IdentityType getClientIdentity(ErmbProfileIdentity identity)
    {
        IdentityType xmlClientIdentity = new IdentityType();

        xmlClientIdentity.setLastname(identity.getSurName());
        xmlClientIdentity.setFirstname(identity.getFirstName());
        String partName = identity.getPatrName();
        if (StringHelper.isNotEmpty(partName))
            xmlClientIdentity.setMiddlename(partName);
        xmlClientIdentity.setBirthday(identity.getBirthDay());

        ErmbProfileIdentityCard identityCard = identity.getIdentityCard();
        IdentityCardType xmlIdentityCard = new IdentityCardType();
        xmlIdentityCard.setIdType(identityCard.getIdType());
        String series = identityCard.getIdSeries();
        if (StringHelper.isNotEmpty(series))
            xmlIdentityCard.setIdSeries(series);
        xmlIdentityCard.setIdNum(identityCard.getIdNum());
        String issuedBy = identityCard.getIssuedBy();
        if (StringHelper.isNotEmpty(issuedBy))
            xmlIdentityCard.setIssuedBy(issuedBy);
        Calendar issueDt = identityCard.getIssueDt();
        if (issueDt != null)
            xmlIdentityCard.setIssueDt(issueDt);

        xmlClientIdentity.setIdentityCard(xmlIdentityCard);
	    xmlClientIdentity.setTb(StringHelper.addLeadingZeros(identity.getTb(), 3));
        return xmlClientIdentity;
    }

    public static ErmbProfileIdentity getClientIdentity(IdentityType identityType) throws ParseException
    {
	    ErmbProfileIdentity personIdentity = new ErmbProfileIdentity();
        personIdentity.setFirstName(identityType.getFirstname());
        personIdentity.setSurName(identityType.getLastname());
        String middleName = identityType.getMiddlename();
        if (!StringHelper.isEmpty(middleName))
            personIdentity.setPatrName(middleName);
        Calendar birthDay = identityType.getBirthday();
        personIdentity.setBirthDay(birthDay);
        IdentityCardType identityCard = identityType.getIdentityCard();
	    ErmbProfileIdentityCard ermbProfileIdentityCard = new ErmbProfileIdentityCard();
	    ermbProfileIdentityCard.setIdType(identityCard.getIdType());
	    ermbProfileIdentityCard.setIdNum(identityCard.getIdNum());
        String docSeries = identityCard.getIdSeries();
        if (!StringHelper.isEmpty(docSeries))
	        ermbProfileIdentityCard.setIdSeries(docSeries);
	    personIdentity.setIdentityCard(ermbProfileIdentityCard);
	    personIdentity.setTb(StringHelper.removeLeadingZeros(identityType.getTb()));
        return  personIdentity;
    }

}
