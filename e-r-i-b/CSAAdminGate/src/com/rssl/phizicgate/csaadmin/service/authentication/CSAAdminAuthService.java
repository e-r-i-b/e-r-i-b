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
 * ������ ������ � ������� �������������� ���������� � ��� �����
 */
public class CSAAdminAuthService extends CSAAdminServiceBase
{
	/**
	 * �����������
	 */
	public CSAAdminAuthService()
	{
		super(null);
	}

	/**
	 * �������� ��������� �������������� � ��� ����� �� ������
	 * @param token - �����
	 * @return ��������� ��������������
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
	 * �������� ��������� �������������� � ��� ����� �� ������
	 * @param externalId ������� ������������� �������
	 * @return ������ ��� �������������
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
	 * ������� ������ � ��� �����
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
	 * ������� ���� ����������.
	 * @param nodeId - ������������� ������ �����
	 * @param action - ����� �������� � ����� �����
	 * @param parameters - ��������� � ������ �������� � ����� �����
	 * @return ��� ��������������� � ����� �����
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
	 * ��������� �������� ��������
	 * @param context - ��������
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
	 * �������� �������� ��������
	 * @return ��������
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
	 * @return ��� ����� �������� �������� ����������
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
