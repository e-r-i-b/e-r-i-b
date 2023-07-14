package com.rssl.phizicgate.csaadmin.service.authentication;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.xstream.XStreamSerializer;
import com.rssl.phizicgate.csaadmin.service.CSAAdminServiceBase;
import com.rssl.phizicgate.csaadmin.service.generated.*;
import com.rssl.phizicgate.csaadmin.service.types.AuthenticationParameters;
import com.rssl.phizicgate.csaadmin.service.types.EmployeeContextData;
import com.rssl.phizicgate.csaadmin.service.types.EmployeeSynchronizationData;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с данными уатентификации сотрудника в ЦСА Админ
 */
public class CSAAdminAuthService extends CSAAdminServiceBase
{
	/**
	 * Конструктор
	 */
	public CSAAdminAuthService()
	{
		super(null);
	}

	/**
	 * Получить параметры аутентификации в ЦСа Админ по токену
	 * @param token - токен
	 * @return параметры аутентификации
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public AuthenticationParameters getAuthenticationParameters(String token) throws GateException,GateLogicException
	{
		RequestData data = new RequestData();
		data.setGetAuthenticationParametersRq(new AuthenticationParametersRequestType(token));
		ResponseData response = process(data);
		AuthenticationParameters authenticationParameters = new AuthenticationParameters();
		AuthenticationParametersResultType authenticationParametersResult = response.getGetAuthenticationParametersRs();
		authenticationParameters.setLoginId(authenticationParametersResult.getLoginId());
		authenticationParameters.setLastEmployeeUpdateDate(parseCalendar(authenticationParametersResult.getLastEmployeeUpdateDate()));
		authenticationParameters.setSessionId(authenticationParametersResult.getSessionId());
		authenticationParameters.setAction(authenticationParametersResult.getAction());
		authenticationParameters.setParameters(fromGate(authenticationParametersResult.getParameters()));
		return authenticationParameters;
	}

	/**
	 * Получить параметры аутентификации в ЦСа Админ по токену
	 * @param externalId внешний идентификатор клиента
	 * @return данные для синхронизации
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public EmployeeSynchronizationData getEmployeeSynchronizationParameters(String externalId) throws GateException,GateLogicException
	{
		RequestData data = new RequestData();
		data.setGetEmployeeSynchronizationDataRq(new GetEmployeeSynchronizationDataRequestType(externalId));
		ResponseData response = process(data);
		return EmployeeSynchronizationData.fromGate(response.getGetEmployeeSynchronizationDataRs());
	}

	/**
	 * Закрыть сессию в ЦСА Админ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void closeSession() throws GateException,GateLogicException
	{
		RequestData data = new RequestData();
		data.setCloseSessionRq(new CloseSessionParametersRequestType());
		process(data);
	}

	/**
	 * Сменить блок сотрудника.
	 * @param nodeId - идентификатор нового блока
	 * @param action - экшен перехода в новом блоке
	 * @param parameters - параметры в экшене перехода в новом блоке
	 * @return урл постАвторизации в новом блоке
	 */
	public String changeEmployeeNode(Long nodeId, String action, Map<String,String> parameters) throws GateException,GateLogicException
	{
		RequestData data = new RequestData();
		ChangeNodeRequestType changeNodeRequest = new ChangeNodeRequestType();
		changeNodeRequest.setNodeId(nodeId);
		changeNodeRequest.setAction(action);
		changeNodeRequest.setParameters(toGate(parameters));
		data.setChangeNodeRq(changeNodeRequest);
		ResponseData response = process(data);
		return response.getChangeNodeRs().getUrl();
	}

	/**
	 * Сохранить контекст операции
	 * @param context - контекст
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void saveOperationContext(Map<String,Object> context) throws GateException,GateLogicException
	{
		RequestData data = new RequestData();
		SaveOperationContextRequestType request = new SaveOperationContextRequestType();
		request.setContext(XStreamSerializer.serialize(context));
		data.setSaveOperationContextRq(request);
		process(data);
	}

	/**
	 * Получить контекст операции
	 * @return контекст
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Map<String,Object> getOperationContext() throws GateException,GateLogicException
	{
		RequestData data = new RequestData();
		GetOperationContextRequestType request = new GetOperationContextRequestType();
		data.setGetOperationContextRq(request);
		ResponseData response = process(data);
		return (Map<String,Object>)XStreamSerializer.deserialize(response.getGetOperationContextRs().getContext());
	}

	/**
	 * @return ЦСА Админ контекст текущего сотрудника
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public EmployeeContextData getCurrentEmployeeContextRq() throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		data.setGetCurrentEmployeeContextRq(new GetCurrentEmployeeContextParametersType());
		ResponseData response = process(data);
		return new EmployeeContextData(response.getGetCurrentEmployeeContextRs().getLoginId(),response.getGetCurrentEmployeeContextRs().isAllTbAccess());
	}
}
