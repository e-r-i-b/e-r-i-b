package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.ReissuedCardDepartmentService;
import com.rssl.phizic.business.dictionaries.ageRequirement.AgeRequirement;
import com.rssl.phizic.business.dictionaries.ageRequirement.AgeRequirementService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.tariffPlan.TarifPlanConfigService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ext.sbrf.dictionaries.ShowOperationsSettings;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.guest.GuestType;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.common.type.PersonOldIdentityStatus;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.node.NodeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.ClientIdGenerator;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 03.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PersonHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final SecurityService securityService = new SecurityService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ReissuedCardDepartmentService reissuedCardDepartmentService = new ReissuedCardDepartmentService();
	private static final ActiveNotCreditCardFilter activeNotCreditCardFilter = new ActiveNotCreditCardFilter();
	private static final ActiveIMAccountFilter activeIMAccountFilter = new ActiveIMAccountFilter();
	private static final ActiveAccountFilter activeAccountFilter = new ActiveAccountFilter();
	private static final PersonService personService    = new PersonService();
	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();
	private static final PersonKeyService personKeyService = new PersonKeyService();
	private static final TarifPlanConfigService tarifPlanConfigService = new TarifPlanConfigService();
	private static final SimpleService simpleService = new SimpleService();
	private static final AgeRequirementService ageRequirementService = new AgeRequirementService();
	private static final PersonDocumentTypeComparator DOCUMENT_COMPARATOR = new PersonDocumentTypeComparator();
	private static final int DOCUMENT_COUNT_IN_PROFILE = 4;

	private static final List<PersonDocumentType> documentTypes;

	static
	{
		documentTypes = new ArrayList<PersonDocumentType>(PersonDocumentType.values().length);
		for (PersonDocumentType type : PersonDocumentType.values())
		{
			if (type != PersonDocumentType.PASSPORT_WAY)     //�� ������� way �� �� ����
				documentTypes.add(type);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ������� way �������, ���� ����
	 * @param person - ������
	 * @return �������� "������� way" ��� null, ���� �� ������
	 */
	public static PersonDocument getPersonPassportWay(Person person)
	{
		return getPersonDocument(person,  PersonDocumentType.PASSPORT_WAY);
	}

	/**
	 * �������� ������� ��� �� �������������� ������������
	 * @param personId ������������� ������������
	 * @return ������� ���
	 * @throws BusinessException
	 */
	public static PersonDocument getPersonPassportWay(Long personId) throws BusinessException
	{
		if (personId == null)
		{
			throw new IllegalArgumentException("������������� ������������ �� ����� ���� null.");
		}

		List<PersonDocument> documents = personService.getPersonDocuments(personId);

		if (CollectionUtils.isEmpty(documents))
		{
			throw new BusinessException("�� ������� ��������� ��� ������������ � id " + personId);
		}

		for (PersonDocument document : documents)
		{
			if (PersonDocumentType.PASSPORT_WAY == document.getDocumentType())
			{
				return document;
			}
		}

		throw new BusinessException("�� ������ ������� way ��� ������������ � id " + personId);
	}

	/**
	 * ������� ������� WAY �������
	 * ���� ��� - ������������������� ������������ �������� ������� ��� ������� WAY
	 * ����� ������ � ������� ����������� ������ (���� ���� � ��, � ��)
	 * @param person - ������
	 * @return �������� "������� way" ��� null, ���� �� ������
	 */
	public static PersonDocument getOrMakePersonPassportWay(Person person)
	{
		PersonDocument doc = getPersonPriorityDocument(person);
		if(doc == null)
			return null;

		if (doc.getDocumentType() == PersonDocumentType.PASSPORT_WAY)
			return doc;

		PersonDocument passportWay = new PersonDocumentImpl();
		passportWay.setDocumentSeries(DocumentHelper.getPassportWayNumber(doc.getDocumentSeries(), doc.getDocumentNumber()));
		passportWay.setDocumentType(PersonDocumentType.PASSPORT_WAY);

		return passportWay;
	}

	/**
	 * �������� ������������ �������� �������(�� ��������� ����, �������� "��������" � �������� "��������� ��������������� ��������")
	 * @param person �������
	 * @return ������������ ��������
	 */
	public static PersonDocument getPersonPriorityDocument(Person person)
	{
		Set<PersonDocument> documents = person.getPersonDocuments();
		if (CollectionUtils.isEmpty(documents))
			return null;

		PersonDocument doc = Collections.max(documents, new PriorityComparator<PersonDocument>()
		{
			@Override
			protected Priority getPriority(PersonDocument document)
			{
				Priority priority = new Priority();

				if (document.getDocumentType() == PersonDocumentType.PASSPORT_WAY)
					priority.p1 = 1;
				if (document.getDocumentMain())
					priority.p2 = 1;
				if (document.getDocumentType() == PersonDocumentType.REGULAR_PASSPORT_RF)
					priority.p3 = 1;
				if (document.isDocumentIdentify())
					priority.p4 = 1;

				return priority;
			}
		});

		return doc;
	}

	/**
	 * ���������� �������� ������� �� ���� �������������� ���������, ���� ������� �������
	 * @param person - ������
	 * @param documentType - ��� �������������� ���������
	 * @return �������� ���� documentType ��� null, ���� �� ������.
	 */
	public static PersonDocument getPersonDocument(Person person, PersonDocumentType documentType)
	{
		if (person == null)
			throw new NullPointerException("�������� 'person' �� ����� ���� null");

		Set<PersonDocument> documents = person.getPersonDocuments();
		if (documents != null) {
			for (PersonDocument document : documents) {
				if (document.getDocumentType() == documentType)
					return document;
			}
		}
		return null;
	}

	/**
	 * @param person ������
	 * @return �������� �������� ���� ��� ������ �� null 
	 */
	public static PersonDocument getMainPersonDocument(Person person)
	{
		if (person != null)
		{

			Set<PersonDocument> personDocuments = person.getPersonDocuments();
			for (PersonDocument personDocument : personDocuments)
			{
				if (personDocument.getDocumentMain())
					return personDocument;
			}
		}
		return null;
	}

	/**
	 * �������� ���� �������. � ������� �������� ���� ��������������� ��� ������
	 * � ��� ���� ����������� ����������� ������ "�������� �����"
	 * @return TarifPlanCodeType - �������� ���� �������
	 */
	public static String getActivePersonTarifPlanCode() throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return TariffPlanHelper.getUnknownTariffPlanCode();

		String personTariffPlanCode = PersonContext.getPersonDataProvider().getPersonData().getProfile().getTariffPlanCode();

		return TariffPlanHelper.getTariffPlanCode(personTariffPlanCode);
	}

	/**
	 * �������� ���� �������. � ������� �������� ���� ��������������� ��� ������
	 * � ��� ���� ����������� ����������� ������ "�������� �����"
	 * @return TarifPlanCodeType - �������� ���� �������
	 */
	public static String getActivePersonTarifPlanCodeWithCheckPerms() throws BusinessException
	{
		return (!PersonContext.isAvailable() || !PermissionUtil.impliesService("ReducedRateService")) ?
				TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
	}

	/**
	 * ���������� �� ��� ��������� ����� ����������� ���� ��� ���������� ������������� ��������
	 * @param tarifPlanCodType - �������� ����
	 * @return
	 */
	public static boolean needShowStandartRate(String tarifPlanCodType) throws BusinessException
	{
		if (StringHelper.isEmpty(tarifPlanCodType))
			return false;

		TariffPlanConfig tarifPlanConfig = tarifPlanConfigService.getTarifPlanConfigByTarifPlanCodeType(tarifPlanCodType);
		return (tarifPlanConfig == null) ? false : tarifPlanConfig.isNeedShowStandartRate();
	}

	public static String getTarifPlanConfigMeessage(String code) throws BusinessException
	{
		TariffPlanConfig tarifPlan = tarifPlanConfigService.getLocaledTarifPlanConfigByTarifPlanCodeType(code);
		if (tarifPlan == null)
			return null;

		String privilegedRateMessage = tarifPlan.getPrivilegedRateMessage();

		Map<String, String> params = new HashMap<String, String>();
		params.put("<tarifPlan/>", tarifPlan.getName());
		privilegedRateMessage = XmlHelper.getWithHtmlTag(privilegedRateMessage, params);

		return privilegedRateMessage;
	}
	/**
	 * ��������� (�������� � �����������) ������� way �������
	 * @param person - ������
	 * @param passportWayNumber - ����� �������� way
	 */
	public static void updatePersonPassportWay(Person person, String passportWayNumber)
	{
		if (person == null)
			throw new NullPointerException("�������� 'person' �� ����� ���� null");
		if (StringHelper.isEmpty(passportWayNumber))
			throw new IllegalArgumentException("�������� 'passportWayNumber' �� ����� ���� ������");

		Set<PersonDocument> documents = person.getPersonDocuments();
		if (documents == null)
			documents = new HashSet<PersonDocument>();

		// 1. ���� ���������� �������� way
		PersonDocument passportWay = null;
		for (PersonDocument document : documents) {
			if (document.getDocumentType() == PersonDocumentType.PASSPORT_WAY) {
				passportWay = document;
				break;
			}
		}

		// (2) ����������� ��������� way ����, => ������ ����� � ��������� ��� � ���
		if (passportWay == null) {
			passportWay = new PersonDocumentImpl();
			passportWay.setDocumentType(PersonDocumentType.PASSPORT_WAY);
			documents.add(passportWay);
		}

		// 3. ��������� ����� ��������� way (���������� � �����, ��� ��� � ������ ����������� ���������� ���
		// �����)
		passportWay.setDocumentSeries(passportWayNumber);

		person.setPersonDocuments(documents);
	}

	public static boolean isRegisteredGuest()
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return isGuest() && personData.getGuestType() == GuestType.REGISTERED;
	}

	/**
	 * ��������, ���� �� � ������� ������� ��
	 * @param person ������ ��� ��������
	 * @return true - ��; false - ���
	 */
	public static Boolean hasRegularPassportRF(Person person)
	{
		Set<PersonDocument> documents = person.getPersonDocuments();

		if (isGuest() && !isRegisteredGuest())
		{
			return false;
		}

		if (documents != null)
		{
			// ���� ������� ��
			for (PersonDocument document : documents)
			{
				if (document.getDocumentType().equals(PersonDocumentType.REGULAR_PASSPORT_RF))
					return true;
			}
		}

		return false;
	}

	/**
	 * ��������, ���� �� � ������� ������� ��
	 * ����������� ������ �� �������� ���������.
	 * @return true - ������� �� ����
	 */
	public static Boolean hasRegularPassportRF()
	{
		return hasRegularPassportRF(PersonContext.getPersonDataProvider().getPersonData().getPerson());
	}

	/**
	 * @return true, ���� ������� ������������ ������������
	 */
	public static boolean isPersonBlocked()
	{
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData != null && personData.isPersonBlocked()) {
				Person person = personData.getPerson();
				Login login = person.getLogin();
				log.info("������ ������������. LOGIN_ID=" + login.getId());
				return true;
			}
		}

		return false;
	}

	/**
	 * @return true, ���� ������� ������������ ���������� � �������������
	 */
	public static boolean isFraud()
	{
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			return personData != null && personData.isFraud();
		}
		return false;
	}

	/**
	 * @return ������������� ������, � ������ ������� ����������� ���� ������� � �������
	 */
	public static String getPersonLogonSessionId()
	{
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData != null) {
				return personData.getLogonSessionId();				
			}
		}
		return null;
	}

	/**
	 * @return ������������� ������, � ������ ������� ����������� ���� ������� � �������
	 */
	public static boolean needKick()
	{
		if (PersonContext.isAvailable())
		{
			Calendar kickTime = NodeContext.getKickTime();
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			return kickTime != null && kickTime.after(personData.getCreationDate());
		}
		return false;
	}

	/**
	 * �������� �� ������� ������������ �����������.
	 * @return true - ���������
	 */
	public static boolean isPensioner()
	{
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData != null)
			{
				ActivePerson person = personData.getPerson();
				return isPensioner(person);
			}
		}
		return false;
	}

	/**
	 * �������� �� ������������ �����������.
	 * @param person ������
	 * @return true - ���������
	 */
	public static boolean isPensioner(Person person)
	{
		if (person.getBirthDay() == null)
			return false;
		if (person.getGender() == null)
			return false;

		Long yearsDiff = DateHelper.yearsDiff(person.getBirthDay(), DateHelper.getCurrentDate());
		try
		{
			AgeRequirement ageRequirement = ageRequirementService.getPensionRequirement();
			if (ageRequirement == null)
				return false;

			if (StringHelper.equals(person.getGender(), "F"))
				return yearsDiff >= ageRequirement.getLowLimitFemale();
			else
				return yearsDiff >= ageRequirement.getLowLimitMale();
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ����������� ����������� ��������", e);
			return false;
		}
	}

	/**
	 * �������� �� ������ ���������������
	 * @param person - ������
	 * @param periodInMonth - ������ � ������� ��� ���������� ���� ��������
	 * @return true, ���� ��������
	 */
	public static boolean isNearPensioner(Person person, int periodInMonth)
	{
		Calendar personBirthDay = person.getBirthDay();
		int clientAgeLimit = StringHelper.equalsNullIgnore(person.getGender(), "M") ? 60 : 55;
		Calendar pensionAge = Calendar.getInstance();
		pensionAge.setTime(DateHelper.add(personBirthDay.getTime(), clientAgeLimit, 0, 0));

		boolean isNowPensioner = !(DateHelper.getCurrentDate().before(pensionAge));
		if (isNowPensioner)
			return false;

		Calendar onDate = DateHelper.addMonths(DateHelper.getCurrentDate(), periodInMonth);
		return !onDate.before(pensionAge);
	}

	/**
	 * ���������� ��� � ������� "��� �������� �."
	 * @param firstName ���
	 * @param surName �������
	 * @param patrName ��������
	 * @return ��� � ������� "��� �������� �."
	 */
	public static String getFormattedPersonName(String firstName, String surName, String patrName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(StringHelper.getEmptyIfNull(firstName));
		if (!StringHelper.isEmpty(patrName))
			builder.append(" ").append(patrName);
		if (!StringHelper.isEmpty(surName))
			builder.append(" ").append(surName.charAt(0)).append(".");
		return builder.toString();
	}

	/**
	 * ���������� ��� � ������� "��� �������� �."
	 * @param firstName ���
	 * @param surName �������
	 * @param patrName ��������
	 * @return ��� � ������� "��� �������� �."
	 */
	public static String getFormattedPersonFIO(String firstName, String surName, String patrName)
	{
		StringBuilder builder = new StringBuilder();
		if (StringHelper.isNotEmpty(firstName))
		{
			builder.append(Character.toUpperCase(firstName.charAt(0)));
			builder.append(firstName.substring(1).toLowerCase());
		}
		if (StringHelper.isNotEmpty(patrName))
		{
			builder.append(" ").append(Character.toUpperCase(patrName.charAt(0)));
			builder.append(patrName.substring(1).toLowerCase());
		}
		if (!StringHelper.isEmpty(surName))
			builder.append(" ").append(surName.toUpperCase().charAt(0)).append(".");
		return builder.toString();
	}

	/**
	 * ���������� ��� ������� person � ������� "��� �������� �."
	 * @param person ������
	 * @return ��� ������� � ������� "��� �������� �."
	 */
	public static String getFormattedPersonName(Person person)
	{
		return getFormattedPersonName(person.getFirstName(), person.getSurName(), person.getPatrName());
	}

	/**
	 * ���������� ��� ������� �� PersonContext-� � ������� "��� �������� �."
	 * ���� �������� ����, �� ������������ ������ ������
	 * @return ��� ������� � ������� "��� �������� �."
	 */
	public static String getFormattedPersonName()
	{
		String formatted = null;
		if (PersonContext.isAvailable())
		{
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			formatted = getFormattedPersonName(person);
		}
		return StringHelper.getEmptyIfNull(formatted);
	}

	/**
	 * ���������� ������� � ������� "�."
	 * @param surName �������
	 * @return ������� � ������� "�."
	 */
	public static String getFormattedSurName(String surName)
	{
		if (StringHelper.isEmpty(surName))
			return "";
		return surName.charAt(0) + (".");
	}

	/* BUG039967 */
	/*
	 * ��������� ������������������ ����� ������� ��� ����������� ���������� � ���� ��� �.�.
	 * @param firstName ���
	 * @param surName �������
	 * @param patrName ��������
	 * @return ������� ��� ����������� ������
	 */
	/*public static String getExtraFormattedPersonName(String firstName, String surName, String patrName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(StringHelper.getEmptyIfNull(firstName));
		if (!StringHelper.isEmpty(patrName))
			builder.append(" ").append(patrName.charAt(0)).append(".");
		if (!StringHelper.isEmpty(surName))
			builder.append(" ").append(surName.charAt(0)).append(".");
		return builder.toString();
	}*/

	/**
	 * ���������� ������������
	 * @param blockUntil - �� ������ ������� �����������
	 * @param needClear - ������������� �������� ���������� �������
	 * @throws BusinessException
	 */
	public static void personLock(Calendar blockUntil, boolean needClear) throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			return;
		Person person = personData.getPerson();
		Login login = person.getLogin();
		personLock(blockUntil, login,  needClear);
		personData.setBlocked(true);
	}

	/**
	 * ���������� ������������
	 * @param blockUntil - �� ������ ������� �����������
	 * @param login - ����� ������������
	 * @param needClear - ������������� �������� ���������� �������
	 * @throws BusinessException
	 */
	public static void personLock(Calendar blockUntil, CommonLogin login,  boolean needClear) throws BusinessException
	{
		try
		{
			if (needClear)
				securityService.lockAndClear(login, blockUntil.getTime());
			else
				securityService.lock(login);

			log.info("������ ������������. LOGIN_ID=" + login.getId());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException("���������� ������������� ������������, loginId = " + login.getId(), e);
		}
	}

	/**
	 * ���������� �� �����������
	 * @param link  - ������, ��� �������� ����������/�������� �����������
	 * @return true - �����������, false - ��������
	 */
	public static boolean isShowOperations(EditableExternalResourceLink link)
	{
		// ���������� ��� ��� �������� � ��
		if (ShowOperationsSettings.isDataBaseSetting())
			return link.getShowOperations();

		// ���� � ������
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Set<String>  showOperations = person.getShowOperations();
		if (showOperations.contains(link.getCode()))
			return true;
		
		return false;
	}

	/**
	 * ���������� ���������� ������ ��� �������
	 * @param person - �������
	 */
	public static Integer getPersonAge(Person person)
	{
		DateSpan age = new DateSpan(person.getBirthDay(),Calendar.getInstance());
		return age.getYears();
	}


	/**
	 * ����� �������� ������ ����� �������� PersonContext
	 * @return ���������� ����� ������� �� ���� ������ ������� � ����������� ������������ ������ �� ����� ��
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public static Money getPersonAccountsMoney(String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new BusinessException("�� ������� ���������� ������ � �������");
		Office office = departmentService.findById(personData.getPerson().getDepartmentId());

		Money accountsMoney = Money.fromCents(0L,nationalCurrency);
		List<AccountLink> accountLinkList = personData.getAccounts(activeAccountFilter);

		for(AccountLink link: accountLinkList)
		{
			Money amount = MoneyUtil.getBalanceInNationalCurrency(link.getAccount().getBalance(),nationalCurrency,
					office,CurrencyRateType.CB, tarifPlanCodeType);
			accountsMoney = accountsMoney.add(amount);
		}

		return accountsMoney;
	}

	/**
	 * ����� �������� ������ ����� �������� PersonContext
	 * @return ���������� ����� ������� �� ���� ������ �������(����� ���������) � ����������� ������������ ������ �� ����� ��
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public static Money getPersonCardsMoney(String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new BusinessException("�� ������� ���������� ������ � �������");
		Office office = departmentService.findById(personData.getPerson().getDepartmentId());

		Money cardsMoney = Money.fromCents(0L,nationalCurrency);
		List<CardLink> cardLinkList = personData.getCards(activeNotCreditCardFilter);

		for(CardLink link: cardLinkList)
		{
			Money amount = MoneyUtil.getBalanceInNationalCurrency(link.getCard().getAvailableLimit(),nationalCurrency,
					office, CurrencyRateType.CB, tarifPlanCodeType);
			cardsMoney = cardsMoney.add(amount);
		}

		return cardsMoney;
	}


	/**
	 * ����� �������� ������ ����� �������� PersonContext
	 * @return ���������� ����� ������� �� ��� ������� � �������� ����������� �� ����� ��
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Money getPersonIMAccountMoney(String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new BusinessException("�� ������� ���������� ������ � �������");
		Office office = departmentService.findById(personData.getPerson().getDepartmentId());

		Money imAccountsMoney = Money.fromCents(0L,nationalCurrency);
		List<IMAccountLink> imAccountList = personData.getIMAccountLinks(activeIMAccountFilter);

		for(IMAccountLink link: imAccountList)
		{
			Money amount = MoneyUtil.getBalanceInNationalCurrency(link.getImAccount().getBalance(), nationalCurrency,
					office, CurrencyRateType.CB, tarifPlanCodeType);
			imAccountsMoney = imAccountsMoney.add(amount);
		}
		return imAccountsMoney;
	}
	/**
	 * ����� �������� ������ ����� �������� PersonContext
	 * @return ���������� ����� ������� �� �������������� ������������ ������� � �������� ����������� �� ����� ��
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Money getPersonSecurityAccountMoney(String tarifPlanCodeType) throws BusinessException, BusinessLogicException
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new BusinessException("�� ������� ���������� ������ � �������");
		Office office = departmentService.findById(personData.getPerson().getDepartmentId());

		Money securityAccountsMoney = Money.fromCents(0L,nationalCurrency);
		List<SecurityAccountLink> securityAccountLinks = personData.getSecurityAccountLinks();

		for(SecurityAccountLink link: securityAccountLinks)
		{
			SecurityAccount securityAccount = link.getSecurityAccount();
			Money nominalAmount = securityAccount.getNominalAmount();
			if  (securityAccount.getTermStartDt().compareTo(DateHelper.getCurrentDate()) < 0)
				nominalAmount = nominalAmount.add(securityAccount.getIncomeAmt());

			Money amount = MoneyUtil.getBalanceInNationalCurrency(nominalAmount, nationalCurrency,
						office, CurrencyRateType.CB, tarifPlanCodeType);
			securityAccountsMoney = securityAccountsMoney.add(amount);
		}
		return securityAccountsMoney;
	}

	/**
	 * ����� ���������� ���� �� � ������� ��� ��� ����������� �� ���������
	 * @return Boolean - ���� �� � ������� ���
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static boolean haveThePersonIMAccount() throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
		{
			log.error("�� ������� �������� ������ ������� �� PersonContext");
			return false;
		}

		List<IMAccountLink> imAccountLinks = personData.getIMAccountLinksAll();
		return CollectionUtils.isNotEmpty(imAccountLinks);
	}

	/**
	 * ������������ ������ � ����� ���������
	 * @param fullSeries - ����� � �����
	 * @return - ��������������� ������
	 */
	public static String getCutDocumentSeries(String fullSeries)
	{
		if (fullSeries == null)
			return "";
		return fullSeries.replace(" ", "").replaceAll("([0-9]{4})([0-9]{0})([0-9]*)([0-9]{2})", "$1 ****$4");
	}
	/**
	 * ������������ ������ ���������
	 * @param number - �����
	 * @return - ��������������� ������
	 */
	public static String getCutDocumentNumber(String number)
	{
		if (number == null)
			return "";
		return number.replace(" ", "").replaceAll("([0-9]*)([0-9]{2})", "****$2");
	}

	/**
	 * ������������ ������ ��� �� � ������������� � ����������� ������������� ��������
	 * @param number - �����
	 * @return - ��������������� ������
	 */
	public static String getCutNumberForDLorRC(String number)
	{
		if (number == null)
			return "";
		return number.replace(" ", "").replaceAll("([0-9]*)([0-9]{4})", "****$2");
	}

	/**
	 * ������������ ����� ��� �� � ������������� � ����������� ������������� ��������
	 * @param number - �����
	 * @return - ��������������� ������
	 */
	public static String getCutSeriesForDLorRC(String number)
	{
		if (number == null)
			return "";
		return number.replace(" ", "").replaceAll("([0-9,A-Z,�-�]{2})([0-9,A-Z,�-�]*)", "$1****");
	}

	/**
	 * @return ������ ����� ���������� �������, ��� �������� way
	 */
	public static List<PersonDocumentType> getDocumentTypes()
	{
		return Collections.unmodifiableList(documentTypes);
	}

	/**
	 * ���������� ������� ����� �������.
	 *
	 * @return ������� ����� �������.
	 * @throws BusinessException
	 */
	public static Login getLastClientLogin() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new BusinessException("�� ������� ���������� ������ � �������");

		return personData.getPerson().getLogin();
	}

	/**
	 * ���������� �������� �� ������ ����
	 * ���� �������� ������������ �� ��������, �� ������������ false.
	 * @return true - ������ ����
	 */
	public static boolean isUdboSupported()
	{
		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			return personData.getPerson().getCreationType() == CreationType.UDBO;
		}
		return false;
	}

	/**
	 * ���������� �� ������� ��������� � ���, ��� � ���� ���������� �������
	 * @return true - ����������
	 */
	public static boolean showOldBrowserMessage()
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		if (dataProvider == null)
			return true;

		PersonData data = dataProvider.getPersonData();
		if (data == null)
			return true;

		return data.isShowOldBrowserMessage();
	}

	/**
	 * @param department ����������� �� �������� ����������
	 * @return clientId
	 */
	public static String generateClientId(Department department) throws BusinessException, BusinessLogicException
	{
		try
		{
			ClientIdGenerator generator = ClientIdGenerator.getInstance();
			return generator.generate(department);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * @deprecated ENH066671
	 *
	 * ��������� ���������� �������
	 * @param person ����� ������
	 * @param department �����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void saveNewPerson(ActivePerson person, Department department) throws BusinessException, BusinessLogicException
	{
		saveNewPersonNoScheme(person,department);
		schemeOwnService.setClientDefaultSchemes(person.getLogin(), DefaultSchemeType.getDefaultSchemeType(person.getCreationType()), department.getRegion());
	}

	private void saveNewPersonNoScheme(ActivePerson person, Department department) throws BusinessLogicException, BusinessException
	{
		ActivePerson cardClient = getCardClient(person,department);

		if(cardClient!=null)
		{
			person.setId(cardClient.getId());
			person.setLogin(cardClient.getLogin());
			personService.update(person);
			return;
		}

		try
		{
			personService.createLogin(person);
			Login login = person.getLogin();
			securityService.lock(login);
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ��� �������� ������", e);
		}
		personService.add(person);
	}

	/**
	 * @deprecated ENH066671
	 *
	 * @param person ����� ������
	 * @param department �����������
	 * @param accessType ��� �������
	 * @param scheme     ����� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void saveNewPerson(ActivePerson person, Department department,  AccessType accessType, AccessScheme scheme) throws BusinessException, BusinessLogicException
	{
		saveNewPersonNoScheme(person,department);
		schemeOwnService.setScheme(person.getLogin(), accessType, scheme);
	}


	private ActivePerson getCardClient(ActivePerson person,Department department) throws BusinessException, BusinessLogicException
	{
		List<ActivePerson> duplicatePersons = null;
		for (PersonDocument document: person.getPersonDocuments())
		{
			duplicatePersons = personService.getByFIOAndDoc(person.getSurName(), person.getFirstName(), person.getPatrName(),
																					document.getDocumentSeries(),
																						document.getDocumentNumber(),
																							person.getBirthDay(),
																								department.getCode().getFields().get("region"));
			if (duplicatePersons != null && duplicatePersons.size()>0)
				break;
		}

		for (ActivePerson duplicate : duplicatePersons)
		{
			switch(duplicate.getCreationType())
			{
				case CARD:
					return duplicate;
				case UDBO:
					throw new BusinessLogicException("������ ������� �������� ���� �������");
			}
		}
		// ���� ��������� ���� ����-������� - ����������� ������� ��������.
		return null;
	}

	/**
	 * ���������� ���� � ������������� ���������� ������ �������.
	 */
	public static void setPersonDataNeedUpdate()
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		if (dataProvider == null)
			return;

		PersonData data = dataProvider.getPersonData();
		if (data == null)
			return;

		data.needPersonUpdate();
	}

	/**
	 * @return  ������ ���� ����� ���������� �������
	 */
	public static List<String> getDocumentStringTypes()
	{
		List<String> documentStringTypes = new ArrayList<String>(PersonDocumentType.values().length);
		for (PersonDocumentType value : PersonDocumentType.values())
			documentStringTypes.add(value.toString());
		return documentStringTypes;
	}

	/**
	 * @param person ������
	 * @return ���������� ������ ����� ����� ���������� ������� � ������� �������� ��� ��������
	 */
	public static List<String> getPersonSeriesAndNumbers(ActivePerson person)
	{
		List<String> seriesAndNumbers = new ArrayList<String>();
		Set<PersonDocument> documents = person.getPersonDocuments();
		for (PersonDocument doc:documents)
		{
			StringBuilder sb = new StringBuilder();
			String series = doc.getDocumentSeries();
			if (!StringHelper.isEmpty(series))
			{
				series = StringUtils.remove(series,' ');
				sb.append(series.toUpperCase());
			}

			String number = doc.getDocumentNumber();
			if (!StringHelper.isEmpty(number))
			{
				number = StringUtils.remove(number,' ');
				sb.append(number.toUpperCase());
			}

			seriesAndNumbers.add(sb.toString());
		}
		return seriesAndNumbers;
	}

	/**
	 * @param person ������
	 * @return �� �������
	 */
	public static String getPersonTb(Person person) throws BusinessException
	{
		ExtendedCodeImpl code = (ExtendedCodeImpl)departmentService.findById(person.getDepartmentId()).getCode();
		return code.getRegion();
	}

	/**
	 * ���������� ��� � ��� ������� � ������� ���/���
	 *
	 * @param person ������
	 * @return �� �������
	 */
	public static String getPersonOSBVSP(Person person) throws BusinessException
	{
		ExtendedCodeImpl code = (ExtendedCodeImpl)departmentService.findById(person.getDepartmentId()).getCode();
		return code.getBranch() + "/" + code.getOffice();
	}

	/**
	 * ���������� ��� ��� �������. 11 ���� ��� ��������: 2-��� ��, 4-��� ���, 5-��� ���
	 *
	 * @param person ������
	 * @return �� �������
	 */
	public static String getPersonTbOsbVsp(Person person) throws BusinessException
	{
		ExtendedCodeImpl code = (ExtendedCodeImpl)departmentService.findById(person.getDepartmentId()).getCode();
		String tb = StringHelper.addLeadingZeros(code.getRegion(), 2);
		String osb = StringHelper.addLeadingZeros(code.getBranch(), 4);
		String vsp = StringHelper.addLeadingZeros(code.getOffice(), 5);
		return tb + osb + vsp;
	}

	/**
	 * @param code ��������� ����� ���
	 * @return ���������� ��������� ������ � ��������� ���
	 * @throws BusinessException
	 */
	public static boolean checkDUL(String code) throws BusinessException
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		CreationType type = person.getCreationType();
		Set<PersonDocument> documents = person.getPersonDocuments();
		if (documents.isEmpty())
			throw new BusinessException("������ ���������� ����");
		
		if (type == CreationType.UDBO)
		{
			for (PersonDocument document : documents)
			{
				if (document.getDocumentType() == PersonDocumentType.REGULAR_PASSPORT_RF)
				{
					return document.getDocumentNumber().endsWith(code);
				}
			}
		}
		else if (type == CreationType.CARD || type == CreationType.SBOL)
		{
			for (PersonDocument document : documents)
			{
				if (document.getDocumentType() == PersonDocumentType.PASSPORT_WAY)
				{
					return document.getDocumentSeries().endsWith(code);
				}
			}
		}
		return false;
	}
	public static Integer getPersonAgeFromContext()
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		return getPersonAge(person);
	}

	/**
	 * @return ��� ��������� ����� �������� �������.
	 */
	public static String getPersonTariffPlan()
	{
		if (!PersonContext.isAvailable())
			return TariffPlanHelper.getUnknownTariffPlanCode();

		String tariffPlanCodeType = PersonContext.getPersonDataProvider().getPersonData().getPerson().getTarifPlanCodeType();
		return StringHelper.getZeroIfEmptyOrNull(tariffPlanCodeType);
	}

	/**
	 * @param listSourceName - id ������
	 * @return ��� ��������� ������� � ������ ����.
	 */
	public static int getStepShowPersonalMenuLinkList(String listSourceName)
	{
		//����������� ���� ��������� ������
		int stepShowNum = 0;
		String stepShow = null;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData != null)
		{
			ActivePerson person = personData.getPerson();
			Map<String, String> stepShowMap = person.getStepShowPersonalMenuLinkListMap();
			if (!MapUtils.isEmpty(stepShowMap))
				stepShow = stepShowMap.get(listSourceName);
			try
			{
				stepShowNum = !StringHelper.isEmpty(stepShow) ? Integer.valueOf(stepShow) : 0;
			}
			catch(NumberFormatException e)
			{
				log.info("�������� ������ ������ ��� ������������ ���� ��������� ������ :"+stepShow);
			}
		}
		return stepShowNum;
	}

	/**
	 * @return ������� �� ���������
	 */
	public static ActivePerson getContextPerson()
	{
		return PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}

	/**
	 * ��������� ������� ��������� ������� � ������� ������ ���������� � �����������
	 * @param person ������
	 * @return �������
	 * @throws BusinessException
	 */
	public static List<UserInfo> buildUserInfoHistory(Person person) throws BusinessException, BusinessLogicException
	{
		if (person == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}

		List<PersonKey> personHistory = personKeyService.findAll(person);
		List<UserInfo> userInfoHistory = new ArrayList<UserInfo>(personHistory.size() + 1);
		userInfoHistory.add(buildUserInfo(person));

		for (PersonKey personKey : personHistory)
		{
			UserInfo userInfo = new UserInfo();
			userInfo.setFirstname(personKey.getFirstName());
			userInfo.setSurname(personKey.getSurName());
			userInfo.setPatrname(personKey.getPatrName());
			userInfo.setPassport(personKey.getPassport());
			userInfo.setBirthdate(personKey.getBirthDay());
			//todo ������� ���� ��������� 38 �� 99, ��� �� ������ � ������, ����� ����� ������
			String cbCode = personKey.getTb();
			userInfo.setCbCode(cbCode.equals("38") ? "99" : cbCode);

			userInfoHistory.add(userInfo);
		}

		return userInfoHistory;
	}

	/**
	 * ��������� ���������� � ������������
	 * @param person ������
	 * @return ���������� � ������������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public static UserInfo buildUserInfo(Person person) throws BusinessLogicException, BusinessException
	{
		if (person == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}

		UserInfo userInfo = new UserInfo();
		//todo ������� ���� ��������� 38 �� 99, ��� �� ������ � ������, ����� ����� ������
		String cbCode = getPersonTb(person);
		userInfo.setCbCode(cbCode.equals("38") ? "99" : cbCode);

		userInfo.setFirstname(person.getFirstName());
		userInfo.setSurname(person.getSurName());
		userInfo.setPatrname(person.getPatrName());
		userInfo.setPassport(buildDocumentInfo(person));
		userInfo.setBirthdate(person.getBirthDay());

		return userInfo;
	}

	/**
	 * ��������� ���������� � ��������� �������������� �������� (����� + �����)
	 * @param person ������
	 * @return ���������� � ���
	 * @throws BusinessLogicException
	 */
	public static String buildDocumentInfo(Person person) throws BusinessLogicException, BusinessException
	{
		if (person == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}

		if (CollectionUtils.isEmpty(person.getPersonDocuments()))
		{
			throw new BusinessLogicException("� ������� " + person.getSurName() + " " + person.getFirstName() + " " + person.getPatrName() + " ��� ��������� ��������������� ��������.");
		}

		StringBuilder passport = new StringBuilder();
		PersonDocument document = PersonHelper.getPersonDocument(person, PersonDocumentType.PASSPORT_WAY);
		if (document == null)
		{
			List<PersonDocument> documentList = new ArrayList<PersonDocument>(person.getPersonDocuments());
			Collections.sort(documentList, DOCUMENT_COMPARATOR);
			document = documentList.get(0);

			if (document == null)
			{
				throw new BusinessException("� ������� "+ person.getId()+" �� ������ �������");
			}
		}

		passport.append(StringHelper.getEmptyIfNull(document.getDocumentSeries())).append(StringHelper.getEmptyIfNull(document.getDocumentNumber()));

		return passport.toString();
	}

	/**
	 * @return �������� �� ������� ����� �������
	 */
	public static boolean availableNewProfile()
	{
		return PermissionUtil.impliesServiceRigid("NewClientProfile");
	}

	/**
	 * ��������� ������ ���������� ������� ��� ����������� � �������
	 * @param personDocumentSet - ��������� �������
	 * @return - ��������� ��� �����������
	 */
	public static List<PersonDocument> getDocumentForProfile(Set<PersonDocument> personDocumentSet){
		List<PersonDocument> personDocuments = new LinkedList<PersonDocument>(personDocumentSet);

		Iterator<PersonDocument> iter = personDocuments.iterator();
		while (iter.hasNext())
		{
			PersonDocument doc = iter.next();
			if (doc.getDocumentType() == PersonDocumentType.PASSPORT_WAY || !doc.isDocumentIdentify())
				iter.remove();
		}
		Collections.sort(personDocuments, new PersonDocumentProfileTypeComparator());
		Iterator<PersonDocument> iterator = personDocuments.iterator();
		PersonDocument document = null;
		PersonDocument nextDocument = null;
		while (iterator.hasNext())
		{
			if (document == null)
				document = iterator.next();
			if (iterator.hasNext())
			{
				nextDocument = iterator.next();
				if (document.getDocumentType().equals(nextDocument.getDocumentType()))
					iterator.remove();
				else
					document = nextDocument;
			}
		}
		return personDocuments.subList(0, Math.min(personDocuments.size(), DOCUMENT_COUNT_IN_PROFILE));
	}

	/**
	 * ���� �� � ������� ������� �������������, � ������� �������� �������� ��������� �����
	 * @return
	 * @throws BusinessException
	 */
	public static boolean isCreditCardOfficeExist() throws BusinessException
    {
	    //������ �������
	    Region region = RegionHelper.getCurrentRegion();
        String regionCodeTb = null;

        if (region != null && region.getParent() != null)
        {
            Region parent = RegionHelper.getParentRegion(region);
            regionCodeTb =  parent.getCodeTB();
        }
        else if (region != null)
        {
            regionCodeTb = region.getCodeTB();
        }
        return reissuedCardDepartmentService.haveDepartmentForCreditCardByRegion(regionCodeTb);
    }

	/**
	 * �������� �� ���������� ������� �����������
	 * @param phoneNumber - ����� ��������
	 * @return
	 */
	public static boolean isSelfPhone(String phoneNumber) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(phoneNumber) && PersonContext.isAvailable())
			return false;
		List<String> clientSelfPhones = new LinkedList<String> (PersonContext.getPersonDataProvider().getPersonData().getPhones(false));
		String internationalPhoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber);
		return clientSelfPhones.contains(internationalPhoneNumber);
	}

	/**
	 * ���������� ������������� ������������� �������
	 * @return �������������
	 */
	public static String getCryptoClientId()
	{
		try
		{
			return ((StaticPersonData) PersonContext.getPersonDataProvider().getPersonData()).getClientId();
		}
		catch (Exception e)
		{
			log.error("�� ������� �������� ������������� ������������ ��� Pixel-�������: " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @return true - �������� �����, false � ��������� ������
	 */
	public static boolean isGuest()
	{
		return PersonContext.isAvailable() && PersonContext.getPersonDataProvider().getPersonData().isGuest();
	}

	/**
	 * @return true - ������ ��������� � ��, false - �� ���������
	 */
	public static boolean isMBConnected()
	{
		return PersonContext.isAvailable() && PersonContext.getPersonDataProvider().getPersonData().isMB();
	}

	/**
	 * �������� ���������� �������� �� ���, ���, ��
	 * @param person1 - ������ (never null)
	 * @param person2 - ������ (never null)
	 * @return true - ������� ���������
	 */
	public static boolean equalByFIODulDr(Person person1, Person person2)
	{
		if (person1 == null)
			throw new IllegalArgumentException("�������� person1 �� ����� ���� null");
		if (person2 == null)
			throw new IllegalArgumentException("�������� person2 �� ����� ���� null");
		boolean rc = getFormattedPersonFIO(person1.getFirstName(), person1.getSurName(), person1.getPatrName())
			.equals(getFormattedPersonFIO(person2.getFirstName(), person2.getSurName(), person2.getPatrName()));
		if (!rc)
			return rc;
		PersonDocument document1 = getPersonDocument(person1, PersonDocumentType.REGULAR_PASSPORT_RF);
		PersonDocument document2 = getPersonDocument(person2, PersonDocumentType.REGULAR_PASSPORT_RF);
		rc = (document1 != null && document2 != null
			&& StringHelper.equals(document1.getDocumentSeries(), document2.getDocumentSeries())
			&& StringHelper.equals(document1.getDocumentNumber(), document2.getDocumentNumber()))
			|| (document1 == null && document2 == null);
		if (!rc)
			return rc;
		return DateHelper.isEqualDate(person1.getBirthDay(), person2.getBirthDay());
	}
	
    /**
     * ���������� ��������� �� ������ � ���������� ����� (��)
     * @param phoneNumber - ����� �������� ������� � ������� 71234567890
     * @return
     */
    public static boolean isMBConnectedPerson(String phoneNumber)
    {
	    Set<String> mbkCards = null;
	    try
	    {
		    MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		    PhoneNumber mobilePhone = PhoneNumber.fromString(phoneNumber);
		    String phoneNumberWithoutCountry = mobilePhone.operator() + mobilePhone.abonent();
		    mbkCards = mobileBankService.getCardsByPhone(phoneNumberWithoutCountry);
	    }
	    catch (Exception e)
	    {
		    log.error("������ ��� ������� �������� ����� ������� �� ������ �������� ",e);
	    }

	    return CollectionUtils.isNotEmpty(mbkCards);

    }

	/**
	 * ���������� ��������� �� ������ � ���������� ����� (��) (���� �� � ������� ������� ���� ��� ����������� � ���)
	 * @param person
	 * @param cardLinks
	 * @return
	 */
	public static boolean isMBConnected(Person person, List<CardLink> cardLinks)
	{
		if (ErmbHelper.isERMBConnected(person))
			return true;

		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		List<String> cardNumbers = CardLinkHelper.listCardNumbers(cardLinks);
		String[] param = cardNumbers.toArray(new String[cardLinks.size()]);
		GroupResult<String, List<MobileBankRegistration>> registrationsByCard = mobileBankService.getRegistrations(false, param);

		for (String cardNumber : cardNumbers)
		{
			List<MobileBankRegistration> cardRegistrations = registrationsByCard.getResult(cardNumber);
			if (!CollectionUtils.isEmpty(cardRegistrations))
				return true;
		}
		return false;
	}

	/**
	 * ���������� ������� �������������� �����
	 * @return �������
	 */
	public static String getGuestPhoneNumber()
	{
		AuthenticationContext context = AuthenticationContext.getContext();
		return ((GuestLogin) context.getLogin()).getAuthPhone();
	}

	/**
	 * @param person �������
	 * @param document ������� ��������
	 * @param employee ������� ���������
	 * @return ����������������� ������
	 * @throws BusinessException
	 */
	public static PersonOldIdentity rememberCurrentIdentity(Person person, PersonDocument document, CommonLogin employee) throws BusinessException
	{
		PersonOldIdentity currentIdentity = new PersonOldIdentity();
		currentIdentity.setPerson(person);
		currentIdentity.setRegion(PersonHelper.getPersonTb(person));
		currentIdentity.setFirstName(person.getFirstName());
		currentIdentity.setSurName(person.getSurName());
		currentIdentity.setPatrName(person.getPatrName());
		currentIdentity.setBirthDay((Calendar) person.getBirthDay().clone());
		if (document != null)
		{
			currentIdentity.setDocType(document.getDocumentType());
			currentIdentity.setDocName(document.getDocumentName());
			currentIdentity.setDocNumber(document.getDocumentNumber());
			currentIdentity.setDocSeries(document.getDocumentSeries());
			if (document.getDocumentIssueDate() != null)
				currentIdentity.setDocIssueDate((Calendar) document.getDocumentIssueDate().clone());
			currentIdentity.setDocIssueBy(document.getDocumentIssueBy());
			currentIdentity.setDocIssueByCode(document.getDocumentIssueByCode());
			currentIdentity.setDocMain(document.getDocumentMain());
			if (document.getDocumentTimeUpDate() != null)
				currentIdentity.setDocTimeUpDate((Calendar) document.getDocumentTimeUpDate().clone());
			currentIdentity.setDocIdentify(document.isDocumentIdentify());
		}
		currentIdentity.setEmployee(employee);
		currentIdentity.setDateChange(Calendar.getInstance());
		currentIdentity.setStatus(PersonOldIdentityStatus.NOT_ACTIVE);
		return currentIdentity;
	}
}
