package com.rssl.phizicgate.way4u.messaging;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizicgate.way4u.messaging.requests.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author krenev
 * @ created 08.10.2013
 * @ $Author$
 * @ $Revision$
 * Сервис получения информации о пользователе из way4u.
 */

public class Way4uUserInfoService extends Way4uTransport
{
	private static final Way4uUserInfoService INSTANCE = new Way4uUserInfoService();
	private static final Set<String> DATA_NOT_FOUND_RET_CODES = new HashSet<String>(Arrays.asList(new String[]{"14", "15", "25", "56", "78", "1900", "2200", "2400", "2620", "2600", "2610"}));
	private static final String SUCCESS_RET_CODE = "0";

	/**
	 * @return инстанс сервиса
	 */
	public static Way4uUserInfoService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Получить информацию о пользователе по номеру карты
	 * @param cardNumber номер карты
	 * @param oneContract получать инфу по 1 запрашиваемой карте или по всем...
	 * @return Информация о пользователе или null
	 */
	public UserInfo getUserInfoByCardNumber(String cardNumber, boolean oneContract) throws SystemException, GateTimeOutException
	{
		if (cardNumber == null)
		{
			throw new IllegalArgumentException("Номер карты не может быть null");
		}

		GetUserInfoByCardNumberRequest request = oneContract ? new GetUserInfoByCardNumberRequest(cardNumber) : new GetUserInfoWithAllCardsByCardNumberRequest(cardNumber);
		UserInfo userInfo = UserInfoBuilder.buildForCard(doRequest(request), cardNumber);
		if (userInfo == null)
		{
			return null;
		}

		if (!cardNumber.equals(userInfo.getCardNumber()))
		{
			throw new SystemException("Получена информация не по запрашиваемой карте. Идентфикатор запроса: " + request.getId());
		}
		return userInfo;
	}

	/**
	 * Получить информацию о пользователе по логину iPas
	 * @param userId логин iPas
	 * @param oneContract получать инфу по 1 запрашиваемому логину или по всем...
	 * @return Информация о пользователе или null
	 */
	public UserInfo getUserInfoByUserId(String userId, boolean oneContract) throws SystemException, GateTimeOutException
	{
		if (userId == null)
		{
			throw new IllegalArgumentException("Логин iPas не может быть null");
		}
		GetUserInfoByUserIdRequest request = oneContract ? new GetUserInfoByUserIdRequest(userId) : new GetUserInfoWithAllCardsByUserIdRequest(userId);

		UserInfo userInfo = UserInfoBuilder.buildForUserId(doRequest(request), userId);
		if (userInfo == null)
		{
			return null;
		}

		if (!userId.equals(userInfo.getUserId()))
		{
			throw new SystemException("Получена информация не по запрашиваему логину. Идентфикатор запроса: " + request.getId());
		}

		return userInfo;
	}

	/**
	 * запрос на подключение/отключение ЕРМБ
	 * @param userInfo - информация о клиенте
	 * @param ermbConnected - подключен клиент к ЕРМБ (true) или отключен (false)
	 * @throws SystemException
	 * @throws GateTimeOutException
	 */
	public void sendErmbConnectInfo(UserInfo userInfo, boolean ermbConnected) throws SystemException, GateTimeOutException
	{
		ErmbConnectionInfoRequest request = new ErmbConnectionInfoRequest(userInfo, ermbConnected);
		Way4uResponse response = doRequest(request);

		if (response != null)
		{
			String respCode = response.getRespCode();
			String respTest = response.getRespText();
			if (DATA_NOT_FOUND_RET_CODES.contains(respCode))
			{
				log.warn(String.format("Получен код отказа %s(%s), который приравнивается к ответу 'Данные не найдены'.", respCode, respTest));
			}
			if (!SUCCESS_RET_CODE.equals(respCode))
			{
				throw new GateException(String.format("Получен код отказа %s(%s) от way4u.", respCode, respTest));
			}
		}
	}
}