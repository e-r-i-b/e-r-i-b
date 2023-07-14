package com.rssl.phizic.test.way4;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.test.way4.actions.MockChangeIPasPasswordByLoginPassword;
import com.rssl.phizic.test.way4.actions.MockIPasGetClientByLoginPasswordJDBCAction;
import static com.rssl.phizic.test.way4.actions.MockIPasGetClientByLoginPasswordJDBCAction.DATA_NOT_FOUND_RET_CODE;
import static com.rssl.phizic.test.way4.actions.MockIPasGetClientByLoginPasswordJDBCAction.INVALID_PASSWORD_RET_CODE;
import com.rssl.phizic.test.way4.ws.mock.generated.*;
import com.rssl.phizic.logging.messaging.System;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Gainanov
 * @ created 13.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class IPASWSSoap_Impl implements IPASWSSoap
{
	private final JDBCActionExecutor executor = new JDBCActionExecutor("jdbc/MobileBank",System.WebTest);
	private static Map SIDMap = new HashMap();

	public VerifyRsType verifyPassword(String STAN, String userId, String password) throws RemoteException
	{
		try
		{
			Pair<Integer, UserInfo> userInfoPair = executor.execute(new MockIPasGetClientByLoginPasswordJDBCAction(userId, password));

			// если не правильный логин
			if(userInfoPair.getFirst().equals(DATA_NOT_FOUND_RET_CODE))
				return new VerifyRsType(STAN, "ERR_USRSRV", new UserInfoType(), "");

			// если не правильный пароль
			if(userInfoPair.getFirst().equals(INVALID_PASSWORD_RET_CODE))
				return new VerifyRsType(STAN, "ERR_BADPSW", new UserInfoType(), "");

			UserInfo userInfo = userInfoPair.getSecond();

			UserInfoType userInfoType = new UserInfoType(
					userInfo.getSurname(),
					userInfo.getFirstname(),
					userInfo.getPatrname(),
					userInfo.getPassport(),
					userInfo.getPassport(), // регистрационный номер пользователя совпадает с номером паспорта
					userInfo.getCbCode(),
					"1234",
					userInfo.getBirthdate() != null ?
							DateHelper.toXMLDateFormat(userInfo.getBirthdate().getTime()) : "",
					"1",
					"1",
					userInfo.getCardNumber());

			return new VerifyRsType(STAN, "AUTH_OK", userInfoType, "");
		}
		catch (SystemException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public PrepareOTPRsType prepareOTP(String STAN, String userId) throws RemoteException
	{
		String SID = SIDGenerator.getUniqueID();
		SIDMap.put(SID, 3);
		return new PrepareOTPRsType("123", "OK", SID, "10", "6", 9);
	}

	public VerifyAttRsType verifyOTP(String STAN, String SID, String password) throws RemoteException
	{
		if(SIDMap.containsKey(SID))
		{
			if(!password.startsWith("5"))  //Все пароли, начинающиеся с 5, считаем невалидными 
				return new VerifyAttRsType("123", "AUTH_OK", new UserInfoType("","","","","","","","","","",""), "111", 0);
			else
			{
				Integer count = ((Integer)SIDMap.get(SID))-1;
				if(count == 0)
				{
					SIDMap.remove(SID);
					return new VerifyAttRsType("123", "ERR_NOTRIES", new UserInfoType("","","","","","","","","","",""), "111", 0);
				}
				SIDMap.put(SID, count);
				return new VerifyAttRsType("123", "ERR_AGAIN", new UserInfoType("","","","","","","","","","",""), "111", count);
			}
		}
		else
			return new VerifyAttRsType("123", "ERR_FORMAT", new UserInfoType("","","","","","","","","","",""), "111", 0);

	}

	public GeneratePasswordRsType generatePassword(String STAN, String userId, String password) throws RemoteException
	{
		try
		{
			Pair<Integer, String> userInfoPair = executor.execute(new MockChangeIPasPasswordByLoginPassword(userId, password));

			// если не правильный логин
			if(userInfoPair.getFirst().equals(DATA_NOT_FOUND_RET_CODE))
				return new GeneratePasswordRsType("123", "ERR_USRSRV", null);

			// если не правильный пароль
			if(userInfoPair.getFirst().equals(INVALID_PASSWORD_RET_CODE))
				return new GeneratePasswordRsType("123", "ERR_BADPSW", null);
			                            
			return new GeneratePasswordRsType("123", "AUTH_OK", userInfoPair.getSecond());
		}
		catch (SystemException e)
		{
			throw new RemoteException();
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
}
