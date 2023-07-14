package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.operations.restrictions.NullClientRestriction;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.webapi.Token;
import com.rssl.phizic.business.webapi.TokenService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.security.AuthenticationManager;
import com.rssl.phizic.web.webApi.exceptions.InvalidSessionException;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.helpers.CommonElementsHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.LoginRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.LoginResponse;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

/**
 * Заполняет ответ на запрос аутентификации пользователя
 * @author Jatsky
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */

public class LoginResponseConstructor extends JAXBResponseConstructor<Request, LoginResponse>
{
	private static final SecurityService securityService = new SecurityService();
	private static final TokenService tokenService = new TokenService();
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();

	@Override protected LoginResponse makeResponse(Request request) throws Exception
	{
		LoginRequest rqst = (LoginRequest) request;
		String token = rqst.getBody().getToken();
		String ip = rqst.getIp();
		if (!rqst.getBody().getAuthenticationCompleted())
		{
			Document doc = CSABackRequestHelper.sendFinishCreateSessionRq(token);
			AuthData authData = new AuthData();
			AuthenticationHelper.fillFromERIBCSAData(authData, doc, false, null);
			fillContext(authData, token, ip, null);
		}
		else
		{
			Token webApiToken = tokenService.findToken(token);
			if (webApiToken == null)
			{
				throw new InvalidSessionException();
			}
			fillContext(null, token, ip, webApiToken.getLogin());
		}

		LoginResponse response = new LoginResponse();
		response.setLoginCompleted(true);

		response.setPerson(CommonElementsHelper.createPersonTag());
		return response;
	}

	private void fillContext(AuthData authData, String token, String ip, Login login) throws BusinessException, ParseException, BusinessLogicException
	{
		if (!SecurityUtil.isAuthenticationComplete())
		{
			Calendar start = Calendar.getInstance();
			AccessPolicy policy = AuthenticationManager.getPolicy();
			AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class);
			List<AccessPolicy> list = authenticationConfig.getPolicies();

			for (AccessPolicy accPolicy : list)
			{
				AuthenticationMode mode = accPolicy.getAuthenticationMode(UserVisitingMode.BASIC);

				// эта политика не применима для аутентификации
				if (mode == null)
					continue;

				policy = accPolicy;
			}

			AuthenticationMode authMode = policy.getAuthenticationMode(UserVisitingMode.BASIC);
			if (authMode == null)
				throw new ConfigurationException("Не найден режим аутентификации для политики " + policy);

			AuthenticationContext context = new AuthenticationContext(policy);
			context.setVisitingMode(UserVisitingMode.BASIC);
			AuthenticationContext.setContext(context);

			Person person;
			if (login == null)
			{
				person = LoginHelper.synchronize(authData, token, AuthentificationSource.full_version, UserVisitingMode.BASIC, NullClientRestriction.INSTANCE);
				if (person == null)
					throw new BusinessException("Клиент с не найден. Логин " + authData.getUserId());
			}
			else
			{
				person = personService.findByLogin(login);
				if (person == null)
					throw new BusinessException("Клиент с не найден. Логин " + login.getId());
				context.setNeedReloadProducts(false);
			}

			SecurityUtil.checkDepartmentIsServiced(person);

			login = person.getLogin();
			securityService.updateLogonInfo(login, ip, WebContext.getCurrentRequest().getSession(false).getId(), ApplicationType.getApplicationType(Application.WebAPI));

			context.setLogin(login);

			createAuthModule(context);

			AthenticationCompleteAction completeAction = authMode.getAuthenticationCompleteAction();
			ContextFillHelper.fillContextByLogin(login);
			try
			{
				completeAction.execute(AuthenticationContext.getContext());

				DefaultLogDataReader reader = new DefaultLogDataReader(LogWriter.AUTHENTICATION_FINISH);
				reader.setKey(com.rssl.phizic.logging.Constants.AUTHENTICATION_FINISH_KEY);
				reader.setOperationKey(com.rssl.phizic.security.config.Constants.USER_LOGON_OPERATION_KEY);
				writeToLog(reader, start, GregorianCalendar.getInstance());
			}
			catch (SecurityException e)
			{
				throw new BusinessException(e);
			}
			catch (SecurityLogicException e)
			{
				AuthenticationContext.getContext().putMessage(e.getMessage());
			}
		}
	}

	private static void createAuthModule(AuthenticationContext context)
	{
		CommonLogin login = context.getLogin();
		AccessPolicy policy = context.getPolicy();
		Properties props = context.getPolicyProperties();

		PrincipalImpl principal = login == null ? null : new PrincipalImpl(login, policy, props);
		SecurityUtil.createAuthModule(principal);
		SecurityUtil.completeAuthentication();
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
			throw new BusinessException(e);
		}
	}
}

