package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.skins.SkinConfig;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.context.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.StrutsUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gololobov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SkinHelper
{
	/**
	 * Системное имя группы "Молодёжь"
	 */
	public static final String YOUNG_PEOPLE_GROUP = "young_people";
	public static final String SKIN_ATTR_NAME = "skinUrl";
	private static final String SKIN_MOBILE_NAME = "mobile";

	private static final SkinsService skinsService = new SkinsService();

	private static final GroupService groupService = new GroupService();

	private static final ProfileService profileService = new ProfileService();

	/**
	 * Глобальный скин текущего пользователя
	 * @return глобальный Skin URL для текущего пользователя
	 * @throws BusinessException
	 */
	public static String getGlobalSkinUrl() throws BusinessException
	{
		return updateSkinPath(getGlobalUrl());
	}

	protected static String getGlobalUrl() throws BusinessException
	{
		String globalUrl = null;

		if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			globalUrl = (personData != null) ? personData.getGlobalUrl() : null;
		}
		else if (EmployeeContext.isAvailable())
		{
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
			globalUrl = (employeeData != null) ? employeeData.getGlobalUrl() : null;
		}

		if (StringHelper.isEmpty(globalUrl))
		{
			Skin globalSkin = ConfigFactory.getConfig(SkinConfig.class).getGlobalSkin();
			if (globalSkin == null)
				throw new BusinessException("Не задан global skin");
			globalUrl = globalSkin.getUrl();
		}

		return globalUrl;
	}

	/**
	 * Скин текущего пользователя (индивидуальный или глобальный)
	 * @return Skin URL для текущего пользователя
	 */
	public static String getSkinUrl() throws BusinessException
	{
		// 1. Если приложение - мобильное или социальное, то используем определенный скин
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getApplicationInfo().isMobileApi() || applicationConfig.getApplicationInfo().isSocialApi())
			return updateSkinPath(getMobileSkinUrl());
		// 2. Используем скин, определённый раньше
		String contextSkinUrl = getUserContextSkinUrl();
		if (!StringHelper.isEmpty(contextSkinUrl))
			return updateSkinPath(contextSkinUrl);
		
		// 3. Определяем скин
		AuthenticationContext authContext = AuthenticationContext.getContext();
		String personalSkinUrl = getProfileSkinUrl(authContext);
		String authSkinUrl = getAuthenticationSkinUrl(authContext);
		String defaultSkinUrl = getDefaultSkinUrl(authContext);

		// Либо персональный, либо указан на входе, либо по-умолчанию
		String skinUrl = personalSkinUrl;
		if (StringHelper.isEmpty(skinUrl))
			skinUrl = authSkinUrl;
		if (StringHelper.isEmpty(skinUrl))
			skinUrl = defaultSkinUrl;

		// 4. Если скин поменялся (отличается от стандартного), пишем сообщение об изменении скина
		if (!skinUrl.equals(defaultSkinUrl))
			authContext.putMessage(buildSkinChangedMessage());

		// 5. Запоминаем скин в контексте
		setUserContextSkinUrl(skinUrl);

		return updateSkinPath(skinUrl);
	}

	/**
	 * Запоминает скин текущего пользователя
	 * @param skinUrl - URL скина
	 */
	public static void setSkinUrl(String skinUrl)
	{
		setUserContextSkinUrl(skinUrl);
	}

	/**
	 * @return URL скина в контексте пользователя, если есть
	 */
	private static String getUserContextSkinUrl()
	{
		if (EmployeeContext.isAvailable())
		{
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
			if(employeeData != null)
				return employeeData.getSkinUrl();
		}

		else if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if(personData != null)
				return personData.getSkinUrl();
		}

		//если о пользователе нет информации, берем данные из сессии
		HttpServletRequest request = WebContext.getCurrentRequest();
		return HttpSessionUtils.getSessionAttribute(request, SKIN_ATTR_NAME);
	}

	/**
	 * Запоминает в контексте указанный скин
	 * @param skinUrl - URL скина
	 */
	private static void setUserContextSkinUrl(String skinUrl)
	{
		boolean infoAvailable = false;
		if (EmployeeContext.isAvailable())
		{
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
			infoAvailable = employeeData != null;
			if (infoAvailable)
				employeeData.setSkinUrl(skinUrl);
		}
		else if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			infoAvailable = personData != null;
			if (infoAvailable)
				personData.setSkinUrl(skinUrl);
		}

		HttpServletRequest request = WebContext.getCurrentRequest();
		if(infoAvailable) //если о клиенте есть информация, удаляем данные о скине из сессии
			HttpSessionUtils.removeSessionAttribute(request, SKIN_ATTR_NAME);
		else  //если нет - сохраняем данные о скине в сессию
			request.getSession().setAttribute(SKIN_ATTR_NAME,skinUrl);
	}

	private static String getProfileSkinUrl(AuthenticationContext authContext) throws BusinessException
	{
		if (authContext == null)
			return null;

		CommonLogin login = authContext.getLogin();
		if (login == null)
			return null;

		// Логин д.б. клиентский, т.к. сейчас профиль работает только для клиентов
		if (!(login instanceof Login))
			return null;

		if (login instanceof GuestLogin)
			return null;

		Profile profile = profileService.findByLogin(login);
		if (profile == null)
			return null;

		Skin skin = profile.getSkin();
		if (skin == null)
			return null;

		return skin.getUrl();
	}

	private static String getAuthenticationSkinUrl(AuthenticationContext authContext) throws BusinessException
	{
		if (authContext == null)
			return null;

		if (authContext.isCameFromYoungPeopleWebsite())
		{
			Group group = groupService.getGroupBySystemName(YOUNG_PEOPLE_GROUP);
			if (group == null)
				return null;

			Skin skin = group.getSkin();
			if (skin == null)
				return null;

			return skin.getUrl();
		}

		return null;
	}

	/**
	 * Возвращает скин по-умолчанию
	 * Т.е. либо персональный, либо скин группы, либо скин приложения
	 * @param authContext - контекст аутентификации (can be null)
	 * @return URL скина (never null)
	 */
	private static String getDefaultSkinUrl(AuthenticationContext authContext) throws BusinessException
	{
		Skin skin = null;
		// A. Используем стандартный скин для сотрудника
		if (EmployeeContext.isAvailable())
			skin = skinsService.getActiveSkin();
		// B. Используем стандартный скин для клиента
		else if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			ActivePerson person = (personData != null) ? personData.getPerson() : null;
			CommonLogin login = (person != null) ? person.getLogin() : null;
			if (!authContext.isAuthGuest())
				skin = (login != null) ? skinsService.getPersonActiveSkin(login.getId()) : null;
		}

		// C. Контекст пользователя не определён
		// => используем скин для логина из контекста аутентификации если это не гость
		if (skin == null && (authContext != null && !authContext.isAuthGuest()))
		{
			CommonLogin login = authContext.getLogin();
			skin = (login != null) ? skinsService.getPersonActiveSkin(login.getId()) : null;
		}

		// D. Не опредлён ни один контекст
		// => используем стандартный скин приложения
		if (skin == null)
		{
			skin = skinsService.getActiveSkin();
			if (skin == null)
				throw new BusinessException("Для приложения не задан скин по-умолчанию");
		}

		// Здесь skin != null
		return skin.getUrl();
	}

	/**
	 * @return Сообщение пользователю о смене скина
	 */
	public static String buildSkinChangedMessage() throws BusinessException
	{
		String url = "";
		HttpServletRequest request = WebContext.getCurrentRequest();
		if (request != null)
			url = request.getContextPath() + "/private/favourite/list.do";
		return StrutsUtils.getMessage("message.young.people.welcome.message", "commonBundle", url);
	}

	/**
	 * если в настройке app.version(buildIKFL) указанно имя версии,
	 * то добавляем это имя в конец строки
	 * @return
	 */
	public static String updateSkinPath(String skinUrl)
	{
		BuildContextConfig buildContextConfig = ConfigFactory.getConfig(BuildContextConfig.class);
		String appVersion = buildContextConfig.getResourceAdditionalPath();
		if (!StringHelper.isEmpty(appVersion))
			return   skinUrl + '/' + appVersion;
		else
			return skinUrl;
	}

	/**
	 * Проверка на то, что скин для мобильного апи
	 * @param skinName - название скина
	 * @return - true - мобильный, false - нет
	 */
	public static boolean skinIsMobile(String skinName)
	{
		return SKIN_MOBILE_NAME.equals(skinName);
	}

	private static String getMobileSkinUrl() throws BusinessException
	{
		Skin skin = skinsService.findBySystemName(SKIN_MOBILE_NAME);
		if (skin == null)
			throw new BusinessException("Не задан скин для мобильного API");
		if (StringHelper.isEmpty(skin.getUrl()))
			throw new BusinessException("Не задан url скина для приложения");
		return skin.getUrl();
	}
}
