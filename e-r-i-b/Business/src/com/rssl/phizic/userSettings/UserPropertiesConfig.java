package com.rssl.phizic.userSettings;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.locale.entities.MultiLanguageApplications;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Конфиг для работы с пользовательскими настройками.
 *
 * @author bogdanov
 * @ created 10.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserPropertiesConfig extends Config
{
	private static final String IS_SHOW_WIDGET_KEY = "com.rssl.phizic.userSettings.showWidget";
	private static final String IS_BANK_OFFER_VIEWED_KEY = "com.rssl.phizic.userSettings.bankOfferViewed";
	private static final String HINTS_READ_KEY = "com.rssl.phizic.userSettings.hintsRead";
	private static final String USE_INTERNET_SECURITY_KEY = "com.rssl.phizic.userSettings.useInternetSecurity";
	private static final String USE_OFERT_KEY = "com.rssl.phizic.userSettings.useOfert";
	private static final String FIRST_USER_ENTER_KEY = "com.rssl.phizic.userSettings.firstUserEnter";
	private static final String MOBILE_ITEMS_MOVED_CLOSED_KEY = "com.rssl.phizic.userSettings.mobileItemsMovedClosed";
	private static final String SHOW_CONTACTS_MESSAGE_KEY = "com.rssl.phizic.userSettings.showContactsMessage";
	private static final String FIRST_CONTACTS_SYNCHRONIZATION_TIME_KEY = "com.rssl.phizic.userSettings.firstContactSynchronizationTime";
	private static final String P2P_PROMO_SHOWN_TIMES = "com.rssl.phizic.userSettings.p2pPromoShownTimes";
	private static final String P2P_NEW_MARK_SHOWN_TIMES = "com.rssl.phizic.userSettings.p2pNewMrkShownTimes";
	private static final String PARAM_ERIB_LOCALE_ID = "com.rssl.phizic.userSettings.ERIBLocaleId";
	private static final String USED_ALF_BUDGETS = "com.rssl.phizic.userSettings.usedALFBudgets";
	private static final String INCOGNITO_ACTUALIZED = "com.rssl.phizic.userSettings.incognitoActual";
	private static final String ADDRESS_BOOK_LAST_ACTUALIZE_TIME = "com.rssl.phizic.userSettings.addressbook.lastactualizetime";
	private static final String ADDRESS_BOOK_YANDEX_CONTACT_NUM = "com.rssl.phizic.userSettings.addressbook.nomOfYandexContacts";
	private static final String FIRST_AVATAR_SHOW = "com.rssl.phizic.userSettings.addressbook.firstAvatarShow";
	private static final String DATE_CONNECTION_UDBO_KEY = "com.rssl.phizic.userSettings.dateConnectionUDBO";
	private static final String FIRST_USER_ENTER_16_RELEASE_KEY = "com.rssl.phizic.userSettings.firstUserEnter16Release";
	private static final String SHOW_MORE_SBOL_NOVELTY_KEY = "com.rssl.phizic.userSettings.showMoreSbolNovelty";
	private static final String COLD_PERIOD_MESSAGE_WAS_SHOW_KEY = "com.rssl.phizic.userSettings.messageColdPeriodWasShow";
	private static final String CONNECT_UDBO_MESSAGE_WAS_SHOW_KEY = "com.rssl.phizic.userSettings.messageConnectUDBOWasShow";
	private static final String RSA_CONNECTION_TIMEOUT = "com.rssl.phizic.rsa.connection.timeout";

	private UserPropertyReader innerReader;

	public UserPropertiesConfig(PropertyReader reader)
	{
		super(reader);
		innerReader = (UserPropertyReader) reader;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}

	/**
	 * Обработка пользовательских настроек не из контекста клиента.
	 * @param loginId логин пользователя.
	 * @param settingProcessor обработчик настроек.
	 */
	public static <T> T processUserSettingsWithoutPersonContext(long loginId, SettingsProcessor<T> settingProcessor) throws BusinessException
	{
		UserPropertiesConfig userPropertiesConfig = ConfigFactory.getConfig(UserPropertiesConfig.class);
		userPropertiesConfig.innerReader.workWithUser(loginId);
		try
		{
			return settingProcessor.onExecute(userPropertiesConfig);
		}
		finally
		{
			userPropertiesConfig.innerReader.finishWorkWithUser();
		}
	}

	/**
	 * Обработка пользовательских настроек не из контекста клиента.
	 * @param login логин пользователя.
	 * @param settingProcessor обработчик настроек.
	 */
	public static <T> T processUserSettingsWithoutPersonContext(CommonLogin login, SettingsProcessor<T> settingProcessor) throws BusinessException
	{
		UserPropertiesConfig userPropertiesConfig = ConfigFactory.getConfig(UserPropertiesConfig.class);
		userPropertiesConfig.innerReader.workWithUser(login.getId());
		try
		{
			return settingProcessor.onExecute(userPropertiesConfig);
		}
		finally
		{
			userPropertiesConfig.innerReader.finishWorkWithUser();
		}
	}

	/**
	 * @return необходимо ли показывать виджеты
	 */
	public boolean isShowWidget()
	{
		return getBoolProperty(IS_SHOW_WIDGET_KEY);
	}

	/**
	 * @param showWidget необходимо ли показывать виджеты
	 */
	public void setShowWidget(boolean showWidget)
	{
		setProperty(IS_SHOW_WIDGET_KEY, Boolean.toString(showWidget));
	}

	/**
	 * Признак отображения блока "Предложения банка" на главной странице
	 * @return true - отображается, false - не отображается
	 */
	public Boolean isBankOfferViewed()
	{
		return getBoolProperty(IS_BANK_OFFER_VIEWED_KEY);
	}

	/**
	 * @return Признак прочитаны ли подсказки в бюджетировании
	 */
	public boolean isHintsRead()
	{
		return getBoolProperty(HINTS_READ_KEY);
	}

	/**
	 * @param hintsRead Признак прочитаны ли подсказки в бюджетировании
	 */
	public void setHintsRead(boolean hintsRead)
	{
		setProperty(HINTS_READ_KEY, Boolean .toString(hintsRead));
	}

	/**
	 * @return возвращает признак согласия с условиями Интернет безопасности
	 */
	public Boolean getUseInternetSecurity()
	{
		return getBoolProperty(USE_INTERNET_SECURITY_KEY);
	}

	/**
	 * @param useInternetSecurity возвращает признак согласия с условиями Интернет безопасности
	 */
	public void setUseInternetSecurity(Boolean useInternetSecurity)
	{
		setProperty(USE_INTERNET_SECURITY_KEY, Boolean.toString(useInternetSecurity));
	}

	/**
	 * @return возвращает признак использования оферты (согласен клиент с офертой или нет, соответственно)
	 */
	public Boolean getUseOfert()
	{
		return getBoolProperty(USE_OFERT_KEY);
	}

	/**
	 * @param useOfert возвращает признак использования оферты (согласен клиент с офертой или нет, соответственно)
	 */
	public void setUseOfert(Boolean useOfert)
	{
		setProperty(USE_OFERT_KEY, Boolean.toString(useOfert));
	}

	private void setProperty(String key, String value)
	{
		if (StringHelper.isEmpty(value))
			innerReader.removeProperty(key);
		else
			innerReader.setProperty(key, value);
	}

	private Calendar getDateProperty(String key)
	{
		String property = getProperty(key);
		if(StringHelper.isEmpty(property))
			return null;
		Calendar dateProperty = Calendar.getInstance();
		dateProperty.setTimeInMillis(Long.valueOf(property));
		return dateProperty;
	}

	private void setDateProperty(String key, Calendar value)
	{
		setProperty(key, String.valueOf(DateHelper.toDate(value).getTime()));
	}

	/**
	 * @return Признак первый вход в профиль
	 */
	public boolean isFirstUserEnter()
	{
		return getBoolProperty(FIRST_USER_ENTER_KEY);
	}

	/**
	 * @param firstUserEnter Признак первый вход в профиль
	 */
	public void setFirstUserEnter(boolean firstUserEnter)
	{
		setProperty(FIRST_USER_ENTER_KEY, Boolean .toString(firstUserEnter));
	}

	/**
	 * @return Признак показа сообщения о переезде мобильного банка и мобильных приложений
	 */
	public boolean isMobileItemsMovedClosed()
	{
		return getBoolProperty(MOBILE_ITEMS_MOVED_CLOSED_KEY);
	}

	/**
	 * @param firstUserEnter Признак показа сообщения о переезде мобильного банка и мобильных приложений
	 */
	public void setMobileItemsMovedClosed(boolean firstUserEnter)
	{
		setProperty(MOBILE_ITEMS_MOVED_CLOSED_KEY, Boolean .toString(firstUserEnter));
	}

	/**
	 * @return отображалось ли клиенту информирующее сообщение об услуге адресной книги (true - отображалось)
	 */
	public boolean isShowContactsMessage()
	{
		return getBoolProperty(SHOW_CONTACTS_MESSAGE_KEY);
	}

	/**
	 * @param showContactsMessage отображалось ли клиенту информирующее сообщение об услуге адресной книги (true - отображалось)
	 */
	public void setShowContactsMessage(boolean showContactsMessage)
	{
		setProperty(SHOW_CONTACTS_MESSAGE_KEY, Boolean.toString(showContactsMessage));
	}

	/**
	 * @return дата первой синхронизации контактов клиента
	 */
	public Calendar getFirstContactSynchronizationDate()
	{
		return getDateProperty(FIRST_CONTACTS_SYNCHRONIZATION_TIME_KEY);
	}

	/**
	 * Установить параметр пользователя
	 * @param firstContactSynchronizationDate дата первой синхронизации контактов клиента
	 */
	public void setFirstContactSynchronizationDateIfEmpty(Calendar firstContactSynchronizationDate)
	{
		if(getDateProperty(FIRST_CONTACTS_SYNCHRONIZATION_TIME_KEY) == null)
			setDateProperty(FIRST_CONTACTS_SYNCHRONIZATION_TIME_KEY,firstContactSynchronizationDate);
	}

	/**
	 * @return Количество сессий, в разрезе которых отобразили пометку "Новинку" у автоплатежей
	 * в личном меню, в связи с появлением p2p
	 */
	public long getP2PNewMarkShownTimes()
	{
		return getLongProperty(P2P_NEW_MARK_SHOWN_TIMES);
	}

	/**
	 * Изменить количество прошедших сессий, в которых отображали пометку "Новинка" у автоплатежей
	 */
	public void setP2PNewMarkShownTimes(Long value)
	{
		setProperty(P2P_NEW_MARK_SHOWN_TIMES, value.toString());
	}

	/**
	 * Увеличить количество прошедших сессий, в которых отображали пометку "Новинка" у автоплатежей
	 */
	public void increaseP2PNewMarkShownTimes()
	{
		if (isNeedToShowP2PNewMark())
		{
			setP2PNewMarkShownTimes(getP2PNewMarkShownTimes() + 1);
		}
	}

	/**
	 * Нужно ли отображать пометку "Новинка" у автоплатежей?
	 */
	public boolean isNeedToShowP2PNewMark()
	{
		return PermissionUtil.impliesServiceRigid(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName())
			&& getP2PNewMarkShownTimes() <= ConfigFactory.getConfig(ClientConfig.class).getP2pShowNewMarkLimit();
	}
	/**
	 * @return количество сессий с начала отображения промо по p2p-автоплатежам
	 */
	public long getP2PPromoShownTimes()
	{
		return getLongProperty(P2P_PROMO_SHOWN_TIMES);
	}

	/**
	 * Изменение настройки по количество сессий с начала отображения промо по p2p-автоплатежам
	 * @param value - новое значение
	 */
	private void setP2PPromoShownTimes(Long value)
	{
		setProperty(P2P_PROMO_SHOWN_TIMES, value.toString());
	}

	/**
	 * Увеличение счётчика по p2p-автоплатежам
	 */
	public void increaseP2PPromoShownTimes()
	{
		Long currentValue = getP2PPromoShownTimes();
		if (currentValue <= ConfigFactory.getConfig(ClientConfig.class).getP2PPromoShowTimes())
		{
			//дабы не увеличивать счетчик бесконечно
			setP2PPromoShownTimes(++currentValue);
		}
	}

	/**
	 * @return - true, если нужно отображать промо по p2p-автоплатежам
	 */
	public boolean isNeedToShowP2PPromo()
	{
		return getP2PPromoShownTimes() <= ConfigFactory.getConfig(ClientConfig.class).getP2PPromoShowTimes();
	}

	/**
	 * @param app приложение
	 * @return локаль пользователя
	 */
	public String getERIBLocaleId(Application app)
	{
		return getProperty(PARAM_ERIB_LOCALE_ID + MultiLanguageApplications.fromApplication(app).name());
	}

	/**
	 * @param locale локаль пользователя
	 * @param app приложение
	 */
	public void setERIBLocaleId(String locale, Application app)
	{
		setProperty(PARAM_ERIB_LOCALE_ID + MultiLanguageApplications.fromApplication(app).name(), locale);
	}

	/**
	 * @return настраивал ли клиент бюджеты
	 */
	public boolean isUsedALFBudgets()
	{
		return getBoolProperty(USED_ALF_BUDGETS);
	}

	/**
	 * @param usedALFBudgets - настраивал ли клиент бюджеты
	 */
	public void setUsedALFBudgets(boolean usedALFBudgets)
	{
		setProperty(USED_ALF_BUDGETS, Boolean.toString(usedALFBudgets));
	}

	/**
	 * @param actual актаульный список инкогнито или нет.
	 */
	public void setIncognitoActual(boolean actual)
	{
		setProperty(INCOGNITO_ACTUALIZED, Boolean.toString(actual));
	}

	/**
	 * @return актуальный список телефонов для инкогнито или нет.
	 */
	public boolean isIncognitoActual()
	{
		return getBoolProperty(INCOGNITO_ACTUALIZED, false);
	}

	/**
	 * @return время последней актуализации адресной книги клиента.
	 */
	public long getLastAddressBookActualizeTime()
	{
		return getLongProperty(ADDRESS_BOOK_LAST_ACTUALIZE_TIME, -1L);
	}

	/**
	 * @param time время последней актуализации адресной книги клиента.
	 */
	public void setLastAddressBookActualizeTime(long time)
	{
		setProperty(ADDRESS_BOOK_LAST_ACTUALIZE_TIME, Long.toString(time));
	}

	/**
	 * @return количество контактов яндекс в адресной книге.
	 */
	public int getYandexContactCount()
	{
		return getIntProperty(ADDRESS_BOOK_YANDEX_CONTACT_NUM, 10);
	}

	/**
	 * @param count количество контактов яндекс в адресной книге.
	 */
	public void setYandexContactCount(int count)
	{
		setProperty(ADDRESS_BOOK_YANDEX_CONTACT_NUM, Integer.toString(count));
	}

	/**
	 * Аватар отображается впервые.
	 *
	 * @return аватар отображается впервые.
	 */
	public boolean isFirstAvatarShow()
	{
		return getBoolProperty(FIRST_AVATAR_SHOW, true);
	}

	/**
	 * @param firstAvatarShow первое отображение аватара.
	 */
	public void setFirstAvatarShow(boolean firstAvatarShow)
	{
		setProperty(FIRST_AVATAR_SHOW, Boolean.toString(firstAvatarShow));
	}

	/**
	 * @return дата удаленного подключения УДБО клиентом. Может быть null, если клиент не подключал сам удаленно УДБО
	 */
	public Calendar getDateConnectionUDBO()
	{
		return getDateProperty(DATE_CONNECTION_UDBO_KEY);
	}

	/**
	 * @param dateConnectionUDBO ата удаленного подключения УДБО клиентом
	 */
	public void setDateConnectionUDBO(Calendar dateConnectionUDBO)
	{
		setDateProperty(DATE_CONNECTION_UDBO_KEY, dateConnectionUDBO);
	}

	/**
	 * @return было ли показано сообщение о холодном периоде
	 */
	public boolean isColdPeriodMessageWasShow()
	{
		return getBoolProperty(COLD_PERIOD_MESSAGE_WAS_SHOW_KEY);
	}

	/**
	 * @param coldPeriodMessageWasShow было ли показано сообщение о холодном периоде
	 */
	public void setColdPeriodMessageWasShow(boolean coldPeriodMessageWasShow)
	{
		setProperty(COLD_PERIOD_MESSAGE_WAS_SHOW_KEY, Boolean.toString(coldPeriodMessageWasShow));
	}

	/**
	 * @return было ли показано сообщение о подключении УДБО
	 */
	public boolean isConnectUDBOMessageWasShow()
	{
		return getBoolProperty(CONNECT_UDBO_MESSAGE_WAS_SHOW_KEY);
	}

	/**
	 * @param connectUDBOMessageWasShow было ли показано сообщение о подключении УДБО
	 */
	public void setConnectUDBOMessageWasShow(boolean connectUDBOMessageWasShow)
	{
		setProperty(CONNECT_UDBO_MESSAGE_WAS_SHOW_KEY, Boolean.toString(connectUDBOMessageWasShow));
	}


	/**
	 * @return Признак первый вход в 16 релизе
	 */
	public boolean isFirstUserEnter16Release()
	{
		return getBoolProperty(FIRST_USER_ENTER_16_RELEASE_KEY);
	}

	/**
	 * @param firstUserEnter16Release Признак первый вход в 16 релизе
	 */
	public void setFirstUserEnter16Release(boolean firstUserEnter16Release)
	{
		setProperty(FIRST_USER_ENTER_16_RELEASE_KEY, Boolean .toString(firstUserEnter16Release));
	}

	/**
	 * @return Признак показа новинки для Больше сбербанк онлайн
	 */
	public boolean isShowMoreSbolNovelty()
	{
		return getBoolProperty(SHOW_MORE_SBOL_NOVELTY_KEY);
	}

	/**
	 * @param showMoreSbolNovelty Признак показа новинки для пункта Больше сбербанк онлайн
	 */
	public void setShowMoreSbolNovelty(boolean showMoreSbolNovelty)
	{
		setProperty(SHOW_MORE_SBOL_NOVELTY_KEY, Boolean .toString(showMoreSbolNovelty));
	}

	public long getTimeout()
	{
		return getLongProperty(RSA_CONNECTION_TIMEOUT);
	}

	public void setTimeout(long value)
	{
		setProperty(RSA_CONNECTION_TIMEOUT, String.valueOf(value));
	}
}
