package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.einvoicing.EInvoicingConstants;
import com.rssl.phizic.logging.logon.LogonLogConfig;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.util.ActionForwardHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.security.AccessControlException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author Evgrafov
 * @ created 14.12.2006
 * @ $Author: khudyakov $
 * @ $Revision: 85701 $
 */

public abstract class LoginStageActionSupport extends AutoSwitchSettingsAction
{
	private   static final String STAGE_COMLETE_KEY               = "login_stage_complete";
	private   static final String OPERATION_CONTEXT_KEY               = OperationContext.class.getName();
	protected static final String FORWARD_GOTO_AUTHORIZED_INDEX   = "GotoAuthorizedIndex";
	protected static final String FORWARD_GOTO_AUTHORIZED_INDEX_GUEST = "GotoAuthorizedIndexGuest";
	protected static final String FORWARD_GOTO_UNAUTHOTIZED_INDEX = "GotoUnauthorizedIndex";
	protected static final DepartmentService departmentService = new DepartmentService();
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	/**
	 * Возвращает режим работы пользователя, определяемый данным экшеном
	 * @return режим работы пользователя
	 */
	protected UserVisitingMode getUserVisitingMode()
	{
		return UserVisitingMode.BASIC;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward;
		setCurrentMapping(mapping);
		if (SecurityUtil.isAuthenticationComplete())
		{
			if (UserVisitingMode.GUEST.equals(getUserVisitingMode()))
			{
				forward = mapping.findForward(FORWARD_GOTO_AUTHORIZED_INDEX_GUEST);
			}
			else
			{
				// Нужно сначала нажать logoff
				forward = mapping.findForward(FORWARD_GOTO_AUTHORIZED_INDEX);
			}
		}
		else
		{
			forward = executeLogin(mapping, form, request, response);
		}

		return forward;
	}

	/**
	 * Форвард на неавтаризованную страницу
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward goToUnauthotized(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 return mapping.findForward(FORWARD_GOTO_UNAUTHOTIZED_INDEX);
	}

	protected ActionForward executeLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception

	{
		ActionForward forward = null;

		AccessPolicy policy = AuthenticationManager.getPolicy();
		Stage stage = AuthenticationManager.getStage();

		// ещё не известна политика, либо пройдены все стадии предыдущей сессии аутентификации
		if (policy == null || stage == null)
		{
			// пытаемся начать аутентификацию
			policy = tryStartAuthentication(mapping, request);
		}
		else
		{
			restoreLogonOperUID();
		}
		if (policy == null)
		{
			// отправляемся на страницу регистрации
			return goToUnauthotized(mapping, form, request, response);
		}

		stage = AuthenticationManager.getStage();
		if (stage == null)
			throw new IllegalStateException("Authentication started but stage is null");

		String actionUrl = mapping.getPath() + ".do";

		if (!stage.getAllAllowedActions().contains(actionUrl))
			return redirectToStage(request,stage);

		// продолжаем процесс аутентификации
		forward = super.execute(mapping, form, request, response);

		boolean comleted = isStageCompleted(request);
		if (comleted)
		{
			try
			{
				forward = forwardNext(request, mapping);
			}
			catch (BusinessLogicException e)
			{
				// ошибка при аутентификации - показываем сообщение
				saveError(request, e);
				forward = forwardError(mapping, form, request);
			}
		}
		return forward;
	}

	/**
	 * восстановить "идентификатор операции входа"
	 */
	private void restoreLogonOperUID()
	{
		Store store = StoreManager.getCurrentStore();
		String operationUID = (String) store.restore(OPERATION_CONTEXT_KEY);
		OperationContext.setCurrentOperUID(operationUID);
	}

	/**
	 * сохранить "идентификатор операции входа"
	 */
	private void saveLogonOperUID()
	{
		Store store = StoreManager.getCurrentStore();
		store.save(OPERATION_CONTEXT_KEY, OperationContext.getCurrentOperUID());
	}

	/**
	 * очистить "идентификатор операции входа"
	 */
	private void clearLogonOperUID()
	{
		Store store = StoreManager.getCurrentStore();
		store.remove(OPERATION_CONTEXT_KEY);
	}

	protected ActionForward forwardError(ActionMapping mapping, ActionForm form, HttpServletRequest request)
	{
		throw new AccessControlException("Доступ запрещен");
	}

	public ActionForward forwardNext(HttpServletRequest request, ActionMapping mapping) throws BusinessException, BusinessLogicException
	{
		Stage nextStage = AuthenticationManager.nextStage(request);

		if (nextStage == null)
		{
			// login completed
			ActionForward actionForward = startJob(mapping, request);
			writeLogonEvent(request.getHeader("User-Agent"));
			clearLogonOperUID();
			return actionForward;
		}
		else
		{
			// goto next stage
			return redirectToStage(request, nextStage);
		}
	}

	private boolean isStageCompleted(HttpServletRequest request)
	{
		Boolean comleted = (Boolean) request.getAttribute(STAGE_COMLETE_KEY);
		return comleted != null && comleted;
	}

	protected ActionForward redirectToStage(HttpServletRequest request, Stage actionStage)
	{
		return new ActionForward(AuthenticationManager.redirectToStage(request, actionStage, false), true);
	}

	private ActionForward startJob(ActionMapping mapping, HttpServletRequest request)
	{
		ActionForward forward;
		String returnTo = AuthenticationManager.getReturnToURL(request);

		AuthenticationContext authContext = AuthenticationContext.getContext();
		if (returnTo != null)
		{
			forward = new ActionForward(returnTo, true);
		}
		else if(authContext != null && StringHelper.isNotEmpty(authContext.getStartJobPagePath()))
		{
			forward = new ActionRedirect(authContext.getStartJobPagePath());
			Map<String, String> authParams = authContext.getAuthenticationParameters();
			// Убираем XML ФНС / УЭК, т.к. они явно не пролезут в HTTP GET
			authParams.remove(EInvoicingConstants.FNS_PAY_INFO);
			authParams.remove(EInvoicingConstants.UEC_PAY_INFO);
			authParams.remove(JS_EVENTS_PARAMETER_NAME);
			authParams.remove(DOM_ELEMENTS_PARAMETER_NAME);
			authParams.remove(DEVICE_PRINT_PARAMETER_NAME);
			authParams.remove(HTML_INJECTION_PARAMETER_NAME);
			authParams.remove(MAN_VS_MACHINE_DETECTION_PARAMETER_NAME);

			forward = ActionForwardHelper.appendParams(forward, authParams);
		}
		else
		{
			forward = mapping.findForward(FORWARD_GOTO_AUTHORIZED_INDEX);
		}

		// Переложим из контекста сообщения для показа пользователю на стартовой странице
		Collection<String> authMessages = authContext.getMessages();
		if (!CollectionUtils.isEmpty(authMessages))
		{
			for (String message : authMessages)
			{
				if (ApplicationUtil.isMobileApi())
					saveMessage(request, message);
				else
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false), null);
			}
		}
		Collection<String> inactiveESMessage = authContext.getInactiveESMessage();
		for (String message : inactiveESMessage)
		{
			if (ApplicationUtil.isMobileApi())
				saveInactiveESMessage(request, message);
			else
				saveInactiveESMessage(request.getSession(), message);
		}

		return forward;
	}

	/**
	 * Начать процесс аутентификации снова
	 * @param request запрос
	 * @param error причина ошибка (можно null)
	 * @return форвард на начало
	 */
	public ActionForward restartAuthentication(HttpServletRequest request, ActionMessage error)
	{
		AccessPolicy policy = AuthenticationManager.getPolicy();

		AuthenticationManager.abort();

		UserVisitingMode visitingMode = getUserVisitingMode();
		AuthenticationManager.startAuthentication(request, policy, visitingMode);

		if (error != null)
			saveSessionError(ActionMessages.GLOBAL_MESSAGE, error, null);

		AuthenticationMode authMode = policy.getAuthenticationMode(visitingMode);
		return redirectToStage(request, authMode.getStages().get(0));
	}

	/**
	 * Попытка начать процесс аутентификации
	 * @param actionPath экшен
	 * @param request текущий request
	 * @return если успешно то возвращается установленныз режим аутентификации иначе null
	 */
	protected AccessPolicy tryStartAuthentication(String actionPath, HttpServletRequest request)
	{
		UserVisitingMode visitingMode = getUserVisitingMode();
		AccessPolicy calculatedPolicy = null;
		int count = 0;

		AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class);
		List<AccessPolicy> list = authenticationConfig.getPolicies();

		for (AccessPolicy policy : list)
		{
            AuthenticationMode mode = policy.getAuthenticationMode(visitingMode);

            // эта политика не применима для аутентификации
            if(mode == null)
                continue;

            Stage firstStage = mode.getStages().get(0);

			if (firstStage.getAllAllowedActions().contains(actionPath))
			{
				calculatedPolicy = policy;
				count++;
			}
		}

		if (count == 1)
		{
			AuthenticationManager.startAuthentication(request, calculatedPolicy, visitingMode);
			saveLogonOperUID();
		}
		else
		{
			calculatedPolicy = null;
		}
		return calculatedPolicy;
	}

	/**
	 * Попытка начать процесс аутентификации
	 * @param mapping текущий mapping
	 * @param request текущий request
	 * @return если успешно то возвращается установленныз режим аутентификации иначе null
	 */
	protected AccessPolicy tryStartAuthentication(ActionMapping mapping, HttpServletRequest request)
	{
		// попытаемся найти mode из mapping.path
		String actionPath = mapping.getPath() + ".do";

		return tryStartAuthentication(actionPath, request);
	}

	/**
	 * @return текущая стадия аутентификации
	 */
	protected Stage currentStage()
	{
		return AuthenticationManager.getStage();
	}

	/**
	 * @return текущий контекст аутентификации
	 */
	protected AuthenticationContext getAuthenticationContext()
	{
		return AuthenticationContext.getContext();
	}

	/**
	 * завершить стадию
	 */
	protected void completeStage() throws BusinessException
	{
		HttpServletRequest request = currentRequest();
		request.setAttribute(STAGE_COMLETE_KEY, true);
	}

	protected void writeToOperationLog(Calendar start, String message) throws BusinessException
	{
		Calendar end = Calendar.getInstance();

		DefaultLogDataReader reader = new DefaultLogDataReader(LogWriter.AUTHENTICATION);
		reader.setKey(com.rssl.phizic.logging.Constants.AUTHENTICATION_KEY);
		reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGIN_DEFAULT_OPERATION_KEY);
		reader.addParametersReader(new SimpleLogParametersReader("message", message));
		try
		{
			logWriter.writeSecurityOperation(reader, start, end);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Записать в журнал входов информацию о поиске профиля
	 */
	protected void writeFindProfileEvent(AuthData authData, String deviceInfo)
	{
		ConfigFactory.getConfig(LogonLogConfig.class).getWriter().writeFindProfile(null,
				authData.getFirstName(), authData.getMiddleName(), authData.getLastName(),
				LoginHelper.getBirthDay(authData), authData.getPAN(), authData.getDocument(), null, deviceInfo
		);
	}

	/**
	 * Записать в журнал входов информацию о залогиненном клиенте
	 * @param browserInfo
	 */
	protected void writeLogonEvent(String browserInfo)
	{
		if (EmployeeContext.isAvailable())
		{
			Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
			ConfigFactory.getConfig(LogonLogConfig.class).getWriter().writeLogon(employee.getLogin().getId(),
					employee.getFirstName(), employee.getPatrName(), employee.getSurName(),
					null, null, null, null, browserInfo
			);
		}
		else if (PersonContext.isAvailable())
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData.isGuest())
			{
				//Журнал регистрации входов в ЕРИБ  для гостевых клиентов не реализуется. Все входы гостей фиксируются в журнале гостевых входов, ведущемся в ЦСА.
				return;
			}

			ActivePerson person = personData.getPerson();
			PersonDocument document = getPersonDocument(person.getPersonDocuments());

			ConfigFactory.getConfig(LogonLogConfig.class).getWriter().writeLogon(person.getLogin().getId(),
					person.getFirstName(), person.getPatrName(), person.getSurName(),
					person.getBirthDay(), person.getLogin().getLastLogonCardNumber(), document.getDocumentSeries(), document.getDocumentNumber(), browserInfo
			);
		}
		else
		{
			throw new IllegalStateException("При записи в журнал регистрации входов недоступны контексты ни сотрудника, ни клиента ");
		}
	}

	private PersonDocument getPersonDocument(Set<PersonDocument> personDocuments)
	{
		for (PersonDocument personDocument : personDocuments)
		{
			if (personDocument.getDocumentMain())
			{
				return personDocument;
			}
		}
		return new PersonDocumentImpl();  //NOP
	}
}
