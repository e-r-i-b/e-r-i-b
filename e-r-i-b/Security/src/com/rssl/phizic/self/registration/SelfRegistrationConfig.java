package com.rssl.phizic.self.registration;

import com.rssl.phizic.auth.modes.RegistrationMode;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * Конфиг общих настроек для самостоятельной регистрации
 * @author basharin
 * @ created 29.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class SelfRegistrationConfig extends Config
{
	public static final String REGISTRATION_MODE_KEY         = "com.rssl.iccs.registration.mode";
	public static final String BAN_IPAS_LOGIN_KEY            = "com.rssl.iccs.ban.ipas.login";
	public static final String SELF_REGISTRATION_HINT_DELAY_KEY   = "com.rssl.iccs.self.registration.hint.delay";
	public static final String SELF_REGISTRATION_DESIGN_KEY  = "com.rssl.iccs.self.registration.new.design";
	public static final String SHOW_LOGIN_SELF_REGISTRATION_SCREEN_KEY = "com.rssl.iccs.self.registration.show.login.self.registration.screen";
	public static final String SELF_DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME = "com.rssl.iccs.registration.user.deny-multiple";
	public static final String SELF_REGISTRATION_SOFT_MODE_NOT_EXIST_MESSAGE_KEY = "com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_NOT_EXIST.mode";
	public static final String SELF_REGISTRATION_HARD_MODE_NOT_EXIST_MESSAGE_KEY = "com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_NOT_EXIST.mode";
    public static final String SELF_REGISTRATION_SOFT_MODE_EXIST_MESSAGE_KEY = "com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_EXIST.mode";
	public static final String SELF_REGISTRATION_HARD_MODE_EXIST_MESSAGE_KEY = "com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_EXIST.mode";
	public static final String SELF_REGISTRATION_DISPOSABLE_MODE_MESSAGE_KEY = "com.rssl.phizic.web.client.SelfRegistrationHelper.DISPOSABLE.mode";
	public static final String SELF_REGISTRATION_WINDOW_TITLE = "com.rssl.phizic.web.client.SelfRegistrationWindow.title";
    public static final String SELF_REGISTRATION_FORM_MESSAGE = "com.rssl.phizic.web.client.SelfRegistrationForm.message";
	public static final String REGISTRATION_WINDOW_SHOW_COUNT = "com.rssl.iccs.registration.window.show.count";
	public static final String MULTIPLE_REGISTRATION_PART_VISIBLE = "com.rssl.iccs.multiple.registration.part.visible";

	private String registrationMode;
	private String softModeNotExistsMessage;
	private boolean newSelfRegistrationDesign;
	private boolean denyMultipleRegistration;
	private String hardModeNotExistsMessage;
	private String softModeExistsMessage;
	private String disposableModeMessage;
	private String hardModeExistsMessage;
	private String windowTitleMessage;
	private String formMessage;
	private Integer registrationWindowShowCount;
	private boolean banIPasLogin;
	private int hintDelay;
	private boolean visibleMultipleRegistrationPart;
	private boolean showLoginSelfRegistrationScreen;

	public SelfRegistrationConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		registrationMode = getProperty(REGISTRATION_MODE_KEY);
		softModeNotExistsMessage = getProperty(SELF_REGISTRATION_SOFT_MODE_NOT_EXIST_MESSAGE_KEY);
		hardModeNotExistsMessage = getProperty(SELF_REGISTRATION_HARD_MODE_NOT_EXIST_MESSAGE_KEY);
	    softModeExistsMessage = getProperty(SELF_REGISTRATION_SOFT_MODE_EXIST_MESSAGE_KEY);
		hardModeExistsMessage = getProperty(SELF_REGISTRATION_HARD_MODE_EXIST_MESSAGE_KEY);
		disposableModeMessage = getProperty(SELF_REGISTRATION_DISPOSABLE_MODE_MESSAGE_KEY);
		windowTitleMessage = getProperty(SELF_REGISTRATION_WINDOW_TITLE);
		formMessage = getProperty(SELF_REGISTRATION_FORM_MESSAGE);
		registrationWindowShowCount = getIntProperty(REGISTRATION_WINDOW_SHOW_COUNT);
		newSelfRegistrationDesign = getBoolProperty(SELF_REGISTRATION_DESIGN_KEY);
		denyMultipleRegistration = getBoolProperty(SELF_DENY_MULTIPLE_REGISTRATION_PROPERTY_NAME);
		banIPasLogin = getBoolProperty(BAN_IPAS_LOGIN_KEY);
		hintDelay = getIntProperty(SELF_REGISTRATION_HINT_DELAY_KEY);
		visibleMultipleRegistrationPart = getBoolProperty(MULTIPLE_REGISTRATION_PART_VISIBLE);
		showLoginSelfRegistrationScreen = getBoolProperty(SHOW_LOGIN_SELF_REGISTRATION_SCREEN_KEY);
	}

	/**
	 * @return режим регистрации.
	 */
	public RegistrationMode getRegistrationMode()
	{
		return RegistrationMode.valueOf(registrationMode);
	}

	/**
	 * @return использовать ли новый дизайн самостоятельной регистрации.
	 */
	public boolean isNewSelfRegistrationDesign()
	{
		return newSelfRegistrationDesign;
	}

	/**
	 * @return Запрет входа с логином/алиасом iPAS
	 */
	public boolean isBanIPasLogin()
	{
		return banIPasLogin;
	}

	/**
	 * @return текст сообщения всплывающего окна в мягком режиме при самостоятельной регистрации при отсутствии профиля в новой CSA
	 */
	public String getSoftModeNotExistsMessage()
	{
		return softModeNotExistsMessage;
	}

	/**
	 * @return текст сообщения всплывающего окна в жестком режиме при самостоятельной регистрации при отсутствии профиля в новой CSA
	 */
	public String getHardModeNotExistsMessage()
	{
		return hardModeNotExistsMessage;
	}

	/**
	 * @return текст сообщения всплывающего окна в мягком режиме при самостоятельной регистрации при наличии профиля в новой CSA
	 */
	public String getSoftModeExistsMessage()
	{
		return softModeExistsMessage;
	}

	/**
	 * @return запрещена ли повторная регистрация.
	 */
	public boolean isDenyMultipleRegistration()
	{
		return denyMultipleRegistration;
	}

	/**
	 * @return текст сообщения всплывающего окна в жестком режиме при самостоятельной регистрации при наличии профиля в новой CSA
	 */
	public String getHardModeExistsMessage()
	{
		return hardModeExistsMessage;
	}

	/**
	 * @return текст сообщения всплывающего окна в случае регистрации по одноразовому логину
	 */
	public String getDisposableModeMessage()
	{
		return disposableModeMessage;
	}

	/**
	 * @return заголовок сообщения клиенту
	 */
	public String getWindowTitleMessage()
	{
		return windowTitleMessage;
	}

	/**
	 * @return сообщение клиенту на форме регистрации
	 */
	public String getFormMessage()
	{
		return formMessage;
	}

	/**
	 * @return количество отображений окна с предложением о регистрации
	 */
	public Integer getRegistrationWindowShowCount()
	{
		return registrationWindowShowCount;
	}

	/**
	 * @return задержка отображения подсказки
	 */
	public int getHintDelay()
	{
		return hintDelay;
	}

	/**
	 * @return Отображать ли текст «Либо зарегистрируйтесь»
	 */
	public boolean isVisibleMultipleRegistrationPart()
	{
		return visibleMultipleRegistrationPart;
	}

	/**
	 * @return показывать страницу, предлагающую войти по собственному(CSA) логину и паролю, после входа под iPas логином.
	 */
	public boolean isShowLoginSelfRegistrationScreen()
	{
		return showLoginSelfRegistrationScreen;
	}
}