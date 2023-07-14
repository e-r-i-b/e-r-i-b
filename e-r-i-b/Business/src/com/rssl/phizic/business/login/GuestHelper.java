package com.rssl.phizic.business.login;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.GuestLoginImpl;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.GuestPersonData;
import com.rssl.phizic.business.clients.DefaultClientIdGenerator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Document;
import com.rssl.phizic.logging.system.Log;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author niculichev
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */
public class GuestHelper
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Web);
	public static final PhoneNumberFormat PHONE_FORMAT = PhoneNumberFormat.MOBILE_INTERANTIONAL;

	//�������� ��������� ��������� �������������� �������, �.�. ��� ����� ���������� �����������
	private static final DefaultClientIdGenerator clientIdGenerator = new DefaultClientIdGenerator();
	private static final DepartmentService departmentService = new DepartmentService();

	public static Person synchronize(AuthData authData) throws ParseException, BusinessException
	{
		GuestPerson person = new GuestPerson();
		person.setFirstName(authData.getFirstName());
		person.setSurName(authData.getLastName());
		person.setPatrName(authData.getMiddleName());
		person.setBirthDay(DateHelper.toCalendar(DateHelper.parseXmlDateTimeFormatWithoutMilliseconds(authData.getBirthDate())));
		person.setClientId(clientIdGenerator.generate(null));
		PersonDocument passport = new PersonDocumentImpl();
		passport.setDocumentSeries(authData.getDocument());
		Set<PersonDocument> documents = new HashSet<PersonDocument>();
		documents.add(passport);
		person.setPersonDocuments(documents);
		person.setLogin(new GuestLoginImpl(authData.getGuestPhone(), authData.getGuestCode()));
		updateDepartmentIdInPerson(person, authData.getCB_CODE());
		return person;
	}

	public static GuestPerson synchronize(UserInfo userInfo, GuestLogin guestLogin, String docSeries, String docNumber) throws BusinessException
	{
		GuestPerson person = new GuestPerson();
		person.setFirstName(userInfo.getFirstname());
		person.setSurName(userInfo.getSurname());
		person.setPatrName(userInfo.getPatrname());
		person.setBirthDay(userInfo.getBirthdate());
		person.setClientId(clientIdGenerator.generate(null));
		PersonDocument passport = new PersonDocumentImpl();
		passport.setDocumentSeries(docSeries);
		passport.setDocumentNumber(docNumber);
		Set<PersonDocument> documents = new HashSet<PersonDocument>();
		documents.add(passport);
		person.setPersonDocuments(documents);
		person.setLogin(new GuestLoginImpl(guestLogin.getAuthPhone(), guestLogin.getGuestCode()));
		updateDepartmentIdInPerson(person, userInfo.getTb());
		return person;
	}

	public static void updateAuthenticationContext(AuthenticationContext context, AuthData authData)
	{
		context.setGuestProfileId(authData.getGuestProfileId());
		context.setUserAlias(authData.getUserAlias());
		context.setTB(authData.getCB_CODE());
	}

	/**
	 * ��������, ���� � ����� �����-������ �������.
	 * @return ��, ���� ����. ��� � ��������� ������.
	 */
	public static boolean hasAccount()
	{
		Long guestId = PersonContext.getPersonDataProvider().getPersonData().getGuestProfileId();
		Long CSAId = PersonContext.getPersonDataProvider().getPersonData().getCSAProfileId();
		if (guestId != null || CSAId != null)
		{
			return true;
		}
		return false;
	}

	/**
	 * ��������, ���� �� � ����� �������� �������
	 * @return ��, ���� ���� �������� �������. ���, � ��������� ������.
	 */
	public static boolean hasGuestAccount()
	{
		Long guestId = null;
		if (PersonContext.isAvailable())
		{
			guestId = PersonContext.getPersonDataProvider().getPersonData().getGuestProfileId();
		}
		return guestId != null;
	}

	/**
	 * ��������, ����� �� ����� �� �������� ��� �� ������
	 * @return true, ���� ����� ����� �� ������ (������������������ �����)
	 */
	public static boolean isLogonGuest()
	{
		String guestLoginAlias = null;
		if (PersonContext.isAvailable())
		{
			guestLoginAlias = PersonContext.getPersonDataProvider().getPersonData().getGuestLoginAlias();
		}
		return StringHelper.isNotEmpty(guestLoginAlias);
	}

	/**
	 * ��������, ���� �� ������� ����� � ��.
	 * @return ��, ���� ����. ��� � ��������� ������.
	 */
	public static boolean hasPhoneInMB()
	{
		Boolean hasPhone = PersonContext.getPersonDataProvider().getPersonData().isMB();
		return BooleanUtils.isTrue(hasPhone);
	}

	/**
	 * ��������� ������ �� ��������������� � CSA
	 * @return ������ �� ���������������
	 */
	public static String getAddressOfSelfRegistrationCSA()
	{
		AuthConfig authConfig = AuthGateSingleton.getAuthService().getConfig();
		return authConfig.getProperty("csaFront.registration.url", "#");
	}

	/**
	 * ��������� �������� ����� �������������� �����������:
	 * <ol>
	 *     <li>��������� �� ������� � ��</li>
	 *     <li>���� �� � ��� �������� ������� ������</li>
	 * </ol>
	 */
	public static void fillGuestContextAdditionData() throws BusinessException, BusinessLogicException
	{
		if (!PersonContext.isAvailable())
		{
			return;
		}

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData.isGuest())
			{
				GuestPersonData guestPersonData = (GuestPersonData) personData;
				if (guestPersonData.isNeedFillContextDataAboutAccountCSAAndMB())
				{
					GuestLogin login = (GuestLogin) guestPersonData.getLogin();
					//��������� ������ �� Back'�
					Document document = CSABackRequestHelper.getAdditionInformationForGuest(login.getAuthPhone());
					String bufCSAProfileId = XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.PROFILE_ID_TAG);
					Long CSAProfileId = StringHelper.isEmpty(bufCSAProfileId) ? null : Long.valueOf(bufCSAProfileId);
					Boolean hasPhoneInMobileBank = BooleanUtils.toBoolean(XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.PHONE_CONNECT_MB_TAG));

					//������������� ��������� ���������� �������
					guestPersonData.setCsaProfileId(CSAProfileId);
					guestPersonData.setHasPhoneInMB(hasPhoneInMobileBank);

					guestPersonData.markThatContextFillDataAboutAccountCSAAndMB();
				}
			}
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������� ����� �������������� �����������, ���� � ���� ���� ��������� ���������������:
	 * <ol>
	 *     <li>��������� �� ������� � ��</li>
	 *     <li>���� �� � ��� �������� ������� ������</li>
	 * </ol>
	 * @return ���, ���� �� ������� ��������� �������� ������. ��, �� ���� ��������� �������.
	 */
	public static boolean fillGuestContextAdditionDataIfNotGuestId()
	{

		if (!PersonContext.isAvailable())
		{
			return false;
		}

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		if (!personData.isGuest())
		{
			return true;
		}

		try
		{
			Long guestId = personData.getGuestProfileId();
			if (guestId == null)
			{
				fillGuestContextAdditionData();
			}
		}
		catch (Exception e)
		{
			LOG.error("������ ��� ������� �� ��������� �������������� ������ �� �����.", e);
			return false;
		}
		return true;
	}

	/**
	 * ��������� � ������� ID �������� �� ���������������� ������ ��.
	 * @param person ������
	 * @param tb ����� ��
	 * @throws BusinessException
	 */
	public static void updateDepartmentIdInPerson(Person person, String tb) throws BusinessException
	{
		if (StringHelper.isNotEmpty(tb))
		{
			Department department = departmentService.getDepartment(tb, null, null);
			if (department != null)
			{
				person.setDepartmentId(department.getId());
			}
		}
	}
}
