package com.rssl.phizic.csaadmin.listeners.authentication;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.csaadmin.business.authtoken.AuthenticationToken;
import com.rssl.phizic.csaadmin.business.authtoken.AuthenticationTokenService;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.departments.AllowedDepartmentService;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.csaadmin.business.operation.OperationContext;
import com.rssl.phizic.csaadmin.business.operation.OperationContextService;
import com.rssl.phizic.csaadmin.business.session.Session;
import com.rssl.phizic.csaadmin.business.session.SessionService;
import com.rssl.phizic.csaadmin.business.session.SessionState;
import com.rssl.phizic.csaadmin.listeners.generated.*;
import com.rssl.phizic.csaadmin.log.InitializeContextHelper;
import com.rssl.phizic.csaadmin.operation.ChangeNodeOperation;
import com.rssl.phizic.csaadmin.utils.CSAAdminUtils;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 */
public class AuthenticationServiceImpl
{
	private static final SessionService sessionService = new SessionService();
	private static final AuthenticationTokenService authTokenService = new AuthenticationTokenService();
	private static final OperationContextService operationContextService = new OperationContextService();
	private static final AllowedDepartmentService allowedDepartmentService = new AllowedDepartmentService();


	protected static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	/**
	 * ѕолучить параметры аутентификации сотрудника по токену аутентификации
	 * @param request - параметры запроса с токеном сессии
	 * @return параметры аутентификации сотрудника
	 * @throws AdminException
	 */
	public AuthenticationParametersResultType getAuthenticationParameters(AuthenticationParametersRequestType request) throws AdminException
	{
		AuthenticationToken authToken = authTokenService.findActiveById(request.getToken());
		Session session = authToken.getSession();
		InitializeContextHelper.initializeContext(session);
		AuthenticationParametersResultType authenticationParameters = new AuthenticationParametersResultType();
		authenticationParameters.setLoginId(session.getLogin().getName());
		authenticationParameters.setSessionId(session.getSid());
		authenticationParameters.setLastEmployeeUpdateDate(getStringDateTime(session.getLogin().getLastUpdateDate()));
		authenticationParameters.setAction(authToken.getAction());
		authenticationParameters.setParameters(toGate(authToken.getParameters()));
		authTokenService.closeToken(authToken);
		return authenticationParameters;
	}

	/**
	 * «акрыть сессию сотрудника
	 * @param session - сесси€ сотрудника
	 * @return пустой ответ
	 * @throws AdminException
	 */
	public VoidResultType closeSession(Session session) throws AdminException
	{
		session.setState(SessionState.CLOSED);
		session.setCloseDate(Calendar.getInstance());
		sessionService.save(session);
		return new VoidResultType();
	}

	/**
	 * —мена блока сотрудника
	 * @param session - сесси€ в рамках которой выполн€етс€ операци€
	 * @param request - параметры запроса
	 * @return урл страница постјвторизации в новом блоке
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public ChangeNodeResultType changeNode(Session session, ChangeNodeRequestType request) throws AdminException, AdminLogicException
	{
		ChangeNodeOperation operation = new ChangeNodeOperation();
		operation.initialize(session,request.getAction(),fromGate(request.getParameters()));
		operation.changeNode(request.getNodeId());

		AuthenticationToken token = operation.createToken();
		NodeInfo nodeInfo = operation.getCurrentNode();
		ChangeNodeResultType result = new ChangeNodeResultType();
		result.setUrl(CSAAdminUtils.generateEribLoginUrl(token,nodeInfo));
		return result;
	}

	/**
	 * —охранить сонтекст операции поиска клиента
	 * @param session - сесси€ в рамках которой выполн€етс€ операци€
	 * @param request - параметры запроса
	 * @return пустой ответ
	 * @throws AdminException
	 */
	public VoidResultType saveOperationContext(Session session, SaveOperationContextRequestType request) throws AdminException
	{
		OperationContext operationContext = new OperationContext(session);
		operationContext.setContext(request.getContext());
		operationContextService.save(operationContext);
		return new VoidResultType();
	}

	/**
	 * ѕолучить контекст операции поиска клиента
	 * @param session - сесси€ в рамках которой выполн€етс€ операци€
	 * @return контекст операции поиска клиента
	 * @throws AdminException
	 */
	public GetOperationContextResultType getOperationContext(Session session) throws AdminException
	{
		OperationContext operationContext = operationContextService.findActiveForSession(session);
		if(operationContext == null)
			throw new AdminException("Ќе найден контекст операции поиска клиента");
		operationContextService.close(operationContext);
		return new GetOperationContextResultType(operationContext.getContext());
	}

	/**
	 * @param login логин сотрудника
	 * @return контекст сотрудника
	 * @throws AdminException
	 */
	public GetCurrentEmployeeContextResultType getContextByLogin(Login login) throws AdminException, AdminLogicException
	{
		GetCurrentEmployeeContextResultType result = new GetCurrentEmployeeContextResultType();
		result.setLoginId(login.getId());
		result.setAllTbAccess(allowedDepartmentService.isAllTbAccess(login.getId()));
		return result;
	}

	private MapEntryType[] toGate(Map<String,String> parameters)
	{
		ArrayList<MapEntryType> resultList = new ArrayList<MapEntryType>();
		for(Map.Entry<String,String> entry: parameters.entrySet())
		{
			resultList.add(new MapEntryType(entry.getKey(),entry.getValue()));
		}
		return resultList.toArray(new MapEntryType[resultList.size()]);
	}

	private Map<String,String> fromGate(MapEntryType[] parameters)
	{
		Map<String,String> result = new HashMap<String,String>();
		if (parameters == null)
			return result;
		for(MapEntryType mapEntry: parameters)
		{
			result.put(mapEntry.getKey(),mapEntry.getValue());
		}
		return result;
	}
}
