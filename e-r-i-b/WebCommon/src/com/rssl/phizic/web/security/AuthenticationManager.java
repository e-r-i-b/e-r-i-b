package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.business.security.auth.StagePermissionProvider;
import com.rssl.phizic.business.timeoutsessionrequest.TimeoutSession;
import com.rssl.phizic.business.timeoutsessionrequest.TimeoutSessionService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.common.ContextFilter;
import com.rssl.phizic.web.log.SystemErrorLogDataReader;
import com.rssl.phizic.web.struts.HttpServletRequestUtils;
import com.rssl.phizic.web.util.DepartmentViewUtil;
import com.rssl.phizic.web.util.RequestHelper;

import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.common.types.security.Constants.AUTHENTICATION_STAGE_KEY;

/**
 * @author Evgrafov
 * @ created 14.12.2006
 * @ $Author: khudyakov $
 * @ $Revision: 85758 $
 */

public class AuthenticationManager
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	private static final SecurityService securityService = new SecurityService();
	private static final TimeoutSessionService timeoutSessionService = new TimeoutSessionService();
	private static final AccessPolicyService accessModeService = new AccessPolicyService();

	/**
	 * Получить текущую политику
	 * @return режим
	 */
	public static AccessPolicy getPolicy()
	{
		AuthenticationContext context = AuthenticationContext.getContext();
		if (context == null)
			return null;
		return context.getPolicy();
	}

	/**
	 * Стадия аутентификации
	 * @return текущая стадия
	 */
	public static Stage getStage()
	{
		return (Stage) StoreManager.getCurrentStore().restore(AUTHENTICATION_STAGE_KEY);
	}

	/**
	 * Начать процесс аутентификации
	 * @param request
	 * @param accessType тип доступа
	 * @param visitingMode режим работы пользователя
	 * @return выбранный режим
	 */
	public static AccessPolicy startAuthentication(HttpServletRequest request, AccessType accessType, UserVisitingMode visitingMode)
	{
		AccessPolicy policy = getAuthenticationConfig().getPolicy(accessType);
		return startAuthentication(request, policy, visitingMode);
	}

	/**
	 * Начать процесс аутентификации
	 * @param request
	 * @param policy политика доступа
	 * @param visitingMode режим работы пользователя
	 * @return policy
	 */
	public static AccessPolicy startAuthentication(HttpServletRequest request, AccessPolicy policy, UserVisitingMode visitingMode)
	{
		if (visitingMode == null)
			throw new NullPointerException("Аргумент 'visitingMode' не может быть null");

		Store store = StoreManager.getCurrentStore();
		store.clear();

		AuthenticationMode authMode = policy.getAuthenticationMode(visitingMode);
		if (authMode == null)
			throw new ConfigurationException("Не найден режим аутентификации для политики " + policy);

		AuthenticationContext context = new AuthenticationContext(policy);
		context.setVisitingMode(visitingMode);
		context.setAuthenticationParameters(getAuthenticationParameters(request));
		AuthenticationContext.setContext(context);

		Stage stage = authMode.getStages().get(0);
		store.save(AUTHENTICATION_STAGE_KEY, stage);

		createAuthModule(stage, context);

		return policy;
	}

	private static Map<String, String> getAuthenticationParameters(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnum = request.getParameterNames();
		while (paramEnum.hasMoreElements()) {
			String paramKey = (String) paramEnum.nextElement();
			String paramValue = request.getParameter(paramKey);
			if (!StringHelper.isEmpty(paramValue))
				map.put(paramKey, paramValue);
		}
		return map;
	}

	/**
	 */
	public static void abort()
	{
		Store store = StoreManager.getCurrentStore();

		store.remove(AUTHENTICATION_STAGE_KEY);
		AuthenticationContext.removeContext();
	}

	/**
	 * Преключает на следующую стадию авторизации
	 * @param request рапрос
	 * @return следующая стадия или null
	 */
	public static Stage nextStage(HttpServletRequest request) throws BusinessException, BusinessLogicException
	{

		Stage stage = getStage();

		if (stage == null)
			throw new RuntimeException("Текущая стадия отсутствует");

		AuthenticationContext authenticationContext = AuthenticationContext.getContext();

		if (!checkAccess(authenticationContext))
		{
			abort();
			throw new BusinessLogicException("Режим аутентификации запрещен");
		}

		Stage next;
		for (; ;)
		{
			next = stage.getNext();
			if (next == null)
				break;

			if (next.isRequired(authenticationContext))
				break;

			stage = next;
		}

		if (next == null)
		{
			completeAuthenticationWithTimeCalculate(request, authenticationContext);
		}
		else
		{
			createAuthModule(next, authenticationContext);
			StoreManager.getCurrentStore().save(AUTHENTICATION_STAGE_KEY, next);
		}

		return next;
	}

	private static void completeAuthenticationWithTimeCalculate(HttpServletRequest request, AuthenticationContext authenticationContext) throws BusinessException
	{
		LogDataReader reader = new DefaultLogDataReader("Вход клиента в систему");
		((DefaultLogDataReader)reader).setKey(getAuthentificationKey());
		((DefaultLogDataReader)reader).setOperationKey(com.rssl.phizic.security.config.Constants.USER_AUTENTICATION_KEY);
		Calendar start = GregorianCalendar.getInstance();

		try
		{
			completeAuthentication(request, authenticationContext);
		}

		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			reader = new SystemErrorLogDataReader(reader,e);
			throw new BusinessException(e);
		}
		finally
		{
			writeToLog(reader, start, Calendar.getInstance());
		}
	}

	private static String getAuthentificationKey()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		Application application = applicationConfig.getLoginContextApplication();
		switch (application)
		{
			case PhizIA:
				return com.rssl.phizic.logging.Constants.EMPLOEE_AUTENTIFICATION_KEY;
			case PhizIC:
				return com.rssl.phizic.logging.Constants.CLIENT_AUTENTIFICATION_KEY;
			case atm:
				return com.rssl.phizic.logging.Constants.CLIENT_ATM_AUTENTIFICATION_KEY;
			case socialApi:
				return com.rssl.phizic.logging.Constants.CLIENT_IOS_AUTENTIFICATION_KEY;
			default:
				if(applicationConfig.getApplicationInfo().isMobileApi())
					return com.rssl.phizic.logging.Constants.CLIENT_IOS_AUTENTIFICATION_KEY;
				throw new IllegalStateException("Недопустимое приложение " + application);
		}
	}

	/**
	 * Проверить доступ к текущей политике и если все Ок установить настойки
	 * @param authenticationContext контекст
	 * @return true = разрешен
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private static boolean checkAccess(AuthenticationContext authenticationContext) throws BusinessException, BusinessLogicException
	{
		if (authenticationContext.getPolicyProperties() != null)
			return true; // уже проверили

		CommonLogin login = authenticationContext.getLogin();

		if (login == null)
			return true; // еще логин не определен

		AccessPolicy currentPolicy = authenticationContext.getPolicy();
		Properties properties = getAccessTypeProperties(login, currentPolicy);

		authenticationContext.setPolicyProperties(properties);
		return (properties != null);
	}

	private static void createAuthModule(Stage stage, AuthenticationContext context)
	{
		CommonLogin login = context.getLogin();
		AccessPolicy policy = context.getPolicy();
		Properties props = context.getPolicyProperties();

		PrincipalImpl principal = login == null ? null : new PrincipalImpl(login, policy, props);
		SecurityUtil.createAuthModule(principal, new StagePermissionProvider(stage));
	}

	private static void completeAuthentication(HttpServletRequest request, AuthenticationContext authenticationContext) throws BusinessException
	{
		AccessPolicy policy = getPolicy();
		AccessType accessType = policy.getAccessType();
		CommonLogin login = authenticationContext.getLogin();

		updateLogonInfo(request, authenticationContext);

        UserPrincipal principal = new PrincipalImpl(login, policy, getAccessTypeProperties(login, policy));
        boolean isMobileLightScheme = MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00)
                && !accessType.equals(AccessType.mobileLimited) && authenticationContext.getMobileAppScheme() == MobileAppScheme.LIGHT;
        principal.setMobileLightScheme(isMobileLightScheme);
		principal.setColdPeriod(!accessType.equals(AccessType.guest) && RemoteConnectionUDBOHelper.isColdPeriod(login));

        SecurityUtil.createAuthModule(principal);
		SecurityUtil.completeAuthentication();

		StoreManager.getCurrentStore().remove(AUTHENTICATION_STAGE_KEY);
		ContextFilter.refreshContext(request);

		executeCompleteAction(request, authenticationContext, policy);
	}

	private static void executeCompleteAction(HttpServletRequest request, AuthenticationContext authenticationContext, AccessPolicy policy) throws BusinessException
	{
		AuthenticationMode authMode = policy.getAuthenticationMode(authenticationContext.getVisitingMode());
		AthenticationCompleteAction completeAction = authMode.getAuthenticationCompleteAction();

		try
		{
			completeAction.execute(authenticationContext);

			DefaultLogDataReader reader = new DefaultLogDataReader(LogWriter.AUTHENTICATION_FINISH);
			reader.setKey(com.rssl.phizic.logging.Constants.AUTHENTICATION_FINISH_KEY);
			reader.setOperationKey(com.rssl.phizic.security.config.Constants.USER_LOGON_OPERATION_KEY);
			writeToLog(reader,GregorianCalendar.getInstance(),GregorianCalendar.getInstance());
		}
		catch (SecurityException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityLogicException e)
		{
			authenticationContext.putMessage(e.getMessage());
		}
		finally
		{
			incrementSessionCounter(request);
		}
	}

	private static String getIdentificationParameter(AuthenticationContext authenticationContext, LoginType loginType)
	{
		if (loginType != null) {
			switch (loginType) {
				case ATM:
					return authenticationContext.getPAN();
				case CSA:
					return authenticationContext.getUserAlias();
				case TERMINAL:
				case MAPI:
                case SOCIAL:
					return authenticationContext.getCsaGuid();
			}
		}
		return "";
	}

	/**
	 * Подсчет кол-ва активных сессий
	 * @param request запрос
	 */
	private static void incrementSessionCounter(HttpServletRequest request)
	{
		try
		{
			HttpSession session = request.getSession(false);
			if(session == null)
				return;

			ServletContext context = session.getServletContext();
			if(context == null)
				return;

			Application application = ApplicationInfo.getCurrentApplication();
			if (application == null)
				return;

			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			String applicationInstanceName = applicationConfig.getApplicationInstanceName();

			Long terBank = DepartmentViewUtil.getCurrentDepartmentTBCode(session);
			UserCounter counter = SessionCountListener.getCounter(application);

			StringBuilder builder = new StringBuilder();
			builder.append("Количество сессий в приложении ");
			builder.append(application);
			builder.append(" увеличилось до ");
			builder.append(counter.incrementAndGet(terBank));
			builder.append(" на машине с адресом ");
			builder.append(applicationInstanceName);
			builder.append(".");
			builder.append(" Для тербанка ");
			builder.append(terBank);
			builder.append(".");

			// записываем в USERLOG
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			Calendar date = Calendar.getInstance();
			logWriter.writeSecurityOperation(new DefaultLogDataReader(builder.toString(), getReaderKey(application), com.rssl.phizic.security.config.Constants.COUNT_ACTIVE_SESSION), date, date);

		}
		catch (Throwable ex)
		{
			log.debug("Ошибка при увеличении счетчика активных сессий", ex);
		}
	}

	private static String getReaderKey(Application application)
	{
		ApplicationInfo appInfo = new ApplicationInfo(application);
		if (Application.PhizIC == application || Application.atm == application || appInfo.isMobileApi())
			return com.rssl.phizic.logging.Constants.COUNT_ACTIVE_SESSION_KEY_CLIENT;

		return com.rssl.phizic.logging.Constants.COUNT_ACTIVE_SESSION_KEY_ADMIN;
	}

	private static AuthenticationConfig getAuthenticationConfig()
	{
		return ConfigFactory.getConfig(AuthenticationConfig.class);
	}

	/**
	 * Переадресовать запрос на стадию аутентификации
	 * @param request запрос
	 * @param stage стадия
	 * @param addReturnTo добавить ?returnTo на url из request
	 * @return URL
	 */
	public static String redirectToStage(HttpServletRequest request, Stage stage, boolean addReturnTo)
	{
		return redirectToUrl(request, stage.getPrimaryAction(), addReturnTo);
	}

	/**
	 * Переадресовать запрос на URL
	 * @param request запрос
	 * @param url url
	 * @param addReturnTo добавить ?returnTo на url из request
	 * @return URL
	 */
	public static String redirectToUrl(HttpServletRequest request, String url, boolean addReturnTo)
	{
		String returnTo = getReturnTo(request);
		UrlBuilder forwardUrl = new UrlBuilder().setUrl(url);

		if (returnTo != null)
		{
			forwardUrl.addParameter("returnTo", returnTo);
		}
		else if (addReturnTo && filterRequest(request))
		{
			TimeoutSession ts = generateReturnTo(request);
			forwardUrl.addParameter("returnTo", String.valueOf(ts.getRandomRecordId()));
		}

		return forwardUrl.getUrl();
	}

	/**
	 * фильтруем запрос
	 */
	private static boolean filterRequest(HttpServletRequest request)
	{
		return !request.getRequestURI().endsWith("logoff.do");
	}

	private static TimeoutSession generateReturnTo(HttpServletRequest request)
	{
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();
		String contextPath = request.getContextPath();
		if (requestURI.startsWith(contextPath))
			requestURI = requestURI.substring(contextPath.length());

		UrlBuilder urlBuilder = new UrlBuilder().setUrl("");
		Map requestParameters = HttpServletRequestUtils.getParameterMap(request);
		for(Iterator itr = requestParameters.entrySet().iterator(); itr.hasNext();)
		{
			Map.Entry entry = (Map.Entry) itr.next();
			urlBuilder.addParameter(StringHelper.getEmptyIfNull(entry.getKey()),
									HttpServletRequestUtils.getEmptyIfNull(entry.getValue()));
		}

		TimeoutSession ts = new TimeoutSession();
		ts.setUrl(requestURI + (queryString == null ? "" : "?" + queryString));
		ts.setParametres(urlBuilder.getUrl());

		try
		{
			return timeoutSessionService.addOrUpdate(ts);
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String getReturnTo(HttpServletRequest request)
	{
		UrlBuilder requestUrl = new UrlBuilder().setUrl("/?" + request.getQueryString());
		return requestUrl.getParameter("returnTo");
	}

	/**
	 * @param request запрос
	 * @return URL url на который надо перейти после окончания аутентификации
	 */
	public static String getReturnToURL(HttpServletRequest request)
	{
		String returnTo = getReturnTo(request);
		if (returnTo == null)
		{
			return null;
		}
		TimeoutSession session = null;
		try
		{
			session = timeoutSessionService.getByRandomRecordId(returnTo);
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}

		if (session == null)
		{
			return null;
		}
		UrlBuilder requestUrl = new UrlBuilder().setUrl(session.getUrl());
		requestUrl.addParameter("sessionData", returnTo);
		return requestUrl.getUrl();
	}

	private static void writeToLog(LogDataReader reader, Calendar start, Calendar end) throws BusinessException
	{
		try
		{
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			logWriter.writeActiveOperation(reader, start, end);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи в лог данных по активной операции", e);
		}
	}

	private static void updateLogonInfo(HttpServletRequest request, AuthenticationContext authenticationContext)
	{
		// пока не понятно что и как логировать для гостя
		if(authenticationContext.isAuthGuest())
			return;

		Application application = ApplicationInfo.getCurrentApplication();
		LoginType loginType = authenticationContext.getLoginType();
		String identificationParameter = getIdentificationParameter(authenticationContext, loginType);

		securityService.updateLogonInfo(authenticationContext.getLogin(), RequestHelper.getIpAddress(request),
				request.getSession().getId(), ApplicationType.getApplicationType(application), loginType, identificationParameter);
	}

	private static Properties getAccessTypeProperties(CommonLogin login, AccessPolicy policy) throws BusinessException
	{
		try
		{
			if(AccessType.guest.equals(policy.getAccessType()))
				return getDefaultProperties(policy);

			return accessModeService.getProperties(login, policy.getAccessType());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	private static Properties getDefaultProperties(AccessPolicy policy)
	{
		ConfirmationMode confirmationMode = policy.getConfirmationMode();
		Properties properties = new Properties();
		properties.put(confirmationMode.getKeyProperty(), confirmationMode.getDefaultKeyStrategy());
		return properties;
	}

}
