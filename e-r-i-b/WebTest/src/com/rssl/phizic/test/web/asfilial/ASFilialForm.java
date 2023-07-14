package com.rssl.phizic.test.web.asfilial;

import org.apache.struts.action.ActionForm;

/**
 * User: moshenko
 * Date: 18.12.2012
 * Time: 11:02:50
 */
public class ASFilialForm extends ActionForm
{
	private String ASListenerUrl;
	private String messagesText;

	//bankInfo
	private String bankInfoRegionId; //VSP
	private String bankInfoAgencyId; //OSB
	private String bankInfoBranchId; //TB

	//Begin QueryProfile
	private boolean createIfNone;
	private String lastName;
	private String firstName;
	private String middleName;
	private String birthday;
	//ДУЛ
	private String idType;
	private String idSeries;
	private String idNum;
	private String issuedBy;
	private String issueDt;
	private String regionId;
	//Старые ДУЛ
	private String[] oldLastName;
	private String[] oldFirstName;
	private String[] oldMiddleName;
	private String[] oldBirthday;
	private String[] oldIdSeries;
	private String[] oldIdNum;
	private String[] oldIdType;
	//End QueryProfile
	//Begin UpdateProfile
	private Long profileID;
	private boolean clientDataChange; //Изменяются ли Данные по клиенту
	private String[] phoneNumber;
	private String[] mobilePhoneOperator;
	private String[] phoneConfirmHolderCode;
	private boolean internetClientServiceChange;//Изменяются ли Данные по услуге «Интернет-клиент»
	private String internetVisibleResources;
	private boolean mobileClientServiceChange;//Изменяются ли Данные по услуге «Мобильный клиент»
	private String mobileVisibleResources;
	private boolean ATMClientServiceChange;////Изменяются ли Данные по услуге «Устройства самообслуживания»
	private String ATMVisibleResources;
	private boolean mobileBankService; // меняются ли данный по услуге мобильный банк
	private boolean mobileBankServiceParams; // Параметры услуги мобильный банк
	private boolean registrationStatus;
	private String tariffId;
	private boolean quickServices;
	private String activePhoneNumber;
	private String visibleResources;
	private String activeMobilePhoneOperator;
	private String informResources;
	private String chargeOffCard;
	private boolean informNewResource;
	private boolean suppressAdvertising;
	private boolean informPeriodChange;
	private String ntfStartTimeString;
	private String ntfEndTimeString;
	private Long timeZone;
	private String[] ntfDays;
	//End UpdateProfile

	public String getMessagesText()
	{
		return messagesText;
	}

	public void setMessagesText(String messagesText)
	{
		this.messagesText = messagesText;
	}

	public String getASListenerUrl()
	{
		return ASListenerUrl;
	}

	public void setASListenerUrl(String ASListenerUrl)
	{
		this.ASListenerUrl = ASListenerUrl;
	}

	public boolean isCreateIfNone()
	{
		return createIfNone;
	}

	public void setCreateIfNone(boolean createIfNone)
	{
		this.createIfNone = createIfNone;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getIdType()
	{
		return idType;
	}

	public void setIdType(String idType)
	{
		this.idType = idType;
	}

	public String getIdSeries()
	{
		return idSeries;
	}

	public void setIdSeries(String idSeries)
	{
		this.idSeries = idSeries;
	}

	public String getIdNum()
	{
		return idNum;
	}

	public void setIdNum(String idNum)
	{
		this.idNum = idNum;
	}

	public String getIssuedBy()
	{
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy)
	{
		this.issuedBy = issuedBy;
	}

	public String getIssueDt()
	{
		return issueDt;
	}

	public void setIssueDt(String issueDt)
	{
		this.issueDt = issueDt;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public void setRegionId(String regionId)
	{
		this.regionId = regionId;
	}

	public Long getProfileID()
	{
		return profileID;
	}

	public void setProfileID(Long profileID)
	{
		this.profileID = profileID;
	}

	public String getATMVisibleResources()
	{
		return ATMVisibleResources;
	}

	public void setATMVisibleResources(String ATMVisibleResources)
	{
		this.ATMVisibleResources = ATMVisibleResources;
	}

	public String getInternetVisibleResources()
	{
		return internetVisibleResources;
	}

	public void setInternetVisibleResources(String internetVisibleResources)
	{
		this.internetVisibleResources = internetVisibleResources;
	}

	public String getMobileVisibleResources()
	{
		return mobileVisibleResources;
	}

	public void setMobileVisibleResources(String mobileVisibleResources)
	{
		this.mobileVisibleResources = mobileVisibleResources;
	}

	public boolean isRegistrationStatus()
	{
		return registrationStatus;
	}

	public void setRegistrationStatus(boolean registrationStatus)
	{
		this.registrationStatus = registrationStatus;
	}

	public String getTariffId()
	{
		return tariffId;
	}

	public void setTariffId(String tariffId)
	{
		this.tariffId = tariffId;
	}

	public boolean isMobileBankService()
	{
		return mobileBankService;
	}

	public void setMobileBankService(boolean mobileBankService)
	{
		this.mobileBankService = mobileBankService;
	}

	public boolean isMobileBankServiceParams()
	{
		return mobileBankServiceParams;
	}

	public void setMobileBankServiceParams(boolean mobileBankServiceParams)
	{
		this.mobileBankServiceParams = mobileBankServiceParams;
	}

	public boolean isQuickServices()
	{
		return quickServices;
	}

	public void setQuickServices(boolean quickServices)
	{
		this.quickServices = quickServices;
	}

	public String getActivePhoneNumber()
	{
		return activePhoneNumber;
	}

	public void setActivePhoneNumber(String activePhoneNumber)
	{
		this.activePhoneNumber = activePhoneNumber;
	}

	public String getActiveMobilePhoneOperator()
	{
		return activeMobilePhoneOperator;
	}

	public void setActiveMobilePhoneOperator(String activeMobilePhoneOperator)
	{
		this.activeMobilePhoneOperator = activeMobilePhoneOperator;
	}

	public String getInformResources()
	{
		return informResources;
	}

	public void setInformResources(String informResources)
	{
		this.informResources = informResources;
	}

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public boolean isInformNewResource()
	{
		return informNewResource;
	}

	public void setInformNewResource(boolean informNewResource)
	{
		this.informNewResource = informNewResource;
	}

	public boolean isSuppressAdvertising()
	{
		return suppressAdvertising;
	}

	public void setSuppressAdvertising(boolean suppressAdvertising)
	{
		this.suppressAdvertising = suppressAdvertising;
	}

	public String[] getNtfDays()
	{
		return ntfDays;
	}

	public void setNtfDays(String[] ntfDays)
	{
		this.ntfDays = ntfDays;
	}

	public String getNtfEndTimeString()
	{
		return ntfEndTimeString;
	}

	public void setNtfEndTimeString(String ntfEndTimeString)
	{
		this.ntfEndTimeString = ntfEndTimeString;
	}

	public String getNtfStartTimeString()
	{
		return ntfStartTimeString;
	}

	public void setNtfStartTimeString(String ntfStartTimeString)
	{
		this.ntfStartTimeString = ntfStartTimeString;
	}

	public Long getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(Long timeZone)
	{
		this.timeZone = timeZone;
	}

	public boolean isClientDataChange()
	{
		return clientDataChange;
	}

	public void setClientDataChange(boolean clientDataChange)
	{
		this.clientDataChange = clientDataChange;
	}

	public boolean isInternetClientServiceChange()
	{
		return internetClientServiceChange;
	}

	public void setInternetClientServiceChange(boolean internetClientServiceChange)
	{
		this.internetClientServiceChange = internetClientServiceChange;
	}

	public boolean isMobileClientServiceChange()
	{
		return mobileClientServiceChange;
	}

	public void setMobileClientServiceChange(boolean mobileClientServiceChange)
	{
		this.mobileClientServiceChange = mobileClientServiceChange;
	}

	public boolean isATMClientServiceChange()
	{
		return ATMClientServiceChange;
	}

	public void setATMClientServiceChange(boolean ATMClientServiceChange)
	{
		this.ATMClientServiceChange = ATMClientServiceChange;
	}

	public boolean isInformPeriodChange()
	{
		return informPeriodChange;
	}

	public void setInformPeriodChange(boolean informPeriodChange)
	{
		this.informPeriodChange = informPeriodChange;
	}

	public String[] getPhoneConfirmHolderCode()
	{
		return phoneConfirmHolderCode;
	}

	public void setPhoneConfirmHolderCode(String[] phoneConfirmHolderCode)
	{
		this.phoneConfirmHolderCode = phoneConfirmHolderCode;
	}

	public String[] getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String[] phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String[] getMobilePhoneOperator()
	{
		return mobilePhoneOperator;
	}

	public void setMobilePhoneOperator(String[] mobilePhoneOperator)
	{
		this.mobilePhoneOperator = mobilePhoneOperator;
	}

	public String getBankInfoAgencyId()
	{
		return bankInfoAgencyId;
	}

	public void setBankInfoAgencyId(String bankInfoAgencyId)
	{
		this.bankInfoAgencyId = bankInfoAgencyId;
	}

	public String getBankInfoBranchId()
	{
		return bankInfoBranchId;
	}

	public void setBankInfoBranchId(String bankInfoBranchId)
	{
		this.bankInfoBranchId = bankInfoBranchId;
	}

	public String getBankInfoRegionId()
	{
		return bankInfoRegionId;
	}

	public void setBankInfoRegionId(String bankInfoRegionId)
	{
		this.bankInfoRegionId = bankInfoRegionId;
	}

    public String getVisibleResources() {
        return visibleResources;
    }

    public void setVisibleResources(String visibleResources) {
        this.visibleResources = visibleResources;
    }

	public String[] getOldLastName()
	{
		return oldLastName;
	}

	public void setOldLastName(String[] oldLastName)
	{
		this.oldLastName = oldLastName;
	}

	public String[] getOldFirstName()
	{
		return oldFirstName;
	}

	public void setOldFirstName(String[] oldFirstName)
	{
		this.oldFirstName = oldFirstName;
	}

	public String[] getOldMiddleName()
	{
		return oldMiddleName;
	}

	public void setOldMiddleName(String[] oldMiddleName)
	{
		this.oldMiddleName = oldMiddleName;
	}

	public String[] getOldBirthday()
	{
		return oldBirthday;
	}

	public void setOldBirthday(String[] oldBirthday)
	{
		this.oldBirthday = oldBirthday;
	}

	public String[] getOldIdSeries()
	{
		return oldIdSeries;
	}

	public void setOldIdSeries(String[] oldIdSeries)
	{
		this.oldIdSeries = oldIdSeries;
	}

	public String[] getOldIdNum()
	{
		return oldIdNum;
	}

	public void setOldIdNum(String[] oldIdNum)
	{
		this.oldIdNum = oldIdNum;
	}

	public String[] getOldIdType()
	{
		return oldIdType;
	}

	public void setOldIdType(String[] oldIdType)
	{
		this.oldIdType = oldIdType;
	}
}


