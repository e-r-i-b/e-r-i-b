package com.rssl.phizic.business.documents.payments;

import com.google.gson.Gson;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.ikfl.crediting.ClaimNumberGenerator;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.ikfl.crediting.OfferResponseChannel;
import com.rssl.ikfl.crediting.OfferTopUp;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.GuestLoginImpl;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.common.CodeImpl;
import com.rssl.phizic.gate.loanclaim.dictionary.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.loanclaim.dictionary.*;
import com.rssl.phizic.gate.loanclaim.type.*;
import com.rssl.phizic.gate.payments.loan.ETSMLoanClaim;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.json.NoPrettyPrintingGsonSingleton;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.util.MoneyFunctions;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;

/**
 * @author Rtischeva
 * @ created 24.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Расширенная заявка на кредит
 */
public class ExtendedLoanClaim extends GateExecutableDocument implements LoanClaim, ETSMLoanClaim
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final LoanClaimDictionaryService loanClaimDictionaryService = new LoanClaimDictionaryService();
	private static final PersonService personService = new PersonService();
	private static final RegionDictionaryService regionService = new RegionDictionaryService();

	private static final String ATTRIBUTE_USE_LOAN_OFFER    = "useLoanOffer";
	private static final String ATTRIBUTE_USE_PERCENT       = "usePercent";
	private static final String ATTRIBUTE_LOAN_OFFER_AMOUNT = "loanOfferAmount";

	private static final String ATTRIBUTE_PERIOD           = "loanPeriod";
	private static final String ATTRIBUTE_RATE             = "loanRate";
	private static final String ATTRIBUTE_LOAN_AMOUNT      = "loanAmount";
	private static final String ATTRIBUTE_LOAN_OFFER_ID    = "loanOfferId";
	private static final String ATTRIBUTE_CAMPAING_MEMBER_ID   = "campaignMemberId";
	private static final String ATTRIBUTE_LOAN_CURRENCY    = "loanCurrency";
	private static final String ATTRIBUTE_PRODUCT_CODE     = "productCode";
	private static final String ATTRIBUTE_SUB_PRODUCT_CODE = "subProductCode";
	private static final String ATTRIBUTE_PRODUCT_TYPE_CODE = "productTypeCode";
	protected static final String ATTRIBUTE_CONDITION_ID      = "condId";
    protected static final String ATTRIBUTE_CONDITION_CURR_ID = "condCurrId";
    protected static final String ATTRIBUTE_PRODUCT_NAME            = "productName";
	protected static final String ATTRIBUTE_COND_MIN_PERCENT_RATE   = "currMinPercentRate";
	protected static final String ATTRIBUTE_COND_MAX_PERCENT_RATE   = "currMaxPercentRate";

	private static final String ATTRIBUTE_SBRF_RELATION  = "sbrf-relation-type";
	private static final String ATTRIBUTE_SBRF_ACCOUNT = "sbrf-account";
	private static final String ATTRIBUTE_SBRF_RESOURCE_ID = "sbrf-resource-id";

	private static final String ATTRIBUTE_FIO_CHANGED   = "fio-changed";
	private static final String ATTRIBUTE_FIO_CHANGED_REASON_TYPE  = "fio-changed-reason-type";
	private static final String ATTRIBUTE_FIO_CHANGED_OTHER_REASON_DESCRIPTION  = "fio-changed-other-reason";
	private static final String ATTRIBUTE_FIO_CHANGED_DATE   = "fio-changed-date";
	private static final String ATTRIBUTE_PREVIOUS_SURNAME  = "previos-surname";
	private static final String ATTRIBUTE_PREVIOUS_FIRSTNAME  = "previos-firstname";
	private static final String ATTRIBUTE_PREVIOUS_PATRNAME  = "previos-patr";

	private static final String ATTRIBUTE_GENDER  = "gender";
	private static final String ATTRIBUTE_BIRTH_PLACE  = "birthPlace";
	private static final String ATTRIBUTE_CITIZENSHIP  = "citizenShip";
	private static final String ATTRIBUTE_FOREIGN_PASSPORT  = "has-foreign-passport";
	private static final String ATTRIBUTE_EMAIL = "email";
	private static final String ATTRIBUTE_EDUCATION = "education-type";
	private static final String ATTRIBUTE_EDUCATION_COURSE = "higher-education-course";

	private static final String ATTRIBUTE_PASSPORT_ISSUE_BY = "passport-issue-by";
	private static final String ATTRIBUTE_PASSPORT_ISSUE_BY_CODE = "passport-issue-by-code";
	private static final String ATTRIBUTE_PASSPORT_ISSUE_DATE = "passport-issue-date";

	private static final String ATTRIBUTE_HAS_OLD_PASSPORT = "has-old-passport";
	private static final String ATTRIBUTE_OLD_PASSPORT_SERIES_AND_NUMBER = "old-passport-series-and-number";
	private static final String ATTRIBUTE_OLD_PASSPORT_ISSUE_BY = "old-passport-issue-by";
	private static final String ATTRIBUTE_OLD_PASSPORT_ISSUE_DATE = "old-passport-issue-date";

	private static final String ATTRIBUTE_RELATIVE_PREFIX = "relative_";
	private static final String ATTRIBUTE_RELATIVE_TYPE = "_relativeType";
	private static final String ATTRIBUTE_CHILD_PREFIX = "child_";
	private static final String ATTRIBUTE_RELATIVE_SURNAME = "_surname";
	private static final String ATTRIBUTE_RELATIVE_NAME = "_name";
	private static final String ATTRIBUTE_RELATIVE_PATR_NAME = "_patrName";
	private static final String ATTRIBUTE_RELATIVE_BIRTHDAY = "_birthday";
	private static final String ATTRIBUTE_RELATIVE_DEPENDENT = "_dependent";
	private static final String ATTRIBUTE_RELATIVE_EMPLOYEE = "_employee";
	private static final String ATTRIBUTE_RELATIVE_CREDIT = "_credit";
	private static final String ATTRIBUTE_RELATIVE_EMPLOYEE_PLACE = "_employeePlace";

	private static final String ATTRIBUTE_REGISTRATION_TYPE = "registration-type-";
	private static final String ATTRIBUTE_REGION_TYPE = "district-type-";
	private static final String ATTRIBUTE_REGION = "region-";
	private static final String ATTRIBUTE_DISTRICT= "district-";
	private static final String ATTRIBUTE_CITY_TYPE = "city-type-";
	private static final String ATTRIBUTE_CITY = "city-";
	private static final String ATTRIBUTE_LOCALITY_TYPE = "locality-type-";
	private static final String ATTRIBUTE_LOCALITY= "locality-";
	private static final String ATTRIBUTE_STREET_TYPE = "street-type-";
	private static final String ATTRIBUTE_STREET = "street-";
	private static final String ATTRIBUTE_HOUSE = "house-";
	private static final String ATTRIBUTE_BUILDING = "building-";
	private static final String ATTRIBUTE_CONSTRUCTION = "construction-";
	private static final String ATTRIBUTE_FLAT = "flat-";
	private static final String ATTRIBUTE_INDEX = "index-";

	private static final String ATTRIBUTE_REGISTRATION_EXPIRY_DATE = "registration-expiry-date";
	private static final String ATTRIBUTE_RESIDENCE_RIGHT = "residence-right";
	private static final String ATTRIBUTE_RESIDENCE_DURATION = "residence-duration";
	private static final String ATTRIBUTE_FACT_ADDRESS_DURATION = "fact-address-duration";

	private static final String ATTRIBUTE_FAMILY_STATUS = "family-status";
	private static final String ATTRIBUTE_PARTNER_SURNAME = "partnerSurname";
	private static final String ATTRIBUTE_PARTNER_FIRSTNAME= "partnerFirstname";
	private static final String ATTRIBUTE_PARTNER_PATRNAME= "partnerPatr";
	private static final String ATTRIBUTE_PARTNER_BIRTHDAY= "partnerBirthday";
	private static final String ATTRIBUTE_PARTNER_ON_DEPENDENT = "partnerOnDependent";
	private static final String ATTRIBUTE_PARTNER_HAS_LOANS_IN_SBER = "partnerHasLoansInSber";
	private static final String ATTRIBUTE_HAS_PRENUP = "hasPrenup";
	private static final String ATTRIBUTE_REDUSED_DATE= "refusedDate";

	private static final String ATTRIBUTE_REALTY_PREFIX = "realty_";
	private static final String ATTRIBUTE_REALTY_TYPE = "_realtyType";
	private static final String ATTRIBUTE_REALTY_ADDRESS = "_address";
	private static final String ATTRIBUTE_REALTY_YEAR_OF_PURCHASE = "_yearOfPurchase";
	private static final String ATTRIBUTE_REALTY_SQUARE = "_square";
	private static final String ATTRIBUTE_REALTY_SQUARE_UNIT = "_typeOfSquareUnit";
	private static final String ATTRIBUTE_REALTY_APPROX_MARKET_VALUE ="_approxMarketValue";

	private static final String ATTRIBUTE_TRANSPORT_PREFIX = "transport_";
	private static final String ATTRIBUTE_TRANSPORT_TYPE = "_transportType";
	private static final String ATTRIBUTE_TRANSPORT_REGISTRATION_NUMBER = "_registrationNumber";
	private static final String ATTRIBUTE_TRANSPORT_BRAND = "_brand";
	private static final String ATTRIBUTE_TRANSPORT_APPROX_MARKET_VALUE ="_approxMarketValue";
	private static final String ATTRIBUTE_TRANSPORT_AGE = "_ageOfTransport";
	private static final String ATTRIBUTE_TRANSPORT_YEAR_OF_PURCHASE = "_yearOfPurchase";

	private static final Pattern PASSPORT_SERIES_AND_NUMBER_PATTERN = Pattern.compile("(\\d{4})(\\d{6})");
	/**
	 * признак скрытия в блоке "Статусы заявок"
	 */
	private static final String IS_HIDDEN = "hidden";
	private static final String IS_STEP_FILLED = "stepFilled-";

	private static final String COMPLETE_APP_FLAG = "completeAppFlag";

	private static final String ATTRIBUTE_CHANNEL_CB_REG_A_APPROVE = "channelCBRegAApprove";
	private static final String ATTRIBUTE_CHANNEL_PFR_REG_A_APPROVE = "channelPFRRegAApprove";

	public static final String REQUEST_EXTRA_PARAMETERS_XPATH        = "request-parameters/parameter";
	public static final String INITIAL_STATE_EXTRA_PARAMETERS_XPATH  = "initial-state-parameters/parameter";
	public static final String INITIAL2_STATE_EXTRA_PARAMETERS_XPATH = "initial2-state-parameters/parameter";
	public static final String INITIAL3_STATE_EXTRA_PARAMETERS_XPATH = "initial3-state-parameters/parameter";
	public static final String INITIAL4_STATE_EXTRA_PARAMETERS_XPATH = "initial4-state-parameters/parameter";
	public static final String INITIAL5_STATE_EXTRA_PARAMETERS_XPATH = "initial5-state-parameters/parameter";
	public static final String INITIAL6_STATE_EXTRA_PARAMETERS_XPATH = "initial6-state-parameters/parameter";
	public static final String INITIAL7_STATE_EXTRA_PARAMETERS_XPATH = "initial7-state-parameters/parameter";
	public static final String INITIAL8_STATE_EXTRA_PARAMETERS_XPATH = "initial8-state-parameters/parameter";

	private static final String ATTRIBUTE_APPLICANT_TAX_ID = "inn";
	private static final String ATTRIBUTE_CONTRACT_TYPE = "contract-type";
	private static final String ATTRIBUTE_IS_SBER_EMPLOYEE = "is-sber-employee";
	private static final String ATTRIBUTE_COMPANY_FULL_NAME = "company-full-name";
	private static final String ATTRIBUTE_COMPANY_INN = "company-inn";
	private static final String ATTRIBUTE_COMPANY_ACTIVITY_COMMENT = "company-activity-comment";
	private static final String ATTRIBUTE_INCORPORATION_TYPE = "incorporation-type";
	private static final String ATTRIBUTE_COMPANY_ACTIVITY_SCOPE = "company-activity-scope";
	private static final String ATTRIBUTE_NUMBER_OF_EMPLOYEES = "number-of-employees";
	private static final String ATTRIBUTE_DEPARTMENT = "department";
	private static final String ATTRIBUTE_DEPARTMENT_FULL_NAME = "department-full-name";
	private static final String ATTRIBUTE_IS_TB_CHAIRMAN = "is-tb-chairman";

	private static final String ATTRIBUTE_POSITION_TYPE = "position-type";
	private static final String ATTRIBUTE_SENIORITY = "seniority";
	private static final String ATTRIBUTE_POSITION = "position";
	private static final String ATTRIBUTE_WORK_PLACES_COUNT = "work-places-count";
	private static final String ATTRIBUTE_PRIVATE_PRACTICE = "private-practice";
	private static final String ATTRIBUTE_BASIC_INCOME = "basic-income";
	private static final String ATTRIBUTE_MONTHLY_INCOME = "monthly-income";
	private static final String ATTRIBUTE_ADDITIONAL_INCOME = "additional-income";
	private static final String ATTRIBUTE_MONTHLY_EXPENSE = "monthly-expense";
	public static final String ATTRIBUTE_LOAN_ISSUE_METHOD_CODE = "creditMethodObtaining";
	private static final String ATTRIBUTE_APPLICANT_SNILS = "snils";
	private static final String ATTRIBUTE_APPLICANT_BKI_REQUEST_FLAG  = "agreeRequestBKI";
	private static final String ATTRIBUTE_APPLICANT_PFR_GRANT_FLAG = "agreeRequestPFP";
	private static final String ATTRIBUTE_APPLICANT_CREDIT_HISTORY_CODE = "subjectCreditHistoryCode";
	private static final String ATTRIBUTE_APPLICANT_GET_CREDIT_CARD_FLAG = "agreeReceiveCard";
	private static final String ATTRIBUTE_APPLICANT_STOCKHOLDER           = "stockholder";
	private static final String ATTRIBUTE_APPLICANT_SBCOMMON_SHARES_COUNT = "ordinaryStockCount";
	private static final String ATTRIBUTE_APPLICANT_SBPREFERENCE_SHARES_COUNT = "preferredStockCount";
	public static final String ATTRIBUTE_LOAN_ISSUE_RESOURCE = "receiving-resource-id";
	private static final String ATTRIBUTE_LOAN_ISSUE_CUSTOM_RESOURCE = "accountNumber";

	private static final String ATTRIBUTE_APPLICANT_CARD_PREFIX = "cardBlank_";
	private static final String ATTRIBUTE_APPLICANT_DEPOSIT_PREFIX = "accountBlank_";
	private static final String ATTRIBUTE_APPLICANT_PRODUCT_TYPE = "_type";
	private static final String ATTRIBUTE_APPLICANT_PRODUCT_ID = "_id";
	private static final String ATTRIBUTE_APPLICANT_DEPOSIT_NUMBER = "_customNumber";

	private static final String ATTRIBUTE_MOBILE_COUNTRY       = "mobileCountry";
	private static final String ATTRIBUTE_MOBILE_TELECOM       = "mobileTelecom";
	private static final String ATTRIBUTE_MOBILE_NUMBER        = "mobileNumber";

	private static final String ATTRIBUTE_JOBPHONE_COUNTRY     = "jobphoneCountry";
	private static final String ATTRIBUTE_JOBPHONE_TELECOM     = "jobphoneTelecom";
	private static final String ATTRIBUTE_JOBPHONE_NUMBER      = "jobphoneNumber";

	private static final String ATTRIBUTE_RESIDENCE_PHONE_COUNTRY     = "residencePhoneCountry";
	private static final String ATTRIBUTE_RESIDENCE_PHONE_TELECOM     = "residencePhoneTelecom";
	private static final String ATTRIBUTE_RESIDENCE_PHONE_NUMBER      = "residencePhoneNumber";

	private static final String ATTRIBUTE_PHONEBLANK_PREFIX = "phoneBlank_";
	private static final String ATTRIBUTE_PHONEBLANK_TYPE = "_type";
	private static final String ATTRIBUTE_PHONEBLANK_COUNTRY = "_country";
	private static final String ATTRIBUTE_PHONEBLANK_NUMBER = "_number";
	private static final String ATTRIBUTE_PHONEBLANK_TELECOM = "_telecom";

	private static final String ATTRIBUTE_ETSM_CLAIM_ID        = "etsmClaimID";
	private static final String ATTRIBUTE_ETSM_ERROR_MESSAGE   = "etsmErrorMessage";
	private static final String ATTRIBUTE_ETSM_APPROVED_PERIOD = "etsmApprovedPeriod";
	private static final String ATTRIBUTE_ETSM_APPROVED_AMOUNT = "etsmApprovedAmount";
	private static final String ATTRIBUTE_ETSM_APPROVED_INTEREST_RATE = "etsmApprovedInterestRate";
	private static final String ATTRIBUTE_ETSM_PRE_APPROVED_PERIOD = "etsmPreApprovedPeriod";
	private static final String ATTRIBUTE_ETSM_PRE_APPROVED_AMOUNT = "etsmPreApprovedAmount";
	private static final String ATTRIBUTE_ETSM_PRE_APPROVED_INTEREST_RATE = "etsmPreApprovedInterestRate";
	private static final String ATTRIBUTE_REFUSE_REASON        = "refuseReason";

	private static final String ATTRIBUTE_SHORT_LOAN_ID         = "shortLoanId";

	private static final String ATTRIBUTE_PERSON_VERIFY_QUESTION_COUNT = "question_count";
	private static final String ATTRIBUTE_PERSON_VERIFY_QUESTION = "question_";
	private static final String ATTRIBUTE_PERSON_VERIFY_QUESTION_ID = "_id";
	private static final String ATTRIBUTE_PERSON_VERIFY_QUESTION_TEXT = "_text";
	private static final String ATTRIBUTE_PERSON_VERIFY_QUESTION_ANSWER_TYPE = "_answer_type";
	private static final String ATTRIBUTE_PERSON_VERIFY_QUESTION_ANSWER = "_answer";

	protected static final String ATTRIBUTE_JSON_CONDITION      = "jsonCondition";
	protected static final String ATTRIBUTE_JSON_CURR_CONDITION = "jsonCurrCondition";
	protected static final String ATTRIBUTE_END_DATE            = "endDate";
	//Пароль для подтверждения обратного звонка в гостевой сессии
	private static final String ATTRIBUTE_CONFIRM_PASSWORD = "confirm-password";
	//Признак действия клиента - заказ звонка, или подтверждение документа
	private static final String ATTRIBUTE_ACTION_SIGN = "confirm-action-sign";
	//Короткий номер заявки, отправляемый в МБК (БКИ_ХХХХХ)
	private static final String BKI_CLAIM_NUMBER = "bki-claim-number";

	public static final String CONFIRM_GUEST_ACTION = "confirm-document";
	public static final String CONFIRM_PHONE_CALL_GUEST_ACTION = "confirm-phone-call";

	public static final String ATTRIBUTE_RESEIVING_REGION = "receivingRegion";
	public static final String ATTRIBUTE_RESEIVING_OFFICE = "receivingOffice";
	public static final String ATTRIBUTE_RESEIVING_BRANCH = "receivingBranch";
	private static final String ATTRIBUTE_LOGIN_KI = "loginKI";

	private static final String OWNER_LAST_NAME = "ownerLastName";
	private static final String OWNER_FIRST_NAME = "ownerFirstName";
	private static final String OWNER_MIDDLE_NAME = "ownerMiddleName";
	private static final String OWNER_BIRTHDAY = "ownerBirthday";
	private static final String OWNER_ID_CARD_NUMBER = "ownerIdCardNumber";
	private static final String OWNER_ID_CARD_SERIES  = "ownerIdCardSeries";

	private static final String ATTRIBUTE_IN_WAIT_TM = "inWaitTM";
	protected static final State STATE_REFUSED = new State("REFUSED", "Отказан");

	private static final String VISIT_OFFICE_REASON = "visitOfficeReason";

	public static final String OPENED_ACCOUNTS_COUNT = "openedAccountsCount";

	public static final String CONFIRMED_OFFER_ID = "confirmedOfferId";

	private Map<String, Address> addressMap;

	private Integer etsmErrorCode;

	private String tb;
	private String osb;
	private String vsp;

	private String ownerGuestPhone;
	private Long ownerLoginId;
	private Long ownerGuestId;
	private String ownerExternalId;
	private Boolean ownerGuestMbk;
	private String ownerFirstName;
	private String ownerLastName;
	private String ownerMiddleName;
	private Calendar ownerBirthday;
	private String ownerIdCardNumber;
	private String ownerIdCardSeries;
	private PersonDocumentType ownerPersonDocumentType;

	private String ownerTb;
	private String ownerOsb;
	private String ownerVsp;

	private LoginType ownerLoginType;
	private Long createManagerLoginId;
	private Long confirmManagerLoginId;
	//дата начала процесса подтверждения заявки через МБК или заказа обратного звонка
	private Calendar bkiConfirmDate;

	///////////////////////////////////////////////////////////////////////////

	public Class<? extends GateDocument> getType()
	{
		return ETSMLoanClaim.class;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	@Override
	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();

		appendNullSaveString(root, "loanProductName", getProductName());

		if (new State("APPROVED").equals(getState()) || new State("ISSUED").equals(getState()))
		{
			appendNullSaveString(root, "approvedPeriod", getApprovedPeriod().toString());
			appendNullSaveString(root, "approvedAmount", MoneyFunctions.formatAmount(getApprovedAmount(), 2));
			appendNullSaveString(root, "approvedInterestRate", MoneyFunctions.formatAmount(getApprovedInterestRate(), 2));
		}

		appendNullSaveString(root, "refuseReason", getRefuseReason());

		appendNullSaveString(root, OWNER_LAST_NAME, getOwnerLastName());
		appendNullSaveString(root, OWNER_FIRST_NAME, getOwnerFirstName());
		appendNullSaveString(root, OWNER_MIDDLE_NAME, getOwnerMiddleName());
		appendNullSaveDate(root, OWNER_BIRTHDAY, getOwnerBirthday());
		appendNullSaveString(root, OWNER_ID_CARD_NUMBER, getOwnerIdCardNumber());
		appendNullSaveString(root, OWNER_ID_CARD_SERIES, getOwnerIdCardSeries());
		return document;
	}

	private String getCardNumberById(long id)
	{
		try
		{
			CardLink link = externalResourceService.findLinkById(CardLink.class, id);
			if (link == null)
			{
				throw new IllegalArgumentException("Не найдена карта с id=" + id);
			}

			return link.getNumber();
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private String getAccountNumberById(final Long id)
	{
		try
		{
			AccountLink link = externalResourceService.findLinkById(AccountLink.class, id);
			if (link == null)
				throw new IllegalArgumentException("Не найдена счет с id=" + id);

			return link.getNumber();
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private long extractId(String source)
	{
		String idString = source.substring(source.indexOf(":") + 1, source.length());
		return Long.parseLong(idString);
	}

	private ResourceType extractType(String resourceId)
	{
		String typeString = resourceId.substring(0, resourceId.indexOf(":"));
		if (typeString.equals("card"))
			return ResourceType.CARD;
		if (typeString.equals("account"))
			return ResourceType.ACCOUNT;
		return null;
	}

	private Map<String, CardKind> getApplicantCardValues()
	{
		Map<String, CardKind> values = new HashMap<String, CardKind>();

		for (int i = 1; i <= ExtendedLoanClaimConstants.MAX_ADDITIONAL_DEPOSIT_BLOCK_COUNT; i++)
		{
			String fieldPrefix = ATTRIBUTE_APPLICANT_CARD_PREFIX + i;
			String cardType = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_TYPE);

			if (StringHelper.isNotEmpty(cardType))
			{
				String id = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_ID);
				if (StringHelper.isEmpty(id))
				{
					String customNumber = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_APPLICANT_DEPOSIT_NUMBER);
					values.put(customNumber, CardKind.valueOf(cardType));
				}
				else
				{
					values.put( getCardNumberById(extractId(id)), CardKind.valueOf(cardType) );
				}
			}
		}

		return values;
	}

	private Map<String, DepositKind> getApplicantDepositsValues()
	{
		Map<String, DepositKind> values = new HashMap<String, DepositKind>();

		for (int i = 1; i <= ExtendedLoanClaimConstants.MAX_ADDITIONAL_DEPOSIT_BLOCK_COUNT; i++)
		{
			String fieldPrefix = ATTRIBUTE_APPLICANT_DEPOSIT_PREFIX + i;
			String depositType = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_TYPE);

			if (StringHelper.isNotEmpty(depositType))
			{
				String id = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_ID);
				if (StringHelper.isEmpty(id))
				{
					String customNumber = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_APPLICANT_DEPOSIT_NUMBER);
					values.put(customNumber, DepositKind.valueOf(depositType));
				}
				else
				{
					values.put(getAccountNumberById(extractId(id)) , DepositKind.valueOf(depositType));
				}
			}
		}
		return values;
	}

	public boolean isUseLoanOffer()
	{
		return getNullSaveAttributeBooleanValue(ATTRIBUTE_USE_LOAN_OFFER);
	}

	public boolean isUsePercent()
	{
		return getNullSaveAttributeBooleanValue(ATTRIBUTE_USE_PERCENT);
	}

	public String getLoanIssueAccount()
	{
		LoanIssueMethod method = getLoanIssueMethod();
		LoanIssueMethod.LoanProductType productType = method.getProductForLoan();
		if (!(productType == LoanIssueMethod.LoanProductType.CURRENT_ACCOUNT))
			return null;

		String id = getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_ISSUE_RESOURCE);
		if (StringHelper.isNotEmpty(id))
			return getAccountNumberById(extractId(id));
		return null;
	}

	public String getLoanIssueCard()
	{
		LoanIssueMethod method = getLoanIssueMethod();
		if (method.isNewProductForLoan())
			return null;
		LoanIssueMethod.LoanProductType productType = method.getProductForLoan();
		if (productType != LoanIssueMethod.LoanProductType.CARD)
			return null;

		String id = getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_ISSUE_RESOURCE);
		if (StringHelper.isEmpty(id))
			return getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_ISSUE_CUSTOM_RESOURCE);
		else return getCardNumberById(extractId(id));
	}

	public LoanIssueMethod getLoanIssueMethod()
	{
		String loanIssueMethodCode = getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_ISSUE_METHOD_CODE);
		if (loanIssueMethodCode == null)
			return null;
		return loanClaimDictionaryService.getDictionaryElementByCode(loanIssueMethodCode, LoanIssueMethod.class);
	}

	public Collection<Phone> getApplicantPhones()
	{
		Collection<Phone> phones = new HashSet<Phone>(6);

		String country = getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_COUNTRY);
		String telecom = getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_TELECOM);
		String number  = getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_NUMBER);
		phones.add( new Phone(Phone.Type.MOBILE, country, telecom, number) );

		country = getNullSaveAttributeStringValue(ATTRIBUTE_JOBPHONE_COUNTRY);
		telecom = getNullSaveAttributeStringValue(ATTRIBUTE_JOBPHONE_TELECOM);
		number  = getNullSaveAttributeStringValue(ATTRIBUTE_JOBPHONE_NUMBER);
		phones.add( new Phone(Phone.Type.WORK, country, telecom, number) );

		country = getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_PHONE_COUNTRY);
		telecom = getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_PHONE_TELECOM);
		number  = getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_PHONE_NUMBER);
		if (StringHelper.isNotEmpty(country) && StringHelper.isNotEmpty(telecom) && StringHelper.isNotEmpty(number))
			phones.add( new Phone(Phone.Type.RESIDENCE, country, telecom, number) );

		for (int i = 1; i <= ExtendedLoanClaimConstants.MAX_ADDITIONAL_PHONE_BLOCK_COUNT; i++)
		{
			String phoneFieldPrefix = ATTRIBUTE_PHONEBLANK_PREFIX + i;
			Phone phone = getBlankPhone(phoneFieldPrefix);
			if (phone != null)
				phones.add(phone);
		}

		return phones;
	}

	/**
	 * возвращает принадлежность клиента к сбербанку
	 * @return принадлежность клиента к сбербанку
	 */
	public SbrfRelation getSbrfRelation()
	{
		String sbrfRelation = getNullSaveAttributeStringValue(ATTRIBUTE_SBRF_RELATION);
		if (StringHelper.isEmpty(sbrfRelation))
			return null;

		return SbrfRelation.fromValue(sbrfRelation);
	}

	public void setMobileCountry(String mobileCountry)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_COUNTRY, mobileCountry);
	}
	/**
	 * Установить префикс мобильного телефона (до 7 цифр)
	 * @param mobileTelecom префикс телефона
	 */
	public void setMobileTelecom(String mobileTelecom)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_TELECOM, mobileTelecom);
	}

	/**
	 * @return префикс мобильного телефона
	 */
	public String getMobileTelecom()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_TELECOM);
	}

	/**
	 * Установить номер мобильного телефона (до 7 цифр)
	 * @param mobileNumber номер телефона
	 */
	public void setMobileNumber(String mobileNumber)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_NUMBER, mobileNumber);
	}

	/**
	 * @return номер мобильного телефона
	 */
	public String getMobileNumber()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_NUMBER);
	}

	/**
	 * Полный номер телефона с кодом страны и кодом оператора
	 * @return
	 */
	public String getFullMobileNumber()
	{
		String telecom = getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_TELECOM);
		String number = getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_NUMBER);
		if (StringHelper.isEmpty(telecom) || StringHelper.isEmpty(number))
			return null;
		return getNullSaveAttributeStringValue(ATTRIBUTE_MOBILE_COUNTRY) + telecom + number;
	}

	/**
	 * Полный рабочий номер телефона
	 * @return
	 */
	public String getFullJobPhoneNumber()
	{
		String telecom = getNullSaveAttributeStringValue(ATTRIBUTE_JOBPHONE_TELECOM);
		String number = getNullSaveAttributeStringValue(ATTRIBUTE_JOBPHONE_NUMBER);
		if (StringHelper.isEmpty(telecom) || StringHelper.isEmpty(number))
			return null;
		return getNullSaveAttributeStringValue(ATTRIBUTE_JOBPHONE_COUNTRY) + telecom + number;
	}

	/**
	 * Полный домашний номер телефона
	 * @return
	 */
	public String getFullResidencePhoneNumber()
	{
		String telecom = getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_PHONE_TELECOM);
		String number = getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_PHONE_NUMBER);
		if (StringHelper.isEmpty(telecom) || StringHelper.isEmpty(number))
			return null;
		return getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_PHONE_COUNTRY) + telecom + number;
	}

	/**
	 * @return id короткой заявки, которая преобразуется в расширенную
	 */
	public Long getShortLoanId()
	{
		return getNullSaveAttributeLongValue(ATTRIBUTE_SHORT_LOAN_ID);
	}

	private Phone getBlankPhone(String fieldPrefix)
	{
		String phoneType = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_PHONEBLANK_TYPE);

		if (StringHelper.isNotEmpty(phoneType))
		{
			String country = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_PHONEBLANK_COUNTRY);
			String telecom = getNullSaveAttributeStringValue(fieldPrefix +ATTRIBUTE_PHONEBLANK_TELECOM);
			String number  = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_PHONEBLANK_NUMBER);
			return new Phone(Phone.Type.valueOf(phoneType), country, telecom, number );
		}
		return null;
	}

	private Map<String, Address> getAddressMap()
	{
		if (addressMap == null)
			getRegistrationAddress();
		return addressMap;
	}

	private void setAddressMap(Map<String, Address> addressMap)
	{
		this.addressMap = addressMap;
	}

	public Calendar getSigningDate()
	{
		return getDocumentDate();
	}

	public String getReceivingRegion()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_REGION);
	}
	public String getClaimDrawDepartmentETSMCode()
	{
		Code code  = new CodeImpl();
		Map<String, String> fields = code.getFields();
		fields.put("region", getNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_REGION));
		fields.put("office", getNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_OFFICE));
		fields.put("branch", getNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_BRANCH));

		return loanClaimDictionaryService.getDepratmentETSMCode(code);
	}

	public Department getClaimDrawDepartment() throws BusinessException
	{
		Code code  = new CodeImpl();
		Map<String, String> fields = code.getFields();
		fields.put("region", getNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_REGION));
		fields.put("office", getNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_OFFICE));
		fields.put("branch", getNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_BRANCH));
		return departmentService.findByCode(code);
	}

	public void setClaimDrawDepartmentETSMCode(String region, String office, String branch)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_REGION, region);
		setNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_OFFICE, office);
		setNullSaveAttributeStringValue(ATTRIBUTE_RESEIVING_BRANCH, branch);
	}

	public String getLoanProductType()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_TYPE_CODE);
	}

	public String getLoanProductCode()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_CODE);
	}

	public String getLoanSubProductCode()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_SUB_PRODUCT_CODE);
	}

	public void setLoanSubProductCode(String subProductCode)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_SUB_PRODUCT_CODE, subProductCode);
	}

	public Money getLoanAmount()
	{
		String amountAsString = getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_AMOUNT);
		if (StringHelper.isEmpty(amountAsString))
			return null;

		String currencyCode = getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_CURRENCY);
		if (StringHelper.isEmpty(currencyCode))
			return null;

		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			Currency currency = currencyService.findByAlphabeticCode(currencyCode);
			return new Money(NumericUtil.parseBigDecimal(amountAsString), currency);
		}
		catch (ParseException e)
		{
			throw new InternalErrorException("Не удалось распарсить сумму кредита: " + amountAsString, e);
		}
		catch (GateException e)
		{
			throw new InternalErrorException(e);
		}
	}

	public Long getLoanPeriod()
	{
		return getNullSaveAttributeLongValue(ATTRIBUTE_PERIOD);
	}

	public ApplicantType getApplicantType()
	{
		return ApplicantType.MAIN_DEBITOR;
	}

	public PersonName getApplicantName()
	{
		return new PersonName(ownerFirstName, ownerLastName, ownerMiddleName);
	}

	public NameChangeReason getApplicantNameChangeReason()
	{
		if (!getFioChanged())
			return null;

		String reasonType = getNullSaveAttributeStringValue(ATTRIBUTE_FIO_CHANGED_REASON_TYPE);
		return NameChangeReason.valueOf(reasonType);
	}

	public String getApplicantNameChangeDescription()
	{
		if (!getFioChanged())
			return null;
		return getNullSaveAttributeStringValue(ATTRIBUTE_FIO_CHANGED_OTHER_REASON_DESCRIPTION);
	}

	public Calendar getApplicantNameChangeDate()
	{
		if (!getFioChanged())
			return null;
		return getNullSaveAttributeDateTimeValue(ATTRIBUTE_FIO_CHANGED_DATE);
	}

	public PersonName getApplicantPreviousName()
	{
		if (!getFioChanged())
			return null;

		String surname = getNullSaveAttributeStringValue(ATTRIBUTE_PREVIOUS_SURNAME);
		String firstname = getNullSaveAttributeStringValue(ATTRIBUTE_PREVIOUS_FIRSTNAME);
		String patrname = getNullSaveAttributeStringValue(ATTRIBUTE_PREVIOUS_PATRNAME);
		return new PersonName(firstname, surname, patrname);
	}

	public Gender getApplicantGender()
	{
		String gender = getNullSaveAttributeStringValue(ATTRIBUTE_GENDER);
		return Gender.valueOf(gender);
	}

	public Calendar getApplicantBirthDay()
	{
		return ownerBirthday;
	}

	public String getApplicantBirthPlace()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_BIRTH_PLACE);
	}

	public Citizenship getApplicantCitizenship()
	{
		return Citizenship.valueOf(getNullSaveAttributeStringValue(ATTRIBUTE_CITIZENSHIP));
	}

	public boolean getApplicantForeignPassportFlag()
	{
		return (Boolean) getNullSaveAttributeValue(ATTRIBUTE_FOREIGN_PASSPORT);
	}

	public String getApplicantEmail()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_EMAIL);
	}

	public String getApplicantTaxID()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_TAX_ID);
	}

	public Education getApplicantEducation()
	{
		String educationCode = getNullSaveAttributeStringValue(ATTRIBUTE_EDUCATION);
		return loanClaimDictionaryService.getDictionaryElementByCode(educationCode, Education.class);
	}

	public Integer getApplicantUnfinishedYear()
	{
		String course = getNullSaveAttributeStringValue(ATTRIBUTE_EDUCATION_COURSE);
		if(StringHelper.isNotEmpty(course))
			return Integer.valueOf(course);
		return null;
	}

	public Address getApplicantResidenceAddress()
	{
		String factRegistrationType = getNullSaveAttributeStringValue(ATTRIBUTE_REGISTRATION_TYPE + "3");
		return getAddressMap().get(factRegistrationType);
	}

	public Address getApplicantFixedAddress()
	{
		return getAddressMap().get("FIXED");
	}

	public Address getApplicantTemporaryAddress()
	{
		return getAddressMap().get("TEMPORARY");
	}

	public Region getRegion(int index)
	{
		String regionCode = getNullSaveAttributeStringValue(ATTRIBUTE_REGION + index);
		return loanClaimDictionaryService.getDictionaryElementByCode(regionCode, Region.class);
	}

	private TypeOfArea getRegionType(int index)
	{
		String regionTypeCode = getNullSaveAttributeStringValue(ATTRIBUTE_REGION_TYPE + index);
		return StringHelper.isNotEmpty(regionTypeCode) ? loanClaimDictionaryService.getDictionaryElementByCode(regionTypeCode, TypeOfArea.class) : null;
	}

	private TypeOfCity getCityType(int index)
	{
		String cityTypeCode = getNullSaveAttributeStringValue(ATTRIBUTE_CITY_TYPE + index);
		return StringHelper.isNotEmpty(cityTypeCode) ? loanClaimDictionaryService.getDictionaryElementByCode(cityTypeCode, TypeOfCity.class) : null;
	}

	private TypeOfLocality getLocalityType(int index)
	{
		String localityTypeCode = getNullSaveAttributeStringValue(ATTRIBUTE_LOCALITY_TYPE + index);
		return StringHelper.isNotEmpty(localityTypeCode) ? loanClaimDictionaryService.getDictionaryElementByCode(localityTypeCode, TypeOfLocality.class) : null;
	}

	private TypeOfStreet getStreetType(int index)
	{
		String streetTypeCode = getNullSaveAttributeStringValue(ATTRIBUTE_STREET_TYPE + index);
		return  StringHelper.isNotEmpty(streetTypeCode) ? loanClaimDictionaryService.getDictionaryElementByCode(streetTypeCode, TypeOfStreet.class) : null;
	}

	private void getRegistrationAddress()
	{
		Map<String, Address> addresses = new LinkedHashMap<String, Address>(3);

		for (int i = 1; i <= 3; i++)
		{
			String registrationType = getNullSaveAttributeStringValue(ATTRIBUTE_REGISTRATION_TYPE + i);
			if (StringHelper.isNotEmpty(registrationType) && !addresses.containsKey(registrationType))
			{
				String postalCode = getNullSaveAttributeStringValue(ATTRIBUTE_INDEX + i);
				Region region = getRegion(i);
				TypeOfArea areaType = getRegionType(i);
				String areaName = getNullSaveAttributeStringValue(ATTRIBUTE_DISTRICT + i);
				TypeOfCity cityType = getCityType(i);
				String cityName = getNullSaveAttributeStringValue(ATTRIBUTE_CITY + i);
				TypeOfLocality localityType = getLocalityType(i);
				String locality = getNullSaveAttributeStringValue(ATTRIBUTE_LOCALITY + i);
				TypeOfStreet streetType = getStreetType(i);
				String streetName = getNullSaveAttributeStringValue(ATTRIBUTE_STREET + i);
				String houseNumber = getNullSaveAttributeStringValue(ATTRIBUTE_HOUSE + i);
				String buildingNumber = getNullSaveAttributeStringValue(ATTRIBUTE_BUILDING + i);
				String unitNumber = getNullSaveAttributeStringValue(ATTRIBUTE_CONSTRUCTION + i);
				String apartmentNumber = getNullSaveAttributeStringValue(ATTRIBUTE_FLAT + i);

				Address address = new Address(postalCode, region, areaType, areaName, cityType, cityName, localityType, locality, streetType, streetName, houseNumber, buildingNumber, unitNumber, apartmentNumber);
				addresses.put(registrationType, address);
			}
		}
		setAddressMap(addresses);
	}

	/**
	 * Проверяет заполнение полей город, тип города, населенный пункт, тип пункта.
	 * Если что либо одно из этой связки не заполнено, то очищает соответственное значение.
	 * Запрос CHG077728: Очищать поле в кредитной заявке.
	 */
	public void checkCityAndLocality()
	{
		boolean changed = false;
		for (int i = 1; i <= 3; i++)
		{
			TypeOfCity cityType = getCityType(i);
			String cityName = getNullSaveAttributeStringValue(ATTRIBUTE_CITY + i);
			TypeOfLocality localityType = getLocalityType(i);
			String locality = getNullSaveAttributeStringValue(ATTRIBUTE_LOCALITY + i);
			TypeOfArea areaType = getRegionType(i);
			String area = getNullSaveAttributeStringValue(ATTRIBUTE_DISTRICT + i);
			// не заполнен Район/Округ
			if (StringHelper.isEmpty(area) && areaType != null)
			{
				setNullSaveAttributeStringValue(ATTRIBUTE_REGION_TYPE + i, null);
			}
			// Заполнен район, но не заполнен тип района
			if (!StringHelper.isEmpty(area) && areaType == null)
			{
				setNullSaveAttributeStringValue(ATTRIBUTE_DISTRICT + i, null);
			}
			// не заполнен населенный пункт
			if (!StringHelper.isEmpty(cityName) && cityType != null
				&& localityType != null && StringHelper.isEmpty(locality))
			{
				setNullSaveAttributeStringValue(ATTRIBUTE_LOCALITY_TYPE + i, null);
				changed = true;
			}
			// не заполнен тип населенного пункта
			if (!StringHelper.isEmpty(cityName) && cityType != null
				&& localityType == null && !StringHelper.isEmpty(locality))
			{
				setNullSaveAttributeStringValue(ATTRIBUTE_LOCALITY + i, null);
				changed = true;
			}
			// не заполнен город
			if (StringHelper.isEmpty(cityName) && cityType != null
				&& localityType != null && !StringHelper.isEmpty(locality))
			{
				setNullSaveAttributeStringValue(ATTRIBUTE_CITY_TYPE + i, null);
				changed = true;
			}
			// не заполнен тип города
			if (!StringHelper.isEmpty(cityName) && cityType == null
				&& localityType != null && !StringHelper.isEmpty(locality))
			{
				setNullSaveAttributeStringValue(ATTRIBUTE_CITY + i, null);
				changed = true;
			}
		}
		if (changed)
			getRegistrationAddress();
	}

	public Calendar getApplicantResidenceExpiryDate()
	{
		if (getAddressMap().containsKey("TEMPORARY"))
			return getNullSaveAttributeCalendarValue(ATTRIBUTE_REGISTRATION_EXPIRY_DATE);
		return null;
	}

	public int getApplicantCityResidencePeriod()
	{
		String duration = getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_DURATION);
		return Integer.valueOf(duration);
	}

	public int getApplicantActualResidencePeriod()
	{
		String duration = getNullSaveAttributeStringValue(ATTRIBUTE_FACT_ADDRESS_DURATION);
		return Integer.valueOf(duration);
	}

	public ResidenceRight getApplicantResidenceRight()
	{
		String residenceRightCode = getNullSaveAttributeStringValue(ATTRIBUTE_RESIDENCE_RIGHT);
		return loanClaimDictionaryService.getDictionaryElementByCode(residenceRightCode, ResidenceRight.class);
	}

	public Passport getApplicantPassport()
	{
		String series = getOwnerIdCardSeries();
		String number =	getOwnerIdCardNumber();
		String issueBy = getNullSaveAttributeStringValue(ATTRIBUTE_PASSPORT_ISSUE_BY);
		String issueByCode = getNullSaveAttributeStringValue(ATTRIBUTE_PASSPORT_ISSUE_BY_CODE);
		Calendar issueDate = getNullSaveAttributeCalendarValue(ATTRIBUTE_PASSPORT_ISSUE_DATE);
		return new Passport(series, number, issueBy, issueByCode, issueDate);
	}

	public Passport getApplicantPreviousPassport()
	{
		boolean hasOldPassport = Boolean.valueOf(getNullSaveAttributeStringValue(ATTRIBUTE_HAS_OLD_PASSPORT));
		if (!hasOldPassport)
			return null;

		String seriesAndNumber = getNullSaveAttributeStringValue(ATTRIBUTE_OLD_PASSPORT_SERIES_AND_NUMBER);
		seriesAndNumber = seriesAndNumber.replace(" ", "");
		Matcher matcher = PASSPORT_SERIES_AND_NUMBER_PATTERN.matcher(seriesAndNumber);
		if (matcher.matches())
		{
			String series = matcher.group(1);
			String number = matcher.group(2);
			String issueBy = getNullSaveAttributeStringValue(ATTRIBUTE_OLD_PASSPORT_ISSUE_BY);
			Calendar issueDate = getNullSaveAttributeCalendarValue(ATTRIBUTE_OLD_PASSPORT_ISSUE_DATE);
			return new Passport(series, number, issueBy, null, issueDate);
		}
		throw new IllegalArgumentException("Не удалось распарсить серию и номер старого паспорта: " + seriesAndNumber);
	}

	public FamilyStatus getApplicantFamilyStatus()
	{
		String familyStatusCode = getNullSaveAttributeStringValue(ATTRIBUTE_FAMILY_STATUS);
		if (StringHelper.isEmpty(familyStatusCode))
			return null;
		return  loanClaimDictionaryService.getDictionaryElementByCode(familyStatusCode, FamilyStatus.class);
	}

	public Spouse getApplicantSpouse()
	{
		FamilyStatus familyStatus = getApplicantFamilyStatus();

		if (!familyStatus.getSpouseInfoRequired().equals(FamilyStatus.SpouseInfo.NOT))
		{
			String firstname = getNullSaveAttributeStringValue(ATTRIBUTE_PARTNER_FIRSTNAME);
			String surname  = getNullSaveAttributeStringValue(ATTRIBUTE_PARTNER_SURNAME);
			String middlename = getNullSaveAttributeStringValue(ATTRIBUTE_PARTNER_PATRNAME);
			PersonName name = new PersonName(firstname, surname, middlename);

			Calendar birthday = getNullSaveAttributeCalendarValue(ATTRIBUTE_PARTNER_BIRTHDAY);

			String dependentStr = getNullSaveAttributeStringValue(ATTRIBUTE_PARTNER_ON_DEPENDENT);
			boolean dependent = BooleanUtils.toBoolean(dependentStr, "true", "false");

			String hasLoansInSberValue = getNullSaveAttributeStringValue(ATTRIBUTE_PARTNER_HAS_LOANS_IN_SBER);
			Boolean hasLoansInSber = BooleanUtils.toBooleanObject(hasLoansInSberValue, "1", "2", "3");

			String marriageContract = getNullSaveAttributeStringValue(ATTRIBUTE_HAS_PRENUP);
			if (StringHelper.isEmpty(marriageContract))
				marriageContract="false";
			boolean contract = BooleanUtils.toBoolean(marriageContract, "true", "false");

			return new Spouse(name, birthday, dependent, hasLoansInSber, contract);
		}

		return null;
	}

	public Collection<Relative> getApplicantRelatives()
	{
		Set<Relative> relatives = new HashSet<Relative>();

		for (int i = 1; i <= ExtendedLoanClaimConstants.MAX_RELATIVE_BLOCK_COUNT; i++)
		{
			String familyFieldPrefix = ATTRIBUTE_RELATIVE_PREFIX + i;
			Relative relative = getRelative(familyFieldPrefix);
			if (relative != null)
				relatives.add(relative);

			String childFieldPrefix = ATTRIBUTE_CHILD_PREFIX + i;
			Relative child = getRelative(childFieldPrefix);
			if (child != null)
				relatives.add(child);
		}

		return relatives;
	}

	private Relative getRelative(String fieldPrefix)
	{
		String relativeType = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_RELATIVE_TYPE);

		if (StringHelper.isNotEmpty(relativeType))
		{
			FamilyRelation familyRelation = loanClaimDictionaryService.getDictionaryElementByCode(relativeType, FamilyRelation.class);
			String surname = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_RELATIVE_SURNAME);
			String name = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_RELATIVE_NAME);
			String patrName = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_RELATIVE_PATR_NAME);
			PersonName personName = new PersonName(name, surname, patrName);

			Calendar birthDay  = getNullSaveAttributeCalendarValue(fieldPrefix + ATTRIBUTE_RELATIVE_BIRTHDAY);

			String dependentStr = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_RELATIVE_DEPENDENT);
			boolean dependent = BooleanUtils.toBoolean(dependentStr, "1", "2");
			boolean isSBEmployee = BooleanUtils.isTrue((Boolean) getNullSaveAttributeValue(fieldPrefix + ATTRIBUTE_RELATIVE_EMPLOYEE));

			String haveSBCreditStr = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_RELATIVE_CREDIT);
			Boolean haveSBCredit = BooleanUtils.toBooleanObject(haveSBCreditStr, "1", "2", "3");

			String employeePlace = getNullSaveAttributeStringValue(fieldPrefix + ATTRIBUTE_RELATIVE_EMPLOYEE_PLACE);

			return new Relative(familyRelation, personName, birthDay, dependent, haveSBCredit, isSBEmployee, employeePlace);
		}
		return null;
	}

	public WorkOnContract getApplicantEmploymentType()
	{
		String contractType = getNullSaveAttributeStringValue(ATTRIBUTE_CONTRACT_TYPE);
		if (contractType == null)
			return null;
		return loanClaimDictionaryService.getDictionaryElementByCode(contractType, WorkOnContract.class);
	}

	private boolean isUnemployed(WorkOnContract contract)
	{
		return contract.isPrivatePractice() || contract.isPensioner();
	}

	public Organization getApplicantOrganization()
	{
		WorkOnContract contract = getApplicantEmploymentType();

		// Не возвращаем данные об организации для частных практиков и пенсионеров
		if (contract == null || isUnemployed(contract))
			return null;

		// Для остальных собираем поля
		String companyFullName = getNullSaveAttributeStringValue(ATTRIBUTE_COMPANY_FULL_NAME);

		String taxID = getNullSaveAttributeStringValue(ATTRIBUTE_COMPANY_INN);
		String kindOfActivityComment = getNullSaveAttributeStringValue(ATTRIBUTE_COMPANY_ACTIVITY_COMMENT);

		String formOfIncorporationCode = getNullSaveAttributeStringValue(ATTRIBUTE_INCORPORATION_TYPE);
		FormOfIncorporation formOfIncorporation = loanClaimDictionaryService.getDictionaryElementByCode(formOfIncorporationCode, FormOfIncorporation.class);

		String kindOfActivityCode = getNullSaveAttributeStringValue(ATTRIBUTE_COMPANY_ACTIVITY_SCOPE);
		KindOfActivity kindOfActivity = loanClaimDictionaryService.getDictionaryElementByCode(kindOfActivityCode, KindOfActivity.class);

		String numbersOfEmployeesCode = getNullSaveAttributeStringValue(ATTRIBUTE_NUMBER_OF_EMPLOYEES);
		NumberOfEmployees numberOfEmployees = loanClaimDictionaryService.getDictionaryElementByCode(numbersOfEmployeesCode, NumberOfEmployees.class);

		return new Organization(companyFullName, formOfIncorporation, taxID, kindOfActivity, kindOfActivityComment, numberOfEmployees);
	}

	public Employee getApplicantAsEmployee()
	{
		WorkOnContract contract = getApplicantEmploymentType();

		// Не возвращаем данные для частных практиков и пенсионеров
		if (isUnemployed(contract))
			return null;

		String categoryOfPositionCode = getNullSaveAttributeStringValue(ATTRIBUTE_POSITION_TYPE);
		CategoryOfPosition categoryOfPosition = loanClaimDictionaryService.getDictionaryElementByCode(categoryOfPositionCode, CategoryOfPosition.class);

		String jobExperienceCode = getNullSaveAttributeStringValue(ATTRIBUTE_SENIORITY);
		JobExperience jobExperience = loanClaimDictionaryService.getDictionaryElementByCode(jobExperienceCode, JobExperience.class);

		String position = getNullSaveAttributeStringValue(ATTRIBUTE_POSITION);
		String workPlacesCount = getNullSaveAttributeStringValue(ATTRIBUTE_WORK_PLACES_COUNT);

		return new Employee(categoryOfPosition, position, jobExperience, Integer.valueOf(workPlacesCount));
	}

	public SBEmployee getApplicantAsSBEmployee()
	{
		boolean isSberEmployee = BooleanUtils.isTrue((Boolean) getNullSaveAttributeValue(ATTRIBUTE_IS_SBER_EMPLOYEE));

		if (!isSberEmployee)
			return null;

		String tb = getNullSaveAttributeStringValue(ATTRIBUTE_DEPARTMENT);
		String departmentFullName = getNullSaveAttributeStringValue(ATTRIBUTE_DEPARTMENT_FULL_NAME);
		boolean isTBChairman = BooleanUtils.isTrue((Boolean) getNullSaveAttributeValue(ATTRIBUTE_IS_TB_CHAIRMAN));

		return new SBEmployee(tb, departmentFullName, isTBChairman);
	}

	public String getApplicantOwnBusinessDescription()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_PRIVATE_PRACTICE);
	}

	public BigDecimal getApplicantBasicIncome()
	{
		return (BigDecimal) getNullSaveAttributeValue(ATTRIBUTE_BASIC_INCOME);
	}

	public BigDecimal getApplicantAdditionalIncome()
	{
		return (BigDecimal) getNullSaveAttributeValue(ATTRIBUTE_ADDITIONAL_INCOME);
	}

	public BigDecimal getApplicantFamilyIncome()
	{
		return (BigDecimal) getNullSaveAttributeValue(ATTRIBUTE_MONTHLY_INCOME);
	}

	public BigDecimal getApplicantExpenses()
	{
		return (BigDecimal) getNullSaveAttributeValue(ATTRIBUTE_MONTHLY_EXPENSE);
	}

	public Collection<RealEstate> getApplicantRealEstates()
	{
		Set<RealEstate> realEstates = new HashSet<RealEstate>();

		for (int i = 1; i <= ExtendedLoanClaimConstants.MAX_REAL_ESTATE_BLOCK_COUNT; i++)
		{
			String realtyFieldPrefix = ATTRIBUTE_REALTY_PREFIX + i;
			String realtyType = getNullSaveAttributeStringValue(realtyFieldPrefix + ATTRIBUTE_REALTY_TYPE);

			if (StringHelper.isNotEmpty(realtyType))
			{
				TypeOfRealty typeOfRealty = loanClaimDictionaryService.getDictionaryElementByCode(realtyType, TypeOfRealty.class);
				String address = getNullSaveAttributeStringValue(realtyFieldPrefix + ATTRIBUTE_REALTY_ADDRESS);
				String yearOfPurchaseValue = getNullSaveAttributeStringValue(realtyFieldPrefix + ATTRIBUTE_REALTY_YEAR_OF_PURCHASE);
				String squareValue = getNullSaveAttributeStringValue(realtyFieldPrefix + ATTRIBUTE_REALTY_SQUARE);
				String squareUnit = getNullSaveAttributeStringValue(realtyFieldPrefix + ATTRIBUTE_REALTY_SQUARE_UNIT);
				SquareUnits sqUnit = SquareUnits.valueOf(squareUnit);
				String marketValue = getNullSaveAttributeStringValue(realtyFieldPrefix + ATTRIBUTE_REALTY_APPROX_MARKET_VALUE);

				BigDecimal square;
				try
				{
					square = NumericUtil.parseBigDecimal(squareValue);
				}
				catch (ParseException e)
				{
					throw new InternalErrorException("Не удалось распарсить площадь недвижимости " + i, e);
				}

				BigDecimal approxMarketValue;
				try
				{
					approxMarketValue = NumericUtil.parseBigDecimal(marketValue);
				}
				catch (ParseException e)
				{
					throw new InternalErrorException("Не удалось распарсить примерную рыночную стоимость недвижимости " + i, e);
				}

				RealEstate realEstate = new RealEstate(typeOfRealty, address, Integer.valueOf(yearOfPurchaseValue), square, sqUnit, approxMarketValue);
				realEstates.add(realEstate);
			}
		}
		return realEstates;
	}

	public Collection<Vehicle> getApplicantVehicles()
	{
		Set<Vehicle> vehicles = new HashSet<Vehicle>();

		for (int i = 1; i <= ExtendedLoanClaimConstants.MAX_VEHICLE_BLOCK_COUNT; i++)
		{
			String vehicleFieldPrefix = ATTRIBUTE_TRANSPORT_PREFIX + i;
			String vehicleType = getNullSaveAttributeStringValue(vehicleFieldPrefix + ATTRIBUTE_TRANSPORT_TYPE);

			if (StringHelper.isNotEmpty(vehicleType))
			{
				TypeOfVehicle typeOfVehicle = loanClaimDictionaryService.getDictionaryElementByCode(vehicleType, TypeOfVehicle.class);
				String registrationNumber = getNullSaveAttributeStringValue(vehicleFieldPrefix + ATTRIBUTE_TRANSPORT_REGISTRATION_NUMBER);
				String brand = getNullSaveAttributeStringValue(vehicleFieldPrefix + ATTRIBUTE_TRANSPORT_BRAND);
				String year = getNullSaveAttributeStringValue(vehicleFieldPrefix + ATTRIBUTE_TRANSPORT_YEAR_OF_PURCHASE);
				String age = getNullSaveAttributeStringValue(vehicleFieldPrefix + ATTRIBUTE_TRANSPORT_AGE);
				String marketValue = getNullSaveAttributeStringValue(vehicleFieldPrefix + ATTRIBUTE_TRANSPORT_APPROX_MARKET_VALUE);

				try
				{
					BigDecimal approxMarketValue = NumericUtil.parseBigDecimal(marketValue);
					Vehicle vehicle = new Vehicle(typeOfVehicle, registrationNumber, Integer.valueOf(year), brand, Integer.valueOf(age), approxMarketValue);
					vehicles.add(vehicle);
				}
				catch (ParseException e)
				{
					throw new InternalErrorException("Не удалось распарсить примерную рыночную стоимость транспортного средства " + i, e);
				}
			}
		}
		return vehicles;
	}

	public boolean getApplicantInsuranceFlag()
	{
		return Boolean.FALSE;
	}

	public String getApplicantSNILS()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_SNILS);
	}

	public boolean getApplicantBKIRequestFlag()
	{
		return Boolean.valueOf(getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_BKI_REQUEST_FLAG));
	}

	public boolean getApplicantBKIGrantFlag()
	{
		return true;
	}

	public boolean getApplicantPFRGrantFlag()
	{
		return Boolean.valueOf(getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_PFR_GRANT_FLAG));
	}

	public String getApplicantCreditHistoryCode()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_CREDIT_HISTORY_CODE);
	}

	public boolean getApplicantGetCreditCardFlag()
	{
		return Boolean.valueOf(getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_GET_CREDIT_CARD_FLAG));
	}

	public boolean getApplicantSpecialAttentionFlag()
	{
		// Клиентов, требующих внимания, нет.
		return false;
	}

	public int getApplicantSBCommonSharesCount()
	{
		Boolean isStockHolder = Boolean.parseBoolean(getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_STOCKHOLDER));
		return isStockHolder ? getNullSaveAttributeLongValue(ATTRIBUTE_APPLICANT_SBCOMMON_SHARES_COUNT).intValue() : 0;
	}

	public int getApplicantSBPreferenceSharesCount()
	{
		Boolean isStockHolder = Boolean.parseBoolean(getNullSaveAttributeStringValue(ATTRIBUTE_APPLICANT_STOCKHOLDER));
		return isStockHolder ? getNullSaveAttributeLongValue(ATTRIBUTE_APPLICANT_SBPREFERENCE_SHARES_COUNT).intValue() : 0;
	}

	public Collection<String> getApplicantSalaryCards()
	{
		return getApplicantCards(CardKind.SALARY);
	}

	public Collection<String> getApplicantPensionCards()
	{
		return getApplicantCards(CardKind.PENSION);
	}

	private Collection<String> getApplicantCards(CardKind kind)
	{
		Collection<String> applicantCards = new HashSet<String>();

		Map<String, CardKind> cards = getApplicantCardValues();
		for (String number : cards.keySet())
		{
			if (cards.get(number) == kind)
				applicantCards.add(number);
		}

		return applicantCards;
	}


	public Collection<String> getApplicantSalaryDeposits()
	{
		return getApplicantDeposits(DepositKind.SALARY);
	}

	public Collection<String> getApplicantPensionDeposits()
	{
		return getApplicantDeposits(DepositKind.PENSION);
	}

	private Collection<String> getApplicantDeposits(DepositKind kind)
	{
		Collection<String> applicantDeposits = new HashSet<String>();

		Map<String, DepositKind> deposits = getApplicantDepositsValues();

		for (String number : deposits.keySet())
		{
			if (deposits.get(number) == kind)
			{
				applicantDeposits.add(number);
			}
		}

		return applicantDeposits;
	}

	/**
	  * Идентификатор предодобренного предложения
	  *
	  * @return Long
	  */
	 public String getLoanOfferId()
	 {
		 return getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_OFFER_ID);
	 }

	public void setLoanOfferId(String loanOfferId)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_LOAN_OFFER_ID, loanOfferId);
	}

	/**
	  * Идентификатор участника компании
	  *
	  * @return Long
	  */
	 public String getCampaingMemberId()
	 {
		 return getNullSaveAttributeStringValue(ATTRIBUTE_CAMPAING_MEMBER_ID);
	 }

	public void setCampaingMemberId(String campaingMemberId)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CAMPAING_MEMBER_ID, campaingMemberId);
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		createClaimNumber();
		regionInitialize();
	}

	private void regionInitialize()
	{
		try
		{
			if (getOwner().isGuest())
			{
				String regionId = getPersonTB();
				if (regionId == null)
				{
					List<com.rssl.phizic.business.dictionaries.regions.Region> regions = regionService.getAllRegions(null);
					if (!regions.isEmpty())
					{
						com.rssl.phizic.business.dictionaries.regions.Region region = regions.get(0);
						setRegionId(region.getId());
						setRegionName(region.getName());
						setPersonTB(region.getCodeTB()==null ? null : region.getCodeTB());
					}

				}
				else
				{
					setRegionId(Long.valueOf(regionId));
					setRegionName(regionService.findByIdFromCache(Long.valueOf(regionId)).getName());
					setPersonTB(getOwnerTb());
				}
			}
		}
		catch (BusinessException e)
		{
			log.error(e);
		}
	}

	private void createClaimNumber() throws DocumentException
	{
		ClaimNumberGenerator generator = new ClaimNumberGenerator();
		try
		{
			setOperationUID(generator.getInverseClaimNumber());
			//установка короткого номера заявки
			LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
			setNullSaveAttributeStringValue(BKI_CLAIM_NUMBER, StringUtils.left(getOperationUID(), config.getLengthShortNumberClaim()));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public BusinessDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		ExtendedLoanClaim copy = (ExtendedLoanClaim) super.createCopy(instanceClass);
		copy.createClaimNumber();
		return copy;
	}

	/**
	 * Сохранить в документ доп. поля, связанные с предодобренным предложением
	 * @param loanOffer - предодобренный кредит
	 * @param productTypeCode - код типа продукта
	 */
	public void storeLoanOfferData(final LoanOffer loanOffer, final String productTypeCode)
    {
		setNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_NAME, loanOffer.getProductName());
		setNullSaveAttributeLongValue(ATTRIBUTE_PERIOD, getNullSaveAttributeLongValue(ATTRIBUTE_PERIOD));
		setNullSaveAttributeStringValue (ATTRIBUTE_RATE,            Double.toString(loanOffer.getPercentRate()));

		Money maxLimit = loanOffer.getMaxLimit();
	    setNullSaveAttributeStringValue(ATTRIBUTE_LOAN_AMOUNT,      getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_OFFER_AMOUNT));
		setNullSaveAttributeStringValue(ATTRIBUTE_LOAN_CURRENCY, maxLimit.getCurrency().getCode());
	    setNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_CODE,     loanOffer.getProductCode());
	    setNullSaveAttributeStringValue(ATTRIBUTE_SUB_PRODUCT_CODE, loanOffer.getSubProductCode());
	    setNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_TYPE_CODE,productTypeCode);
	    setNullSaveAttributeCalendarValue(ATTRIBUTE_END_DATE, loanOffer.getEndDate());
	    setCampaingMemberId(loanOffer.getCampaignMemberId());
    }

	/**
	 * сохранить в документ доп. поля при создании заявки на общих условиях
	 * @param condition - условие по кредитному продукту
	 * @param currCondition - Повалютное условие по кредитному продукту.
	 * @throws DocumentException
	 */
	public void storeLoanOfferData(CreditProductCondition condition, CurrencyCreditProductCondition currCondition) throws DocumentException
	{
	    setNullSaveAttributeDecimalValue(ATTRIBUTE_COND_MIN_PERCENT_RATE, currCondition.getMinPercentRate());
	    setNullSaveAttributeDecimalValue(ATTRIBUTE_COND_MAX_PERCENT_RATE, currCondition.getMaxPercentRate());
	    setNullSaveAttributeStringValue (ATTRIBUTE_PRODUCT_NAME, condition.getCreditProduct().getName());
	    setNullSaveAttributeStringValue (ATTRIBUTE_LOAN_CURRENCY, currCondition.getCurrency().getCode());

	    setNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_CODE, condition.getCreditProduct().getCode());
		storeSubProductCodeData(condition, currCondition, tb);
		setNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_TYPE_CODE, condition.getCreditProductType().getCode());
		setNullSaveAttributeStringValue(ATTRIBUTE_JSON_CONDITION, getJson(condition));
		setNullSaveAttributeStringValue(ATTRIBUTE_JSON_CURR_CONDITION, getJson(currCondition));
    }

	private String getJson(Object object)
	{
		Gson gson = NoPrettyPrintingGsonSingleton.getGson();
		return gson.toJson(object);
	}

	public void storeSubProductCodeData(CreditProductCondition condition, CurrencyCreditProductCondition currCondition, String tb) throws DocumentException
	{
		Set<CreditSubProductType> creditSubProductTypes = condition.getCreditProduct().getCreditSubProductTypes();
		if (StringHelper.isNotEmpty(tb))
		{
			for (CreditSubProductType creditSubProductType : creditSubProductTypes)
			{
				if (tb.equals(creditSubProductType.getTerbank()))
				{
					if (currCondition.getCurrency().compare(creditSubProductType.getCurrency()))
							setNullSaveAttributeStringValue(ATTRIBUTE_SUB_PRODUCT_CODE, creditSubProductType.getCode());
				}
			}
		}
	}

	public LoanRate getLoanRate()
	{
		try
		{
			if (isUseLoanOffer())
			{
				BigDecimal loanRate = NumericUtil.parseBigDecimal(getNullSaveAttributeStringValue(ATTRIBUTE_RATE));
				return new LoanRate(loanRate);
			}

			BigDecimal minLoanRate = NumericUtil.parseBigDecimal(getNullSaveAttributeStringValue(ATTRIBUTE_COND_MIN_PERCENT_RATE));
			BigDecimal maxLoanRate = NumericUtil.parseBigDecimal(getNullSaveAttributeStringValue(ATTRIBUTE_COND_MAX_PERCENT_RATE));

			return new LoanRate(minLoanRate, maxLoanRate);
		}
		catch (ParseException e)
		{
			throw new InternalErrorException("Не удалось распарсить процентную ставку по кредиту");
		}
	}

	public String getProductName()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_NAME);
	}

	private boolean getFioChanged()
	{
		return getNullSaveAttributeBooleanValue(ATTRIBUTE_FIO_CHANGED);
	}

	@Override
	protected void readExtendedFields(Element root) throws DocumentException
	{
		try
		{
			NodeList parameterList;
			State state =  this.getState();
			if (state == null)
			{
				parameterList = XmlHelper.selectNodeList(root, REQUEST_EXTRA_PARAMETERS_XPATH);
			}
			else
			{
				String stateCode = state.getCode();

				if (stateCode.equals("INITIAL"))
					parameterList = XmlHelper.selectNodeList(root, INITIAL_STATE_EXTRA_PARAMETERS_XPATH);

				else if (stateCode.equals("INITIAL2"))
					parameterList = XmlHelper.selectNodeList(root, INITIAL2_STATE_EXTRA_PARAMETERS_XPATH);

				else if (stateCode.equals("INITIAL3"))
					parameterList = XmlHelper.selectNodeList(root, INITIAL3_STATE_EXTRA_PARAMETERS_XPATH);

				else if (stateCode.equals("INITIAL4"))
					parameterList =  XmlHelper.selectNodeList(root, INITIAL4_STATE_EXTRA_PARAMETERS_XPATH);

				else if (stateCode.equals("INITIAL5"))
					parameterList = XmlHelper.selectNodeList(root, INITIAL5_STATE_EXTRA_PARAMETERS_XPATH);

				else if (stateCode.equals("INITIAL6"))
					parameterList = XmlHelper.selectNodeList(root, INITIAL6_STATE_EXTRA_PARAMETERS_XPATH);

				else if (stateCode.equals("INITIAL7"))
					parameterList = XmlHelper.selectNodeList(root, INITIAL7_STATE_EXTRA_PARAMETERS_XPATH);

				else if (stateCode.equals("INITIAL8"))
					parameterList = XmlHelper.selectNodeList(root, INITIAL8_STATE_EXTRA_PARAMETERS_XPATH);

				else
					parameterList = XmlHelper.selectNodeList(root, EXTRA_PARAMETERS_XPATH);
			}

			readExtendedFields(parameterList);
		}
		catch (TransformerException e)
		{
			throw new DocumentException(e);
		}
	}

	@Override
	public void readFromDom(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, messageCollector);

		State state =  this.getState();
		if (state == null)
			return;

		String stateCode = state.getCode();
		try
		{
			if (stateCode.equals("INITIAL2"))
			{
				Element root = document.getDocumentElement();
				Element parameterList = XmlHelper.selectSingleNode(root, "initial-2-fields");
				setOwnerLastName(XmlHelper.getSimpleElementValue(parameterList, OWNER_LAST_NAME));
				setOwnerFirstName(XmlHelper.getSimpleElementValue(parameterList, OWNER_FIRST_NAME));
				setOwnerMiddleName(XmlHelper.getSimpleElementValue(parameterList, OWNER_MIDDLE_NAME));
				setOwnerBirthday(getDateFromDom(parameterList, OWNER_BIRTHDAY));
				setOwnerIdCardNumber(XmlHelper.getSimpleElementValue(parameterList, OWNER_ID_CARD_NUMBER));
				setOwnerIdCardSeries(XmlHelper.getSimpleElementValue(parameterList, OWNER_ID_CARD_SERIES));
			}
		}
		catch (TransformerException e)
		{
			throw new RuntimeException(e);
		}

	}

	public Long getConditionId()
	{
		return getNullSaveAttributeLongValue(ATTRIBUTE_CONDITION_ID);
	}

	public Long getConditionCurrencyId()
    {
        return getNullSaveAttributeLongValue(ATTRIBUTE_CONDITION_CURR_ID);
    }

	public boolean isHidden()
	{
		return BooleanUtils.isTrue((Boolean) getNullSaveAttributeValue(IS_HIDDEN));
	}

	public void setHidden(boolean hidden)
	{
		setNullSaveAttributeBooleanValue(IS_HIDDEN, hidden);
	}

	public boolean isStepFilled(String step)
	{
		return BooleanUtils.isTrue((Boolean) getNullSaveAttributeValue(IS_STEP_FILLED + step));
	}

	public void setStepFilled(boolean filled, String step)
	{
		setNullSaveAttributeBooleanValue(IS_STEP_FILLED + step, filled);
	}

	public boolean getCompleteAppFlag()
	{
		return getNullSaveAttributeBooleanValue(COMPLETE_APP_FLAG);
	}

	public void setCompleteAppFlag(boolean complete)
	{
		setNullSaveAttributeBooleanValue(COMPLETE_APP_FLAG, complete);
	}

	public ChannelType getChannel()
	{
		try {
			switch (getClientCreationChannel())
			{
				case internet:
					if (getOwner().isGuest())
						return ChannelType.GUEST;
					else
						return ChannelType.SBOL;
				case sms:
					return ChannelType.MBK;
				case atm:
					return ChannelType.ATM;
				case mobile:
					return ChannelType.MP;
				default:
					return ChannelType.SBOL;
			}
		} catch (BusinessException e)
		{
			return ChannelType.SBOL;
		}
	}

	/**
	 * @return Канал отклика
	 * Одно из: ЕРИБ-СБОЛ, ЕРИБ-МП, ЕРИБ-УС, ЕРИБ-МБ, ЕРИБ-ГОСТЕВОЙ
	 */
	public String getCRMSourceChannel() throws BusinessException
	{
		switch (getClientCreationChannel())
		{
			case internet:
				if (getOwner().isGuest())
					return OfferResponseChannel.SBOL_GUEST.getCrmCode();
				else
					return OfferResponseChannel.SBOL.getCrmCode();
			case sms:
				return OfferResponseChannel.MP.getCrmCode();
			case atm:
				return OfferResponseChannel.US.getCrmCode();
			case mobile:
				return OfferResponseChannel.MB.getCrmCode();
			default:
				return OfferResponseChannel.SBOL.getCrmCode();
		}
	}

	public ChannelCBRegAApproveType getChannelCBRegAApprove()
	{
		String channelTypeCode = getNullSaveAttributeStringValue(ATTRIBUTE_CHANNEL_CB_REG_A_APPROVE);
        if (StringHelper.isEmpty(channelTypeCode))
            return null;
        return ChannelCBRegAApproveType.fromCode(channelTypeCode);
	}

	public void setChannelCBRegAApprove(ChannelCBRegAApproveType channel)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CHANNEL_CB_REG_A_APPROVE, channel.getCode());
	}

	public ChannelPFRRegAApproveType getChannelPFRRegAApprove()
	{
		String channelTypeCode = getNullSaveAttributeStringValue(ATTRIBUTE_CHANNEL_PFR_REG_A_APPROVE);
        if (StringHelper.isEmpty(channelTypeCode))
            return null;
        return ChannelPFRRegAApproveType.fromCode(channelTypeCode);
	}

	public void setChannelPFRRegAApprove(ChannelPFRRegAApproveType channel)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CHANNEL_PFR_REG_A_APPROVE, channel.getCode());
	}

	public Collection<Question> getQuestions()
	{
		int questionCount = getQuestionCount();

		if (questionCount == 0)
			return Collections.emptyList();

		List<Question> questions = new ArrayList<Question>(questionCount);

		for (int i=1; i <= questionCount; i++)
		{
			long questionId = getNullSaveAttributeLongValue(ATTRIBUTE_PERSON_VERIFY_QUESTION + i + ATTRIBUTE_PERSON_VERIFY_QUESTION_ID);
			String questionText = getNullSaveAttributeStringValue(ATTRIBUTE_PERSON_VERIFY_QUESTION + i + ATTRIBUTE_PERSON_VERIFY_QUESTION_TEXT);
			String answerType = getNullSaveAttributeStringValue(ATTRIBUTE_PERSON_VERIFY_QUESTION + i + ATTRIBUTE_PERSON_VERIFY_QUESTION_ANSWER_TYPE);
			String answer = getNullSaveAttributeStringValue(ATTRIBUTE_PERSON_VERIFY_QUESTION + i + ATTRIBUTE_PERSON_VERIFY_QUESTION_ANSWER);
			questions.add(new Question(questionId, questionText, answerType, answer));
		}

		return questions;
	}

	public void resetClaimStatus()
	{
		removeAttribute(ATTRIBUTE_ETSM_CLAIM_ID);
		this.etsmErrorCode = null;
		removeAttribute(ATTRIBUTE_ETSM_ERROR_MESSAGE);
		removeAttribute(ATTRIBUTE_ETSM_APPROVED_PERIOD);
		removeAttribute(ATTRIBUTE_ETSM_APPROVED_AMOUNT);
		removeAttribute(ATTRIBUTE_ETSM_APPROVED_INTEREST_RATE);
		removeAttribute(ATTRIBUTE_REFUSE_REASON);
	}

	/**
	 * @param claimID - идентификатор заявки в ETSM
	 */
	public void setETSMClaimID(String claimID)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_ETSM_CLAIM_ID, claimID);
	}

	/**
	 * @param etsmErrorCode - код ошибки/отказа обработки заявки в ETSM, см. ETSMLoanClaimStatus
	 */
	public void setEtsmErrorCode(Integer etsmErrorCode)
	{
		this.etsmErrorCode = etsmErrorCode;
	}

	public Integer getEtsmErrorCode()
	{
		return etsmErrorCode;
	}

	/**
	 * @param errorMessage - текст ошибки/отказа обработки заявки в ETSM
	 */
	public void setErrorMessage(String errorMessage)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_ETSM_ERROR_MESSAGE, errorMessage);
	}

	private Long getApprovedPeriod()
	{
		return getNullSaveAttributeLongValue(ATTRIBUTE_ETSM_APPROVED_PERIOD);
	}

	/**
	 * @param periodMonths - срок одобренного кредита в месяцах
	 */
	public void setApprovedPeriod(long periodMonths)
	{
		setNullSaveAttributeLongValue(ATTRIBUTE_ETSM_APPROVED_PERIOD, periodMonths);
	}

	private BigDecimal getApprovedAmount()
	{
		return (BigDecimal)getNullSaveAttributeValue(ATTRIBUTE_ETSM_APPROVED_AMOUNT);
	}

	/**
	 * @param amount - сумма одобренного кредита
	 */
	public void setApprovedAmount(BigDecimal amount)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_ETSM_APPROVED_AMOUNT, amount);
	}

	public BigDecimal getApprovedInterestRate()
	{
		return (BigDecimal)getNullSaveAttributeValue(ATTRIBUTE_ETSM_APPROVED_INTEREST_RATE);
	}

	/**
	 * @param interestRate - ставка одобренного кредита
	 */
	public void setApprovedInterestRate(BigDecimal interestRate)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_ETSM_APPROVED_INTEREST_RATE, interestRate);
	}

	public Long getPreApprovedPeriod()
	{
		return getNullSaveAttributeLongValue(ATTRIBUTE_ETSM_PRE_APPROVED_PERIOD);
	}

	/**
	 * @param periodMonths - срок предварительно одобренного кредита в месяцах
	 */
	public void setPreApprovedPeriod(long periodMonths)
	{
		setNullSaveAttributeLongValue(ATTRIBUTE_ETSM_PRE_APPROVED_PERIOD, periodMonths);
	}

	public BigDecimal getPreApprovedAmount()
	{
		return (BigDecimal)getNullSaveAttributeValue(ATTRIBUTE_ETSM_PRE_APPROVED_AMOUNT);
	}

	/**
	 * @param amount - сумма предварительно одобренного кредита
	 */
	public void setPreApprovedAmount(BigDecimal amount)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_ETSM_PRE_APPROVED_AMOUNT, amount);
	}

	public BigDecimal getPreApprovedInterestRate()
	{
		return (BigDecimal)getNullSaveAttributeValue(ATTRIBUTE_ETSM_PRE_APPROVED_INTEREST_RATE);
	}

	/**
	 * @param interestRate - ставка предварительно одобренного кредита
	 */
	public void setPreApprovedInterestRate(BigDecimal interestRate)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_ETSM_PRE_APPROVED_INTEREST_RATE, interestRate);
	}

	/**
	 * @param refusedDate Дата отказа.
	 */
	public void setRefusedDate(Calendar refusedDate)
	{
		setNullSaveAttributeCalendarValue(ATTRIBUTE_REDUSED_DATE, refusedDate);
	}


	private String getRefuseReason()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_REFUSE_REASON);
	}

	/**
	 * @param refuseReason - причина отказа
	 */
	public void setRefuseReason(String refuseReason)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_REFUSE_REASON, refuseReason);
	}

	/**
	 * @return Кредитное условие в формате json
	 */
	public String getJsonCondition()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_JSON_CONDITION);
	}

	/**
	 * @return По валютное кредитное условие в формате json
	 */
	public String getJsonConditionCurrency()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_JSON_CURR_CONDITION);
	}

	/**
	 * @return Дата окончания действия предложения
	 */
	public Calendar getEndDate()
	{
		return getNullSaveAttributeCalendarValue(ATTRIBUTE_END_DATE);
	}

	public void setEndDate(Calendar endDate)
	{
		setNullSaveAttributeCalendarValue(ATTRIBUTE_END_DATE, endDate);
	}

	/**
	 * @return Число записей в анкете (если не 0, значит анкета в завке есть)
	 */
	public int getQuestionCount()
	{
		String questionCountStr = getNullSaveAttributeStringValue(ATTRIBUTE_PERSON_VERIFY_QUESTION_COUNT);
		if (StringHelper.isEmpty(questionCountStr))
			return 0;
		return Integer.valueOf(questionCountStr);
	}

	/**
	 * Копирует данные зарплатной/пенсионной карты/вклада с первого шага в поля шага доп. информация
	 */
	public void storeAdditionalResourceData()
	{
		String accountFieldPrefix = ATTRIBUTE_APPLICANT_DEPOSIT_PREFIX + 1;
		String accountTypeFieldName = accountFieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_TYPE;
		String accountIdFieldName = accountFieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_ID;
		String customAccountNumberFieldName = accountFieldPrefix + ATTRIBUTE_APPLICANT_DEPOSIT_NUMBER;

		String cardFieldPrefix = ATTRIBUTE_APPLICANT_CARD_PREFIX + 1;
		String cardTypeFieldName = cardFieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_TYPE;
		String cardIdFieldName = cardFieldPrefix + ATTRIBUTE_APPLICANT_PRODUCT_ID;
		String customCardNumberFieldName = cardFieldPrefix + ATTRIBUTE_APPLICANT_DEPOSIT_NUMBER;

		/**
		 * Вытаскиваем продукт, который клиент указал на первом шаге:
		 * - в качестве зарплатной/пенсионной карты или вклада (в т.ч. для включённой галки "сотрудник СБ")
		 */
		String sbrfResourceId = getNullSaveAttributeStringValue(ATTRIBUTE_SBRF_RESOURCE_ID);
		if (StringHelper.isNotEmpty(sbrfResourceId))
		{
		   ResourceType resourceType = extractType(sbrfResourceId);
			if (resourceType == ResourceType.CARD)
			{
				String cardTypeFieldValue = getNullSaveAttributeStringValue(cardTypeFieldName);
				String cardIdFieldValue = getNullSaveAttributeStringValue(cardIdFieldName);
				if (StringHelper.isEmpty(cardTypeFieldValue)|| !StringUtils.equals(cardIdFieldValue, sbrfResourceId))
					setNullSaveAttributeStringValue(cardTypeFieldName, CardKind.EMPTY.name());
				setNullSaveAttributeStringValue(cardIdFieldName, sbrfResourceId);
			}
			else if (resourceType == ResourceType.ACCOUNT)
			{
				String accountTypeFieldValue = getNullSaveAttributeStringValue(accountTypeFieldName);
				String accountIdFieldValue = getNullSaveAttributeStringValue(accountIdFieldName);
				if (StringHelper.isEmpty(accountTypeFieldValue) || !StringUtils.equals(accountIdFieldValue, sbrfResourceId))
					setNullSaveAttributeStringValue(accountTypeFieldName, DepositKind.EMPTY.name());
				setNullSaveAttributeStringValue(accountIdFieldName, sbrfResourceId);
			}
		}
		else
		{
			String sbrfAccount = getNullSaveAttributeStringValue(ATTRIBUTE_SBRF_ACCOUNT);
			if (StringHelper.isNotEmpty(sbrfAccount))
			{
				//если для гостевой заявки введено меньше 20 символов, то считаем, что это номер карты
				if (isGuest() && sbrfAccount.length() < 20)
				{
					String cardTypeFieldValue = getNullSaveAttributeStringValue(cardTypeFieldName);
					String cardIdFieldValue = getNullSaveAttributeStringValue(cardIdFieldName);
					if (StringHelper.isEmpty(cardTypeFieldValue)|| !StringUtils.equals(cardIdFieldValue, sbrfResourceId))
						setNullSaveAttributeStringValue(cardTypeFieldName, CardKind.EMPTY.name());
					setNullSaveAttributeStringValue(customCardNumberFieldName, sbrfAccount);
				}
				else
				{
					String accountTypeFieldValue = getNullSaveAttributeStringValue(accountTypeFieldName);
					String accountIdFieldValue = getNullSaveAttributeStringValue(accountIdFieldName);
					if (StringHelper.isEmpty(accountTypeFieldValue) || !StringUtils.equals(accountIdFieldValue, sbrfResourceId))
						setNullSaveAttributeStringValue(accountTypeFieldName, DepositKind.EMPTY.name());
					setNullSaveAttributeStringValue(customAccountNumberFieldName, sbrfAccount);
				}
			}
		}
	}

	public boolean isUpgradable()
	{
		return true;
	}

	public boolean isAlwaysIMSICheck()
	{
		return true;
	}

	public String getActionSign()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_ACTION_SIGN);
	}

	public void setActionSign(String actionSign)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_ACTION_SIGN, actionSign);
	}

    public String getConfirmPassword()
    {
        return getNullSaveAttributeStringValue(ATTRIBUTE_CONFIRM_PASSWORD);
    }

    public void setConfirmPassword(String password)
    {
        setNullSaveAttributeStringValue(ATTRIBUTE_CONFIRM_PASSWORD, password);
    }
	
	public Long getOwnerLoginId()
	{
		return ownerLoginId;
	}

	public void setOwnerLoginId(Long ownerLoginId)
	{
		this.ownerLoginId = ownerLoginId;
	}

	public String getExternalOwnerId()
	{
		return ownerExternalId;
	}

	public void setExternalOwnerId(String ownerExternalId)
	{
		this.ownerExternalId = ownerExternalId;
	}

	public void setOwnerFirstName(String ownerFirstName)
	{
		this.ownerFirstName = ownerFirstName;
	}

	public String getOwnerFirstName()
	{
		return ownerFirstName;
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

	public Boolean getOwnerGuestMbk()
	{
		return ownerGuestMbk;
	}

	public void setOwnerGuestMbk(Boolean ownerGuestMbk)
	{
		this.ownerGuestMbk = ownerGuestMbk;
	}

	public String getOwnerLastName()
	{
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName)
	{
		this.ownerLastName = ownerLastName;
	}

	public String getOwnerMiddleName()
	{
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(String ownerMiddleName)
	{
		this.ownerMiddleName = ownerMiddleName;
	}

	public Calendar getOwnerBirthday()
	{
		return ownerBirthday;
	}

	public void setOwnerBirthday(Calendar ownerBirthday)
	{
		this.ownerBirthday = ownerBirthday;
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

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getOsb()
	{
		return osb;
	}

	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	public String getVsp()
	{
		return vsp;
	}

	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	public String getOwnerTb()
	{
		return ownerTb;
	}

	public void setOwnerTb(String ownerTb)
	{
		this.ownerTb = ownerTb;
	}

	public String getOwnerOsb()
	{
		return ownerOsb;
	}

	public void setOwnerOsb(String ownerOsb)
	{
		this.ownerOsb = ownerOsb;
	}

	public String getOwnerVsp()
	{
		return ownerVsp;
	}

	public void setOwnerVsp(String ownerVsp)
	{
		this.ownerVsp = ownerVsp;
	}

	public LoginType getOwnerLoginType()
	{
		return ownerLoginType;
	}

	public void setOwnerLoginType(LoginType ownerLoginType)
	{
		this.ownerLoginType = ownerLoginType;
	}

	public Long getCreateManagerLoginId()
	{
		return createManagerLoginId;
	}

	public void setCreateManagerLoginId(Long createManagerLoginId)
	{
		this.createManagerLoginId = createManagerLoginId;
	}

	public Long getConfirmManagerLoginId()
	{
		return confirmManagerLoginId;
	}

	public void setConfirmManagerLoginId(Long confirmManagerLoginId)
	{
		this.confirmManagerLoginId = confirmManagerLoginId;
	}

	@Override
	public Department getDepartment() throws BusinessException
	{
		return departmentService.getDepartment(tb, osb, vsp);
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

	@Override
	public void setDepartment(Department department)
	{
		if (clientLogin != null)
		{
			tb = department.getRegion();
			osb = department.getOSB();
			vsp = department.getVSP();
		}
		else
		{
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			tb = loanClaimConfig.getGuestLoanDepartmentTb();
			osb = loanClaimConfig.getGuestLoanDepartmentOsb();
			vsp = loanClaimConfig.getGuestLoanDepartmentVsp();
		}
	}

	@Override
	public BusinessDocumentOwner getOwner() throws BusinessException
	{
		PersonDocument personDocument = new PersonDocumentImpl();
		personDocument.setDocumentType(ownerPersonDocumentType == null ? PersonDocumentType.REGULAR_PASSPORT_RF : ownerPersonDocumentType);
		personDocument.setDocumentNumber(ownerIdCardNumber);
		personDocument.setDocumentSeries(ownerIdCardSeries);

		if (!isGuest())
		{
			SimpleService simpleService = new SimpleService();
			clientLogin = simpleService.findById(Login.class, ownerLoginId);
			ActivePerson activePerson = personService.findByLogin(clientLogin.getId());

			Login login = clientLogin;
			ActivePerson person = new ActivePerson();
			person.setLogin(login);

			person.setId(activePerson.getId());
//			person.setCreationType(activePerson.getCreationType());
			person.setFirstName(ownerFirstName);
			person.setSurName(ownerLastName);
			person.setPatrName(ownerMiddleName);
			person.setBirthDay(ownerBirthday);
			person.setClientId(activePerson.getClientId());
			Department ownerDepartment = departmentService.getDepartment(ownerTb, ownerOsb, ownerVsp);
			person.setDepartmentId(ownerDepartment.getId());
			person.setBirthDay(getOwnerBirthday());
			person.setPersonDocuments(Collections.singleton(personDocument));

			return new ClientBusinessDocumentOwner(person);
		}
		else
		{
			GuestLogin guestLogin = new GuestLoginImpl(ownerGuestPhone, ownerGuestId);

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

	private boolean isGuest()
	{
		return ownerLoginId == null;
	}

	@Override
	public void setOwner(BusinessDocumentOwner businessDocumentOwner) throws BusinessException
	{
		Login login = businessDocumentOwner.getLogin();

		ActivePerson person = businessDocumentOwner.getPerson();

		if (login instanceof GuestLogin)
		{
			GuestPerson guestPerson = (GuestPerson) person;
			ownerGuestMbk = guestPerson.isHaveMBKConnection();

			GuestLogin guestLogin = (GuestLogin) login;
			ownerGuestPhone = guestLogin.getAuthPhone();
			ownerGuestId = guestLogin.getGuestCode();

			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			ownerTb = loanClaimConfig.getGuestLoanDepartmentTb();
			ownerOsb = loanClaimConfig.getGuestLoanDepartmentOsb();
			ownerVsp = loanClaimConfig.getGuestLoanDepartmentVsp();

			ownerLoginType = LoginType.GUEST;
		}
		else
		{
			clientLogin = login;
			ownerLoginId = login.getId();

			Department department = departmentService.findById(businessDocumentOwner.getPerson().getDepartmentId());

			if(department != null)
			{
				ownerTb = department.getRegion();
				ownerOsb = department.getOSB();
				ownerVsp = department.getVSP();
			}
		}

		ownerFirstName = person.getFirstName();
		ownerLastName = person.getSurName();
		ownerMiddleName = person.getPatrName();
		ownerBirthday = person.getBirthDay();
		ownerExternalId = person.getClientId();

		Set<PersonDocument> documents = person.getPersonDocuments();
		if (documents != null && !documents.isEmpty())
		{
			PersonDocument mainDocument = documents.size() == 1 ? documents.iterator().next() : getMainDocument(documents);
			if (mainDocument != null)
			{
				ownerPersonDocumentType = mainDocument.getDocumentType();
				ownerIdCardNumber = mainDocument.getDocumentNumber();
				ownerIdCardSeries = mainDocument.getDocumentSeries();
			}
		}

	}

	@Override
	public String getFormName()
	{
		return FormConstants.EXTENDED_LOAN_CLAIM;
	}

	@Override
	public void setFormName(String formName)
	{
		if (!FormConstants.EXTENDED_LOAN_CLAIM.equals(formName))
			throw new IllegalArgumentException("Неверное значение поля навзвание формы " + formName);
		super.setFormName(formName);
	}

	public boolean checkApplicationRestriction()
	{
		return true;
	}

	// Получить главный документ
	private PersonDocument getMainDocument(Set<PersonDocument> docs)
	{
		for (PersonDocument doc : docs)
		{
			if (doc.getDocumentMain() && doc.getDocumentType() == PersonDocumentType.REGULAR_PASSPORT_RF)
				return doc;
		}
		return null;
	}

	@Override
	public State getState()
	{
		State currentState = super.getState();

		if (STATE_REFUSED.equals(currentState))
		{
			return currentState;
		}

		if (getBkiConfirmDate() == null)
		{
			return currentState;
		}

		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);

		Calendar callTime = Calendar.getInstance();
		callTime.setTimeInMillis(getBkiConfirmDate().getTimeInMillis());
		callTime.add(Calendar.DATE, config.getWaitTmResponseDays());
		if (callTime.before(Calendar.getInstance()))
		{
			currentState = STATE_REFUSED;
		}

		return currentState;
	}

	public String getLoginKI()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_LOGIN_KI);
	}

	public Set<ClaimOfferTopUp> getTopUps()
	{
		String loanOfferId = getLoanOfferId();
		if (StringHelper.isEmpty(loanOfferId))
		{
			return Collections.emptySet();
		}

		try
		{
			LoanOffer loanOffer = getLoanOffer();
			if (loanOffer == null)
			{
				return Collections.emptySet();
			}

			Set<ClaimOfferTopUp> result = new HashSet<ClaimOfferTopUp>();
			for (OfferTopUp topUp : loanOffer.getTopUps())
			{
				ClaimOfferTopUp claimTopUp = new ClaimOfferTopUp();
				claimTopUp.setAgreementNumber(topUp.getAgreementNumber());
				claimTopUp.setLoanAccountNumber(topUp.getLoanAccountNumber());
				claimTopUp.setStartDate(topUp.getStartDate());
				claimTopUp.setIdSource(topUp.getIdSource());
				claimTopUp.setTotalRepaymentSum(topUp.getTotalRepaymentSum());
				claimTopUp.setTopUpLoanBlockCount(topUp.getTopUpLoanBlockCount());
				claimTopUp.setIdContract(topUp.getIdContract());
				claimTopUp.setCurrency(topUp.getCurrency());
				claimTopUp.setMaturityDate(topUp.getMaturityDate());
				claimTopUp.setTotalAmount(topUp.getTotalAmount());
				result.add(claimTopUp);
			}

			return result;
		}
		catch (BusinessException e)
		{
			String message = "Возникла ошибка при попытке получить Top-Up предложения клиента";

			log.error(message, e);
			throw new RuntimeException(message, e);
		}
	}

	public void setLoginKI(String loginKI)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_LOGIN_KI, loginKI);
	}

	public Calendar getBkiConfirmDate()
	{
		return bkiConfirmDate;
	}

	public void setBkiConfirmDate(Calendar bkiConfirmDate)
	{
		this.bkiConfirmDate = bkiConfirmDate;
	}

	public String getBkiClaimNumber()
	{
		return getNullSaveAttributeStringValue(BKI_CLAIM_NUMBER);
	}

	public void clearBkiClaimNumber()
	{
		setNullSaveAttributeStringValue(BKI_CLAIM_NUMBER, null);
	}

	public boolean isInWaitTM()
	{
		return BooleanUtils.isTrue((Boolean) getNullSaveAttributeValue(ATTRIBUTE_IN_WAIT_TM));
	}

	public void setInWaitTM(boolean inWaitTM)
	{
		setNullSaveAttributeBooleanValue(ATTRIBUTE_IN_WAIT_TM, inWaitTM);
	}

	public void setVisitOfficeReason(String visitOfficeReason)
	{
		setNullSaveAttributeStringValue(VISIT_OFFICE_REASON, visitOfficeReason);
	}

	public String getVisitOfficeReason()
	{
		return getNullSaveAttributeStringValue(VISIT_OFFICE_REASON);
	}

	public void setOpenedAccountsCount(Long openedAccountsCount)
	{
		setNullSaveAttributeLongValue(OPENED_ACCOUNTS_COUNT, openedAccountsCount);
	}

	public Long getOpenedAccountsCount()
	{
		Long openedAccountsCount = getNullSaveAttributeLongValue(OPENED_ACCOUNTS_COUNT);
		return openedAccountsCount == null ? 0 : openedAccountsCount;
	}

	public void setConfirmedOfferId(Long confirmedOfferId)
	{
		setNullSaveAttributeLongValue(CONFIRMED_OFFER_ID, confirmedOfferId);
	}

	public Long getConfirmedOfferId()
	{
		Long confirmedOfferId = getNullSaveAttributeLongValue(CONFIRMED_OFFER_ID);
		return confirmedOfferId == null ? 0 : confirmedOfferId;
	}

	public void setNewOpenedAccount(String accountNumber) throws BusinessException
	{
		ExternalResourceLink accountLink = externalResourceService.findLinkByNumber(getOwnerLoginId(), ResourceType.ACCOUNT, accountNumber);
		if (accountLink == null)
			throw new BusinessException("Не найден счет по номеру счета " + accountNumber);

		setNullSaveAttributeStringValue(ATTRIBUTE_LOAN_ISSUE_RESOURCE, "account:" + accountLink.getId());
		List<LoanIssueMethod> loanIssueMethods = loanClaimDictionaryService.getLoanIssueMethodAvailable();
		String code= "";
		for (LoanIssueMethod loanIssueMethod : loanIssueMethods)
			if (loanIssueMethod.isNewProductForLoan() && loanIssueMethod.getProductForLoan().equals(LoanIssueMethod.LoanProductType.CURRENT_ACCOUNT))
				code = loanIssueMethod.getCode();
		setNullSaveAttributeStringValue(ATTRIBUTE_LOAN_ISSUE_METHOD_CODE, code);

		Long openedAccountsCount = getOpenedAccountsCount();
		setOpenedAccountsCount(++openedAccountsCount);
	}


	public String getReceivingResourceId()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_ISSUE_RESOURCE);
	}

	public String getRegionName()
	{
		return getNullSaveAttributeStringValue(LoanCardClaim.REGION_NAME);
	}

	public void setRegionName(String regionName)
	{
		setNullSaveAttributeStringValue(LoanCardClaim.REGION_NAME, regionName);
	}

	public Long getRegionId()
	{
		return getNullSaveAttributeLongValue(LoanCardClaim.REGION_ID);
	}

	public void setRegionId(Long regionId)
	{
		setNullSaveAttributeLongValue(LoanCardClaim.REGION_ID, regionId);
	}

	public String getPersonTB()
	{
		return getNullSaveAttributeStringValue(LoanCardClaim.PERSON_TB);
	}

	public void setPersonTB(String personTB)
	{
		setNullSaveAttributeStringValue(LoanCardClaim.PERSON_TB, personTB);
	}

	public LoanOffer getLoanOffer() throws BusinessException
	{
		if (StringHelper.isEmpty(getLoanOfferId()))
		{
			return null;
		}

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return personData.getLoanOffer(OfferId.fromString(getLoanOfferId()));
	}
}