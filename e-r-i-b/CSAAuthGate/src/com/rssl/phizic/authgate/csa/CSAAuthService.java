package com.rssl.phizic.authgate.csa;

import com.rssl.phizgate.common.services.StubTimeoutUrlWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.authgate.*;
import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.authgate.csa.ws.generated.*;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.logging.messaging.handlers.log.CSAJAXRPCLogHandler;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.config.ConfigFactory;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 * @author Gainanov
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class CSAAuthService implements AuthGateService
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private StubTimeoutIntegratedGateWrapper wrapper;

	public CSAAuthService() throws AuthGateException
	{
		wrapper = new StubTimeoutIntegratedGateWrapper();
	}
	public AuthParamsContainer checkSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException
	{
		AuthTokenType authToken = new AuthTokenType("string", params.getParameter("AuthToken"));
		GetPutListType getList = new GetPutListType();
		CheckSessionRqAuthContextGet []p = new CheckSessionRqAuthContextGet[13];
		p[0] = new CheckSessionRqAuthContextGet("AuthType","string","");
		p[1] = new CheckSessionRqAuthContextGet("AuthResult","string","");
		p[2] = new CheckSessionRqAuthContextGet("LastName","string","");
		p[3] = new CheckSessionRqAuthContextGet("FirstName","string","");
		p[4] = new CheckSessionRqAuthContextGet("MiddleName","string","");
		p[5] = new CheckSessionRqAuthContextGet("CLIENT","string","");
		p[6] = new CheckSessionRqAuthContextGet("ASPKey","string","");
		p[7] = new CheckSessionRqAuthContextGet("PAN","string","");
		p[8] = new CheckSessionRqAuthContextGet("BirthDate","string","");
		p[9] = new CheckSessionRqAuthContextGet("IsMoveSession","string","");
		p[10] = new CheckSessionRqAuthContextGet("UserId","string","");
		p[11] = new CheckSessionRqAuthContextGet("MB","string","");
		p[12] = new CheckSessionRqAuthContextGet("CB_CODE","string","");
		getList.setGet(p);
		try
		{
			CheckSessionRsType checkRs =  wrapper.getStub().checkSession(authToken,params.getParameter("Service"), getList);

			String statusCode = checkRs.getStatus().getStatusCode();
			if(!AuthStatusType.OK.toString().equals(statusCode))
				throw new AuthGateLogicException(getMessageForCode(checkRs.getStatus().getStatusCode()));

			AuthParamsContainer res = new AuthParamsContainer();
			res.addParameter("SID", checkRs.getSID());
			res.addParameter("StatusCode", statusCode);

			CheckSessionRsAuthDataParam []resultArray = checkRs.getAuthData().getParam();
			for(CheckSessionRsAuthDataParam param :resultArray)
				res.addParameter(param.getName(), param.get_value());

			if(!res.getParameter("AuthResult").equals(AuthStatusType.OK.toString()))
				throw new AuthGateLogicException(getMessageForCode(res.getParameter("AuthResult")));

			return res;
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	private String getMessageForCode(String statusCode)
	{
		AuthStatusType status = null;
		String error = "Ошибка в работе системы";
		try
		{
			status = AuthStatusType.valueOf(statusCode);
		}
		catch (IllegalArgumentException e)
		{
			return error;
		}

		if (status.equals(AuthStatusType.ERR_BADPSW) || status.equals(AuthStatusType.ERR_USRSRV) ||
				status.equals(AuthStatusType.TIMEBLOCK) || status.equals(AuthStatusType.ERR_NOTRIES) ||
				status.equals(AuthStatusType.ERR_NOPSW) || status.equals(AuthStatusType.ERR_CANCEL) ||
				status.equals(AuthStatusType.ERR_TIMEOUT))
		{
			return status.getDescription();
		}
		return error;
	}

	public AuthParamsContainer prepareAuthentication(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException
	{
		String SID = params.getParameter("SID");
		GetPutListType putList = new GetPutListType();
		CheckSessionRqAuthContextPut []p = new CheckSessionRqAuthContextPut[5];
		String service = params.getParameter("Service");
		String authType = params.getParameter("AuthType");
		p[0] = new CheckSessionRqAuthContextPut("AuthType", "String", "");
		p[1] = new CheckSessionRqAuthContextPut("NextService", "String", service);
		p[2] = new CheckSessionRqAuthContextPut("BackRef", "String", params.getParameter("BackRef"));
		p[3] = new CheckSessionRqAuthContextPut("OID", "String", params.getParameter("OID"));
		p[4] = new CheckSessionRqAuthContextPut("TEXT", "String", params.getParameter("Text"));
		putList.setPut(p);
		try
		{
			AuthParamsContainer container = new AuthParamsContainer();
			PrepareAuthenticationRsType response =  wrapper.getStub().prepareAuthentication(SID, putList);
			if(!response.getStatus().getStatusCode().equals(AuthStatusType.OK.toString()))
				throw new AuthGateLogicException(getMessageForCode(response.getStatus().getStatusCode()));
			container.addParameter("AuthToken",response.getAuthToken().get_value());
			return container;
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	public AuthParamsContainer checkAuthentication(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException
	{
		AuthTokenType authToken = new AuthTokenType("string", params.getParameter("AuthToken"));
		String service = params.getParameter("Service");
		GetPutListType getList = new GetPutListType();
		CheckSessionRqAuthContextGet []p = new CheckSessionRqAuthContextGet[3];
		p[0] = new CheckSessionRqAuthContextGet("AuthType","string","");
		p[1] = new CheckSessionRqAuthContextGet("AuthResult","string","");
		p[2] = new CheckSessionRqAuthContextGet("OID","string","");
		getList.setGet(p);
		try
		{
			CheckAuthenticationRsType checkAuth =  wrapper.getStub().checkAuthentication(authToken, service, getList);
			AuthParamsContainer res = new AuthParamsContainer();
			if(!checkAuth.getStatus().getStatusCode().equals(AuthStatusType.OK.toString()))
				throw new AuthGateLogicException(getMessageForCode(checkAuth.getStatus().getStatusCode()));
			CheckSessionRsAuthDataParam []result = checkAuth.getAuthData().getParam();
			for(CheckSessionRsAuthDataParam param :result)
			{
				res.addParameter(param.getName(), param.get_value());
			}
			if(!res.getParameter("AuthResult").equals(AuthStatusType.OK.toString()))
				throw new AuthGateLogicException(getMessageForCode(res.getParameter("AuthResult")));
			return res;
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	public AuthParamsContainer moveSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException
	{
		String SID = params.getParameter("SID");
		String authTokenRq = params.getParameter("AuthTokenRq");
		String nextService = params.getParameter("NextService");
		GetPutListType putList = new GetPutListType();
		CheckSessionRqAuthContextPut []p = new CheckSessionRqAuthContextPut[4];
		p[0] = new CheckSessionRqAuthContextPut("ASPKey", "String", params.getParameter("ASPKey"));
		p[1] = new CheckSessionRqAuthContextPut("BackRef", "String", params.getParameter("BackRef"));
		p[2] = new CheckSessionRqAuthContextPut("Page", "String", params.getParameter("Page"));
		p[3] = new CheckSessionRqAuthContextPut("ISEDBO", "String", params.getParameter("ISEDBO"));
		putList.setPut(p);
		try
		{
			MoveSessionRsType mvRes = wrapper.getStub().moveSession(SID,authTokenRq, nextService, putList);
			if(!mvRes.getStatus().getStatusCode().equals(AuthStatusType.OK.toString()))
				throw new AuthGateLogicException(getMessageForCode(mvRes.getStatus().getStatusCode()));
			AuthParamsContainer res = new AuthParamsContainer();
			res.addParameter("AuthToken", mvRes.getAuthToken().get_value());
			return res;
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	public AuthParamsContainer closeSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException
	{
		String SID = params.getParameter("SID");
		try
		{
			AuthSessionCancelRsType res =  wrapper.getStub().authSessionCancel(SID);
			if(!res.getStatus().getStatusCode().equals(AuthStatusType.OK.toString()))
				throw new AuthGateLogicException(getMessageForCode(res.getStatus().getStatusCode()));
			return null;
		}
		catch (RemoteException e)
		{
			if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
			{
				log.error("получена ошибка таймаута при закрытии сессии в csa.");
				return null;
			}
			throw new AuthGateException(e);
		}
	}

	public AuthParamsContainer prepareSession(String userId, String password) throws AuthGateException, AuthGateLogicException
	{
		try
		{
			PrepareSessionRsType res = wrapper.getStub().prepareSession(userId, password);
			if(!AuthStatusType.OK.toString().equals(res.getStatus().getStatusCode()))
				throw new AuthGateLogicException(getMessageForCode(res.getStatus().getStatusCode()));

			AuthParamsContainer container = new AuthParamsContainer();
			container.addParameter("AuthToken", res.getAuthToken());
			return container;
		}
		catch (RemoteException e)
		{
			throw new AuthGateException(e);
		}
	}

	public AuthConfig getConfig()
	{
		return ConfigFactory.getConfig(CSAConfig.class);
	}

	private void checkTimeoutException(RemoteException e) throws AuthGateLogicException
	{
		if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
		{
			log.error("получена ошибка таймаута при отправке сообщения в csa.");
			throw new AuthGateLogicException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
		}
	}

	private class StubTimeoutIntegratedGateWrapper extends StubTimeoutUrlWrapper<AuthServicePortType>
	{
		private CSARefreshConfig config;

		StubTimeoutIntegratedGateWrapper()
		{
			super();
			config = ConfigFactory.getConfig(CSARefreshConfig.class);
		}

		public Integer getTimeout()
		{
			return config.getAuthTimeout();
		}

		protected String getUrl()
		{
			return config.getCsaServiceUrl();
		}

		protected void createStub()
		{
			AuthService service = new AuthService_Impl();
			try
			{
				setStub(service.getAuthServicePort());
				//добавляем хендлер для лого писания
				WSRequestHandlerUtil.addWSRequestHandlerToService(service, CSAJAXRPCLogHandler.class);
			}
			catch (ServiceException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}
