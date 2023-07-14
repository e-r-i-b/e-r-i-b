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
 * ������ ��������� ���������� � ������������ �� way4u.
 */

public class Way4uUserInfoService extends Way4uTransport
{
	private static final Way4uUserInfoService INSTANCE = new Way4uUserInfoService();
	private static final Set<String> DATA_NOT_FOUND_RET_CODES = new HashSet<String>(Arrays.asList(new String[]{"14", "15", "25", "56", "78", "1900", "2200", "2400", "2620", "2600", "2610"}));
	private static final String SUCCESS_RET_CODE = "0";

	/**
	 * @return ������� �������
	 */
	public static Way4uUserInfoService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * �������� ���������� � ������������ �� ������ �����
	 * @param cardNumber ����� �����
	 * @param oneContract �������� ���� �� 1 ������������� ����� ��� �� ����...
	 * @return ���������� � ������������ ��� null
	 */
	public UserInfo getUserInfoByCardNumber(String cardNumber, boolean oneContract) throws SystemException, GateTimeOutException
	{
		if (cardNumber == null)
		{
			throw new IllegalArgumentException("����� ����� �� ����� ���� null");
		}

		GetUserInfoByCardNumberRequest request = oneContract ? new GetUserInfoByCardNumberRequest(cardNumber) : new GetUserInfoWithAllCardsByCardNumberRequest(cardNumber);
		UserInfo userInfo = UserInfoBuilder.buildForCard(doRequest(request), cardNumber);
		if (userInfo == null)
		{
			return null;
		}

		if (!cardNumber.equals(userInfo.getCardNumber()))
		{
			throw new SystemException("�������� ���������� �� �� ������������� �����. ������������ �������: " + request.getId());
		}
		return userInfo;
	}

	/**
	 * �������� ���������� � ������������ �� ������ iPas
	 * @param userId ����� iPas
	 * @param oneContract �������� ���� �� 1 �������������� ������ ��� �� ����...
	 * @return ���������� � ������������ ��� null
	 */
	public UserInfo getUserInfoByUserId(String userId, boolean oneContract) throws SystemException, GateTimeOutException
	{
		if (userId == null)
		{
			throw new IllegalArgumentException("����� iPas �� ����� ���� null");
		}
		GetUserInfoByUserIdRequest request = oneContract ? new GetUserInfoByUserIdRequest(userId) : new GetUserInfoWithAllCardsByUserIdRequest(userId);

		UserInfo userInfo = UserInfoBuilder.buildForUserId(doRequest(request), userId);
		if (userInfo == null)
		{
			return null;
		}

		if (!userId.equals(userInfo.getUserId()))
		{
			throw new SystemException("�������� ���������� �� �� ������������ ������. ������������ �������: " + request.getId());
		}

		return userInfo;
	}

	/**
	 * ������ �� �����������/���������� ����
	 * @param userInfo - ���������� � �������
	 * @param ermbConnected - ��������� ������ � ���� (true) ��� �������� (false)
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
				log.warn(String.format("������� ��� ������ %s(%s), ������� �������������� � ������ '������ �� �������'.", respCode, respTest));
			}
			if (!SUCCESS_RET_CODE.equals(respCode))
			{
				throw new GateException(String.format("������� ��� ������ %s(%s) �� way4u.", respCode, respTest));
			}
		}
	}
}