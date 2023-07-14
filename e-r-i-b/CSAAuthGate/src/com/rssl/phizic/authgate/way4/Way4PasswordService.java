package com.rssl.phizic.authgate.way4;

import com.rssl.phizgate.common.services.StubTimeoutWrapper;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.AuthStatusType;
import com.rssl.phizic.authgate.csa.CSAConfig;
import com.rssl.phizic.authgate.csa.CSARefreshConfig;
import com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationPortType_v1_0;
import com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationService_v1_0;
import com.rssl.phizic.authgate.ipas.cap.ws.generated.CAPAuthenticationService_v1_0_Impl;
import com.rssl.phizic.authgate.passwords.PasswordGateService;
import com.rssl.phizic.authgate.way4.ws.generated.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.logging.messaging.handlers.log.IPASJAXRPCLogHandler;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.sun.xml.rpc.client.StubBase;

import java.rmi.RemoteException;
import java.util.Random;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

/**
 * @author Gainanov
 * @ created 13.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class Way4PasswordService implements PasswordGateService
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private StubTimeoutIntegratedGateWrapper<IPASWSSoap> wrapper;
	private StubTimeoutIntegratedGateWrapper<CAPAuthenticationPortType_v1_0> wrapperCap;

	public Way4PasswordService() throws AuthGateException
	{
		try
		{
			IPASWS service = new IPASWS_Impl();
			wrapper = new StubTimeoutIntegratedGateWrapper<IPASWSSoap>(service.getIPASWSSoap());
			((StubBase) wrapper.getStub())._getHandlerChain().add(new IPASJAXRPCLogHandler());
			((com.sun.xml.rpc.client.StubBase) wrapper.getStub()).
					_setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, ConfigFactory.getConfig(CSAConfig.class).getWay4ServiceUrl());

			CAPAuthenticationService_v1_0 serviceCap = new CAPAuthenticationService_v1_0_Impl();
			wrapperCap = new StubTimeoutIntegratedGateWrapper<CAPAuthenticationPortType_v1_0>(serviceCap.getIPASWSSoap());
			((StubBase) wrapper.getStub())._getHandlerChain().add(new IPASJAXRPCLogHandler());
			((com.sun.xml.rpc.client.StubBase) wrapperCap.getStub()).
					_setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, ConfigFactory.getConfig(CSAConfig.class).getiPasCapServiceUrl());
		}
		catch (ServiceException e)
		{
			throw new AuthGateException(e);
		}
	}

	public AuthParamsContainer prepareOTP(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException
	{
		try
		{
			if (container.getParameter("UserId") == null || container.getParameter("UserId").equals(""))
				throw new AuthGateException("Не указан UserId");

			AuthParamsContainer result = new AuthParamsContainer();
			PrepareOTPRsType res = wrapper.getStub().prepareOTP(generateSTAN(), container.getParameter("UserId"));
			if (!res.getStatus().equals(AuthStatusType.OK.toString()))
				getMessageForError(res);

			result.addParameter("SID", res.getSID());
			result.addParameter("PasswordNo", res.getPasswordNo());
			result.addParameter("ReceiptNo", res.getReceiptNo());
			result.addParameter("PasswordsLeft", res.getPasswordsLeft().toString());
			return result;
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	private void getMessageForError(CommonRsType rs) throws AuthGateLogicException, AuthGateException
	{
		AuthStatusType status = null;
		String unknown = "Вернулся неизвестный статус";
		try
		{
			status = AuthStatusType.valueOf(rs.getStatus());
		}
		catch (IllegalArgumentException e)
		{
			throw new AuthGateException(unknown, e);
		}

		if (status.equals(AuthStatusType.ERR_AGAIN))
		{
			if (rs instanceof VerifyAttRsType)
				throw new AuthGateLogicException("Неправильный пароль, повторите ввод еще раз. Осталось попыток ввода: "
						+ ((VerifyAttRsType) rs).getAttempts(), status);
			throw new AuthGateLogicException(status.getDescription(), status);
		}
		else if (status.equals(AuthStatusType.ERR_BADPSW) || status.equals(AuthStatusType.ERR_USRSRV) ||
				status.equals(AuthStatusType.ERR_NOTRIES) || status.equals(AuthStatusType.ERR_NOPSW) ||
				status.equals(AuthStatusType.ERR_TIMEOUT)||status.equals(AuthStatusType.TIMEBLOCK))
		{
			throw new AuthGateLogicException(status.getDescription(), status);
		}
		else if (status.equals(AuthStatusType.ERR_PRMFMT) || status.equals(AuthStatusType.ERR_FORMAT) ||
				status.equals(AuthStatusType.ERR_SID) || status.equals(AuthStatusType.ERR_SCHEME))
		{
			throw new AuthGateException(status.getDescription());
		}
		else
		{
			throw new AuthGateException(unknown);
		}
	}

	private String generateSTAN()
	{
		return SIDGenerator.getUniqueID();
	}

	public AuthParamsContainer verifyOTP(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException
	{
		try
		{
			if (container.getParameter("SID") == null || container.getParameter("SID").equals(""))
				throw new AuthGateException("Незаполнен параметр SID!");
			if (container.getParameter("password") == null || container.getParameter("password").equals(""))
				throw new AuthGateException("Незаполнен параметр password!");

			String upperCasePassword = container.getParameter("password").toUpperCase();
			container.addParameter("password",upperCasePassword);

			VerifyAttRsType res = wrapper.getStub().verifyOTP(generateSTAN(), container.getParameter("SID"), container.getParameter("password"));
			if (!res.getStatus().equals(AuthStatusType.AUTH_OK.toString()))
				getMessageForError(res);
			AuthParamsContainer result = new AuthParamsContainer();
			result.addParameter("Token", res.getToken()!=null ? res.getToken() : "");
			result.addParameter("Attempts", res.getAttempts() != null ? res.getAttempts().toString() : "0");
			return result;
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	public AuthParamsContainer generateStaticPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException
	{
		try
		{
			if (container.getParameter("UserId") == null || container.getParameter("UserId").equals(""))
				throw new AuthGateException("Незаполнен параметр UserId!");
			if (container.getParameter("password") == null || container.getParameter("password").equals(""))
				throw new AuthGateException("Незаполнен параметр password!");
			GeneratePasswordRsType res = wrapper.getStub().generatePassword(generateSTAN(), container.getParameter("UserId"), container.getParameter("password"));
			if (!res.getStatus().equals(AuthStatusType.AUTH_OK.toString()))
				getMessageForError(res);
			AuthParamsContainer result = new AuthParamsContainer();
			result.addParameter("Password", res.getPassword());
			return result;
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	public AuthParamsContainer sendSmsPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException
	{
		return null;  //TODO пока нет отсылки смс-паролей оставляем в таком виде
	}

	public AuthParamsContainer verifyPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException
	{
		try
		{
		  if (container.getParameter("UserId") == null || container.getParameter("UserId").equals(""))
				throw new AuthGateException("Незаполнен параметр UserId!");
		  if (container.getParameter("password") == null || container.getParameter("password").equals(""))
				throw new AuthGateException("Незаполнен параметр password!");
		String upperCasePassword = container.getParameter("password").toUpperCase();
		VerifyRsType res = wrapper.getStub().verifyPassword(generateSTAN(), container.getParameter("UserId"), upperCasePassword);
		if (!res.getStatus().equals(AuthStatusType.AUTH_OK.toString()))
				getMessageForError(res);
			AuthParamsContainer  result  = new AuthParamsContainer();
	        result.addParameter("Status", res.getStatus());
	        result.addParameter("BirthDate", res.getUserInfo().getBirthDate());
	        result.addParameter("CardNumber", res.getUserInfo().getCardNumber());
	        result.addParameter("CB_CODE", res.getUserInfo().getCB_CODE());
	        result.addParameter("ClientRegNum", res.getUserInfo().getClientRegNum());
	        result.addParameter("DL", res.getUserInfo().getDL());
	        result.addParameter("FirstName", res.getUserInfo().getFirstName());
	        result.addParameter("LastName", res.getUserInfo().getLastName());
	        result.addParameter("MiddleName", res.getUserInfo().getMiddleName());
			result.addParameter("MB", res.getUserInfo().getMB());
			result.addParameter("PassportNo", res.getUserInfo().getPassportNo());
			result.addParameter("PasswordsLeft", res.getUserInfo().getPasswordsLeft());
			result.addParameter("UserId", container.getParameter("UserId"));
			return  result;
		}
		catch  (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}

	}

	public void verifyCAP(String cardNumber, String capToken) throws AuthGateException, AuthGateLogicException
	{
		try
		{
			if (StringHelper.isEmpty(cardNumber))
				throw new AuthGateException("Не заполнен параметр CardNumber!");
			if (StringHelper.isEmpty(capToken))
				throw new AuthGateException("Не заполнен параметр CAPToken!");
			com.rssl.phizic.authgate.ipas.cap.ws.generated.CommonRsType res  = wrapperCap.getStub().verifyCAP(generateSTAN(),cardNumber,capToken);
			if (!res.getStatus().equals(AuthStatusType.AUTH_OK.toString()))
			{
				AuthStatusType status = AuthStatusType.valueOf(res.getStatus());
				throw new AuthGateLogicException(status.getDescription(), status);
			}
		}
		catch (RemoteException e)
		{
			checkTimeoutException(e);
			throw new AuthGateException(e);
		}
	}

	private static class SIDGenerator
	{
		private static final int NUM_CHARS = 32;
		private static String chars = "ABCDEF0123456789";
		private static Random r = new Random();

		public static String getUniqueID()
		{
			char[] buf = new char[NUM_CHARS];

			for (int i = 0; i < buf.length; i++)
			{
				buf[i] = chars.charAt(r.nextInt(chars.length()));
			}

			return new String(buf);
		}
	}

	private void checkTimeoutException(RemoteException e) throws AuthGateLogicException
	{
		if (e.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
		{
			log.error("получена ошибка таймаута при отправке сообщения в ipas.");
			throw new AuthGateLogicException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, e);
		}
	}

	private class StubTimeoutIntegratedGateWrapper<S extends java.rmi.Remote> extends StubTimeoutWrapper<S>
	{
		private CSARefreshConfig config;

		StubTimeoutIntegratedGateWrapper(S stub)
		{
			super(stub);
			config = ConfigFactory.getConfig(CSARefreshConfig.class);
		}

		public Integer getTimeout()
		{
			return config.getIPasTimeout();
		}
	}
}
