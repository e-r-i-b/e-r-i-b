package com.rssl.auth.csa.back.integration;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.way4u.messaging.Way4uUserInfoService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.Arrays;
import java.util.List;

/**
 * @author krenev
 * @ created 08.10.2013
 * @ $Author$
 * @ $Revision$
 * �����, ��������������� ���������� � ������������ �� �� � ��������������� ������ ������ ����������� �������� ������
 */

public class UserInfoProvider
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final List<String> NOT_ACCESS_TB_CODES = Arrays.asList("91", "92");
	private static final UserInfoProvider INSTANCE = new UserInfoProvider();

	/**
	 * @return ������� ����������
	 */
	public static UserInfoProvider getInstance()
	{
		return INSTANCE;
	}

	/**
	 * �������� ���������� � ������������ �� ������ �����
	 * @param cardNumber ����� �����
	 * @return ���������� � ������������ ��� null
	 */
	public UserInfo getUserInfoByCardNumber(String cardNumber) throws SystemException, GateLogicException
	{
		Cache cache = CacheProvider.getCache("user-info-by-card-number");
		if (cache == null)
		{
			return getDirectUserInfoByCardNumber(cardNumber);
		}
		Element element = cache.get(cardNumber);
		if (element == null)
		{
			UserInfo result = getDirectUserInfoByCardNumber(cardNumber);
			cache.put(new Element(cardNumber, result));
			return result;
		}
		return (UserInfo) element.getObjectValue();
	}

	private UserInfo getDirectUserInfoByCardNumber(String cardNumber) throws SystemException, GateLogicException
	{
		Config.UserInfoProviderName userInfoProvider = ConfigFactory.getConfig(Config.class).getUserInfoProvider();
		switch (userInfoProvider)
		{
			case MOBILE_BANK:
			{
				UserInfo userInfo = MobileBankService.getInstance().getClientByCardNumber(cardNumber);
				return isAccessUserInfo(userInfo) ? userInfo : null;
			}
			case WAY4U:
			{
				UserInfo userInfo = Way4uUserInfoService.getInstance().getUserInfoByCardNumber(cardNumber, false);
				if (userInfo == null && ConfigFactory.getConfig(Config.class).getWAY4UWorkaround089795())
				{
					//���� ������ �� ��� �� ���������, ������� �������� ������ �� ��(ENH086371: [ISUP] ��� ��������� ������ �� �������� CSA_Way4U ������ ������ � ���).
					log.warn("������ �� Way4u_CSA ��� ����� " + Utils.maskCard(cardNumber) + " �� ���������, ������� �������� ������ �� ���.");
					userInfo = MobileBankService.getInstance().getClientByCardNumber(cardNumber);
				}
				return isAccessUserInfo(userInfo) ? userInfo : null;
			}
			default:
				throw new IllegalStateException("����������� �������� ��������� ������ � ������������� " + userInfoProvider);
		}
	}

	/**
	 * �������� ���������� � ������������ �� ������ iPas
	 * @param userId ����� iPas
	 * @return ���������� � ������������ ��� null
	 */
	public UserInfo getUserInfoByUserId(String userId) throws SystemException, GateLogicException
	{
		Cache cache = CacheProvider.getCache("user-info-by-login");
		if (cache == null)
		{
			return getDirectUserInfoByUserId(userId);
		}
		Element element = cache.get(userId);
		if (element == null)
		{
			UserInfo result = getDirectUserInfoByUserId(userId);
			cache.put(new Element(userId, result));
			return result;
		}
		return (UserInfo) element.getObjectValue();
	}

	private UserInfo getDirectUserInfoByUserId(String userId) throws SystemException, GateLogicException
	{
		Config.UserInfoProviderName userInfoProvider = ConfigFactory.getConfig(Config.class).getUserInfoProvider();
		switch (userInfoProvider)
		{
			case MOBILE_BANK:
			{
				UserInfo userInfo = MobileBankService.getInstance().getClientByLogin(userId);
				// ���� �� ��������, ��������� ������ "�� �������"
				return isAccessUserInfo(userInfo) ? userInfo : null;
			}
			case WAY4U:
			{
				UserInfo userInfo = Way4uUserInfoService.getInstance().getUserInfoByUserId(userId, true);
				if (userInfo == null && ConfigFactory.getConfig(Config.class).getWAY4UWorkaround089795())
				{
					//���� ������ �� ��� �� ���������, ������� �������� ������ �� ��(ENH086371: [ISUP] ��� ��������� ������ �� �������� CSA_Way4U ������ ������ � ���).
					log.warn("������ �� Way4u_CSA ��� ������ iPas " + userId + " �� ���������, ������� �������� ������ �� ���.");
					userInfo = MobileBankService.getInstance().getClientByLogin(userId);
				}
				// ���� �� ��������, ��������� ������ "�� �������"
				return isAccessUserInfo(userInfo) ? userInfo : null;
			}
			default:
				throw new IllegalStateException("����������� �������� ��������� ������ � ������������� " + userInfoProvider);
		}
	}

	/**
	 * ����������� ���������� � ������������ �� ��
	 * @param userInfo ���������� � ������������
	 * @return true - ��������(����������� �� �������������)
	 */
	private boolean isAccessUserInfo(UserInfo userInfo)
	{
		if (userInfo == null)
			return false;

		String tb = userInfo.getCbCode().substring(0, 2);
		return !NOT_ACCESS_TB_CODES.contains(tb);
	}
}
