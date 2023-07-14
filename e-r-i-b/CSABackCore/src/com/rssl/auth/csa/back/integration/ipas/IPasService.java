package com.rssl.auth.csa.back.integration.ipas;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.ipas.generated.*;
import com.rssl.phizgate.common.services.StubTimeoutUrlWrapper;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.handlers.log.IPASJAXRPCLogHandler;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.sun.xml.rpc.client.StubBase;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.rpc.ServiceException;

/**
 * @author krenev
 * @ created 31.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IPasService
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Object lock = new Object();
	private volatile StubTimeoutIntegratedGateWrapper wrapper;
	//��� ������ �� iPas, ��������������� �� �������� ���������� ��������
	private static final String SUCCESS_CODE = "OK";
	//������������ ������, ��������� ���� ��� ���
	private static final String ERR_AGAIN = "ERR_AGAIN";
	//��������� ����� ������� �����
	private static final String ERR_NOTRIES = "ERR_NOTRIES";
	//��� ������ �� iPas, ��������������� �� �������� �������������
	private static final String AUTH_SUCCESS_CODE = "AUTH_OK";
	//��� ������ �� iPas, ��������������� � ������������� ������� ��������
	private static final String SERVICE_UNAVAILABLE_CODE = "ERR_FAIL";
	//��� ������ �� iPas, ��������������� �� ���������� �������������. ����, ������������� �����, �������������
	//� ���������� ������� ��������������, � �� ��������� ������.
	private static final Set<String> AUTH_FAILURE_CODE = new HashSet<String>();
	//���� ��������� ������, ��� ������� �� ���� ������� ����� � ������� ��������� � ������������� ��������� ����������.
	private static final Set<String> SYSTEM_EXCEPTION_CODE = new HashSet<String>();

	static
	{
		AUTH_FAILURE_CODE.add("ERR_BADPSW"); //������������ ������
		AUTH_FAILURE_CODE.add("ERR_USRSRV"); //�������� ��� ������������
		AUTH_FAILURE_CODE.add(ERR_NOTRIES); //��������� ����� ������� �����
		AUTH_FAILURE_CODE.add("ERR_NOPSW"); //��� ���������������� ������� � ������ ����������� �������
		AUTH_FAILURE_CODE.add("ERR_TIMEOUT"); //��������� ���������� ����� ����� ������
		AUTH_FAILURE_CODE.add(ERR_AGAIN); //������������ ������, ��������� ���� ��� ���
		AUTH_FAILURE_CODE.add("TIME_BLOCK");
		AUTH_FAILURE_CODE.add("TIMEBLOCK");
		SYSTEM_EXCEPTION_CODE.add("ERR_PRMFMT");
		SYSTEM_EXCEPTION_CODE.add("ERR_FORMAT");
		SYSTEM_EXCEPTION_CODE.add("ERROR");
		SYSTEM_EXCEPTION_CODE.add(SERVICE_UNAVAILABLE_CODE);
	}

	private static final IPasService INSTANCE = new IPasService();

	private IPasService()
	{
	}

	/**
	 * @return ������� �������
	 */
	public static IPasService getInstance()
	{
		return INSTANCE;
	}

	private IPASWSSoap getIPasStub() throws ServiceException
	{
		if (wrapper != null)
		{
			return wrapper.getStub();
		}
		synchronized (lock)
		{
			if (wrapper != null)
			{
				return wrapper.getStub();
			}
			wrapper = new StubTimeoutIntegratedGateWrapper();
			return wrapper.getStub();
		}
	}

	/**
	 * ��������� ���� �����/������ � ipas
	 * @param login �����
	 * @param password ������
	 * @return ���� � ������������ � ������ �������� ��������������, null � ������ ��������� ��������������
	 * @throws ServiceUnavailableException � ������ ������������� iPas ��� ������� � ��� ��������.
	 * @throws Exception � ������ ��������� ������
	 */
	public CSAUserInfo verifyPassword(String login, String password) throws Exception
	{
		String stan = generateSTAN();
		VerifyRsType result = null;
		try
		{
			result = getIPasStub().verifyPassword(stan, login, password.toUpperCase());
		}
		catch (RemoteException e)
		{
			throw new ServiceUnavailableException(e);
		}
		catch (ServiceException e)
		{
			throw new ServiceUnavailableException(e);
		}

		if (!stan.equals(result.getSTAN()))
		{
			throw new SystemException("��������� ��������� �������������� � iPas: ��������� STAN = " + stan + ", �������  STAN = " + result.getSTAN());
		}

		String status = result.getStatus();
		if (AUTH_SUCCESS_CODE.equals(status))
		{
			//������� ��������������.
			return fillUserInfo(login, result.getUserInfo());
		}
		if (AUTH_FAILURE_CODE.contains(status))
		{
			//��������� ��������������.
			return null;
		}
		if (SYSTEM_EXCEPTION_CODE.contains(status))
		{
			//������ �������� ����������.
			throw new AdjacentServiceUnavailableException(status);
		}
		//��� ��������� ���� - ��������� ������.
		throw new SystemException("��������� ������ �������������� � iPas: ������� ��� ������ " + status);
	}

	/**
	 * ���������� ����� ������������ ������ � ����.
	 * @param userId �������������
	 * @return ���������. � ��� ���������� SID, ����� ���� � ����� ������, ���������� ���������  �������
	 */
	public IPasPasswordInformation prepareOTP(String userId) throws ServiceUnavailableException, SystemException
	{
		String stan = generateSTAN();
		PrepareOTPRsType result = null;

		try
		{
			result = getIPasStub().prepareOTP(stan, userId);
		}
		catch (RemoteException e)
		{
			throw new ServiceUnavailableException(e);
		}
		catch (ServiceException e)
		{
			throw new ServiceUnavailableException(e);
		}

		String status = result.getStatus();
		if (SUCCESS_CODE.equals(status))
		{
			//������� ��������������.
			return new IPasPasswordInformation(result.getSID(), result.getPasswordNo(), result.getReceiptNo(), result.getPasswordsLeft());
		}
		if (AUTH_FAILURE_CODE.contains(status))
		{
			//��������� ��������������.
			return null;
		}
		if (SERVICE_UNAVAILABLE_CODE.equals(status))
		{
			//������ �������� ����������.
			throw new ServiceUnavailableException("������� ����� � ������������� ������� �������� �� iPas: " + status);
		}
		//��� ��������� ���� - ��������� ������.
		throw new SystemException("��������� ������ �������������� � iPas: ������� ��� ������ " + status);
	}

	/**
	 * �������� ������������ ����� ������������ ������ � ����.
	 * @param sid �������� � ���� ��������� ��� �������� (������, etc.)
	 * @param password ������, ��������� ��������
	 * @return ���������� ���������� �������, ���� ������ ������� ���������� null
	 */
	public Integer verifyOTP(String sid, String password) throws ServiceUnavailableException, SystemException
	{
		String stan = generateSTAN();
		VerifyAttRsType result = null;

		try
		{
			result = getIPasStub().verifyOTP(stan, sid,  password.toUpperCase());
		}
		catch (RemoteException e)
		{
			throw new ServiceUnavailableException(e);
		}
		catch (ServiceException e)
		{
			throw new ServiceUnavailableException(e);
		}

		String status = result.getStatus();
		if (AUTH_SUCCESS_CODE.equals(status))
		{
			//������� ��������������.
			return null;
		}
		if (ERR_AGAIN.equals(status) || ERR_NOTRIES.equals(status))
		{
			return result.getAttempts();
		}
		if (SERVICE_UNAVAILABLE_CODE.equals(status))
		{
			//������ �������� ����������.
			throw new ServiceUnavailableException("������� ����� � ������������� ������� �������� �� iPas: " + status);
		}
		//��� ��������� ���� - ��������� ������.
		throw new SystemException("��������� ������ �������������� � iPas: ������� ��� ������ " + status);
	}

	private String generateSTAN()
	{
		return Utils.generateGUID();
	}

	private CSAUserInfo fillUserInfo(String login, UserInfoType userInfoType)
	{
		if (userInfoType == null)
		{
			throw new IllegalArgumentException("userInfoType �� ������ ���� null");
		}
		CSAUserInfo userInfo = new CSAUserInfo(CSAUserInfo.Source.WAY4U);
		userInfo.setFirstname(userInfoType.getFirstName());
		userInfo.setPatrname(userInfoType.getMiddleName());
		userInfo.setSurname(userInfoType.getLastName());
		userInfo.setPassport(userInfoType.getPassportNo());
		userInfo.setBirthdate(XMLDatatypeHelper.parseDate(userInfoType.getBirthDate()));
		userInfo.setCbCode(userInfoType.getCB_CODE());
		userInfo.setUserId(login);
		userInfo.setCardNumber(userInfoType.getCardNumber());
		return userInfo;
	}

	private class StubTimeoutIntegratedGateWrapper extends StubTimeoutUrlWrapper<IPASWSSoap>
	{
		private Config config;

		StubTimeoutIntegratedGateWrapper()
		{
			super();
			config = ConfigFactory.getConfig(Config.class);
		}

		public Integer getTimeout()
		{
			return config.getIPasCsaBackTimeout();
		}

		protected String getUrl()
		{
			return config.getIPasURL();
		}

		protected void createStub()
		{
			setStub(new IPASWS_Impl().getIPASWSSoap());
			((StubBase) wrapper.getStub())._getHandlerChain().add(new IPASJAXRPCLogHandler());
		}
	}
}
