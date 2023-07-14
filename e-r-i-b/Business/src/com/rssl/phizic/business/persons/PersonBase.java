package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.person.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 15.09.2005
 * Time: 15:57:12
 */
@SuppressWarnings({"OverlyComplexClass", "ClassWithTooManyFields", "OverlyLongMethod", "UNUSED_SYMBOL"})
public abstract class PersonBase implements Person
{
	private static final String STORE_SECURITY_TYPE_KEY = "STORE_SECURITY_TYPE";

	private static final SubscriptionService subscriptionService = new SubscriptionService();
	private static final DepartmentService departmentService = new DepartmentService();

    private Long     id;
    private String   clientId;
    private Login    login;
    private String   firstName;
    private String   surName;
    private String   patrName;
    private Calendar birthDay;
    private String   birthPlace;
	private Set<PersonDocument> personDocuments = new HashSet<PersonDocument>();
	private Address  registrationAddress;
    private String   homePhone;
    private String   jobPhone;
    private Long     trustingPersonId;  // ID доверяющего лица (то есть, это поле заполнено у представителя, у клиента= налл)
    private Address  residenceAddress;
    private String   mobileOperator;
    private String   agreementNumber;
    private Calendar agreementDate;
    private Calendar serviceInsertionDate;
    private String   gender="M";
    private String   citizenship;
    private Calendar prolongationRejectionDate;
	private Long     departmentId;
    private String   contractCancellationCouse;
	private String   secretWord;
	private Boolean isResident;
	private String displayClientId;
	private CreationType creationType;
	private Calendar lastUpdateDate;
	private Boolean isRegisteredInDepo; //признак регистрации в депозитарии
	private MDMState mdmState = MDMState.NOT_SENT;
	private String employeeId;
	private SegmentCodeType segmentCodeType;
	private String tarifPlanCodeType;
	private Calendar tarifPlanConnectionDate;
	private String managerId;
	private SecurityType securityType;
	private SecurityType storeSecurityType;
	private String managerTB;
	private PersonalSubscriptionData subscriptionData;
	private String managerOSB;
	private String managerVSP;
	private ErmbProfile ermbProfile;

	protected PersonBase()
    {
    }

    protected PersonBase(Person person)
    {
        this.id                        = person.getId();
        this.clientId                  = person.getClientId();
        this.login                     = person.getLogin();
        this.firstName                 = person.getFirstName();
        this.surName                   = person.getSurName();
        this.patrName                  = person.getPatrName();
        this.birthDay                  = person.getBirthDay();
        this.birthPlace                = person.getBirthPlace();

	    Set<PersonDocument> newDocuments = new HashSet<PersonDocument>(person.getPersonDocuments().size());
	    for (PersonDocument personDocument : person.getPersonDocuments())
	    {
		    newDocuments.add(new PersonDocumentImpl(personDocument));
	    }
	    this.personDocuments           = newDocuments;
	    this.registrationAddress       = person.getRegistrationAddress();
        this.homePhone                 = person.getHomePhone();
        this.jobPhone                  = person.getJobPhone();
        this.trustingPersonId          = person.getTrustingPersonId();
        this.residenceAddress          = person.getResidenceAddress();
        this.mobileOperator            = person.getMobileOperator();
        this.agreementNumber           = person.getAgreementNumber();
        this.agreementDate             = person.getAgreementDate();
        this.serviceInsertionDate      = person.getServiceInsertionDate();
        this.gender                    = person.getGender();
        this.citizenship = person.getCitizenship();
        this.prolongationRejectionDate = person.getProlongationRejectionDate();
	    this.departmentId              = person.getDepartmentId();
        this.contractCancellationCouse = person.getContractCancellationCouse();
	    this.secretWord                = person.getSecretWord();
	    this.isResident                = person.getIsResident();
	    this.displayClientId           = person.getDisplayClientId();
	    this.creationType              = person.getCreationType();
	    this.lastUpdateDate            = person.getLastUpdateDate();
	    this.isRegisteredInDepo        = person.getIsRegisteredInDepo();
	    this.mdmState                  = person.getMdmState();
	    this.employeeId                = person.getEmployeeId();
	    this.securityType              = person.getSecurityType();
	    this.managerTB                 = person.getManagerTB();
	    this.managerOSB                = person.getManagerOSB();
	    this.managerVSP                = person.getManagerVSP();
      }

	public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public Login getLogin()
    {
        return login;
    }

    public void setLogin(Login login)
    {
        this.login = login;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurName()
    {
        return surName;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public String getPatrName()
    {
        return patrName;
    }

    public void setPatrName(String patrName)
    {
        this.patrName = patrName;
    }

    public Calendar getBirthDay()
    {
        return birthDay;
    }

    public String getBirthPlace()
    {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    public void setBirthDay(Calendar birthDay)
    {
        this.birthDay = birthDay;
    }

	public Address getRegistrationAddress()
	{
		return registrationAddress;
	}

	public void setRegistrationAddress (Address registrationAddress)
    {
        this.registrationAddress = registrationAddress;
    }

    public String getEmail()
    {
	    if(getSubscriptionData() != null)
	        return getSubscriptionData().getEmailAddress();
	    return null;
    }

	public MailFormat getMailFormat()
	{
		if(getSubscriptionData() != null)
	        return getSubscriptionData().getMailFormat();
	    return null;
	}
    public String getHomePhone()
    {
        return homePhone;
    }

    public void setHomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }

    public String getJobPhone()
    {
        return jobPhone;
    }

    public void setJobPhone(String jobPhone)
    {
        this.jobPhone = jobPhone;
    }

    public String getMobilePhone()
    {
	    if(getSubscriptionData() != null)
            return getSubscriptionData().getMobilePhone();
        return null;
    }


    public TranslitMode getSMSFormat()
    {
	    if(getSubscriptionData() != null)
            return getSubscriptionData().getSmsTranslitMode();
        return null;
    }


    public String toString()
    {
        return getFullName();
    }

    public String getFullName()
    {
        StringBuffer buf = new StringBuffer();

        if (getSurName() != null)
        {
            buf.append(getSurName());
        }

        if (getFirstName() != null)
        {
            if(buf.length() > 0) buf.append(" ");
            buf.append(getFirstName());
        }

        if (getPatrName() != null)
        {
            if (buf.length() > 0) buf.append(" ");
            buf.append(getPatrName());
        }

        return buf.toString();
    }

	/**
	 * Получает ФИО.
	 * @return ФИО в формате Фамилия И. О.
	 */
	public String getFIO()
    {
        StringBuffer buf = new StringBuffer();

        if (!StringHelper.isEmpty(getSurName()))
        {
            buf.append(getSurName());
        }

        if (!StringHelper.isEmpty(getFirstName()))
        {
            if(buf.length() > 0) buf.append(" ");
            buf.append(getFirstName().substring(0,1));
	        buf.append(".");
        }

	    if (!StringHelper.isEmpty(getPatrName()))
        {
            buf.append(getPatrName().substring(0,1));
	        if (buf.length() > 0) buf.append(".");
        }

        return buf.toString();
    }

	/**
	 * Получает Имя Отчество клиента
	 * @return Имя Отчество
	 */
	public String getFirstPatrName()
    {
        StringBuffer buf = new StringBuffer();

        if (!StringHelper.isEmpty(getFirstName()))
        {
            buf.append(getFirstName());
        }

        if (!StringHelper.isEmpty(getPatrName()))
        {
            if (buf.length() > 0) buf.append(" ");
            buf.append(getPatrName());
        }

        return buf.toString();
    }

    public Long getTrustingPersonId()
    {
        return trustingPersonId;
    }

    public void setTrustingPersonId(Long trustingPersonId)
    {
        this.trustingPersonId = trustingPersonId;
    }

    public String getResidenceAddressString()
    {
	    if (residenceAddress != null)
	        return residenceAddress.toString();
	    return "";
    }

	public Address getResidenceAddress()
    {
        return residenceAddress;
    }

    public void setResidenceAddress(Address residenceAddress)
    {
        this.residenceAddress = residenceAddress;
    }

    public String getMobileOperator()
    {
        return mobileOperator;
    }

    public void setMobileOperator(String mobileOperator)
    {
        this.mobileOperator = mobileOperator;
    }

    public String getAgreementNumber()
    {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber)
    {
        this.agreementNumber = agreementNumber;
    }

    public Calendar getAgreementDate()
    {
        return agreementDate;
    }

    public void setAgreementDate(Calendar agreementDate)
    {
        this.agreementDate = agreementDate;
    }

    public Calendar getServiceInsertionDate()
    {
        return serviceInsertionDate;
    }

    public void setServiceInsertionDate(Calendar serviceInsertionDate)
    {
        this.serviceInsertionDate = serviceInsertionDate;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getCitizenship()
    {
        return citizenship;
    }

    public void setCitizenship(String citizen)
    {
        this.citizenship = citizen;
    }

    public String getInn()
    {
        return UserDocumentService.get().getUserDocumentNumber(getLogin(), DocumentType.INN);
    }

    public Calendar getProlongationRejectionDate()
    {
        return prolongationRejectionDate;
    }

    public void setProlongationRejectionDate(Calendar prolongationRejectionDate)
    {
        this.prolongationRejectionDate = prolongationRejectionDate;
    }

	public void setDiscriminator(String value)
	{
		return;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long depertmentId)
	{
		this.departmentId = depertmentId;
	}

    public String getContractCancellationCouse()
    {
        return contractCancellationCouse;
    }

    public void setContractCancellationCouse(String contractCancellationCouse)
    {
        this.contractCancellationCouse = contractCancellationCouse;
    }

	public String getSecretWord()
	{
		return secretWord;
	}

	public void setSecretWord(String secretWord)
	{
		this.secretWord = secretWord;
	}

	public Boolean getIsResident()
	{
		return this.isResident;
	}

	public void setIsResident(Boolean isResident)
	{
		this.isResident = isResident;
	}

	public void setPersonDocuments(Set<PersonDocument> documents)
	{
		this.personDocuments = documents;
	}

	public Set<PersonDocument> getPersonDocuments()
	{
		return personDocuments;
	}

	public void setDisplayClientId(String displayClientId)
	{
		this.displayClientId = displayClientId;
	}

	public String getDisplayClientId()
	{
		return displayClientId;
	}

	public CreationType getCreationType()
	{
		return creationType;
	}

	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	public Boolean getIsRegisteredInDepo()
	{
		return isRegisteredInDepo;
	}

	public void setIsRegisteredInDepo(Boolean registeredInDepo)
	{
		isRegisteredInDepo = registeredInDepo;
	}

	public MDMState getMdmState()
	{
		return mdmState;
	}

	public void setMdmState(MDMState mdmState)
	{
		this.mdmState = mdmState;
	}

	public byte[] getSignableObject()
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

	public String getSNILS()
	{
		return UserDocumentService.get().getUserDocumentNumber(getLogin(), DocumentType.SNILS);
	}

	public String getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}

	public boolean getIsERIBPerson()
	{
		return getLogin().getLogonDate() != null;
	}

	public SegmentCodeType getSegmentCodeType()
	{
		return segmentCodeType;
	}

	public void setSegmentCodeType(SegmentCodeType segmentCodeType)
	{
		this.segmentCodeType = segmentCodeType;
	}

	public String getTarifPlanCodeType()
	{
		return tarifPlanCodeType;
	}

	public void setTarifPlanCodeType(String tarifPlanCodeType)
	{
		this.tarifPlanCodeType = tarifPlanCodeType;
	}

	public Calendar getTarifPlanConnectionDate()
	{
		return tarifPlanConnectionDate;
	}

	public void setTarifPlanConnectionDate(Calendar tarifPlanConnectionDate)
	{
		this.tarifPlanConnectionDate = tarifPlanConnectionDate;
	}

	public String getManagerId()
	{
		return managerId;
	}

	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	public SecurityType getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(SecurityType securityType)
	{
		this.securityType = securityType;
	}

	public ErmbProfile getErmbProfile()
	{
		return ermbProfile;
	}

	public void setErmbProfile(ErmbProfile ermbProfile)
	{
		this.ermbProfile = ermbProfile;
	}

	private PersonalSubscriptionData getSubscriptionData()
	{
		if (subscriptionData == null)
		{
			if(login == null)
				return null;
			try
			{
				subscriptionData = subscriptionService.findPersonalData(this.login);
			}
			catch (BusinessException e)
			{
				throw new RuntimeException(e);
			}
		}
		return subscriptionData;
	}


	/**
	 * обновляем информацию о PersonalSubscriptionData
	 */
	public void refreshPersonSubscriptionData()
	{
		subscriptionData = null;
	}

	public String getManagerTB()
	{
		return managerTB;
	}

	public void setManagerTB(String managerTB)
	{
		this.managerTB = managerTB;
	}

	public String getManagerOSB()
	{
		return managerOSB;
	}

	public void setManagerOSB(String managerOSB)
	{
		this.managerOSB = managerOSB;
	}

	public String getManagerVSP()
	{
		return managerVSP;
	}

	public void setManagerVSP(String managerVSP)
	{
		this.managerVSP = managerVSP;
	}

	public ClientData asClientData() throws IKFLMessagingException
	{
        return new ClientDataImpl(this);
	}

	/**
	 * @return ключевые данные клиента
	 */
	public PersonKey getPersonKey() throws BusinessException
	{
		PersonKey personKey = new PersonKey();
		personKey.setLoginId(getLogin().getId());
		personKey.setFirstName(getFirstName());
		personKey.setSurName(getSurName());
		personKey.setPatrName(getPatrName());
		List<PersonDocument> documents = new ArrayList<PersonDocument>(getPersonDocuments());
		Collections.sort(documents, new PersonDocumentTypeComparator());
		PersonDocument document = documents.get(0);
		personKey.setPassport((StringHelper.getEmptyIfNull(document.getDocumentSeries()) + StringHelper.getEmptyIfNull(document.getDocumentNumber())).replaceAll(" ", ""));
		personKey.setBirthDay(getBirthDay());
		Department department = departmentService.findById(getDepartmentId());
		if (department != null)
			personKey.setTb(department.getRegion());
		personKey.setCreationDate(Calendar.getInstance());

		return personKey;
	}

	public SecurityType getStoreSecurityType()
	{
		return storeSecurityType;
	}

	public void setStoreSecurityType(SecurityType storeSecurityType)
	{
		this.storeSecurityType = storeSecurityType;
	}

	/**
	 * Сохранить уровень безопасности, если низки - в сессию, иначе в БД
	 * @param storeSecurityType уровень безопасности
	 */
	public void saveStoreSecurityType(SecurityType storeSecurityType)
	{
		if (SecurityType.LOW == storeSecurityType)
		{
			//если уровень безопасности низкий, записываем в сесиию, а не в БД
			StoreManager.getCurrentStore().save(STORE_SECURITY_TYPE_KEY, storeSecurityType.name());
		}
		else
		{
			//иначе пишем в БД
			this.storeSecurityType = storeSecurityType;
		}
	}

	public SecurityType restoreSecurityType()
	{
		String securityTypeValue = (String) StoreManager.getCurrentStore().restore(STORE_SECURITY_TYPE_KEY);
		//если в сессии хранится низкий уровень безопасности, возвращаем его
		if (StringHelper.isNotEmpty(securityTypeValue) && SecurityType.LOW == SecurityType.valueOf(securityTypeValue))
		{
			return SecurityType.LOW;
		}
		//если уровень безопасности в БД низкий, то записываем в сесиию (обработка ситуации, когда подтвержден платеж в КЦ)
		if (SecurityType.LOW == storeSecurityType)
		{
			StoreManager.getCurrentStore().save(STORE_SECURITY_TYPE_KEY, storeSecurityType.name());
		}
		//возвращем уровень безопасности из БД
		return storeSecurityType;
	}
}
