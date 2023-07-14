package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.ikfl.crediting.ClaimNumberGenerator;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.GuestLoginImpl;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.NotResidentException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Balovtsev
 * @since 21.04.2015.
 */
public class LoanCardClaim extends GateExecutableDocument
{
	private static final SimpleService simpleService = new SimpleService();
	private static final RegionDictionaryService regionService = new RegionDictionaryService();

	private static final String CREDIT_CARD_ATTRIBUTE_NAME                 = "credit-card"; // значение поля «Кредитная карта»
	private static final String GRACE_PERIOD_DURATION_ATTRIBUTE_NAME       = "grace-period-duration"; // льготный период до
	private static final String GRACE_PERIOD_RATE                          = "grace-period-interest-rate"; //% ставка в льготный период
	private static final String FIRST_YEAR_PAY                             = "first-year-service"; // плата за годовое обслуживание: первый год
	private static final String NEXT_YEAR_PAY                              = "next-year-service"; // плата за последующие года
	private static final String ADDITIONAL_TERMS                           = "additionalTerms"; // дополнительные условия
	private static final String MIN_LIMIT                                  = "min-limit"; // минимальный лимит
	private static final String MAX_LIMIT                                  = "max-limit"; // максимальный лимит
	private static final String MAX_LIMIT_INCLUDE                          = "max-limit-include"; // макс. лимит включительно
	private static final String INTEREST_RATE                              = "interest-rate";	// льготный период включен
	private static final String CREDIT_CARD_OFFICE                         = "credit-card-office"; // место получения кредитной карты
	private static final String OFFER_ID_ATTRIBUTE_NAME                    = "offer-id"; // id предложения
	private static final String OFFER_TYPE_ATTRIBUTE_NAME                  = "offer-type"; // тип предложения
	private static final String LOAN_ATTRIBUTE_NAME                        = "loan"; // id предложения или id условия в разрезе валюты
	private static final String DURATION_ATTRIBUTE_NAME                    = "duration"; // срок кредита
	private static final String CHANGE_DATE_ATTRIBUTE_NAME                 = "changeDate"; // дата создания
	private static final String ATTRIBUTE_CLAIM_NUMBER                     = "claimNumber"; // номер заявки
	private static final String OWNER_LAST_NAME                            = "ownerLastName";
	private static final String OWNER_FIRST_NAME                           = "ownerFirstName";
	private static final String OWNER_MIDDLE_NAME                          = "ownerMiddleName";
	private static final String OWNER_GUEST_PHONE                          = "ownerGuestPhone";
	private static final String OWNER_BIRTHDAY                             = "ownerBirthday";
	private static final String OWNER_ID_CARD_NUMBER                       = "ownerIdCardNumber";
	private static final String OWNER_ID_CARD_SERIES                       = "ownerIdCardSeries";
	private static final String REFUSE_REASON                              = "refuseReason";
	private static final String AMOUNT_ATTRIBUTE_NAME                      = "amount";
	private static final String CURRENCY_ATTRIBUTE_NAME                    = "currency";
	private static final String EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME          = "exact-amount";
	private static final String PREAPPROVED_FIELD_ATTRIBUTE_NAME           = "preapproved";
	public static String REGION_ID = "regionId";
	public static String REGION_NAME = "regionName";
	public static String PERSON_TB = "personTB";

	private String       nextState;
	private Boolean      preapproved = Boolean.FALSE;
	private String       tb;
	private String       osb;
	private String       vsp;
	private Long         ownerLoginId;
	private Long         ownerGuestId;
	private String       ownerGuestPhone;
	private Boolean      ownerGuestMbk;
	private String       ownerFirstName;
	private String       ownerLastName;
	private String       ownerMiddleName;
	private Calendar     ownerBirthday;
	private String       ownerIdCardSeries;
	private String       ownerIdCardNumber;
	private String       ownerOsb;
	private String       ownerVsp;
	private LoginType    ownerLoginType;
	private String       ownerTb;
	private Money        destinationAmount;
	private Money        chargeOffAmount;
	private String       receiverName;
	private InputSumType inputSumType;
    private String       creditCard;
    private String       currency;

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		setPreapproved(StringHelper.isNotEmpty(getOfferId()));
		setClaimNumber();
		try
		{
			if (getOwner().isGuest())
			{
				Long regionId = getPersonTB();
				if (regionId == null)
				{
					List<Region> regions = regionService.getAllRegions(null);
					if (!regions.isEmpty())
					{
						Region region = regions.get(0);
						setRegionId(region.getId());
						setRegionName(region.getName());
						setPersonTB(region.getCodeTB()==null ? null : Long.valueOf(region.getCodeTB()));
					}

				}
				else
				{
					setRegionId(regionId);
					setRegionName(regionService.findByIdFromCache(regionId).getName());
					setPersonTB(Long.valueOf(getOwnerTb()));
				}
			}
		}
		catch (BusinessException e)
		{
			log.error(e);
		}
	}

	@Override
	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		Element root = document.getDocumentElement();

		InputSumType sumType = InputSumType.fromValue(XmlHelper.getSimpleElementValue(document.getDocumentElement(), EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME));
		if (sumType == null)
		{
			setDestinationAmount(null);
		}
		else
		{
			String value    = getNullSaveAttributeStringValue(AMOUNT_ATTRIBUTE_NAME);
			String currency = XmlHelper.getSimpleElementValue(root, CURRENCY_ATTRIBUTE_NAME);
            setCurrency(currency);
			setDestinationAmount(createMoney(value, currency));
		}

		String preapprovedValue = XmlHelper.getSimpleElementValue(root, PREAPPROVED_FIELD_ATTRIBUTE_NAME);
		if (StringHelper.isNotEmpty(preapprovedValue))
		{
			setPreapproved(Boolean.parseBoolean(preapprovedValue));
		}

		setReceiverName(XmlHelper.getSimpleElementValue(root, RECEIVER_NAME_ATTRIBUTE_NAME));
		setCreditCard(XmlHelper.getSimpleElementValue(root, CREDIT_CARD_ATTRIBUTE_NAME));

		setOwnerLastName(XmlHelper.getSimpleElementValue(root, OWNER_LAST_NAME));
		setOwnerFirstName(XmlHelper.getSimpleElementValue(root, OWNER_FIRST_NAME));
		setOwnerMiddleName(XmlHelper.getSimpleElementValue(root, OWNER_MIDDLE_NAME));

		Calendar birthDay = getDateFromDom(root, OWNER_BIRTHDAY);
		if (birthDay != null)
		{
			setOwnerBirthday(birthDay);
		}

		String cardNumber = XmlHelper.getSimpleElementValue(root, OWNER_ID_CARD_NUMBER);
		if (StringHelper.isNotEmpty(cardNumber))
		{
			setOwnerIdCardNumber(cardNumber);
		}
		String cardSeries = XmlHelper.getSimpleElementValue(root, OWNER_ID_CARD_SERIES);
		if (StringHelper.isNotEmpty(cardSeries))
		{
			setOwnerIdCardSeries(cardSeries);
		}
	}

	@Override
	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element  element  = document.getDocumentElement();

		appendNullSaveString(element, REFUSE_REASON,                getRefusingReason());
		appendNullSaveDate  (element, OWNER_BIRTHDAY,               getOwnerBirthday());
		appendNullSaveString(element, OWNER_LAST_NAME,              getOwnerLastName());
		appendNullSaveString(element, OWNER_FIRST_NAME,             getOwnerFirstName());
		appendNullSaveString(element, OWNER_MIDDLE_NAME,            getOwnerMiddleName());
		appendNullSaveString(element, OWNER_ID_CARD_NUMBER,         getOwnerIdCardNumber());
		appendNullSaveString(element, OWNER_ID_CARD_SERIES,         getOwnerIdCardSeries());
		appendNullSaveString(element, RECEIVER_NAME_ATTRIBUTE_NAME, getReceiverName());
		appendNullSaveString(element, PREAPPROVED_FIELD_ATTRIBUTE_NAME, getPreapproved().toString());
		appendNullSaveString(element, OWNER_GUEST_PHONE, getOwnerGuestPhone());

		Money amount = getDestinationAmount();
		if (amount != null)
		{
			appendNullSaveString(element, AMOUNT_ATTRIBUTE_NAME,   amount.getDecimal().toPlainString());
			appendNullSaveString(element, CURRENCY_ATTRIBUTE_NAME, amount.getCurrency().getCode());
		}

		appendNullSaveString(element, EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME, getInputSumType() == null ? null: getInputSumType().toValue());
		appendNullSaveString(element, CREDIT_CARD_ATTRIBUTE_NAME, getCreditCard());
		return document;
	}

	public AbstractPaymentDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		LoanClaimBase claim = (LoanClaimBase) super.createCopy(instanceClass);
		setClaimNumber();
		return claim;
	}

	@Override
	public void setDepartment(Department department)
	{
		if (clientLogin != null)
		{
			tb  = department.getRegion();
			osb = department.getOSB();
			vsp = department.getVSP();
		}
		else
		{
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			tb  = loanClaimConfig.getGuestLoanDepartmentTb();
			osb = loanClaimConfig.getGuestLoanDepartmentOsb();
			vsp = loanClaimConfig.getGuestLoanDepartmentVsp();
		}
	}

	@Override
	public Department getDepartment() throws BusinessException
	{
		if (StringHelper.isEmpty(tb)  &&
			StringHelper.isEmpty(osb) &&
			StringHelper.isEmpty(vsp))
		{
			return null;
		}

		return departmentService.getDepartment(tb, osb, vsp);
	}

	@Override
	public BusinessDocumentOwner getOwner() throws BusinessException
	{
		PersonDocument personDocument = new PersonDocumentImpl();
		personDocument.setDocumentNumber(ownerIdCardNumber);
		personDocument.setDocumentSeries(ownerIdCardSeries);

		if (ownerLoginId != null)
		{
			clientLogin = simpleService.findById(Login.class, ownerLoginId);

			Login        login  = simpleService.findById(Login.class, ownerLoginId);
			ActivePerson active = personService.findByLogin(clientLogin.getId());
			ActivePerson person = new ActivePerson();

			person.setLogin(login);
			person.setId(active.getId());
			person.setFirstName(ownerFirstName);
			person.setSurName(ownerLastName);
			person.setPatrName(ownerMiddleName);
			person.setBirthDay(ownerBirthday);
			person.setClientId(active.getClientId());

			Department ownerDepartment = departmentService.getDepartment(ownerTb, ownerOsb, ownerVsp);
			person.setDepartmentId(ownerDepartment.getId());
			person.setBirthDay(getOwnerBirthday());
			personDocument.setDocumentType(PersonDocumentType.REGULAR_PASSPORT_RF);
			person.setPersonDocuments(Collections.singleton(personDocument));

			return new ClientBusinessDocumentOwner(person);
		}
		else
		{
			GuestLogin  guestLogin  = new GuestLoginImpl(ownerGuestPhone, ownerGuestId);
			GuestPerson guestPerson = new GuestPerson();

			guestPerson.setHaveMBKConnection(ownerGuestMbk);
			guestPerson.setFirstName(ownerFirstName);
			guestPerson.setSurName(ownerLastName);
			guestPerson.setPatrName(ownerMiddleName);
			guestPerson.setBirthDay(ownerBirthday);
			guestPerson.setPersonDocuments(Collections.singleton(personDocument));

			return new GuestBusinessDocumentOwner(guestLogin, guestPerson);
		}
	}

	@Override
	public void setOwner(BusinessDocumentOwner owner) throws BusinessException
	{
		Login        login  = owner.getLogin();
		ActivePerson person = owner.getPerson();

		if (login instanceof GuestLogin)
		{
			GuestPerson     guestPerson     = (GuestPerson) person;
			GuestLogin      guestLogin      = (GuestLogin) login;
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);

			ownerGuestMbk   = guestPerson.isHaveMBKConnection();
			ownerGuestPhone = guestLogin.getAuthPhone();
			ownerGuestId    = guestLogin.getGuestCode();
			ownerTb         = loanClaimConfig.getGuestLoanDepartmentTb();
			ownerOsb        = loanClaimConfig.getGuestLoanDepartmentOsb();
			ownerVsp        = loanClaimConfig.getGuestLoanDepartmentVsp();

			ownerLoginType = LoginType.GUEST;

			Set<PersonDocument> documents = person.getPersonDocuments();
			if (documents != null && !documents.isEmpty())
			{
				PersonDocument mainDocument = documents.size() == 1 ? documents.iterator().next() : getRegularPassportRF(documents);
				if (mainDocument != null)
				{
					ownerIdCardNumber = mainDocument.getDocumentNumber();
					ownerIdCardSeries = mainDocument.getDocumentSeries();
				}
			}
		}
		else
		{
			clientLogin  = login;
			ownerLoginId = login.getId();

			Department department = departmentService.findById(owner.getPerson().getDepartmentId());
			if(department != null)
			{
				ownerTb  = department.getRegion();
				ownerOsb = department.getOSB();
				ownerVsp = department.getVSP();
			}

			Set<PersonDocument> documents    = person.getPersonDocuments();
			PersonDocument passportRF = getRegularPassportRF(documents);
			if (passportRF == null)
			{
				log.error("Невозможно создать заявку, т.к. у клиента нет паспорт РФ");
				throw new NotResidentException("Данная операция доступна только для резидентов РФ");
			}

			ownerIdCardNumber = passportRF.getDocumentNumber();
			ownerIdCardSeries = passportRF.getDocumentSeries();
		}

		ownerFirstName  = person.getFirstName();
		ownerLastName   = person.getSurName();
		ownerMiddleName = person.getPatrName();
		ownerBirthday   = person.getBirthDay();
	}

	private PersonDocument getRegularPassportRF(Set<PersonDocument> docs)
	{
		for (PersonDocument doc : docs)
		{
			if (doc.getDocumentType() == PersonDocumentType.REGULAR_PASSPORT_RF)
			{
				return doc;
			}
		}

		return null;
	}

	@Override
	public ExtendedAttribute createAttribute(String type, String name, Object value)
	{
		ExtendedAttribute attribute = super.createAttribute(type, name, value);
		attribute.setDateCreated(getDateCreated());
		return attribute;
	}

	@Override
	public ExtendedAttribute createAttribute(String type, String name, String value)
	{
		ExtendedAttribute attribute = super.createAttribute(type, name, value);
		attribute.setDateCreated(getDateCreated());
		return attribute;
	}

	public LoginType getOwnerLoginType()
	{
		return ownerLoginType;
	}

	public void setOwnerLoginType(LoginType ownerLoginType)
	{
		this.ownerLoginType = ownerLoginType;
	}

	public String getOwnerVsp()
	{
		return ownerVsp;
	}

	public void setOwnerVsp(String ownerVsp)
	{
		this.ownerVsp = ownerVsp;
	}

	public String getOwnerOsb()
	{
		return ownerOsb;
	}

	public void setOwnerOsb(String ownerOsb)
	{
		this.ownerOsb = ownerOsb;
	}

	public String getOwnerTb()
	{
		return ownerTb;
	}

	public void setOwnerTb(String ownerTb)
	{
		this.ownerTb = ownerTb;
	}

	public String getOwnerIdCardNumber()
	{
		return ownerIdCardNumber;
	}

	public void setOwnerIdCardNumber(String ownerIdCardNumber)
	{
		this.ownerIdCardNumber = ownerIdCardNumber;
	}

	public String getOwnerIdCardSeries()
	{
		return ownerIdCardSeries;
	}

	public void setOwnerIdCardSeries(String ownerIdCardSeries)
	{
		this.ownerIdCardSeries = ownerIdCardSeries;
	}

	public Calendar getOwnerBirthday()
	{
		return ownerBirthday;
	}

	public void setOwnerBirthday(Calendar ownerBirthday)
	{
		this.ownerBirthday = ownerBirthday;
	}

	public String getOwnerMiddleName()
	{
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(String ownerMiddleName)
	{
		this.ownerMiddleName = ownerMiddleName;
	}

	public String getOwnerLastName()
	{
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName)
	{
		this.ownerLastName = ownerLastName;
	}

	public String getOwnerFirstName()
	{
		return ownerFirstName;
	}

	public void setOwnerFirstName(String ownerFirstName)
	{
		this.ownerFirstName = ownerFirstName;
	}

	public Boolean getOwnerGuestMbk()
	{
		return ownerGuestMbk;
	}

	public void setOwnerGuestMbk(Boolean ownerGuestMbk)
	{
		this.ownerGuestMbk = ownerGuestMbk;
	}

	public String getVsp()
	{
		return vsp;
	}

	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	public String getOsb()
	{
		return osb;
	}

	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getOwnerGuestPhone()
	{
		return ownerGuestPhone;
	}

	public void setOwnerGuestPhone(String ownerGuestPhone)
	{
		this.ownerGuestPhone = ownerGuestPhone;
	}

	public Long getOwnerGuestId()
	{
		return ownerGuestId;
	}

	public void setOwnerGuestId(Long ownerGuestId)
	{
		this.ownerGuestId = ownerGuestId;
	}

	public Long getOwnerLoginId()
	{
		return ownerLoginId;
	}

	public void setOwnerLoginId(Long ownerLoginId)
	{
		this.ownerLoginId = ownerLoginId;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public String getNextState()
	{
		return nextState;
	}

	public void setNextState(String nextState)
	{
		this.nextState = nextState;
	}

	public String getLoan()
	{
		return getNullSaveAttributeStringValue(LOAN_ATTRIBUTE_NAME);
	}

	public Long getDuration()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(DURATION_ATTRIBUTE_NAME));
	}

	public Long getChangeDate()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(CHANGE_DATE_ATTRIBUTE_NAME));
	}

	private void setClaimNumber() throws DocumentException
	{
		ClaimNumberGenerator generator = new ClaimNumberGenerator();

		try
		{
			setOperationUID(generator.execute());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public void setClaimNumber(String claimNumber)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CLAIM_NUMBER, claimNumber);
	}

	public String getClaimNumber()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_CLAIM_NUMBER);
	}

	public String getGracePeriodDuration()
	{
		return getNullSaveAttributeStringValue(GRACE_PERIOD_DURATION_ATTRIBUTE_NAME);
	}

	public String getGracePeriodRate()
	{
		return getNullSaveAttributeStringValue(GRACE_PERIOD_RATE);
	}

	public String getFirstYearPay()
	{
		return getNullSaveAttributeStringValue(FIRST_YEAR_PAY);
	}

	public String getNextYearPay()
	{
		return getNullSaveAttributeStringValue(NEXT_YEAR_PAY);
	}

	public String getAdditionalTerms()
	{
		return getNullSaveAttributeStringValue(ADDITIONAL_TERMS);
	}

	public String getMinLimit()
	{
		return getNullSaveAttributeStringValue(MIN_LIMIT);
	}

	public String getMaxLimit()
	{
		return getNullSaveAttributeStringValue(MAX_LIMIT);
	}

	public String getMaxLimitInclude()
	{
		return getNullSaveAttributeStringValue(MAX_LIMIT_INCLUDE);
	}

	public String getInterestRate()
	{
		return getNullSaveAttributeStringValue(INTEREST_RATE);
	}

	public String getCreditCardOffice()
	{
		return getNullSaveAttributeStringValue(CREDIT_CARD_OFFICE);
	}

	/**
	 * @return id предложения
	 */
	public String getOfferId()
	{
		return getNullSaveAttributeStringValue(OFFER_ID_ATTRIBUTE_NAME);
	}

	/**
	 * @return получает номер типа предложения (в таком виде оно у нас хранится у платежа в БД)
	 */
	public Long getOfferType()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(OFFER_TYPE_ATTRIBUTE_NAME));
	}

	/**
	 * @return получет тип предложения
	 */
	public LoanCardOfferType getOfferTypeString()
	{
		return LoanCardOfferType.valueOf(Integer.valueOf(getNullSaveAttributeStringValue(OFFER_TYPE_ATTRIBUTE_NAME)));
	}

	/**
	 * @return Имя поля, по которому определяется дублирование заявки
	 */
	public String getIdentityFieldName()
	{
		return null;
	}

	/**
	 * @return Значение поля, по которому определяется дублирование заявки
	 */
	public String getIdentityFieldValue()
	{
		return null;
	}

	/**
	 * Фактичский тип документа
	 */
	public Class<? extends GateDocument> getType()
	{
		return LoanCardClaim.class;
	}

	public Boolean getPreapproved()
	{
		return preapproved;
	}

	public void setPreapproved(Boolean preapproved)
	{
		this.preapproved = preapproved;
	}

	@Override
	public String getFormName()
	{
		return FormConstants.LOAN_CARD_CLAIM;
	}

	@Override
	public void setFormName(String formName)
	{
		if (!FormConstants.LOAN_CARD_CLAIM.equals(formName))
		{
			throw new IllegalArgumentException("Неверное название формы " + formName);
		}

		super.setFormName(formName);
	}

	/**
	 * Сумма зачисления. (без комиссии)
	 *
	 * @return сумма зачисления.
	 */
	public Money getDestinationAmount()
	{
		String value    = getNullSaveAttributeStringValue(AMOUNT_ATTRIBUTE_NAME);
		String currency = getCurrency();
		return createMoney(value, currency);
	}

	/**
	 * Установить сумму зачисления(в том случае, если она изменилась в процессе исполнения платежа)
	 * @param amount сумма зачисленная на счет
	 */
	public void setDestinationAmount(Money amount)
	{
		this.destinationAmount = amount;
	}

	public void setChargeOffAmount(Money chargeOffAmount)
	{
		this.chargeOffAmount = chargeOffAmount;
	}

	public Money getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
		this.inputSumType = inputSumType;
	}

	public void setInputSumType(String inputSumType)
	{
		if(inputSumType == null || inputSumType.trim().length() == 0)
		{
			return;
		}

		this.inputSumType = Enum.valueOf(InputSumType.class, inputSumType);
	}

    public String getCreditCard()
    {
        return creditCard;
    }

    public void setCreditCard(String creditCard)
    {
        this.creditCard = creditCard;
	}

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
	}

	@Override
	public boolean supportStatusHistory()
	{
		return true;
	}

	public Long getRegionId()
	{
		return getNullSaveAttributeLongValue(REGION_ID);
	}

	public void setRegionId(Long regionId)
	{
		setNullSaveAttributeLongValue(REGION_ID, regionId);
	}

	public String getRegionName()
	{
		return getNullSaveAttributeStringValue(REGION_NAME);
	}

	public void setRegionName(String regionName)
	{
		setNullSaveAttributeStringValue(REGION_NAME, regionName);
	}

	public Long getPersonTB()
	{
		return getNullSaveAttributeLongValue(PERSON_TB);
	}

	public void setPersonTB(Long personTB)
	{
		setNullSaveAttributeLongValue(PERSON_TB, personTB);
	}
}
