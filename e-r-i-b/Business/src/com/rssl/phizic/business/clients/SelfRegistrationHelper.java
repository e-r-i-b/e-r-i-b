package com.rssl.phizic.business.clients;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.wsclient.requests.info.ValidateLoginInfo;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.RegistrationMode;
import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.LightPerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import org.apache.commons.logging.Log;

import java.io.Serializable;
import java.util.Calendar;

/**
 * �������� � ���������������.
 *
 * @author bogdanov
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class SelfRegistrationHelper implements Serializable
{
	private static final ProfileService profileService = new ProfileService();
	private static final PersonService personService = new PersonService();
	private static final SimpleService simpleService = new SimpleService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private Profile profile;

	/**
	 * ���� ��� �������� ������ � ������.
	 */
	private static final String STORE_KEY = SelfRegistrationHelper.class.getName();

	/**
	 * ������� �� ����� ������� �����������.
	 */
	private boolean hardRegistrationMode = false;
	/**
	 * ���������� �� ���������� �������������� ��������� � ������������� �����������.
	 */
	private boolean needShowPreregistrationMessage = false;
	/**
	 * �������������� ��������� � ������������� �����������.
	 */
	private String preregistrationMessage = "";
	/**
	 * ������������ �� ���� � ������������� �����������.
	 */
	private boolean windowShowed = false;

	/**
	 * ������ �����������.
	 */
	private RegistrationStatus registrationStatus;

	/**
	 * ��� ������ �����������.
	 */
	private RegistrationMode registrationMode;

	private SelfRegistrationHelper()
	{
		final AuthenticationContext context = AuthenticationContext.getContext();
		registrationStatus = context.getRegistrationStatus();

		try
		{
			LightPerson person = context.isAuthGuest() ? null : personService.getLightPersonByLogin(context.getLogin().getId());
			if (person == null || person.getUserRegistrationMode() == null || person.getUserRegistrationMode() == UserRegistrationMode.DEFAULT)
			{
				registrationMode = ConfigFactory.getConfig(SelfRegistrationConfig.class).getRegistrationMode();
			}
			else
			{
				registrationMode = RegistrationMode.valueOf(person.getUserRegistrationMode().toString());
			}
		}
		catch (BusinessException ignore)
		{
			registrationMode = ConfigFactory.getConfig(SelfRegistrationConfig.class).getRegistrationMode();
		}

		if (context.getLoginType() != LoginType.TERMINAL && context.getLoginType() != LoginType.DISPOSABLE)
			return;

		hardRegistrationMode = registrationMode == RegistrationMode.HARD || (LoginType.DISPOSABLE.equals(AuthenticationContext.getContext().getLoginType()))
		                                           || (context.getLoginType() == LoginType.TERMINAL && ConfigFactory.getConfig(SelfRegistrationConfig.class).isBanIPasLogin());
		needShowPreregistrationMessage = registrationStatus != RegistrationStatus.OFF && registrationMode != RegistrationMode.OFF;

		SelfRegistrationConfig selfRegistrationConfig = ConfigFactory.getConfig(SelfRegistrationConfig.class);
		if ((LoginType.DISPOSABLE.equals(AuthenticationContext.getContext().getLoginType())))
		{
			registrationMode = RegistrationMode.HARD;
			needShowPreregistrationMessage = true;
			preregistrationMessage = selfRegistrationConfig.getDisposableModeMessage();
		}
		else if (!needShowPreregistrationMessage)
		{
			return;
		}
		else if (registrationStatus == RegistrationStatus.NOT_EXIST)
		{
			if (hardRegistrationMode)
				preregistrationMessage = selfRegistrationConfig.getHardModeNotExistsMessage();
			else
				preregistrationMessage = selfRegistrationConfig.getSoftModeNotExistsMessage();
		}
		else if (registrationStatus == RegistrationStatus.EXIST)
		{
			if (hardRegistrationMode)
				preregistrationMessage = selfRegistrationConfig.getHardModeExistsMessage();
			else
				preregistrationMessage = selfRegistrationConfig.getSoftModeExistsMessage();
		}
	}

	/**
	 * ������� ��� ��������������� �� ������ ��������� ���������������.
	 *
	 * @return ��������� ���������������.
	 */
	public static SelfRegistrationHelper getIt()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalArgumentException("��� ������.");

		Object obj = store.restore(STORE_KEY);

		if (obj == null)
		{
			SelfRegistrationHelper helper = new SelfRegistrationHelper();
			store.save(STORE_KEY, helper);
			return helper;
		}

		return (SelfRegistrationHelper) obj;
	}

	/**
	 * @return ������� �� ����� ������� �����������.
	 */
	public boolean getHardRegistrationMode()
	{
		return hardRegistrationMode;
	}

	/**
	 * �������� ���������� ����������� ����������� ���� � ������������ � �����������
	 * @return true - ���������� ����������� ����������� ���� �� ��������� ������������� �����
	 */
	public boolean checkRegistrationWindowShowCount()
	{
		SelfRegistrationConfig selfRegistrationConfig = ConfigFactory.getConfig(SelfRegistrationConfig.class);
		if (registrationMode == RegistrationMode.SOFT && needShowPreregistrationMessage)
		{
			try
			{
				profile = PersonContext.getPersonDataProvider().getPersonData().getProfile();
			}
			catch(BusinessException e)
			{
				log.error(e.getMessage(), e);
				return true;
			}
			Integer registrationWindowShowCount = profile.getRegistrationWindowShowCount();
			if (registrationWindowShowCount == null)
				registrationWindowShowCount = 0;

			if (ConfigFactory.getConfig(SelfRegistrationConfig.class).isNewSelfRegistrationDesign() && registrationWindowShowCount > 0)
			{
				return false;
			}
			else if (registrationWindowShowCount >= selfRegistrationConfig.getRegistrationWindowShowCount())
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * @return ���������� �� ���������� ������������ �������������� ���������.
	 */
	public boolean getNeedShowPreRegistrationMessage()
	{
		return needShowPreregistrationMessage && !windowShowed;
	}

	/**
	 * @return �������������� ��������� �������.
	 */
	public String getPreregistrationMessage()
	{
		return preregistrationMessage;
	}

	/**
	 * @return ���������� �� ���������� �������� ����������� � ������������� ����� (������� �����������).
	 */
	public boolean getNeedHardRegistrationPage()
	{
		return hardRegistrationMode && registrationStatus == RegistrationStatus.EXIST;
	}

	/**
	 * @param windowShowed ������������ �� ���� �����������.
	 */
	public void setWindowShowed(boolean windowShowed)
	{
		this.windowShowed = windowShowed;
	}

	/**
	 * @return ������ �����������.
	 */
	public RegistrationStatus getRegistrationStatus()
	{
		return registrationStatus;
	}

	/**
	 * @return ����� �����������.
	 */
	public RegistrationMode getRegistrationMode()
	{
		return registrationMode;
	}

	/**
	 * @param registrationMode ����� �����������.
	 */
	public void setRegistrationMode(RegistrationMode registrationMode)
	{
		this.registrationMode = registrationMode;
	}

	/**
	 * @return ����� �� �������� �����
	 */
	public boolean needShowCaptcha()
	{
		if (!PersonContext.isAvailable())
			return false;

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Long checkLoginCount = getCheckLoginCount(person);

		return checkLoginCount >= getMaximinCheckLoginCount();
	}

	/**
	 * ��������� ����� ������� ���� ������������ � ����� �������
	 */
	public void incRegistrationShowedCount()
	{
		if (!PersonContext.isAvailable())
			return;
		try
		{
			if (registrationMode == RegistrationMode.SOFT && needShowPreregistrationMessage)
			{
				profile = PersonContext.getPersonDataProvider().getPersonData().getProfile();

				Integer registrationWindowShowCount = profile.getRegistrationWindowShowCount();
				if (registrationWindowShowCount == null)
					registrationWindowShowCount = 0;

				registrationWindowShowCount++;
				profile.setRegistrationWindowShowCount(registrationWindowShowCount);
				profileService.update(profile);
			}
		}
		catch(BusinessException e)
		{
			log.error(e.getMessage(), e);
			return;
		}
	}

	/**
	 * ��������� ����� ��������� �������� ���������� ������
	 */
	public void incCheckLoginCount()
	{
		if (!PersonContext.isAvailable())
			return;

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Long checkLoginCount = getCheckLoginCount(person);
		checkLoginCount++;
		person.setCheckLoginCount(checkLoginCount);
		person.setLastFailureLoginCheck(Calendar.getInstance());
		try
		{
			simpleService.update(person);
		}
		catch (BusinessException e)
		{
			log.error("������ �� ����� ���������� ������ �������", e);
		}
	}

	/**
	 * @param loginForTest ����� ��� ��������.
	 * @return ���������� �� �������� ����� � ���� ��� ��� ���.
	 */
	public boolean checkLogin(String loginForTest)
	{
		if (!PersonContext.isAvailable())
			return false;

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Long checkLoginCount = getCheckLoginCount(person);

		try
		{
			if (checkLoginCount >= getMaximinCheckLoginCount())
				return false;

			checkLoginCount++;
			person.setCheckLoginCount(checkLoginCount);

			ValidateLoginInfo validateLoginInfo = new ValidateLoginInfo(AuthenticationContext.getContext().getCSA_SID(), loginForTest, true);
			CSABackRequestHelper.sendValidateLoginRq(validateLoginInfo);
		}
		catch (BackException e)
		{
			log.error("������ �� ����� �������� ������", e);
			return false;
		}
		catch (LoginAlreadyRegisteredException e)
		{
			person.setLastFailureLoginCheck(Calendar.getInstance());
			return true;
		}
		catch (BackLogicException e)
		{
			log.error("������ �� ����� �������� ������", e);
			return false;
		}
		finally
		{
			try
			{
				simpleService.update(person);
			}
			catch (BusinessException e)
			{
				log.error("������ �� ����� ���������� ������ �������", e);
			}
		}
		return false;
	}

	private Long getCheckLoginCount(ActivePerson person)
	{
		Long checkLoginCount = person.getCheckLoginCount();
		Calendar calendar = Calendar.getInstance();
		Calendar lastAttempt = person.getLastFailureLoginCheck();
		if (lastAttempt == null || checkLoginCount == null)
		{
			lastAttempt = Calendar.getInstance();
			lastAttempt.setTimeInMillis(0L);
		}
		if (DateHelper.diff(calendar, lastAttempt) > getMinuteToResetCaptchaAtRegistration() * DateHelper.MILLISECONDS_IN_MINUTE)
			checkLoginCount = 0L;
		return checkLoginCount;
	}

	/**
	 * @return ���������: ������������ ����� ������� �������� ������.
	 */
	public static int getMaximinCheckLoginCount()
	{
		return ConfigFactory.getConfig(SecurityConfig.class).getNumberOfLoginAttemptsAtRegistration();
	}

	/**
	 * @return ���������: ����� ����� ����� ������� ���������� ���� �����.
	 */
	public static int getMinuteToResetCaptchaAtRegistration()
	{
		return ConfigFactory.getConfig(SecurityConfig.class).getMinuteToResetCaptchaAtRegistration();
	}
}
