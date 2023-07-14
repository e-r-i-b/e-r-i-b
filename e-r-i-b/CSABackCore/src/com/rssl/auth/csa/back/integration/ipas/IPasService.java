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
	//Код ответа от iPas, сигнализирующий об успешном выполнении операции
	private static final String SUCCESS_CODE = "OK";
	//Неправильный пароль, повторите ввод еще раз
	private static final String ERR_AGAIN = "ERR_AGAIN";
	//Превышено число попыток ввода
	private static final String ERR_NOTRIES = "ERR_NOTRIES";
	//Код ответа от iPas, сигнализирующий об успешной аутентифкации
	private static final String AUTH_SUCCESS_CODE = "AUTH_OK";
	//Код ответа от iPas, сигнализирующий о недоступности смежных сервисов
	private static final String SERVICE_UNAVAILABLE_CODE = "ERR_FAIL";
	//Код ответа от iPas, сигнализирующий об неуспешной аутентифкации. Коды, перечисленные здесь, сигнализируют
	//о неуспешной попытке аутентификации, а не системной ошибке.
	private static final Set<String> AUTH_FAILURE_CODE = new HashSet<String>();
	//Коды системной ошибки, при котором не даем клиенту войти и выводим сообщение о невозможности усановить соединение.
	private static final Set<String> SYSTEM_EXCEPTION_CODE = new HashSet<String>();

	static
	{
		AUTH_FAILURE_CODE.add("ERR_BADPSW"); //Неправильный пароль
		AUTH_FAILURE_CODE.add("ERR_USRSRV"); //Неверное имя пользователя
		AUTH_FAILURE_CODE.add(ERR_NOTRIES); //Превышено число попыток ввода
		AUTH_FAILURE_CODE.add("ERR_NOPSW"); //Нет неиспользованных паролей в списке одноразовых паролей
		AUTH_FAILURE_CODE.add("ERR_TIMEOUT"); //Превышено допустимое время ввода пароля
		AUTH_FAILURE_CODE.add(ERR_AGAIN); //Неправильный пароль, повторите ввод еще раз
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
	 * @return инстанс сервиса
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
	 * Проверить пару логин/пароль в ipas
	 * @param login логин
	 * @param password пароль
	 * @return инфа о пользователе в случае успешной аутентификации, null в случае неудачной аутентификации
	 * @throws ServiceUnavailableException в случае недоступности iPas или смежных с ним сервисов.
	 * @throws Exception в случае системных ошибок
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
			throw new SystemException("Нарушение протокола взаимодействия с iPas: отправлен STAN = " + stan + ", получен  STAN = " + result.getSTAN());
		}

		String status = result.getStatus();
		if (AUTH_SUCCESS_CODE.equals(status))
		{
			//Удачная аутентификация.
			return fillUserInfo(login, result.getUserInfo());
		}
		if (AUTH_FAILURE_CODE.contains(status))
		{
			//Неудачная аутентификация.
			return null;
		}
		if (SYSTEM_EXCEPTION_CODE.contains(status))
		{
			//Сервис временно недоступен.
			throw new AdjacentServiceUnavailableException(status);
		}
		//Все остальные коды - системная ошибка.
		throw new SystemException("Системная ошибка взаимодействия с iPas: получен код отказа " + status);
	}

	/**
	 * Подготовка ввода одноразового пароля с чека.
	 * @param userId идентификатор
	 * @return Контейнер. В нем содержится SID, номер чека и номер пароля, количество отавшихся  паролей
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
			//Удачная аутентификация.
			return new IPasPasswordInformation(result.getSID(), result.getPasswordNo(), result.getReceiptNo(), result.getPasswordsLeft());
		}
		if (AUTH_FAILURE_CODE.contains(status))
		{
			//Неудачная аутентификация.
			return null;
		}
		if (SERVICE_UNAVAILABLE_CODE.equals(status))
		{
			//Сервис временно недоступен.
			throw new ServiceUnavailableException("Получен ответ о недоступности смежных сервисов от iPas: " + status);
		}
		//Все остальные коды - системная ошибка.
		throw new SystemException("Системная ошибка взаимодействия с iPas: получен код отказа " + status);
	}

	/**
	 * Проверка правильности ввода одноразового пароля с чека.
	 * @param sid содержит в себе параметры для проверки (пароль, etc.)
	 * @param password пароль, введенный клиентом
	 * @return оставшееся количество попыток, если пароль валиден возвращаем null
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
			//Удачная аутентификация.
			return null;
		}
		if (ERR_AGAIN.equals(status) || ERR_NOTRIES.equals(status))
		{
			return result.getAttempts();
		}
		if (SERVICE_UNAVAILABLE_CODE.equals(status))
		{
			//Сервис временно недоступен.
			throw new ServiceUnavailableException("Получен ответ о недоступности смежных сервисов от iPas: " + status);
		}
		//Все остальные коды - системная ошибка.
		throw new SystemException("Системная ошибка взаимодействия с iPas: получен код отказа " + status);
	}

	private String generateSTAN()
	{
		return Utils.generateGUID();
	}

	private CSAUserInfo fillUserInfo(String login, UserInfoType userInfoType)
	{
		if (userInfoType == null)
		{
			throw new IllegalArgumentException("userInfoType не должен быть null");
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
